package wu.justin.doclet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestResultHandler {
	
	private static final String RegExSpecialChars = "<([{\\^-=$!|]})?*+.>";  // all regualr Express special characters which need escape
	private static final String RegExSpecialCharsRE = RegExSpecialChars.replaceAll( ".", "\\\\$0");  // replace all characters with \
	private static final Pattern reCharsREP = Pattern.compile( "[" + RegExSpecialCharsRE + "]");
	
	private Map<String, TestResult> allTestResults = new HashMap<>();
	
	private String prefix;
	private String testResultFolder;
	
	
	public TestResultHandler(String prefix, String testResultFolder){
		this.prefix = prefix;
		this.testResultFolder = testResultFolder;
		loadTestResults();
	}
	
	
	private static String quoteRegExSpecialChars( String s)	{
	    Matcher m = reCharsREP.matcher( s);
	    return m.replaceAll( "\\\\$0");
	}
	
	private  void loadTestResults() {
		
		File resultFolder = new File(testResultFolder);
		if(!resultFolder.exists()){
			System.out.println("didn't find " + testResultFolder);
			return;
		}
		if(!resultFolder.isDirectory()){
			throw new RuntimeException("integration.test.result is not a folder: " + testResultFolder);
		}
		
		File[] files = resultFolder.listFiles();
		
		for(File file :files){
			//System.out.println("TestResult: " + file.getAbsolutePath());
			try {
				TestResult result = handleOneFile(file);
				allTestResults.put(file.getAbsolutePath(), result);
			} catch (IOException e) {
				// impossible to get here
				throw new RuntimeException("impossible error:", e);
			}			

		}		
	}
	
	// please align with ApiTestUtil.saveOutput if you change it
	private static TestResult handleOneFile(File file) throws IOException{

		FileReader reader = new FileReader(file);					
		try(BufferedReader br = new BufferedReader(reader)){
		
			String method = br.readLine();  // Method: GET
			method = method.substring(7).trim();
			String status = br.readLine();  // status: 200
			status = status.substring(7).trim();
			String url = br.readLine();  // Url: http://localhost:12001/jersey2/api/users/user/current.json
			
			String url2 = url.split("/", 5)[4];  // remove host port and application context, will get api/users/user/current.json
			url = "/" + url2;
			String cost = br.readLine();  // cost: 225ms
			cost = cost.substring(5).trim();
			String start = br.readLine();  // start: Mon Oct 02 14:25:07 EDT 2017
			start= start.substring(6).trim();
			String end = br.readLine();  // end: Mon Oct 02 14:25:07 EDT 2017			
			end= end.substring(4).trim();
			
			StringBuilder sb = new StringBuilder();
			String oneLine = br.readLine();
			while (oneLine != null){
				sb.append(oneLine).append("\r\n");
				oneLine = br.readLine();
			}			
			TestResult result =  new TestResult(method, status, url, cost, start, end);
			result.setFilePath(file.getAbsolutePath()); 
			return result;
		}
		
	}
	
	public static String getJsonFile(File file) throws IOException{

		FileReader reader = new FileReader(file);					
		try(BufferedReader br = new BufferedReader(reader)){
		
			br.readLine();  // Method: GET
			
			br.readLine();  // status: 200
			
			br.readLine();  // Url: http://localhost:12001/jersey2/api/users/user/current.json
			
			br.readLine();  // cost: 225ms
			
			br.readLine();  // start: Mon Oct 02 14:25:07 EDT 2017
			
			br.readLine();  // end: Mon Oct 02 14:25:07 EDT 2017
			
			StringBuilder sb = new StringBuilder();
			String oneLine = br.readLine();
			while (oneLine != null){
				sb.append(oneLine).append("\r\n");
				oneLine = br.readLine();
			}
			
			return sb.toString();
		}
		
	}
	
	public List<TestResult> findResultFiles(String apiUrl, String httpMethod, ApiEntry apiEntry){
		
		List<TestResult> results =  new ArrayList<>();
		
		for(String filePath : allTestResults.keySet()){
			TestResult oneResult = allTestResults.get(filePath);
			
			//System.out.println( oneResult);
			
			if(!httpMethod.equals(oneResult.getMethod())){
				continue;
			}

			if(matchUrl(apiUrl, apiEntry, oneResult, prefix)){
				results.add(oneResult);
			}
		}		
		return results;		
	}
	
	// assume one url seg only has at maximum one path parameter: {userId}{programId} is not allowed
	// assume urlpath never have a string wuyg719
	private static boolean matchUrl(String apiUrl, ApiEntry apiEntry, TestResult oneResult, String prefix){
		//String Prefix = "/api";		
		//String apiUrl = "/users/user/{userId}";
		// String testUrl = "/api/users/user/12";
		
		//System.out.println( "oneResult = " + oneResult);
		//System.out.println( "apiUrl = " + apiUrl);
		
		String url = oneResult.getUrl();  //  /api/users/user/12
		String [] urlSegs = url.split("/");
		
		String apiUrl2= prefix+ apiUrl;
		
		String [] apiUrlSegs = apiUrl2.split("/");
		if(urlSegs.length != apiUrlSegs.length){
			return false;
		}
		
		for(int i=0;i< apiUrlSegs.length; i++){
			if(apiUrlSegs[i].startsWith("{")){
				if(!handlePathParameterSeg(apiEntry, apiUrlSegs[i], urlSegs[i])){
					return false;
				}
			}else{
				if(!apiUrlSegs[i].equals(urlSegs[i])){
					return false;
				}
			}
		}
		return true;			
	}
	
	//   abc{name}.json  should match abc234sdfllsduew\|][r.43232jsf.json
	//   abc{int}.json should match abc-2328.json
	protected static boolean handlePathParameterSeg(ApiEntry apiEntry, String apiUrlSeg, String resultUrlSeg){
		if(apiEntry.getParameters() == null){
			return false;
		}
		if(apiEntry.getParameters().isEmpty()){
			return false;
		}
		ParameterEntry parameter = findPathParameter(apiEntry, apiUrlSeg);
		if(parameter == null){
			return false;
		}
		String pathName ="{"+ parameter.getName() + "}";
		String escapeResultEeg = apiUrlSeg.replace(pathName, "wuyg719");
		
		escapeResultEeg = quoteRegExSpecialChars(escapeResultEeg);
		if(parameter.getJavaType().equals("Integer")){
			escapeResultEeg = escapeResultEeg.replace("wuyg719", "[-]?\\d+");  // match any digtal 	
		}else if(parameter.getJavaType().equals("String")){
			escapeResultEeg = escapeResultEeg.replace("wuyg719", "\\S+"); // match any non space character 
		}else{
			// TODO handle other type
			return false;
		}
		
		Pattern pattern = Pattern.compile( escapeResultEeg );
		
		Matcher m = pattern.matcher(resultUrlSeg);
		if(m.matches()){
			return true;
		}else{
			return false;
		}
		
	}
	
	protected static ParameterEntry findPathParameter(ApiEntry apiEntry, String apiUrlSeg){
		for(ParameterEntry parameter : apiEntry.getParameters()){
			if(parameter.getType().equals("PathParam")){
				
				String pathName ="{"+ parameter.getName() + "}";
				if(apiUrlSeg.contains(pathName)){
					return parameter; 
				}
				
			}
		}	
		return null;

	}

}

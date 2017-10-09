package wu.justin.doclet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestResultHandler {
	
	private Map<String, TestResult> allTestResults = new HashMap<>();
	
	private String prefix;
	
	public TestResultHandler(String prefix){
		this.prefix = prefix;
	}
	public void loadTestResults(String location) {
		
		File resultFolder = new File(location);
		if(!resultFolder.exists()){
			System.out.println("didn't find " + location);
			return;
		}
		if(!resultFolder.isDirectory()){
			throw new RuntimeException("integration.test.result is not a folder: " + location);
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
	
	public List<TestResult> findResultFiles(String apiUrl, String httpMethod){
		
		List<TestResult> results =  new ArrayList<>();
		
		for(String filePath : allTestResults.keySet()){
			TestResult oneResult = allTestResults.get(filePath);
			
			//System.out.println( oneResult);
			
			if(!httpMethod.equals(oneResult.getMethod())){
				continue;
			}
			if(matchUrl(apiUrl, oneResult, prefix)){
				results.add(oneResult);				
			}
		}
		
		return results;
		
	}
	
	
	private static boolean matchUrl(String apiUrl, TestResult oneResult, String prefix){
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
				
				try{
					Integer.parseInt(urlSegs[i]);					
				} catch (NumberFormatException e) {
					return false;
				}
				// it has a defect, TODO handle it with PathParam
			}else{
				if(!apiUrlSegs[i].equals(urlSegs[i])){
					return false;
				}
			}
		}
		return true;			
	}

}

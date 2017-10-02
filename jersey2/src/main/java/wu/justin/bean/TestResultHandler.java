package wu.justin.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
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
	
	private static TestResult handleOneFile(File file) throws IOException{

		FileReader reader = new FileReader(file);					
		try(BufferedReader br = new BufferedReader(reader)){
		
			String method = br.readLine();  // Method: GET
			method = method.substring(7).trim();
			String status = br.readLine();  // status: 200
			status = status.substring(7).trim();
			String url = br.readLine();  // Url: http://localhost:12001/jersey2/api/users/user/current.json
			url = url.substring(4).trim();
			String cost = br.readLine();  // cost: 225ms
			cost = cost.substring(5).trim();
			String start = br.readLine();  // start: Mon Oct 02 14:25:07 EDT 2017
			start= start.substring(6).trim();
			String end = br.readLine();  // end: Mon Oct 02 14:25:07 EDT 2017			
			end= end.substring(4).trim();
			
			TestResult result =  new TestResult(method, status, url, cost, start, end);
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
				sb.append(oneLine);
				oneLine = br.readLine();
			}
			
			return sb.toString();
		}
		
	}
	
	public String findResultFile(String apiUrl, String httpMethod){
		
		for(String filePath : allTestResults.keySet()){
			TestResult oneResult = allTestResults.get(filePath);
			
			//System.out.println( oneResult);
			
			if(!httpMethod.equals(oneResult.getMethod())){
				return null;
			}
			if(matchUrl(apiUrl, oneResult, prefix)){
				return filePath;
			}
		}
		return null;
		
	}
	
	
	private static boolean matchUrl(String apiUrl, TestResult oneResult, String prefix){
		//String Prefix = "http://localhost:8080/jersey2/api";		
		//String apiUrl = "/users/user/{userId}";
		
		// String testUrl = "http://localhost:8080/jersey2/api/users/user/12"; 
		
		System.out.println( "Prefix=" + prefix);
		
		System.out.println( "oneResult = " + oneResult);
		System.out.println( "apiUrl = " + apiUrl);
		
		
		String url = oneResult.getUrl().substring(prefix.length());  //  /users/user/12
		String [] urlSegs = url.split("/");
		String [] apiUrlSegs = apiUrl.split("/");
		if(urlSegs.length != apiUrlSegs.length){
			return false;
		}
		
		for(int i=0;i< apiUrlSegs.length; i++){
			if(apiUrlSegs[i].startsWith("{")){
				// TODO handle PathParam
			}else{
				if(!apiUrlSegs[i].equals(urlSegs[i])){
					return false;
				}
			}
		}
		return true;			
	}

}

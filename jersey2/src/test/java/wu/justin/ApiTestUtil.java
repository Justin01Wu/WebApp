package wu.justin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import static org.junit.Assert.assertEquals;


public final class ApiTestUtil {
	
	private ApiTestUtil() {		
	}
	
	public static String getReturn(HttpResponse response) throws HttpException, IOException{		
		
		StringBuffer result = new StringBuffer();
		
		try (BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		}
		
		return result.toString();
		
	}
	
	public static String getUrlRoot(){
		
		String port = System.getProperty("maven.tomcat.port");  // embedded Tomcat will use this parameter, so align to it
		if(port == null){
			port = "8080";
		}else{
			try{
				Integer.valueOf(port);
			}catch(NumberFormatException e){
				System.out.println("wrong maven.tomcat.port parameter: " + port);
				port = "8080";
			}
		}
		
		final String URL_ROOT = "http://localhost:" + port + "/jersey2";
		
		return URL_ROOT;		
		
	}
	
	public static String getResponseBodyByGetRequest(HttpClient client, HttpGet request, int statusCodeExpected) throws HttpException, IOException {
		
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(statusCodeExpected, statusCode);
		System.out.println("Status code: " + statusCode);
									    
		String responseBody = null;
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT){
			responseBody = getReturn(response);
		};
		return responseBody;
		
	}
	
	public static String getReponseBodyByPutRequest(HttpClient client, HttpPut request, String data, int statusCodeExpected) throws HttpException, IOException{
		
		StringEntity params = new StringEntity(data,"UTF-8");
        params.setContentType("application/json");
        request.addHeader("content-type", "application/json");
        request.addHeader("Accept", "*/*");
        request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        request.addHeader("Accept-Language", "en-US,en;q=0.8");
        request.setEntity(params);
        
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(statusCodeExpected, statusCode);
       
		String responseBody = null;
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT){
			responseBody = getReturn(response);
		}
        return responseBody;
	}
	
	public static String getResponseBodyByPostReqeust(HttpClient client, HttpPost request, String data, int statusCodeExpected) throws HttpException, IOException{
		 
		StringEntity params = new StringEntity(data, "UTF-8");
		params.setContentType("application/json");
        request.addHeader("content-type", "application/json");
        request.addHeader("Accept", "*/*");
        request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        request.addHeader("Accept-Language", "en-US,en;q=0.8");
        request.setEntity(params);

        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(statusCodeExpected, statusCode);
        
		String responseBody = null;
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT){
			responseBody = getReturn(response);
		}
		return responseBody;
	}
	
	public static String getResponseBodyByDeleteRequest(HttpClient client, HttpDelete request, int statusCodeExpected) throws IOException, HttpException{
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(statusCodeExpected, statusCode);
		
		String responseBody = null;
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT){
			responseBody = getReturn(response);
		}
		return responseBody;
	}
	
	
	  public static String readJSONFile(String fileName) throws FileNotFoundException, URISyntaxException {
		  
		  File targetFile = new File("src\\test\\resources\\" + fileName);
		  System.out.println("Json File: " + targetFile.getAbsolutePath());
		  
		  String content = new Scanner(targetFile).useDelimiter("\\Z").next();
		  System.out.println("Content :" + content);
		  return content;
	  }
	  
	  public static void save(String Url, HttpRequestBase request, String JsonFile, String caseName, String type) throws FileNotFoundException{
		  
		  String fileName = null;
		  String dir = null;
		  
		  switch(type){
			  case "output":
				  fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + "_" +caseName + ".txt";
				  dir = "target\\test-results\\";
				  break;
			  case "input":
				  fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + "_" +caseName + "_input.txt";
				  dir = "target\\test-input-json\\";
				  break;
		  }
		  
		  File dirF = new File(dir);
			if (!dirF.exists()){
				dirF.mkdirs();
			}


		  String requestType = request.getMethod();
		  
		  try (PrintStream out = new PrintStream(new FileOutputStream(dir + fileName))) {
			    out.println("Method: " + requestType);
			    out.println("Url: " + Url);
			    out.print(JsonFile);
		  }
	  }
}

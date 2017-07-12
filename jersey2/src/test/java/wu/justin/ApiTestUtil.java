package wu.justin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import com.fasterxml.jackson.databind.ObjectMapper;


public final class ApiTestUtil {
	
	public static final String ISO_LONG_DATE_FORMAT = "HHmmss";
	
	private ApiTestUtil() {		
	}
	
	private static String getReturn(HttpResponse response) throws HttpException, IOException{		
		
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
	
	public static String getResponseBodyByGetRequest(HttpClient client, HttpGet request, Integer... statusCodeExpected) throws HttpException, IOException {
		
		Date start =  new Date();
		HttpResponse response = client.execute(request);
		Date end =  new Date();
		int statusCode = response.getStatusLine().getStatusCode();
									    
		String responseBody = saveOutput( request, response, start, end);
		
		List<Integer> statusList = Arrays.asList(statusCodeExpected);
		
		assertTrue(statusList.contains(statusCode));
		//assertEquals(statusCodeExpected, statusCode);

		return responseBody;
		
	}
	
	public static String getResponseBodyByPutRequest(HttpClient client, HttpPut request, String data, int statusCodeExpected) throws HttpException, IOException{
		StringEntity params = new StringEntity(data,"UTF-8");
        params.setContentType("application/json");
        request.addHeader("content-type", "application/json");
        request.addHeader("Accept", "*/*");
        request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        request.addHeader("Accept-Language", "en-US,en;q=0.8");
        request.setEntity(params);
        
        Date start =  new Date();
        HttpResponse response = client.execute(request);
        Date end =  new Date();
        
        int statusCode = response.getStatusLine().getStatusCode();
        
        String responseBody = saveOutput( request, response, start, end);
		
        assertEquals(statusCodeExpected, statusCode);
       

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

		Date start =  new Date();
		HttpResponse response = client.execute(request);
		Date end =  new Date();
		
        int statusCode = response.getStatusLine().getStatusCode();
        
        String responseBody = saveOutput( request, response, start, end);
        
        assertEquals(statusCodeExpected, statusCode);
        
		return responseBody;
	}
	
	public static String getResponseBodyByDeleteRequest(HttpClient client, HttpDelete request, int statusCodeExpected) throws IOException, HttpException{
		
		Date start =  new Date();
		HttpResponse response = client.execute(request);
		Date end =  new Date();
		
		int statusCode = response.getStatusLine().getStatusCode();		
		
		String responseBody = saveOutput( request, response, start, end);
		
		assertEquals(statusCodeExpected, statusCode);

		return responseBody;
	}
	
	
	  public static String readJSONFile(String fileName) throws FileNotFoundException, URISyntaxException {
		  
		  File targetFile = new File("src\\test\\resources\\" + fileName);
		  System.out.println("Json File: " + targetFile.getAbsolutePath());
		  
		  Scanner scanner = new Scanner(targetFile);
		  String content = scanner.useDelimiter("\\Z").next();
		  scanner.close();
		  
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
	  
		private static String saveOutput(HttpRequestBase request, HttpResponse response, Date start, Date end) throws HttpException, IOException {

			String requestType = request.getMethod();

			String url = request.getURI().toString();

			Header type = response.getFirstHeader("Content-Type");
			String typeStr = type.getValue();
			
			String responseBody = getReturn(response);
			if (responseBody.length() > 20) {
				String newFormat = null;
				if(typeStr.startsWith("text/html")){  // Content-Type: text/html;charset=utf-8
					newFormat = getFormatedHtmlOrNull(responseBody);
				}else if(typeStr.startsWith("application/json")){   // Content-Type: application/json
					newFormat = getFormatedJsonOrNull(responseBody);	
				}			
				if (newFormat != null) {
					responseBody = newFormat;
				}
			}
			int statusCode = response.getStatusLine().getStatusCode();

			int backLevel = 4;
			String caseName = getCaseName(backLevel);

			try (PrintStream out = getPrintStream("output", caseName);) {
				long cost =  end.getTime() - start.getTime();
				out.println("Method: " + requestType  + "\tstatus: " + statusCode);
				out.println("Url: " + url);
				out.println(String.format("cost: %dms, start: %s   ---    end: %s   " , cost, start.toString(), end.toString()));
				
				out.println(responseBody);
			}

			return responseBody;
		}
		
		public static String getCurrentOnISO() {
			
			DateFormat df = new SimpleDateFormat(ISO_LONG_DATE_FORMAT);
			
			return df.format(new java.util.Date());
		}	
		
		private static PrintStream getPrintStream(String type, String caseName) throws FileNotFoundException {

			String dir = null;

			switch (type) {
			case "output":
				dir = "target\\test-output\\";
				break;
			case "input":
				dir = "target\\test-input\\";
				break;
			default:
				throw new IllegalArgumentException("unknown type: " + type);
			}

			File dirF = new File(dir);
			if (!dirF.exists()) {
				dirF.mkdirs();
			}		
			
			String fileName =  caseName + "_"  + getCurrentOnISO() +".txt";

			PrintStream out = new PrintStream(new FileOutputStream(dir + fileName));
			return out;

		}
		
		  private static String getCaseName(int backLevel){		  
			  
			  StackTraceElement caller = Thread.currentThread().getStackTrace()[backLevel];  
			  
			  String[] classNameArray = caller.getClassName().split("\\.");
			  String classSimpleName = classNameArray[classNameArray.length-1];
			  
			  String caseName = classSimpleName + "_"+caller.getMethodName();
			  return caseName;
			  
		  }
		
		private static String getFormatedJsonOrNull(String jsonInString) {
			
			try {
				ObjectMapper mapper = new ObjectMapper();
				Object json = mapper.readValue(jsonInString, Object.class);
				String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
				return indented;
			} catch (IOException e) {
				return null;
			}
		}
		
		private static String getFormatedHtmlOrNull(String aStr) {
			
			InputStream is = new ByteArrayInputStream( aStr.getBytes() );
			
			Tidy tidy = new Tidy(); 
			tidy.setIndentContent(true);		
			tidy.setPrintBodyOnly(true);
		    tidy.setTidyMark(false);
			//tidy.setQuiet(true);
			//tidy.setShowWarnings(false);
			Document htmlDOM = tidy.parseDOM(is, null);	
			
		    // Pretty Print
		    OutputStream out = new ByteArrayOutputStream();
		    tidy.pprint(htmlDOM, out);
		    return out.toString();
		}
}

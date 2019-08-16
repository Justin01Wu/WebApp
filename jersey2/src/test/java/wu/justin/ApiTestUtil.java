package wu.justin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.experimental.categories.Category;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import wu.justin.rest2.ApiUtil;


public final class ApiTestUtil {

	public static final String ISO_LONG_DATE_FORMAT = "HHmmss";

	private ApiTestUtil() {
	}

	public static String getCurrentOnISO() {

		DateFormat df = new SimpleDateFormat(ISO_LONG_DATE_FORMAT);

		return df.format(new java.util.Date());
	}


	private static String getReturn(HttpResponse response) throws HttpException, IOException {
		if (response.getEntity() == null) {
			return "";
		}

		StringBuffer result = new StringBuffer();

		try (BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		}

		return result.toString();

	}

	public static String getUrlRoot() {

		String port = System.getProperty("maven.tomcat.port");
		if (port == null) {
			port = "8080";
		} else {
			try {
				Integer.valueOf(port);
			} catch (NumberFormatException e) {
				System.err.println("wrong maven.tomcat.port parameter: " + port);
				port = "8080";
			}
		}

		final String URL_ROOT = "http://localhost:" + port + "/jersey2";
		return URL_ROOT;

	}
	
	public static String getResponseByRequest(HttpClient client, HttpGet request, Integer... statusCodeExpected)
			throws HttpException, IOException {
		
		Date start = new Date();
		HttpResponse response = client.execute(request);
		Date end = new Date();
		int statusCode = response.getStatusLine().getStatusCode();

		String responseBody = saveOutput(request, response, start, end);

		List<Integer> statusList = Arrays.asList(statusCodeExpected);

		assertTrue(statusList.contains(statusCode));
		return responseBody;

	}
	
	public static String getResponseByRequest(HttpClient client, HttpPut request, String jsonData,
			int statusCodeExpected) throws HttpException, IOException {
		
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");

		// set body
		if (jsonData != null) {
			request.addHeader("content-type", "application/json");
			StringEntity params = new StringEntity(jsonData, "UTF-8");
			params.setContentType("application/json");
			request.setEntity(params);
			saveInput(request, jsonData);
		}

		Date start = new Date();
		HttpResponse response = client.execute(request);
		Date end = new Date();

		int statusCode = response.getStatusLine().getStatusCode();

		String responseBody = saveOutput(request, response, start, end);

		assertEquals(statusCodeExpected, statusCode);

		return responseBody;
	}	

	// please align with TestResultHandler.handleOneFile if you change it
	private static String saveOutput(HttpRequestBase request, HttpResponse response, Date start, Date end)
			throws HttpException, IOException {

		String requestType = request.getMethod();

		String url = request.getURI().toString();

		System.out.println("    ==>> saving response for URL: " + url);

		String responseBody = getReturn(response);

		if (responseBody.length() > 20) {

			Header type = response.getFirstHeader("Content-Type");
			String typeStr = type.getValue();

			String newFormat = null;
			if (typeStr.startsWith("text/html")) { // Content-Type:
													// text/html;charset=utf-8
				newFormat = ApiUtil.getFormatedHtmlOrNull(responseBody);
			} else if (typeStr.startsWith("application/json")) { // Content-Type:
																	// application/json
				newFormat = ApiUtil.getFormatedJsonOrNull(responseBody);
			}
			if (newFormat != null) {
				responseBody = newFormat;
			}
		}
		int statusCode = response.getStatusLine().getStatusCode();

		String caseName = getCaseName();

		try (PrintStream out = getPrintStream("output", caseName);) {
			long cost = end.getTime() - start.getTime();
			out.println("Method: " + requestType );
			out.println("status: " + statusCode);
			out.println("Url: " + url);
			out.println("cost: "+cost+"ms");
			out.println("start: " + start.toString()); 
			out.println("end: " + end.toString());

			out.println(responseBody);
		}

		return responseBody;
	}

	public static String getResponseByRequest(HttpClient client, HttpPost request, String jsonData,
			int statusCodeExpected) throws HttpException, IOException {
		
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");

		// set body
		if (jsonData != null) {
			request.addHeader("content-type", "application/json");
			StringEntity params = new StringEntity(jsonData, "UTF-8");
			params.setContentType("application/json");
			request.setEntity(params);
			saveInput(request, jsonData);
		}

		Date start = new Date();
		HttpResponse response = client.execute(request);
		Date end = new Date();

		int statusCode = response.getStatusLine().getStatusCode();

		String responseBody = saveOutput(request, response, start, end);

		assertEquals(statusCodeExpected, statusCode);

		return responseBody;
	}
	
	public static String getResponseByRequest(HttpClient client, HttpDelete request, int statusCodeExpected)
			throws IOException, HttpException {

		Date start = new Date();
		HttpResponse response = client.execute(request);
		Date end = new Date();

		int statusCode = response.getStatusLine().getStatusCode();

		String responseBody = saveOutput(request, response, start, end);

		assertEquals(statusCodeExpected, statusCode);

		return responseBody;
	}
	

	public static String readJSONFile(String fileName) throws FileNotFoundException, URISyntaxException {

		File targetFile = new File("src\\test\\resources\\" + fileName);
		System.out.println("Json File: " + targetFile.getAbsolutePath());

		Scanner scanner = new Scanner(targetFile);
		String content = scanner.useDelimiter("\\Z").next();
		scanner.close();

		return content;
	}
	
	
	
	public static net.minidev.json.JSONObject readJSONFile2Obj(String fileName) throws FileNotFoundException, URISyntaxException, ParseException {

		String	jsonStr = ApiTestUtil.readJSONFile(fileName);
		JSONObject expectedJson = (JSONObject)new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(jsonStr);

		return expectedJson;
	}

	
	private static String getCaseName() {
		
		Pair2<String, String> result = ClassUtil.getMethodByPrefixOnAnnotation(Category.class, "step");
		
		if (result == null) {
			//return "unknownClassAndMethod";
			throw new RuntimeException("method with api call name should start with step; one method should only have one api call!");
		} else {
			String className = result.getL();
			String methodName = result.getR();
			String[] classNameArray = className.split("\\.");
			String classSimpleName = classNameArray[classNameArray.length - 1];

			String caseName = classSimpleName + "_" + methodName;
			return caseName;
		}

	}

	private static void saveInput(HttpRequestBase request, String content) throws FileNotFoundException {
		String caseName = getCaseName();
		
		if (content != null && content.length() > 20) {
			String newFormat = ApiUtil.getFormatedJsonOrNull(content);
			if(newFormat != null){
				content = newFormat; 
			}			
		}
		String url="unknown";
		try {
			url = request.getURI().toURL().toString();
		} catch (MalformedURLException e) {
			throw new RuntimeException("can't get URL", e);
		}
		save(url, request, content, caseName, "input");
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

		String fileName = caseName + "_" + getCurrentOnISO() + ".txt";

		PrintStream out = new PrintStream(new FileOutputStream(dir + fileName));
		return out;

	}


	private static void save(String Url, HttpRequestBase request, String JsonFile, String caseName, String type)
			throws FileNotFoundException {

		String requestType = request.getMethod();

		try (PrintStream out = getPrintStream(type, caseName);) {
			out.println("Method: " + requestType);
			out.println("Url: " + Url);
			out.print(JsonFile);
		}
	}
	
	// by pass invalid ssl certificate  if needed, do nothing if it is non https request
	public static HttpClientBuilder createTrustAllHttpClientBuilder( ) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		  SSLContextBuilder builder = new SSLContextBuilder();
		  builder.loadTrustMaterial(null, (chain, authType) -> true);           
		  SSLConnectionSocketFactory sslsf = new 
		  SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
		  return HttpClients.custom().setSSLSocketFactory(sslsf);
	}	
	
	/** go through every fields in recursive way to verify JSON structure
	 * expectedJson can has less fields than actual Json*/
	// For non 3rd library implementation, please see https://stackoverflow.com/questions/50967015/how-to-compare-json-documents-and-return-the-differences-with-jackson-or-gson
	public static void verifyJson(Map<String, Object> actualJson, Map<String, Object> expectedJson) throws JsonProcessingException, IOException {
		
		for(String key:  expectedJson.keySet()) {
			Object expectValue = expectedJson.get(key);
		    Object actualValue = JsonPath.read(actualJson, "$."+key);
		    if(expectValue instanceof Map) {		    		
		    	@SuppressWarnings("unchecked")
				Map<String,Object> a = (Map<String, Object>)actualValue;
		    	@SuppressWarnings("unchecked")
		    	Map<String,Object> e = (Map<String,Object>)expectValue;		    	
		    	verifyJson(a,e);
		    }else if(expectValue instanceof JSONArray) {
		    	if(!(actualValue instanceof JSONArray)) {
		    		fail("different type on " + key);
		    	}
		    	List<?>  e = (ArrayList<?>)expectValue;
		    	List<?> a = (ArrayList<?>)actualValue;

		    	if(a.size() != e.size()) {
		    		fail("different size on " + key);
		    	}
		    	//no need to sort before compare because JSON specs said: 
		    	//  An array is an ordered sequence of zero or more values
		    	
		    	for(int i=0;i<a.size();i++) {
		    		Map<String, Object> aa = (Map<String, Object>)a.get(i);
		    		Map<String, Object> ee = (Map<String, Object>)e.get(i);
		    		verifyJson(aa,ee);
		    	}		    	
		    }else {
		    	assertEquals("verify property " + key, expectValue, actualValue);
		    }		    
		}
	}
	
	/** will ignore the order of fields and space to compare two JSON string
	 * the difference from verifyJson: 
	 * (1) can't tell which field failed, 
	 * (2) can ignore the order of an json array which verifyJson will fail*/
	public static boolean jsonEquals(String actualJson , String expectedJson ) throws JsonProcessingException, IOException  {
		
		Objects.requireNonNull(actualJson, "actualJson can't be null");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);		

		JsonNode tree1 = mapper.readTree(actualJson);
		JsonNode tree2 = mapper.readTree(expectedJson);

		return tree1.equals(tree2);
		
	}
	
	public static boolean jsonEquals(Map<String, Object> actualJson , JSONObject expectedJson ) throws JsonProcessingException, IOException  {
		
		Objects.requireNonNull(actualJson, "actualJson can't be null");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		
		String json1  = mapper.writeValueAsString(actualJson);		

		JsonNode tree1 = mapper.readTree(json1);
		JsonNode tree2 = mapper.readTree(expectedJson.toJSONString());

		return tree1.equals(tree2);
		
	}

}

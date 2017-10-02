package wu.justin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.junit.experimental.categories.Category;

import wu.justin.rest2.ApiUtil;


public final class ApiTestUtil {
	
	public static final String ISO_LONG_DATE_FORMAT = "HHmmss";
	

	private ApiTestUtil() {
	}

	public static String getCurrentOnISO() {

		DateFormat df = new SimpleDateFormat(ISO_LONG_DATE_FORMAT);

		return df.format(new java.util.Date());
	}


	protected static String getReturn(HttpResponse response) throws HttpException, IOException {
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

	public static String getResponseBodyByGetRequest(HttpClient client, HttpGet request, Integer... statusCodeExpected)
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

	public static String getResponseBodyByPutRequest(HttpClient client, HttpPut request, String data,
			int statusCodeExpected) throws HttpException, IOException {
		request.addHeader("content-type", "application/json");
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");

		// set body
		if (data != null) {
			StringEntity params = new StringEntity(data, "UTF-8");
			params.setContentType("application/json");
			request.setEntity(params);
		}

		Date start = new Date();
		HttpResponse response = client.execute(request);
		Date end = new Date();

		int statusCode = response.getStatusLine().getStatusCode();

		String responseBody = saveOutput(request, response, start, end);

		assertEquals(statusCodeExpected, statusCode);

		return responseBody;
	}

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

	public static String getResponseByPostRequest(HttpClient client, HttpPost request, String data,
			int statusCodeExpected) throws HttpException, IOException {
		request.addHeader("content-type", "application/json");
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");

		// set body
		if (data != null) {
			StringEntity params = new StringEntity(data, "UTF-8");
			params.setContentType("application/json");
			request.setEntity(params);
		}

		Date start = new Date();
		HttpResponse response = client.execute(request);
		Date end = new Date();

		int statusCode = response.getStatusLine().getStatusCode();

		String responseBody = saveOutput(request, response, start, end);

		assertEquals(statusCodeExpected, statusCode);

		return responseBody;
	}


	public static String getResponseBodyByDeleteRequest(HttpClient client, HttpDelete request, int statusCodeExpected)
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

		// System.out.println("Content :" + content);

		return content;
	}

	public static String getCaseName() {
		
		Pair2<String, String> result = ClassUtil.getTopMethodOnAnnotation(Category.class);
		
		if (result == null) {
			return "unknownClassAndMethod";
		} else {
			String className = result.getL();
			String methodName = result.getR();
			String[] classNameArray = className.split("\\.");
			String classSimpleName = classNameArray[classNameArray.length - 1];

			String caseName = classSimpleName + "_" + methodName;
			return caseName;
		}

	}

	public static void saveInput(String Url, HttpRequestBase request, String content) throws FileNotFoundException {
		String caseName = getCaseName();
		save(Url, request, content, caseName, "input");
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


	protected static void save(String Url, HttpRequestBase request, String JsonFile, String caseName, String type)
			throws FileNotFoundException {

		String requestType = request.getMethod();

		try (PrintStream out = getPrintStream(type, caseName);) {
			out.println("Method: " + requestType);
			out.println("Url: " + Url);
			out.print(JsonFile);
		}
	}
}

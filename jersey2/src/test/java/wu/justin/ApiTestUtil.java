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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import wu.justin.bean.Pair2;
import wu.justin.doclet.DTO2JsonListCreator;
import wu.justin.rest2.ApiUtil;

public final class ApiTestUtil {

	private static final Logger LOG = Logger.getLogger(ApiTestUtil.class.getSimpleName());

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

		final String URL_ROOT = "http://localhost:" + port + "/vcaps3";
		// final String URL_ROOT =
		// "https://waterloo-qa.validusholdings.com:8090/vcaps3";

		return URL_ROOT;
	}

	public static String getResponseByRequest(HttpClient client, HttpGet request, Integer... statusCodeExpected)
			throws HttpException, IOException {

		request.setHeader("Authorization", "Bearer " + TestLoginService.ACCESS_TOKEN);

		Date start = new Date();
		HttpResponse response = client.execute(request);
		Date end = new Date();
		int statusCode = response.getStatusLine().getStatusCode();

		String responseBody = saveOutput(request, response, start, end);

		List<Integer> statusList = Arrays.asList(statusCodeExpected);

		assertTrue(statusList.contains(statusCode));
		return responseBody;
	}

	public static MyHttpResponse getResponseByRequest2(HttpClient client, HttpGet request,
			Integer... statusCodeExpected) throws HttpException, IOException {

		request.setHeader("Authorization", "Bearer " + TestLoginService.ACCESS_TOKEN);

		Date start = new Date();
		HttpResponse response = client.execute(request);
		Date end = new Date();
		int statusCode = response.getStatusLine().getStatusCode();

		String responseBody = saveOutput(request, response, start, end);

		List<Integer> statusList = Arrays.asList(statusCodeExpected);

		assertTrue(statusList.contains(statusCode));

		MyHttpResponse result = new MyHttpResponse();
		result.setStatus(statusCode);
		result.setBody(responseBody);
		return result;
	}

	public static String getResponseByRequest(HttpClient client, HttpPut request, String data, int statusCodeExpected)
			throws HttpException, IOException {

		request.setHeader("Authorization", "Bearer " + TestLoginService.ACCESS_TOKEN);

		request.addHeader("content-type", "application/json");
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");

		// set body
		if (data != null) {
			StringEntity params = new StringEntity(data, "UTF-8");
			params.setContentType("application/json");
			request.setEntity(params);
			saveInput(request, data);
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

		LOG.info("    ==>> saving response for URL: " + url);

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
			out.println("Method: " + requestType);
			out.println("status: " + statusCode);
			out.println("Url: " + url);
			out.println("cost: " + cost + "ms");
			out.println("start: " + start.toString());
			out.println("end: " + end.toString());

			out.println(responseBody);
		}

		return responseBody;
	}

	public static String getResponseByRequest(HttpClient client, HttpPost request, String data, int statusCodeExpected)
			throws HttpException, IOException {

		request.setHeader("Authorization", "Bearer " + TestLoginService.ACCESS_TOKEN);

		request.addHeader("content-type", "application/json");
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");

		// set body
		if (data != null) {
			StringEntity params = new StringEntity(data, "UTF-8");
			params.setContentType("application/json");
			request.setEntity(params);
			saveInput(request, data);
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

		request.setHeader("Authorization", "Bearer " + TestLoginService.ACCESS_TOKEN);

		Date start = new Date();
		HttpResponse response = client.execute(request);
		Date end = new Date();

		int statusCode = response.getStatusLine().getStatusCode();

		String responseBody = saveOutput(request, response, start, end);

		assertEquals(statusCodeExpected, statusCode);

		return responseBody;
	}

	/** Sometimes you can replace it with readJSONFile2Obj */
	public static String readJSONFile(String fileName) throws FileNotFoundException, URISyntaxException {

		File targetFile = new File("src/test/resources/" + fileName);
		LOG.info("Json File: " + targetFile.getAbsolutePath());

		Scanner scanner = new Scanner(targetFile);
		String content = scanner.useDelimiter("\\Z").next();
		scanner.close();

		return content;
	}

	public static JSONObject readJSONFile2Obj(String fileName)
			throws FileNotFoundException, URISyntaxException, ParseException {

		String jsonStr = readJSONFile(fileName);
		return ApiUtil.convertJSONStr2Obj(jsonStr);
	}

	public static JSONArray readJSONFile2Array(String fileName)
			throws FileNotFoundException, URISyntaxException, ParseException {

		String jsonStr = readJSONFile(fileName);
		return convertJSONStr2Array(jsonStr);
	}

	public static JSONArray convertJSONStr2Array(String jsonStr)
			throws FileNotFoundException, URISyntaxException, ParseException {
		JSONArray expectedJson = (JSONArray) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(jsonStr);
		return expectedJson;
	}

	private static String getCaseName() {

		Pair2<String, String> result = ClassUtil.getMethodByPrefixOnAnnotation(Category.class, "step");

		if (result == null) {
			// return "unknownClassAndMethod";
			throw new RuntimeException(
					"   ==>>  Method with API call name should start with 'step'; one method should only have one api call!");
		} else if (result.getL() == null) {
			throw new RuntimeException("   ==>>  Junit case for API must have @Category() ");
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
			if (newFormat != null) {
				content = newFormat;
			}
		}
		String url = "unknown";
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

	/**
	 * verify a java class JSON structure match expectedJsonFile, which is in the
	 * folder src/test/resources/
	 */
	public static <T> void verifyClassJsonStructure(String expectedJsonFile, Class<T> t)
			throws IOException, URISyntaxException, ParseException {

		String jsonData = ApiTestUtil.readJSONFile(expectedJsonFile);

		ObjectMapper mapper = new ObjectMapper();
		T p = mapper.readValue(jsonData, t);

		String targetJson = mapper.writeValueAsString(p);
		JSONObject json = ApiUtil.convertJSONStr2Obj(targetJson);
		JSONObject expectedJson = ApiUtil.convertJSONStr2Obj(jsonData);

		ApiTestUtil.verifyJson(json, expectedJson);

		appendToListFile(expectedJsonFile, t);
	}

	private static <T> void appendToListFile(String expectedJsonFile, Class<T> t) throws IOException {

		String dir = "target/" + DTO2JsonListCreator.INPUT_FOLDER;
		File dirF = new File(dir);
		if (!dirF.exists()) {
			dirF.mkdirs();
		}
		String fileName = dir + DTO2JsonListCreator.INPUT_File;

		File f = new File(fileName);
		if (!f.exists()) {
			f.createNewFile();
			LOG.info("====>>  created DTO2JsonList File: " + f.getAbsolutePath());
		}

		try (PrintStream out = new PrintStream(new FileOutputStream(fileName, true))) {
			out.append(
					t.getName() + DTO2JsonListCreator.JSON_LIST_SEPARATOR + expectedJsonFile + System.lineSeparator());
		}
	}

	/**
	 * @deprecated, please use verifyJson2 or verifyClassJsonStructure
	 */
	@Deprecated
	// For non 3rd library implementation, please see
	// https://stackoverflow.com/questions/50967015/how-to-compare-json-documents-and-return-the-differences-with-jackson-or-gson
	public static void verifyJson(Map<String, Object> actualJson, Map<String, Object> expectedJson)
			throws JsonProcessingException, IOException {

		for (String key : expectedJson.keySet()) {
			Object expectValue = expectedJson.get(key);
			Object actualValue = JsonPath.read(actualJson, "$.['" + key + "']");
			if (expectValue instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> a = (Map<String, Object>) actualValue;
				@SuppressWarnings("unchecked")
				Map<String, Object> e = (Map<String, Object>) expectValue;
				verifyJson(a, e);
			} else if (expectValue instanceof JSONArray) {
				if (!(actualValue instanceof JSONArray)) {
					fail("different type on " + key);
				}
				List<?> e = (ArrayList<?>) expectValue;
				List<?> a = (ArrayList<?>) actualValue;

				if (a.size() != e.size()) {
					fail("different size on " + key);
				}
				// no need to sort before compare because JSON specs said:
				// An array is an ordered sequence of zero or more values

				for (int i = 0; i < a.size(); i++) {
					Object aa = a.get(i);
					Object ee = e.get(i);
					if (aa == null) {
						if (ee != null) {
							fail("expected value should be null on " + i + " of " + key);
						}
					} else {
						if (ee == null) {
							fail("expected value should not be null on " + i + " of " + key);
						} else {
							// both are not null
							if (aa instanceof Map) {
								if (ee instanceof Map) {
									verifyJson((Map<String, Object>) aa, (Map<String, Object>) ee);
								} else {
									fail("expected value should be a Json Object on " + i + " of " + key);
								}
							} else if (aa instanceof Double) {
								if (ee instanceof Double) {
									if (!aa.equals(ee)) {
										fail("expected value not equals on " + i + " of " + key);
									}
								} else {
									fail("expected value should be Double on " + i + " of " + key);
								}

							} else {
								LOG.severe("    == notImplemented on " + aa.getClass().getName() + "on " + i + " of "
										+ key);
							}
						}
					}
				}
			} else {
				assertEquals("verify property " + key, expectValue, actualValue);
			}
		}
	}

	/**
	 * verify if actualJsonObj matched expectedJsonFile, which is in the folder
	 * src/test/resources/ expectedJson can has less fields than actual Json for
	 * backward compatibility
	 */
	public static void verifyJson2(String expectedJsonFile, Object actualJsonObj)
			throws JsonProcessingException, IOException, URISyntaxException, ParseException {
		Map<String, Object> actualJson = (Map<String, Object>) actualJsonObj;
		net.minidev.json.JSONObject expectedJson = ApiTestUtil.readJSONFile2Obj(expectedJsonFile);
		ApiTestUtil.verifyJson(actualJson, expectedJson);
	}

	/**
	 * @deprecated, please use verifyJson will ignore the order of fields and space
	 * to compare two JSON string the difference from verifyJson: (1) can't tell
	 * which field failed, (2) can ignore the order of an json array which
	 * verifyJson will fail
	 */
	@Deprecated
	public static boolean jsonEquals(String actualJson, String expectedJson)
			throws JsonProcessingException, IOException {

		Objects.requireNonNull(actualJson, "actualJson can't be null");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

		JsonNode tree1 = mapper.readTree(actualJson);
		JsonNode tree2 = mapper.readTree(expectedJson);

		return tree1.equals(tree2);
	}

	/**
	 * @deprecated, please use verifyJson
	 */
	@Deprecated
	public static boolean jsonEquals(Map<String, Object> actualJson, JSONObject expectedJson)
			throws JsonProcessingException, IOException {

		Objects.requireNonNull(actualJson, "actualJson can't be null");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

		String json1 = mapper.writeValueAsString(actualJson);

		JsonNode tree1 = mapper.readTree(json1);
		JsonNode tree2 = mapper.readTree(expectedJson.toJSONString());

		return tree1.equals(tree2);
	}
}

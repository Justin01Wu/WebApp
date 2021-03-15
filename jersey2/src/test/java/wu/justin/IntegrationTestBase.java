package wu.justin;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;

import com.jayway.jsonpath.Configuration;

import net.minidev.json.JSONArray;

public class IntegrationTestBase {

	protected CookieStore httpCookieStore;
	protected static String urlRoot = ApiTestUtil.getUrlRoot();
	
	protected HttpClient client;
	protected Object jsonDoc;
	protected String httpBody;
	
	protected Integer value, expectedValue;
	protected String sValue;
	protected Boolean bValue , expectedBValue;
	protected Double dValue, expectedDValue;
	protected JSONArray array;

	@Before
	public void before() throws Exception {
		httpCookieStore = new BasicCookieStore();
		
		UserInfo userInfo = new UserInfo();
		TestLoginService.loginAsUser(httpCookieStore, userInfo);
		HttpClientBuilder builder = ApiTestUtil.createTrustAllHttpClientBuilder();
		client = builder.setDefaultCookieStore(httpCookieStore).build();
	}

	@After
	public void after() throws Exception {
		TestLoginService.logout(urlRoot, httpCookieStore);
	}
	
	protected String getUniqueName(){
		String newProgramName = this.getClass().getSimpleName() + "_" + new Date().toString();
		return newProgramName;
	}
	
	public Object getJsonByRequest(HttpClient client, HttpGet request, Integer statusCodeExpected) throws HttpException, IOException {
		String responseStr = ApiTestUtil.getResponseByRequest(client, request, statusCodeExpected);
		Object json = Configuration.defaultConfiguration().jsonProvider().parse(responseStr);
		return json;
	}

	
	public Object getJsonByRequest(HttpClient client, HttpPost request, String body, Integer statusCodeExpected) throws HttpException, IOException {

		String responseStr = ApiTestUtil.getResponseByRequest(client, request, body, HttpStatus.SC_OK);
		Object json = Configuration.defaultConfiguration().jsonProvider().parse(responseStr);
		return json;
	}

	public Object getJsonByRequest(HttpClient client, HttpPut request, String body, Integer statusCodeExpected) throws HttpException, IOException {

		String responseStr = ApiTestUtil.getResponseByRequest(client, request, body, HttpStatus.SC_OK);
		Object json = Configuration.defaultConfiguration().jsonProvider().parse(responseStr);
		return json;
	}
	
	public Object getJsonByRequest(HttpClient client, HttpDelete request, Integer statusCodeExpected) throws HttpException, IOException {

		String responseStr = ApiTestUtil.getResponseByRequest(client, request, HttpStatus.SC_OK);
		Object json = Configuration.defaultConfiguration().jsonProvider().parse(responseStr);
		return json;
	}

	

	

}


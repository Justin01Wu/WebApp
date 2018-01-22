package wu.justin;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.jayway.jsonpath.Configuration;

public class IntegrationTestBase2 {

	protected static CookieStore httpCookieStore;
	protected static String urlRoot = ApiTestUtil.getUrlRoot();
	
	protected static HttpClient client;
	protected static Object jsonDoc;
	protected static String httpBody;
	
	protected static Integer value, expectedValue;
	protected static String sValue;
	protected static Boolean bValue , expectedBValue;
	protected static Double dValue, expectedDValue;

	@BeforeClass
	public static void beforeClass() throws Exception {
		httpCookieStore = new BasicCookieStore();
		
		UserInfo userInfo = new UserInfo();
		LoginService.loginAsUser(httpCookieStore, userInfo);
		client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		LoginService.logout(urlRoot, httpCookieStore);
	}
	
	protected String getUniqueName(){
		String newProgramName = this.getClass().getSimpleName() + "_" + new Date().toString();
		return newProgramName;
	}
	
	public Object getJsonByGetRequest(HttpClient client, HttpGet request, Integer statusCodeExpected) throws HttpException, IOException {
		String responseStr = ApiTestUtil.getResponseByRequest(client, request, statusCodeExpected);
		Object json = Configuration.defaultConfiguration().jsonProvider().parse(responseStr);
		return json;
	}

	

}


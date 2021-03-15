package wu.justin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;


@Category(IntegrationTest.class)
public class TestUserApi extends IntegrationTestBase {
	
	private CookieStore httpCookieStore;
	public static String URL_ROOT = ApiTestUtil.getUrlRoot();
	
	
	
	@Before
	public void setup() throws HttpException, IOException  {
		httpCookieStore = new BasicCookieStore();
		UserInfo userInfo = new UserInfo();
		TestLoginService.loginAsUser(httpCookieStore, userInfo);

	}	

	@Test
	public void testAllUserApi() throws HttpException, IOException, URISyntaxException, ParseException{
		
		// every method only call one API
		// everyAPI call method must start with "step"
		stepGetCurrentUserApi(); 
		stepGetUserByIdApi();
		stepGetUserByNegativeIdApi();
		stepGetUserByIdApi2();
		
	}
	
	@Test
	public void step5_UpdateCurrentUserTestFlag() throws HttpException, IOException{

		String url = URL_ROOT + "/api/users/user/current.json?testFlag=true";
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		HttpPost request = new HttpPost(url);
		jsonDoc = this.getJsonByRequest(client, request, null, HttpStatus.SC_OK);		
		String msg = JsonPath.read(jsonDoc, "$.msg");
		String expetcedResponse = "it is test request, so do nothing";
		assertEquals( expetcedResponse, msg);

	}
	
	@Test
	public void step5_UpdateCurrentUserSucceed() throws HttpException, IOException, URISyntaxException{

		
		String origVriscJsonFile2 = TestUserApi.class.getSimpleName() + "_rita.json";
		
		String payLoad = ApiTestUtil.readJSONFile(origVriscJsonFile2);

		String url = URL_ROOT + "/api/users/user/current.json";
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		HttpPost request = new HttpPost(url);
		jsonDoc = this.getJsonByRequest(client, request, payLoad, HttpStatus.SC_OK);		
		String msg = JsonPath.read(jsonDoc, "$.msg");
		String expetcedResponse = "succeed";
		assertEquals( expetcedResponse, msg);

	}
	
	// test JsonMappingExceptionMapper
	@Test
	public void step6_UpdateCurrentUserFailOnWrongField() throws HttpException, IOException{
		
		String url = URL_ROOT + "/api/users/user/current.json";
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		HttpPost request = new HttpPost(url);
		httpBody = ApiTestUtil.getResponseByRequest(client, request, "{\"wrong\":\"format\"}", HttpStatus.SC_BAD_REQUEST);	
		
		assertTrue(httpBody.contains("Unrecognized field \"wrong\""));

	}
	
	// test JsonParseExceptionMapper
	@Test
	public void step6_UpdateCurrentUserFailOnWrongFormat() throws HttpException, IOException{
		
		String url = URL_ROOT + "/api/users/user/current.json";
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		HttpPost request = new HttpPost(url);
		httpBody = ApiTestUtil.getResponseByRequest(client, request, "incorrect json data", HttpStatus.SC_BAD_REQUEST);	
		
		assertTrue(httpBody.contains("Unrecognized token 'incorrect'"));

	}
	
	@Test
	public void step6_UpdateCurrentUserFail() throws HttpException, IOException{
		
		String url = URL_ROOT + "/api/users/user/current.json";
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		HttpPost request = new HttpPost(url);
		httpBody = ApiTestUtil.getResponseByRequest(client, request, "", HttpStatus.SC_FORBIDDEN);		

	}
	
	private void stepGetCurrentUserApi() throws HttpException, IOException, URISyntaxException, ParseException{
		// every method only call one API 
		
		System.out.println("                ==>testUserApi started....");
		
		String url = URL_ROOT +"/api/users/user/current.json";

		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		HttpGet request = new HttpGet(url);		 
		jsonDoc = this.getJsonByRequest(client, request, HttpStatus.SC_OK);
		
		Integer userId = JsonPath.read(jsonDoc, "$.id");		
		assertEquals(userId, Integer.valueOf(56239));
		
		String file = TestUserApi.class.getSimpleName() + "_justin.json";
		JSONObject expectedJson = ApiTestUtil.readJSONFile2Obj(file);
		
		ApiTestUtil.verifyJson((Map<String, Object>)jsonDoc, (Map<String, Object>)expectedJson);
		
	}
	
	
	private void stepGetUserByIdApi() throws HttpException, IOException{
		
		System.out.println("                ==>testUserApi started....");
		
		String url = URL_ROOT +"/api/users/user/56239";
		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		HttpGet request = new HttpGet(url);
		 
		jsonDoc = this.getJsonByRequest(client, request, HttpStatus.SC_OK);				
		
		String userName = JsonPath.read(jsonDoc, "$.name");		
		assertEquals(userName, "Justin Wu");
		System.out.println("userName= " + userName);
		
		Integer userId = JsonPath.read(jsonDoc, "$.id");		
		assertEquals(userId, Integer.valueOf(56239));		
		
	}
	
	private void stepGetUserByIdApi2() throws HttpException, IOException{
		
		System.out.println("                ==>testUserApi started....");
		
		String url = URL_ROOT +"/api/users/user2/56239";
		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		HttpGet request = new HttpGet(url);		 
		
		jsonDoc = this.getJsonByRequest(client, request, HttpStatus.SC_OK);		
		
		String userName = JsonPath.read(jsonDoc, "$.name");		
		assertEquals(userName, "Justin Wu");
		System.out.println("userName= " + userName);
		
		Integer userId = JsonPath.read(jsonDoc, "$.id");		
		assertEquals(userId, Integer.valueOf(56239));		
		
	}
	
	
	private void stepGetUserByNegativeIdApi() throws HttpException, IOException{
		
		System.out.println("                ==>testUserApi started....");
		
		String url = URL_ROOT +"/api/users/user/-12";
		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		final HttpGet request = new HttpGet(url);
		 
		httpBody = ApiTestUtil.getResponseByRequest(client, request, HttpStatus.SC_BAD_REQUEST);
		
		assertEquals(httpBody, "userId can't be negative");		
	}	

	
	@After
	public void tearDown() throws Exception {		
		// logout
		TestLoginService.logout(URL_ROOT, httpCookieStore);
		

	}

}

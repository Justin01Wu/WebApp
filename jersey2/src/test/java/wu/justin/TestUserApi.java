package wu.justin;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

@Category(IntegrationTest.class)
public class TestUserApi {
	
	
	private CookieStore httpCookieStore;
	public static String URL_ROOT = ApiTestUtil.getUrlRoot();
	
	private Object document;
	private String responseBody;
	
	@Before
	public void setup() throws HttpException, IOException  {
		httpCookieStore = new BasicCookieStore();
		UserInfo userInfo = new UserInfo();
		LoginService.loginAsUser(httpCookieStore, userInfo);

	}	
	
	@Test
	public void testGetCurrentUserApi() throws HttpException, IOException{
		
		System.out.println("                ==>testUserApi started....");
		
		String url = URL_ROOT +"/api/users/user/current.json";

		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		final HttpGet request = new HttpGet(url);
		 
		responseBody = ApiTestUtil.getResponseByRequest(client, request, HttpStatus.SC_OK);
		document = Configuration.defaultConfiguration().jsonProvider().parse(responseBody);		
		
		
		String userName = JsonPath.read(document, "$.name");		
		assertEquals(userName, "Justin Wu");
		System.out.println("userName= " + userName);
		
		Integer userId = JsonPath.read(document, "$.id");		
		assertEquals(userId, new Integer(56239));

		
		
	}
	
	@Test
	public void testGetUserByIdApi() throws HttpException, IOException{
		
		System.out.println("                ==>testUserApi started....");
		
		String url = URL_ROOT +"/api/users/user/12";

		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		final HttpGet request = new HttpGet(url);
		 
		responseBody = ApiTestUtil.getResponseByRequest(client, request, HttpStatus.SC_OK);
		document = Configuration.defaultConfiguration().jsonProvider().parse(responseBody);		
		
		
		String userName = JsonPath.read(document, "$.name");		
		assertEquals(userName, "Justin Wu");
		System.out.println("userName= " + userName);
		
		Integer userId = JsonPath.read(document, "$.id");		
		assertEquals(userId, new Integer(56239));

		
		
	}
	
	@Test
	public void testGetUserByNegativeIdApi() throws HttpException, IOException{
		
		System.out.println("                ==>testUserApi started....");
		
		String url = URL_ROOT +"/api/users/user/-12";

		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		final HttpGet request = new HttpGet(url);
		 
		responseBody = ApiTestUtil.getResponseByRequest(client, request, HttpStatus.SC_BAD_REQUEST);
		
		assertEquals(responseBody, "userId can't be negative");

		
		
	}
	
	// it comes from 
	// https://github.com/jayway/JsonPath
	@Test
	public void testJson(){		
		
		String json ="{store: "
				+ "{    book: ["
				+ "        {            "
				+ "category: 'reference',            "
				+ "author: 'Nigel Rees',            "
				+ "title: 'Sayings of the Century',"
				+ "            price: 8.95"
				+ "        },"
				+ "        {"
				+ "            category: 'fiction',"
				+ "            author: 'J. R. R. Tolkien',"
				+ "            title: 'The Lord of the Rings',"
				+ "            isbn: '0-395-19395-8',"
				+ "            price: 22.99"
				+ "        }    ],"
				+ "    bicycle: {"
				+ "        color: 'red',"
				+ "        price: 19.95"
				+ "    }"
				+ "},"
				+ " expensive: 10"
				+ "}";
		
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		
		String author0 = JsonPath.read(document, "$.store.book[0].author");
		List<String> category = JsonPath.read(document, "store.book[?(@.author=='Nigel Rees')].category");

		System.out.println("author0= " + author0);
		System.out.println("category= " + category);
		
		assertEquals(author0, "Nigel Rees");

		assertEquals(category.size(), 1);
		assertEquals(category.get(0), "reference");

	}
	
	@After
	public void tearDown() throws Exception {
		
		// logout
		LoginService.logout(URL_ROOT, httpCookieStore);
		

	}

}

package wu.justin;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jayway.jsonpath.JsonPath;

@Category(IntegrationTest.class)
public class TestPublicApi  extends IntegrationTestBase2{

	
	@Test
	public void stepTestPingApi() throws HttpException, IOException{
		
		// every method only call one API
		// everyAPI call method must start with "step"

		String url = urlRoot +"/api/public/ping";

		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		final HttpGet request = new HttpGet(url);
		 
		String responseBody = ApiTestUtil.getResponseByRequest(client, request, HttpStatus.SC_OK);
		
		assertEquals(responseBody, "{}");

		
	}
	
	@Test
	public void stepTestTimeApi() throws HttpException, IOException{
		
		String url = urlRoot +"/api/public/time";

		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		final HttpGet request = new HttpGet(url);
		 
		Object responseJson = getJsonByGetRequest(client, request, HttpStatus.SC_OK);
		
		long time = JsonPath.read(responseJson, "$.time");		
		assertTrue(time>100000000);
		
	}
	
	@Test
	public void stepDateFormatApi() throws HttpException, IOException{
		
		String url = urlRoot +"/api/public/dateFormat.json";		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		final HttpGet request = new HttpGet(url);
		 
		Object responseJson = getJsonByGetRequest(client, request, HttpStatus.SC_OK);
		
		long utilDate = JsonPath.read(responseJson, "$.utilDate");		
		assertTrue(utilDate>10000000);

		String utilDateOnCustomized = JsonPath.read(responseJson, "$.utilDateOnCustomized");		
		assertTrue(utilDateOnCustomized.contains("T"));

		
		
	}

	
	


}

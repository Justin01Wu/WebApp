package wu.justin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

@Category(IntegrationTest.class)
public class TestCreateAccessToken {

	@Test
	public void testGetAccessTokenFlow() throws HttpException, IOException{
		System.out.println("     === >>> start testGetAccessTokenFlow");
		String accessToken = stepTestGetAccessToken();
		stepTestPingApi(accessToken);
		stepTestPingApiFailure();
	}
	
	private String stepTestGetAccessToken() throws HttpException, IOException{
		//access 
		
		String url = ApiTestUtil.getUrlRoot() + "/CreateAccessToken";
		HttpPost request = new HttpPost(url);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tokenId", "1"));
		params.add(new BasicNameValuePair("tokenPassword", "justin719"));
		request.setEntity(new UrlEncodedFormEntity(params));		
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		//HttpResponse response = client.execute(request);
		//int statusCode = response.getStatusLine().getStatusCode();
		//assertEquals(statusCode, HttpStatus.SC_OK);
		
		//String body = ApiTestUtil.getReturn(response);
		String body = ApiTestUtil.getResponseByRequest(client, request, null, HttpStatus.SC_OK);
		
		Object json = Configuration.defaultConfiguration().jsonProvider().parse(body);
		
		String accessToken = JsonPath.read(json, "$.access_token");		
		assertTrue(accessToken != null);
		assertTrue(!accessToken.isEmpty());

		String expiredDate = JsonPath.read(json, "$.expired");		
		Long expired = Long.valueOf(expiredDate);
		assertTrue(expired > 1538060613243l); // great than case created time
		
		System.out.println("accessToken ="+  accessToken);
		
		client.close();
		
		return accessToken;

	}
	
	
	private void stepTestPingApi(String accessToken) throws HttpException, IOException{
		
		// every method only call one API
		// everyAPI call method must start with "step"

		String url = ApiTestUtil.getUrlRoot() + "/tokenApi/public/ping";
	
		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Bearer " + accessToken);
		CloseableHttpClient client = HttpClients.createDefault();
		
		String responseBody = ApiTestUtil.getResponseByRequest(client, request, HttpStatus.SC_OK);
		
		assertEquals(responseBody, "{}");

		
	}

	private void stepTestPingApiFailure() throws HttpException, IOException{
		
		// every method only call one API
		// everyAPI call method must start with "step"

		String url = ApiTestUtil.getUrlRoot() + "/tokenApi/public/ping";
	
		HttpGet request = new HttpGet(url);
		CloseableHttpClient client = HttpClients.createDefault();
		
		ApiTestUtil.getResponseByRequest(client, request, HttpStatus.SC_UNAUTHORIZED);		
		

		
	}

	
}

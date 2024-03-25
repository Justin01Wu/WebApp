package wu.justin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jayway.jsonpath.JsonPath;

@Category(IntegrationTest.class)
public class TestPublicApi  extends IntegrationTestBase{

	
	@Test
	public void stepTestPingApi() throws HttpException, IOException{
		
		// every method only call one API
		// everyAPI call method must start with "step"

		String url = urlRoot +"/api/public/ping";		
		HttpGet request = new HttpGet(url);
		 
		String responseBody = ApiTestUtil.getResponseByRequest(client, request, HttpStatus.SC_OK);
		
		assertEquals(responseBody, "{}");

		
	}
	
	@Test
	public void stepTestTimeApi() throws HttpException, IOException{
		
		String url = urlRoot +"/api/public/time";	
		HttpGet request = new HttpGet(url);
		 
		Object responseJson = getJsonByRequest(client, request, HttpStatus.SC_OK);
		
		long time = JsonPath.read(responseJson, "$.time");		
		assertTrue(time>100000000);
		
	}
	
	// this test case will always fail
	@Test
	public void stepTestFailure() throws HttpException, IOException{
		
		String url = urlRoot +"/api/public/wrongAPI";	
		HttpGet request = new HttpGet(url);
		 
		Object responseJson = getJsonByRequest(client, request, HttpStatus.SC_OK);
		
		long time = JsonPath.read(responseJson, "$.time");		
		assertTrue(time>100000000);
		
	}
	
	@Test
	public void stepDateFormatApi() throws HttpException, IOException{
		
		String url = urlRoot +"/api/public/dateFormat.json";		
		
		HttpGet request = new HttpGet(url);
		 
		Object responseJson = getJsonByRequest(client, request, HttpStatus.SC_OK);
		
		long utilDate = JsonPath.read(responseJson, "$.utilDate");		
		assertTrue(utilDate>10000000);

		String utilDateOnCustomized = JsonPath.read(responseJson, "$.utilDateOnCustomized");		
		assertTrue(utilDateOnCustomized.contains("T"));

		
		
	}

	
	@Test
	public void stepTestTimestampApi() throws HttpException, IOException{
		
		String url = urlRoot +"/api/public/timestamp.json";

		String body ="{ \"processEndTime\" : 1434989360380, \"localDate\" : \"2021-04-29\"}";
		
		HttpPost request = new HttpPost(url);
	
		Object responseJson = getJsonByRequest(client, request, body, HttpStatus.SC_OK);
		
		long time = JsonPath.read(responseJson, "$.processEndTime");		
		assertTrue(time == 1434989360380l);
		
	}
	


}

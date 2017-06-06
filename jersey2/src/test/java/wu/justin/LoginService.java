package wu.justin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class LoginService {

	public static String URL_ROOT = "http://localhost:8080/jersey2";

	public static void loginAsUser(CookieStore httpCookieStore, UserInfo userInfo)
			throws HttpException, IOException {

		String url = URL_ROOT + "/index.html" ;
		System.out.println("url = " + url);

		HttpClient client = initClient(httpCookieStore);

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		// will automatically save Http SessionId into httpCookieStore

		if (response.getStatusLine().getStatusCode() != 200) {
			System.out.println("http status = " + response.getStatusLine().getStatusCode() );
			throw new RuntimeException("status should be 200"); 
		};

		String bodyStr = getReturn(response);

		System.out.println("body = " + bodyStr);

	}

	public static void logout(String baseUrl, CookieStore httpCookieStore) throws HttpException, IOException {
		
	}

	public static HttpClient initClient(CookieStore httpCookieStore) {
		HttpClient http = null;
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore);
		http = builder.build();
		return http;
	}

	public static String getReturn(HttpResponse response) throws HttpException, IOException {

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();

	}
}
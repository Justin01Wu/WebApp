package wu.justin.rest2;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

public class NetworkUtil {

	  // by pass invalid ssl certificate  if needed, do nothing if it is non https request
	  public static HttpClientBuilder createTrustAllHttpClientBuilder()
	      throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
	    SSLContextBuilder builder = new SSLContextBuilder();
	    builder.loadTrustMaterial(null, (chain, authType) -> true);
	    SSLConnectionSocketFactory sslsf =
	        new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
	    return HttpClients.custom().setSSLSocketFactory(sslsf);
	  }
}

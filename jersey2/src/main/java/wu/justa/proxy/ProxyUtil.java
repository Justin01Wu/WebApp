package wu.justa.proxy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.BitSet;
import java.util.Formatter;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

public class ProxyUtil {	
	
		
		protected static final BitSet asciiQueryChars;
		  static {
		    char[] c_unreserved = "_-!.~'()*".toCharArray();//plus alphanum
		    char[] c_punct = ",;:$&+=".toCharArray();
		    char[] c_reserved = "?/[]@".toCharArray();//plus punct

		    asciiQueryChars = new BitSet(128);
		    for(char c = 'a'; c <= 'z'; c++) asciiQueryChars.set((int)c);
		    for(char c = 'A'; c <= 'Z'; c++) asciiQueryChars.set((int)c);
		    for(char c = '0'; c <= '9'; c++) asciiQueryChars.set((int)c);
		    for(char c : c_unreserved) asciiQueryChars.set((int)c);
		    for(char c : c_punct) asciiQueryChars.set((int)c);
		    for(char c : c_reserved) asciiQueryChars.set((int)c);

		    asciiQueryChars.set((int)'%');//leave existing percent escapes in place
		  }
		
		
		  /**
		   * Encodes characters in the query or fragment part of the URI.
		   *
		   * <p>Unfortunately, an incoming URI sometimes has characters disallowed by the spec.  HttpClient
		   * insists that the outgoing proxied request has a valid URI because it uses Java's {@link URI}.
		   * To be more forgiving, we must escape the problematic characters.  See the URI class for the
		   * spec.
		   *
		   * @param in example: name=value&amp;foo=bar#fragment
		   */
		  public static CharSequence encodeUriQuery(CharSequence in) {
		    //Note that I can't simply use URI.java to encode because it will escape pre-existing escaped things.
		    StringBuilder outBuf = null;
		    Formatter formatter = null;
		    for(int i = 0; i < in.length(); i++) {
		      char c = in.charAt(i);
		      boolean escape = true;
		      if (c < 128) {
		        if (asciiQueryChars.get((int)c)) {
		          escape = false;
		        }
		      } else if (!Character.isISOControl(c) && !Character.isSpaceChar(c)) {//not-ascii
		        escape = false;
		      }
		      if (!escape) {
		        if (outBuf != null)
		          outBuf.append(c);
		      } else {
		        //escape
		        if (outBuf == null) {
		          outBuf = new StringBuilder(in.length() + 5*3);
		          outBuf.append(in,0,i);
		          formatter = new Formatter(outBuf);
		        }
		        //leading %, 0 padded, width 2, capital hex
		        formatter.format("%%%02X",(int)c);//TODO
		      }
		    }
		    return outBuf != null ? outBuf : in;
		  }
		  
		  private static DefaultHttpClient httpClientTrustingAllSSLCerts(HttpParams hcParams) throws NoSuchAlgorithmException, KeyManagementException {
				//DefaultHttpClient httpclient = new DefaultHttpClient();
				DefaultHttpClient httpclient = new DefaultHttpClient(	new ThreadSafeClientConnManager(), hcParams);

				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, getTrustingManager(), new java.security.SecureRandom());

				SSLSocketFactory socketFactory = new SSLSocketFactory(sc);
				Scheme sch = new Scheme("https", 443, socketFactory);
				httpclient.getConnectionManager().getSchemeRegistry().register(sch);
				return httpclient;
			}
		  
		  public static HttpClient createHttpClient(final RequestConfig requestConfig) {
			  HttpClientBuilder builder = HttpClientBuilder.create();
			  if(requestConfig != null){
				  builder.setDefaultRequestConfig(requestConfig);  
			  }
			  
			  HttpClient httpClient =  builder.build();
			  return httpClient;
		}
		  
		  
		  private static TrustManager[] getTrustingManager() {
				TrustManager[] trustAllCerts = new TrustManager[] { 
					new X509TrustManager() {
						@Override
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
						@Override
						public void checkClientTrusted(X509Certificate[] certs, String authType) {
							// Do nothing
						}
						@Override
						public void checkServerTrusted(X509Certificate[] certs, String authType) {
							// Do nothing
						}
					} 
				};
				return trustAllCerts;
			}
		  
			 /** Copy response body data (the entity) from the proxy to the servlet client. */
		  public static void copyResponseEntity(HttpResponse proxyResponse, HttpServletResponse servletResponse,
		                                    HttpRequest proxyRequest, HttpServletRequest servletRequest)
		          throws IOException {
		    HttpEntity entity = proxyResponse.getEntity();
		    if (entity != null) {
		      OutputStream servletOutputStream = servletResponse.getOutputStream();
		      entity.writeTo(servletOutputStream);
		    }
		  }
		  
		  
		  

	}
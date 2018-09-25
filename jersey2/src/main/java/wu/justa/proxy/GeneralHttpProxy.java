package wu.justa.proxy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.AbortableHttpRequest;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.HeaderGroup;

public abstract class GeneralHttpProxy implements Filter {
	
	private static Logger log = Logger.getLogger(GeneralHttpProxy.class.getName());
	
	//protected boolean doPreserveCookies = false;
	protected boolean doPreserveHost = false;
	  /** User agents shouldn't send the url fragment but what if it does? */
	  protected boolean doSendUrlFragment = true;
	  protected boolean doForwardIP = true;
	
	protected boolean isAuthenticated(HttpServletRequest servletRequest){
		return true;
	}
	
	protected abstract String getValidOriginalUrlStart();
	
	protected String getValidTargetUrlStart(){
		return "apiReal";	
	}
	
	protected boolean postAuthenticationFail(){
		return true; 
		// decide if go to next filter, if true, then go to the next filter 
	}
	
	
	  
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if(hasError()){
			log.severe("GeneralHttpProxy init error, please check settings, so skip it");
			chain.doFilter(request, response);
			return;
		}
		
		if(!request.getScheme().equals("https") && !request.getScheme().equals("http")){
			chain.doFilter(request, response);
			return;
		}
		
		HttpServletRequest httpRequest   =  (HttpServletRequest) request;
		HttpServletResponse httpResponse =  (HttpServletResponse) response;
		
  		String uri = httpRequest.getRequestURI();
  		
  		log.info(uri);
		
		if (isAuthenticated(httpRequest)) {
			doRealFilter( httpRequest, httpResponse,	chain);
		}else{
			// no authorization
			
			log.fine("no authorization, so skip it");
			if(postAuthenticationFail()){
				chain.doFilter(request, response);
			}else{
				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please relogin before do anything");			
			}
			
		}
		
		
	}
	
	protected void doRealFilter(final HttpServletRequest servletRequest,
			final HttpServletResponse servletResponse, final FilterChain chain)
			throws IOException, ServletException {
		
//		if (log.isDebugEnabled()) {
//			log.debug("request: " + servletRequest.getRequestURL().toString());
//		}	
		
		final String servletPath = servletRequest.getServletPath();
		String origUrlStart =  "/" + getValidOriginalUrlStart();
		if (!servletPath.startsWith(origUrlStart) ) {			
			log.warning("GeneralHttpProxy doFilter is called on wrong URI, please check web.xml setting: " + servletPath);
			chain.doFilter(servletRequest, servletResponse);
			return;
		}		
		
		// Make the Request
	    //note: we won't transfer the protocol version because I'm not sure it would truly be compatible
	    String httpMethod = servletRequest.getMethod();

		String proxyRequestUri = rewriteUrlFromRequest(servletRequest);

		if (proxyRequestUri == null) {
			log.fine("can't find  proxyRequestUri, so skip it"  );
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		URI targetUri;		
		try {
			targetUri = new URI(proxyRequestUri);
		} catch (URISyntaxException e) {

			throw new ServletException(e);
		}
		log.fine("targetUrl= " + httpMethod + " " + targetUri);
		
	    HttpRequest proxyRequest;
	    //spec: RFC 2616, sec 4.3: either of these two headers signal that there is a message body.
	    if (servletRequest.getHeader(HttpHeaders.CONTENT_LENGTH) != null ||
	        servletRequest.getHeader(HttpHeaders.TRANSFER_ENCODING) != null) {
	      proxyRequest = newProxyRequestWithEntity(httpMethod, proxyRequestUri, servletRequest);
	    } else {
	      proxyRequest = new BasicHttpRequest(httpMethod, proxyRequestUri);
	    }

	    copyRequestHeaders(servletRequest, proxyRequest);

	    setXForwardedForHeader(servletRequest, proxyRequest);
	    
	    HttpClient proxyClient = ProxyUtil.createHttpClient(null);

		log.fine("proxy " + servletRequest.getMethod() + " uri: " + servletRequest.getRequestURI() + " -- "
					+ proxyRequest.getRequestLine().getUri());
		

		try {

			HttpResponse proxyResponse = proxyClient.execute(getTargetHost(servletRequest), proxyRequest);


		      // Process the response:

		      // Pass the response code. This method with the "reason phrase" is deprecated but it's the
		      //   only way to pass the reason along too.
		      int statusCode = proxyResponse.getStatusLine().getStatusCode();
		      //noinspection deprecation
		      servletResponse.setStatus(statusCode, proxyResponse.getStatusLine().getReasonPhrase());

		      // Copying response headers to make sure SESSIONID or other Cookie which comes from the remote
		      // server will be saved in client when the proxied url was redirected to another one.
		      // See issue [#51](https://github.com/mitre/HTTP-Proxy-Servlet/issues/51)
		      copyResponseHeaders(proxyResponse, servletRequest, servletResponse);

		      if (statusCode == HttpServletResponse.SC_NOT_MODIFIED) {
		        // 304 needs special handling.  See:
		        // http://www.ics.uci.edu/pub/ietf/http/rfc1945.html#Code304
		        // Don't send body entity/content!
		        servletResponse.setIntHeader(HttpHeaders.CONTENT_LENGTH, 0);
		      } else {
		        // Send the content to the client
		        copyResponseEntity(proxyResponse, servletResponse, proxyRequest, servletRequest);
		      }
		    

		} catch (Exception e) {
			
			log.fine(" exception is " + e.getClass().getName());
			// abort request, according to best practice with HttpClient
			if (proxyRequest instanceof AbortableHttpRequest) {
				AbortableHttpRequest abortableHttpRequest = (AbortableHttpRequest) proxyRequest;
				abortableHttpRequest.abort();
			}
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			if (e instanceof ServletException)
				throw (ServletException) e;
			if (e instanceof IOException)
				throw (IOException) e;
			throw new RuntimeException(e);
		}
	}
	
	 /** Copy response body data (the entity) from the proxy to the servlet client. */
	  protected void copyResponseEntity(HttpResponse proxyResponse, HttpServletResponse servletResponse,
	                                    HttpRequest proxyRequest, HttpServletRequest servletRequest)
	          throws IOException {
		  
		  ProxyUtil.copyResponseEntity(proxyResponse, servletResponse, proxyRequest, servletRequest);

	  }

	
	@Override
	public void destroy() {
		  
	}
	
	private static boolean hasError(){
		return false;
	}
	
	protected HttpHost getTargetHost(HttpServletRequest servletRequest) {
		return new HttpHost("localhost", 8080, "http"); 
	}
	
	protected String getTargetUri(HttpServletRequest servletRequest) {
		// convert vcaps3/api/... vcaps3/apiReal/...
		String origUrlStart =  getValidOriginalUrlStart();
		String targetUrlStart =  getValidTargetUrlStart();		
		String targetURI = servletRequest.getRequestURI().replaceFirst(origUrlStart, targetUrlStart);
		return targetURI;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	protected void copyRequestHeaders(HttpServletRequest servletRequest, HttpRequest proxyRequest) {
	    // Get an Enumeration of all of the header names sent by the client
	    @SuppressWarnings("unchecked")
	    Enumeration<String> enumerationOfHeaderNames = servletRequest.getHeaderNames();
	    while (enumerationOfHeaderNames.hasMoreElements()) {
	      String headerName = enumerationOfHeaderNames.nextElement();
	      copyRequestHeader(servletRequest, proxyRequest, headerName);
	    }
	  }
	
	  /**
	   * Copy a request header from the servlet client to the proxy request.
	   * This is easily overridden to filter out certain headers if desired.
	   */
	  protected void copyRequestHeader(HttpServletRequest servletRequest, HttpRequest proxyRequest,
	                                   String headerName) {
	    //Instead the content-length is effectively set via InputStreamEntity
	    if (headerName.equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH))
	      return;
	    if (hopByHopHeaders.containsHeader(headerName))
	      return;

	    @SuppressWarnings("unchecked")
	    Enumeration<String> headers = servletRequest.getHeaders(headerName);
	    while (headers.hasMoreElements()) {//sometimes more than one value
	      String headerValue = headers.nextElement();
	      // In case the proxy host is running multiple virtual servers,
	      // rewrite the Host header to ensure that we get content from
	      // the correct virtual server
	      if (!doPreserveHost && headerName.equalsIgnoreCase(HttpHeaders.HOST)) {
	        HttpHost host = getTargetHost(servletRequest);
	        headerValue = host.getHostName();
	        if (host.getPort() != -1)
	          headerValue += ":"+host.getPort();
	      } //else if (!doPreserveCookies && headerName.equalsIgnoreCase(org.apache.http.cookie.SM.COOKIE)) {
	        //headerValue = getRealCookie(headerValue);
	      //}
	      proxyRequest.addHeader(headerName, headerValue);
	    }
	  }	
	  
	  /** These are the "hop-by-hop" headers that should not be copied.
	   * http://www.w3.org/Protocols/rfc2616/rfc2616-sec13.html
	   * I use an HttpClient HeaderGroup class instead of Set&lt;String&gt; because this
	   * approach does case insensitive lookup faster.
	   */
	  protected static final HeaderGroup hopByHopHeaders;
	  static {
	    hopByHopHeaders = new HeaderGroup();
	    String[] headers = new String[] {
	        "Connection", "Keep-Alive", "Proxy-Authenticate", "Proxy-Authorization",
	        "TE", "Trailers", "Transfer-Encoding", "Upgrade" };
	    for (String header : headers) {
	      hopByHopHeaders.addHeader(new BasicHeader(header, null));
	    }
	  }	  
//	  
//	  /** Take any client cookies that were originally from the proxy and prepare them to send to the
//	   * proxy.  This relies on cookie headers being set correctly according to RFC 6265 Sec 5.4.
//	   * This also blocks any local cookies from being sent to the proxy.
//	   */
//	  protected String getRealCookie(String cookieValue) {
//	    StringBuilder escapedCookie = new StringBuilder();
//	    String cookies[] = cookieValue.split("[;,]");
//	    for (String cookie : cookies) {
//	      String cookieSplit[] = cookie.split("=");
//	      if (cookieSplit.length == 2) {
//	        String cookieName = cookieSplit[0].trim();
//	        if (cookieName.startsWith(getCookieNamePrefix(cookieName))) {
//	          cookieName = cookieName.substring(getCookieNamePrefix(cookieName).length());
//	          if (escapedCookie.length() > 0) {
//	            escapedCookie.append("; ");
//	          }
//	          escapedCookie.append(cookieName).append("=").append(cookieSplit[1].trim());
//	        }
//	      }
//	    }
//	    return escapedCookie.toString();
//	  }	  
	  
	  /** The string prefixing rewritten cookies. */
	  protected String getCookieNamePrefix(String name) {
	    return "!Proxy!" ;
	  }
	  
	  /** Reads the request URI from {@code servletRequest} and rewrites it, considering targetUri.
	   * It's used to make the new request.
	   */
	  protected String rewriteUrlFromRequest(HttpServletRequest servletRequest) {
	    StringBuilder uri = new StringBuilder(500);
	    uri.append(getTargetUri(servletRequest));
	    
	    // Handle the path given to the servlet
//	    if (servletRequest.getPathInfo() != null) {//ex: /my/path.html
//	      uri.append(ProxyUtil.encodeUriQuery(servletRequest.getPathInfo()));
//	    }
	    
	    // Handle the query string & fragment
	    String queryString = servletRequest.getQueryString();//ex:(following '?'): name=value&foo=bar#fragment
	    String fragment = null;
	    
	    //split off fragment from queryString, updating queryString if found
	    if (queryString != null) {
	      int fragIdx = queryString.indexOf('#');
	      if (fragIdx >= 0) {
	        fragment = queryString.substring(fragIdx + 1);
	        queryString = queryString.substring(0,fragIdx);
	      }
	    }

	    queryString = rewriteQueryStringFromRequest(servletRequest, queryString);
	    if (queryString != null && queryString.length() > 0) {
	      uri.append('?');
	      uri.append(ProxyUtil.encodeUriQuery(queryString));
	    }

	    if (doSendUrlFragment && fragment != null) {
	      uri.append('#');
	      uri.append(ProxyUtil.encodeUriQuery(fragment));
	    }
	    return uri.toString();
	  }
	  
	  protected String rewriteQueryStringFromRequest(HttpServletRequest servletRequest, String queryString) {
		    return queryString;
	  }
	  
	protected HttpRequest newProxyRequestWithEntity(String method, String proxyRequestUri,
			HttpServletRequest servletRequest) throws IOException {
		HttpEntityEnclosingRequest eProxyRequest = new BasicHttpEntityEnclosingRequest(method, proxyRequestUri);
		// Add the input entity (streamed)
		// note: we don't bother ensuring we close the servletInputStream since
		// the container handles it
		eProxyRequest
				.setEntity(new InputStreamEntity(servletRequest.getInputStream(), getContentLength(servletRequest)));
		return eProxyRequest;
	}
	
	  // Get the header value as a long in order to more correctly proxy very large requests
	  private long getContentLength(HttpServletRequest request) {
	    String contentLengthHeader = request.getHeader("Content-Length");
	    if (contentLengthHeader != null) {
	      return Long.parseLong(contentLengthHeader);
	    }
	    return -1L;
	  }
	  
	private void setXForwardedForHeader(HttpServletRequest servletRequest, HttpRequest proxyRequest) {
		if (doForwardIP) {
			String forHeaderName = "X-Forwarded-For";
			String forHeader = servletRequest.getRemoteAddr();
			String existingForHeader = servletRequest.getHeader(forHeaderName);
			if (existingForHeader != null) {
				forHeader = existingForHeader + ", " + forHeader;
			}
			proxyRequest.setHeader(forHeaderName, forHeader);

			String protoHeaderName = "X-Forwarded-Proto";
			String protoHeader = servletRequest.getScheme();
			proxyRequest.setHeader(protoHeaderName, protoHeader);
		}
	}
	
	  /** Copy proxied response headers back to the servlet client. */
	  protected void copyResponseHeaders(HttpResponse proxyResponse, HttpServletRequest servletRequest,
	                                     HttpServletResponse servletResponse) {
	    for (Header header : proxyResponse.getAllHeaders()) {
	      copyResponseHeader(servletRequest, servletResponse, header);
	    }
	  }
	  
	  /** Copy a proxied response header back to the servlet client.
	   * This is easily overwritten to filter out certain headers if desired.
	   */
	  protected void copyResponseHeader(HttpServletRequest servletRequest,
	                                  HttpServletResponse servletResponse, Header header) {
	    String headerName = header.getName();
	    if (hopByHopHeaders.containsHeader(headerName))
	      return;
	    String headerValue = header.getValue();
	    if (headerName.equalsIgnoreCase(org.apache.http.cookie.SM.SET_COOKIE) ||
	            headerName.equalsIgnoreCase(org.apache.http.cookie.SM.SET_COOKIE2)) {
	      //copyProxyCookie(servletRequest, servletResponse, headerValue);
	    } else if (headerName.equalsIgnoreCase(HttpHeaders.LOCATION)) {
	      // LOCATION Header may have to be rewritten.
	      servletResponse.addHeader(headerName, rewriteUrlFromResponse(servletRequest, headerValue));
	    } else {
	      servletResponse.addHeader(headerName, headerValue);
	    }
	  }
	  
//	  /** Copy cookie from the proxy to the servlet client.
//	   *  Replaces cookie path to local path and renames cookie to avoid collisions.
//	   */
//	  protected void copyProxyCookie(HttpServletRequest servletRequest,
//	                                 HttpServletResponse servletResponse, String headerValue) {
//	    List<HttpCookie> cookies = HttpCookie.parse(headerValue);
//	    String path = servletRequest.getContextPath(); // path starts with / or is empty string
//	    path += servletRequest.getServletPath(); // servlet path starts with / or is empty string
//	    if(path.isEmpty()){
//	        path = "/";
//	    }

//	    for (HttpCookie cookie : cookies) {
//	      //set cookie name prefixed w/ a proxy value so it won't collide w/ other cookies
//	      String proxyCookieName = doPreserveCookies ? cookie.getName() : getCookieNamePrefix(cookie.getName()) + cookie.getName();
//	      Cookie servletCookie = new Cookie(proxyCookieName, cookie.getValue());
//	      servletCookie.setComment(cookie.getComment());
//	      servletCookie.setMaxAge((int) cookie.getMaxAge());
//	      servletCookie.setPath(path); //set to the path of the proxy servlet
//	      // don't set cookie domain
//	      servletCookie.setSecure(cookie.getSecure());
//	      servletCookie.setVersion(cookie.getVersion());
//	      servletResponse.addCookie(servletCookie);
//	    }
//	  }
	  
	  /** For a redirect response from the target server, this translates {@code theUrl} to redirect to
	   * and translates it to one the original client can use. */
	  protected String rewriteUrlFromResponse(HttpServletRequest servletRequest, String theUrl) {
	    //TODO document example paths
	    final String targetUri = getTargetUri(servletRequest);
	    if (theUrl.startsWith(targetUri)) {
	      /*-
	       * The URL points back to the back-end server.
	       * Instead of returning it verbatim we replace the target path with our
	       * source path in a way that should instruct the original client to
	       * request the URL pointed through this Proxy.
	       * We do this by taking the current request and rewriting the path part
	       * using this servlet's absolute path and the path from the returned URL
	       * after the base target URL.
	       */
	      StringBuffer curUrl = servletRequest.getRequestURL();//no query
	      int pos;
	      // Skip the protocol part
	      if ((pos = curUrl.indexOf("://"))>=0) {
	        // Skip the authority part
	        // + 3 to skip the separator between protocol and authority
	        if ((pos = curUrl.indexOf("/", pos + 3)) >=0) {
	          // Trim everything after the authority part.
	          curUrl.setLength(pos);
	        }
	      }
	      // Context path starts with a / if it is not blank
	      curUrl.append(servletRequest.getContextPath());
	      // Servlet path starts with a / if it is not blank
	      curUrl.append(servletRequest.getServletPath());
	      curUrl.append(theUrl, targetUri.length(), theUrl.length());
	      theUrl = curUrl.toString();
	    }
	    return theUrl;
	  }	  


}

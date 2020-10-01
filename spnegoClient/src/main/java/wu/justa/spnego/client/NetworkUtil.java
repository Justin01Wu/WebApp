package wu.justa.spnego.client;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public final class NetworkUtil {
	
	private static final Logger LOG = Logger.getLogger(NetworkUtil.class);
    private NetworkUtil() {
    }
	
	/**
	 * For web application which is behind a proxy server, load balancer or the popular Cloudflare solution, 
	 * you should get the client IP address via the HTTP request header X-Forwarded-For (XFF).
	 */
	public static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
            
            // it has a bug if we have multiple layer proxy
            // in this case, X-FORWARDED-FOR will queue like this :  X-Forwarded-For: client, proxy1, proxy2
            // TODO fix it 
        }

        return remoteAddr;
    }
	
	public Map<String, String> getRequestHeadersInMap(HttpServletRequest request) {

        Map<String, String> result = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }

        return result;
    }
	
	public static void printHeaders(HttpServletRequest request) {

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            
            LOG.info(key + ": " + value);
        }

    }

}

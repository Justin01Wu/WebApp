package wu.justa.spnego.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.auth0.jwt.interfaces.DecodedJWT;

import wu.justa.model.User;

public class PostAuthenticationFilter implements Filter {

	private static final Logger log = Logger.getLogger(PostAuthenticationFilter.class);

    private static final String LOGIN_ERROR = "/jsp/common/errorLogin.jsp";
    private static final String IMPERSONATED_ENABLED = "sso.impersonated.enabled";
    
    private static final String AuthServer = "http://localhost:8380/spnego/Security?redirect_uri=";
    public static final String DOMAIN_USER_NAME = "domainUserName";
    public static final String JWT_TOKEN_QUERY = "jwtToken";
    public static final String AS_USER = "as_user";
    public static final String KEY_AUTH_USER = "loginUser";
    
    private boolean isSSOImpersonatedEnabled;
    
	@Override
	public void init(final FilterConfig filterConfig) {
		this.isSSOImpersonatedEnabled = Boolean.getBoolean(IMPERSONATED_ENABLED);
		if ( this.isSSOImpersonatedEnabled ) {
			log.warn("Impersonation Enabled, are you sure?");
		}
	}

	@Override
	public void destroy() {
		// NoOp
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest)req;
        final HttpServletResponse response = (HttpServletResponse)resp;
	    final HttpSession session = request.getSession(false);

	    User authUser = null;
	    if (session != null) {
	    	authUser = (User)session.getAttribute(KEY_AUTH_USER);	
	    }

	    if (null != authUser) {//already authorized => possible to continue
	        if (!authUser.getDomainUserName().equals(request.getAttribute(DOMAIN_USER_NAME))) {
	            //cannot change domain user name after domain user name is set for the first time (either standard authentication or impersonation)
	            //to change logout is needed
	        	request.setAttribute(DOMAIN_USER_NAME, authUser.getDomainUserName());
	        }
            //if there is no change to the system after user is authenticated (system can be changed if and only if after user is authenticated on default system)
            //approach will simplify maintenance, because eventually this approach will multiple systems on the same instance will disappear (be removed from VCAPS)
             chain.doFilter(request, response);
	        return;
	    }
	    
		String token = req.getParameter(JWT_TOKEN_QUERY);
        if (null == token) {
            redirectToAuthCenter(request, response);
            return;
        }	
        
        //not in session => first request
        String domainUserName;
		try {
			domainUserName = extractUniqueName(request, this.isSSOImpersonatedEnabled);
	    	request.setAttribute(DOMAIN_USER_NAME, domainUserName);
	    	processUserRequest(request, response, token);
		} catch (Exception e) {
			log.error("", e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

        getRideOfTokenQuery(request, response);
        
	}

	public void redirectToAuthCenter(HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		String origUrl = getFullUrl(request);		
		origUrl = URLEncoder.encode(origUrl, "UTF-8");
		String nextpage = AuthServer + origUrl ;
		response.sendRedirect(nextpage);
	}
	
	public void getRideOfTokenQuery(HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		StringBuffer requestURL = request.getRequestURL();
		String queryString = getRideOfTokenQuery(request);

		String origUrl;
		if (queryString == null) {
			origUrl =  requestURL.toString();
		} else {			
			origUrl = requestURL.append(queryString).toString();
		}
		String nextpage = origUrl ;
		response.sendRedirect(nextpage);

	}
	
	private static String getRideOfTokenQuery(HttpServletRequest request) {
		String queryString = request.getQueryString();
		if(queryString == null) {
			return null;
		}
		Map<String, String> qMap  = getQueryMap(queryString);
		String newQuery = null;
		for(String key: qMap.keySet()) {
			if(key.equals(JWT_TOKEN_QUERY)) {
				continue;
			}
			String newQPara = key + "=" + qMap.get(key);
			if(newQuery == null) {
				newQuery = "?" + newQPara;
			}else {
				newQuery = newQuery + "&" + newQPara;
			}
		}
		
		return newQuery;
		
	}
	private static Map<String, String> getQueryMap(String query) {  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();

	    for (String param : params) {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	}
	
	static String getFullUrl(HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (queryString == null) {
			return requestURL.toString();
		} else {
			return requestURL.append('?').append(queryString).toString();
		}
	}

	private static String extractUniqueName(final HttpServletRequest req, boolean isSSOImpersonatedEnabled ) throws URISyntaxException, SecurityException, IOException {
		
		String token = req.getParameter(JWT_TOKEN_QUERY);   // get the  Query          
        
        if (null==token) {
            //if ID token is not available then try with access token
            throw new RuntimeException("didn't find token");
        }
        //check if token is for "named user" or "service account"
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        if (null == jwt) {
        	throw new RuntimeException("can't find jwt");
        }
        if (isSSOImpersonatedEnabled) {
            String asUser = req.getParameter(AS_USER);
            if(asUser !=  null && !asUser.isEmpty()) {
    	        //handle impersonation 
            	return asUser;
            }        	
        }
        return jwt.getSubject();
        
	}
	

	private void processUserRequest(final HttpServletRequest request, final HttpServletResponse response, String token) throws ServletException, IOException {
		if (log.isTraceEnabled()) {
			log.trace("Entering PostAuthenticationFilter processing of user request");
		}

		String clientIp = NetworkUtil.getClientIp(request);
		String authDomainUserName = (String)request.getAttribute(DOMAIN_USER_NAME);
		log.info(String.format("%s\t%s \ttried to log in", authDomainUserName, clientIp));

        User authUser = new User();  // TODO get user from database by domainUserName
        authUser.setDomainUserName(authDomainUserName);

        if (!validateUser(request, response, authUser, authDomainUserName)) {
			return;
		}
		
		authUser.setToken(token);

        HttpSession session = request.getSession();
		session.setAttribute(KEY_AUTH_USER, authUser);

	}

    private boolean validateUser(final HttpServletRequest request, final HttpServletResponse response, final User authUser, final String authDomainUserName) throws ServletException, IOException {
        boolean isValid = false;
        String clientIp = NetworkUtil.getClientIp(request);
        if (authUser==null) {
            log.warn(String.format("%s\t%s \tLogin failed! - The user name does not exist!", authDomainUserName, clientIp)); 
            prepareResponse(request, response, "You are not authorized VCAPS user, please contact the system administrator");
        } else if (!"active".equalsIgnoreCase(authUser.getStatus())) {
            log.warn(String.format("%s\t%s \tLogin failed! - The user is not active!", authDomainUserName, clientIp));
            prepareResponse(request, response, String.format("The user name %s is not active VCAPS user, please contact the system administrator", authDomainUserName));
        } else {
            isValid = true;
        }
        return isValid;
    }

    private void prepareResponse(final HttpServletRequest request, final HttpServletResponse response, final String msg) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(request.getContentType());
        if ("application/json".equalsIgnoreCase(request.getContentType())) {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"status\": ").append(HttpServletResponse.SC_FORBIDDEN).append(", \"message\": \"").append(msg).append("\"}");
            response.getWriter().write(sb.toString());
            return;
        }
        //forward for other media types
        dispatch(request,response,LOGIN_ERROR);
    }

    private void dispatch(final HttpServletRequest request, final HttpServletResponse response, final String nextpage) throws ServletException, IOException {
        RequestDispatcher disp = request.getServletContext().getRequestDispatcher(nextpage);
        disp.forward(request, response);
    }

}

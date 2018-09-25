package wu.justa.proxy;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InnerTokenFilter implements Filter {
	
	public static final String INNER_TOKEN_KEY ="USERID";
	public static final String INNER_TOKEN_FROM ="ACCESS_FROM";	

	
	private static final Logger log = Logger.getLogger(InnerTokenFilter.class.getName());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
  		HttpServletRequest httpRequest=(HttpServletRequest) request;
  		HttpServletResponse httpResponse=(HttpServletResponse) response;
  		
  		String uri = httpRequest.getRequestURI();
  		
  		log.info(uri);

  		
  		String remoteAddr = request.getRemoteAddr();
		log.finest("remoteAddr = " + remoteAddr);	

		// check if inner token exists and valid
		String token = httpRequest.getHeader(INNER_TOKEN_KEY);
		if(token == null || token.isEmpty()){
			log.info(String.format("remoteAddr %s is denied for %s" , remoteAddr, uri));
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "you are not allowed to access this API");	
			return;
		}
		try{
			Integer userId = Integer.valueOf(token);
			if(userId<=0){
				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "you are not allowed to access this API");	
				return;				
			}
		}catch(NumberFormatException e){
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "you are not allowed to access this API");	
			return;
		}		
		
		// check if it declare "access from"
		token = httpRequest.getHeader(INNER_TOKEN_FROM);
		if(token == null || token.isEmpty()){
			log.info(String.format("remoteAddr %s is denied for %s" , remoteAddr, uri));
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "you are not allowed to access this API");
			return;			
		}

  		// white list protection, only local now
		String[] ipArray = new String[1]; 	//VcapsPropertiesEnum.VRISC_IP.getValueAsArray();
		ipArray[0]="127.0.0.1";		
		for(String ip : ipArray){
			if(ip.equals(remoteAddr) ){
				log.info(String.format("access to %s is accepted from remote address %s" , uri, remoteAddr));	
				chain.doFilter(request, response);
				log.info("InnerTokenFilter return");
				return;
			}
		}
		
		log.info(String.format("remoteAddr %s is denied for %s" , remoteAddr, uri));
		httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "you are not allowed to access this API");
		
		return; 

		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void destroy() {		
	}
}


package wu.justa.spnego;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

// direct return JWT token as json format
// CORS is enabled, so JavaScript from other origin can directly call it
public class JWTTokenServlet extends HttpServlet {

	private static final long serialVersionUID = 15464563L;

	private static final Logger log = Logger.getLogger(JWTTokenServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		log.info("getting JWT Token...");
		
		TokenUser au = SecurityServlet.getAuthUser(request, response);
		if (au == null) {
			return;
		}

		String newLine = System.lineSeparator();
		String temp = "{\"userName\": \"%s\"," + newLine
				+ " \"JWTToken\": \"%s\", " + newLine
				+ " \"keyId\": \"%s\", " + newLine
				+ " \"JWTCreated\": %d, " + newLine
				+ "\"JWTExpired\": %d}";
		String token = createToken2 (au);
		String result = String.format(temp, au.getUserName(), 
				token, 
				au.getTokenKey(),
				au.getTokenCreateTime().getTime(),
				au.getTokenExpiredTime().getTime()
				);
		response.setStatus(HttpServletResponse.SC_OK);

		response.setContentType("application/json");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD");
        
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
		
		log.info("created token " + au.getTokenKey() + " for user " + au.getUserName());

	}
	
	public static String createToken2( TokenUser user) {
		long ttlMillis = 86400000l; // one day
		// long ttlMillis = 31536000000l; // one year

		Date now = new Date(); // create time
		long expMillis = now.getTime() + ttlMillis;
		Date exp = new Date(expMillis); // expired time
		
		user.setTokenCreateTime(now);
		user.setTokenKey("Spnego_" + user.getUserName() + "_" + now.getTime());
		user.setTokenExpiredTime(exp);
		
		String token = createToken(user);
		return token;		
	}
	
	private static String createToken( TokenUser user) {
		
		Algorithm algorithm = Algorithm.HMAC256("MyPassword1234");
		String token = JWT.create()
				.withExpiresAt(user.getTokenExpiredTime())
				.withKeyId(user.getTokenKey()).withIssuedAt(user.getTokenCreateTime())
				.withSubject(user.getUserName())
				.withIssuer("Spnego")				
				.withClaim("justin", "I can add any fields into JWT token")
				.withClaim("email", "justin.wu@global.local")
				.withClaim("preferred_username", "justin.wu")
				.withClaim("realm_access", "offline_access")
				.sign(algorithm);
		return token;
	}

}
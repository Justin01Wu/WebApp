package wu.justa.proxy;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
	public static String createToken(Integer userId, String userName, long ttlMillis) {

		Date now = new Date();  		// create time
		long expMillis = now.getTime() + ttlMillis;
		Date exp = new Date(expMillis); // expired time

		Algorithm algorithm = Algorithm.HMAC256(JWTSetting.JWT_SECRET);
		String token = JWT.create()
				.withExpiresAt(exp)
				.withIssuedAt(now)
				.withSubject(String.valueOf(userId))
				.withIssuer("Jersey2")
				.withClaim("userName", userName)
				.withClaim("justin", "I can add any fields into JWT token")
				.sign(algorithm);
		return token;
	}


	
	public static DecodedJWT verifyToken(String token) {
	    Algorithm algorithm = Algorithm.HMAC256(JWTSetting.JWT_SECRET);
	    JWTVerifier verifier = JWT.require(algorithm)
	        .withIssuer("Jersey2")
	        .build(); //Reusable verifier instance
	    DecodedJWT jwt = verifier.verify(token);  // will throw many runtimeexception if it fails		
		return jwt;		
	}
	
	public static void printToken(DecodedJWT jwt) {
		System.out.println("subject=" + jwt.getSubject());
		System.out.println("keyId=" + jwt.getKeyId());
		System.out.println("Id=" + jwt.getId());
		System.out.println("issuer=" + jwt.getIssuer());
		
		// customized field
		System.out.println("userName=" + jwt.getClaim("userName").asString());
		System.out.println("justin=" + jwt.getClaim("justin").asString());
		
	}


}

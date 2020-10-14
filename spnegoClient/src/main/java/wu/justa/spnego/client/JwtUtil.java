package wu.justa.spnego.client;

import java.io.IOException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

public class JwtUtil {
	public static final String JWT_SECRET = "MyPassword1234";

	public static DecodedJWT verifyToken(String token) throws SecurityException, IOException {

		Algorithm algorithm = extractAlgorithm2(token);
		Verification ver = JWT.require(algorithm);
		if(algorithm.getName().equals("HS256")) {
			ver = ver.withIssuer("Spnego");
		}else {
			ver = ver.acceptExpiresAt(6678520957l);  // to skip expired verification
		}
		JWTVerifier verifier = ver.build(); // Reusable verifier instance
		DecodedJWT jwt = verifier.verify(token);

		return jwt;

	}

	private static Algorithm extractAlgorithm2(String token) throws SecurityException, IOException {
		JWT parser =  new JWT ();	
		DecodedJWT jwt = parser.decodeJwt(token);
		String alg = jwt.getAlgorithm();
		
		if (alg.equals("HS256")) {
			return Algorithm.HMAC256(JWT_SECRET);			
		}

		throw new SecurityException("unknown Algorithm: " + alg );
	}
	
}
package wu.justa.spnego.client;

import java.io.IOException;
import java.util.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtUtil {
	public static final String JWT_SECRET = "MyPassword1234";

	private static final String CHARSET_NAME = "UTF-8";
	
	public static DecodedJWT verifyToken(String token) throws SecurityException, IOException {

		Algorithm algorithm = extractAlgorithm(token);
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

	private static Algorithm extractAlgorithm(String token) throws SecurityException, IOException {
		System.out.println("Attempting to extract algorithm information from token.");
		final String[] tokenParts = token.split("\\.");
		if (tokenParts.length != 3) { // Header + Body + Signature
			throw new SecurityException("Invalid token received.");
		}

		String base64EncodedHeader = tokenParts[0];
		String base64EncodedBody = tokenParts[1];

		String header = new String(Base64.getDecoder().decode(base64EncodedHeader.getBytes()), CHARSET_NAME);
		String body = new String(Base64.getDecoder().decode(base64EncodedBody.getBytes()), CHARSET_NAME);
		System.out.println(String.format("Decoded token's header: %s, and boy: %s", header, body));

		// The kid claim indicates the particular public key that was used to sign the token 
		new ObjectMapper();
		JsonNode headerNode = new ObjectMapper().readTree(header);
		JsonNode bodyNode = new ObjectMapper().readTree(body);

		String alg = headerNode.get("alg").textValue();
		Algorithm a = Algorithm.HMAC256(JWT_SECRET);
		if (a.getName().equals(alg)) {
			return a;
		}else {
			throw new RuntimeException("unknown Algorithm: " + alg);
		}

	}

}
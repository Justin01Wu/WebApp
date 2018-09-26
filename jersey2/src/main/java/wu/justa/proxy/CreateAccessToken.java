package wu.justa.proxy;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CreateAccessToken extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger LOG = Logger.getLogger(CreateAccessToken.class.getName());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try (OutputStream out = response.getOutputStream()) {

			String tokenId = request.getParameter("tokenId");
			String tokenPassword = request.getParameter("tokenPassword");
	  		String remoteAddr = request.getRemoteAddr();

			if(tokenId == null || !tokenId.equals("1")){

	  			LOG.finest("CreateAccessToken is rejected for token "+ tokenId +" on remoteAddr = " + remoteAddr);	

				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
				return;
			}
			
			if(tokenPassword == null || !tokenPassword.equals("justin719")){
				LOG.finest("CreateAccessToken is rejected for token "+ tokenId +" on remoteAddr = " + remoteAddr);
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
				return;
			}
			
			String accessTokenUser = "1";   // TODO get userId from token table
			InnerUser user = null;
	  		try{
	  			int userId = Integer.valueOf(accessTokenUser);
	  			user = InnerUserService.load(userId);
	  			if(user == null){
					LOG.finest("CreateAccessToken is rejected for token "+ tokenId +" on remoteAddr = " + remoteAddr);
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
					return;
	  			}
	  			
	  		}catch(NumberFormatException e){
				LOG.severe(e.getMessage());
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
				return;
	  		}catch(SQLException e){
				LOG.severe(e.getMessage());
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "invalid token");
				return;
	  		}
			
			Date now= new Date();
			
			long expiredTime =  now.getTime()+ 30*60000;   // will expire in 30 minutes
			
			String accessToken = null;
			try {
				accessToken = createJWTToken(user, expiredTime);
			} catch (Exception e) {
				LOG.severe(e.getMessage());
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed on token creating");
				return;
			}
			
			Map<String, String> restData = new HashMap<>();
			
			restData.put("access_token", accessToken);
			restData.put("expired", String.valueOf(expiredTime));
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(restData);			
			
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
		    out.write(jsonInString.getBytes("UTF-8"));        
		    out.flush();
		    

		}
	}
	
	// comes from https://blog.csdn.net/qq_37636695/article/details/79265711
	public String createJWTToken(InnerUser user, long ttlMillis) throws Exception {
		
		//return "not yet";
		
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        
        Map<String,Object> claims = new HashMap<String,Object>();
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        //claims.put("uid", "DSSFAWDWADAS...");
        claims.put("userName", user.getName());
        claims.put("userId", user.getId());
        claims.put("justin","I can add any fields into JWT");
        SecretKey key = JWTSetting.SecretKey;  //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        
        
        String tokenId = UUID.randomUUID().toString();
        
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(tokenId)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           //iat: jwt的签发时间
                .setIssuer("Jersey2")           //iss: who issue this token                
                .setSubject(user.getId().toString())        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(SignatureAlgorithm.HS256, key);  //设置签名使用的签名算法和签名使用的秘钥
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     //设置过期时间
        }
        
        String result =  builder.compact();
        return result;
        
    }
}

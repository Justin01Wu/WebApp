package wu.justin.rest2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import wu.justa.proxy.InnerUser;
import wu.justa.proxy.InnerUserService;
import wu.justin.rest2.exception.BadRequestError;
import wu.justin.rest2.exception.NotReadyError;

public class ApiUtil {
	
	private static final Logger LOG = Logger.getLogger(ApiUtil.class.getSimpleName());
	
	public static final String UserNameField = "username";
	public static final String KEY_AUTH_USER = "authUser";
	public static final String JWTToken = "JWTToken";	
	public static final String JWTExpireAt = "JWTExpireAt";
	
	private ApiUtil() {
		//do nothing
	}
	
	public static InnerUser getCurrentUser( HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession(false);
		
		String userName = (String)request.getAttribute(UserNameField);
		// can we directly get decoded token from SecurityConfig.JWTToken?
		
		InnerUser user = null;
		if (userName != null) {
			user = getUserFromToken(request, userName);
		} else if(session != null){
			// web based access
			user = (InnerUser) session.getAttribute(KEY_AUTH_USER);			
		} else {
			 throw new 	JWTVerificationException("token or session is not found " );
		}
		return user;
	} 
	
	
    public static String getSimpledUserName(String userName) {

        String[] userNameParts = userName.split("\\\\");
        if(userNameParts.length >  2) {        	
        	String msg =  "token user name format is incorrect: " + userName;
        	System.out.println(msg);        	
			throw new JWTVerificationException(msg);
        }
        String simpledUserName = userNameParts.length == 2 ?  userNameParts[1] : userName;
        return simpledUserName;
    }
	
	private static InnerUser getUserFromToken(HttpServletRequest request, String userName) throws SQLException {
		// token based access		
        String simpledUserName = getSimpledUserName(userName) ;
        InnerUser user = InnerUserService.load(1); // TODO fix hard coded userId, should get userId from token
		if(user == null) {
			String msg = String.format("could not load user info for %s", userName);
			LOG.warning(msg);
			throw new 	JWTVerificationException(msg );					
		}
		String token = (String)request.getAttribute(JWTToken);
		Long expireAt = (Long)request.getAttribute(JWTExpireAt);
		Date expire = new Date();
		expire.setTime(expireAt);
		//user.setTokenBin(token, expire);
		return user;

	}
	
	public static String getFormatedJsonOrNull(String jsonInString) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(jsonInString, Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			return indented;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static String getFormatedHtmlOrNull(String aStr) {
		
		InputStream is = new ByteArrayInputStream( aStr.getBytes() );
		
		Tidy tidy = new Tidy(); 
		tidy.setIndentContent(true);		
		tidy.setPrintBodyOnly(true);
	    tidy.setTidyMark(false);
		//tidy.setQuiet(true);
		//tidy.setShowWarnings(false);
		Document htmlDOM = tidy.parseDOM(is, null);	
		
	    // Pretty Print
	    OutputStream out = new ByteArrayOutputStream();
	    tidy.pprint(htmlDOM, out);
	    return out.toString();
	}
	
	public static String getJsonFromFile(File resultFile) throws IOException{
		Objects.requireNonNull(resultFile, "The resultFile must be provided.");
		if(!resultFile.exists()){
			throw new NotReadyError("The result file is not there, please try it later: "+ resultFile.getAbsolutePath());
		}
		if(!resultFile.isFile()){
			throw new BadRequestError(" it is not a file: "+ resultFile.getAbsolutePath());
		}
		String resultStr = FileUtils.readFileToString(resultFile, StandardCharsets.UTF_8);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			@SuppressWarnings("unused")
			JsonNode jsonNode = objectMapper.readTree(resultStr);
		} catch (IOException e) {
			String msg = "invalid json file: " + resultFile.getAbsolutePath();
			System.err.println(msg);
			throw new BadRequestError(msg);
		}
	    return resultStr;		

	}
	
	public static String getContextPath(HttpServletRequest request) {
		String scheme = request.getScheme();           // http
		String serverName = request.getServerName();   // hostname.com
		int serverPort = request.getServerPort();      // 80
		String contextPath = request.getContextPath(); // /mywebapp
		String contextURL = scheme + "://" + serverName + ":" + serverPort+ contextPath;
		return contextURL;
	}
	
	public static String utf8Encode(String pathName) throws UnsupportedEncodingException{
		String urlEncode = URLEncoder.encode(pathName, "UTF-8").replace("+", "%20");
		return urlEncode;
	}
	
	public static void validJsonStr(final String jsonStr) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			@SuppressWarnings("unused")
			JsonNode jsonNode = objectMapper.readTree(jsonStr);
		} catch (IOException e) {
			throw new BadRequestError("invalid json string");
		}
		return ;
	}
	
	  public static JSONObject convertJSONStr2Obj(String jsonStr) throws ParseException {
		    JSONObject expectedJson =
		        (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(jsonStr);
		    return expectedJson;
		  }
	
    public static String convertObject2JSONStr(Object obj) throws JsonProcessingException  {
    	ObjectMapper objectMapper = new ObjectMapper();
    	String result = objectMapper.writeValueAsString(obj);
        return result;
    }
}

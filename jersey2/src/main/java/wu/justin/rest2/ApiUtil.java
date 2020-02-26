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
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import wu.justin.rest2.exception.BadRequestError;
import wu.justin.rest2.exception.NotReadyError;

public class ApiUtil {
	
	private ApiUtil() {
		//do nothing
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
}

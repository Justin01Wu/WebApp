package wu.justin.rest2;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.Predicate;


//it comes from  https://stackoverflow.com/questions/38449267/optional-jsonpath-using-jayway
public class JsonPathUtil {

	private static Configuration JsonPathConfig = Configuration
			.defaultConfiguration()
			.addOptions(Option.SUPPRESS_EXCEPTIONS);  
	// this option will let dc always return null rather than exception if target doesn't exists
	// or empty list if it expected a list
	
	private DocumentContext dc;
	
	
	public JsonPathUtil(String document) {
		dc = JsonPath.using(JsonPathConfig).parse(document);
	}
	
	public JsonPathUtil(Object document) {
		dc = JsonPath.using(JsonPathConfig).parse(document);
	}	
	
	public <T> T readOrNull(String jsonPath, Predicate... filters) {
		
		return dc.read(jsonPath, filters);
	}
}

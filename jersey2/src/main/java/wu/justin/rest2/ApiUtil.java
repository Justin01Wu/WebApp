package wu.justin.rest2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiUtil {
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
}

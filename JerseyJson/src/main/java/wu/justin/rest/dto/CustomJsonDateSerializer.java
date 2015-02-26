package wu.justin.rest.dto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class CustomJsonDateSerializer extends JsonSerializer<Date> {
	
	public static final String DateFormatStr = "MM/dd/yyyy";

	public void serialize(Date date, JsonGenerator gen,	SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat format = new SimpleDateFormat(DateFormatStr);
		String formattedDate = format.format(date);
		gen.writeString(formattedDate);
	}
}
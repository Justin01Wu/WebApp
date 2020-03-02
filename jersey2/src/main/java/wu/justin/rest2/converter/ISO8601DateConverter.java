package wu.justin.rest2.converter;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ISO8601DateConverter {
public static class ISO8601DateSerializer extends JsonSerializer<Date> {
		
		public static final String DateFormatStr = "yyyy-MM-dd'T'HH:mm:ssX";  
		// the last X is only supported by JDK 7 and above, it is not traditional Z, which is incorrect ISO8601 format
		// for details, please see  https://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
		

		public void serialize(Date date, JsonGenerator gen,	SerializerProvider provider) throws IOException,
				JsonProcessingException {
			
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat format = new SimpleDateFormat(DateFormatStr);
			format.setTimeZone(tz);
			
			if(date == null){
				gen.writeString("");
			}else{		
				String formattedDate = format.format(date);
				gen.writeString(formattedDate);
			}
		}
	}
	
	public static class ISO8601DateDeserializer extends JsonDeserializer<Date> {
		
		@Override
		public Timestamp deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException,
				JsonProcessingException {

			TimeZone tz = TimeZone.getTimeZone("UTC");
			SimpleDateFormat format = new SimpleDateFormat(ISO8601DateSerializer.DateFormatStr);
			format.setTimeZone(tz);
			
			String dateStr = jsonparser.getText();
			if(dateStr == null){
				return null;
			}
			dateStr = dateStr.trim();
			if(dateStr.isEmpty()){
				return null;
			}
			try {
				Date x = format.parse(dateStr);
				return new Timestamp(x.getTime()); 
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}

		}

	}

}

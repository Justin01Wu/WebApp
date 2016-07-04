package wu.justin.rest2;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ISO8601ShortDateConverter {
	
	@Provider
	public static class ISO8601ShortDateSerializer extends JsonSerializer<Date> {
		
		public static final String DateFormatStr = "yyyy-MM-dd";

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
	
	@Provider
	public static class ISO8601ShortDateDeserializer extends JsonDeserializer<Date> {
		
		@Override
		public Timestamp deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException,
				JsonProcessingException {

			TimeZone tz = TimeZone.getTimeZone("UTC");
			SimpleDateFormat format = new SimpleDateFormat(ISO8601ShortDateSerializer.DateFormatStr);
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

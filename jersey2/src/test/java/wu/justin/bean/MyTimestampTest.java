package wu.justin.bean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MyTimestampTest {
	
    @Test
    public void verifyJsonStructure() throws IOException, URISyntaxException {
    	
		MyTimestamp result =new MyTimestamp();
		result.setRunDate(new Timestamp(System.currentTimeMillis()));
		
		ObjectMapper mapper = new ObjectMapper();
		
		String targetJson = mapper.writeValueAsString(result);
		System.out.println(targetJson);
		MyTimestamp r2 = mapper.readValue(targetJson, MyTimestamp.class);
		
		
    }
}

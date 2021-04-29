package wu.justin.bean;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MyTimestampTest {
	
    @Test
    public void verifyJsonStructure() throws IOException, URISyntaxException {
    	
		MyTimestamp result =new MyTimestamp();
		result.setRunDate(new Timestamp(System.currentTimeMillis()));
		result.setLocalDate(LocalDate.of(2021, 04, 29));
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		String targetJson = mapper.writeValueAsString(result);
		System.out.println(targetJson);
		MyTimestamp r2 = mapper.readValue(targetJson, MyTimestamp.class);
		
		
		assertEquals(r2.getLocalDate(), LocalDate.of(2021, 4, 29));
    }
}

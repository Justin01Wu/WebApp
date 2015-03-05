package wu.justin.rest.dto;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

// it comes from 
// http://www.mkyong.com/java/how-to-convert-java-object-to-from-json-jackson/
public class AddressHideDTOTest {

	@Test
	public void testGetAddress() throws JsonGenerationException, JsonMappingException, IOException {
		
		AddressHideDTO address = new AddressHideDTO();
		address.setId(1234);
		address.setCountry("China");
		address.setAddress("ShenZhen box 2345");
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString  = mapper.writeValueAsString(address);
		
		AddressHideDTO result = mapper.readValue(jsonString, AddressHideDTO.class);
		// validate only address is return
		assertTrue(result.getId() == 0);
		assertTrue(result.getCountry() == null);
		assertTrue(result.getAddress().equals("ShenZhen box 2345"));
	}

}

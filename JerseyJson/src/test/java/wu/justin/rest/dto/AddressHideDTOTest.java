package wu.justin.rest.dto;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.junit.Test;

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
	
	@Test
	public void testFields() {
		
		JaxbAnnotationIntrospector  ai = new JaxbAnnotationIntrospector ();
		AnnotatedClass ac = AnnotatedClass.construct(AddressHideDTO.class, ai, null);
		ac.resolveFields();
		ac.resolveClassAnnotations();
		ac.resolveMemberMethods(null);
		ac.resolveClassAnnotations();
		System.out.println("fieldCount=" + ac.getFieldCount());
		
		for(AnnotatedField f: ac.fields()){
			if(ai.isIgnorableField(f)){
				System.out.println("ignored: " + f.getName());	
			}else{
				System.out.println("valid: " + f.getName());
			}			
		}
		// always return "valid", don't know why
		
		System.out.println("");
		
		for(AnnotatedMethod m: ac.memberMethods()){
			if(ai.isIgnorableMethod(m)){
				System.out.println("ignored: " + m.getName());	
			}else{
				System.out.println("valid: " + m.getName());
			}			
		}
		// always return "valid", don't know why		
		
		
		String[] ignoredList = ai.findPropertiesToIgnore(ac);		
		// ignoredList is always null, don't know why
//		for(String one: ignoredList){
//			System.out.println(one);
//		}

	}

}

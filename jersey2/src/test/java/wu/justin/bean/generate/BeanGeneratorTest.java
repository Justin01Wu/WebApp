package wu.justin.bean.generate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import wu.justin.bean.CustomizedBean;
import wu.justin.bean.ManyDataType;
import wu.justin.bean.User;
import wu.justin.bean.User2;

public class BeanGeneratorTest {

	@Test
	public void testCustomizedCreator() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		generator.addExteralCreator(CustomizedBean.class, new CustomizedBeanCreator());
		String result = generator.generateJson(CustomizedBean.class);
		System.out.println(result);
		assertTrue(result.contains("this bean is created by a customized creator"));

	}
	
	@Test
	public void testManyDataType() throws Exception {
		String result = new BeanGenerator().generateJson(ManyDataType.class);
		System.out.println(result);
		assertTrue(result.contains("oneEnum"));
		assertTrue(result.contains("addressList"));
		assertTrue(result.contains("oneBoolean2"));
		assertTrue(result.contains("stringList"));
		assertTrue(result.contains("integerSet"));
		
		
	}
	
	@Test
	public void testUser2() throws Exception {
		String result = new BeanGenerator().generateJson(User2.class);
		System.out.println(result);
		assertTrue(result.contains("username"));
		// looks like it can't handle toJsonString method in ObjectId, why?

	}

	@Test
	public void testString() throws Exception {
		String result = new BeanGenerator().generateJson(String.class);
		System.out.println(result);
		assertEquals(result, "\"a string\"");

	}
	
	@Test
	public void testDateConvert() throws Exception {
		String result = new BeanGenerator().generateJson(String.class);
		System.out.println(result);
		assertEquals(result, "\"a string\"");

	}
	
	@Test
	public void testUser() throws Exception {
		String result = new BeanGenerator().generateJson(User.class);
		System.out.println(result);
		
		assertTrue(result.contains("homeAddress"));
		assertTrue(result.contains("Admin"));
		

	}

	@Test
	public void testCustomizedString() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		generator.addExteralCreator(String.class, new CustomizedStringCreator());
		String result = generator.generateJson(String.class);
		System.out.println(result);
		assertTrue(result.contains("===this is customized String==="));

	}

	
	

}

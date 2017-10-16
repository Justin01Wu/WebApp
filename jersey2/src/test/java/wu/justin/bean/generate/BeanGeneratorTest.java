package wu.justin.bean.generate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import wu.justin.bean.BeanWithMap;
import wu.justin.bean.CustomizedBean;
import wu.justin.bean.ManyDataType;
import wu.justin.bean.NestedBean;
import wu.justin.bean.TypeEnum;
import wu.justin.bean.User;
import wu.justin.bean.User2;

public class BeanGeneratorTest {

	@Test
	public void testCustomizedCreator() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		generator.addExteralCreator(new CustomizedBeanCreator());
		CustomizedBean result = generator.generate(CustomizedBean.class);
		System.out.println(result);
		assertEquals(result.getId(), new Integer(-2323));
		assertEquals(result.getName(), "this bean is created by a customized creator");

	}
	
	@Test
	public void testManyDataType() throws Exception {
		ManyDataType result = new BeanGenerator().generate(ManyDataType.class);
		System.out.println(result);
		assertEquals(result.getOneEnum(), TypeEnum.Admin);
		assertEquals(result.getOneBoolean2(), Boolean.TRUE);
		assertTrue(result.getStringList().size() ==1);
		assertEquals(result.getStringList().get(0), "a string");
		
		assertTrue(result.getIntegerSet().size() ==1);
		assertEquals(result.getIntegerSet().iterator().next(), new Integer(12345));
		
		assertTrue(result.getAddressList().size() ==1);
		assertEquals(result.getAddressList().get(0).getAddress(), "a string");
		
		
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
		String result = new BeanGenerator().generate(String.class);
		System.out.println(result);
		assertEquals(result, "a string");

	}
	
	@Test
	public void testDateConvert() throws Exception {
		String result = new BeanGenerator().generate(String.class);
		System.out.println(result);
		assertEquals(result, "a string");

	}
	
	@Test
	public void testUser() throws Exception {
		User user = new BeanGenerator().generate(User.class);
		System.out.println(user);
		
		assertEquals(user.getId(), new Integer(12345));
		assertEquals(user.getType(), TypeEnum.Admin);
		

	}

	@Test
	public void testCustomizedString() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		generator.addExteralCreator( new CustomizedStringCreator());
		String result = generator.generate(String.class);
		System.out.println(result);
		assertEquals(result, "===this is customized String===");

	}
	
	@Test
	public void testNestedBean() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		NestedBean result = generator.generate(NestedBean.class);
		System.out.println(result);
		assertNull(result.getParent());  //nested bean, so it will be null 
		assertEquals(result.getName(), "a string");

	}
	
	
	@Test
	public void testBeanWithMap() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		BeanWithMap result = generator.generate(BeanWithMap.class);
		System.out.println(result);
		assertNotNull(result.getUserMap());  
		
		User user = result.getUserMap().get(12345);
		assertEquals(user.getId(), new Integer(12345));
		assertEquals(user.getType(), TypeEnum.Admin);

	}
	
	@Test
	public void testBeanWithArray() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		BeanWithArray result = generator.generate(BeanWithArray.class);
		System.out.println(result);
		assertNotNull(result);  
		assertEquals(result.getStringArray()[0],"a string");
		assertEquals(result.getIntegerArray()[0], new Integer(12345));
		assertEquals(result.getIntArray()[0], 12345);
		assertEquals(result.getUsers()[0].getId(), new Integer(12345));
		

	}

	
	
	
	

	
	

}

package wu.justin.rest.dto;

import static org.junit.Assert.*;

import org.junit.Test;

import wu.justin.business.Address;
import wu.justin.business.ObjectUpdater;
import wu.justin.business.User;

public class UserDTOTest {

	@Test
	public void testBeanUpdater() {
		
		UserDTO dest = new UserDTO();
		dest.setName("Justin");

		Address homeAddress = new Address();
		homeAddress.setId(1111);
		homeAddress.setCountry("cou11");
		homeAddress.setAddress("add11");
		dest.setHomeAddress(homeAddress);
		
		User user = new User();
		user.setId(222);
		user.setName("Justin New");
		
		Address homeAddress2 = new Address();
		homeAddress2.setId(2222);
		homeAddress2.setCountry("cou22");
		homeAddress2.setAddress("add22");
		user.setHomeAddress(homeAddress2);
		
		
		try {
//			BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
//			beanUtilsBean.getConvertUtils().register(false,false,0);
//			beanUtilsBean.copyProperties(dest,address);
			
			//MyBeanUtil.merge(dest,user);
			
			ObjectUpdater.updateObject(dest, user);
		} catch (Exception e) {
			throw new RuntimeException("BeanUtils.copyProperties fail", e);
		}
		assertTrue(dest.getId() == 222);
		assertEquals(dest.getName(), "Justin New");
		assertEquals(dest.getHomeAddress().getId(), 2222);
		assertEquals(dest.getHomeAddress().getCountry(), "cou22");
		
	}

}

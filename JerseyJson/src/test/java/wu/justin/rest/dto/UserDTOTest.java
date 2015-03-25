package wu.justin.rest.dto;

import static org.junit.Assert.*;

import org.junit.Test;

import wu.justin.business.Address;
import wu.justin.business.ObjectUpdater;
import wu.justin.business.User;

public class UserDTOTest {

	@Test
	public void testBeanUpdater() {
		User user = new User();
		user.setId(24234);
		user.setName("Justin");
		
		UserDTO dest = new UserDTO();
		dest.setName("Justin New");

		Address homeAddress = new Address();
		homeAddress.setId(445);
		homeAddress.setCountry("cou");
		homeAddress.setAddress("add");
		dest.setHomeAddress(homeAddress);
		
		try {
//			BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
//			beanUtilsBean.getConvertUtils().register(false,false,0);
//			beanUtilsBean.copyProperties(dest,address);
			
			//MyBeanUtil.merge(dest,user);
			
			ObjectUpdater.updateObject(dest, user);
		} catch (Exception e) {
			throw new RuntimeException("BeanUtils.copyProperties fail", e);
		}
		assertTrue(dest.getId() == 24234);
		assertEquals(dest.getName(), "Justin");
		assertEquals(dest.getHomeAddress().getId(), 445);
		assertEquals(dest.getHomeAddress().getCountry(), "cou");
		
	}

}

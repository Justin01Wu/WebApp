package wu.justin.bean.generate;

import wu.justin.bean.CustomizedBean;

public class CustomizedBeanCreator implements BeanCreator{
	
	@Override
	public <T> T createBean() {
		CustomizedBean bean = new CustomizedBean();
		bean.setId(-2323);
		bean.setName("this bean is created by a customized creator");
		
		@SuppressWarnings("unchecked")
		T t = (T)bean;
		
		return t;
	}

}

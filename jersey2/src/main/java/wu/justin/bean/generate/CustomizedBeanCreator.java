package wu.justin.bean.generate;

import wu.justin.bean.CustomizedBean;

public class CustomizedBeanCreator implements BeanCreator<CustomizedBean>{
	
	@Override
	public CustomizedBean createBean() {
		CustomizedBean bean = new CustomizedBean();
		bean.setId(-2323);
		bean.setName("this bean is created by a customized creator");
		
		return bean;
	}
	
	@Override
	public Class<CustomizedBean> getClazz() {
		return CustomizedBean.class;
	}

}

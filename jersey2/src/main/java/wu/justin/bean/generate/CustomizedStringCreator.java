package wu.justin.bean.generate;

public class CustomizedStringCreator implements BeanCreator{
	
	@Override
	public <T> T createBean() {
		String bean = new String("===this is customized String===");
		
		@SuppressWarnings("unchecked")
		T t = (T)bean;
		
		return t;
	}

}
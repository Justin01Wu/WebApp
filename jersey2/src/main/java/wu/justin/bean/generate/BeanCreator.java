package wu.justin.bean.generate;

public interface BeanCreator<T> {
	
	public T createBean();
	public Class<T> getClazz();

}

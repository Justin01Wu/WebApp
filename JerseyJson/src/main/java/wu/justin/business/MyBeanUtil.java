package wu.justin.business;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class MyBeanUtil {
	//merge two bean by discovering differences 
	public static <M> void merge(M target, M origin) throws Exception {
		
		if (target == origin){
			return;
		}
			
		BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());

		// Iterate over all the attributes
		for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

			System.out.println(descriptor.getName());
			// Only copy writable attributes
			if (descriptor.getWriteMethod() != null) {
				Method method = descriptor.getReadMethod();
				Object originalValue = method.invoke(origin);

				// Only copy values values where the origin values is not null
				if (originalValue != null) {
					Object defaultValue = descriptor.getReadMethod().invoke(origin);
					descriptor.getWriteMethod().invoke(target, defaultValue);
				}

			}
		}
	}
}

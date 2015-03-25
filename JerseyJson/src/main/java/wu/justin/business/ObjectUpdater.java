package wu.justin.business;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

// this code comes from here:
// http://www.coderanch.com/t/450842/java/java/merging-data-values-similar-objects
public class ObjectUpdater {

	private static Map<Class, PropertyDescriptor[]> descriptorsMap = new HashMap<Class, PropertyDescriptor[]>();

	public static void updateObject(Object updateObject, Object originalObject)
			throws InvocationTargetException, IllegalAccessException {
		if (updateObject == null || originalObject == null) {
			throw new NullPointerException(
					"A null paramter was passed into updateObject");
		}

		// Only go through the process if the objects are not the same reference
		if (originalObject == updateObject) {
			return;
		}
		
		Class orignalClass = originalObject.getClass();
		Class updateClass = updateObject.getClass();

//		// you may want to work this check if you need to handle polymorphic
//		// relations
//		if (!orignalClass.equals(updateClass)) {
//			throw new IllegalArgumentException(
//					"Received parameters are not the same type of class, but must be");
//		}

		PropertyDescriptor[] descriptors = descriptorsMap.get(orignalClass);
		if (descriptors == null) {
			descriptors = PropertyUtils
					.getPropertyDescriptors(orignalClass);
			descriptorsMap.put(orignalClass, descriptors);
		}

		for (PropertyDescriptor descriptor : descriptors) {
			updateProperty(originalObject, updateObject, descriptor);
		}
		
	}

	private static void updateProperty(Object originalObject,
			Object updateObject, PropertyDescriptor descriptor)
			throws IllegalAccessException, InvocationTargetException {

		if (!PropertyUtils.isReadable(originalObject, descriptor.getName())){
			System.out.println("skip invisible field: " + descriptor.getName());
			return;
		}

		if (! PropertyUtils.isWriteable(originalObject,	descriptor.getName())) {
			System.out.println("skip read only field: " + descriptor.getName());
			return;
		}
		Method readMethod = descriptor.getReadMethod();
		Object value = readMethod.invoke(originalObject);
		if (value == null) {
			System.out.println("skip null value of orig: " + descriptor.getName());	
			return;
		}
		
		Method writeMethod = descriptor.getWriteMethod();
		writeMethod.invoke(updateObject, value);
		
	}
}

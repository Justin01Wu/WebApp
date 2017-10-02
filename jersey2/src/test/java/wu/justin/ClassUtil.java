package wu.justin;

import java.lang.annotation.Annotation;

public class ClassUtil {
	/**
	 * go back stack trace until we find last method on target annotation 
	 */
	public static Pair2<String, String> getTopMethodOnAnnotation(Class<? extends Annotation> annotationClass ) {

		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		String className = null, methodName = null;

		for (int i = 1; i < stacks.length; i++) {

			if(stacks[i].isNativeMethod()){
				continue;
			}
			String classNameA = stacks[i].getClassName();
			String methodNameA = stacks[i].getMethodName();
			//System.out.println(classNameA + "." + methodNameA);
			Class<?> clazz;
			
			try {
				clazz= loader.loadClass(classNameA);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("className is not found: " + classNameA, e);
			}

			if (clazz.isAnnotationPresent(annotationClass)) {
				className = classNameA;
				methodName = methodNameA;
			}
		}

		if (className == null) {
			return null;
		} else {
			return new Pair2<>(className, methodName);

		}

	}

}

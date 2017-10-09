package wu.justin.bean.generate;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import wu.justin.bean.DateConvert;
import wu.justin.rest2.ApiUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanGenerator {
	
	public static <T> T generate(Class<T> clazz) throws Exception {
		T t = clazz.newInstance();
		
		Method[] allMethods = clazz.getDeclaredMethods();
    	for (Method method : allMethods) {
    	    if (Modifier.isPublic(method.getModifiers())) {    	    	
    	        handleOneMethod(method, t);
    	    }
    	}
		return t;
	}
	
	private static void  handleOneMethod(Method method, Object t) throws Exception{
		if(!method.getName().startsWith("set")){
			return;
		}
		if( !method.getReturnType().equals(Void.TYPE)){
			return;
		}

		if(method.getParameters() == null){
			return;
		}
		if(method.getParameters().length  != 1){
			return;
		}
		
		Parameter parameter = method.getParameters()[0];
		
		Type[] types = method.getGenericParameterTypes();
		Object argOne = handleOneParameter(parameter, types);
		
		method.invoke(t, argOne);
		
	}
	
	private static Object handleOneParameter(Parameter parameter, Type[] types) throws Exception{
		Class<?> clazz  = parameter.getType();
		return handleBasicClass(clazz, types);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T handleBasicClass(Class<T> clazz, Type[] types) throws Exception{
		Date now = new Date();
		if(clazz.getName().equals("java.sql.Timestamp")){
			Timestamp  timestamp= new Timestamp(now.getTime());
			return (T)timestamp;
		}
		if(clazz.getName().equals("java.lang.String")){
			String string = new String( "a string");
			return (T)string;
		}
		if(clazz.getName().equals("java.lang.Integer")){
			Integer myInt = 12345;
			return (T)myInt;
		}
		if(clazz.getName().equals("int")){
			Integer myInt = 12345;
			return (T)myInt;
		}
		if(clazz.getName().equals("java.util.Date")){
			return (T)now;
		}
		if(clazz.getName().equals("java.sql.Date")){
			java.sql.Date sqlDate= new java.sql.Date(now.getTime());
			return (T)sqlDate;
		}
		
		if(clazz.getName().equals("java.util.Calendar")){
			Calendar cal = Calendar.getInstance();
			return (T)cal;
		}
		
		if(clazz.getName().equals("java.util.List")){
			
			ParameterizedType pType = (ParameterizedType) types[0];
			Class<?> pClazz = (Class<?>) pType.getActualTypeArguments()[0];
			System.out.println(pClazz); //prints out java.lang.Integer
			Object one = handleBasicClass(pClazz, null);
			
			java.util.List<Object> list = new ArrayList<>();
			T t =(T)list;
			list.add(one);
			return t;
		}
		
		
		return generate(clazz);

        
//		// others
//		try{
//			return clazz.newInstance();
//		}catch(Exception e){
//			System.out.println("can't get object from "+clazz.getName());
//			return null;
//		}

	}
	
	public static <T> void generateJson(Class<T> clazz) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		T obj = generate(clazz);
		String jsonInString = mapper.writeValueAsString(obj);
		
		System.out.println(jsonInString);
		
		String newFormat = ApiUtil.getFormatedJsonOrNull(jsonInString);
		
		System.out.println(newFormat);
	}
	
	public static void main(String[] args) throws Exception{
		
		generateJson(DateConvert.class);
		//generateJson(User.class);
		
		
	}
	
	
}

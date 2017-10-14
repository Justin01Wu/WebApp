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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import wu.justin.rest2.ApiUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanGenerator {
	
	private static Date NOW = new Date();
	
	private Map<Class<?>, BeanCreator> reigsteredClass = new HashMap<>();
	
	public BeanGenerator(){
		add(java.util.List.class);
		add(java.lang.Integer.class);
		add(java.lang.String.class);
		add(java.sql.Timestamp.class);
		add(java.util.Date.class);
		add(java.util.Calendar.class);
	}
	
	private void add(Class<?> clazz){
		reigsteredClass.put(clazz, null);
	}
	
	public void addExteralCreator(BeanCreator<?> creator){
		reigsteredClass.put(creator.getClazz(), creator);
	}
	
	public <T> T generate(Class<T> clazz) throws Exception {
		
		T t;
		if(reigsteredClass.keySet().contains(clazz)){
			t = handleBasicClass(clazz, null);
			return t;
		}
		
		t = clazz.newInstance();	
		
		Method[] allMethods = clazz.getDeclaredMethods();
    	for (Method method : allMethods) {
    	    if (Modifier.isPublic(method.getModifiers())) {    	    	
    	        handleOneMethod(method, t);
    	    }
    	}
		return t;
	}
	
	private void  handleOneMethod(Method method, Object t) throws Exception{
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
	
	private Object handleOneParameter(Parameter parameter, Type[] types) throws Exception{
		Class<?> clazz  = parameter.getType();
		return handleBasicClass(clazz, types);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T handleBasicClass(Class<T> clazz, Type[] types) throws Exception{		
		
		BeanCreator customizedCreator = reigsteredClass.get(clazz);
		if(customizedCreator != null){
			Object o = customizedCreator.createBean();
			return (T)o;
		}
		
		if(Enum.class.isAssignableFrom(clazz)){
			return clazz.getEnumConstants()[0];
		}
		if(clazz.getName().equals("java.sql.Timestamp")){
			Timestamp  timestamp= new Timestamp(NOW.getTime());
			return (T)timestamp;
		}
		if(clazz.getName().equals("java.lang.String")){
			String string = new String( "a string");
			return (T)string;
		}
		if(clazz.getName().equals("java.lang.Integer") || clazz.getName().equals("int")){
			Integer myInt = 12345;
			return (T)myInt;
		}

		if(clazz.getName().equals("double") || clazz.getName().equals("java.lang.Double")){
			Double myDouble = 12345.6789d;
			return (T)myDouble;
		}
		
		if(clazz.getName().equals("java.lang.Boolean") || clazz.getName().equals("boolean")){
			Boolean myBoolean = true;;
			return (T)myBoolean;
		}		
		
		if(clazz.getName().equals("java.util.Date")){
			return (T)NOW;
		}
		if(clazz.getName().equals("java.sql.Date")){
			java.sql.Date sqlDate= new java.sql.Date(NOW.getTime());
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
		
		if(clazz.getName().equals("java.util.Set")){
			ParameterizedType pType = (ParameterizedType) types[0];
			Class<?> pClazz = (Class<?>) pType.getActualTypeArguments()[0];
			System.out.println(pClazz); //prints out java.lang.Integer
			Object one = handleBasicClass(pClazz, null);
			
			java.util.Set<Object> set = new HashSet<>();
			T t =(T)set;
			set.add(one);
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
	
	// TODO should move this method out of this class.
	public <T> String generateJson(Class<T> clazz) throws Exception{
		
		T obj = generate(clazz);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(obj);
		
		//System.out.println(jsonInString);
		
		String newFormat = ApiUtil.getFormatedJsonOrNull(jsonInString);
		
		//System.out.println(newFormat);
		return newFormat;
	}
	
}

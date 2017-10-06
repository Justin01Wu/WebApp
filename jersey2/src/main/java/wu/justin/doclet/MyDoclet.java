package wu.justin.doclet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

import freemarker.template.TemplateException;

/**
  this is a doclet to generate restful Api docs
  it will collect integration result for sample
*/
public class MyDoclet {
	
	private static String Prefix;
	
	private static TestResultHandler handler;
	
	private static List<ApiEntry> allApis = new ArrayList<ApiEntry>();
	
	private static List<ApiClassEntry> allApiClass = new ArrayList<ApiClassEntry>();
	
	
	public static boolean start(RootDoc root){
		
		Prefix = getUrlRoot();  // it has a bug, caller can't pass prefix in, so hard code it now
		Prefix ="/api";  // TODO remove hard code
		
		String[][] options =root.options();
		for(String[] list : options){
			for(String one: list){
				System.out.print(one);
				System.out.print(", ");
			}
			System.out.println("");
		}

		printClassPath();
		
		String location = System.getProperty("integration.test.result");
		if (location == null) {
			System.out.println("didn't find integration.test.result, use default value");
			location = "C:/samples/WebApp/WebApp/jersey2/target/test-output";
		} 

		handler = new TestResultHandler(Prefix);
		handler.loadTestResults(location);
        
        for( ClassDoc aClass : root.classes() ){
        	handleOneClass(aClass);
        }        
        
        for(ApiClassEntry oneClass: allApiClass){
        	if(oneClass.getApis() != null){
        		allApis.addAll(oneClass.getApis());	
        	}        	
        }
        
        File output = new File("C:/samples/WebApp/WebApp/jersey2/target/ApiDoc.html");
        try {
            FileOutputStream out = new FileOutputStream(output);
			ApiHtmlCreator.create(allApis, allApiClass, out);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
        return true;
    
	}
	
	public static String getUrlRoot() {

		String port = System.getProperty("maven.tomcat.port");
		if (port == null) {
			System.out.println("didn't find maven.tomcat.port, use default value 8080");
			port = "12001";
		} else {
			try {
				Integer.valueOf(port);
			} catch (NumberFormatException e) {
				System.err.println("wrong maven.tomcat.port parameter: " + port);
				port = "8080";
			}
		}

		final String URL_ROOT = "http://localhost:" + port + "/jersey2/api";
		return URL_ROOT;

	}
	
	private static void handleOneClass(ClassDoc aClass){
    	AnnotationDesc[] annotations = aClass.annotations();
    	
    	boolean isRESTfulAPI = false;
    	for(AnnotationDesc desc: annotations){
    		//System.out.println("annotation= "+desc);
    		AnnotationTypeDoc aDoc = desc.annotationType();
    		String myType = aDoc.toString();
    		if("javax.ws.rs.Path".equals(myType)){
    			isRESTfulAPI = true;
    			break;
    		}
    		//System.out.println("aDoc= " + aDoc);
    		
    		//System.out.println("aDoc class= " + aDoc.getClass().getName());
    	}
    	if(!isRESTfulAPI){
    		return;
    	}
    	
    	System.out.println("");
    	System.out.println("=============  start of " + aClass.qualifiedTypeName() + "======================================================   ");
    	System.out.println("  qualifiedName = "  + aClass.qualifiedName());
    	//System.out.println("  name = " + aClass.name());
    	System.out.println("  commentText = " + aClass.commentText());
    	//System.out.println("  rawCommentText = " + aClass.getRawCommentText());
    	//System.out.println("  dimension = " + aClass.dimension());
    	System.out.println("  qualifiedTypeName = "+aClass.qualifiedTypeName());
    	System.out.println("  simpleTypeName = " + aClass.simpleTypeName());
		
    	Class<?> clazz ;
    	try {
    		clazz = Thread.currentThread().getContextClassLoader().loadClass(aClass.qualifiedTypeName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
    	
    	ApiClassEntry oneClass = new ApiClassEntry(clazz);
    	
    	Path[] myPaths = clazz.getAnnotationsByType(javax.ws.rs.Path.class);
    	String root = myPaths[0].value();
    	
    	System.out.println("  url root = " + root);   	
    	
    	oneClass.setUrl(root);    	
    	
    	Method[] allMethods = clazz.getDeclaredMethods();
    	for (Method method : allMethods) {
    	    if (Modifier.isPublic(method.getModifiers())) {    	    	
    	        handleOneMethod(method,root, oneClass, aClass);
    	    }
    	}
    	allApiClass.add(oneClass);
    	System.out.println("=============  end of " + aClass.qualifiedTypeName() + "======================================================   ");
    	System.out.println("");

    	
	}
	
	private static void handleOneMethod(Method method, String root, ApiClassEntry apiClass, ClassDoc aClass){
		
		
		Path myPath = method.getDeclaredAnnotation(javax.ws.rs.Path.class);
		if(myPath == null){
			// it is not API method
			return;
		}

		MethodDoc[] methodDocs = aClass.methods();
		MethodDoc myMethodDoc = null;
		for(MethodDoc methodDoc : methodDocs){
			if(methodDoc.name().equals(method.getName())){
				myMethodDoc = methodDoc;
				break;
			}			
			//System.out.println(methodDoc.name());
		}
		
		String methodPath  = myPath.value();
		String fullPath = root + methodPath; 
		String SimpleName =  apiClass.getName()+ "."+ method.getName();
		
		System.out.println("         -----------------  start of " + SimpleName + "------------------------ ");
		
		System.out.println("         url for method " +SimpleName + " is " + fullPath);
		if(myMethodDoc != null){
			if(myMethodDoc.commentText() != null && !myMethodDoc.commentText().isEmpty()){
				System.out.println("         comment: " + myMethodDoc.commentText());	
			}
		}
		
		
		String httpMethod = findHttpMethod(method);
		
		Class<?> returnType = method.getReturnType();
		if(returnType != null){
			System.out.println("         it's return is "+ returnType.getSimpleName());
		}
		
		System.out.println("         it is HTTP "+ httpMethod +" method " );
		Parameter[]  parameters = method.getParameters();
		for(int i=0;i<parameters.length ;i++){
			handleOneParameter(parameters[i], myMethodDoc,i);
		}
		
		String filePath = handler.findResultFile(fullPath, httpMethod);
		if(filePath!= null){
			File file = new File(filePath);
			System.out.println("found test result on: " + file.getAbsolutePath());
			String jsonStr = null; ;
			try {
				jsonStr = TestResultHandler.getJsonFile(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(jsonStr);
		};
    	System.out.println("         -----------------  end of " + SimpleName + "------------------------ ");
    	System.out.println("");
    	
    	ApiEntry oneEnrty = new ApiEntry(httpMethod, fullPath, apiClass.getFullName(), method.getName());
    	
    	apiClass.addApis(oneEnrty);    	
	}
	
	private static void handleOneParameter(Parameter parameter, MethodDoc myMethodDoc, int i){
		com.sun.javadoc.Parameter[] parameterDocs = myMethodDoc.parameters();
		
		Class<?> clazz =  parameter.getType();
		
		com.sun.javadoc.Parameter myParameterDoc = parameterDocs[i];
		
		String parameterDesc = "parameter[" + i + "] is " + myParameterDoc.name() + ", type is " + clazz.getSimpleName();		
		
		QueryParam myQueryParam = parameter.getAnnotation(QueryParam.class);
		if(myQueryParam != null){
			parameterDesc = parameterDesc + ", it is query parameter which name is " + myQueryParam.value();
		}
		System.out.println("             " + parameterDesc);
	}
	
	private static String findHttpMethod(Method method){
		GET[] myGETs = method.getAnnotationsByType(GET.class);
		if(myGETs.length >0){
			return "GET";
		}else{
			POST[] myPOSTs = method.getAnnotationsByType(POST.class);
			if(myPOSTs.length >0){
				
				return "POST";
			}else{
				PUT[] myPUTs = method.getAnnotationsByType(PUT.class);
				if(myPUTs.length >0){
					return "PUT";
				}else{
					DELETE[] myDELETEs = method.getAnnotationsByType(DELETE.class);
					if(myDELETEs.length >0){
						return "DELETE";
					}
				}
			}
		}
		return "UNKNOWN";
	}
	


	
	private static void printClassPath(){
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		URL[] urls = ((URLClassLoader) cl).getURLs();

		System.out.println("   ==>>   current classpath for doclet:");
		for (URL url : urls) {
			System.out.println( "               " + url.getFile());
		}
		
	}
	
	public static void main(String[] args) {
		
		// call my self as doclet:

		System.setProperty("integration.test.result","C:/samples/WebApp/WebApp/jersey2/target/test-output");
		System.setProperty("maven.tomcat.port","12001");
		
		
		String sourcePath = "C:/samples/WebApp/WebApp/jersey2/src/main/java/";
		//String sourcePath = "C:/projects/WebApp/WebApp/jersey2/src/main/java/";
		String subpackages  = "wu.justin.rest2";
		String[] myArgs = { "-doclet", "wu.justin.doclet.MyDoclet",
				"-sourcepath", sourcePath, "-subpackages", subpackages };

		com.sun.tools.javadoc.Main.execute(myArgs);
	}

}

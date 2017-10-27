package wu.justin.doclet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

import freemarker.template.TemplateException;
import wu.justa.utils.BeanGenerator;
import wu.justin.rest2.ApiUtil;

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
		
		System.out.println("");
		System.out.println("========================= start MyDoclet===========================");
		
		printEnv(root);
		
		Prefix = System.getProperty("restful.api.prefix");
		if (Prefix == null) {
			System.out.println("didn't find restful.api.prefix, use default value 'api'");
			Prefix = "/api";
		}
		
		String testResultFolder = System.getProperty("integration.test.result");
		if (testResultFolder == null) {
			System.out.println("didn't find integration.test.result");
			return false;
		}		
		System.out.println("testResultFolder= "+ testResultFolder);

		String outputPath = System.getProperty("restful.doc.output.path");
		if (outputPath == null) {
			System.out.println("didn't find restful.doc.output.path");
			return false;
		}	
		System.out.println("generating file: " + outputPath);
		
		handler = new TestResultHandler(Prefix, testResultFolder);
        
        for( ClassDoc aClass : root.classes() ){
        	handleOneClass(aClass);
        }        
        
        for(ApiClassEntry oneClass: allApiClass){
        	if(oneClass.getApis() != null){
        		allApis.addAll(oneClass.getApis());	
        	}        	
        }
        
        
        File output = new File(outputPath);
        try {
            FileOutputStream out = new FileOutputStream(output);
			ApiHtmlCreator.create(allApis, allApiClass, out);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
        return true;
    
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
//    	System.out.println("  qualifiedName = "  + aClass.qualifiedName());
//    	//System.out.println("  name = " + aClass.name());
//    	System.out.println("  commentText = " + aClass.commentText());
//    	//System.out.println("  rawCommentText = " + aClass.getRawCommentText());
//    	//System.out.println("  dimension = " + aClass.dimension());
//    	System.out.println("  qualifiedTypeName = "+aClass.qualifiedTypeName());
//    	System.out.println("  simpleTypeName = " + aClass.simpleTypeName());
		
    	Class<?> clazz ;
    	try {
    		clazz = Thread.currentThread().getContextClassLoader().loadClass(aClass.qualifiedTypeName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("can't find a class", e);
		}
    	
    	ApiClassEntry oneClass = new ApiClassEntry(clazz);
    	oneClass.setComment(aClass.getRawCommentText());
    	
    	Path[] myPaths = clazz.getAnnotationsByType(javax.ws.rs.Path.class);
    	String root = myPaths[0].value();
    	
    	//System.out.println("  url root = " + root);   	
    	
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
	
	private static String generateJson(Object obj) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(obj);
		
		//System.out.println(jsonInString);
		
		String newFormat = ApiUtil.getFormatedJsonOrNull(jsonInString);
		
		//System.out.println(newFormat);
		return newFormat;

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
		
		String comment = null;
		if(myMethodDoc != null){
			comment = myMethodDoc.getRawCommentText();
		}		
		
		String httpMethod = findHttpMethod(method);
		
		Class<?> returnType = method.getReturnType();
		if(returnType != null){
			System.out.println("         it's return is "+ returnType.getSimpleName());
		}
		
		System.out.println("         it is HTTP "+ httpMethod +" method " );
		
		ApiEntry oneEntry = new ApiEntry(httpMethod, fullPath, apiClass.getFullName(), method.getName());
		oneEntry.setComment(comment);
		
		Parameter[]  parameters = method.getParameters();
		for(int i=0;i<parameters.length ;i++){
			com.sun.javadoc.Parameter[] parameterDocs = myMethodDoc.parameters();
			com.sun.javadoc.Parameter myParameterDoc = parameterDocs[i];
			handleOneParameter(parameters[i], oneEntry, myParameterDoc);
		}
		
		List<TestResult> results  = handler.findResultFiles(fullPath, httpMethod, oneEntry);
		if(results.isEmpty()){
			if( !method.getReturnType().equals(Void.TYPE)){
				String returnJson = null;
				Type type = method.getGenericReturnType();
				try {
					Object obj = new BeanGenerator().generate(method.getReturnType(), type);
					returnJson = generateJson(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(returnJson!= null){
					 results  = new ArrayList<>();
					 TestResult oneResult =  new TestResult(httpMethod, "200", fullPath, "unknown", "unknown", "unknown");
					 oneResult.setJson(returnJson);
					 oneEntry.addResult(oneResult);
				}
			}

		}else{
			for(TestResult oneResult: results){
				String filePath = oneResult.getFilePath();
				File file = new File(filePath);
				System.out.println("found test result on: " + file.getAbsolutePath());
				String jsonStr = null; ;
				try {
					jsonStr = TestResultHandler.getJsonFile(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				oneResult.setJson(jsonStr);
				System.out.println(jsonStr);
				oneEntry.addResult(oneResult);

			}
		}
		


    	System.out.println("         -----------------  end of " + SimpleName + "------------------------ ");
    	System.out.println("");
    	
    	
    	
    	apiClass.addApis(oneEntry);    	
	}
	
	private static void handleOneParameter(Parameter parameter, ApiEntry method, com.sun.javadoc.Parameter myParameterDoc ){
		
		Class<?> clazz =  parameter.getType();		
		
		String type = "";
		String name =  myParameterDoc.name();
		QueryParam myQueryParam = parameter.getAnnotation(QueryParam.class);
		if(myQueryParam != null){
			type = "QueryParam";
			name = myQueryParam.value();
		}else{
			PathParam myPathParam = parameter.getAnnotation(PathParam.class);
			if(myPathParam != null){
				type = "PathParam";
				name = myPathParam.value();
			}
		}
		
		ParameterEntry parameterEntry = new ParameterEntry(name, clazz.getSimpleName(), type);
		method.addParameter(parameterEntry);
		
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
	


	
	private static void printEnv(RootDoc root){
		
		String[][] options = root.options();
		for(String[] list : options){
			for(String one: list){
				System.out.print(one);
				System.out.print(", ");
			}
			System.out.println("");
		}
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		URL[] urls = ((URLClassLoader) cl).getURLs();

		System.out.println("   ==>>   current classpath for doclet:");
		for (URL url : urls) {
			System.out.println( "               " + url.getFile());
		}
		
	}
	
	public static void main(String[] args) {
		
		// call my self as doclet:
		
		//String projectDir = "C:/projects/WebApp/WebApp/jersey2";
		String projectDir = "C:/samples/WebApp/WebApp/jersey2";
		
		System.setProperty("integration.test.result", projectDir + "/target/test-output");
		System.setProperty("restful.doc.output.path", projectDir + "/target/apiDoc.html");		
		System.setProperty("maven.tomcat.port","12001");		
		System.setProperty("restful.api.prefix","/api");	
		
		String sourcePath = projectDir + "/src/main/java/";  // this can support multiple paths
		String subpackages  = "wu.justin.rest2";     // also search sub packages
		String[] myArgs = { "-doclet", MyDoclet.class.getName(), 
				"-sourcepath", sourcePath, "-subpackages", subpackages };

		com.sun.tools.javadoc.Main.execute(myArgs);
	}

}

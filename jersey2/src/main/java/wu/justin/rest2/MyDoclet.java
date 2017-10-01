package wu.justin.rest2;

import java.net.URL;
import java.net.URLClassLoader;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

/**
this is a doclet 

*/
public class MyDoclet {
	
	public static boolean start(RootDoc root){
		
		printClassPath();
        
        for( ClassDoc aClass : root.classes() ){
        	handleOneClass(aClass);
        }
        return true;
    
	}
	
	private static void handleOneClass(ClassDoc aClass){
    	AnnotationDesc[] annotations = aClass.annotations();
    	
    	boolean isRESTfulAPI = false;
    	for(AnnotationDesc desc: annotations){
    		System.out.println("annotation= "+desc);
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
    	System.out.println("  qualifiedName = "  + aClass.qualifiedName());
    	System.out.println("  name = " + aClass.name());
    	System.out.println("  commentText = " + aClass.commentText());
    	System.out.println("  rawCommentText = " + aClass.getRawCommentText());
    	System.out.println("  dimension = " + aClass.dimension());
    	System.out.println("  qualifiedTypeName = "+aClass.qualifiedTypeName());
    	System.out.println("  simpleTypeName = " + aClass.simpleTypeName());
    	System.out.println("=============  end ======================================================   ");
    	System.out.println("");
		
	}
	
	private static void printClassPath(){
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		URL[] urls = ((URLClassLoader) cl).getURLs();

		for (URL url : urls) {
			System.out.println(url.getFile());
		}
		
	}
	
	public static void main(String[] args) {

		// String sourcePath = "C:/samples/WebApp/WebApp/jersey2/src/main/java/";
		String sourcePath = "C:/projects/WebApp/WebApp/jersey2/src/main/java/";
		String packageName1 = "wu.justin.rest2";
		String packageName2 = "wu.justin.rest2.user";
		String[] myArgs = { "-doclet", "wu.justin.rest2.MyDoclet",
				"-sourcepath", sourcePath, packageName1, packageName2 };

		com.sun.tools.javadoc.Main.execute(myArgs);
	}

}

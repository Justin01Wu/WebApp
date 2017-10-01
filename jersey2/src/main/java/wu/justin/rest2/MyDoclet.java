package wu.justin.rest2;

import java.net.URL;
import java.net.URLClassLoader;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

/**
this is a doclet, please use this command to run it:

javadoc -doclet ca.justa.doclet.MyDoclet -docletpath .\target\justin-doclet.jar C:\samples\JavaFrame\JavaFrame\scjp\src\main\java\scjp\testclass\inherit\HughJampton.java
javadoc -doclet ca.justa.doclet.MyDoclet -docletpath .\target\justin-doclet.jar C:\samples\WebApp\WebApp\jersey2\src\main\java\wu\justin\rest2\PublicApi.java
*/
public class MyDoclet {
	
	public static boolean start(RootDoc root){
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		URL[] urls = ((URLClassLoader) cl).getURLs();

		for (URL url : urls) {
			System.out.println(url.getFile());
		}
		
        
        for( ClassDoc aClass : root.classes() ){
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
        		continue;
        	}
        	System.out.println("     =======    ");

        	System.out.println("  qualifiedName = "  + aClass.qualifiedName());
        	System.out.println("  name = " + aClass.name());
        	System.out.println("  commentText = " + aClass.commentText());
        	System.out.println("  rawCommentText = " + aClass.getRawCommentText());
        	System.out.println("  dimension = " + aClass.dimension());
        	System.out.println("  qualifiedTypeName = "+aClass.qualifiedTypeName());
        	System.out.println("  simpleTypeName = " + aClass.simpleTypeName());

        }
        return true;
    
	}

}

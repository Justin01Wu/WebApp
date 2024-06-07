package wu.justin.doclet;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.ws.rs.Path;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class ApiDocs implements Doclet {
    @Override
    public void init(Locale locale, Reporter reporter) {  }
 
    @Override
    public String getName() {
        // For this doclet, the name of the doclet is just the
        // simple name of the class. The name may be used in
        // messages related to this doclet, such as in command-line
        // help when doclet-specific options are provided.
        return getClass().getSimpleName();
    }
 
    @Override
    public Set<? extends Option> getSupportedOptions() {
        // This doclet does not support any options.
        return Collections.emptySet();
        //	return new StandardDoclet().getSupportedOptions();
    }
 
    @Override
    public SourceVersion getSupportedSourceVersion() {
        // This doclet supports all source versions.
        // More sophisticated doclets may use a more
        // specific version, to ensure that they do not
        // encounter more recent language features that
        // they may not be able to handle.
        return SourceVersion.latest();
    }
 
    private static final boolean OK = true;
    
    private static void handleOneClass(TypeMirror aClass){
    	List<? extends AnnotationMirror> list = aClass.getAnnotationMirrors();
    	for(AnnotationMirror m : list) {
    		System.out.println("     AnnotationType = " + m.getAnnotationType());
    	
    	}
    }
 
    @Override
    public boolean run(DocletEnvironment docEnv) {
        // This method is called to perform the work of the doclet.
        // In this case, it just prints out the names of the
        // elements specified on the command line.
    	
   	
    	Set<? extends Element> elements;
    	
//    	elements = environment.getIncludedElements();
//    	print(elements);
    	
    	elements = docEnv.getSpecifiedElements();
    	print(elements);
    	
    	
    	// get the DocTrees utility class to access document comments
        DocTrees docTrees = docEnv.getDocTrees();

        for (TypeElement t : ElementFilter.typesIn(docEnv.getIncludedElements())) {
            
        	List<? extends AnnotationMirror> annotations = t.getAnnotationMirrors();
        	boolean isRESTfulAPI = false;
			for(AnnotationMirror one: annotations) {
				//System.out.println("      Annotation: " + one.getAnnotationType());
	    		String myType = one.getAnnotationType().toString();
	    		if("javax.ws.rs.Path".equals(myType)){
	    			isRESTfulAPI = true;
	    			break;
	    		}
			}
			if(isRESTfulAPI) {

	            Path[] myPaths = t.getAnnotationsByType(javax.ws.rs.Path.class);
	            String rootPath = myPaths[0].value();
				System.out.println(t.getKind() + ":" + t + " API path:" + rootPath);
	            for (Element e : t.getEnclosedElements()) {
	                printElement(docTrees, e, rootPath);
	            }
			}

        }
        return OK;
    }
    
    public void printElement(DocTrees trees, Element e, String rootPath) {
    	
    	
    	if(e.getKind() == ElementKind.METHOD) {
    		
    		Set<Modifier> modifiers  = e.getModifiers();
    		if(modifiers.contains(Modifier.PUBLIC)) {
    			

	            Path[] myPaths = e.getAnnotationsByType(javax.ws.rs.Path.class);
	            
	            if(myPaths.length==0) {
	            	return;
	            }
	            String apiUrl = rootPath;
	            if(rootPath.endsWith("/") || myPaths[0].value().startsWith("/")) {
	            	apiUrl = rootPath + myPaths[0].value();	
	            }else {
	            	apiUrl = rootPath + "/" + myPaths[0].value();
	            }
	            

	            System.out.println("  Element (" + e.getKind() + ": "  + e + "), apiUrl = " + apiUrl );
                DocCommentTree docCommentTree = trees.getDocCommentTree(e);
                if (docCommentTree != null) {
                    System.out.println("      Entire body: " + docCommentTree.getFullBody());
                    System.out.println("      Block tags: " + docCommentTree.getBlockTags());
                }   
    		}
 		
    	}
    }
    
    
    void print(Set<? extends Element> elements){
        for(Element t : ElementFilter.typesIn(elements)) {
            System.out.println(   "   t = " + t.getSimpleName().toString());
        }
        for(Element p : ElementFilter.packagesIn(elements)) {
        	System.out.println("   p = " + p.getSimpleName().toString());
        }
    	
    	//Set<TypeElement> typeElements = ElementFilter.typesIn(elements);
    	int i=0;
    	Set<PackageElement> packageElements = ElementFilter.packagesIn(elements);
        for(PackageElement packageElement : packageElements) {            
        	List<? extends Element> typeElements = packageElement.getEnclosedElements();
        	for(Element t : typeElements) {       
        		System.out.println(t.getSimpleName().toString());
        		handleOneClass(t.asType());
        		i++;
        	}            
        }
        System.out.println("     total  = "+ i);
    }
        

}
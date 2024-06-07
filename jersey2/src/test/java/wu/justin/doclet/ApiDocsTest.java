package wu.justin.doclet;

import javax.tools.DocumentationTool;
import javax.tools.ToolProvider;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import wu.justin.ManualTest;

@Category(ManualTest.class)
public class ApiDocsTest {
	

	/**
	 * subpackages can only accept one package, not a list of comma or semi-common
	 * the root  of the package must have java classes, it won't scan sub packages
	 */
	@Test
	public void testApiDocs() {
		String sourcePath =  "src/main/java";
		
		//String projectDir = "C:/projects/vcaps/jdk21/vcaps-svn-git/main";
		//String sourcePath =  projectDir + "/shared-java/src/main/java";
		//sourcePath = sourcePath + ";" + projectDir + "/dnf/src/main/java";
		
		// sourcePath can support multiple paths

		//  "wu.justin.rest2:wu.justin.rest3"
		String subpackages  = "wu.justin.rest2";
		String[] myArgs = { 
				"-doclet", ApiDocs.class.getName(), 
				"-docletpath", "target/classes/", 
				"-sourcepath", sourcePath,  subpackages };

		DocumentationTool docTool = ToolProvider.getSystemDocumentationTool();
		docTool.run(System.in, System.out, System.err, myArgs);
	}

}

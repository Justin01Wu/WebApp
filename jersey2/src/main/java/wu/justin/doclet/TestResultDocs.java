package wu.justin.doclet;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.tools.DocumentationTool;
import javax.tools.ToolProvider;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

//collect all unit and integration result and convert into two JSON files dto-json-list.json,
//apiCaseCovers.json
public class TestResultDocs implements Doclet {

	@Override
	public void init(Locale locale, Reporter reporter) {
		// nothing to initialize
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public Set<? extends Option> getSupportedOptions() {
		return Collections.emptySet();
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	@Override
	public boolean run(DocletEnvironment environment) {
		ApiCaseCoverCreator.create();

		return true;
	}

	public static void main(String[] args) {

		// original design is running doclet outside of main project
		// but now we put it inside the main project,
		// so we don't need integration.test.result.input and
		// integration.test.result.output system
		// properties
		// TODO remove them
		System.setProperty("integration.test.result.input", "D:\\samples\\webApp\\WebApp\\jersey2\\target\\test-input");
		System.setProperty("integration.test.result.output", "D:\\samples\\webApp\\WebApp\\jersey2\\target\\test-output");
		String sourcePath = "src/main/java";
		String subpackages = TestResultDocs.class.getPackageName();

		String[] myArgs = { "-doclet", TestResultDocs.class.getName(), "-docletpath", "target/classes/", "-sourcepath",
				sourcePath, subpackages };

		DocumentationTool docTool = ToolProvider.getSystemDocumentationTool();
		docTool.run(System.in, System.out, System.err, myArgs);
	}
}

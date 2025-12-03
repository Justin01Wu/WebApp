package wu.justin.doclet;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import wu.justin.rest2.ApiUtil;

// For details, please see https://docs.oracle.com/en/java/javase/11/docs/api/jdk.javadoc/jdk/javadoc/doclet/package-summary.html#migration
public class ApiDocs implements Doclet {

	@Override
	public void init(Locale locale, Reporter reporter) {
	}

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
		// return new StandardDoclet().getSupportedOptions();
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

	@Override
	public boolean run(DocletEnvironment docEnv) {

		DTO2JsonListCreator.create();
		return new ApiFromCreator().create(docEnv);
	}

}
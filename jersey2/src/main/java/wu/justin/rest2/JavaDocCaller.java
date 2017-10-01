package wu.justin.rest2;

import java.net.URL;
import java.net.URLClassLoader;

public class JavaDocCaller {

	public static void main(String[] args) {

		ClassLoader cl = ClassLoader.getSystemClassLoader();

		URL[] urls = ((URLClassLoader) cl).getURLs();

		for (URL url : urls) {
			System.out.println(url.getFile());
		}

		// String sourcePath = "C:/samples/WebApp/WebApp/jersey2/src/main/java/";
		String sourcePath = "C:/projects/WebApp/WebApp/jersey2/src/main/java/";
		String packageName1 = "wu.justin.rest2";
		String packageName2 = "wu.justin.bean";
		String[] myArgs = { "-doclet", "wu.justin.rest2.MyDoclet",
				"-sourcepath", sourcePath, packageName1, packageName2 };

		com.sun.tools.javadoc.Main.execute(myArgs);
	}

}

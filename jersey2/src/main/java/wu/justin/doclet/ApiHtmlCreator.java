package wu.justin.doclet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

public class ApiHtmlCreator {
	
	public static void create(List<ApiEntry> allApis, List<ApiClassEntry> allApiClass, TestResultHandler handler, OutputStream out) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{
		// 1. Configure FreeMarker
        //
        // You should do this ONLY ONCE, when your application starts,
        // then reuse the same Configuration object elsewhere.

        Configuration cfg = new Configuration(new Version(2, 3, 20));

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(ApiHtmlCreator.class, "templates");

        // Some other recommended settings:
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 2. Proccess template(s)
        //
        // You will do this for several times in typical applications.

        // 2.1. Prepare the template input:

        Map<String, Object> input = new HashMap<String, Object>();

        input.put("title", "RESTful Api list");
        
        input.put("lastUpdated", new Date().toString());

        input.put("allApis", allApis);
        input.put("allApiClass", allApiClass);
        
        List<TestResult> testResults  = getCombinedResult(handler);
        input.put("testResults", testResults);

        // 2.2. Get the template

        Template template = cfg.getTemplate("api_template.html");

        // 2.3. Generate the output

        // Write output 
        Writer consoleWriter = new OutputStreamWriter(out);
        template.process(input, consoleWriter);

    }
	
	private static List<TestResult> getCombinedResult(TestResultHandler handler){
        Map<String, TestResult> combinedResults =  new HashMap<>();
        // convert to caseName result map from fileName result map 
        
        Map<String, TestResult> output = handler.getAllTestResults();
        Map<String, TestResultInput> in = handler.getAllTestInputResults();
        for(String key: output.keySet()) {
        	TestResult one = output.get(key);
        	one.setOutput(one.getJson());
        	one.setJson(null);
        	combinedResults.put(one.getCaseName(), one);
        }
        
        for(String key: in.keySet()) {
        	TestResultInput oneIn = in.get(key);
        	TestResult oneOut = combinedResults.get(oneIn.getCaseName());
        	if(oneOut != null) {
        		oneOut.setJson(oneIn.getJson());
        	}
        }

        List<TestResult> testResults = new ArrayList<>(combinedResults.values());
        
        testResults.sort( Comparator.comparing( TestResult::getCaseName ) ); 
        
        return testResults;

	}


}

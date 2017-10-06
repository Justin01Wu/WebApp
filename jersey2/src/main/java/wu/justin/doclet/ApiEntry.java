package wu.justin.doclet;

import java.util.ArrayList;
import java.util.List;

public class ApiEntry {

	private String httpMethod;
	private String url;
	private String className;
	private String methodName;
	private List<TestResult> results = new ArrayList<>(); 

	public ApiEntry(String httpMethod, String url, String className, String methodName) {
		super();
		this.httpMethod = httpMethod;
		this.url = url;
		this.className = className;
		this.methodName = methodName;
	}
	
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<TestResult> getResults() {
		return results;
	}

	public void setResults(List<TestResult> results) {
		this.results = results;
	}
	
	public void addResult(TestResult result) {
		results.add(result);
	}
	
}

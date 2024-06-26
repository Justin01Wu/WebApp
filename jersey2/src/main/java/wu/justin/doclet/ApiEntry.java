package wu.justin.doclet;

import java.util.ArrayList;
import java.util.List;

public class ApiEntry implements Comparable<ApiEntry> {
	private String httpMethod;
	private String url;
	private String className;
	private String methodName;
	private String comment;
	private String blockComment;
	private boolean caseCovered= false;
	private List<TestResult> results = new ArrayList<>(); 
	private List<TestResultInput> inputs = new ArrayList<>();
	
	private List<ParameterEntry> parameters = new ArrayList<>();
	

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
	
	public List<TestResultInput> getInputs() {
		return inputs;
	}

	public void setInput(List<TestResultInput> inputs) {
		this.inputs = inputs;
	}	
	
	public void addInput(TestResultInput result) {
		inputs.add(result);
	}

	public List<ParameterEntry> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterEntry> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(ParameterEntry parameter) {
		parameters.add(parameter);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
    public int compareTo(ApiEntry another) {
        String url2 = another.getUrl();
        /* For Ascending order*/
        return this.url.compareTo(url2);

    }

	public boolean isCaseCovered() {
		return caseCovered;
	}

	public void setCaseCovered(boolean caseCovered) {
		this.caseCovered = caseCovered;
	}

	public String getBlockComment() {
		return blockComment;
	}

	public void setBlockComment(String blockComment) {
		this.blockComment = blockComment;
	}

}

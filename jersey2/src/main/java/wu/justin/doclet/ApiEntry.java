package wu.justin.doclet;

public class ApiEntry {
	
	public ApiEntry(String httpMethod, String url, String className, String methodName) {
		super();
		this.httpMethod = httpMethod;
		this.url = url;
		this.className = className;
		this.methodName = methodName;
	}
	private String httpMethod;
	private String url;
	private String className;
	private String methodName;
	
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
	
}

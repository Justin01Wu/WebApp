package wu.justin.doclet;

public class TestResultInput {
	protected String method;
	protected String url;
	protected String json;
	protected String filePath;
	protected String caseName= "CodeReflection";
	
	public TestResultInput(String method, String url) {
		super();
		this.method = method;
		this.url = url;		
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "TestResultBase [method=" + method + ", url=" + url + "]";
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		if(filePath!= null && filePath.endsWith(".txt")) {
			String first = filePath.substring(0, filePath.length()-4 );
			String[] segs = first.split("\\\\");
			String myCase = segs[segs.length-1];
			segs = myCase.split("_");
			if(segs.length == 3 ) {
				caseName = segs[0]+ "."+ segs[1];
			}
		}
	}

	public String getCaseName() {
		return caseName;
	}

}

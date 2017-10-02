package wu.justin.bean;

public class TestResult {
	
	
	public TestResult(String method, String status, String url, String cost, String start, String end) {
		super();
		this.method = method;
		this.status = status;
		this.url = url;
		this.cost = cost;
		this.start = start;
		this.end = end;
	}
	
	private String method;
	private String status;
	private String url;
	private String cost;
	private String start;
	private String end;
	
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	@Override
	public String toString() {
		return "TestResult [method=" + method + ", status=" + status + ", url=" + url + ", cost=" + cost + ", start="
				+ start + ", end=" + end + "]";
	}
}

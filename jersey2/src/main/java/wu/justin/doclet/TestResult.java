package wu.justin.doclet;

public class TestResult extends TestResultInput {

	private String status;
	private String cost;
	private String start;
	private String end;
	
	public TestResult(String method, String status, String url, String cost, String start, String end) {
		super(method, url);
		this.status = status;
		this.cost = cost;
		this.start = start;
		this.end = end;		
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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

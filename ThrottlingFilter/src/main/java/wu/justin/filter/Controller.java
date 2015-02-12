package wu.justin.filter;

public class Controller implements ControllerMBean {
	
	private boolean errorQueue = false;
	private boolean delayQueue = false;
	
	private int delayMillionSecond = 0;
	private String delayUrl;
	private String errorUrl;
	private int errorCode;
	
	private static Controller instance = new Controller();
	
	private Controller(){
		
		// test code in case jmx doesn't work
		delayUrl = "/js/a.json";
		delayMillionSecond  =10000;
		delayQueue = true;
		
	}
	
	public static Controller getInstance(){
	       return instance;		
	}

	public boolean hasErrorQueue() {
		return errorQueue;
	}

	public void setErrorQueue(boolean errorQueue) {
		this.errorQueue = errorQueue;
	}

	public boolean hasDelayQueue() {
		return delayQueue;
	}

	public void setDelayQueue(boolean delayQueue) {
		this.delayQueue = delayQueue;
	}

	public int getDelayMillionSecond() {
		return delayMillionSecond;
	}

	public void setDelayMillionSecond(int delayMillionSecond) {
		this.delayMillionSecond = delayMillionSecond;
	}

	public String getDelayUrl() {
		return delayUrl;
	}

	public void setDelayUrl(String delayUrl, int delayMillionSecond) {
		this.delayUrl = delayUrl;
		this.delayMillionSecond = delayMillionSecond;
		delayQueue =  true;
	}
	
	public void cleanDelayUrl() {
		delayUrl = null;
		delayMillionSecond = 0;
		delayQueue =  false;

	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
		errorQueue =  true;
	}
	
	public void cleanErrorUrl() {
		this.errorUrl = null;
		errorQueue =  false;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}

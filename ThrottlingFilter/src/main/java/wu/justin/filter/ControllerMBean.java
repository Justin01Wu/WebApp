package wu.justin.filter;

public interface ControllerMBean {

	public void setDelayUrl(String delayUrl, int delayMillionSecond);
	public void cleanDelayUrl();
	public void setErrorUrl(String errorUrl) ;
	public void cleanErrorUrl() ;
	
}

package wu.justin.doclet;

import java.util.ArrayList;
import java.util.List;

public class ApiClassEntry {
	
	private final String fullName;
	private final String name;	
	private String url;	
	private String comment;
	private List<ApiEntry> apis;
	
	public ApiClassEntry(Class<?> clazz) {
		this.name = clazz.getSimpleName();
		this.fullName = clazz.getName();
	}
	
	public String getFullName() {
		return fullName;
	}
//	public void setFullName(String fullName) {
//		this.fullName = fullName;
//	}
	public String getName() {
		return name;
	}
//	public void setName(String name) {
//		this.name = name;
//	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<ApiEntry> getApis() {
		return apis;
	}
	public void setApis(List<ApiEntry> apis) {
		this.apis = apis;
	}
	
	public void addApis(ApiEntry one){
		if(apis == null){
			apis = new ArrayList<>();
		}
		apis.add(one);
		
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}

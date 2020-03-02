package wu.justin.bean;

public class ClassRoom {
	
	public ClassRoom(Integer id, String desc ) {
		super();
		this.id = id;
		this.desc = desc;
	}
	
	private Integer id;
	private String desc;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	

}

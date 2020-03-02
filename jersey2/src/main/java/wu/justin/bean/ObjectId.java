package wu.justin.bean;

public class ObjectId {
	
	private int id;
	private String name;
	
	
	public ObjectId(){
		this.id = 1256;
		this.name = "object Id name";
	}
	
	//JACKSON will call this method in ObjectIdSerializerModule
	public String toJsonString(){
		return "test global serializer on a special type, id= " + id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

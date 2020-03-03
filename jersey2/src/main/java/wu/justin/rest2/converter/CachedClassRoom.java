package wu.justin.rest2.converter;

import java.util.HashMap;
import java.util.Map;

import wu.justin.bean.ClassRoom;

public class CachedClassRoom {
	
	private CachedClassRoom() {}
	
	private static Map<Integer, ClassRoom> classrooms = new HashMap<>();
	static {
		ClassRoom cr = new ClassRoom(1, "customized serializer without parameter");
		classrooms.put(cr.getId(), cr);
		cr = new ClassRoom(2, "customized serializer with parameter");
		classrooms.put(cr.getId(), cr);
		cr = new ClassRoom(3, "customized serializer with static class");
		classrooms.put(cr.getId(), cr);

	}
	
	 public static ClassRoom findById(Integer id) {
		 if(id == null) {
			 return null;
		 }
		 return classrooms.get(id);
	 }
}

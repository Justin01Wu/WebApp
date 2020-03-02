package wu.justin.rest2.converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import wu.justin.bean.ClassRoom;

public class ClassRoomSerializer extends JsonSerializer<Integer> {
	
	private static Map<Integer, ClassRoom> classrooms = new HashMap<>();
	static {
		ClassRoom cr = new ClassRoom(1, "test cutomized serializer on foreign key");
		classrooms.put(1, cr);
		cr = new ClassRoom(2,"test cutomized serializer on foreign key");
		classrooms.put(2, cr);
	}
	
	private static final String NOT_FOUND = "Unknown classroom "; 

	 @Override
	public void serialize(Integer id, JsonGenerator gen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		if (id == null  ) {
			gen.writeString("");
			return;
		} 
		if (id<=0 ) {
			gen.writeString("Wrong userId" + id);
			return;
		}
		
		ClassRoom cr = classrooms.get(id);
		if (cr == null  ) {
			gen.writeString(NOT_FOUND + id);
			return;
		}

		gen.writeObject(cr);
		// TODO please follow this to implement a parameter:
		// https://stackoverflow.com/questions/22634860/how-to-pass-parameter-to-jsonserializer

	}
}


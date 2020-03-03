package wu.justin.rest2.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import wu.justin.bean.ClassRoom;

public class ClassRoomConverter {

	private static final String NOT_FOUND = "Unknown classroom ";

	public static class Desc extends JsonSerializer<Integer> {
		
		public void serialize(Integer id, JsonGenerator gen, SerializerProvider provider)
				throws IOException, JsonProcessingException {

			if (id == null) {
				gen.writeString("");
			} else {
				ClassRoom cr = CachedClassRoom.findById(id);
				if (cr == null) {
					gen.writeString(NOT_FOUND + id);
					return;
				}
				gen.writeString(cr.getDesc());
			}
		}
	}
	
	public static class IdDesc extends JsonSerializer<Integer> {
		
		public void serialize(Integer id, JsonGenerator gen, SerializerProvider provider)
				throws IOException, JsonProcessingException {

			if (id == null) {
				gen.writeString("");
			} else {
				ClassRoom cr = CachedClassRoom.findById(id);
				if (cr == null) {
					gen.writeString(NOT_FOUND + id);
					return;
				}
				gen.writeString(cr.getId() + ": " + cr.getDesc());
			}
		}
	}


}

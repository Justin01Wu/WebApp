package wu.justin.rest2.converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import wu.justin.bean.ClassRoom;

public class ClassRoomSerializer extends StdSerializer<Integer> implements ContextualSerializer {
	
	private static final long serialVersionUID = 1345345242L;
	private JsonTargetEnum type;

	public ClassRoomSerializer() {
		super(Integer.class);
		this.type = JsonTargetEnum.WholeObject;
	}
	public ClassRoomSerializer(JsonTargetEnum t ) {
		super(Integer.class);
		this.type = t;
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
		
		ClassRoom cr = CachedClassRoom.findById(id);
		if (cr == null  ) {
			gen.writeString(NOT_FOUND + id);
			return;
		}

		// https://stackoverflow.com/questions/22634860/how-to-pass-parameter-to-jsonserializer
		if(type== JsonTargetEnum.WholeObject ) {
			gen.writeObject(cr);	
		}else if(type== JsonTargetEnum.Desc ) {
			gen.writeString(cr.getDesc());
		}		
	}

	 
	 // from https://stackoverflow.com/questions/22634860/how-to-pass-parameter-to-jsonserializer
	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {

		JsonTargetEnum key = null;
        JsonTargetType ann = null;

        if (property != null) {
          ann = property.getAnnotation(JsonTargetType.class);
        }

        if (ann == null) {
        	key = JsonTargetEnum.WholeObject;        	
        }else {
        	key = ann.getType();
        }


        return new ClassRoomSerializer(key);
	}
}


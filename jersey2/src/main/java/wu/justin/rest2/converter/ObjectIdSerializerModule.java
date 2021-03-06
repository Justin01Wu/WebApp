package wu.justin.rest2.converter;

import java.io.IOException;

import wu.justin.bean.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 *    http://jmchung.github.io/blog/2014/06/18/how-to-customise-the-jackson-json-objectmapper-in-java-ee-enterprise-application/
 * @author justin.wu
 *  convert a type of object into customized format 
 */

public class ObjectIdSerializerModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public ObjectIdSerializerModule() {
        super("ObjectIdSerializerModule", new Version(0, 1, 0, "alpha", "wu.justin", "jersey2"));
        this.addSerializer(ObjectId.class, new ObjectIdSerializer());
    }

    public class ObjectIdSerializer extends JsonSerializer<ObjectId> {

        @Override
        public void serialize(ObjectId value, JsonGenerator jgen
                , SerializerProvider provider)
                throws IOException, JsonProcessingException {
            jgen.writeString(value.toJsonString());
        }
    }
}

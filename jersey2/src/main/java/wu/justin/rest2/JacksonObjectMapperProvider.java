package wu.justin.rest2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * from http://jmchung.github.io/blog/2014/06/18/how-to-customise-the-jackson-json-objectmapper-in-java-ee-enterprise-application/
 * and http://stackoverflow.com/questions/16380065/how-to-extract-objectmapper-from-jax-rs-client/16384686#16384686
 *
 */
@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

    final ObjectMapper defaultObjectMapper;

    public JacksonObjectMapperProvider() {
        defaultObjectMapper = createDefaultMapper();
    }
    
    public ObjectMapper getContext(Class<?> type) {
        return defaultObjectMapper;
    }

    private static ObjectMapper createDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper(); // This will change default date conversion, TODO: please find a correct way
        DateFormat df = new SimpleDateFormat("MMM/yyyy/dd'T'HH:mm:ssZ");
        mapper.setDateFormat(df); // 1.8 and above

        return mapper;
    }

}
package wu.justin.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import wu.justin.ApiTestUtil;

public class UserExtTest {

    @Test
    public void verifyBean() throws IOException, URISyntaxException, ParseException {
    	
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	
        String origJsonDataFile = UserExtTest.class.getSimpleName() + ".json";

        String templateData = ApiTestUtil.readJSONFile(origJsonDataFile);
        UserExt dto = mapper.readValue(templateData, UserExt.class);
        
        assertNull( dto.getId());
        String targetJson = mapper.writeValueAsString(dto);
        
        JSONObject json = ApiTestUtil.convertJSONStr2Obj(targetJson);
        JSONObject expectedJson = ApiTestUtil.convertJSONStr2Obj(templateData);

        ApiTestUtil.verifyJson((Map<String, Object>)json, (Map<String, Object>)expectedJson);
    }

}

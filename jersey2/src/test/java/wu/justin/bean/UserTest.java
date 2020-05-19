package wu.justin.bean;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import wu.justin.ApiTestUtil;

/**
 * 
 * Because User class exposed to external APi, 
 * so we need to verify the son structure is not changed by accidental refactoring
 *
 */
public class UserTest {

    @Test
    public void verifyUserJsonStructure() throws IOException, URISyntaxException, ParseException {

        ObjectMapper mapper = new ObjectMapper();

        String jsonData = getUserJustin();
        User u = mapper.readValue(jsonData, User.class);
        assertEquals( Integer.valueOf(56239), u.getId());

        String targetJson = mapper.writeValueAsString(u);
        JSONObject json = ApiTestUtil.convertJSONStr2Obj(targetJson);
        JSONObject expectedJson = ApiTestUtil.convertJSONStr2Obj(jsonData);

        ApiTestUtil.verifyJson((Map<String, Object>)json, (Map<String, Object>)expectedJson);

    }
    
    public static String getUserJustin() throws URISyntaxException, FileNotFoundException {

        String origJsonDataFile = UserTest.class.getSimpleName() + "_justin.json";
        String templateData = ApiTestUtil.readJSONFile(origJsonDataFile);
        return templateData;
    }

}

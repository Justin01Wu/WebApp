package wu.justin.bean;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import wu.justin.ApiTestUtil;

/**
 * 
 * Because User class exposed to external APi, 
 * so we need to verify the son structure is back ward compatible to stop accidental refactoring
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
    
    /**
     * it still can pass if the json doesn't desc( User class added desc field recently) 
     */
    @Test
    public void verifyJsonBackWardCompatible() throws IOException, URISyntaxException, ParseException {

        ObjectMapper mapper = new ObjectMapper();

        String jsonData = getUserWithoutDesc();
        User u = mapper.readValue(jsonData, User.class);
        assertEquals( Integer.valueOf(56239), u.getId());

        String targetJson = mapper.writeValueAsString(u);
        JSONObject json = ApiTestUtil.convertJSONStr2Obj(targetJson);
        JSONObject expectedJson = ApiTestUtil.convertJSONStr2Obj(jsonData);

        ApiTestUtil.verifyJson((Map<String, Object>)json, (Map<String, Object>)expectedJson);

    }
    
    /**
     * it will fail the json have desc2( User class renamed desc field recently) 
     */
    @Test(expected = UnrecognizedPropertyException.class)
    public void failIfFieldRename() throws IOException, URISyntaxException, ParseException {

        ObjectMapper mapper = new ObjectMapper();

        String jsonData = getUserWithDesc2();
        User u = mapper.readValue(jsonData, User.class);
        assertEquals( Integer.valueOf(56239), u.getId());

        String targetJson = mapper.writeValueAsString(u);
        JSONObject json = ApiTestUtil.convertJSONStr2Obj(targetJson);
        JSONObject expectedJson = ApiTestUtil.convertJSONStr2Obj(jsonData);

        ApiTestUtil.verifyJson((Map<String, Object>)json, (Map<String, Object>)expectedJson);

    }
    
    private static String getUserJustin() throws URISyntaxException, FileNotFoundException {

        String origJsonDataFile = UserTest.class.getSimpleName() + "_justin.json";
        String templateData = ApiTestUtil.readJSONFile(origJsonDataFile);
        return templateData;
    }
    
    private static String getUserWithoutDesc() throws URISyntaxException, FileNotFoundException {

        String origJsonDataFile = UserTest.class.getSimpleName() + "_noDesc.json";
        String templateData = ApiTestUtil.readJSONFile(origJsonDataFile);
        return templateData;
    }
    
    private static String getUserWithDesc2() throws URISyntaxException, FileNotFoundException {

        String origJsonDataFile = UserTest.class.getSimpleName() + "_desc2.json";
        String templateData = ApiTestUtil.readJSONFile(origJsonDataFile);
        return templateData;
    }

}

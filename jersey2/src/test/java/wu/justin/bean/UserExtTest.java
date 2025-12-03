package wu.justin.bean;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import net.minidev.json.parser.ParseException;
import wu.justin.ApiTestUtil;

public class UserExtTest {

    @Test
    public void verifyBean() throws IOException, URISyntaxException, ParseException {
    	        
        String origJsonDataFile = UserExtTest.class.getSimpleName() + ".json";
        ApiTestUtil.verifyClassJsonStructure(origJsonDataFile, UserExt.class);

    }

}

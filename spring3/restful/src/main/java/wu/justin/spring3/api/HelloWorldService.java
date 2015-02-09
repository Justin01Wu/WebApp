package wu.justin.spring3.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api")
public class HelloWorldService {

	
	@RequestMapping(value = "/ping", 
	method = RequestMethod.GET,
	produces={"application/json"})
	public @ResponseBody String ping(){
		return "{\"msg\":\"hello\"}";
	}
}

package wu.justin.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonAutoDetect(
        fieldVisibility= JsonAutoDetect.Visibility.NONE,
        getterVisibility= JsonAutoDetect.Visibility.NONE,
        isGetterVisibility= JsonAutoDetect.Visibility.NONE,
        setterVisibility= JsonAutoDetect.Visibility.NONE
)
@JsonPropertyOrder(alphabetic=true)
public class UserExt extends User {

    @Override
    @JsonGetter("id")  		// this one also enable super.setId
	public Integer getId() {
		return super.getId();
	}

    @Override
    @JsonProperty("name")  // this one also enable super.setName
	public String getName() {
		return super.getName();
	}
    
    @Override
    @JsonProperty("desc")
	public String getDesc() {
		return super.getDesc();
	}
}

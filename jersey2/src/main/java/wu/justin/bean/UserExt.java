package wu.justin.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
    @JsonGetter("id")  		
    // this one also enable super.setId, to stop it, please override with @JsonIgnore before version 1.9
    // or use @JsonProperty(access = Access.WRITE_ONLY) after version 1.9
	public Integer getId() {
		return super.getId();
	}
    
    @Override
    @JsonProperty(access = Access.READ_ONLY)  
    // only disable setter method, it is a bad design(bean should be mirrored for input and output)
    // just for demo    
	public void setId(Integer id) {
		super.setId(id);
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

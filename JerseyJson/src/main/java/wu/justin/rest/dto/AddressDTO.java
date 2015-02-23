package wu.justin.rest.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonProperty;

import wu.justin.business.Address;

@JsonAutoDetect(
		fieldVisibility=Visibility.NONE,
		getterVisibility=Visibility.NONE, 
		isGetterVisibility=Visibility.NONE
		)
public class AddressDTO extends Address{

	@Override
	@JsonProperty("address7548")
	public String getAddress() {
		return super.getAddress();
	}
	
}

package wu.justin.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import wu.justin.rest.dto.AddressHideDTO;
import wu.justin.rest.dto.AddressXMLDTO;

@Path("/user/address")
public class AddressApi {

	 @PUT
	 @Consumes(MediaType.APPLICATION_XML)
	 @Path("/update")
	 public Response updateAddress(JAXBElement<AddressHideDTO> addressDTO) {
		 System.out.println("updateAddress...");
		 System.out.println(addressDTO.getValue().getAddress());		 
	    return Response.status(200).entity("success").build();
	  }
	
	/** demo how to hide most of fields */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/current")
	public AddressHideDTO getCurrentAddress() {	
		
		System.out.println("getCurrentAddress...");
		
		AddressHideDTO homeAddress =  new AddressHideDTO();
		homeAddress.setId(123768);
		homeAddress.setCountry("Canada");
		homeAddress.setAddress("This is a demo how to hide most of fields");		
	    return homeAddress;
	}
	
	/** demo how to handle xml output */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/xml/current")
	public AddressXMLDTO getCurrentXmlAddress() {	
		
		System.out.println("getCurrentXmlAddress...");
		
		AddressXMLDTO homeAddress =  new AddressXMLDTO();
		homeAddress.setId(123768);
		homeAddress.setCountry("Canada");
		homeAddress.setAddress("This is a demo how to get xml data from java object");		
	    return homeAddress;
	}
}

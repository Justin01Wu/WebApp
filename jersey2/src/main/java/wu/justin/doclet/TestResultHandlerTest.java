package wu.justin.doclet;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestResultHandlerTest {
	
	private static String apiIntUrlSeg = "abc{int}.json";
	private static String apiStringUrlSeg = "abc{string}.json";
	
	private static String resultIntUrlSeg = "abc-2328.json";
	private static String resultNotIntUrlSeg = "abcnotInt.json";
	private static String resultStringUrlSeg = "abc234sdfllsduew\\|][r.43232jsf.json";


	//   abc{string}.json  should match abc234sdfllsduew\|][r.43232jsf.json
	//   abc{int}.json should match abc-2328.json

	/**
	 * no parameters, so return false
	 */
	@Test
	public void testIntegerNoParameter() {
		
		ApiEntry apiEntry = new ApiEntry("GET", null, null, null );
		
		boolean result  = TestResultHandler.handlePathParameterSeg(apiEntry, apiIntUrlSeg, resultIntUrlSeg);
		assertFalse(result);
		
	}
	
	/**
	 * parameter is not found, so return false
	 */
	@Test
	public void testIntegerParameterNotFound() {
		
		Class<?> clazz =  TestResultHandlerTest.class;
		ApiEntry apiEntry = new ApiEntry("GET", null, null, null );
		String type = "QueryParam";
		String name = "int";
		ParameterEntry parameterEntry = new ParameterEntry(name, clazz.getSimpleName(), type);
		apiEntry.addParameter(parameterEntry);
		
		boolean result  = TestResultHandler.handlePathParameterSeg(apiEntry, apiIntUrlSeg, resultIntUrlSeg);
		assertFalse(result);
		
	}
	
	/**
	 * parameter is found, regular express didn't match, so return false
	 */
	@Test
	public void testIntegerParameterFoundRegFails() {
		
		Class<?> clazz =  Integer.class;
		ApiEntry apiEntry = new ApiEntry("GET", null, null, null );
		String type = "PathParam";
		String name = "int";
		ParameterEntry parameterEntry = new ParameterEntry(name, clazz.getSimpleName(), type);
		apiEntry.addParameter(parameterEntry);
		
		boolean result  = TestResultHandler.handlePathParameterSeg(apiEntry, apiIntUrlSeg, resultNotIntUrlSeg);
		assertFalse(result);
		
	}


	/**
	 * parameter is found, regular express match,  return true
	 */
	@Test
	public void testIntegerMatch() {
		
		Class<?> clazz =  Integer.class;
		ApiEntry apiEntry = new ApiEntry("GET", null, null, null );
		String type = "PathParam";
		String name = "int";
		ParameterEntry parameterEntry = new ParameterEntry(name, clazz.getSimpleName(), type);
		apiEntry.addParameter(parameterEntry);
		
		boolean result  = TestResultHandler.handlePathParameterSeg(apiEntry, apiIntUrlSeg, resultIntUrlSeg);
		assertTrue(result);
		
	}

	/**
	 * parameter is found, regular express match,  return true
	 */
	@Test
	public void testStringMatch() {
		
		Class<?> clazz =  String.class;
		ApiEntry apiEntry = new ApiEntry("GET", null, null, null );
		String type = "PathParam";
		String name = "string";
		ParameterEntry parameterEntry = new ParameterEntry(name, clazz.getSimpleName(), type);
		apiEntry.addParameter(parameterEntry);
		
		boolean result  = TestResultHandler.handlePathParameterSeg(apiEntry, apiStringUrlSeg, resultStringUrlSeg);
		assertTrue(result);
		
	}


}

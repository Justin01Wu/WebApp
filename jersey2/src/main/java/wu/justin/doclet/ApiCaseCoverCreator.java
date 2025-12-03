package wu.justin.doclet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** create apiCaseCovers.json */
// Please don't use log4j because it doesn't work in doclet
public class ApiCaseCoverCreator {

	// Logger configured for doclet environment with forced console output
	private static final Logger LOG = Logger.getLogger(ApiCaseCoverCreator.class.getSimpleName());

	static {
		// Configure logger to force output to System.out in doclet environment
		LOG.setUseParentHandlers(false);
		StreamHandler handler = DTO2JsonListCreator.getStreamHandler();
		LOG.addHandler(handler);
	}

	public static boolean create() {

		LOG.info("     ==>> handle test cases : ");
		try {
			handleOneProject();
		} catch (IOException e) {
			LOG.severe(e.getClass().getSimpleName() + ": " + e.getMessage());
			return false;
		}

		return true;
	}

	private static void handleOneProject() throws IOException {
		String outFileKey = "integration.test.result.output";
		String testResultFolder = System.getProperty(outFileKey);
		String skipMessage = "didn't find %s, so skip it";
		if (testResultFolder == null) {
			LOG.info(String.format(skipMessage, outFileKey));
			return;
		}
		LOG.info("testResultFolder= " + testResultFolder);

		String inFileKey = "integration.test.result.input";
		String testResultInputFolder = System.getProperty(inFileKey);
		if (testResultInputFolder == null) {
			LOG.info(String.format(skipMessage, inFileKey));
			return;
		}
		LOG.info("testResultInputFolder= " + testResultInputFolder);

		TestResultHandler handler = new TestResultHandler("api", testResultFolder, testResultInputFolder);

		Map<String, TestResult> allApiOutputs = handler.getAllTestResults();
		Map<String, InputAndOutput> allApis = new HashMap<>();

		for (var oneApi : allApiOutputs.values()) {
			InputAndOutput one = new InputAndOutput();
			one.setCaseName(oneApi.getCaseName());
			String fileName = removePathFromName(oneApi.getFilePath());
			one.setFilePath(fileName);
			one.setOutputJson(oneApi.getJson());
			one.setMethod(oneApi.getMethod());
			one.setCost(oneApi.getCost());
			one.setUrl(oneApi.getUrl());
			one.setStatus(oneApi.getStatus());

			allApis.put(one.getCaseName(), one);
		}

		Map<String, TestResultInput> allApiInputs = handler.getAllTestInputResults();
		for (var apiInput : allApiInputs.entrySet()) {
			TestResultInput oneApi = apiInput.getValue();
			InputAndOutput one = allApis.get(apiInput.getKey());
			one.setCaseName(oneApi.getCaseName());
			one.setInputJson(oneApi.getJson());
		}

		LOG.info("total found integration test cases = " + allApiOutputs.size());
		if (!allApiOutputs.isEmpty()) {
			String jsonString = convertObject2JSONStr(allApis);
			writeOutputFile(jsonString);
		}
	}

	public static String removePathFromName(String fname) {
		int pos = fname.lastIndexOf(File.separator);
		if (pos > -1) {
			return fname.substring(pos + 1);
		} else {
			return fname;
		}
	}

	public static String convertObject2JSONStr(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper
				// enable pretty print
				.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	}

	private static void writeOutputFile(String jsonString) throws IOException {
		java.nio.file.Path currentRelativePath = java.nio.file.Paths.get("");
		String myPath = currentRelativePath.toAbsolutePath().toString();
		LOG.info("Current absolute path is: " + myPath);

		var outputFile = getOutputFile(myPath);
		LOG.info("output file is : " + outputFile);

		FileWriter fw = new FileWriter(outputFile);
		try (PrintWriter out1 = new PrintWriter(fw)) {
			out1.write(jsonString);
		}
	}

	private static String getOutputFile(String myPath) {
		String outputFile;
		String fileName = "apiCaseCovers.json";
		String apiDocsSuffix = "apidocs";
		if (myPath.endsWith(apiDocsSuffix)) {
			// it will get this folder if it is run in maven:
			// project.build.directory}\target\apidocs
			// because ApiDocs is the first doclet executed
			outputFile = myPath.substring(0, myPath.length() - apiDocsSuffix.length() - 1) + "/../"
					+ "/src/main/webapp/" + fileName;
		} else {
			// it will get this folder if it is run in unit test: project.build.directory
			outputFile = myPath + "/target/" + fileName;
		}
		return outputFile;
	}
}
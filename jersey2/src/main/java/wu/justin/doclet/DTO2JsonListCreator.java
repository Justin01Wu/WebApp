package wu.justin.doclet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import wu.justin.bean.Pair2;

/** create dto-json-list.json */
//Please don't use log4j because it doesn't work in doclet
public class DTO2JsonListCreator {

private static final String OUTPUT_FILE = "dto-json-list.json";
public static final String INPUT_FOLDER = "dto-json-test/";
public static final String INPUT_File = "class_file_list.txt";
public static final String JSON_LIST_SEPARATOR = " ::: ";

// Logger configured for doclet environment with forced console output
private static final Logger LOG = Logger.getLogger(DTO2JsonListCreator.class.getSimpleName());

static {
 // Configure logger to force output to System.out in doclet environment
 LOG.setUseParentHandlers(false);
 StreamHandler handler = getStreamHandler();
 LOG.addHandler(handler);
}

public static StreamHandler getStreamHandler() {
 // Create custom handler that writes directly to System.out
 StreamHandler handler =
     new StreamHandler(System.out, new SimpleFormatter()) {
       @Override
       public synchronized void publish(java.util.logging.LogRecord record) {
         super.publish(record);
         flush(); // Ensure immediate output
       }
     };
 handler.setLevel(Level.ALL);
 return handler;
}

public static boolean create() {

 LOG.info("     ==>> generating " + OUTPUT_FILE + " ...");

 String inputFilePath = INPUT_FOLDER + INPUT_File;
 LOG.info("     ==>> collect all JSON structure test result in " + inputFilePath);

 String targetRoot = getTargetFolder();
 File f = new File(targetRoot + "/" + inputFilePath);
 if (!f.exists()) {
   LOG.info("     ==>> didn't find input file, so do nothing: " + f.getAbsolutePath());
   return false;
 }

 HashSet<String> allSet = new HashSet<>();
 try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
   String line;
   while ((line = reader.readLine()) != null) {
     allSet.add(line);
   }
 } catch (IOException e) {
   LOG.severe(e.getClass().getSimpleName() + ": " + e.getMessage());
   return false;
 }

 LOG.info("     ==>> total JSON structure test results: " + allSet.size());

 try {
   writeFile(allSet, targetRoot);
   return true;
 } catch (IOException e) {
   LOG.severe(e.getClass().getSimpleName() + ": " + e.getMessage());
   return false;
 }
}

private static String getOutputFile() {
 String outputFile;

 Path currentRelativePath = Paths.get("");
 String myPath = currentRelativePath.toAbsolutePath().toString();
 LOG.info("Current absolute path is: " + myPath);

 String apiDocsSuffix = "apidocs";
 if (myPath.endsWith(apiDocsSuffix)) {

   // it will get this folder if it is run in maven:
   // project.build.directory}\target\apidocs because ApiDocs is the first doclet
   // executed
   outputFile =
       myPath.substring(0, myPath.length() - apiDocsSuffix.length() - 1)
           + "/vcaps3/"
           + OUTPUT_FILE;

   // we need include output file in the war file, so put it in src/main/webapp
   // folder

 } else {
   // it will get this folder if it is run in unit test: project.build.directory
   outputFile = myPath + "/target/" + OUTPUT_FILE;
 }
 return outputFile;
}

/*
private static String getAppRoot() {
 String appRoot;
 Path currentRelativePath = Paths.get("");
 String myPath = currentRelativePath.toAbsolutePath().toString();
 LOG.info("Current absolute path is: " + myPath);

 String apiDocsSuffix = "apidocs";
 if (myPath.endsWith(apiDocsSuffix)) {
   // it will get this folder if it is run in maven:
   // project.build.directory}\target\apidocs because ApiDocs is the first doclet
   // executed
   appRoot = myPath.substring(0, myPath.length() - apiDocsSuffix.length() - 1) + "/..";
 } else {
   // it will get this folder if it is run in unit test: project.build.directory
   appRoot = myPath;
 }
 LOG.info("appRoot is: " + appRoot);
 return appRoot;
}

*/

private static String getTargetFolder() {
 String appRoot;
 Path currentRelativePath = Paths.get("");
 String myPath = currentRelativePath.toAbsolutePath().toString();
 LOG.info("Current absolute path is: " + myPath);

 String apiDocsSuffix = "apidocs";
 if (myPath.endsWith(apiDocsSuffix)) {
   // it will get this folder if it is run in maven:
   // project.build.directory}\target\apidocs because ApiDocs is the first doclet
   // executed
   appRoot = myPath.substring(0, myPath.length() - apiDocsSuffix.length() - 1);
 } else {
   // it will get this folder if it is run in unit test: project.build.directory
   appRoot = myPath + "/target";
 }
 LOG.info("appRoot is: " + appRoot);
 return appRoot;
}

private static void writeFile(HashSet<String> allSet, String targetRoot) throws IOException {
 String outputFile = getOutputFile();

 try (FileWriter fw = new FileWriter(outputFile)) {
   fw.write("[\n");
   boolean first = true;

   for (String oneLine : allSet) {
     Pair2<String, String> p = handleOneLine(oneLine, targetRoot);
     if (p == null) {
       continue;
     }
     if (!first) {
       fw.write(",\n");
     }
     fw.write("  {\"className\": \"" + p.getL() + "\", \"sample\": " + p.getR() + "}");
     first = false;
   }
   fw.write("\n]\n");
 }
 LOG.info("output file is : " + outputFile);
}

private static String readFileToString(File f) throws IOException {
 StringBuilder sb = new StringBuilder();
 try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
   String line;
   while ((line = reader.readLine()) != null) {
     sb.append(line).append(System.lineSeparator());
   }
 }
 return sb.toString();
}

private static Pair2<String, String> handleOneLine(String oneLine, String targetRoot)
   throws IOException {
 // LOG.info("     ==>> one line: " + oneLine);
 String[] two = oneLine.split(JSON_LIST_SEPARATOR);

 File f = new File(targetRoot + "/test-classes/" + two[1]);
 if (!f.exists()) {
   LOG.info("     ==>> didn't find JSON structure file: " + f.getAbsolutePath());
   return null;
 }

 String jsonStr = readFileToString(f);
 //    LOG.info(
 //        "\t ==>> read JSON structure file content for "
 //            + two[0]
 //            + ": "
 //            + jsonStr.length()
 //            + " characters");
 return new Pair2<>(two[0], jsonStr);
}
}
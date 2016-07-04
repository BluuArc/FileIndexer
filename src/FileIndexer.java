import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/*
 * Author: Joshua Castor
 * Created: July 2, 2016
 * Purpose: Read a directory and output a CSV file that contains a list of 
 * 			all of the files in that directory and their respective date info 
 */

public class FileIndexer {
	public static boolean debugOutput = false;
	public static int timeOffset = -5;			//change as necessary to fit your time zone
	
	public static void main(String[] args) {
		String version = "v1.0";
		
		//set directory to analyze and output file
		printMessage("Begin execution of " + FileIndexer.class.getName() + " " + version);
		printMessage("Choose the root directory to analyze.");
		String dir = FileChooser.pickAFile();
		setDirectory(dir);
		dir = getDirectory(dir);
		String outFile = FileChooser.getMediaDirectory() + "\\_list." + currentDate() +".csv";

		//read directory
		File[] listFiles = readFolder(dir);
		
		if(listFiles.length == 0){
			printMessage("ERROR: no files in directory. Terminating program.");
			return;
		}
		
		//index files
		try {
			printMessage("Begin analysis of [" + dir + "\\]");
			appendToFile(outFile, "file path,created,last accessed,last modified,\n");
			indexFiles(listFiles, FileChooser.getMediaDirectory(), outFile, 0);
			printMessage("Finished analysis of [" + dir + "\\]");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		printMessage("Output saved to [" + outFile + "]\nDone.");
	}//end main
	
	// method to setMediaPath using a picture, assuming that the images are in
	// one directory
	public static void setDirectory(String fName) {
		int pos = fName.lastIndexOf(File.separatorChar);
		String dir = fName.substring(0, pos);
		FileChooser.setMediaPath(dir);
	}// end setDirectory method
	
	//method to get list of files and folders in a directory
	public static File[] readFolder(String dir){
		File folder = new File(dir);
		File[] list = folder.listFiles();
		return list;
	}//end analyzeFolder
	
	//method to index the files in a file array
	public static void indexFiles(File[] list, String rootDir, String outFile, int depth) throws IOException{
		debug("Indexing files");
		for(int i = 0; i < list.length; ++i){
			debug("Checking [" + list[i].toString() + "]");
			if(list[i].isFile()){
				debug("It's a file");
				//get directory (relative to rootDir)
				String data = list[i].toString().replace(rootDir, "") + ",";
				
				//get data attributes (creation, last access, and last modified)
				BasicFileAttributes attr = null;
				try{
					attr = Files.readAttributes(list[i].toPath(), BasicFileAttributes.class);
				} finally{
					if(attr != null){
						data += formatTimeStamp(attr.creationTime().toInstant()) + ","; 
						data += formatTimeStamp(attr.lastAccessTime().toInstant()) + ",";
						data += formatTimeStamp(attr.lastModifiedTime().toInstant())	+ ",";
					}
				}//end try/finally
				
				//end with newline
				data += "\n";
				
				//append to output file
				appendToFile(outFile, data);
			}else if(list[i].isDirectory()){//end if isFile
				debug("It's a directory");
				printMessage(depthSpaces(depth+1) + "Analyzing [" + list[i].toString().replace(rootDir, "") + "\\]");
				File[] newDir = readFolder(list[i].toString());
				indexFiles(newDir, rootDir, outFile, depth+1);
			}else{//end if isDirectory()
				debug("I don't know what [" + list[i] + "] is.");
				return;
			}
		}//end for each index
	}//end indexFiles
	
	//method to append a string to a text file
	public static void appendToFile(String outFile, String s) throws IOException{
		debug("Appending string to file");
		File f = new File (outFile);
		Path p = f.toPath();
		byte[] data = s.getBytes();
		
		try (OutputStream out = new BufferedOutputStream(
	      Files.newOutputStream(p, StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
	      out.write(data, 0, data.length);
	    } catch (IOException x) {
	      System.err.println(x);
	    }
	}//end appendToFile
	
	//method to format a time stamp
	//sample input: 2016-01-19T01:46:31.009804Z
	public static String formatTimeStamp(Instant t){
		String s = t.atOffset(ZoneOffset.ofHours(timeOffset)).toString();
		String output = s.substring(0, s.lastIndexOf(":")).replace("T", " ");
		//remove T and Z from string
		return output;
	}//end formatTimeStamp
	
	//method to get current date in format of YYYY-MM-DD
	public static String currentDate(){
		return LocalDateTime.now().toLocalDate().toString();
	}//end currentDate()
	
	//print number of spaces according to count
	public static String depthSpaces(int count){
		String output = "";
		
		if(count <= 0)
			return output;
		
		for(int i = 0; i < count; ++i){
			output += " ";
		}
		
		return output;
	}//end depthSpaces
	
	//mainly for convenience
	public static void printMessage(String s){
		System.out.println(s);
	}
	
	//debug output
	public static void debug(String message){
		if(debugOutput)
			System.out.println("DEBUG: " + message);
	}//end debug
	
	// method to get directory as a string
	public static String getDirectory(String fName) {
		int pos = fName.lastIndexOf(File.separatorChar);
		String dir = fName.substring(0, pos);
		return dir;
	}// end setDirectory method
}// end class

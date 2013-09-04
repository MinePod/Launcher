package fr.minepod.launcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ClassFile {	
	public static String ReadFile(String path) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(path));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public static void WriteFile(String path, String stringToWrite) throws IOException {
		File file = new File(path);
 
		if(!file.exists()) {
			file.createNewFile();
		}
 
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stringToWrite);
		bw.close();
	}
	
	public static void Delete(File file) {		
		if(file.exists()) {
			if(file.isDirectory() && file.list().length != 0) {
				String files[] = file.list();
				for (String temp : files) {
	        		File fileDelete = new File(file, temp);
	        		Delete(fileDelete);
	        	}
			}
			file.delete();
		}
	}
	
	 public static void UnZip(String input, String dirOutput, String fileOutputName) {
		 try {
			 ClassFile.Delete(new File(dirOutput + fileOutputName));
			 ZipFile zipFile = new ZipFile(input);
		     zipFile.extractAll(dirOutput);
		     System.out.println(input + " unzipped to " + dirOutput);
		 } catch (ZipException e) {
			 CrashReport.SendReport(e.toString(), "unzipping file " + input + " to " + dirOutput);
		 }
	 }
	 
	 public static void Zip(String input, String output) {
		 try {
			 new File(output).delete();
			 ZipFile zipFile = new ZipFile(output);
			 ZipParameters parameters = new ZipParameters();
			 parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			 parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
				
			 if(new File(input).isDirectory()) {
				 zipFile.addFolder(input, parameters);
			 } else {
				 zipFile.addFile(new File(input), parameters);
			 }
				
			 System.out.println(input + " zipped to " + output);
		 } catch (ZipException e) {
			 CrashReport.SendReport(e.toString(), "zipping folder " + input + " to " + output);
		 }
	 }
}

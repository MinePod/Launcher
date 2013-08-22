package fr.minepod.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Downloader {
	 private InputStream rbc;
	 private byte[] buffer;
	 private double fileLength = 0.0D;
	 private int bytesRead;
	 private FileOutputStream fos;
	 private ArrayList<Integer> al = new ArrayList<Integer>();
	 private Gui Gui = Config.Gui;
 
	 public void DownloadFiles(URL website, String path, boolean isGui) {
		 System.out.println("\nStarting " + website + " to " + path);
		 new File(path).delete();
		 try {
		     fos = new FileOutputStream(path);
		     URLConnection urlConnection = website.openConnection();
		     fileLength = urlConnection.getContentLength();
		     rbc = website.openStream();
		     buffer = new byte[153600];
		     bytesRead = 0;
		     
	         if(isGui)
	        	 Gui.Max(fileLength);
		      
		     System.out.println("Downloading...");
		     while ((bytesRead = rbc.read(buffer)) > 0) {
		         fos.write(buffer, 0, bytesRead);
		         buffer = new byte[153600];
		        
		         if(isGui)
		        	 Gui.Add(bytesRead);
		     }
		      
		     al.clear();
		     System.out.println();

		 } catch (MalformedURLException e) {
			 CrashReport.SendReport(e.toString(), "downloading file");
		 } catch (IOException e) {
			 CrashReport.SendReport(e.toString(), "downloading file");
		 }

		 System.out.println("Downloading complete!");
	 }
	 
	 public void UnZip(String input, String dirOutput, String fileOutputName) {
		 try {
			 ClassFile.Delete(new File(dirOutput + fileOutputName));
			 ZipFile zipFile = new ZipFile(input);
		     zipFile.extractAll(dirOutput);
		     System.out.println(input + " unzipped to " + dirOutput);
		 } catch (ZipException e) {
			 CrashReport.SendReport(e.toString(), "unzipping file " + input + " to " + dirOutput);
		 }
	 }
	 
	 public void Zip(String input, String output) {
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

package fr.minepod.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

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
}

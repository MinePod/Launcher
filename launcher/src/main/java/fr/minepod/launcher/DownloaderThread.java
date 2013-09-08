package fr.minepod.launcher;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloaderThread extends Thread{	
	 private Downloader Downloader = new Downloader();
	 private String url;
	 private String md5Location;
	 private String folderLocation;
	 private String folderName;
	 private String zipLocation;
	 
	 public DownloaderThread(String url, String md5Location, String folderLocation, String folderName, String zipLocation) {
		 this.url = url;
		 this.md5Location = md5Location;
		 this.folderLocation = folderLocation;
		 this.folderName = folderName;
		 this.zipLocation = zipLocation;
	 }
	 
	 public void run() {
		 try {
			 System.out.println("Running new thread for downloading " + folderName);
			 
			 Downloader.DownloadFiles(new URL(Config.GetMd5FileUrl + this.url), md5Location, true);
			 ClassFile.Zip(folderLocation + folderName, zipLocation);
			 
			 String expectedMd5 = ClassFile.ReadFile(md5Location);
			 String currentMd5 = ClassFile.md5(new File(zipLocation));
			 
			 System.out.println("Expected md5: " + expectedMd5);
			 System.out.println("Current md5: " + currentMd5);
			 
			 if(!expectedMd5.equals(currentMd5)) {
				 System.out.println("Detecting modified " + folderName + " files, deleting...");
				 Downloader.DownloadFiles(new URL(url), zipLocation, true);
				 ClassFile.UnZip(zipLocation, folderLocation, folderName);
			 }
			 
		 } catch (MalformedURLException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			e.printStackTrace();
		}
	 }
}

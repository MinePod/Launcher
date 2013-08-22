package fr.minepod.launcher;

import java.io.File;
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
			 Downloader.Zip(folderLocation + folderName, zipLocation);
	
			 if(!new GetMd5().VerifyMd5(new File(md5Location), new File(zipLocation))) {
				 System.out.println("Detecting modified " + folderName + " files, deleting...");
				 Downloader.DownloadFiles(new URL(url), zipLocation, true);
				 Downloader.UnZip(zipLocation, folderLocation, folderName);
			 }
		 } catch (MalformedURLException e) {
			 e.printStackTrace();
		 }
	 }
}

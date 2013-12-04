package fr.minepod.launcher;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.lingala.zip4j.exception.ZipException;

public class DownloaderThread extends Thread{	
	 private Downloader Downloader = new Downloader();
	 private String url;
	 private String md5Location;
	 private String folderLocation;
	 private String folderName;
	 private String zipLocation;
	 private String zipMd5;
	 
	 public DownloaderThread(String url, String md5Location, String folderLocation, String folderName, String zipLocation, String zipMd5) {
		 this.url = url;
		 this.md5Location = md5Location;
		 this.folderLocation = folderLocation;
		 this.folderName = folderName;
		 this.zipLocation = zipLocation;
		 this.zipMd5 = zipMd5;
	 }
	 
	 public void run() {
		 try {
			 Config.Logger.info("Running new thread for downloading " + folderName);
			 
			 Downloader.DownloadFiles(new URL(Config.GetMd5FileUrl + this.url), md5Location, true);
			 
			 if(new File(zipLocation).exists()) {
				 String expectedMd5 = fr.minepod.Utils.Files.ReadFile(md5Location);
				 
				 Config.Logger.info("Expected md5: " + expectedMd5);
				 Config.Logger.info("Current md5: " + zipMd5 + " for " + zipLocation);
				 
				 if(!expectedMd5.equals(zipMd5)) {
					 Config.Logger.warning("Detecting modified " + folderName + " files, deleting...");
					 Downloader.DownloadFiles(new URL(url), zipLocation, true);
				 }
				 
			 } else {
				 Config.Logger.warning("No zip found for " + folderName + ", downloading...");
				 Downloader.DownloadFiles(new URL(url), zipLocation, true);
			 }
			 
			 Config.Gui.SetLoading();
			 fr.minepod.Utils.Files.UnZip(zipLocation, folderLocation, folderName);
		 } catch (MalformedURLException e) {
			new CrashReport(e.toString(), "downloading file");
		 } catch (IOException e) {
			new CrashReport(e.toString(), "downloading file");
		} catch (ZipException e) {
			new CrashReport(e.toString(), "unzipping file");
		}
	 }
}

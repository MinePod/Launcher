package fr.minepod.launcher;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.lingala.zip4j.exception.ZipException;

public class DownloaderThread extends Thread{	
	private String url;
	private String md5;
	private String folderLocation;
	private String folderName;
	private String fileLocation;
	private String fileMd5;
	private String fileType;

	public DownloaderThread(String url, String md5, String folderLocation, String folderName, String fileLocation, String fileMd5, String fileType) {
		this.url = url;
		this.md5 = md5;
		this.folderLocation = folderLocation;
		this.folderName = folderName;
		this.fileLocation = fileLocation;
		this.fileMd5 = fileMd5;
		this.fileType = fileType;
	}

	public void run() {
		try {
			Config.logger.info("Running new thread for downloading " + folderName);

			if(new File(fileLocation).exists()) {
				Config.logger.info("Expected md5: " + md5);
				Config.logger.info("Current md5: " + fileMd5 + " for " + fileLocation);

				if(!md5.equalsIgnoreCase(fileMd5)) {
					Config.logger.warning("Detecting modified " + folderName + " files, downloading...");
					new Downloader(new URL(url), fileLocation, true);
				}
			} else {
				Config.logger.warning("No file found for " + folderName + ", downloading...");
				new Downloader(new URL(url), fileLocation, true);
			}

			Config.gui.setLoading();
			
			if(fileType.equalsIgnoreCase("zip")) {
				fr.minepod.utils.UtilsFiles.unZip(fileLocation, folderLocation, folderName);
			} else {
				fr.minepod.utils.UtilsFiles.writeFile(folderLocation + folderName + "." + fileType, fr.minepod.utils.UtilsFiles.readFile(fileLocation));
			}
		} catch (MalformedURLException e) {
			new CrashReport(e.toString(), "downloading file");
		} catch (ZipException e) {
			new CrashReport(e.toString(), "unzipping file");
		} catch (IOException e) {
			new CrashReport(e.toString(), "copying file");
		}
	}
}

package fr.minepod.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Downloader {
	private InputStream rbc;
	private byte[] buffer;
	private double fileLength = 0.0D;
	private int bytesRead;
	private FileOutputStream fos;

	public Downloader(URL website, String path, boolean isGui) {
		Config.logger.info("Starting " + website + " to " + path);
		new File(path).delete();
		try {
			fos = new FileOutputStream(path);
			URLConnection urlConnection = website.openConnection();
			fileLength = urlConnection.getContentLength();
			rbc = website.openStream();
			buffer = new byte[153600];
			bytesRead = 0;

			if(isGui)
				Config.gui.setMax(fileLength);

			Config.logger.info("Downloading...");
			while ((bytesRead = rbc.read(buffer)) > 0) {
				fos.write(buffer, 0, bytesRead);
				buffer = new byte[153600];

				if(isGui)
					Config.gui.add(bytesRead);
			}

		} catch (MalformedURLException e) {
			new CrashReport(e.toString(), "downloading file");
		} catch (IOException e) {
			new CrashReport(e.toString(), "downloading file");
		}

		Config.logger.info("Downloading complete!");
	}
}

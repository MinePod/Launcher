package fr.minepod.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.lingala.zip4j.exception.ZipException;
import fr.minepod.utils.UtilsFiles;

public class DownloaderThread extends Thread {
  private boolean all = true;
  private String url;
  private String md5;
  private String folderLocation;
  private String folderName;
  private String fileLocation;
  private String fileMd5;
  private String fileType;
  private String fileAction;

  public DownloaderThread(String url, String fileLocation) {
    all = false;
    this.url = url;
    this.fileLocation = fileLocation;
  }

  public DownloaderThread(String url, String md5, String folderLocation, String folderName,
      String fileLocation, String fileMd5, String fileType, String fileAction) {
    this.url = url;
    this.md5 = md5;
    this.folderLocation = folderLocation;
    this.folderName = folderName;
    this.fileLocation = fileLocation;
    this.fileMd5 = fileMd5;
    this.fileType = fileType;
    this.fileAction = fileAction;
  }

  public void run() {
    try {
      if (all) {
        Config.logger.info("Running new thread for downloading " + folderName);

        if (new File(fileLocation).exists()) {
          Config.logger.info("Expected md5: " + md5);
          Config.logger.info("Current md5: " + fileMd5 + " for " + fileLocation);

          if (!md5.equalsIgnoreCase(fileMd5)) {
            Config.logger.warning("Detecting modified " + folderName + " files, downloading...");
            download(new URL(url), fileLocation, true);
          }
        } else {
          Config.logger.warning("No file found for " + folderName + ", downloading...");
          download(new URL(url), fileLocation, true);
        }

        Launcher.gui.setLoading(true);

        if (fileAction.equalsIgnoreCase("unzip")) {
          UtilsFiles.unZip(fileLocation, folderLocation, folderName);
        } else {
          UtilsFiles.copyFile(fileLocation, folderLocation + folderName + "."
              + fileType);
        }
      } else {
        download(new URL(url), fileLocation, false);
      }
    } catch (MalformedURLException e) {
      new CrashReport(e.toString(), "downloading file");
    } catch (ZipException e) {
      new CrashReport(e.toString(), "unzipping file");
    } catch (IOException e) {
      new CrashReport(e.toString(), "copying file");
    }
  }

  public void download(URL website, String path, boolean isGui) {
    Config.logger.info("Starting " + website + " to " + path);

    new File(path).delete();

    try {
      FileOutputStream fos = new FileOutputStream(path);
      URLConnection urlConnection = website.openConnection();
      int fileLength = urlConnection.getContentLength();
      InputStream input = website.openStream();
      byte[] buffer = new byte[153600];
      int bytesRead = 0;

      if (isGui) {
        Launcher.gui.addMax(fileLength);
      }

      Config.logger.info("Downloading...");
      while ((bytesRead = input.read(buffer)) > 0) {
        fos.write(buffer, 0, bytesRead);
        buffer = new byte[153600];

        if (isGui) {
          Launcher.gui.add(bytesRead);
        }
      }

      fos.close();
    } catch (IOException e) {
      new CrashReport(e.toString(), "downloading file");
    }

    Config.logger.info("Downloading complete!");
  }
}

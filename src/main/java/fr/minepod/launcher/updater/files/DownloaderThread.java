package fr.minepod.launcher.updater.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import fr.minepod.launcher.CrashReport;
import fr.minepod.launcher.Utils;
import fr.minepod.launcher.gui.LauncherGui;
import fr.minepod.utils.UtilsFiles;

public class DownloaderThread extends Thread {
  LauncherGui gui;
  Logger logger;
  FileClass file;
  String url;
  String temp;

  public DownloaderThread(LauncherGui gui, Logger logger, String url, String temp) {
    this(gui, logger, url, temp, null);
  }

  public DownloaderThread(LauncherGui gui, Logger logger, String url, String temp, FileClass file) {
    this.gui = gui;
    this.logger = logger;
    this.file = file;

    if (file != null) {
      this.url = Utils.fillVersionData(url, file.getId(), file.getVersion(), file.getType(), false);
      this.temp =
          Utils.fillVersionData(temp, file.getId(), file.getVersion(), file.getType(), true);
    } else {
      this.url = url;
      this.temp = temp;
    }
  }

  public void run() {
    try {
      logger.info("URL: " + url);
      logger.info("Temp path: " + temp);

      new File(temp).getParentFile().mkdirs();

      if (file != null) {
        logger.info("Running new thread for downloading " + file.getId());

        if (new File(temp).exists()) {
          String md5 = new UtilsFiles().md5(temp);

          logger.info("Expected md5: " + file.getMd5());
          logger.info("Current md5: " + md5 + " for " + temp);

          if (!file.getMd5().equalsIgnoreCase(md5)) {
            logger.warning("Detecting modifications for " + file.getId() + ", downloading...");
            download(new URL(url), temp);
          }
        } else {
          logger.warning("No file found for " + file.getId() + ", downloading...");
          download(new URL(url), temp);
        }
      } else {
        download(new URL(url), temp);
      }
    } catch (IOException | NoSuchAlgorithmException e) {
      CrashReport.show(e);
    }
  }

  public void download(URL website, String path) {
    logger.info("Starting " + website + " to " + path);

    new File(path).delete();

    try {
      FileOutputStream fos = new FileOutputStream(path);
      URLConnection urlConnection = website.openConnection();
      int fileLength = urlConnection.getContentLength();
      InputStream input = website.openStream();
      byte[] buffer = new byte[153600];
      int bytesRead = 0;

      if (gui != null) {
        gui.addMax(fileLength);
      }

      logger.info("Downloading " + website + ", size: " + fileLength / 1000 + "kB");
      while ((bytesRead = input.read(buffer)) > 0) {
        fos.write(buffer, 0, bytesRead);
        buffer = new byte[153600];

        if (gui != null) {
          gui.add(bytesRead);
        }
      }

      fos.close();

      logger.info("Final size for " + path + ": " + new File(path).length() / 1000 + "kB");
    } catch (IOException e) {
      CrashReport.show(e);
    }

    logger.info("Downloading " + website + " to " + path + " complete!");
  }
}

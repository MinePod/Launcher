package fr.minepod.launcher.updater.files;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fr.minepod.launcher.LauncherConfig;
import fr.minepod.launcher.gui.LauncherGui;
import fr.minepod.launcher.json.CustomJSONObject;

public class FileInstaller {
  public FileInstaller(LauncherGui gui, Logger logger, CustomJSONObject json)
      throws InterruptedException {
    List<DownloaderThread> threads = new ArrayList<>();

    String temp = json.getString("temp");
    String url = json.getString("url");

    JSONArray files = json.getArray("files");

    Iterator<?> iterator = files.iterator();
    while (iterator.hasNext()) {
      FileClass file = new FileClass(new CustomJSONObject((JSONObject) iterator.next()));

      DownloaderThread thread = new DownloaderThread(gui, logger, url, temp, file);
      thread.start();

      if (LauncherConfig.threaded) {
        threads.add(thread);
      } else {
        thread.join();
      }
    }

    for (DownloaderThread thread : threads) {
      thread.join();
    }
  }
}

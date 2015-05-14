package fr.minepod.launcher.updater.versions;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.minepod.launcher.CrashReport;
import fr.minepod.launcher.LauncherConfig;
import fr.minepod.launcher.Utils;
import fr.minepod.launcher.gui.LauncherGui;
import fr.minepod.launcher.json.CustomJSONObject;
import fr.minepod.launcher.updater.actions.ActionPerformer;
import fr.minepod.launcher.updater.files.DownloaderThread;
import fr.minepod.launcher.updater.files.FileInstaller;
import fr.minepod.utils.UtilsFiles;

public class VersionsUpdater {
  Logger logger;

  public Map<String, VersionClass> versions = new LinkedHashMap<>();

  public VersionsUpdater(Logger logger) {
    this.logger = logger;
  }

  public void init(LauncherGui gui) throws IOException, ParseException, InterruptedException {
    DownloaderThread downloader =
        new DownloaderThread(gui, logger, LauncherConfig.launcherDataUrl,
            LauncherConfig.launcherDataFile);
    downloader.start();
    downloader.join();

    String dataFile = new UtilsFiles().readFile(LauncherConfig.launcherDataFile);
    JSONObject jsonObject = (JSONObject) new JSONParser().parse(dataFile);

    if (jsonObject.get("skip") != null && (Boolean) jsonObject.get("skip")) {
      CrashReport
          .show("Le serveur de fichiers est actuellement en cours de maintenance. Retestez plus tard.");
    }

    JSONArray versions = (JSONArray) jsonObject.get("versions");
    if (versions.size() > 0) {
      for (int i = 0; i < versions.size(); i++) {
        JSONObject versionObject = (JSONObject) versions.get(i);

        String date = (String) versionObject.get("date");
        String version = (String) versionObject.get("version");
        String id = (String) versionObject.get("id");
        String type = (String) versionObject.get("type");

        String url = Utils.fillVersion((String) versionObject.get("url"), date, version, id, type);
        String message =
            Utils.fillVersion((String) versionObject.get("message"), date, version, id, type);

        VersionClass versionClass = new VersionClass(url, date, version, id, type);

        if (versionObject.get("skip") != null && (Boolean) versionObject.get("skip")) {
          message = "Désactivée: " + message;

          versionClass.setSkip(true);
        }

        if (!this.versions.containsKey(message)) {
          this.versions.put(message, versionClass);
        }
      }
    } else {
      CrashReport.show("Pas de versions disponibles.");
    }
  }

  public void installVersion(LauncherGui gui, VersionClass version) throws IOException,
      ParseException, InterruptedException, NoSuchAlgorithmException {
    logger.info("Launching installation of version:");
    logger.info("URL: " + version.getUrl());
    logger.info("Date: " + version.getDate());
    logger.info("Version: " + version.getId());

    DownloaderThread downloader =
        new DownloaderThread(gui, logger, version.getUrl(), LauncherConfig.launcherDataFile);
    downloader.start();
    downloader.join();

    String dataFile = new UtilsFiles().readFile(LauncherConfig.launcherDataFile);

    CustomJSONObject json = new CustomJSONObject((JSONObject) new JSONParser().parse(dataFile));
    new FileInstaller(gui, logger, json);

    gui.setLoading(true);

    new ActionPerformer(logger, json);
  }
}

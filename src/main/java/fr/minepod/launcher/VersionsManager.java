package fr.minepod.launcher;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.minepod.utils.UtilsFiles;

public class VersionsManager {
  String launcherLocation;
  String launcherDataUrl;
  String minecraftDir;
  String slash;
  Logger logger;
  String launcherDataFile;

  public Map<String, VersionClass> versions = new LinkedHashMap<>();

  public VersionsManager(String launcherLocation, String launcherDataUrl, String minecraftDir,
      String slash, Logger logger) {
    this.launcherLocation = launcherLocation;
    this.launcherDataUrl = launcherDataUrl;
    this.minecraftDir = minecraftDir;
    this.slash = slash;
    this.logger = logger;

    launcherDataFile = launcherLocation + slash + "launcher_data.json";
  }

  public void initVersionsManager() throws IOException, ParseException, InterruptedException {
    DownloaderThread downloader =
        new DownloaderThread(logger, launcherDataUrl, launcherDataFile);
    downloader.start();
    downloader.join();

    String dataFile = UtilsFiles.readFile(launcherDataFile);
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

        String url = fillVersion((String) versionObject.get("url"), date, version, id, type);
        String message =
            fillVersion((String) versionObject.get("message"), date, version, id, type);

        if (!this.versions.containsKey(message)) {
          this.versions.put(message, new VersionClass(url, date, version, id, type, message));
        }
      }
    } else {
      CrashReport.show("Pas de versions disponibles.");
    }
  }

  public void installVersion(VersionClass version, LauncherGui gui, Logger logger)
      throws IOException, ParseException, InterruptedException, NoSuchAlgorithmException {
    List<DownloaderThread> list = new ArrayList<>();

    logger.info("Launching installation of version:");
    logger.info("URL: " + version.getUrl());
    logger.info("Date: " + version.getDate());
    logger.info("Version: " + version.getId());

    DownloaderThread downloader =
        new DownloaderThread(logger, version.getUrl(), launcherDataFile);
    downloader.start();
    downloader.join();

    String dataFile = UtilsFiles.readFile(launcherDataFile);
    JSONObject jsonObject = (JSONObject) new JSONParser().parse(dataFile);

    JSONArray files = (JSONArray) jsonObject.get("files");

    @SuppressWarnings("unchecked")
    Iterator<Object> iterator = files.iterator();
    while (iterator.hasNext()) {
      JSONObject object = (JSONObject) iterator.next();

      if (object.get("skip") == null || !(Boolean) object.get("skip")) {
        String fileId = (String) object.get("id");
        String fileVersion = (String) object.get("version");
        String fileType = (String) object.get("type");
        String fileAction = (String) object.get("action");
        String fileMd5 = (String) object.get("md5");

        String filePath =
            fillVersionData((String) object.get("path"), fileId, fileVersion, fileType, true);
        String fileName =
            fillVersionData((String) object.get("name"), fileId, fileVersion, fileType, true);
        String fileTemp =
            fillVersionData((String) object.get("temp"), fileId, fileVersion, fileType, true);
        String fileUrl =
            fillVersionData((String) object.get("url"), fileId, fileVersion, fileType, false);

        if (!new File(fileTemp).exists()) {
          if (!new File(fileTemp).getParentFile().exists()) {
            new File(fileTemp).getParentFile().mkdirs();
          }

          new File(fileTemp).createNewFile();
        }

        if (!new File(filePath).exists()) {
          if (!new File(filePath).getParentFile().exists()) {
            new File(filePath).getParentFile().mkdirs();
          }

          new File(filePath).mkdir();
        }

        int listSize = list.size();
        list.add(new DownloaderThread(gui, logger, fileUrl, fileMd5, filePath, fileName, fileTemp,
            UtilsFiles.md5(fileTemp), fileType, fileAction));
        list.get(listSize).start();
      }
    }

    for (DownloaderThread temp : list) {
      temp.join();
    }
  }

  private String fillVersion(String url, String date, String version, String id, String type) {
    url = url.replace("{date}", date);
    url = url.replace("{version}", version);
    url = url.replace("{id}", id);
    url = url.replace("{type}", type);

    return url;
  }

  private String fillVersionData(String input, String id, String version, String type,
      boolean replaceSlashes) {
    input = input.replace("{launcherdir}", launcherLocation);
    input = input.replace("{minecraftdir}", minecraftDir);
    input = input.replace("{id}", id);
    input = input.replace("{version}", version);
    input = input.replace("{type}", type);

    if (replaceSlashes) {
      input = input.replace("/", slash);
    }

    return input;
  }
}

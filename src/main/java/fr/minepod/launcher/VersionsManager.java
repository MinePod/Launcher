package fr.minepod.launcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.minepod.utils.UtilsFiles;

public class VersionsManager {
  String launcherDataFile = Config.launcherLocation + Config.slash + "launcher_data.json";

  public Map<String, VersionClass> versions = new LinkedHashMap<>();

  public VersionsManager() throws IOException, ParseException, InterruptedException {
    DownloaderThread downloader = new DownloaderThread(Config.launcherDataUrl, launcherDataFile);
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

        String url = (String) versionObject.get("url");
        String date = (String) versionObject.get("date");
        String version = (String) versionObject.get("version");
        String id = (String) versionObject.get("id");

        url = fillVersionUrl(url, date, version, id);

        this.versions
            .put(date + "-" + version + "-" + id, new VersionClass(url, date, version, id));
      }
    } else {
      CrashReport.show("Pas de versions disponibles.");
    }
  }

  public void installVersion(VersionClass version) throws IOException, ParseException,
      InterruptedException {
    List<DownloaderThread> list = new ArrayList<DownloaderThread>();

    Config.logger.info("Launching installation of version:");
    Config.logger.info(version.getUrl());
    Config.logger.info(version.getDate());
    Config.logger.info(version.getId());

    DownloaderThread downloader = new DownloaderThread(version.getUrl(), launcherDataFile);
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
        String filePath =
            fillVersionData((String) object.get("path"), fileId, fileVersion, fileType, true);
        String fileName =
            fillVersionData((String) object.get("name"), fileId, fileVersion, fileType, true);
        String fileTemp =
            fillVersionData((String) object.get("temp"), fileId, fileVersion, fileType, true);
        String fileUrl =
            fillVersionData((String) object.get("url"), fileId, fileVersion, fileType, false);
        String fileMd5 = (String) object.get("md5");

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
        list.add(new DownloaderThread(fileUrl, fileMd5, filePath, fileName, fileTemp, UtilsFiles
            .md5(fileTemp), fileType, fileAction));
        list.get(listSize).start();
      }
    }

    for (DownloaderThread temp : list) {
      temp.join();
    }

    Launcher.gui.setLoading(false);
  }

  public String fillVersionUrl(String url, String date, String version, String id) {
    url = url.replace("{date}", date);
    url = url.replace("{version}", version);
    url = url.replace("{id}", id);

    return url;
  }

  public String fillVersionData(String input, String id, String version, String type,
      boolean replaceSlashes) {
    input = input.replace("{launcherdir}", Config.launcherLocation);
    input = input.replace("{minecraftdir}", Config.minecraftDir);
    input = input.replace("{id}", id);
    input = input.replace("{version}", version);
    input = input.replace("{type}", type);

    if (replaceSlashes) {
      input = input.replace("/", Config.slash);
    }

    return input;
  }
}

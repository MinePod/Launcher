package fr.minepod.launcher.updater.files;

import fr.minepod.launcher.json.CustomJSONObject;

public class FileClass {
  String id;
  String version;
  String type;
  String md5;

  public FileClass() {}

  public FileClass(CustomJSONObject json) {
    this(json.getString("id"), json.getString("version"), json.getString("type"), json
        .getString("md5"));
  }

  public FileClass(String id, String version, String type, String md5) {
    this.id = id;
    this.version = version;
    this.type = type;
    this.md5 = md5;
  }

  public String getId() {
    return id;
  }

  public String getVersion() {
    return version;
  }

  public String getType() {
    return type;
  }

  public String getMd5() {
    return md5;
  }
}

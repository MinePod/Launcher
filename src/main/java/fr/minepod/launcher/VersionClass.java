package fr.minepod.launcher;

public class VersionClass {
  String url;
  String date;
  String version;
  String id;
  String type;

  public VersionClass(String url, String date, String version, String id, String type) {
    this.url = url;
    this.date = date;
    this.version = version;
    this.id = id;
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public String getDate() {
    return date;
  }

  public String getVersion() {
    return version;
  }

  public String getId() {
    return id;
  }

  public String getType() {
    return type;
  }
}

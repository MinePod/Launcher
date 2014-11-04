package fr.minepod.launcher.updater.versions;

public class VersionClass {
  String url;
  String date;
  String version;
  String id;
  String type;

  boolean skip = false;

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

  public boolean isSkip() {
    return skip;
  }

  public void setSkip(boolean skip) {
    this.skip = skip;
  }
}

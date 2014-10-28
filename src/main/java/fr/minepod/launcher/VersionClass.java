package fr.minepod.launcher;

public class VersionClass {
  String url;
  String date;
  String version;
  String id;
  String type;
  String message;

  public VersionClass(String url, String date, String version, String id, String type,
      String message) {
    this.url = url;
    this.date = date;
    this.version = version;
    this.id = id;
    this.type = type;
    this.message = message;
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

  public String getMessage() {
    return message;
  }
}

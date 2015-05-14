package fr.minepod.launcher;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Utils {
  public static String fillVersion(String url, String date, String version, String id, String type) {
    url = url.replace("{date}", date);
    url = url.replace("{version}", version);
    url = url.replace("{id}", id);
    url = url.replace("{type}", type);

    return url;
  }

  public static String fillVersionData(String input, String id, String version, String type,
      boolean replaceSlashes) {
    input = fillLocations(input, false);

    input = input.replace("{id}", id);
    input = input.replace("{version}", version);
    input = input.replace("{type}", type);

    if (replaceSlashes) {
      input = input.replace("/", File.separator);
    }

    return input;
  }

  public static String fillLocations(String input, boolean replaceSlashes) {
    input = input.replace("{launcherdir}", LauncherConfig.launcherLocation);
    input = input.replace("{minecraftdir}", LauncherConfig.minecraftDir);

    if (replaceSlashes) {
      input = input.replace("/", File.separator);
    }

    return input;
  }

  public static String getStackTrace(Exception e) {
    StringWriter errors = new StringWriter();
    e.printStackTrace(new PrintWriter(errors));
    return errors.toString();
  }
}

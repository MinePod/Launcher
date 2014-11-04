package fr.minepod.launcher;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class LaunchJar {
  public LaunchJar(String path) {
    try {
      String OS = System.getProperty("os.name").toUpperCase();

      if (OS.contains("WIN")) {
        Runtime.getRuntime().exec("java -jar \"" + path + "\"");
      } else if (OS.contains("MAC")) {
        Desktop.getDesktop().open(new File(path));
      } else if (OS.contains("NUX")) {
        Runtime.getRuntime().exec("java -jar \"" + path + "\"");
      } else {
        Runtime.getRuntime().exec("java -jar \"" + path + "\"");
      }

      System.exit(0);
    } catch (IOException e) {
      CrashReport.show(e);
    }
  }
}

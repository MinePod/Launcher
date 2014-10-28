package fr.minepod.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import fr.minepod.utils.UtilsLogger;

public class Config {
  public static String launcherChangelogPage = "http://assets.minepod.fr/launcher/news/news.html";
  public static String launcherDataUrl = "http://assets.minepod.fr/launcher/data.json";
  public static String launcherName = "MinePod";
  public static String profilesVersion = "2";
  public static String launcherVersion;
  public static String launcherBuildTime;

  public static String logFile;
  public static String appDataPath;
  public static String launcherDir;
  public static String launcherTempDir;
  public static String minecraftDir;
  public static String minecraft;
  public static String slash;
  public static String launcherLocation;
  public static String launcherMinecraftJar;
  public static String profilesPath;
  public static String profilesVersionPath;
  public static String bootstrapVersion;
  public static String language;

  public static java.util.logging.Logger logger;

  public static void setConfig() throws SecurityException, IOException {
    String OS = System.getProperty("os.name").toUpperCase();
    if (OS.contains("WIN")) {
      appDataPath = System.getenv("APPDATA");
      launcherDir = "\\." + launcherName;
      minecraft = "\\.minecraft";
    } else if (OS.contains("MAC")) {
      appDataPath = System.getProperty("user.home") + "/Library/Application Support";
      launcherDir = "/" + launcherName;
      minecraft = "/minecraft";
    } else if (OS.contains("NUX")) {
      appDataPath = System.getProperty("user.home");
      launcherDir = "/." + launcherName;
      minecraft = "/.minecraft";
    } else {
      appDataPath = System.getProperty("user.dir");
      launcherDir = "/." + launcherName;
      minecraft = "/.minecraft";
    }

    slash = System.getProperty("file.separator");
    launcherTempDir = launcherDir + slash + "temp";
    minecraftDir = appDataPath + minecraft;
    launcherLocation = appDataPath + launcherDir;
    launcherMinecraftJar = launcherLocation + slash + "minecraft.jar";
    profilesPath = minecraftDir + slash + "launcher_profiles.json";
    profilesVersionPath = launcherLocation + slash + "profiles.txt";
    logFile = launcherLocation + slash + "launcher_logs.txt";

    logger = UtilsLogger.setLogger(logFile);

    InputStream InputStream =
        Launcher.class.getProtectionDomain().getCodeSource().getLocation().openStream();
    JarInputStream JarInputStream = new JarInputStream(InputStream);
    Manifest manifest = JarInputStream.getManifest();
    JarInputStream.close();
    InputStream.close();

    if (manifest != null) {
      Attributes Attributes = manifest.getMainAttributes();
      launcherVersion = Attributes.getValue("Launcher-version");
      launcherBuildTime = "Compilé le " + Attributes.getValue("Build-time");
    } else {
      launcherVersion = "Version de développement";
      launcherBuildTime = "";
    }
  }
}

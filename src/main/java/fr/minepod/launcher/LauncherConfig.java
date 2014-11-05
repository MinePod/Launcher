package fr.minepod.launcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.logging.Logger;

import fr.minepod.utils.UtilsLogger;

public class LauncherConfig {
  public static String launcherChangelogPage = "http://assets.minepod.fr/launcher/news/news.html";
  public static String launcherDataUrl = "http://assets.minepod.fr/launcher/data.json";
  public static String launcherName = "MinePod";
  public static String profilesVersion = "2";
  public static String launcherVersion;
  public static String launcherBuildTime;

  public static String appDataPath;
  public static String launcherDir;
  public static String minecraft;
  public static String launcherTempDir;
  public static String minecraftDir;
  public static String launcherLocation;
  public static String launcherDataFile;
  public static String launcherMinecraftJar;
  public static String profilesPath;
  public static String profilesVersionPath;
  public static String launcherJar;
  public static String launcherMd5;
  public static String bootstrapVersionFile;
  public static String logFile;

  public static Logger logger;

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

    launcherTempDir = launcherDir + File.separator + "temp";
    minecraftDir = appDataPath + minecraft;
    launcherLocation = appDataPath + launcherDir;
    launcherDataFile = launcherLocation + File.separator + "launcher_data.json";
    launcherMinecraftJar = launcherLocation + File.separator + "minecraft.jar";
    profilesPath = minecraftDir + File.separator + "launcher_profiles.json";
    profilesVersionPath = launcherLocation + File.separator + "profiles.txt";
    launcherJar = launcherLocation + File.separator + "launcher.jar";
    launcherMd5 = launcherLocation + File.separator + "launcher.md5";
    bootstrapVersionFile = launcherLocation + File.separator + "bootstrap.txt";
    logFile = launcherLocation + File.separator + "logs.txt";

    if (!new File(launcherLocation).exists()) {
      new File(launcherLocation).mkdir();
    }

    logger = UtilsLogger.setLogger(logFile);
  }

  public static void setVersionInfos() throws IOException {
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

package fr.minepod.launcher;

import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import fr.minepod.launcher.gui.LauncherGui;
import fr.minepod.launcher.updater.versions.VersionsUpdater;
import fr.minepod.utils.UtilsFiles;

public class Launcher {
  public static VersionsUpdater versionsUpdater;
  public static LauncherGui gui;

  public static void main(String[] args) throws IOException {
    if (args.length != 0) {
      start(args[0]);
    } else {
      start();
    }
  }

  public static void start() {
    start("unknown");
  }

  public static void start(String args) {
    try {
      LauncherConfig.setLauncherConfig();
      LauncherConfig.logger.setUseParentHandlers(false);
      LauncherConfig.bootstrapVersion = args;

      if (System.getProperty("threaded") == null
          || !System.getProperty("threaded").equalsIgnoreCase("no")) {
        LauncherConfig.logger.info("Threading status: threaded");
      } else {
        LauncherConfig.threaded = false;

        LauncherConfig.logger.info("Threading status: not threaded - debug?");
      }

      if (!new File(LauncherConfig.launcherLocation).exists()) {
        new File(LauncherConfig.launcherLocation).mkdir();
      }

      LauncherConfig.logger.info("Downloading versions informations...");
      versionsUpdater = new VersionsUpdater(LauncherConfig.logger);
      versionsUpdater.init(gui);
      gui = new LauncherGui(versionsUpdater.versions);
      gui.initGui();
      gui.setLoading(true);
      LauncherConfig.logger.info("Downloaded versions informations.");

      checkProfile();
      gui.finish();
      gui.setLoading(false);
      gui.setButtonState(true);
    } catch (SecurityException | IOException | ParseException | InterruptedException e) {
      CrashReport.show(e);
    }
  }

  public static void checkProfile() throws IOException, ParseException {
    try {
      if (new File(LauncherConfig.profilesPath).exists()
          && !new UtilsFiles().readFile(LauncherConfig.profilesPath).isEmpty()) {
        Profile profile = new Profile();

        if (new File(LauncherConfig.profilesVersionPath).exists()) {
          String version = new UtilsFiles().readFile(LauncherConfig.profilesVersionPath);

          if (version != null && version.contains(LauncherConfig.profilesVersion)) {
            profile.set();
          } else {
            LauncherConfig.logger.info("Current profile version: " + version);
            LauncherConfig.logger.info("New profile version: " + LauncherConfig.profilesVersion);

            profile.update();
            new UtilsFiles().writeFile(LauncherConfig.profilesVersionPath,
                LauncherConfig.profilesVersion);
          }
        } else {
          LauncherConfig.logger.warning("The profile version doesn't exist, creating a new one...");

          profile.set();
          new UtilsFiles().writeFile(LauncherConfig.profilesVersionPath,
              LauncherConfig.profilesVersion);
        }
      } else {
        LauncherConfig.logger.severe("The profile file doesn't exist");
        CrashReport.show("Le jeu doit avoir été lancé au moins une fois avant.");
      }
    } catch (IOException e) {
      CrashReport.show(e);
    }
  }
}

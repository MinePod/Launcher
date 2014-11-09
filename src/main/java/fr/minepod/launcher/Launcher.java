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
    if (args.length != 0)
      start(args[0]);
    else
      start();
  }

  public static void start() {
    start("unknown");
  }

  public static void start(String args) {
    try {
      Config.bootstrapVersion = args;
      Config.setConfig();

      if (!new File(Config.launcherLocation).exists()) {
        new File(Config.launcherLocation).mkdir();
      }

      Config.logger.info("Downloading versions informations...");
      versionsUpdater = new VersionsUpdater(Config.logger);
      versionsUpdater.init(gui);
      gui = new LauncherGui(versionsUpdater.versions);
      gui.initGui();
      gui.setLoading(true);
      Config.logger.info("Downloaded versions informations.");

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
      if (new File(Config.profilesPath).exists()
          && !new UtilsFiles().readFile(Config.profilesPath).isEmpty()) {
        Profile profile = new Profile();

        if (new File(Config.profilesVersionPath).exists()) {
          String version = new UtilsFiles().readFile(Config.profilesVersionPath);

          if (version != null && version.contains(Config.profilesVersion)) {
            profile.set();
          } else {
            Config.logger.info("Current profile version: " + version);
            Config.logger.info("New profile version: " + Config.profilesVersion);

            profile.update();
            new UtilsFiles().writeFile(Config.profilesVersionPath, Config.profilesVersion);
          }
        } else {
          Config.logger.warning("The profile version doesn't exist, creating a new one...");

          profile.set();
          new UtilsFiles().writeFile(Config.profilesVersionPath, Config.profilesVersion);
        }
      } else {
        Config.logger.severe("The profile file doesn't exist");
        CrashReport.show("Le jeu doit avoir été lancé au moins une fois avant.");
      }
    } catch (IOException e) {
      CrashReport.show(e);
    }
  }
}

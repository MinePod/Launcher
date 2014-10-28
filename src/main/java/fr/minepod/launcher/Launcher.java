package fr.minepod.launcher;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

import fr.minepod.utils.UtilsFiles;

public class Launcher {
  public static VersionsManager versionsManager;
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

      versionsManager =
          new VersionsManager(Config.launcherLocation, Config.launcherDataUrl, Config.minecraftDir,
              Config.slash, Config.logger);
      versionsManager.initVersionsManager();
      gui = new LauncherGui(versionsManager.versions);
      gui.initGui();
      gui.setLoading(true);
      Config.logger.info("Downloaded versions informations");

      checkProfile();
      gui.finish();
      gui.setLoading(false);
      gui.setButtonState(true);
    } catch (SecurityException | IOException | ParseException | InterruptedException e) {
      CrashReport.show(e.toString());
    }
  }

  public static void checkProfile() throws IOException, ParseException {
    try {
      if (new File(Config.profilesPath).exists()
          && !UtilsFiles.readFile(Config.profilesPath).isEmpty()) {
        Profile profile = new Profile();

        if (new File(Config.profilesVersionPath).exists()) {
          if (UtilsFiles.readFile(Config.profilesVersionPath).contains(Config.profilesVersion)) {
            profile.set();
          } else {
            Config.logger.info("Current profile version: "
                + UtilsFiles.readFile(Config.profilesVersionPath));
            Config.logger.info("New profile version: " + Config.profilesVersion);

            profile.update();
            UtilsFiles.writeFile(Config.profilesVersionPath, Config.profilesVersion);
          }
        } else {
          Config.logger.warning("The profile version doesn't exist, creating a new one...");

          profile.set();
          UtilsFiles.writeFile(Config.profilesVersionPath, Config.profilesVersion);
        }
      } else {
        Config.logger.severe("The profile file doesn't exist");
        JOptionPane.showMessageDialog(null,
            "Lancez le jeu via le launcher Mojang, fermez-le et relancez le launcher "
                + Config.launcherName, "Attention", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
      }
    } catch (IOException e) {
      CrashReport.show(e.toString());
    }
  }
}

package fr.minepod.launcher;

import javax.swing.JOptionPane;

public class CrashReport {
  public static void show(Exception exception) {
    show(Utils.getStackTrace(exception));
  }

  public static void show(String exception) {
    show(exception, true);
  }

  public static void show(String exception, boolean exit) {
    if (LauncherConfig.logger != null) {
      LauncherConfig.logger.severe(exception);
    }

    JOptionPane.showMessageDialog(null, exception, "Erreur", JOptionPane.ERROR_MESSAGE);

    if (exit) {
      System.exit(0);
    }
  }
}

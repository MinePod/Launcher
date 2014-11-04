package fr.minepod.launcher;

import javax.swing.JOptionPane;

public class CrashReport {
  public static void show(Exception exception) {
    Config.logger.info(Thread.currentThread().getStackTrace()[2].getClassName());
    show(exception.getLocalizedMessage());
  }

  public static void show(String exception) {
    show(exception, true);
  }

  public static void show(String exception, boolean exit) {
    if (Config.logger != null) {
      Config.logger.severe(exception);
    }

    JOptionPane.showMessageDialog(null, exception, "Erreur", JOptionPane.ERROR_MESSAGE);

    if (exit) {
      System.exit(0);
    }
  }
}

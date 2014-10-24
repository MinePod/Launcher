package fr.minepod.launcher;

import javax.swing.JOptionPane;

public class CrashReport {
  public static void show(String exception) {
    if (Config.logger != null) {
      Config.logger.severe(exception);
    }

    JOptionPane.showMessageDialog(null, exception, "Erreur", JOptionPane.ERROR_MESSAGE);

    System.exit(0);
  }
}

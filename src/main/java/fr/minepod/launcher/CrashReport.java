package fr.minepod.launcher;

import javax.swing.JOptionPane;

public class CrashReport {
  public CrashReport(String exception, String when) {
    if (Config.logger != null) {
      Config.logger.severe(exception);
    }

    String msg = exception + "\n" + Langage.WHEN.toString() + when;
    JOptionPane.showMessageDialog(null, msg, Langage.ERROR.toString(), JOptionPane.ERROR_MESSAGE);

    System.exit(0);
  }
}

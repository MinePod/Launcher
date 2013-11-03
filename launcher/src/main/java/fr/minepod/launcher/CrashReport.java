package fr.minepod.launcher;

import java.awt.HeadlessException;
import java.io.IOException;

public class CrashReport {
	public static String DebugFilePath = Config.DebugFilePath;
	
	public static void SendReport(String exception, String when) {
		try {
			Config.Logger.severe(exception);
			javax.swing.JOptionPane.showMessageDialog(null, exception + "\n" +  Langage.WHEN.toString() + when + "\n\n\n" + Langage.DEBUGINFORMATIONS.toString() + ":\n\n" + fr.minepod.Utils.Files.ReadFile(DebugFilePath), Langage.ERROR.toString(), javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (HeadlessException e) {
			Config.Logger.severe(e.toString());
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(),  Langage.ERROR.toString(), javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			Config.Logger.severe(e.toString());
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(),  Langage.ERROR.toString(), javax.swing.JOptionPane.ERROR_MESSAGE);
		}
        System.exit(0);
	}
}

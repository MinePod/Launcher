package fr.minepod.launcher;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;

import fr.minepod.launcher.Config;
import fr.minepod.launcher.Langage;

public class CrashReport {
	public static void SendReport(String exception, String when) {
		try {
			if(Config.Logger != null)
				Config.Logger.severe(exception);
			
			String debugMsg = "";
			
			if(new File(Config.DebugFilePath).exists())
				debugMsg = fr.minepod.Utils.Files.ReadFile(Config.DebugFilePath);
			
			javax.swing.JOptionPane.showMessageDialog(null, exception + "\n" +  Langage.WHEN.toString() + when + "\n\n\n" + Langage.DEBUGINFORMATIONS.toString() + ":\n\n" + debugMsg, Langage.ERROR.toString(), javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (HeadlessException e) {
			if(Config.Logger != null)
				Config.Logger.severe(e.toString());
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(),  Langage.ERROR.toString(), javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			if(Config.Logger != null)
				Config.Logger.severe(e.toString());
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(),  Langage.ERROR.toString(), javax.swing.JOptionPane.ERROR_MESSAGE);
		}
        System.exit(0);
	}
}

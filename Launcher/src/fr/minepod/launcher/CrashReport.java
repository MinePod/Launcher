package fr.minepod.launcher;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class CrashReport {
	public String DebugFilePath = new Config().DebugFilePath;
	public void SendReport(String exception, String when) {
		try {
			javax.swing.JOptionPane.showMessageDialog(null, exception + "\nWhen " + when + "\n\n\nDebug informations:\n\n" + new ClassFile().ReadFile(DebugFilePath), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (HeadlessException e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
        System.exit(0);
	}
}

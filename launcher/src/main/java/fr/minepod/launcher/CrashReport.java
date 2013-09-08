package fr.minepod.launcher;

import java.awt.HeadlessException;
import java.io.IOException;

public class CrashReport {
	public static String DebugFilePath = Config.DebugFilePath;
	
	public static void SendReport(String exception, String when) {
		try {
			javax.swing.JOptionPane.showMessageDialog(null, exception + "\n" +  fr.minepod.translate.Translate.get("WhenExecute") + " " + when + "\n\n\n" + fr.minepod.translate.Translate.get("DebugInformations") + ":\n\n" + ClassFile.ReadFile(DebugFilePath), fr.minepod.translate.Translate.get("Error"), javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (HeadlessException e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(),  fr.minepod.translate.Translate.get("Error"), javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(),  fr.minepod.translate.Translate.get("Error"), javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		System.out.println(exception);
        System.exit(0);
	}
}

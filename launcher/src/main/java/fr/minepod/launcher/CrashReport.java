package fr.minepod.launcher;

import java.awt.HeadlessException;
import java.io.IOException;

public class CrashReport {
	public static String DebugFilePath = Config.DebugFilePath;
	
	public static void SendReport(String exception, String when) {
		try {
			System.out.println(exception);
			javax.swing.JOptionPane.showMessageDialog(null, exception + "\n" +  Langage.WHEN.toString() + when + "\n\n\n" + Langage.DEBUGINFORMATIONS.toString() + ":\n\n" + ClassFile.ReadFile(DebugFilePath), Langage.ERROR.toString(), javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (HeadlessException e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(),  Langage.ERROR.toString(), javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			String Message = Langage.ERROR.toString();
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(),  Message, javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		System.out.println(exception);
        System.exit(0);
	}
}

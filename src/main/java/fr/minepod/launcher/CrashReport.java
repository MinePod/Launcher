package fr.minepod.launcher;

public class CrashReport {
	public CrashReport(String exception, String when) {
		if(Config.Logger != null)
			Config.Logger.severe(exception);
		
		String msg = exception + "\n" +  Langage.WHEN.toString() + when + "\n\n\n";
		msg+= Langage.DEBUGINFORMATIONS.toString() + ":\n\n" + new Debug().GetDebugString();
		
		javax.swing.JOptionPane.showMessageDialog(null, msg, Langage.ERROR.toString(), javax.swing.JOptionPane.ERROR_MESSAGE);
        System.exit(0);
	}
}

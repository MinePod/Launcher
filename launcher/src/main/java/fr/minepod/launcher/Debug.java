package fr.minepod.launcher;

import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.simple.JSONPrettyPrint;

public class Debug extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String DebugFilePath = Config.DebugFilePath;
	
	public void SetDebug() throws IOException {
		Map<String, String> obj = new LinkedHashMap<String, String>();
		obj.put("OS-name", System.getProperty("os.name", "Unknown"));
		obj.put("OS-arch", System.getProperty("os.arc", "Unknown"));
		obj.put("OS-version", System.getProperty("os.version", "Unknown"));
		obj.put("Java-home", System.getProperty("java.home", "Unknown"));
		obj.put("Java-vendor", System.getProperty("java.vendor", "Unknown"));
		obj.put("Java-version", System.getProperty("java.version", "Unknown"));
		obj.put("File-separator", System.getProperty("file.separator", "Unknown"));
		obj.put("User-dir", System.getProperty("user.dir"));
		obj.put("User-home", System.getProperty("user.home"));
		obj.put("User-name", System.getProperty("user.name"));
		obj.put("Launcher-version", Config.LauncherVersion);
		obj.put("Launcher-location", Config.LauncherLocation);
		obj.put("Profiles-version", Config.ProfilesVersion);
		obj.put("Profiles-version-location", Config.ProfilesVersionPath);
		obj.put("Minecraft-location", Config.MinecraftAppData);
		obj.put("Bootstrap-version", Config.BootstrapVersion);
		obj.put("Time", new Date().toString());
		
		if(new File(DebugFilePath).exists()) {
			new File(DebugFilePath).delete();
		}
		
		FileWriter file = new FileWriter(DebugFilePath);
		file.write(JSONPrettyPrint.toJSONString(obj));
		file.flush();
		file.close();
	}
	
	public void EnableConsole() {
	    try {
	    	JTextArea textArea = new JTextArea();
	        textArea.setEditable(false);
	    	
	        JFrame j = new JFrame(fr.minepod.translate.Translate.get("DebugConsoleGuiName"));

			JPanel b1 = new JPanel();
			b1.setLayout(new BoxLayout(b1, BoxLayout.LINE_AXIS));
			b1.add(textArea);
			
		    JPanel b2 = new JPanel();
		    b2.setLayout(new BoxLayout(b2, BoxLayout.PAGE_AXIS));
		    b2.add(new JScrollPane(textArea));
		    b2.add(b1);
		
		    j.setContentPane(b2);
		
		    j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		      
		    j.setSize(new Dimension(700, 500));
		
		    j.setLocationRelativeTo(null);
		    j.setVisible(true);
		    
	        PrintStream PrintStream = new PrintStream(new Console(textArea));
	        System.setOut(PrintStream);
	        System.setErr(PrintStream);
	        System.out.println("Opened debug console");
	      } catch(Exception e) {
	    	  e.toString();
	      }
	}
}

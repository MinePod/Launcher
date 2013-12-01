package fr.minepod.launcher;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;



import org.json.simple.JSONPrettyPrint;

public class Debug {
	
	public void SetDebug() {
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
			
		try {
			fr.minepod.Utils.Files.WriteFile(Config.DebugFilePath, JSONPrettyPrint.toJSONString(obj));
		} catch (IOException e) {
			CrashReport.SendReport(e.toString(), "setting debug informations");
		}
	}
}
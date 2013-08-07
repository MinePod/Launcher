package fr.minepod.launcher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.JSONPrettyPrint;

public class Debug {
	private Config Config = new Config();
	private String DebugFilePath = Config.DebugFilePath;
	
	public void Set() throws IOException {
		JSONPrettyPrint obj = new JSONPrettyPrint();
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
		
		if(new File(DebugFilePath).exists()) {
			new File(DebugFilePath).delete();
		}
		
		FileWriter file = new FileWriter(DebugFilePath);
		file.write(obj.toJSONString());
		file.flush();
		file.close();
	}
}

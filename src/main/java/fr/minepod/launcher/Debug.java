package fr.minepod.launcher;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONPrettyPrint;

public class Debug {
  public String getDebugString() {
    return JSONPrettyPrint.toJSONString(getDebugMap());
  }

  public Map<String, String> getDebugMap() {
    Map<String, String> obj = new HashMap<String, String>();

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
    obj.put("Launcher-version", Config.launcherVersion);
    obj.put("Launcher-location", Config.launcherLocation);
    obj.put("Profiles-version", Config.profilesVersion);
    obj.put("Profiles-version-location", Config.profilesVersionPath);
    obj.put("Minecraft-location", Config.minecraftDir);
    obj.put("Bootstrap-version", Config.bootstrapVersion);
    obj.put("Time", new Date().toString());

    return obj;
  }
}

package fr.minepod.launcher;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Profile {
	public void set(String LauncherName, String ProfilesPath, String LauncherLocation) throws IOException, ParseException {
		Config.logger.info("Detecting profile...");
		String profile = fr.minepod.utils.UtilsFiles.readFile(ProfilesPath);

		if(!profile.contains(LauncherName)) {
			Config.logger.info("Adding profile...");
			JSONParser parser = new JSONParser();	 
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(ProfilesPath)); 
			String selectedProfile = (String) jsonObject.get("selectedProfile");
			JSONObject profiles = (JSONObject) jsonObject.get("profiles");
			JSONObject currentProfile = (JSONObject) profiles.get(selectedProfile);
			String playerUUID = (String) currentProfile.get("playerUUID");

			profile = profile.substring(0, 19) + "    \"" + LauncherName + "\": { \"name\": \"" + LauncherName + "\", \"gameDir\": \"" + LauncherLocation.replace("\\", "\\\\") + "\", \"lastVersionId\": \"" + LauncherName + "\", \"playerUUID\": \"" + playerUUID + "\", \"javaArgs\": \"-Xmx512M -Dfml.ignoreInvalidMinecraftCertificates\u003dtrue -Dfml.ignorePatchDiscrepancies\u003dtrue\"    }," + profile.substring(20);
			profile = profile.replace("\"selectedProfile\": \"" + selectedProfile + "\",", "\"selectedProfile\": \"" + LauncherName + "\",");
		}

		new File(ProfilesPath).delete();
		fr.minepod.utils.UtilsFiles.writeFile(ProfilesPath, profile);	
	}

	public void update(String LauncherName, String ProfilesPath, String LauncherLocation) throws IOException, ParseException {
		String Profile = fr.minepod.utils.UtilsFiles.readFile(ProfilesPath);

		if(Profile.contains(LauncherName)) {
			JSONParser parser = new JSONParser();	
			Object obj;
			obj = parser.parse(new FileReader(ProfilesPath));
			JSONObject jsonObject = (JSONObject) obj;	 
			jsonObject.remove("MinePod");
			new File(ProfilesPath).delete();
			fr.minepod.utils.UtilsFiles.writeFile(ProfilesPath, jsonObject.toJSONString());
		}

		set(LauncherName, ProfilesPath, LauncherLocation);
	}
}

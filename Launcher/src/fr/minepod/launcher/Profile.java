package fr.minepod.launcher;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Profile {
	public void Set(String LauncherName, String ProfilesPath, String LauncherLocation) throws IOException, ParseException {
		System.out.println("Detecting profile...");
		String Profile = ClassFile.ReadFile(ProfilesPath);
		System.out.println("Current profile: " + Profile);
		if(!Profile.contains(LauncherName)) {
			System.out.println("Adding profile...");
		JSONParser parser = new JSONParser();	 
		Object obj = parser.parse(new FileReader(ProfilesPath));
		JSONObject jsonObject = (JSONObject) obj;	 
		String selectedProfile = (String) jsonObject.get("selectedProfile");
		System.out.println(selectedProfile);
		JSONObject profiles = (JSONObject) jsonObject.get("profiles");
		JSONObject currentProfile = (JSONObject) profiles.get(selectedProfile);
		String playerUUID = (String) currentProfile.get("playerUUID");
		System.out.println(playerUUID);
		
		Profile = Profile.substring(0, 19) + "    \"" + LauncherName + "\": { \"name\": \"" + LauncherName + "\", \"gameDir\": \"" + LauncherLocation.replace("\\", "\\\\") + "\", \"lastVersionId\": \"" + LauncherName + "\", \"playerUUID\": \"" + playerUUID + "\", \"javaArgs\": \"-Xmx512M -Dfml.ignoreInvalidMinecraftCertificates\u003dtrue -Dfml.ignorePatchDiscrepancies\u003dtrue\"    }," + Profile.substring(20);
		Profile = Profile.replace("\"selectedProfile\": \"" + selectedProfile + "\",", "\"selectedProfile\": \"" + LauncherName + "\",");
		}
		new File(ProfilesPath).delete();
		ClassFile.WriteFile(ProfilesPath, Profile);	
	}
	
	public void Update(String LauncherName, String ProfilesPath, String LauncherLocation) throws IOException, ParseException {
		String Profile = ClassFile.ReadFile(ProfilesPath);
		if(Profile.contains(LauncherName)) {
			JSONParser parser = new JSONParser();	
			Object obj;
			try {
				obj = parser.parse(new FileReader(ProfilesPath));
				JSONObject jsonObject = (JSONObject) obj;	 
				jsonObject.remove("MinePod");
				new File(ProfilesPath).delete();
				ClassFile.WriteFile(ProfilesPath, jsonObject.toJSONString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Set(LauncherName, ProfilesPath, LauncherLocation);
	}
}

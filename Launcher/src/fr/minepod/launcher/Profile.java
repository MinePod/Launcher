package fr.minepod.launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Profile {
	public void Profile(String LauncherName, String ProfilesPath, String LauncherLocation) throws IOException {
		System.out.println("Detecting profile...");
		String Profile = ClassFile.ReadFile(ProfilesPath);
		System.out.println("Current profile: " + Profile);
		if(!Profile.contains(LauncherName + "\",\n      \"gameDir\":")) {
			if(!Profile.contains(LauncherName)) {
				System.out.println("Adding profile...");
				JSONParser parser = new JSONParser();	 
				try { 
					Object obj = parser.parse(new FileReader(ProfilesPath));
					JSONObject jsonObject = (JSONObject) obj;	 
					String selectedProfile = (String) jsonObject.get("selectedProfile");
					System.out.println(selectedProfile);
					JSONObject profiles = (JSONObject) jsonObject.get("profiles");
					JSONObject currentProfile = (JSONObject) profiles.get(selectedProfile);
					String playerUUID = (String) currentProfile.get("playerUUID");
					System.out.println(playerUUID);
					
					Profile = Profile.substring(0, 19) + "    \"" + LauncherName + "\": {\n      \"name\": \"" + LauncherName + "\",\n      \"gameDir\": \"Launcher_GameDir\",\n      \"lastVersionId\": \"" + LauncherName + "\",\n      \"playerUUID\": \"" + playerUUID + "\",\n      \"javaArgs\": \"-Xmx1G -Dfml.ignoreInvalidMinecraftCertificates\u003dtrue -Dfml.ignorePatchDiscrepancies\u003dtrue\"\n    },\n" + Profile.substring(20);
					Profile = Profile.replace("\"selectedProfile\": \"" + selectedProfile + "\",", "\"selectedProfile\": \"" + LauncherName + "\",");
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Modifying profile...");
				Profile = Profile.replace("\"name\": \"" + LauncherName + "\"", "\"name\": \"" + LauncherName + "\",\n \"gameDir\": \"Launcher_GameDir\"");
			}
		}
		Profile = Profile.replace("\"gameDir\": \"Launcher_GameDir\",", "\"gameDir\": \"" + LauncherLocation.replace("\\", "\\\\") + "\",");
		new File(ProfilesPath).delete();
		ClassFile.WriteFile(ProfilesPath, Profile);	
	}
}

package fr.minepod.launcher;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Profile {
  @SuppressWarnings("unchecked")
  public void set(String LauncherName, String ProfilesPath, String LauncherLocation)
      throws IOException, ParseException {
    Config.logger.info("Detecting profile...");
    String profile = fr.minepod.utils.UtilsFiles.readFile(ProfilesPath);

    JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(ProfilesPath));

    if (!jsonObject.containsKey("MinePod")) {
      Config.logger.info("Adding profile...");
      String selectedProfile = (String) jsonObject.get("selectedProfile");
      JSONObject profiles = (JSONObject) jsonObject.get("profiles");
      JSONObject currentProfile = (JSONObject) profiles.get(selectedProfile);
      String playerUUID = (String) currentProfile.get("playerUUID");

      JSONObject newProfile = new JSONObject();
      newProfile.put("name", LauncherName);
      newProfile.put("gameDir", LauncherLocation.replace("\\", "\\\\"));
      newProfile.put("lastVersionId", LauncherName);
      newProfile.put("playerUUID", playerUUID);
      // TODO: Let this be totally configurable in a small config file
      newProfile
          .put(
              "javaArgs",
              "-Xmx512M -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true -Dforge.forceNoStencil=true");

      profiles.put("MinePod", newProfile);

      jsonObject.put("profiles", profiles);
      jsonObject.put("selectedProfile", LauncherName);
      profile = jsonObject.toJSONString();
    }

    new File(ProfilesPath).delete();
    fr.minepod.utils.UtilsFiles.writeFile(ProfilesPath, profile);
  }

  public void update(String LauncherName, String ProfilesPath, String LauncherLocation)
      throws IOException, ParseException {
    String Profile = fr.minepod.utils.UtilsFiles.readFile(ProfilesPath);

    if (Profile.contains(LauncherName)) {
      JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(ProfilesPath));
      jsonObject.remove("MinePod");
      new File(ProfilesPath).delete();
      fr.minepod.utils.UtilsFiles.writeFile(ProfilesPath, jsonObject.toJSONString());
    }

    set(LauncherName, ProfilesPath, LauncherLocation);
  }
}

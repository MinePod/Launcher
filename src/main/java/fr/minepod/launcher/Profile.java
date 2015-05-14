package fr.minepod.launcher;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.minepod.utils.UtilsFiles;

public class Profile {
  public void set() throws IOException, ParseException {
    set(false);
  }

  public void update() throws IOException, ParseException {
    set(true);
  }

  @SuppressWarnings("unchecked")
  public void set(boolean update) throws IOException, ParseException {
    LauncherConfig.logger.info("Detecting profile...");
    String profile = new UtilsFiles().readFile(LauncherConfig.profilesPath);

    JSONObject jsonObject =
        (JSONObject) new JSONParser().parse(new FileReader(LauncherConfig.profilesPath));

    if (update || !((JSONObject) jsonObject.get("profiles")).containsKey("MinePod")) {
      LauncherConfig.logger.info("Adding profile...");
      String selectedProfile = (String) jsonObject.get("selectedProfile");
      JSONObject profiles = (JSONObject) jsonObject.get("profiles");
      JSONObject currentProfile = (JSONObject) profiles.get(selectedProfile);
      String playerUUID = (String) currentProfile.get("playerUUID");

      JSONObject newProfile;
      if (update && profiles.containsKey("MinePod")) {
        newProfile = (JSONObject) profiles.get("MinePod");
      } else {
        newProfile = new JSONObject();
      }
      newProfile.put("name", LauncherConfig.launcherName);
      newProfile.put("gameDir", LauncherConfig.launcherLocation.replace("\\", "\\\\"));
      newProfile.put("lastVersionId", LauncherConfig.launcherName);
      newProfile.put("playerUUID", playerUUID);
      // TODO: Let this be totally configurable in a small config file
      newProfile
          .put(
              "javaArgs",
              "-Xmx512M -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true -Dforge.forceNoStencil=true");

      profiles.put("MinePod", newProfile);

      jsonObject.put("profiles", profiles);
      jsonObject.put("selectedProfile", LauncherConfig.launcherName);
      profile = jsonObject.toJSONString();
    }

    new File(LauncherConfig.profilesPath).delete();
    new UtilsFiles().writeFile(LauncherConfig.profilesPath, profile);

    LauncherConfig.logger.info("Waiting for user action...");
  }
}

package fr.minepod.launcher;

public class Config {
	 public static String LibrariesLatestVersionUrl = "http://assets.minepod.fr/launcher/libraries.php";
	 public static String VersionsLatestVersionUrl = "http://assets.minepod.fr/launcher/versions.php";
	 public static String ModsLatestVersionUrl = "http://assets.minepod.fr/launcher/mods.php";
	 public static String ResourcepacksLatestVersionUrl = "http://assets.minepod.fr/launcher/textures.php";
	 public static String MinecraftLatestVersionUrl = "http://assets.minepod.fr/launcher/minecraft.php";
	 public static String LauncherNewsHtmlUrl = "http://assets.minepod.fr/launcher/news/news.html";
	 public static String LauncherNewsCssUrl = "http://assets.minepod.fr/launcher/news/news.css";
	 public static String GetMd5FileUrl = "http://assets.minepod.fr/launcher/md5.php?file=";
	 public static String LauncherName = "MinePod";
	 public static String ProfilesVersion = "1";
	 public static String LauncherVersion;
	 public static String LauncherBuildTime;
	 
	 public static String AppDataPath;
	 public static String LauncherDir;
	 public static String Minecraft;
	 public static String Slash;
	 public static String LauncherLocation;
	 public static String MinecraftAppData;
	 public static String LauncherZippedLibraries;
	 public static String LauncherZippedVersions;
	 public static String LauncherZippedMods;
	 public static String LauncherZippedResourcepacks;
	 public static String LauncherMinecraftJar;
	 public static String LauncherNewsHtml;
	 public static String LauncherNewsCss;
	 public static String ProfilesPath;
	 public static String ProfilesVersionPath;
	 public static String DebugFilePath;
	 public static String BootstrapVersion;
	 public static String Language;
	 public static Gui Gui;
	 
	 public void SetConfig() {
			String OS = System.getProperty("os.name").toUpperCase();
			if(OS.contains("WIN")) {
				Config.AppDataPath = System.getenv("APPDATA");
				Config.LauncherDir = "\\." + LauncherName;
				Config.Minecraft = "\\.minecraft";
			} else if(OS.contains("MAC")) {
				Config.AppDataPath = System.getProperty("user.home") + "/Library/Application Support";
				Config.LauncherDir = "/" + LauncherName;
				Config.Minecraft = "/minecraft";
			} else if(OS.contains("NUX")) {
				Config.AppDataPath = System.getProperty("user.home");
				Config.LauncherDir = "/." + LauncherName;
				Config.Minecraft = "/.minecraft";
			} else {
				Config.AppDataPath =  System.getProperty("user.dir");
				Config.LauncherDir = "/." + LauncherName;
				Config.Minecraft = "/.minecraft";
			}
			
			Config.Slash = System.getProperty("file.separator");
			
			System.out.println(AppDataPath);
			
			Config.LauncherLocation = AppDataPath + LauncherDir;
			Config.MinecraftAppData = AppDataPath + Minecraft;
			Config.LauncherZippedLibraries = LauncherLocation + Slash + "Libraries.zip";
			Config.LauncherZippedVersions = LauncherLocation + Slash + "Versions.zip";
			Config.LauncherZippedMods = LauncherLocation + Slash + "Mods.zip";
			Config.LauncherZippedResourcepacks = LauncherLocation + Slash + "Resourcespacks.zip";
			Config.LauncherMinecraftJar = LauncherLocation + Slash + "Minecraft.jar";
			Config.LauncherNewsHtml = LauncherLocation + Slash + "news.html";
			Config.LauncherNewsCss = LauncherLocation + Slash + "news.css";
			Config.ProfilesPath = MinecraftAppData + Slash + "launcher_profiles.json";
			Config.ProfilesVersionPath =  LauncherLocation + Slash + "profiles.txt";
			Config.DebugFilePath = LauncherLocation + Slash + "debug.json";
			
			Config.Language = "fr_FR";
			// TODO Add option to change language
	 }
	 
	 public void SetBootstrapVersion(String Version) {
		 Config.BootstrapVersion = Version;
	 }
	 
	 public void SetLauncherVersion(String Version) {
		 Config.LauncherVersion = Version;
	 }
	 
	 public void SetLauncherBuildTime(String BuildTime) {
		 Config.LauncherBuildTime = BuildTime;
	 }
	 
	 public String getInfos() {
		 String infos = "Version " + LauncherVersion + " through Bootstrap " + Config.BootstrapVersion + "\n";
		 infos += "\n";
		 infos += "By DarkShimy for MinePod.fr";
		 return infos;
	 }
}

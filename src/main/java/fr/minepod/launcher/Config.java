package fr.minepod.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class Config {
	public static String launcherChangelogPage = "http://assets.minepod.fr/launcher/news/news.html";
	public static String launcherDataUrl = "http://assets.minepod.fr/launcher/data.json";
	public static String launcherName = "MinePod";
	public static String profilesVersion = "1";
	public static String launcherVersion;
	public static String launcherBuildTime;

	public static String logFile;
	public static String appDataPath;
	public static String launcherDir;
	public static String launcherTempDir;
	public static String minecraftDir;
	public static String minecraft;
	public static String slash;
	public static String launcherLocation;
	public static String launcherMinecraftJar;
	public static String profilesPath;
	public static String profilesVersionPath;
	public static String bootstrapVersion;
	public static String language;
	public static Gui gui;

	public static java.util.logging.Logger logger;

	public static void SetConfig() {
		String OS = System.getProperty("os.name").toUpperCase();
		if(OS.contains("WIN")) {
			Config.appDataPath = System.getenv("APPDATA");
			Config.launcherDir = "\\." + launcherName;
			Config.minecraft = "\\.minecraft";
		} else if(OS.contains("MAC")) {
			Config.appDataPath = System.getProperty("user.home") + "/Library/Application Support";
			Config.launcherDir = "/" + launcherName;
			Config.minecraft = "/minecraft";
		} else if(OS.contains("NUX")) {
			Config.appDataPath = System.getProperty("user.home");
			Config.launcherDir = "/." + launcherName;
			Config.minecraft = "/.minecraft";
		} else {
			Config.appDataPath =  System.getProperty("user.dir");
			Config.launcherDir = "/." + launcherName;
			Config.minecraft = "/.minecraft";
		}

		Config.slash = System.getProperty("file.separator");
		Config.launcherTempDir = launcherDir + slash + "temp";
		Config.minecraftDir = appDataPath + minecraft;
		Config.launcherLocation = appDataPath + launcherDir;
		Config.launcherMinecraftJar = launcherLocation + slash + "minecraft.jar";
		Config.profilesPath = minecraftDir + slash + "launcher_profiles.json";
		Config.profilesVersionPath =  launcherLocation + slash + "profiles.txt";
		Config.logFile = launcherLocation + slash + "launcher_logs.txt";
	}

	public static void setup() throws SecurityException, IOException {
		logger = new fr.minepod.utils.UtilsLogger().SetLogger(Config.logFile);

		InputStream InputStream = Launcher.class.getProtectionDomain().getCodeSource().getLocation().openStream();
		JarInputStream JarInputStream = new JarInputStream(InputStream);
		Manifest manifest = JarInputStream.getManifest();
		JarInputStream.close();
		InputStream.close();

		if(manifest != null) {
			Attributes Attributes = manifest.getMainAttributes();
			setLauncherVersion(Attributes.getValue("Launcher-version"));
			setLauncherBuildTime(Langage.COMPILEDON.toString() + Attributes.getValue("Build-time"));
		} else {
			setLauncherVersion(Langage.DEVELOPMENTVERSION.toString());
			setLauncherBuildTime("");
		}
	}

	public static void setBootstrapVersion(String Version) {
		Config.bootstrapVersion = Version;
	}

	public static void setLauncherVersion(String Version) {
		Config.launcherVersion = Version;
	}

	public static void setLauncherBuildTime(String BuildTime) {
		Config.launcherBuildTime = BuildTime;
	}
}

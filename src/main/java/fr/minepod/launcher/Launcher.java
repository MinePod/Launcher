package fr.minepod.launcher;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Launcher {
	public static void main(String[] args) throws IOException {
		if(args.length != 0)
			Start(args[0]);
		else
			Start();
	}

	public static void Start() {
		Start("unknown");
	}

	public static void Start(String args) {
		try {
			Config.setBootstrapVersion(args);
			Config.SetConfig();

			if(!new File(Config.launcherLocation).exists())
				new File(Config.launcherLocation).mkdir();	

			Config.setup();
			Config.gui = new Gui(Config.launcherChangelogPage, Config.launcherVersion, Config.launcherBuildTime);

			new VersionsManager();
			checkProfile();

			Config.gui.finish();
			Config.logger.info("Ready!");
			Config.gui.enableButton();
		} catch (SecurityException e) {
			new CrashReport(e.toString(), Langage.DOINGMAINTHREADTASKS.toString());
		} catch (IOException e) {
			new CrashReport(e.toString(), Langage.DOINGMAINTHREADTASKS.toString());
		} catch (ParseException e) {
			new CrashReport(e.toString(), Langage.DOINGMAINTHREADTASKS.toString());
		} catch (InterruptedException e) {
			new CrashReport(e.toString(), Langage.DOINGMAINTHREADTASKS.toString());
		}
	}

	public static void checkProfile() throws IOException, ParseException {
		if(new File(Config.profilesPath).exists()) {
			Profile profile = new Profile();

			if(new File(Config.profilesVersionPath).exists()) {
				if(fr.minepod.utils.UtilsFiles.readFile(Config.profilesVersionPath).contains(Config.profilesVersion)) {
					profile.set(Config.launcherName, Config.profilesPath, Config.launcherLocation);
				} else {
					Config.logger.info("Current version: " + fr.minepod.utils.UtilsFiles.readFile(Config.profilesVersionPath));
					Config.logger.info("New profile version found: " + Config.profilesVersion);
					profile.update(Config.launcherName, Config.profilesPath, Config.launcherLocation);
					fr.minepod.utils.UtilsFiles.writeFile(Config.profilesVersionPath, Config.profilesVersion);
				}
			} else {
				Config.logger.warning("Profile version does not exist, creating new one");
				profile.set(Config.launcherName, Config.profilesPath, Config.launcherLocation);
				fr.minepod.utils.UtilsFiles.writeFile(Config.profilesVersionPath, Config.profilesVersion);
			}
		} else {
			Config.logger.severe("Profile do not exists");
			javax.swing.JOptionPane.showMessageDialog(null, "Lancez le jeu via le launcher Mojang, fermez-le et relancez le launcher " + Config.launcherName, "Attention", javax.swing.JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}

	public static void launchGame() {
		try {
			String OS = System.getProperty("os.name").toUpperCase();

			if(OS.contains("WIN"))
				Runtime.getRuntime().exec("java -jar -Xmx1G \"" + Config.launcherMinecraftJar + "\"");
			else if(OS.contains("MAC"))
				Desktop.getDesktop().open(new File(Config.launcherMinecraftJar));
			else if(OS.contains("NUX"))
				Runtime.getRuntime().exec("java -jar -Xmx1G \"" + Config.launcherMinecraftJar + "\"");
			else
				Runtime.getRuntime().exec("java -jar -Xmx1G \"" + Config.launcherMinecraftJar + "\"");

			System.exit(0);
		} catch (Exception e) {
			new CrashReport(e.toString(), Langage.LAUNCHINGGAME.toString());
		}
	}
}

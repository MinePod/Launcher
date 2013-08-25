package fr.minepod.launcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Start {
	private static Profile Profile = new Profile();
	private static Downloader Downloader = new Downloader();
	
	public static void main(String[] args) throws IOException {
		if(args.length != 0)
			new Config().BootstrapVersion(args[0]);
		else
			new Config().BootstrapVersion("> 1.0.6 or is it a Mac?");
		
		new Config().GetConfig();
		new Debug().Set();
		DownloadRequiredFiles();
	}
	
	 public static void DownloadRequiredFiles() {
		 try {
			 if(!new File(Config.LauncherLocation).exists()) {
				 new File(Config.LauncherLocation).mkdir();
			 }	
			 
			
			 if(!new File(Config.MinecraftAppData + Config.Slash + "libraries").exists()) {
				 new File(Config.MinecraftAppData + Config.Slash + "libraries").mkdir();
			 }
			
			 if(!new File(Config.MinecraftAppData + Config.Slash + "versions").exists()) {
			 	 new File(Config.MinecraftAppData + Config.Slash + "versions").mkdir();
			 }
			
			 if(!new File(Config.MinecraftAppData + Config.Slash + "versions" + Config.Slash + Config.LauncherName).exists()) {
			   	 new File(Config.MinecraftAppData + Config.Slash + "versions" + Config.Slash + Config.LauncherName).mkdir();
			 }
			
			 if(!new File(Config.LauncherLocation + Config.Slash + "mods").exists()) {
				 new File(Config.LauncherLocation + Config.Slash + "mods").mkdir();
			 }	
			
			 if(new File(Config.LauncherNewsHtml).exists()) {
				 new File(Config.LauncherNewsHtml).delete();
			 }
			
			 if(new File(Config.LauncherNewsCss).exists()) {
				 new File(Config.LauncherNewsCss).delete();
			 }

			
			 Downloader.DownloadFiles(new URL(Config.LauncherNewsHtmlUrl), Config.LauncherNewsHtml, false);
			 Downloader.DownloadFiles(new URL(Config.LauncherNewsCssUrl), Config.LauncherNewsCss, false);
			
			 Config.Gui = new Gui(new URL("file:///" + Config.LauncherNewsCss), ClassFile.ReadFile(Config.LauncherNewsHtml), Config.LauncherVersion);
			
			 if(!new File(Config.LauncherMinecraftJar).exists()) {
				 Downloader.DownloadFiles(new URL(Config.MinecraftJarUrl), Config.LauncherMinecraftJar, true);		
			 }
			 
				 
		     DownloaderThread DT1 = new DownloaderThread(Config.LibrariesLatestVersionUrl, Config.LauncherLocation + Config.Slash + "Libraries.md5", Config.MinecraftAppData + Config.Slash, "libraries", Config.LauncherZippedLibraries);
		     DT1.start();
		     DownloaderThread DT2 = new DownloaderThread(Config.VersionsLatestVersionUrl, Config.LauncherLocation + Config.Slash + "Versions.md5", Config.MinecraftAppData + Config.Slash + "versions" + Config.Slash, Config.LauncherName, Config.LauncherZippedVersions);
		     DT2.start();
		     DownloaderThread DT3 = new DownloaderThread(Config.ModsLatestVersionUrl, Config.LauncherLocation + Config.Slash + "Mods.md5", Config.LauncherLocation + Config.Slash, "mods", Config.LauncherZippedMods);
		     DT3.start();
			
			 if(new File(Config.ProfilesPath).exists()) {
				 if(new File(Config.ProfilesVersionPath).exists()) {
					 if(ClassFile.ReadFile(Config.ProfilesVersionPath).contains(Config.ProfilesVersion)) {
						 Profile.Set(Config.LauncherName, Config.ProfilesPath, Config.LauncherLocation);
					 } else {
						 System.out.println("Current version: " + ClassFile.ReadFile(Config.ProfilesVersionPath));
						 System.out.println("New profile version found: " + Config.ProfilesVersion);
						 Profile.Update(Config.LauncherName, Config.ProfilesPath, Config.LauncherLocation);
						 ClassFile.WriteFile(Config.ProfilesVersionPath, Config.ProfilesVersion);
					 }
				 } else {
					 System.out.println("Profile version do not exists, creating new one");
					 Profile.Set(Config.LauncherName, Config.ProfilesPath, Config.LauncherLocation);
					 ClassFile.WriteFile(Config.ProfilesVersionPath, Config.ProfilesVersion);
				 }
				
				 while(DT1.isAlive() || DT2.isAlive() || DT3.isAlive()) {
					 Thread.sleep(500);
				 }
				 
				 System.out.println("Ready!");
				 Config.Gui.EnableButton();

			 } else {
				 System.out.println("Profile do not exists");
			     javax.swing.JOptionPane.showMessageDialog(null, "Lancez le jeu via le launcher Mojang, fermez-le et relancez le launcher " + Config.LauncherName, "Attention", javax.swing.JOptionPane.WARNING_MESSAGE);
			     System.exit(0);
			 }

		 } catch (IOException e) {	 
			 CrashReport.SendReport(e.toString(), "doing main thread's tasks");
		 } catch (Exception e) {
			 CrashReport.SendReport(e.toString(), "doing main thread's tasks");
		 }
		 
	 }
	 
	 public static void LaunchGame(String ParLauncherMinecraftJar, String ParLauncherLocation) {
		 System.out.println(ParLauncherMinecraftJar);
		 try {
			 new JarLoader(ParLauncherMinecraftJar);
			 System.exit(0);
		 } catch (Exception e) {
			 CrashReport.SendReport(e.toString(), "launching game");
		 }
	 }
}

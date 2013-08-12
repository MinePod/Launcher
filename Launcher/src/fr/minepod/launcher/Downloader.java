package fr.minepod.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import fr.minepod.launcher.JarLoader;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Downloader {
	 private InputStream rbc;
	 private byte[] buffer;
	 private double fileLength = 0.0D;
	 private double totalBytesRead = 0.0D;
	 private int bytesRead;
	 private int percent;
	 private FileOutputStream fos;
	 private DisplayDownload DisplayDownload;
	 private GetMd5 GetMd5 = new GetMd5();
	 private Profile Profile = new Profile();
	 private Config Config = new Config();
   	 private URLConnection urlConnection;
   	 private CrashReport CrashReport = new CrashReport();
   	 private ArrayList<Integer> al = new ArrayList<Integer>();
   	 
	 private String ProfilesPath = Config.ProfilesPath;
	 private String MinecraftAppData = Config.MinecraftAppData;
	 private String Slash = Config.Slash; 
	 private String LauncherLocation = Config.LauncherLocation;
	 private String LauncherZippedLibraries = Config.LauncherZippedLibraries;
	 private String LauncherZippedVersions = Config.LauncherZippedVersions;
	 private String LauncherZippedMods = Config.LauncherZippedMods;
	 private String LauncherMinecraftJar = Config.LauncherMinecraftJar;
	 private String LauncherNewsCss = Config.LauncherNewsCss;
	 private String LauncherNewsHtml = Config.LauncherNewsHtml;
	 private String ProfilesVersionPath = Config.ProfilesVersionPath;
	 private String LibrariesLatestVersionUrl = Config.LibrariesLatestVersionUrl;
	 private String VersionsLatestVersionUrl = Config.VersionsLatestVersionUrl;
	 private String ModsLatestVersionUrl = Config.ModsLatestVersionUrl;
	 private String MinecraftJarUrl = Config.MinecraftJarUrl;
	 private String LauncherNewsHtmlUrl = Config.LauncherNewsHtmlUrl;
	 private String LauncherNewsCssUrl = Config.LauncherNewsCssUrl;
	 private String GetMd5FileUrl = Config.GetMd5FileUrl;
	 private String LauncherName = Config.LauncherName;
	 private String ProfilesVersion = Config.ProfilesVersion;
	 private String LauncherVersion = Config.LauncherVersion;
 
	 private void DownloadFiles(URL website, String path, boolean isGui) {
		    System.out.println("Starting " + website + " to " + path);
		    new File(path).delete();
		    try {
		      fos = new FileOutputStream(path);
		    	
		      urlConnection = website.openConnection();
		      fileLength = urlConnection.getContentLength();
	
		      rbc = website.openStream();
	
		      buffer = new byte[153600];
		      totalBytesRead = 0.0D;
		      bytesRead = 0;
		      
		      System.out.println("Downloading...");
	
		      while ((bytesRead = rbc.read(buffer)) > 0) {
		        fos.write(buffer, 0, bytesRead);
		        buffer = new byte[153600];
		        totalBytesRead += bytesRead;
		        percent = ((int)Math.round(totalBytesRead / fileLength * 100.0D));
		        
		        if(!al.contains(percent)) {
		        	al.add(percent);
		        	System.out.print("*");
		        }
		        
		        if(isGui) {
		        	DisplayDownload.Update(percent);
		        }
		        
		      }
		      
		      al.clear();
		      System.out.println("");

		    } catch (MalformedURLException e) {
		    	CrashReport.SendReport(e.toString(), "downloading file");
		    } catch (IOException e) {
		    	CrashReport.SendReport(e.toString(), "downloading file");
		    }

		    System.out.println("Downloading complete!");
		  }
	 
	 public void UnZip(String fileInput, String folderOutput) {
		   try {
			   ClassFile.Delete(new File(folderOutput));
			   ZipFile zipFile = new ZipFile(fileInput);
		       zipFile.extractAll(folderOutput);
		    } catch (ZipException e) {
		    	CrashReport.SendReport(e.toString(), "unzipping file " + fileInput + " to " + folderOutput);
		    }
	 }
	 
	 public void Zip(String input, String fileOutput) {
			try {
				new File(fileOutput).delete();
				ZipFile zipFile = new ZipFile(fileOutput);
				ZipParameters parameters = new ZipParameters();
				parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
				parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
				
				if(new File(input).isDirectory()) {
					zipFile.addFolder(input, parameters);
				} else {
					zipFile.addFile(new File(input), parameters);
				}
			} catch (ZipException e) {
				CrashReport.SendReport(e.toString(), "zipping folder " + input + " to " + fileOutput);
			}
	 }
	 
	 public void LaunchGame(String ParLauncherMinecraftJar, String ParLauncherLocation) {
		System.out.println(ParLauncherMinecraftJar);
		try {
			new JarLoader(ParLauncherMinecraftJar);
		} catch (Exception e) {
			CrashReport.SendReport(e.toString(), "launching game");
		}
			
		System.exit(0);
	 }
	 
	 public void DownloadRequiredFiles() {
		 try {
			
			if(!new File(LauncherLocation).exists()) {
				new File(LauncherLocation).mkdir();
			}		
			
			if(!new File(MinecraftAppData + Slash + "libraries").exists()) {
				new File(MinecraftAppData + Slash + "libraries").mkdir();
			}
			
			if(!new File(MinecraftAppData + Slash + "versions").exists()) {
				new File(MinecraftAppData + Slash + "versions").mkdir();
			}
			
			if(!new File(MinecraftAppData + Slash + "versions" + Slash + LauncherName).exists()) {
				new File(MinecraftAppData + Slash + "versions" + Slash + LauncherName).mkdir();
			}
			
			if(!new File(LauncherLocation + Slash + "mods").exists()) {
				new File(LauncherLocation + Slash + "mods").mkdir();
			}	
			
			if(new File(LauncherNewsHtml).exists()) {
				new File(LauncherNewsHtml).delete();
			}
			
			if(new File(LauncherNewsCss).exists()) {
				new File(LauncherNewsCss).delete();
			}
			
			DownloadFiles(new URL(LauncherNewsHtmlUrl), LauncherNewsHtml, false);
			DownloadFiles(new URL(LauncherNewsCssUrl), LauncherNewsCss, false);
			
			this.DisplayDownload = new DisplayDownload(new URL("file:///" + LauncherNewsCss), ClassFile.ReadFile(LauncherNewsHtml), LauncherVersion);
			
			if(!new File(LauncherMinecraftJar).exists()) {
				DownloadFiles(new URL(MinecraftJarUrl), LauncherMinecraftJar, true);		
			}
				
			DownloadFiles(new URL(GetMd5FileUrl + LibrariesLatestVersionUrl), LauncherLocation + Slash + "Libraries.md5", true);
			Zip(MinecraftAppData + Slash + "libraries", LauncherZippedLibraries);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Libraries.md5"), new File(LauncherZippedLibraries))) {
				System.out.println("Detecting modified libraries files, deleting...");
				DownloadFiles(new URL(LibrariesLatestVersionUrl), LauncherZippedLibraries, true);
				UnZip(LauncherZippedLibraries, MinecraftAppData + Slash + "libraries");
			}
			

			DownloadFiles(new URL(GetMd5FileUrl + VersionsLatestVersionUrl), LauncherLocation + Slash + "Versions.md5", true);
			Zip(MinecraftAppData + Slash + "versions" + Slash + LauncherName, LauncherZippedVersions);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Versions.md5"), new File(LauncherZippedVersions))) {
				System.out.println("Detecting modified versions files, deleting...");
				DownloadFiles(new URL(VersionsLatestVersionUrl), LauncherZippedVersions, true);
				UnZip(LauncherZippedVersions, MinecraftAppData + Slash + "versions" + Slash + LauncherName);
			}
			

			DownloadFiles(new URL(GetMd5FileUrl + ModsLatestVersionUrl), LauncherLocation + Slash + "Mods.md5", true);
			Zip(LauncherLocation + Slash + "mods", LauncherZippedMods);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Mods.md5"), new File(LauncherZippedMods))) {
				System.out.println("Detecting modified mods files, deleting...");
				DownloadFiles(new URL(ModsLatestVersionUrl), LauncherZippedMods, true);
				UnZip(LauncherZippedMods, LauncherLocation + Slash + "mods");
			}
			
			
			if(new File(ProfilesPath).exists()) {
				if(new File(ProfilesVersionPath).exists()) {
					if(ClassFile.ReadFile(ProfilesVersionPath).contains(ProfilesVersion)) {
						Profile.Set(LauncherName, ProfilesPath, LauncherLocation);
					} else {
						System.out.println("Current version: " + ClassFile.ReadFile(ProfilesVersionPath));
						System.out.println("New profile version found: " + ProfilesVersion);
						Profile.Update(LauncherName, ProfilesPath, LauncherLocation);
						ClassFile.WriteFile(ProfilesVersionPath, ProfilesVersion);
					}
				} else {
					System.out.println("Profile version do not exists, creating new one");
					Profile.Set(LauncherName, ProfilesPath, LauncherLocation);
					ClassFile.WriteFile(ProfilesVersionPath, ProfilesVersion);
				}
				
				DisplayDownload.EnableButton();

			} else {
				System.out.println("Profile do not exists");
			    javax.swing.JOptionPane.showMessageDialog(null, "Lancez le jeu via le launcher Mojang, fermez-le et relancez le launcher " + LauncherName, "Attention", javax.swing.JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			}
			
			
		} catch (IOException e) {
			CrashReport.SendReport(e.toString(), "doing main thread's tasks");
		} catch (Exception e) {
			CrashReport.SendReport(e.toString(), "doing main thread's tasks");
		}
	 }
}

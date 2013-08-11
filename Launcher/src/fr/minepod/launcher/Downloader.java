package fr.minepod.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
	 private DisplayDownload DisplayDownload;
	 private GetMd5 GetMd5 = new GetMd5();
	 private Profile Profile = new Profile();
	 private Config Config = new Config();
   	 private URLConnection urlConnection;
   	 private CrashReport CrashReport = new CrashReport();
   	 
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
	 
	 private void DownloadFilesNoGui(URL website, FileOutputStream fos) {
		    System.out.println("Starting...");
		    try {
		      System.out.println("Getting url...");
		      urlConnection = website.openConnection();
		      fileLength = urlConnection.getContentLength();

		      System.out.println("Openning stream...");
		      this.rbc = website.openStream();

		      System.out.println("Reading stream...");
		      this.buffer = new byte[153600];
		      this.totalBytesRead = 0.0D;
		      this.bytesRead = 0;

		      while ((this.bytesRead = this.rbc.read(this.buffer)) > 0) {
		        fos.write(this.buffer, 0, this.bytesRead);
		        this.buffer = new byte[153600];
		        this.totalBytesRead += this.bytesRead;
		        this.percent = ((int)Math.round(this.totalBytesRead / fileLength * 100.0D));
		        System.out.println("Bytes readed: " + (int)this.totalBytesRead + "/" + (int)fileLength + " " + this.percent + "%");
		      }

		    } catch (MalformedURLException e) {
		    	CrashReport.SendReport(e.toString(), "downloading file");
		    } catch (IOException e) {
		    	CrashReport.SendReport(e.toString(), "downloading file");
		    }

		    System.out.println("Downloading complete!");
		  }

	 
	 private void DownloadFiles(URL website, FileOutputStream fos) {
		    System.out.println("Starting...");
		    try {
		      System.out.println("Getting url...");
		      urlConnection = website.openConnection();
		      fileLength = urlConnection.getContentLength();

		      System.out.println("Openning stream...");
		      this.rbc = website.openStream();

		      System.out.println("Reading stream...");
		      this.buffer = new byte[153600];
		      this.totalBytesRead = 0.0D;
		      this.bytesRead = 0;

		      while ((this.bytesRead = this.rbc.read(this.buffer)) > 0) {
		        fos.write(this.buffer, 0, this.bytesRead);
		        this.buffer = new byte[153600];
		        this.totalBytesRead += this.bytesRead;
		        this.percent = ((int)Math.round(this.totalBytesRead / fileLength * 100.0D));
		        System.out.println("Bytes readed: " + (int)this.totalBytesRead + "/" + (int)fileLength + " " + this.percent + "%");
		        this.DisplayDownload.Update(this.percent);
		      }

		    } catch (MalformedURLException e) {
		    	CrashReport.SendReport(e.toString(), "downloading file");
		    } catch (IOException e) {
		    	CrashReport.SendReport(e.toString(), "downloading file");
		    }

		    System.out.println("Downloading complete!");
		  }
	 
	 public void UnZip(String fileInput, String folderOutput) {
		   try {
			   ZipFile zipFile = new ZipFile(fileInput);
		       zipFile.extractAll(folderOutput);
		    } catch (ZipException e) {
		    	CrashReport.SendReport(e.toString(), "unzipping file " + fileInput + " to " + folderOutput);
		    }
	 }
	 
	 public void Zip(String folderInput, String fileOutput) {
			try {
				ZipFile zipFile = new ZipFile(fileOutput);
				ZipParameters parameters = new ZipParameters();
				parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
				parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
				zipFile.createZipFileFromFolder(folderInput, parameters, true, 10485760);
			} catch (ZipException e) {
				CrashReport.SendReport(e.toString(),"zipping folder " + folderInput + " to " + fileOutput);
			}
	 }
	 
	 public void Clean() {
			new File(LauncherLocation + Slash + "Libraries.zip").delete();
			new File(LauncherLocation + Slash + "Libraries.z01").delete();
			new File(LauncherLocation + Slash + "Libraries.md5").delete();
			new File(LauncherLocation + Slash + "Versions.zip").delete();
			new File(LauncherLocation + Slash + "Versions.z01").delete();
			new File(LauncherLocation + Slash + "Versions.md5").delete();
			new File(LauncherLocation + Slash + "Mods.zip").delete();
			new File(LauncherLocation + Slash + "Mods.z01").delete();
			new File(LauncherLocation + Slash + "Mods.md5").delete();
			System.out.println("Directory " + LauncherLocation + Slash + " cleaned up!");
	 }
	 
	 public void LaunchGame(String ParLauncherMinecraftJar, String ParLauncherLocation) {
		System.out.println(ParLauncherMinecraftJar);
		try {
			new JarLoader(ParLauncherMinecraftJar);
		} catch (Exception e) {
			CrashReport.SendReport(e.toString(), "launching game");
		}
			
		Clean();
			
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
			
			Clean();
			
			if(new File(LauncherNewsHtml).exists()) {
				new File(LauncherNewsHtml).delete();
			}
			
			if(new File(LauncherNewsCss).exists()) {
				new File(LauncherNewsCss).delete();
			}
			
			DownloadFilesNoGui(new URL(LauncherNewsHtmlUrl), new FileOutputStream(LauncherNewsHtml));
			DownloadFilesNoGui(new URL(LauncherNewsCssUrl), new FileOutputStream(LauncherNewsCss));
			
			this.DisplayDownload = new DisplayDownload(new URL("file:///" + LauncherNewsCss), ClassFile.ReadFile(LauncherNewsHtml), LauncherVersion);
			
			if(!new File(LauncherMinecraftJar).exists()) {
				DownloadFiles(new URL(MinecraftJarUrl), new FileOutputStream(LauncherMinecraftJar));		
			}
				
			DownloadFiles(new URL(GetMd5FileUrl + LibrariesLatestVersionUrl), new FileOutputStream(LauncherLocation + Slash + "Libraries.md5"));
			Zip(MinecraftAppData + Slash + "libraries", LauncherZippedLibraries);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Libraries.md5"), new File(LauncherZippedLibraries))) {
				new File(LauncherZippedLibraries).delete();
				ClassFile.Delete(new File(MinecraftAppData + Slash + "libraries"));
				System.out.println("Detecting modified libraries files, deleting...");
				DownloadFiles(new URL(LibrariesLatestVersionUrl), new FileOutputStream(LauncherZippedLibraries));
				UnZip(LauncherZippedLibraries, MinecraftAppData + Slash + "libraries");
			}
			

			DownloadFiles(new URL(GetMd5FileUrl + VersionsLatestVersionUrl), new FileOutputStream(LauncherLocation + Slash + "Versions.md5"));
			Zip(MinecraftAppData + Slash + "versions" + Slash + LauncherName, LauncherZippedVersions);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Versions.md5"), new File(LauncherZippedVersions))) {
				new File(LauncherZippedVersions).delete();
				ClassFile.Delete(new File(MinecraftAppData + Slash + "versions" + Slash + LauncherName));
				System.out.println("Detecting modified versions files, deleting...");
				DownloadFiles(new URL(VersionsLatestVersionUrl), new FileOutputStream(LauncherZippedVersions));
				UnZip(LauncherZippedVersions, MinecraftAppData + Slash + "versions" + Slash + LauncherName);
			}
			

			DownloadFiles(new URL(GetMd5FileUrl + ModsLatestVersionUrl), new FileOutputStream(LauncherLocation + Slash + "Mods.md5"));
			Zip(LauncherLocation + Slash + "mods", LauncherZippedMods);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Mods.md5"), new File(LauncherZippedMods))) {
				new File(LauncherZippedMods).delete();
				ClassFile.Delete(new File(LauncherLocation + Slash + "mods"));
				System.out.println("Detecting modified mods files, deleting...");
				DownloadFiles(new URL(ModsLatestVersionUrl), new FileOutputStream(LauncherZippedMods));
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
						new File(ProfilesVersionPath).delete();
						ClassFile.WriteFile(ProfilesVersionPath, ProfilesVersion);
					}
				} else {
					System.out.println("Profile version do not exists, creating new one");
					ClassFile.WriteFile(ProfilesVersionPath, ProfilesVersion);
				}
			} else {
				System.out.println("Profile do not exists");
			    javax.swing.JOptionPane.showMessageDialog(null, "Lancez le jeu via le launcher Mojang, fermez-le et relancez le launcher " + LauncherName, "Attention", javax.swing.JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			}
			
			
			DisplayDownload.EnableButton();
			
			Clean();
			
		} catch (IOException e) {
			CrashReport.SendReport(e.toString(), "doing main thread's tasks");
		} catch (Exception e) {
			CrashReport.SendReport(e.toString(), "doing main thread's tasks");
		}
	 }
}

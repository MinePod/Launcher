package fr.minepod.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

import fr.minepod.launcher.MPLoader;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Downloader {
	 private FileOutputStream fos;
	 private InputStream rbc;
	 private URL website;
	 private byte[] buffer;
	 private double fileLength = 0.0D;
	 private double totalBytesRead = 0.0D;
	 private int bytesRead;
	 private int percent;
	 private DisplayDownload DisplayDownload;
	 private GetMd5 GetMd5 = new GetMd5();
	 private JOptionPane JOptionPane = new JOptionPane();
	 private Profile Profile = new Profile();
   	 private URLConnection urlConnection;
	 private String AppDataPath;
	 private String ProfilesPath;
	 private String Minecraft;
	 private String MinecraftAppData;
	 private String Slash; 
	 private String LauncherLocation;
	 private String LauncherZippedLibraries;
	 private String LauncherZippedVersions;
	 private String LauncherZippedMods;
	 private String LauncherMinecraftJar;
	 private String LauncherNewsCss;
	 private String LauncherNewsHtml;
	 private String LauncherDir;
	 private String ProfilesVersionPath;
	 
	 private String LibrariesLatestVersionUrl = "http://assets.minepod.fr/launcher/libraries.php";
	 private String VersionsLatestVersionUrl = "http://assets.minepod.fr/launcher/versions.php";
	 private String ModsLatestVersionUrl = "http://assets.minepod.fr/launcher/mods.php";
	 private String MinecraftJarUrl = "http://assets.minepod.fr/launcher/minecraft.jar";
	 private String LauncherNewsHtmlUrl = "http://assets.minepod.fr/launcher/news/news.html";
	 private String LauncherNewsCssUrl = "http://assets.minepod.fr/launcher/news/news.css";
	 private String GetMd5FileUrl = "http://assets.minepod.fr/launcher/md5.php?file=";
	 private String LauncherName = "MinePod";
	 private String ProfilesVersion = "1";
	 
	 private void DownloaderNoGui(URL website, FileOutputStream fos) {
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
		      e.printStackTrace();
		      JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
		      System.exit(0);
		    } catch (IOException e) {
		      e.printStackTrace();
		      JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
		      System.exit(0);
		    }

		    System.out.println("Downloading complete!");
		  }

	 
	 private void Downloader(URL website, FileOutputStream fos) {
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
		      e.printStackTrace();
		      JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
		      System.exit(0);
		    } catch (IOException e) {
		      e.printStackTrace();
		      JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
		      System.exit(0);
		    }

		    System.out.println("Downloading complete!");
		  }
	 
	 public void UnZip(String fileInput, String folderOutput) {
		   try {
		         ZipFile zipFile = new ZipFile(fileInput);
		         zipFile.extractAll(folderOutput);
		    } catch (ZipException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
		        System.exit(0);
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
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
	 }
	 
	 public void Clean(String PathToClean) {
			new File(PathToClean + "Libraries.zip").delete();
			new File(PathToClean + "Libraries.z01").delete();
			new File(PathToClean + "Libraries.md5").delete();
			new File(PathToClean + "Versions.zip").delete();
			new File(PathToClean + "Versions.z01").delete();
			new File(PathToClean + "Versions.md5").delete();
			new File(PathToClean + "Mods.zip").delete();
			new File(PathToClean + "Mods.z01").delete();
			new File(PathToClean + "Mods.md5").delete();
			System.out.println("Directory cleaned up!");
	 }
	 
	 public void LaunchGame(String ParLauncherMinecraftJar, String ParLauncherLocation) {
		System.out.println(ParLauncherMinecraftJar);
		try {
			new MPLoader(ParLauncherMinecraftJar);
		} catch (Exception e) {
			e.printStackTrace();
		    JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
		    System.exit(0);
		}
			
		Clean(ParLauncherLocation);
			
		System.exit(0);
	 }
	 
	 public void DownloadRequiredFiles() {
		 try {
			String OS = System.getProperty("os.name").toUpperCase();
			if(OS.contains("WIN")) {
				AppDataPath = System.getenv("APPDATA");
				LauncherDir = "\\." + LauncherName;
				Minecraft = "\\.minecraft";
				Slash = "\\";
			} else if(OS.contains("MAC")) {
				AppDataPath = System.getProperty("user.home") + "/Library/Application " + "Support";
				LauncherDir = "/" + LauncherName;
				Minecraft = "/minecraft";
				Slash = "/";
			} else if(OS.contains("NUX")) {
				AppDataPath = System.getProperty("user.home");
				LauncherDir = "/." + LauncherName;
				Minecraft = "/.minecraft";
				Slash = "/";
			} else {
				AppDataPath =  System.getProperty("user.dir");
				LauncherDir = "/." + LauncherName;
				Minecraft = "/.minecraft";
				Slash = "/";
			}
			
			System.out.println(AppDataPath);
			
			LauncherLocation = AppDataPath + LauncherDir;
			MinecraftAppData = AppDataPath + Minecraft;
			LauncherZippedLibraries = LauncherLocation + Slash + "Libraries.zip";
			LauncherZippedVersions = LauncherLocation + Slash + "Versions.zip";
			LauncherZippedMods = LauncherLocation + Slash + "Mods.zip";
			LauncherMinecraftJar = LauncherLocation + Slash + "Minecraft.jar";
			LauncherNewsHtml = LauncherLocation + Slash + "news.html";
			LauncherNewsCss = LauncherLocation + Slash + "news.css";
			ProfilesPath = MinecraftAppData + Slash + "launcher_profiles.json";
			ProfilesVersionPath =  LauncherLocation + Slash + "profiles.txt";
			
			if(!new File(LauncherLocation).exists()) {
				new File(LauncherLocation).mkdir();
			}		
			
			if(!new File(MinecraftAppData + Slash + "libraries").exists()) {
				new File(MinecraftAppData + Slash + "libraries").mkdir();
			}
			
			if(!new File(MinecraftAppData + Slash + "versions").exists()) {
				new File(MinecraftAppData + Slash + "versions").mkdir();
			}
			
			if(!new File(LauncherLocation + Slash + "mods").exists()) {
				new File(LauncherLocation + Slash + "mods").mkdir();
			}	
			
			Clean(LauncherLocation + Slash);
			
			if(new File(LauncherNewsHtml).exists()) {
				new File(LauncherNewsHtml).delete();
			}
			
			if(new File(LauncherNewsCss).exists()) {
				new File(LauncherNewsCss).delete();
			}
			
			DownloaderNoGui(new URL(LauncherNewsHtmlUrl), new FileOutputStream(LauncherNewsHtml));
			DownloaderNoGui(new URL(LauncherNewsCssUrl), new FileOutputStream(LauncherNewsCss));
			
			this.DisplayDownload = new DisplayDownload(new URL("file:///" + LauncherNewsCss), ClassFile.ReadFile(LauncherNewsHtml));
			
			if(!new File(LauncherMinecraftJar).exists()) {
				Downloader(new URL(MinecraftJarUrl), new FileOutputStream(LauncherMinecraftJar));		
			}
				
			Downloader(new URL(GetMd5FileUrl + LibrariesLatestVersionUrl), new FileOutputStream(LauncherLocation + Slash + "Libraries.md5"));
			Zip(MinecraftAppData + Slash + "libraries", LauncherZippedLibraries);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Libraries.md5"), new File(LauncherZippedLibraries))) {
				new File(LauncherZippedLibraries).delete();
				ClassFile.Delete(new File(MinecraftAppData + Slash + "libraries"));
				System.out.println("Detecting modified libraries files, deleting...");
				Downloader(new URL(LibrariesLatestVersionUrl), new FileOutputStream(LauncherZippedLibraries));
				UnZip(LauncherZippedLibraries, MinecraftAppData + Slash + "libraries");
			}
			

			Downloader(new URL(GetMd5FileUrl + VersionsLatestVersionUrl), new FileOutputStream(LauncherLocation + Slash + "Versions.md5"));
			Zip(MinecraftAppData + Slash + "versions" + Slash + LauncherName, LauncherZippedVersions);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Versions.md5"), new File(LauncherZippedVersions))) {
				new File(LauncherZippedVersions).delete();
				ClassFile.Delete(new File(MinecraftAppData + Slash + "versions" + Slash + LauncherName));
				System.out.println("Detecting modified versions files, deleting...");
				Downloader(new URL(VersionsLatestVersionUrl), new FileOutputStream(LauncherZippedVersions));
				UnZip(LauncherZippedVersions, MinecraftAppData + Slash + "versions" + Slash + LauncherName);
			}
			

			Downloader(new URL(GetMd5FileUrl + ModsLatestVersionUrl), new FileOutputStream(LauncherLocation + Slash + "Mods.md5"));
			Zip(LauncherLocation + Slash + "mods", LauncherZippedMods);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Mods.md5"), new File(LauncherZippedMods))) {
				new File(LauncherZippedMods).delete();
				ClassFile.Delete(new File(LauncherLocation + Slash + "mods"));
				System.out.println("Detecting modified mods files, deleting...");
				Downloader(new URL(ModsLatestVersionUrl), new FileOutputStream(LauncherZippedMods));
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
			    JOptionPane.showMessageDialog(null, "Lancez le jeu via le launcher Mojang, fermez-le et relancez le launcher " + LauncherName, "Attention", JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			}
			
			
			DisplayDownload.EnableButton(LauncherMinecraftJar, LauncherLocation);
			
			Clean(LauncherLocation + Slash);
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	 }
}

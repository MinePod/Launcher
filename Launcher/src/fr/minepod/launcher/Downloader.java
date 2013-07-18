package fr.minepod.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

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
	 
	 private String LibrariesLatestVersionUrl = "http://assets.minepod.fr/launcher/libraries.php";
	 private String VersionsLatestVersionUrl = "http://assets.minepod.fr/launcher/versions.php";
	 private String ModsLatestVersionUrl = "http://assets.minepod.fr/launcher/mods.php";
	 private String MinecraftJarUrl = "http://assets.minepod.fr/launcher/minecraft.jar";
	 private String LauncherNewsHtmlUrl = "http://assets.minepod.fr/launcher/news/news.html";
	 private String LauncherNewsCssUrl = "http://assets.minepod.fr/launcher/news/news.css";
	 private String LauncherDefaultProfileUrl = "http://assets.minepod.fr/launcher/launcher_profiles.json";
	 private String GetMd5FileUrl = "http://assets.minepod.fr/launcher/md5.php?file=";
	 private String LauncherName = "MinePod";
	 
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
		    } catch (IOException e) {
		      e.printStackTrace();
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
		    } catch (IOException e) {
		      e.printStackTrace();
		    }

		    System.out.println("Downloading complete!");
		  }
	 
	 public void UnZip(String fileInput, String folderOutput) {
		   try {
		         ZipFile zipFile = new ZipFile(fileInput);
		         zipFile.extractAll(folderOutput);
		    } catch (ZipException e) {
		        e.printStackTrace();
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
			}
	 }
	 
	 public void Clean(String PathToClean) {
			new File(PathToClean + Slash + "Libraries.zip").delete();
			new File(PathToClean + Slash + "Libraries.z01").delete();
			new File(PathToClean + Slash + "Libraries.md5").delete();
			new File(PathToClean + Slash + "Versions.zip").delete();
			new File(PathToClean + Slash + "Versions.z01").delete();
			new File(PathToClean + Slash + "Versions.md5").delete();
			new File(PathToClean + Slash + "Mods.zip").delete();
			new File(PathToClean + Slash + "Mods.z01").delete();
			new File(PathToClean + Slash + "Mods.md5").delete();
			System.out.println("Directory cleaned up!");
	 }
	 
	 public void LaunchGame(String ParLauncherMinecraftJar, String ParLauncherLocation) {
		System.out.println(ParLauncherMinecraftJar);
		try {
			new MPLoader(ParLauncherMinecraftJar);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		Clean(ParLauncherLocation);
			
		System.exit(0);
	 }
	 
	 public void DownloadRequiredFiles() {
		 try {
			String OS = System.getProperty("os.name").toUpperCase();
			if(OS.contains("WIN")) {
				AppDataPath = System.getenv("APPDATA");
				LauncherName = "\\." + LauncherName;
				Minecraft = "\\.minecraft";
				Slash = "\\";
			} else if(OS.contains("MAC")) {
				AppDataPath = System.getProperty("user.home") + "/Library/Application " + "Support";
				LauncherName = "/" + LauncherName;
				Minecraft = "/minecraft";
				Slash = "/";
			} else if(OS.contains("NUX")) {
				   AppDataPath = System.getProperty("user.home");
				   LauncherName = "/." + LauncherName;
				Minecraft = "/.minecraft";
				Slash = "/";
			} else {
				AppDataPath =  System.getProperty("user.dir");
				LauncherName = "/." + LauncherName;
				Minecraft = "/.minecraft";
				Slash = "/";
			}
			
			System.out.println(AppDataPath);
			
			LauncherLocation = AppDataPath + LauncherName;
			MinecraftAppData = AppDataPath + Minecraft;
			LauncherZippedLibraries = LauncherLocation + Slash + "Libraries.zip";
			LauncherZippedVersions = LauncherLocation + Slash + "Versions.zip";
			LauncherZippedMods = LauncherLocation + Slash + "Mods.zip";
			LauncherMinecraftJar = LauncherLocation + Slash + "Minecraft.jar";
			LauncherNewsHtml = LauncherLocation + Slash + "news.html";
			LauncherNewsCss = LauncherLocation + Slash + "news.css";
			ProfilesPath = LauncherLocation + Slash + "launcher_profiles.json";
			
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
			
			Clean(LauncherLocation);
			
			if(new File(LauncherNewsHtml).exists()) {
				new File(LauncherNewsHtml).delete();
			}
			
			if(new File(LauncherNewsCss).exists()) {
				new File(LauncherNewsCss).delete();
			}
			
			DownloaderNoGui(new URL(LauncherNewsHtmlUrl), new FileOutputStream(LauncherNewsHtml));
			DownloaderNoGui(new URL(LauncherNewsCssUrl), new FileOutputStream(LauncherNewsCss));
			
			this.DisplayDownload = new DisplayDownload(new URL("file:///" + LauncherNewsCss), ClassFile.ReadFile(LauncherNewsHtml, StandardCharsets.UTF_8));
			
			if(!new File(LauncherMinecraftJar).exists()) {
				Downloader(new URL(MinecraftJarUrl), new FileOutputStream(LauncherMinecraftJar));		
			}
				
			Downloader(new URL(GetMd5FileUrl + LibrariesLatestVersionUrl), new FileOutputStream(LauncherLocation + Slash + "Libraries.md5"));
			Zip(MinecraftAppData + Slash + "libraries", LauncherZippedLibraries);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Libraries.md5"), new File(LauncherZippedLibraries))) {
				new File(LauncherZippedLibraries).delete();
				new File(MinecraftAppData + Slash + "libraries").delete();
				Downloader(new URL(LibrariesLatestVersionUrl), new FileOutputStream(LauncherZippedLibraries));
				UnZip(LauncherZippedLibraries, MinecraftAppData + Slash + "libraries");
			}
			

			Downloader(new URL(GetMd5FileUrl + VersionsLatestVersionUrl), new FileOutputStream(LauncherLocation + Slash + "Versions.md5"));
			Zip(MinecraftAppData + Slash + "versions" + Slash + LauncherName, LauncherZippedVersions);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Versions.md5"), new File(LauncherZippedVersions))) {
				new File(LauncherZippedVersions).delete();
				new File(MinecraftAppData + Slash + "versions" + Slash + LauncherName).delete();
				Downloader(new URL(VersionsLatestVersionUrl), new FileOutputStream(LauncherZippedVersions));
				UnZip(LauncherZippedVersions, MinecraftAppData + Slash + "versions" + Slash + LauncherName);
			}
			

			Downloader(new URL(GetMd5FileUrl + ModsLatestVersionUrl), new FileOutputStream(LauncherLocation + Slash + "Mods.md5"));
			Zip(LauncherLocation + Slash + "mods", LauncherZippedMods);

			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Mods.md5"), new File(LauncherZippedMods))) {
				new File(LauncherZippedMods).delete();
				new File(LauncherLocation + Slash + "mods").delete();
				Downloader(new URL(ModsLatestVersionUrl), new FileOutputStream(LauncherZippedMods));
				UnZip(LauncherZippedMods, LauncherLocation + Slash + "mods");
			}
			
			
			if(!new File(ProfilesPath).exists()) {
				Downloader(new URL(LauncherDefaultProfileUrl), new FileOutputStream(ProfilesPath));
			}
			
			String Profile = ClassFile.ReadFile(ProfilesPath, StandardCharsets.UTF_8);
			new File(ProfilesPath).delete();
			if(!Profile.contains(LauncherName + "\",\n      \"gameDir\":")) {
				if(!Profile.contains(LauncherName)) {
					Profile = Profile.substring(0, 19) + "    \"" + LauncherName + "\": {\n      \"name\": \"" + LauncherName + "\",\n      \"gameDir\": \"Launcher_GameDir\",\n      \"lastVersionId\": \"" + LauncherName + "\",\n      \"javaArgs\": \"-Xmx1G -Dfml.ignoreInvalidMinecraftCertificates\u003dtrue -Dfml.ignorePatchDiscrepancies\u003dtrue\"\n    },\n" + Profile.substring(20);
				} else {
					Profile = Profile.replace("\"name\": \"" + LauncherName + "\"", "\"name\": \"" + LauncherName + "\",\n      \"gameDir\": \"" + LauncherName + "_GameDir\"");
				}
				Profile = Profile.replace("\"selectedProfile\": \"(Default)\",", "\"selectedProfile\": \"" + LauncherName + "\",");
			}
			Profile = Profile.replace("\"gameDir\": \"Launcher_GameDir\",", "\"gameDir\": \"" + LauncherLocation.replace("\\", "\\\\") + "\",");
			ClassFile.WriteFile(ProfilesPath, Profile);		
			
			DisplayDownload.EnableButton(LauncherMinecraftJar, LauncherLocation);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
}

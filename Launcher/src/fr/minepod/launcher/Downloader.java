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
	 private DisplayDownload DisplayDownload = new DisplayDownload();
	 private ClassFile ClassFile = new ClassFile();
	 private GetMd5 GetMd5 = new GetMd5();
   	 private URLConnection urlConnection;
	 private String AppDataPath;
	 private String MinePod;
	 private String latestVersionLibraries;
	 private String MinePodLibrariesZip;
	 private String latestVersionVersions;
	 private String MinePodVersionsZip;
	 private File MinecraftLaunch;
	private String ProfilesPath;
	 
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

		      this.DisplayDownload.pack();
		      this.DisplayDownload.setVisible(true);

		      while ((this.bytesRead = this.rbc.read(this.buffer)) > 0) {
		        fos.write(this.buffer, 0, this.bytesRead);
		        this.buffer = new byte[153600];
		        this.totalBytesRead += this.bytesRead;
		        this.percent = ((int)Math.round(this.totalBytesRead / fileLength * 100.0D));
		        System.out.println("Bytes readed: " + (int)this.totalBytesRead + "/" + (int)fileLength + " " + this.percent + "%");
		        this.DisplayDownload.Update(this.percent * 20);
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
			new File(PathToClean + "\\Libraries.txt").delete();
			new File(PathToClean + "\\Libraries.zip").delete();
			new File(PathToClean + "\\Libraries.z01").delete();
			new File(PathToClean + "\\Libraries.md5").delete();
			new File(PathToClean + "\\Versions.txt").delete();
			new File(PathToClean + "\\Versions.zip").delete();
			new File(PathToClean + "\\Versions.z01").delete();
			new File(PathToClean + "\\Versions.md5").delete();
			System.out.println("Directory cleaned up!");
	 }
	 
	 public void Launch() {
		 try {
			String OS = System.getProperty("os.name").toUpperCase();
			if(OS.contains("WIN")) {
				AppDataPath = System.getenv("APPDATA");
			} else if(OS.contains("MAC")) {
				AppDataPath = System.getProperty("user.home") + "/Library/Application " + "Support";
			} else if(OS.contains("NUX")) {
			    AppDataPath = System.getProperty("user.home");
			} else {
				AppDataPath =  System.getProperty("user.dir");
			}
			
			System.out.println(AppDataPath);
			
			MinePod = "\\.MinePod";
			MinePodLibrariesZip = AppDataPath + MinePod + "\\Libraries.zip";
			MinePodVersionsZip = AppDataPath + MinePod + "\\Versions.zip";
			MinecraftLaunch = new File(AppDataPath + "\\.MinePod\\Minecraft.jar");
			ProfilesPath = AppDataPath + "\\.minecraft\\launcher_profiles.json";
			
			
			if(!new File(AppDataPath + "\\.minecraft\\libraries").exists()) {
				new File(AppDataPath + "\\.minecraft\\libraries").mkdir();
			}
			
			if(!new File(AppDataPath + "\\.minecraft\\versions").exists()) {
				new File(AppDataPath + "\\.minecraft\\versions").mkdir();
			}
			
			Clean(AppDataPath + MinePod);
			
			if(!MinecraftLaunch.exists()) {
				Downloader(new URL("http://assets.minepod.fr/launcher/minecraft.jar"), new FileOutputStream(AppDataPath + "\\.MinePod" + "\\Minecraft.jar"));		
			}
				
			Downloader(new URL("http://assets.minepod.fr/launcher/versions/libraries.txt"), new FileOutputStream(AppDataPath + MinePod + "\\Libraries.txt"));
			latestVersionLibraries = ClassFile.ReadFile(AppDataPath + MinePod + "\\Libraries.txt", StandardCharsets.UTF_8);

			Downloader(new URL("http://assets.minepod.fr/launcher/md5.php?file=" + latestVersionLibraries), new FileOutputStream(AppDataPath + MinePod + "\\Libraries.md5"));
			Zip(AppDataPath + "\\.minecraft\\libraries", MinePodLibrariesZip);

			if(!GetMd5.VerifyMd5(new File(AppDataPath + MinePod + "\\Libraries.md5"), new File(MinePodLibrariesZip))) {
				new File(MinePodLibrariesZip).delete();
				new File(AppDataPath + "\\.minecraft\\libraries").delete();
				Downloader(new URL(latestVersionLibraries), new FileOutputStream(MinePodLibrariesZip));
				UnZip(MinePodLibrariesZip, AppDataPath + "\\.minecraft" + "\\libraries");
			}
			
			
			Downloader(new URL("http://assets.minepod.fr/launcher/versions/versions.txt"), new FileOutputStream(AppDataPath + MinePod + "\\Versions.txt"));
			latestVersionVersions = ClassFile.ReadFile(AppDataPath + MinePod + "\\Versions.txt", StandardCharsets.UTF_8);

			Downloader(new URL("http://assets.minepod.fr/launcher/md5.php?file=" + latestVersionVersions), new FileOutputStream(AppDataPath + MinePod + "\\Versions.md5"));
			Zip(AppDataPath + "\\.minecraft\\versions\\MinePod", MinePodVersionsZip);

			if(!GetMd5.VerifyMd5(new File(AppDataPath + MinePod + "\\Versions.md5"), new File(MinePodVersionsZip))) {
				new File(MinePodVersionsZip).delete();
				new File(AppDataPath + "\\.minecraft\\versions\\MinePod").delete();
				Downloader(new URL(latestVersionVersions), new FileOutputStream(MinePodVersionsZip));
				UnZip(MinePodVersionsZip, AppDataPath + "\\.minecraft\\versions\\MinePod");
			}
			
			
			if(!new File(ProfilesPath).exists()) {
				Downloader(new URL("http://assets.minepod.fr/launcher/launcher_profiles.json"), new FileOutputStream(ProfilesPath));
			} else {
				String Profile = ClassFile.ReadFile(ProfilesPath, StandardCharsets.UTF_8);
				if(!Profile.contains("MinePod")) {
					new File(ProfilesPath).delete();
					Profile = Profile.substring(0, 19) + "    \"MinePod\": {\n      \"name\": \"MinePod\",\n      \"lastVersionId\": \"MinePod\",\n      \"javaArgs\": \"-Xmx1G -Dfml.ignoreInvalidMinecraftCertificates\u003dtrue -Dfml.ignorePatchDiscrepancies\u003dtrue\"\n    }," + Profile.substring(20);
					Profile = Profile.replace("\"selectedProfile\": \"(Default)\",", "\"selectedProfile\": \"MinePod\",");
					ClassFile.WriteFile(ProfilesPath, Profile);
				}
			}
			
			new MPLoader(MinecraftLaunch.getAbsolutePath());
			
			Clean(AppDataPath + MinePod);
			
			System.exit(0);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
}

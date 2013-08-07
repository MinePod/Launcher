package fr.minepod.launcher;

import java.io.IOException;

public class Start {
	public static void main(String[] args) throws IOException {
		if(args.length != 0) {
			new Config().BootstrapVersion(args[0]);
		} else {
			new Config().BootstrapVersion("> 1.0.6");
		}
		new Config().GetConfig();
		new Debug().Set();
		new Downloader().DownloadRequiredFiles();
	}
}

package fr.minepod.launcher;

import java.io.IOException;

public class Start {
	public static void main(String[] args) throws IOException {
		new Config().GetConfig();
		new Debug().Set();
		new Downloader().DownloadRequiredFiles();
	}
}

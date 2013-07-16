package fr.minepod.launcher;

import java.io.IOException;

public class Start {
	public static void main(String[] args) {
		new Downloader().Launch();
		Runtime runTime = Runtime.getRuntime();
		/*try {
			Process process = runTime.exec(new String[] {""});
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}

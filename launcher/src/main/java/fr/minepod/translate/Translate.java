package fr.minepod.translate;

import java.util.LinkedHashMap;

public class Translate {
	private static LinkedHashMap<String,String> LHM = new LinkedHashMap<String,String>();
	
	public static String get(String key) {
		if(LHM.containsKey(key))
			return LHM.get(key);
		else
			return "Not translated: " + key;
	}
	
	public void set(String key, String value) {
		if(!LHM.containsKey(key))
			LHM.put(key, value);
	}
	
	public Translate(String language) {
		if(language == "fr_FR") {
			this.set("LaunchButton", "Jouer");
			this.set("OpenDebugConsoleGui", "Ouvrir la console de debug");
			this.set("DebugConsoleGuiName", "Console de debug");
			this.set("OpenAboutGui", "A propos");
			this.set("AboutGuiName", "A propos");
			this.set("DebugInformations", "Informations de debug");
			this.set("Error", "Erreur");
			this.set("WhenExecute", "En executant");
			this.set("WhenDownloading", "En telechargeant");
			this.set("Tools", "Outils");
			this.set("Options", "Options");
		} else if(language == "en_US") {
			this.set("LaunchButton", "Play");
			this.set("OpenDebugConsole", "Open debug console");
			this.set("DebugConsoleGuiName", "Debug console");
			this.set("OpenAboutGui", "About");
			this.set("AboutGuiName", "About");
			this.set("DebugInformations", "Debug informations");
			this.set("Error", "Error");
			this.set("WhenExecute", "When executing");
			this.set("WhenDownload", "When downloading");
			this.set("Tools", "Tools");
			this.set("Options", "Options");
		}
	}
}

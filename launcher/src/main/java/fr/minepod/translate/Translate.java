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
}

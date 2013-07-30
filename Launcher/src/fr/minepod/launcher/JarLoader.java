package fr.minepod.launcher;

public class JarLoader {
	public JarLoader(String path) throws Exception {
		Runtime runtime = Runtime.getRuntime();
			runtime.exec("\"" + System.getProperty("java.home") + "\\bin\\javaw.exe\" -jar -Xmx1G \"" + path + "\"");
		}
}

package fr.minepod.launcher;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logger {

	public void SetLogger(String LogFile) throws IOException {
		java.util.logging.Logger logger = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
	    logger.setLevel(Level.INFO);

	    FileHandler file = new FileHandler(LogFile);
	    SimpleFormatter formatter = new SimpleFormatter();
	    file.setFormatter(formatter);
	    logger.addHandler(file);
	}
}

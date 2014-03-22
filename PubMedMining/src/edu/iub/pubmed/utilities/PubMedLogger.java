package edu.iub.pubmed.utilities;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PubMedLogger {
	
	private static Logger pubMedLogger = null;
	
	private PubMedLogger(){
		
	}
	
	private static Logger initialize() {
		FileHandler fileHandler = null;
		pubMedLogger = Logger.getLogger("PubMed Logger");
		try {
			fileHandler = new FileHandler("C:\\Drives\\Spring14\\LibIndStdy\\Logs\\ErrorFile.log");
			pubMedLogger.addHandler(fileHandler);
			fileHandler.setFormatter(new SimpleFormatter());
		} catch (Exception ex) {
			pubMedLogger.log(Level.WARNING,
					"Exception while adding fileHandler");
		}
		return pubMedLogger;
	}
	
	public static Logger getInstance(){
		if(pubMedLogger == null){
			return initialize();
		} else {
			return pubMedLogger;
		}
	}

	public static void main(String args[]){
		Logger logger  = PubMedLogger.getInstance();
		logger.log(Level.INFO,"Writing to File");
	}
}

package edu.iub.pubmed.file;

import java.io.File;
import java.util.Iterator;

import edu.iub.pubmed.PubmedCentral;

public class FileOperations {

	public void retrieveFiles(String path) {
		File rootDirectory = null;
		File[] subFiles = null;
		File currentFile = null;
		rootDirectory = new File(path);
		if (rootDirectory != null && rootDirectory.exists()) {
			subFiles = rootDirectory.listFiles();
			for (int index = 0; index < subFiles.length; index++) {
				currentFile = subFiles[index];
				if (currentFile.isFile()) {
					PubmedCentral central = new PubmedCentral();
					central.loadFileToDB(currentFile.getAbsolutePath());
				} else if (currentFile.isDirectory()) {
					retrieveFiles(currentFile.getAbsolutePath());
				}
			}
		}

	}

	public Iterator<String> completedFiles(String pathName) {
		return null;
	}

	public static void main(String[] args) {
		FileOperations fileOperations = new FileOperations();
		fileOperations
				.retrieveFiles("C:\\Drives\\Spring14\\LibIndStdy\\Pubmed\\DataSet\\Sample");

	}

}

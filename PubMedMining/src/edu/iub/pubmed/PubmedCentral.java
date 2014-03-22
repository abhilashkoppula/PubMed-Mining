package edu.iub.pubmed;

import java.util.logging.Logger;

import edu.iub.pubmed.dto.Article;
import edu.iub.pubmed.parsing.RootParser;
import edu.iub.pubmed.utilities.PubMedLogger;

public class PubmedCentral {
	Logger logger = PubMedLogger.getInstance();
	
	
	
	
	public void loadFileToDB(String fileName){
		Article article = new Article();
		try{
			RootParser parser = new RootParser(article,fileName);
			parser.parse(fileName);
			System.out.println("Loading Completed");
			return;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		String fileName = "C:\\Drives\\spring14\\LibIndstdy\\Pubmed\\DataSet\\Data\\Alcohol_Alcohol\\Alcohol_Alcohol_2008_Aug_7_Nov-Dec_43(6)_669-674.nxml";
		PubmedCentral central = new PubmedCentral();
		central.loadFileToDB(fileName);
		return;
	}

}

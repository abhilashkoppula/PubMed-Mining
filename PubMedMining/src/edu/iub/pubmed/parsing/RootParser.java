package edu.iub.pubmed.parsing;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Sample class to Explore the XML document
 * @author Abhilash
 *
 */
public class RootParser {

	/**
	 * Parses the given file using Dom Parser
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Document parseFile(String fileName) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fileName);
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	/**
	 * Prints information about given child nodes
	 * @param node
	 * @param name
	 */
	public void printChildNodes(NodeList node , String name){		
	    for(int i = 0 ; i < node.getLength() ; i++){
	    	System.out.println(name + node.item(i).getNodeName());
	    }
	}
	
	/**
	 * Journal MetaData
	 * @param document
	 */
	public void printJournalMetadata(Document document){
	System.out.println("*************** Journal MetaData ***********************");
		
		Node jouranlMetadata = document.getElementsByTagName("journal-meta").item(0);
		NodeList journalMetaNodes = jouranlMetadata.getChildNodes();
		for(int index = 0 ; index < journalMetaNodes.getLength() ; index++){
			System.out.println(journalMetaNodes.item(index).getNodeName() + " - " + 
					journalMetaNodes.item(index).getTextContent());
		}
		//System.out.println("***************End of Journal MetaData ***********************");
	}
	
/**
 * Article Metadata
 * @param document
 */
	
	public void printArticleInformation(Document document) {
		System.out.println("*************** Article MetaData ***********************");
		NodeList articleMetadata = document.getElementsByTagName("article-meta");
		Node articleMeta = articleMetadata.item(0);
		NodeList articleMetaNodes = articleMeta.getChildNodes();
		for(int index = 0 ; index < articleMetaNodes.getLength() ; index++){
			System.out.println(articleMetaNodes.item(index).getNodeName() + " - " + 
					"");
		}
		//System.out.println("***************Article MetaData ***********************");
	}
	
	/**
	 *  References information
	 * @param document
	 */
	public void printReferenceInformation(Document document){
		System.out.println("*************** References Information ***********************");
		NodeList references = document.getElementsByTagName("ref-list").item(0).getChildNodes();
		System.out.println("Number of references ::" + references.getLength());
	//	System.out.println("***************End of References Information ***********************");
	}
	
	/**
	 * XML Walkthrough
	 * @param doc
	 */
	public void exploreDocument(Document doc){
		System.out.println("*************** XML document Structure  ***********************");
		
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		Element rootElement = doc.getDocumentElement();
		/********************************************************
		 *  Finding child Nodes 								*
		 ********************************************************/
		printChildNodes(rootElement.getChildNodes(), "Child Elements :");
	    /********************************************************
		 *  Finding Front Elements								*
		 ********************************************************/
		printChildNodes(rootElement.getElementsByTagName("front").item(0).getChildNodes(), 
				"FrontNode Elements :");
		    /********************************************************
		 *  Finding Body Elements								*
		 ********************************************************/
		printChildNodes(rootElement.getElementsByTagName("body").item(0).getChildNodes(), 
				"Body Elements :");
	    /********************************************************
		 *  Finding Back Elements								*
		 ********************************************************/
		printChildNodes(rootElement.getElementsByTagName("back").item(0).getChildNodes(), 
				"Back Elements :");
		//System.out.println("*************** End of XML document Structure ***********************");
		
		printReferenceInformation(doc);
		printJournalMetadata(doc);
		printArticleInformation(doc);
		
	}
	
	
	
	public static void main(String args[]) throws Exception{
		String fileName = "C:\\Drives\\spring14\\LibIndstdy\\Pubmed\\Abdom_Imaging\\Abdom_Imaging_2008_Jul_10_33(4)_407-416.nxml";
		RootParser rp = new RootParser();
		Document document = rp.parseFile(fileName);
		rp.exploreDocument(document);
	}
}

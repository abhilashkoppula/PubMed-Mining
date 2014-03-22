package edu.iub.pubmed.parsing;

import java.util.HashMap;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MetaDataParser {

	public void parseArticleMetaData(Document root) {

	}

	/**
	 * 
	 * @param root
	 * @throws XPathExpressionException 
	 */
	public void praseKeyWords(Document root) throws XPathExpressionException {
		HashMap<String, List<String>> keyWords = new HashMap<>();
		
		XPath path = XPathFactory.newInstance().newXPath();
		NodeList nodes =  (NodeList) path.evaluate("/article/front/article-meta/kwd-group[title = 'Abc']/kwd", root.getDocumentElement(),XPathConstants.NODESET);
		
		
		System.out.println("Length from XPath " + nodes.getLength());
		
		NodeList keyWordGroups = root.getElementsByTagName("kwd-group");
		Node keyWordGroup = null;
		for (int index = 0; index < keyWordGroups.getLength(); index++) {
			keyWordGroup = keyWordGroups.item(index);
			System.out.println(keyWordGroup.getTextContent());
		}
	}
}

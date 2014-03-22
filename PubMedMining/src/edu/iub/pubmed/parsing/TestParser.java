package edu.iub.pubmed.parsing;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.iub.pubmed.dao.ArticleHome;
import edu.iub.pubmed.dto.Article;
import edu.iub.pubmed.dto.Author;
import edu.iub.pubmed.dto.AuthorReference;
import edu.iub.pubmed.dto.Category;
import edu.iub.pubmed.dto.CategoryReference;
import edu.iub.pubmed.dto.CategoryReferenceId;
import edu.iub.pubmed.dto.Citation;
import edu.iub.pubmed.dto.CitationReference;
import edu.iub.pubmed.dto.Conference;
import edu.iub.pubmed.dto.Keyword;
import edu.iub.pubmed.dto.KeywordReference;
import edu.iub.pubmed.dto.PubmedReference;
import edu.iub.pubmed.dto.PubmedReferenceId;
import edu.iub.pubmed.dto.Volume;
import edu.iub.pubmed.utilities.PubMedLogger;

/**
 * Test class to Explore the XML document
 * 
 * @author Abhilash
 * 
 */
public class TestParser {
	Logger logger = PubMedLogger.getInstance();
	XPath path = null;
	Article currentArticle = null;
	String fileName = null;
	ArticleHome articleDAO = null;

	public TestParser(Article article, String fileName) {
		path = XPathFactory.newInstance().newXPath();
		currentArticle = article;
		this.fileName = fileName;
		//articleDAO = new ArticleHome();
	}

	/**
	 * Parses the given file using Dom Parser
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Document parseFile() throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fileName);
		doc.getDocumentElement().normalize();
		return doc;
	}

	

	public void parse(String fileName) throws Exception {
		logger.log(Level.INFO,
				"***********************************************************");
		logger.log(Level.INFO, "Parsing file " + fileName);
		Document document = parseFile();
		extractFields(document);
	}

	public void extractFields(Document document) {
		try {
			//articleDAO.getCurrentSession().beginTransaction();
			logger.log(Level.INFO, "### Parsing Article MetaData ###");
			extractArticleMetaData(document);
			logger.log(Level.INFO, "### Parsing Author Information ###");
			extractAuthorInformation(document);
			logger.log(Level.INFO, "### Parsing Key Words  ###");
			extractKeyWords(document);
			logger.log(Level.INFO, "### Parsing Citations ###");
			extractCitations(document);
			logger.log(Level.INFO, "### Parsing Categories ###");
			extractArticleCategories(document);
			logger.log(Level.INFO, "### Parsing Volume Information ###");
			extractVolumeDetails(document);
			logger.log(Level.INFO, "### Parsing Conference Details ###");
			extractConferenceDetails(document);
			//articleDAO.persistArticle(currentArticle);
			//articleDAO.getCurrentSession().getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//articleDAO.sessionClose();
		}
	}

	/**
	 * Extracts following from the article-meta node
	 *  i) PubMedId
	 *  ii) Article Title
	 *  iii) Article Subtitle
	 *  iv) Abstract text
	 * @param document
	 */
	private void extractArticleMetaData(Document document) {
		String pubmedId = null;
		String articleTitle = null;
		String articleSubTitle = null;
		String abstractText = null;
		try {
			Node pubMedNode = (Node) path
					.evaluate(
							"/article/front/article-meta/article-id[@pub-id-type='pmid']",
							document.getDocumentElement(), XPathConstants.NODE);
			if (pubMedNode == null || pubMedNode.getTextContent() == null) {
				throw new Exception("No PubMed Id");
			}
			pubmedId = pubMedNode.getTextContent();
		} catch (Exception ex) {
			logger.severe("Exception while Parsing PUBMedId for file "
					+ fileName);
			ex.printStackTrace();
			return;
		}

		try {
			articleTitle = (String) path
					.evaluate(
							"/article/front/article-meta/title-group/article-title/text()",
							document.getDocumentElement(),
							XPathConstants.STRING);
			articleSubTitle = (String) path.evaluate(
					"/article/front/article-meta/title-group/subtitle/text()",
					document.getDocumentElement(), XPathConstants.STRING);
			if (articleTitle == null) {
				throw new Exception("No Article Title");
			}
		} catch (Exception ex) {
			logger.severe("Exception while Parsing PUBMedId for file "
					+ fileName);
			ex.printStackTrace();
			return;
		}

		try {
			Node abstractNode = (Node) path.evaluate(
					"/article/front/article-meta/abstract",
					document.getDocumentElement(), XPathConstants.NODE);
			if (abstractNode == null || abstractNode.getTextContent() == null) {
				throw new Exception("No Abstract Text");
			}
			abstractText = abstractNode.getTextContent();
		} catch (Exception ex) {
			logger.severe("Exception while Parsing Abstract Text for file "
					+ fileName);
			ex.printStackTrace();
			return;
		}

		currentArticle.setPubmedId(pubmedId);
		currentArticle.setArticleTitle(articleTitle);
		currentArticle.setSubtitle(articleSubTitle);
		currentArticle.setAbstractText(abstractText);
	}

	/**
	 * Extracts Author Information by retrieving the author nodes and traversing them
	 * to find the name of the author . Inserts into author and authorReference Table
	 * @param document
	 */
	private void extractAuthorInformation(Document document) {
		List<Author> authors = null;
		Author author = null;
		try {
			NodeList authorsNodes = (NodeList) path
					.evaluate(
							"/article/front/article-meta/contrib-group/contrib[@contrib-type = 'author']",
							document.getDocumentElement(),
							XPathConstants.NODESET);
			if (authorsNodes == null || authorsNodes.getLength() == 0) {
				logger.severe("No authors found for this article" + fileName);
				throw new Exception("No authors");
			}
			authors = new ArrayList<>();
			Element authorElement = null;
			Element nameElement = null;
			for (int index = 0; index < authorsNodes.getLength(); index++) {
				authorElement = (Element) authorsNodes.item(index);
				nameElement = (Element) authorElement.getElementsByTagName(
						"name").item(0);
				if(nameElement != null && nameElement.getChildNodes() != null) {
					
					author = new Author();
				for (int childIndex = 0; childIndex < nameElement
						.getChildNodes().getLength(); childIndex++) {
					Node nameChildNode = nameElement.getChildNodes().item(
							childIndex);
					if (nameChildNode.getNodeName() == "surname") {
						author.setSurname(nameChildNode.getTextContent());
					} else if (nameChildNode.getNodeName() == "given-names") {
						author.setGivenName(nameChildNode.getTextContent());
					}
				}
				authors.add(author);
				}
			}
		} catch (Exception ex) {
			logger.severe("Exception while parsing Author Information "
					+ fileName);
			ex.printStackTrace();
			return;
		}
		Set<AuthorReference> authorReferences = new HashSet<>();
		AuthorReference authorReference = null;

		for (Author authorI : authors) {
			//articleDAO.persistAuthor(authorI);
			authorReference = new AuthorReference();
			authorReference.setRole("");
			authorReference.setComments("");
//			authorReference.setId(new AuthorReferenceId(0, currentArticle
//					.getPubmedId(), authorI.getId()));
			//articleDAO.persistAuthorReference(authorReference);
			authorReferences.add(authorReference);
		}
	}

	/**
	 * Extracts and saves the keywords .
	 * @param document
	 */
	private void extractKeyWords(Document document) {
		List<Keyword> keyWords = new ArrayList<Keyword>();
		Keyword keyword = null;
		NodeList keywordGroups = null;
		try {
			keywordGroups = (NodeList) path.evaluate("/article/front/article-meta/kwd-group",document.getDocumentElement(), XPathConstants.NODESET);
			if (keywordGroups == null || keywordGroups.getLength() == 0) {
				logger.severe("No KeyWords found for this article" + fileName);
				throw new Exception("No KeyWords");
			}
			Element keywordGroup = null;
			String kwdGroupType = null;
			for (int index = 0; index < keywordGroups.getLength(); index++) {
				keywordGroup = (Element) keywordGroups.item(index);
				kwdGroupType = keywordGroup.getAttribute("kwd-group-type");
				for (int childIndex = 0; childIndex < keywordGroup.getChildNodes().getLength(); childIndex++) {
					Element keywordNode = (Element) keywordGroup
							.getChildNodes().item(childIndex);
					if (keywordNode.getNodeName() == "kwd") {
						keyword = new Keyword();
						keyword.setKwdGroupType(kwdGroupType);
						keyword.setKeywordText(keywordNode.getTextContent());
						keyWords.add(keyword);
					}

				}

			}
			Set<KeywordReference> kwdRefs = new HashSet<>();
			KeywordReference kwdRef = null;
			for (Keyword kwd : keyWords) {
				//articleDAO.persistKeyword(kwd);
//				kwdRef = new KeywordReference(new KeywordReferenceId(
//						currentArticle.getPubmedId(), kwd.getKeywordId()));
				//articleDAO.persistKeywordReference(kwdRef);
				kwdRefs.add(kwdRef);
			}
			// currentArticle.setKeywordReferences(kwdRefs);
		} catch (Exception ex) {
			logger.severe("Exception while parsing KeyWord Information "+ fileName);
			ex.printStackTrace();
			return;
		}
	}

	private void extractCitations(Document document) {
		List<String> validCitations = new ArrayList<String>();
		validCitations.add("mixed-citation");
		validCitations.add("element-citation");
		validCitations.add("citation");

		List<Citation> externalCitations = new ArrayList<>();
		Citation externalCitation = null;
		List<String> pubMedCitations = new ArrayList<>();
		String pubMedCitation = null;
		NodeList refNodes = document.getElementsByTagName("ref");

		for (int index = 0; index < refNodes.getLength(); index++) {
			Element refNode = (Element) refNodes.item(index);
			NodeList childNodes = refNode.getChildNodes();
			for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
				Element childNode = (Element) childNodes.item(childIndex);
				if (validCitations.contains(childNode.getNodeName())) {
					NodeList pubMedNode = childNode
							.getElementsByTagName("pub-id");
					boolean pubmedCitation = false;
					if (pubMedNode != null && pubMedNode.getLength() >= 1) {
						for (int pubIdIndex = 0; pubIdIndex < pubMedNode
								.getLength(); pubIdIndex++) {
							Element pubMedElement = (Element) pubMedNode
									.item(pubIdIndex);
							if (pubMedElement.getAttribute("pub-id-type")
									.equals("pmid")) {
								pubMedCitation = pubMedElement.getTextContent();
								pubMedCitations.add(pubMedCitation);
								pubmedCitation = true;
								break;
							}
						}
					}
					if (pubmedCitation) {
						continue;
					}
					String citationType = null;
					String publicationformat = null;
					String publisherType = null;
					String source = null;
					String authors = null;
					String articleTitle = null;
					String volume = null;
					String issue = null;
					String name = null;
					Date date = null;

					if (!childNode.getAttribute("publication-type").equals(
							"other")) {

						for (int cIndex = 0; cIndex < childNode.getChildNodes()
								.getLength(); cIndex++) {
							Node citationChildNode = childNode.getChildNodes()
									.item(cIndex);
							String nodeName = citationChildNode.getNodeName();
							String nodeValue = citationChildNode
									.getTextContent();
							if (nodeName.equals("article-title")) {
								articleTitle = nodeValue;
								if (articleTitle.length() > 100) {
									articleTitle = articleTitle
											.substring(0, 98);
								}
							} else if (nodeName.equals("source")) {
								source = nodeValue;
							} else if (nodeName.equals("volume")) {
								volume = nodeValue;
							} else if (nodeName.equals("issue")) {
								issue = nodeValue;
							} else if (nodeName.equals("person-group")) {
								// System.out.println("Found person group");
							}
						}
						if (childNode.getNodeName() == "citation") {
							citationType = childNode.getAttribute("citation");
						} else {
							citationType = childNode
									.getAttribute("publication-type");
							publicationformat = childNode
									.getAttribute("publication-format");
							publisherType = childNode
									.getAttribute("publisher-type");
						}
						externalCitation = new Citation(citationType,
								publicationformat, publisherType, source,
								authors, articleTitle, volume, issue, name,
								date);
						externalCitations.add(externalCitation);
					}

				}
			}
		}

		Set<CitationReference> citationReference = new HashSet<>();
		for (Citation cite : externalCitations) {
			//articleDAO.persistCitation(cite);
//			CitationReference cRef = new CitationReference(
//					new CitationReferenceId(currentArticle.getPubmedId(),
//							cite.getCitationId()));
		//	citationReference.add(cRef);
			//articleDAO.persistCitationReference(cRef);
		}
		Set<PubmedReference> pubmedReferences = new HashSet<>();
		for (String pubMedId : pubMedCitations) {
			PubmedReference pubmedReference = new PubmedReference(
					new PubmedReferenceId(currentArticle.getPubmedId(),
							pubMedId));
			pubmedReferences.add(pubmedReference);
			//articleDAO.persistPubmedReference(pubmedReference);
		}
		// currentArticle.setCitationReferences(citationReference);
		// currentArticle.setPubmedReferences(pubmedReferences);
	}

	private void extractVolumeDetails(Document document) {
		try {
			Node volume = (Node) path.evaluate(
					"/article/front/article-meta/volume",
					document.getDocumentElement(), XPathConstants.NODE);
			Node issue = (Node) path.evaluate(
					"/article/front/article-meta/issue",
					document.getDocumentElement(), XPathConstants.NODE);
			Node issueTitle = (Node) path.evaluate(
					"/article/front/article-meta/issue-title",
					document.getDocumentElement(), XPathConstants.NODE);

			String journalTitle = (String) path
					.evaluate(
							"/article/front/journal-meta/journal-title-group/journal-title/text()",
							document.getDocumentElement(),
							XPathConstants.STRING);
			if (journalTitle == null) {
				journalTitle = (String) path.evaluate(
						"/article/front/journal-meta/jounrnal-title/text()",
						document.getDocumentElement(), XPathConstants.STRING);
			}
			String volumeId = (String) path.evaluate(
					"/article/front/journal-meta/issn/text()",
					document.getDocumentElement(), XPathConstants.STRING);

			Volume vol = new Volume();
			vol.setVolumeId(volumeId);
			vol.setIsbn(volumeId);
			if (issue != null) {
				vol.setIssue(issue.getTextContent());
			}
			if (issueTitle != null) {
				vol.setIssueTitle(issueTitle.getTextContent());
			}
			if (volume != null) {
				vol.setVolume(volume.getTextContent());
			}
			//
			//articleDAO.persistVolume(vol);
			currentArticle.setVolumeId(vol.getVolumeId());
		} catch (Exception ex) {
			logger.severe("Exception while parsing Volume Information "
					+ fileName);
			ex.printStackTrace();
			return;
		}

	}

	/**
	 * Extracts the Conference node from article-meta and traverses to its child Nodes
	 * to get conference details
	 * @param document
	 * @return
	 */
	private void extractConferenceDetails(Document document) {
		Conference conf = null;
		NodeList conferenceNode = null;
		try {
		 conferenceNode = (NodeList) path.evaluate(
					"/article/front/article-meta/conference",
					document.getDocumentElement(), XPathConstants.NODESET);
			 if (conferenceNode != null && conferenceNode.getLength() == 1) {
				Element conferenceElement = (Element) conferenceNode.item(0);
				conf = new Conference();
				for (int index = 0; index < conferenceElement.getChildNodes()
						.getLength(); index++) {
					Element childNode = (Element) conferenceElement
							.getChildNodes().item(index);
					String nodeName = childNode.getNodeName();
					if (nodeName.equals("conf-date")) {
						// TO Do  traverse further to find the Day,Month , Year
						Date date = new Date(System.currentTimeMillis());
						conf.setDate(date);
					} else if (nodeName.equals("conf-name")) {
						conf.setName(childNode.getTextContent());
					} else if (nodeName.equals("conf-loc")) {
						conf.setLoc(childNode.getTextContent());
					} else if (nodeName.equals("conf-sponsor")) {
						conf.setSponsor(childNode.getTextContent());
					} else if (nodeName.equals("conf-acronym")) {
						conf.setAcronym(childNode.getTextContent());
					} else if (nodeName.equals("conf-theme")) {
						conf.setTheme(childNode.getTextContent());
					} else if (nodeName.equals("conf-num")) {
						conf.setNum(childNode.getTextContent());
					}
				}
				//articleDAO.persistConference(conf);
				currentArticle.setConferenceId(conf.getConferenceId());
			}
		} catch (Exception ex) {
			logger.severe("Exception while parsing Conference details "
					+ fileName);
			ex.printStackTrace();
		}
	}

	/**
	 * Extracts category information Recursively i.e for each category if there are child
	 * categories they are traversed further.
	 * @param document
	 */
	private void extractArticleCategories(Document document) {
		List<Category> categories = new ArrayList<>();
		Category category = null;
		NodeList categoryNodes = null;
		try {
			categoryNodes = (NodeList) path.evaluate(
							"/article/front/article-meta/article-categories/subj-group",
							document.getDocumentElement(),
							XPathConstants.NODESET);
			Element categoryNode = null;
			for (int index = 0; index < categoryNodes.getLength(); index++) {
				categoryNode = (Element) categoryNodes.item(index);
				category = new Category();
				category.setSubject(categoryNode.getFirstChild()
						.getTextContent());
			//	articleDAO.persistCategory(category);
				categories.add(category);
				if (categoryNode.getChildNodes().getLength() > 1) {
					setSubCategories(categoryNode.getLastChild(), category,
							categories);
				}
				printCategoryType(categoryNode);
			}
			//Set<CategoryReference> categoryReferences = new HashSet<>();
			CategoryReference categoryReference = null;
			if (categories.size() == 0) {
				logger.severe(" No categories found ");
			}
			for (Category categoryI : categories) {
				if (category.getSubject() != null) {
//					categoryReference = new CategoryReference(
//							new CategoryReferenceId(
//									currentArticle.getPubmedId(),
//									categoryI.getCategoryId()));
				//	articleDAO.persistCatReference(categoryReference);
				}
			}
		} catch (Exception ex) {
			logger.severe("Exception while parsing Categories "
					+ fileName);
			ex.printStackTrace();
		}
	}

	private void printCategoryType(Node category) {
		Element categoryE = (Element) category;
		String categoryType = categoryE.getAttribute("subj-group-type");
		if (categoryType == "heading") {
			logger.info("Article with Category Heading found ::" + fileName);
		} else if (categoryType == "hwp-journal-coll") {
			logger.info("Article with Category hwp-journal-coll found ::"
					+ fileName);
		} else if (categoryType == "Discipline") {
			logger.info("Article with Category Discipline found ::" + fileName);
		} else if (categoryType == "Primary") {
			logger.info("Article with Category Primary found ::" + fileName);
		} else if (categoryType == "Secondary") {
			logger.info("Article with Category Secondary found ::" + fileName);
		}
	}

	private void setSubCategories(Node lastChild, Category category,
			List<Category> categories) {
		Category subCategory = null;
		subCategory = new Category();
		subCategory.setParentCategoryId(category.getCategoryId());
		// subCategory.setCategory(category);

		subCategory.setSubject(lastChild.getFirstChild().getNodeValue());
		//articleDAO.persistCategory(subCategory);
		categories.add(subCategory);
		printCategoryType(lastChild);
		if (lastChild.getChildNodes().getLength() > 1) {
			setSubCategories(lastChild.getLastChild(), subCategory, categories);
		}
	}

	private void extractFullText(Document document) {

		HashMap<String, StringBuilder> bodyText = new HashMap<>();
		try {
			NodeList sectionNodes = (NodeList) path.evaluate(
					"/article/body/sec", document.getDocumentElement(),
					XPathConstants.NODESET);
			Element sectionNode = null;
			Node sectionChildNode = null;
			String sectionTitle = null;
			for (int index = 0; index < sectionNodes.getLength(); index++) {
				sectionNode = (Element) sectionNodes.item(index);
				NodeList subSections = (NodeList) path.evaluate("./sec/sec",
						sectionNode, XPathConstants.NODESET);
				System.out.println("Calculating SubSections size :: "
						+ subSections.getLength());
				sectionTitle = sectionNode.getFirstChild().getTextContent();
				bodyText.put(sectionTitle, new StringBuilder());
				for (int sectionNodeIndex = 0; sectionNodeIndex < sectionNode
						.getChildNodes().getLength(); sectionNodeIndex++) {
					sectionChildNode = sectionNode.getChildNodes().item(
							sectionNodeIndex);
					if (sectionChildNode.getNodeName() == "p") {
						bodyText.get(sectionTitle).append(
								sectionChildNode.getTextContent());
					}
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String key : bodyText.keySet()) {
			System.out.println(bodyText.get(key).toString());
		}
		System.out.println(bodyText.size());
	}
	
	public static void main(String[] args) throws Exception{
		String fileName = "C:\\Drives\\Spring14\\LibIndStdy\\Pubmed\\DataSet\\Sample\\Acc_Chem_Res\\Acc_Chem_Res_2009_Jun_16_42(6)_788-797.nxml";
		TestParser cp = new TestParser(new Article(), fileName);
		cp.parse(fileName);
	}

}

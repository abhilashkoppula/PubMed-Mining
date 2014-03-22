package edu.iub.pubmed.dao;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;


import org.hibernate.service.ServiceRegistry;

import edu.iub.pubmed.dto.Article;
import edu.iub.pubmed.dto.Author;
import edu.iub.pubmed.dto.AuthorReference;
import edu.iub.pubmed.dto.Category;
import edu.iub.pubmed.dto.CategoryReference;
import edu.iub.pubmed.dto.Citation;
import edu.iub.pubmed.dto.CitationReference;
import edu.iub.pubmed.dto.Conference;
import edu.iub.pubmed.dto.Keyword;
import edu.iub.pubmed.dto.KeywordReference;
import edu.iub.pubmed.dto.PubmedReference;
import edu.iub.pubmed.dto.Volume;

/**
 * Home object for domain model class Article.
 * @see .Article
 * @author Hibernate Tools
 */
public class ArticleHome {

	private static final Log log = LogFactory.getLog(ArticleHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();
	private Session session = null;

	protected SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
		serviceRegistryBuilder.applySettings(configuration.getProperties());

		ServiceRegistry serviceRegistry =  serviceRegistryBuilder.build();
		return configuration.buildSessionFactory(serviceRegistry);

	}
	
	public Session getCurrentSession(){
		if(session == null || !session.isConnected()){
			session = sessionFactory.openSession();
		}
		return session;
	}

	
	public void persist(Object instance){
		log.debug("persisting Article instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			getCurrentSession().flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("persist failed", re);
			throw re;
		}
	}

	public void sessionClose() {
		if(session != null){
			System.out.println("Closing Session");
			session.close();
		}
		
	}
	
}

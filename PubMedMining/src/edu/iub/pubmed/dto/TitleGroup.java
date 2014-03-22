package edu.iub.pubmed.dto;

// Generated Mar 19, 2014 7:17:36 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TitleGroup generated by hbm2java
 */
@Entity
@Table(name = "title_group", catalog = "pubmedcentral")
public class TitleGroup implements java.io.Serializable {

	private int titleGroupId;
	private String articleTitle;
	private String articleSubtitle;
	private String alternateTitle;
	private String transTitle;
	private String transSubtitle;

	public TitleGroup() {
	}

	public TitleGroup(int titleGroupId) {
		this.titleGroupId = titleGroupId;
	}

	public TitleGroup(int titleGroupId, String articleTitle,
			String articleSubtitle, String alternateTitle, String transTitle,
			String transSubtitle) {
		this.titleGroupId = titleGroupId;
		this.articleTitle = articleTitle;
		this.articleSubtitle = articleSubtitle;
		this.alternateTitle = alternateTitle;
		this.transTitle = transTitle;
		this.transSubtitle = transSubtitle;
	}

	@Id
	@Column(name = "title_group_id", unique = true, nullable = false)
	public int getTitleGroupId() {
		return this.titleGroupId;
	}

	public void setTitleGroupId(int titleGroupId) {
		this.titleGroupId = titleGroupId;
	}

	@Column(name = "article_title", length = 45)
	public String getArticleTitle() {
		return this.articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	@Column(name = "article_subtitle", length = 45)
	public String getArticleSubtitle() {
		return this.articleSubtitle;
	}

	public void setArticleSubtitle(String articleSubtitle) {
		this.articleSubtitle = articleSubtitle;
	}

	@Column(name = "alternate_title", length = 45)
	public String getAlternateTitle() {
		return this.alternateTitle;
	}

	public void setAlternateTitle(String alternateTitle) {
		this.alternateTitle = alternateTitle;
	}

	@Column(name = "trans_title", length = 45)
	public String getTransTitle() {
		return this.transTitle;
	}

	public void setTransTitle(String transTitle) {
		this.transTitle = transTitle;
	}

	@Column(name = "trans_subtitle", length = 45)
	public String getTransSubtitle() {
		return this.transSubtitle;
	}

	public void setTransSubtitle(String transSubtitle) {
		this.transSubtitle = transSubtitle;
	}

}
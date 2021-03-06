package edu.iub.pubmed.dto;

// Generated Mar 21, 2014 5:53:07 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Article generated by hbm2java
 */
@Entity
@Table(name = "article", catalog = "pubmedcentral")
public class Article implements java.io.Serializable {

	private String pubmedId;
	private Date pubDate;
	private String articleTitle;
	private String subtitle;
	private String abstractText;
	private Integer conferenceId;
	private String customText;
	private String volumeId;
	private Integer unstructuredKeywordGroupId;

	public Article() {
	}

	public Article(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	public Article(String pubmedId, Date pubDate, String articleTitle,
			String subtitle, String abstractText, Integer conferenceId,
			String customText, String volumeId,
			Integer unstructuredKeywordGroupId) {
		this.pubmedId = pubmedId;
		this.pubDate = pubDate;
		this.articleTitle = articleTitle;
		this.subtitle = subtitle;
		this.abstractText = abstractText;
		this.conferenceId = conferenceId;
		this.customText = customText;
		this.volumeId = volumeId;
		this.unstructuredKeywordGroupId = unstructuredKeywordGroupId;
	}

	@Id
	@Column(name = "pubmed_id", unique = true, nullable = false, length = 45)
	public String getPubmedId() {
		return this.pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pub_date", length = 19)
	public Date getPubDate() {
		return this.pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	@Column(name = "article_title", length = 1000)
	public String getArticleTitle() {
		return this.articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	@Column(name = "subtitle", length = 100)
	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	@Column(name = "abstract_text", length = 65535)
	public String getAbstractText() {
		return this.abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	@Column(name = "conference_id")
	public Integer getConferenceId() {
		return this.conferenceId;
	}

	public void setConferenceId(Integer conferenceId) {
		this.conferenceId = conferenceId;
	}

	@Column(name = "custom_text", length = 65535)
	public String getCustomText() {
		return this.customText;
	}

	public void setCustomText(String customText) {
		this.customText = customText;
	}

	@Column(name = "volume_id", length = 45)
	public String getVolumeId() {
		return this.volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	@Column(name = "unstructured_keyword_group_id")
	public Integer getUnstructuredKeywordGroupId() {
		return this.unstructuredKeywordGroupId;
	}

	public void setUnstructuredKeywordGroupId(Integer unstructuredKeywordGroupId) {
		this.unstructuredKeywordGroupId = unstructuredKeywordGroupId;
	}

}

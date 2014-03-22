package edu.iub.pubmed.dto;

// Generated Mar 19, 2014 7:17:36 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UnstructuredKeywordGroup generated by hbm2java
 */
@Entity
@Table(name = "unstructured_keyword_group", catalog = "pubmedcentral")
public class UnstructuredKeywordGroup implements java.io.Serializable {

	private UnstructuredKeywordGroupId id;
	private Article article;
	private String unstructuredKeyword;

	public UnstructuredKeywordGroup() {
	}

	public UnstructuredKeywordGroup(UnstructuredKeywordGroupId id,
			Article article, String unstructuredKeyword) {
		this.id = id;
		this.article = article;
		this.unstructuredKeyword = unstructuredKeyword;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
			@AttributeOverride(name = "pubmedId", column = @Column(name = "pubmed_id", nullable = false, length = 45)) })
	public UnstructuredKeywordGroupId getId() {
		return this.id;
	}

	public void setId(UnstructuredKeywordGroupId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pubmed_id", nullable = false, insertable = false, updatable = false)
	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Column(name = "unstructured_keyword", nullable = false, length = 256)
	public String getUnstructuredKeyword() {
		return this.unstructuredKeyword;
	}

	public void setUnstructuredKeyword(String unstructuredKeyword) {
		this.unstructuredKeyword = unstructuredKeyword;
	}

}

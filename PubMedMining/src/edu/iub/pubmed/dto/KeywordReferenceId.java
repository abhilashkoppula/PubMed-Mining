package edu.iub.pubmed.dto;

// Generated Mar 20, 2014 11:33:48 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * KeywordReferenceId generated by hbm2java
 */
@Embeddable
public class KeywordReferenceId implements java.io.Serializable {

	private String pubmedId;
	private int keywordGroupId;

	public KeywordReferenceId() {
	}

	public KeywordReferenceId(String pubmedId, int keywordGroupId) {
		this.pubmedId = pubmedId;
		this.keywordGroupId = keywordGroupId;
	}

	@Column(name = "pubmed_id", nullable = false, length = 45)
	public String getPubmedId() {
		return this.pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	@Column(name = "keyword_group_id", nullable = false)
	public int getKeywordGroupId() {
		return this.keywordGroupId;
	}

	public void setKeywordGroupId(int keywordGroupId) {
		this.keywordGroupId = keywordGroupId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof KeywordReferenceId))
			return false;
		KeywordReferenceId castOther = (KeywordReferenceId) other;

		return ((this.getPubmedId() == castOther.getPubmedId()) || (this
				.getPubmedId() != null && castOther.getPubmedId() != null && this
				.getPubmedId().equals(castOther.getPubmedId())))
				&& (this.getKeywordGroupId() == castOther.getKeywordGroupId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPubmedId() == null ? 0 : this.getPubmedId().hashCode());
		result = 37 * result + this.getKeywordGroupId();
		return result;
	}

}

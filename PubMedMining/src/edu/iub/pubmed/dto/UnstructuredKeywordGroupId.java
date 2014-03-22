package edu.iub.pubmed.dto;

// Generated Mar 19, 2014 7:17:36 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UnstructuredKeywordGroupId generated by hbm2java
 */
@Embeddable
public class UnstructuredKeywordGroupId implements java.io.Serializable {

	private int id;
	private String pubmedId;

	public UnstructuredKeywordGroupId() {
	}

	public UnstructuredKeywordGroupId(int id, String pubmedId) {
		this.id = id;
		this.pubmedId = pubmedId;
	}

	@Column(name = "id", nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "pubmed_id", nullable = false, length = 45)
	public String getPubmedId() {
		return this.pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UnstructuredKeywordGroupId))
			return false;
		UnstructuredKeywordGroupId castOther = (UnstructuredKeywordGroupId) other;

		return (this.getId() == castOther.getId())
				&& ((this.getPubmedId() == castOther.getPubmedId()) || (this
						.getPubmedId() != null
						&& castOther.getPubmedId() != null && this
						.getPubmedId().equals(castOther.getPubmedId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getId();
		result = 37 * result
				+ (getPubmedId() == null ? 0 : this.getPubmedId().hashCode());
		return result;
	}

}
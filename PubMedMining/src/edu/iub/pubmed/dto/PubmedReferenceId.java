package edu.iub.pubmed.dto;

// Generated Mar 21, 2014 5:53:07 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PubmedReferenceId generated by hbm2java
 */
@Embeddable
public class PubmedReferenceId implements java.io.Serializable {

	private String pubmedId;
	private String citedPubmedId;

	public PubmedReferenceId() {
	}

	public PubmedReferenceId(String pubmedId, String citedPubmedId) {
		this.pubmedId = pubmedId;
		this.citedPubmedId = citedPubmedId;
	}

	@Column(name = "pubmed_id", nullable = false, length = 45)
	public String getPubmedId() {
		return this.pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	@Column(name = "cited_pubmed_id", nullable = false, length = 45)
	public String getCitedPubmedId() {
		return this.citedPubmedId;
	}

	public void setCitedPubmedId(String citedPubmedId) {
		this.citedPubmedId = citedPubmedId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PubmedReferenceId))
			return false;
		PubmedReferenceId castOther = (PubmedReferenceId) other;

		return ((this.getPubmedId() == castOther.getPubmedId()) || (this
				.getPubmedId() != null && castOther.getPubmedId() != null && this
				.getPubmedId().equals(castOther.getPubmedId())))
				&& ((this.getCitedPubmedId() == castOther.getCitedPubmedId()) || (this
						.getCitedPubmedId() != null
						&& castOther.getCitedPubmedId() != null && this
						.getCitedPubmedId()
						.equals(castOther.getCitedPubmedId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPubmedId() == null ? 0 : this.getPubmedId().hashCode());
		result = 37
				* result
				+ (getCitedPubmedId() == null ? 0 : this.getCitedPubmedId()
						.hashCode());
		return result;
	}

}

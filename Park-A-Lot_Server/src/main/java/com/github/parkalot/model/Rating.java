package com.github.parkalot.model;

import java.time.LocalDateTime;

import org.lightcouch.Document;

public class Rating extends Document {

	private String ratingId;
	private int value;
	private LocalDateTime dateSubmitted;
	private long submittedByUserId;

	public Rating(String ratingId, int value, long submittedByUserId) {
		this.ratingId = ratingId;
		// Set the internal CouchDB _id as well
		this.setId(ratingId);
		this.value = value;
		this.dateSubmitted = LocalDateTime.now();
		this.submittedByUserId = submittedByUserId;
	}

	public String getRatingId() {
		return ratingId;
	}

	public void setRatingId(String ratingId) {
		this.ratingId = ratingId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public long getSubmittedByUserId() {
		return submittedByUserId;
	}

	public LocalDateTime getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(LocalDateTime dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public void setSubmittedByUserId(long submittedByUserId) {
		this.submittedByUserId = submittedByUserId;
	}

	@Override
	public String toString() {
		return String.format("ParkingLotId: %s Rating: %s SubmittedBy: %s @ %s", this.ratingId, this.value,
				this.submittedByUserId, this.dateSubmitted);
	}

}

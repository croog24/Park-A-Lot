package com.github.parkalot.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.lightcouch.Document;

public class Rating extends Document {

	private String ratingId;
	private int value;
	private String parkingLotId;
	private int hour;
	private DayOfWeek dayOfWeek;
	private long submittedByUserId;

	public Rating(String ratingId, int value, String parkingLotId, long submittedByUserId) {
		this.ratingId = ratingId;
		// Set the internal CouchDB _id as well
		this.setId(ratingId);
		this.value = value;
		this.parkingLotId = parkingLotId;
		LocalDateTime dt = LocalDateTime.now();
		this.hour = dt.getHour();
		this.dayOfWeek = dt.getDayOfWeek();
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
	
	public String getParkingLotId() {
		return parkingLotId;
	}

	public void setParkingLotId(String parkingLotId) {
		this.parkingLotId = parkingLotId;
	}

	public long getSubmittedByUserId() {
		return submittedByUserId;
	}

	public void setSubmittedByUserId(long submittedByUserId) {
		this.submittedByUserId = submittedByUserId;
	}
	
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@Override
	public String toString() {
		return String.format("ParkingLotId: %s RatingId: %s Value: %s SubmittedBy: %s @ %s %s", this.parkingLotId, this.ratingId, this.value,
				this.submittedByUserId, this.dayOfWeek, this.hour);
	}

}

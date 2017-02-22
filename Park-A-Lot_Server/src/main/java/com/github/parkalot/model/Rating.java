package com.github.parkalot.model;

import java.time.LocalDateTime;

public class Rating {
	
	private long parkingLotId;
	private int value;
	private LocalDateTime dateSubmitted;
	private long submittedByUserId;

	public Rating(long parkingLotId, int value, long submittedByUserId) {
		this.parkingLotId = parkingLotId;
		this.value = value;
		this.dateSubmitted = LocalDateTime.now();
		this.submittedByUserId = submittedByUserId;
	}

	public long getParkingLotId() {
		return parkingLotId;
	}

	public void setParkingLotId(long parkingLotId) {
		this.parkingLotId = parkingLotId;
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

	public void setSubmittedByUserId(long submittedByUserId) {
		this.submittedByUserId = submittedByUserId;
	}

	@Override
	public String toString() {
		return String.format("ParkingLotId: %s Rating: %s SubmittedBy: %s @ %s", this.parkingLotId, this.value,
				this.submittedByUserId, this.dateSubmitted);
	}

}

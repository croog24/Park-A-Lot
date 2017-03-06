package com.github.parkalot.model;

import java.util.ArrayList;

import org.lightcouch.Document;

public class ParkingLot extends Document {

	private String parkingLotId;
	private String name;
	private ArrayList<String> ratingIdList;

	public ParkingLot(String parkingLotId) {
		this.parkingLotId = parkingLotId;
		// Set the internal CouchDB _id as well
		this.setId(parkingLotId);
	}

	public String getParkingLotId() {
		return parkingLotId;
	}

	public void setParkingLotId(String parkingLotId) {
		this.parkingLotId = parkingLotId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getRatingList() {
		return ratingIdList;
	}

	public void setRatingList(ArrayList<String> ratingIdList) {
		this.ratingIdList = ratingIdList;
	}

	@Override
	public String toString() {
		return String.format("Parking Lot ID:%s Name:%s Ratings:%s", parkingLotId, name, ratingIdList);
	}

}

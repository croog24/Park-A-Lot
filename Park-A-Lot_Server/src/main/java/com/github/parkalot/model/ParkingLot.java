package com.github.parkalot.model;

import java.util.ArrayList;

import org.lightcouch.Document;

public class ParkingLot extends Document {

	private String parkingLotId;
	private String name;
	private ArrayList<Rating> ratingList;

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

	public ArrayList<Rating> getRatingList() {
		return ratingList;
	}

	public void setRatingList(ArrayList<Rating> ratingList) {
		this.ratingList = ratingList;
	}

	@Override
	public String toString() {
		return String.format("Parking Lot ID:%s Name:%s Ratings:%s", parkingLotId, name, ratingList);
	}

}

package com.github.parkalot.model;

import java.util.ArrayList;

public class ParkingLot {

	private String parkingLotId;
	private String name;
	private ArrayList<Rating> ratingList;

	// TODO: implement this into sequence
	public ParkingLot(String id) {
		this.parkingLotId= id;
	}
	public String getParkingLotId() {
		return parkingLotId;
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

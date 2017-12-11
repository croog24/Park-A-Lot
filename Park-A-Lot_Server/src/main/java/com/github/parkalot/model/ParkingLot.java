package com.github.parkalot.model;

import java.util.ArrayList;

import org.lightcouch.Document;

public class ParkingLot extends Document {

    private final String parkingLotId;
    private final String name;

    public ParkingLot(final String parkingLotId, final String name) {
        this.parkingLotId = parkingLotId;
        this.name = name;
        // Set the internal CouchDB _id as well
        this.setId(parkingLotId);
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Parking Lot ID:%s Name:%s", parkingLotId, name);
    }

}

package com.github.parkalot.model;

public class ParkingLot {

    private final String parkingLotId;
    private final String name;

    public ParkingLot(final String parkingLotId, final String name) {
        this.parkingLotId = parkingLotId;
        this.name = name;
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

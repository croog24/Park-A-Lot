package com.googleapis.maps.place.model;

import com.googleapis.maps.place.model.Northeast;
import com.googleapis.maps.place.model.Southwest;

public class Viewport {

    private Northeast northeast;
    private Southwest southwest;

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

}

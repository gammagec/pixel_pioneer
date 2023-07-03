package com.pixel_pioneer.ui;

import com.pixel_pioneer.util.PointI;

public class Light {
    private int diameter;
    private PointI location;
    private int flicker;

    public Light(PointI location, int diameter, int flicker) {
        this.location = location;
        this.diameter = diameter;
        this.flicker = flicker;
    }

    public int getFlicker() {
        return flicker;
    }

    public PointI getLocation() {
        return location;
    }

    public int getDiameter() {
        return diameter;
    }
}

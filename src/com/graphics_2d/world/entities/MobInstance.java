package com.graphics_2d.world.entities;

import com.graphics_2d.util.PointI;

public class MobInstance {
    private PointI location;
    private final int mobId;

    public MobInstance(int mobId, PointI location) {
        this.location = location;
        this.mobId = mobId;
    }

    public int getMobId() {
        return mobId;
    }

    public PointI getLocation() {
        return location;
    }

    public void setLocation(PointI dst) {
        this.location = dst;
    }
}

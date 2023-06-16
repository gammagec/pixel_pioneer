package com.graphics_2d.world;

public class LocationInfo {

    int tileId;
    ObjectInstance objectInstance;

    int biomeId;

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    public void setBiomeId(int biomeId) {
        this.biomeId = biomeId;
    }

    public int getBiomeId() {
        return biomeId;
    }

    public void setObjectInstance(ObjectInstance objectInstance) {
        this.objectInstance = objectInstance;
    }

    public ObjectInstance getObjectInstance() {
        return objectInstance;
    }

    public int getTileId() {
        return tileId;
    }
}

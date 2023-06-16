package com.graphics_2d.world;

public class ObjectInstance {
    private final int objectId;
    private int usesLeft = 0;
    private int count = 1;

    public ObjectInstance(int objectId, int uses) {
        this.objectId = objectId;
        this.usesLeft = uses;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setUses(int uses) {
        this.usesLeft = uses;
    }

    public boolean same(ObjectInstance objectInstance) {
        return objectId == objectInstance.objectId && usesLeft == objectInstance.usesLeft;
    }

    public int getUsesLeft() {
        return usesLeft;
    }

    public int getCount() {
        return count;
    }

    public void addInstances(int count) {
        this.count += count;
    }

    public void reduceCount(int i) {
        this.count -= i;
    }
}

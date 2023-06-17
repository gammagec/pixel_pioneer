package com.pixel_pioneer.world;

public class ObjectInstance {
    private final int objectId;
    private int usesLeft;
    private int count = 1;

    public ObjectInstance(int objectId, int uses) {
        this.objectId = objectId;
        this.usesLeft = uses;
    }

    public ObjectInstance newCopy() {
        ObjectInstance obj = new ObjectInstance(objectId, usesLeft);
        obj.setCount(count);
        return obj;
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

    public void setCount(int count) {
        this.count = count;
    }

    public void addInstances(int count) {
        this.count += count;
    }

    public void reduceCount(int i) {
        this.count -= i;
    }
}

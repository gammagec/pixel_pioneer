package com.graphics_2d.world;

public class GameObject {

    private boolean isBlocking;
    private final String name;
    private final ImageAsset imageAsset;
    private final int id;
    private int damage = 0;
    private static int nextId = 0;
    private boolean canPickup = false;

    private boolean canEat = false;

    public GameObject(String name, ImageAsset imageAsset) {
        this.imageAsset = imageAsset;
        this.isBlocking = false;
        this.name = name;
        this.id = nextId++;
    }

    public boolean isCanEat() {
        return canEat;
    }

    public void setCanEat(boolean canEat) {
        this.canEat = canEat;
    }

    public void setCanPickup(boolean canPickup) {
        this.canPickup = canPickup;
    }

    public boolean isCanPickup() {
        return this.canPickup;
    }

    public void setBlocking(boolean blocking) {
        this.isBlocking = blocking;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public ImageAsset getImageAsset() {
        return imageAsset;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public boolean isBlocking() {
        return isBlocking;
    }
}

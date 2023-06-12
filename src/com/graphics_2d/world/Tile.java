package com.graphics_2d.world;

public class Tile {

    private boolean isBlocking;
    private final ImageAsset imageAsset;
    private final String name;
    private final int id;
    private int damage = 0;
    private static int nextId = 0;
    private boolean swim = false;
    private ImageAsset swimAsset;

    Tile(String name, ImageAsset imageAsset) {
        this.isBlocking = false;
        this.imageAsset = imageAsset;
        this.name = name;
        this.id = nextId++;
    }

    public void setBlocking(boolean blocking) {
        this.isBlocking = blocking;
    }

    public void setSwim(boolean swim) { this.swim = swim; }

    public boolean isSwim() {
        return this.swim;
    }

    public void setSwimAsset(ImageAsset swimAsset) {
        this.swimAsset = swimAsset;
    }

    public ImageAsset getSwimAsset() {
        return swimAsset;
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

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public boolean isBlocking() {
        return isBlocking;
    }
}

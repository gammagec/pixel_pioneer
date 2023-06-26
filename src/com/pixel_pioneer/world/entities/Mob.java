package com.pixel_pioneer.world.entities;

import com.pixel_pioneer.actions.KeyboardHandler;
import com.pixel_pioneer.sound.SoundEngine;
import com.pixel_pioneer.util.PointI;
import com.pixel_pioneer.world.*;
import com.pixel_pioneer.world.biomes.Biome;

import java.util.*;

public class Mob {
    private final String name;
    private final Set<Integer> biomes;

    private final int visionRange;
    private final ImageAsset imageAsset;

    private boolean canSwim = false;
    private int damage = 0;

    private static final Random RANDOM = new Random();

    private final List<ObjectInstance> dropsByWeight = new ArrayList<>();
    private final int id;
    private static int nextId = 0;

    public static Map<Integer, Mob> MOBS_BY_ID = new HashMap<>();

    public Mob(String name, Set<Integer> biomes, int visionRange, ImageAsset imageAsset, Map<ObjectInstance, Integer> drops) {
        this.name = name;
        this.biomes = biomes;
        this.visionRange = visionRange;
        this.imageAsset = imageAsset;
        this.id = nextId++;
        MOBS_BY_ID.put(id, this);

        for (ObjectInstance drop : drops.keySet().toArray(new ObjectInstance[0])) {
            int weight = drops.get(drop);
            for (int i = 0; i < weight; i++) {
                dropsByWeight.add(drop);
            }
        }
    }

    public String getName() {
        return name;
    }

    public static Mob getRandomMob() {
        return (Mob) MOBS_BY_ID.values().toArray()[RANDOM.nextInt(MOBS_BY_ID.values().size())];
    }

    public int getId() {
        return id;
    }

    public ObjectInstance getDrop() {
        if (dropsByWeight.size() > 0) {
            ObjectInstance drop = dropsByWeight.get(RANDOM.nextInt(dropsByWeight.size()));
            if (drop != null) {
                return drop.newCopy();
            }
        }
        return null;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setCanSwim(boolean canSwim) {
        this.canSwim = canSwim;
    }

    public boolean isCanSwim() {
        return canSwim;
    }

    public Set<Integer> getBiomes() {
        return biomes;
    }

    public int getVisionRange() {
        return visionRange;
    }

    public ImageAsset getImageAsset() {
        return imageAsset;
    }

    public void update(World world, MobInstance mobInstance, SoundEngine soundEngine, KeyboardHandler keyboardHandler) {
        PointI dst = null;

        PointI pLoc = world.getPlayer().getLocation();

        PointI mLoc = mobInstance.getLocation();
        int xDist = pLoc.getX() - mLoc.getX();
        int yDist = pLoc.getY() - mLoc.getY();
        // Player Visible
        if (Math.abs(xDist) < visionRange && Math.abs(yDist) < visionRange) {
            if (Math.abs(xDist) > Math.abs(yDist)) {
                // Move x
                if (xDist > 0) {
                    dst = mLoc.delta(1, 0);
                } else {
                    dst = mLoc.delta(-1, 0);
                }
            } else {
                // Move y
                if (yDist > 0) {
                    dst = mLoc.delta(0, 1);
                } else {
                    dst = mLoc.delta(0, -1);
                }
            }
        } else {
            switch (RANDOM.nextInt(4)) {
                case 0 -> dst = mLoc.delta(1, 0); // right
                case 1 -> dst = mLoc.delta(-1, 0); // left
                case 2 -> dst = mLoc.delta(0, 1); // down
                case 3 -> dst = mLoc.delta(0, -1); // up
            }
        }
        if (!world.inBounds(dst)) {
            return;
        }
        Biome b = world.getBiomeAt(dst);
        if (!biomes.contains(b.getBiomeId())) {
            return;
        }
        Tile tile = world.getTileAt(dst);
        if (tile.isSwim() && !isCanSwim()) {
            return;
        }
        if (canMove(world, dst)) {
            mobInstance.setLocation(dst);
            Player player = world.getPlayer();
            if (pLoc.equals(dst) && !player.isFlying() && player.isAlive()) {
                int damage = getDamage();
                if (damage > 0) {
                    player.takeDamage(getDamage());
                    if (!player.isAlive()) {
                        keyboardHandler.setDeadMode();
                        soundEngine.playDeadSong();
                    } else {
                        soundEngine.playOw();
                    }
                    world.playerUpdated();
                    world.worldUpdated();
                }
            }
        }
    }

    public boolean canMove(World world, PointI loc) {
        return !world.getBlocking(loc.getX(), loc.getY());
    }
}

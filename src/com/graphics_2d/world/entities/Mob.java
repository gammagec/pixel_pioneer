package com.graphics_2d.world.entities;

import com.graphics_2d.util.PointI;
import com.graphics_2d.world.ImageAsset;
import com.graphics_2d.world.ImageAssets;
import com.graphics_2d.world.Tile;
import com.graphics_2d.world.World;
import com.graphics_2d.world.biomes.Biome;
import com.graphics_2d.world.biomes.Biomes;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Mob extends Entity {
    static private final Random RANDOM = new Random();
    private final World world;
    private final MobType type;

    public Mob(World world, MobType mobType) {
        this.world = world;
        this.type = mobType;
    }

    public static Integer[] getBiomesForMob(MobType type) {
        return switch (type) {
            case SNAKE -> new Integer[] {
                    Biomes.PLAINS.getBiomeId()
            };
            case LAVA_MONSTER -> new Integer[] {
                    Biomes.MOUNTAIN.getBiomeId()
            };
            case SEA_DRAGON -> new Integer[] {
                    Biomes.WATER.getBiomeId()
            };
        };
    }

    public static int getMobVisionRange(MobType type) {
        return switch (type) {
            case SNAKE -> 4;
            case LAVA_MONSTER, SEA_DRAGON -> 5;
        };
    }

    public static boolean getCanSwimForMob(MobType type) {
        return type == MobType.LAVA_MONSTER ||
                type == MobType.SEA_DRAGON;
    }

    public int getDamage() {
        return getDamageForMob(type);
    }

    public static int getDamageForMob(MobType type) {
        return switch (type) {
            case SNAKE -> 1;
            case SEA_DRAGON, LAVA_MONSTER -> 2;
        };
    }

    public boolean isCanSwim() {
        return  getCanSwimForMob(type);
    }

    private static  ImageAsset imageAssetForType(MobType type) {
        return switch (type) {
            case SNAKE -> ImageAssets.SNAKE;
            case LAVA_MONSTER -> ImageAssets.LAVA_MONSTER;
            case SEA_DRAGON -> ImageAssets.SEA_DRAGON;
        };
    }

    public ImageAsset getImageAsset() {
        return imageAssetForType(type);
    }

    public void update() {
        Set<Integer> biomesSet = Set.of(getBiomesForMob(type));
        PointI loc = getLocation();
        PointI dst = null;

        PointI pLoc = world.getPlayer().getLocation();
        int range = getMobVisionRange(type);

        PointI mLoc = getLocation();
        int xDist = pLoc.getX() - mLoc.getX();
        int yDist = pLoc.getY() - mLoc.getY();
        // Player Visible
        if (Math.abs(xDist) < range && Math.abs(yDist) < range) {
            if (Math.abs(xDist) > Math.abs(yDist)) {
                // Move x
                if (xDist > 0) {
                    dst = loc.delta(1, 0);
                } else {
                    dst = loc.delta(-1, 0);
                }
            } else {
                // Move y
                if (yDist > 0) {
                    dst = loc.delta(0, 1);
                } else {
                    dst = loc.delta(0, -1);
                }
            }
        } else {
            switch (RANDOM.nextInt(4)) {
                case 0 -> dst = loc.delta(1, 0); // right
                case 1 -> dst = loc.delta(-1, 0); // left
                case 2 -> dst = loc.delta(0, 1); // down
                case 3 -> dst = loc.delta(0, -1); // up
            }
        }
        if (!world.inBounds(dst.getX(), dst.getY())) {
            return;
        }
        Biome b = world.getBiomeAt(dst.getX(), dst.getY());
        if (!biomesSet.contains(b.getBiomeId())) {
            return;
        }
        Tile tile = world.getTileAt(dst.getX(), dst.getY());
        if (tile.isSwim() && !isCanSwim()) {
            return;
        }
        if (canMove(dst)) {
            setLocation(dst);
            if (Objects.equals(dst.getX(), pLoc.getX()) && Objects.equals(dst.getY(), pLoc.getY())) {
                world.getPlayer().takeDamage(getDamage());
                world.playerUpdated();
                world.worldUpdated();
            }
        }
    }

    public boolean canMove(PointI loc) {
        return !world.getBlocking(loc.getX(), loc.getY());
    }
}
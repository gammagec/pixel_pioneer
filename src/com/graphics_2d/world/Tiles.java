package com.graphics_2d.world;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static Tile WATER = new Tile("water", ImageAssets.WATER) {{
        setSwim(true);
        setSwimAsset(ImageAssets.IN_WATER);
    }};
    public static Tile STONE = new Tile("stone", ImageAssets.STONE);
    public static Tile GRASS = new Tile("grass", ImageAssets.GRASS);
    public static Tile FOREST_GRASS = new Tile("forest grass", ImageAssets.FOREST_GRASS);
    public static Tile DIRT = new Tile("dirt", ImageAssets.DIRT);
    public static Tile LAVA = new Tile("lava", ImageAssets.LAVA) {{
        setDamage(1);
        setSwim(true);
        setSwimAsset(ImageAssets.IN_LAVA);
    }};
    public static Tile SAND = new Tile("sand", ImageAssets.SAND);
    public static Tile SNOW = new Tile("snow", ImageAssets.SNOW);
    public static Tile ICE = new Tile("ice", ImageAssets.ICE);

    public static Tile[] ALL_TILES = new Tile[] {
            WATER,
            STONE,
            GRASS,
            FOREST_GRASS,
            DIRT,
            LAVA,
            SAND,
            SNOW,
            ICE
    };

    public static Map<Integer, Tile> TILES_BY_ID = new HashMap<>() {{
        for (Tile t : ALL_TILES) {
            put(t.getId(), t);
        }
    }};
}

package com.pixel_pioneer.world;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static Tile WATER = new Tile("water", ImageAssets.WATER) {{
        setSwim(true);
        setSwimAsset(ImageAssets.IN_WATER);
    }};
    public static Tile STONE = new Tile("stone", ImageAssets.STONE);
    public static Tile GRASS = new Tile("grass", ImageAssets.GRASS) {{
        addVariant(ImageAssets.GRASS_V1);
        addVariant(ImageAssets.GRASS_V2);
        addVariant(ImageAssets.GRASS_V3);
        addVariant(ImageAssets.GRASS_V4);
    }};
    public static Tile FOREST_GRASS = new Tile("forest grass", ImageAssets.FOREST_GRASS) {{
        addVariant(ImageAssets.FOREST_GRASS_V1);
        addVariant(ImageAssets.FOREST_GRASS_V2);
    }};
    public static Tile DIRT = new Tile("dirt", ImageAssets.DIRT);
    public static Tile LAVA = new Tile("lava", ImageAssets.LAVA) {{
        setDamage(1);
        setSwim(true);
        setSwimAsset(ImageAssets.IN_LAVA);
    }};
    public static Tile SAND = new Tile("sand", ImageAssets.SAND);
    public static Tile SNOW = new Tile("snow", ImageAssets.SNOW);
    public static Tile ICE = new Tile("ice", ImageAssets.ICE);

    // 4096 combinations
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

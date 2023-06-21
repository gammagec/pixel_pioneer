package com.pixel_pioneer.world;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static Tile WATER = new Tile("water", ImageAssets.WATER, /* growObjects= */ new HashMap<>()) {{
        setSwim(true);
        setSwimAsset(ImageAssets.IN_WATER);
    }};
    public static Tile STONE = new Tile("stone", ImageAssets.STONE, /* growObjects= */ new HashMap<>());
    public static Tile GRASS = new Tile("grass", ImageAssets.GRASS, new HashMap<>() {{
        // Grow objects
        put(GameObjects.NO_OBJECT, 999);
        put(GameObjects.BERRY_BUSH, 1);
    }}) {{
        addVariant(ImageAssets.GRASS_V1);
        addVariant(ImageAssets.GRASS_V2);
        addVariant(ImageAssets.GRASS_V3);
        addVariant(ImageAssets.GRASS_V4);
    }};
    public static Tile FOREST_GRASS = new Tile("forest grass", ImageAssets.FOREST_GRASS, new HashMap<>() {{
        // Grow objects
        put(GameObjects.NO_OBJECT, 198);
        put(GameObjects.TREE, 1);
        put(GameObjects.TREE_V1, 2);
    }}) {{
        addVariant(ImageAssets.FOREST_GRASS_V1);
        addVariant(ImageAssets.FOREST_GRASS_V2);
    }};
    public static Tile DIRT = new Tile("dirt", ImageAssets.DIRT, /* growObjects= */ new HashMap<>());
    public static Tile LAVA = new Tile("lava", ImageAssets.LAVA, /* growObjects= */ new HashMap<>()) {{
        setDamage(1);
        setSwim(true);
        setSwimAsset(ImageAssets.IN_LAVA);
    }};
    public static Tile SAND = new Tile("sand", ImageAssets.SAND, new HashMap<>() {{
        // Grow Objects
        put(GameObjects.NO_OBJECT, 299);
        put(GameObjects.CACTUS, 1);
    }});
    public static Tile SNOW = new Tile("snow", ImageAssets.SNOW, new HashMap<>() {{
        put(GameObjects.NO_OBJECT, 199);
        put(GameObjects.SPRUCE_TREE, 1);
    }}) {{
        addVariant(ImageAssets.SNOW_V1);
        addVariant(ImageAssets.SNOW_V2);
        addVariant(ImageAssets.SNOW_V3);
    }};
    public static Tile ICE = new Tile("ice", ImageAssets.ICE, /* growObjects= */ new HashMap<>());

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

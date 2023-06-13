package com.graphics_2d.world.biomes;

import com.graphics_2d.world.GameObjects;
import com.graphics_2d.world.Tiles;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Biomes {

    public static Biome WATER = new Biome("ocean", new HashMap<>() {{
        put(Tiles.WATER, 1);
    }}, new HashMap<>(), new Color(0, 0, 128));

    public static Biome PLAINS = new Biome("plains", new HashMap<>() {{
        put(Tiles.GRASS, 97);
        put(Tiles.DIRT, 2);
        put(Tiles.STONE, 1);
    }}, new HashMap<>() {{
        put(GameObjects.NO_OBJECT, 1969);
        put(GameObjects.BREAD, 10);
        put(GameObjects.PORK_CHOP, 10);
        put(GameObjects.BEEF, 10);
        put(GameObjects.IRON, 20);
        put(GameObjects.GOLD, 10);
        put(GameObjects.DIAMOND, 1);
    }}, Color.GREEN);

    public static Biome FOREST = new Biome("forest", new HashMap<>() {{
        put(Tiles.FOREST_GRASS, 195);
        put(Tiles.DIRT, 5);
    }}, new HashMap<>() {{
        put(GameObjects.NO_OBJECT, 1469);
        put(GameObjects.TREE, 200);
        put(GameObjects.IRON, 20);
        put(GameObjects.BREAD, 10);
        put(GameObjects.BEEF, 10);
        put(GameObjects.PORK_CHOP, 10);
        put(GameObjects.GOLD, 10);
        put(GameObjects.DIAMOND, 1);
    }}, new Color(0, 128, 0));

    public static Biome MOUNTAIN = new Biome("mountain", new HashMap<>() {{
        put(Tiles.STONE, 1);
    }}, new HashMap<>() {{
        put(GameObjects.NO_OBJECT, 1969);
        put(GameObjects.IRON, 20);
        put(GameObjects.GOLD, 10);
        put(GameObjects.DIAMOND, 1);
    }}, new Color(80, 80, 80));

    public static Biome DESERT = new Biome("desert", new HashMap<>() {{
        put(Tiles.SAND, 1);
    }}, new HashMap<>() {{
        put(GameObjects.NO_OBJECT, 1869);
        put(GameObjects.CACTUS, 100);
        put(GameObjects.IRON, 20);
        put(GameObjects.GOLD, 10);
        put(GameObjects.DIAMOND, 1);
    }}, new Color(255, 255, 0));
    public static Biome SNOW = new Biome("snow biome", new HashMap<>() {{
        put(Tiles.SNOW, 4);
        put(Tiles.ICE, 1);
    }}, new HashMap<>() {{
        put(GameObjects.NO_OBJECT, 1869);
        put(GameObjects.SPRUCE_TREE, 100);
    }}, new Color(255, 255, 255));

    public static Biome ICE = new Biome("ice biome", new HashMap<>() {{
        put(Tiles.ICE, 1);
    }}, new HashMap<>() {{
        put(GameObjects.NO_OBJECT, 1869);
    }}, new Color(128, 255, 255));

    public static Biome[] ALL_BIOMES = new Biome[] {
        WATER,
        PLAINS,
        FOREST,
        MOUNTAIN,
        DESERT,
        SNOW,
        ICE
    };

    public static Map<Integer, Biome> BIOMES_BY_ID = new HashMap<>() {{
        for (Biome b : ALL_BIOMES) {
            put(b.getBiomeId(), b);
        }
    }};
}

package com.pixel_pioneer.world.entities;

import com.pixel_pioneer.world.GameObjects;
import com.pixel_pioneer.world.ImageAssets;
import com.pixel_pioneer.world.ObjectInstance;
import com.pixel_pioneer.world.biomes.Biomes;

import java.util.HashMap;
import java.util.HashSet;

public class Mobs {

    public static void initialize() {
        // Just getting our mobs created.
    }

    public static final Mob SNAKE = new Mob("snake", new HashSet<>() {{
        add(Biomes.PLAINS.getBiomeId());
    }}, 4, ImageAssets.SNAKE, new HashMap<>() {{
        // Drops
    }}) {{
        setDamage(1);
    }};

    public static final Mob COW = new Mob("cow", new HashSet<>() {{
        add(Biomes.PLAINS.getBiomeId());
    }}, 0, ImageAssets.COW, new HashMap<>() {{
        // Drops
        put(null, 50);
        put(new ObjectInstance(GameObjects.RAW_BEEF.getId(), GameObjects.RAW_BEEF.getUses()), 50);
    }});

    public static final Mob CHICKEN = new Mob("chicken", new HashSet<>() {{
        add(Biomes.PLAINS.getBiomeId());
    }}, 0, ImageAssets.CHICKEN, new HashMap<>() {{
        // Drops
        put(null, 50);
        put(GameObjects.RAW_CHICKEN.getDefaultInstance(), 25);
        put(GameObjects.FEATHER.getDefaultInstance(), 25);
    }});

    public static final Mob LAVA_MONSTER = new Mob("lava monster", new HashSet<>() {{
        add(Biomes.MOUNTAIN.getBiomeId());
    }}, 7, ImageAssets.LAVA_MONSTER, /* Drops= */ new HashMap<>()) {{
        setCanSwim(true);
        setDamage(2);
        setLight(500);
    }};

    public static final Mob SEA_DRAGON = new Mob("sea dragon", new HashSet<>() {{
        add(Biomes.WATER.getBiomeId());
    }}, 7, ImageAssets.SEA_DRAGON, /* Drops= */ new HashMap<>()) {{
        setCanSwim(true);
        setDamage(2);
    }};
}

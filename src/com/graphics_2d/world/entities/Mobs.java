package com.graphics_2d.world.entities;

import com.graphics_2d.world.GameObjects;
import com.graphics_2d.world.ImageAssets;
import com.graphics_2d.world.ObjectInstance;
import com.graphics_2d.world.biomes.Biomes;

import java.util.HashSet;

public class Mobs {

    public static void initialize() {
        // Just getting our mobs created.
    }

    public static final Mob SNAKE = new Mob("snake", new HashSet<>() {{
        add(Biomes.PLAINS.getBiomeId());
    }}, 4, ImageAssets.SNAKE) {{
        setDamage(1);
    }};

    public static final Mob COW = new Mob("cow", new HashSet<>() {{
        add(Biomes.PLAINS.getBiomeId());
    }}, 0, ImageAssets.COW) {{
        setDrop(new ObjectInstance(GameObjects.BEEF.getId(), GameObjects.BEEF.getUses()));
    }};

    public static final Mob LAVA_MONSTER = new Mob("lava monster", new HashSet<>() {{
        add(Biomes.MOUNTAIN.getBiomeId());
    }}, 7, ImageAssets.LAVA_MONSTER) {{
        setCanSwim(true);
        setDamage(2);
    }};

    public static final Mob SEA_DRAGON = new Mob("sea dragon", new HashSet<>() {{
        add(Biomes.WATER.getBiomeId());
    }}, 7, ImageAssets.SEA_DRAGON) {{
        setCanSwim(true);
        setDamage(2);
    }};
}

package com.graphics_2d.world;

import java.util.HashMap;

public class Recipes {

    public static final Recipe BASIC_AXE = new Recipe(new HashMap<>() {{
        put(GameObjects.TWIG.getId(), 1);
        put(GameObjects.ROCK.getId(), 1);
    }}, GameObjects.BASIC_AXE.getId(), 1);
    public static final Recipe BASIC_PICKAXE = new Recipe(new HashMap<>() {{
        put(GameObjects.TWIG.getId(), 1);
        put(GameObjects.ROCK.getId(), 1);
    }}, GameObjects.BASIC_PICK_AXE.getId(), 1);

    public static final Recipe BRICK = new Recipe(new HashMap<>() {{
        put(GameObjects.ROCK.getId(), 2);
    }}, GameObjects.BRICK.getId(), 1);

    public static final Recipe BASIC_SWORD = new Recipe(new HashMap<>() {{
        put(GameObjects.ROCK.getId(), 3);
        put(GameObjects.TWIG.getId(), 2);
    }}, GameObjects.BASIC_SWORD.getId(), 1);

    public static final Recipe[] ALL_RECIPES = {
            BASIC_AXE,BASIC_PICKAXE, BRICK, BASIC_SWORD
    };
}

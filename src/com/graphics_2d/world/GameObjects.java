package com.graphics_2d.world;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GameObjects {
    public static GameObject NO_OBJECT = new GameObject("nothing", null);
    public static GameObject SELF = new GameObject("self", null);

    public static GameObject CACTUS = new GameObject("cactus", ImageAssets.CACTUS) {{
        setDamage(1);
    }};

    public static GameObject IRON = new GameObject("iron", ImageAssets.IRON) {{
        setCanPickup(true);
    }};
    public static GameObject GOLD = new GameObject("gold", ImageAssets.GOLD) {{
        setCanPickup(true);
    }};
    public static GameObject DIAMOND = new GameObject("diamond", ImageAssets.DIAMOND) {{
        setCanPickup(true);
    }};

    public static GameObject BEEF = new GameObject("beef", ImageAssets.BEEF) {{
        setCanPickup(true);
        setCanEat(true);
    }};
    public static GameObject PORK_CHOP = new GameObject("pork chop", ImageAssets.PORK_CHOP) {{
        setCanPickup(true);
        setCanEat(true);
    }};
    public static GameObject BREAD = new GameObject("bread", ImageAssets.BREAD) {{
        setCanPickup(true);
        setCanEat(true);
    }};

    public static GameObject TWIG = new GameObject("twig", ImageAssets.TWIG) {{
        setCanPickup(true);
    }};

    public static GameObject ROCK = new GameObject("rock", ImageAssets.ROCK) {{
        setCanPickup(true);
    }};

    public static GameObject BASIC_AXE = new GameObject("axe", ImageAssets.AXE) {{
        setCanPickup(true);
        setCanUse(true);
    }};
    public static GameObject BASIC_PICK_AXE = new GameObject("Pickaxe", ImageAssets.PICKAXE) {{
        setCanPickup(true);
        setCanUse(true);
    }};

    public static GameObject BOULDER = new GameObject("boulder", ImageAssets.BOULDER) {{
        setBlocking(true);
        addUseEffect(new UseEffect(new HashSet<>() {{
            add(GameObjects.BASIC_PICK_AXE.getId());
        }}, new HashMap<>() {{
            put(GameObjects.ROCK.getId(), 2);
        }}, 1));
    }};


    public static GameObject TREE = new GameObject("tree", ImageAssets.TREE) {{
        setBlocking(true);
        addUseEffect(new UseEffect(new HashSet<>() {{
            add(GameObjects.BASIC_AXE.getId());
        }}, new HashMap<>() {{
            put(GameObjects.TWIG.getId(), 2);
        }}, 1));
    }};

    public static GameObject SPRUCE_TREE = new GameObject("Spruce Tree", ImageAssets.SPRUCE_TREE) {{
        setBlocking(true);
        addUseEffect(new UseEffect(new HashSet<>() {{
            add(GameObjects.BASIC_AXE.getId());
        }}, new HashMap<>() {{
            put(GameObjects.TWIG.getId(), 1);
        }}, 1));
    }};

    public static GameObject BRICK = new GameObject("brick", ImageAssets.BRICK) {{
        setBlocking(true);
        setCanUse(true);
        setCanBuild(true);
        addUseEffect(new UseEffect(new HashSet<>() {{
            add(GameObjects.BASIC_PICK_AXE.getId());
        }}, new HashMap<>() {{
            put(GameObjects.SELF.getId(), 1);
        }}, 1));
    }};

    public static GameObject[] ALL_OBJECTS = new GameObject[] {
            TREE,
            CACTUS,
            SPRUCE_TREE,
            IRON,
            GOLD,
            DIAMOND,
            BEEF,
            PORK_CHOP,
            BREAD,
            TWIG, ROCK, BASIC_AXE, BASIC_PICK_AXE,
            BOULDER, BRICK
    };

    public static Map<Integer, GameObject> OBJECTS_BY_ID = new HashMap<>() {{
       for (GameObject obj : ALL_OBJECTS) {
           put(obj.getId(), obj);
       }
    }};
}

package com.graphics_2d.world;

import java.util.HashMap;
import java.util.Map;

public class GameObjects {
    public static GameObject TREE = new GameObject("tree", ImageAssets.TREE) {{
        setBlocking(true);
    }};
    public static GameObject CACTUS = new GameObject("cactus", ImageAssets.CACTUS) {{
        setDamage(1);
    }};
    public static GameObject SPRUCE_TREE = new GameObject("Spruce Tree", ImageAssets.SPRUCE_TREE) {{
        setBlocking(true);
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
    };

    public static Map<Integer, GameObject> OBJECTS_BY_ID = new HashMap<>() {{
       for (GameObject obj : ALL_OBJECTS) {
           put(obj.getId(), obj);
       }
    }};

    public static GameObject NO_OBJECT = new GameObject("nothing", null);
}

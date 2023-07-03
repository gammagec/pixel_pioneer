package com.pixel_pioneer.world;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

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

    public static GameObject BASIC_SWORD = new GameObject("sword", ImageAssets.BASIC_SWORD) {{
        setCanPickup(true);
        setCanUse(true);
    }};

    public static GameObject BOULDER = new GameObject("boulder", ImageAssets.BOULDER) {{
        setBlocking(true);
        setUses(3);
        setAssetAtUse(2, ImageAssets.BOULDER_2);
        setAssetAtUse(1, ImageAssets.BOULDER_1);
        addUseEffect(new UseEffect(new HashSet<>() {{
            add(GameObjects.BASIC_PICK_AXE.getId());
        }}, new HashMap<>() {{
            put(GameObjects.ROCK.getId(), 2);
        }}, 1));
    }};


    public static GameObject TREE = new GameObject("tree", ImageAssets.TREE) {{
        setBlocking(true);
        setUses(2);
        setAssetAtUse(1, ImageAssets.TREE_1);
        addUseEffect(new UseEffect(new HashSet<>() {{
            add(GameObjects.BASIC_AXE.getId());
        }}, new HashMap<>() {{
            put(GameObjects.TWIG.getId(), 2);
        }}, 1));
    }};

    public static GameObject TREE_V1 = new GameObject("tree_v1", ImageAssets.TREE_V1) {{
        setBlocking(true);
        setUses(2);
        setAssetAtUse(1, ImageAssets.TREE_V1_1);
        addUseEffect(new UseEffect(new HashSet<>() {{
            add(GameObjects.BASIC_AXE.getId());
        }}, new HashMap<>() {{
            put(GameObjects.TWIG.getId(), 2);
        }}, 1));
    }};

    public static GameObject SPRUCE_TREE = new GameObject("Spruce Tree", ImageAssets.SPRUCE_TREE) {{
        setBlocking(true);
        setUses(2);
        setAssetAtUse(1, ImageAssets.SPRUCE_TREE_1);
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
        setUses(1);
        addUseEffect(new UseEffect(new HashSet<>() {{
            add(GameObjects.BASIC_PICK_AXE.getId());
        }}, new HashMap<>() {{
            put(GameObjects.SELF.getId(), 1);
        }}, 1));
    }};

    public static GameObject BERRY = new GameObject("berry", ImageAssets.BERRY) {{
        setCanPickup(true);
        setCanEat(true);
    }};

    public static GameObject BERRY_BUSH = new GameObject("berry bush", ImageAssets.BERRY_BUSH) {{
        setUses(1);
        setUseOnWalk(BERRY.getId(), 1, 1);
        setAssetAtUse(0, ImageAssets.BERRY_BUSH_0);
    }};

    public static GameObject SANDSTONE = new GameObject("sandstone", ImageAssets.SANDSTONE) {{
        setCanPickup(true);
    }};

    public static GameObject SANDSTONE_WALL = new GameObject("sandstone wall", ImageAssets.SANDSTONE_WALL) {{
        setCanBuild(true);
        setCanUse(true);
        setBlocking(true);
        setUses(1);
        addUseEffect(new UseEffect(new HashSet<>() {{
            add(GameObjects.BASIC_PICK_AXE.getId());
        }}, new HashMap<>() {{
            put(GameObjects.SELF.getId(), 1);
        }}, 1));
    }};

    public static GameObject SAND_BOULDER = new GameObject("sand boulder", ImageAssets.SAND_BOULDER) {{
        setUses(3);
        setAssetAtUse(2, ImageAssets.SAND_BOULDER_2);
        setAssetAtUse(1, ImageAssets.SAND_BOULDER_1);
        setBlocking(true);
        addUseEffect(new UseEffect(new HashSet<>() {{
            add(GameObjects.BASIC_PICK_AXE.getId());
        }}, new HashMap<>() {{
            put(GameObjects.SANDSTONE.getId(), 2);
        }}, 1));
    }};

    public static GameObject RAW_BEEF = new GameObject("raw beef", ImageAssets.RAW_BEEF) {{
        setCanPickup(true);
        setCanEat(true);
    }};

    public static GameObject RAW_CHICKEN = new GameObject("raw chicken", ImageAssets.RAW_CHICKEN) {{
        setCanPickup(true);
        setCanEat(true);
    }};

    public static GameObject CAMPFIRE = new GameObject("campfire", ImageAssets.CAMP_FIRE) {{
        setCanBuild(true);
        setCanUse(true);
        setBlocking(false);
        setDamage(1);
        addAnimationFrame(ImageAssets.CAMP_FIRE_2);
        setLight(1200);
        setLightFlicker(100);
    }};

    public static GameObject FEATHER = new GameObject("feather", ImageAssets.FEATHER) {{
        setCanPickup(true);
    }};

    public static void initialize() {
    }
}

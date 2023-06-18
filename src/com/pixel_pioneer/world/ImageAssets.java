package com.pixel_pioneer.world;

import java.io.IOException;

import static java.lang.System.exit;

public class ImageAssets {

    public static ImageAsset GUY = new ImageAsset("images/guy3.png", AssetType.OBJECT_ASSET);
    public static ImageAsset DEAD = new ImageAsset("images/dead.png", AssetType.OBJECT_ASSET);
    public static ImageAsset STONE = new ImageAsset("images/stone2.png", AssetType.TILE_ASSET);
    public static ImageAsset GRASS = new ImageAsset("images/grass3.png", AssetType.TILE_ASSET);
    public static ImageAsset GRASS_V1 = new ImageAsset("images/grass3_v1.png", AssetType.TILE_ASSET);
    public static ImageAsset GRASS_V2 = new ImageAsset("images/grass3_v2.png", AssetType.TILE_ASSET);
    public static ImageAsset FOREST_GRASS = new ImageAsset("images/forest_grass.png", AssetType.TILE_ASSET);
    public static ImageAsset WATER = new ImageAsset("images/Water.png", AssetType.TILE_ASSET);
    public static ImageAsset DIRT = new ImageAsset("images/Dirt.png", AssetType.TILE_ASSET);
    public static ImageAsset LAVA = new ImageAsset("images/Lava.png", AssetType.TILE_ASSET);
    public static ImageAsset SAND = new ImageAsset("images/Sand.png", AssetType.TILE_ASSET);
    public static ImageAsset TREE = new ImageAsset("images/tree1.png", AssetType.OBJECT_ASSET);
    public static ImageAsset TREE_1 = new ImageAsset("images/tree1_1.png", AssetType.OBJECT_ASSET);
    public static ImageAsset CACTUS = new ImageAsset("images/cactus.png", AssetType.OBJECT_ASSET);
    public static ImageAsset SNOW = new ImageAsset("images/snow2.png", AssetType.TILE_ASSET);
    public static ImageAsset ICE = new ImageAsset("images/ice.png", AssetType.TILE_ASSET);
    public static ImageAsset SPRUCE_TREE = new ImageAsset("images/Spruce Tree.png", AssetType.OBJECT_ASSET);

    public static ImageAsset SPRUCE_TREE_1 = new ImageAsset("images/spruce_1.png", AssetType.OBJECT_ASSET);
    public static ImageAsset IRON = new ImageAsset("images/iron_ore.png", AssetType.OBJECT_ASSET);
    public static ImageAsset GOLD = new ImageAsset("images/Gold.png", AssetType.OBJECT_ASSET);
    public static ImageAsset DIAMOND = new ImageAsset("images/diamond.png", AssetType.OBJECT_ASSET);

    public static ImageAsset IN_WATER = new ImageAsset("images/in_water.png", AssetType.OBJECT_ASSET);

    public static ImageAsset IN_LAVA = new ImageAsset("images/in_lava.png", AssetType.OBJECT_ASSET);

    public static ImageAsset LAVA_MONSTER = new ImageAsset("images/Lava Monster.png", AssetType.OBJECT_ASSET);
    public static ImageAsset PORK_CHOP = new ImageAsset("images/Porkchop.png", AssetType.OBJECT_ASSET);
    public static ImageAsset SNAKE = new ImageAsset("images/snake2.png", AssetType.OBJECT_ASSET);
    public static ImageAsset SEA_DRAGON = new ImageAsset("images/Sea Dragon.png", AssetType.OBJECT_ASSET);
    public static ImageAsset BREAD = new ImageAsset("images/Bread.png", AssetType.OBJECT_ASSET);
    public static ImageAsset BEEF = new ImageAsset("images/Beef.png", AssetType.OBJECT_ASSET);
    public static ImageAsset GUY_FLY = new ImageAsset("images/guy_fly.png", AssetType.OBJECT_ASSET);
    public static ImageAsset TWIG = new ImageAsset("images/Twig.png", AssetType.OBJECT_ASSET);
    public static ImageAsset ROCK = new ImageAsset("images/rock.png", AssetType.OBJECT_ASSET);
    public static ImageAsset AXE = new ImageAsset("images/axe.png", AssetType.OBJECT_ASSET);
    public static ImageAsset CHECK = new ImageAsset("images/check.png", AssetType.OBJECT_ASSET);
    public static ImageAsset X = new ImageAsset("images/x.png", AssetType.OBJECT_ASSET);
    public static ImageAsset PICKAXE = new ImageAsset("images/Pickaxe.png", AssetType.OBJECT_ASSET);
    public static ImageAsset BOULDER = new ImageAsset("images/boulder3.png", AssetType.OBJECT_ASSET);
    public static ImageAsset BOULDER_1 = new ImageAsset("images/boulder3_1.png", AssetType.OBJECT_ASSET);
    public static ImageAsset BOULDER_2 = new ImageAsset("images/boulder3_2.png", AssetType.OBJECT_ASSET);
    public static ImageAsset COW = new ImageAsset("images/cow.png", AssetType.OBJECT_ASSET);
    public static ImageAsset BRICK = new ImageAsset("images/brick.png", AssetType.OBJECT_ASSET);
    public static ImageAsset BASIC_SWORD = new ImageAsset("images/sword.png", AssetType.OBJECT_ASSET);
    public static ImageAsset BERRY = new ImageAsset("images/Berry.png", AssetType.OBJECT_ASSET);
    public static ImageAsset BERRY_BUSH = new ImageAsset("images/Berry_Bush.png", AssetType.OBJECT_ASSET);
    public static ImageAsset BERRY_BUSH_0 = new ImageAsset("images/berry_bush_no_berries.png", AssetType.OBJECT_ASSET);
    public static ImageAsset CAMP_FIRE = new ImageAsset("images/Campfire.png", AssetType.OBJECT_ASSET);
    public static ImageAsset CHEST = new ImageAsset("images/Chest.png", AssetType.OBJECT_ASSET);
    public static ImageAsset CHICKEN = new ImageAsset("images/chicken2.png", AssetType.OBJECT_ASSET);
    public static ImageAsset DOOR = new ImageAsset("images/Door.png", AssetType.OBJECT_ASSET);
    public static ImageAsset EGG = new ImageAsset("images/Egg.png", AssetType.OBJECT_ASSET);
    public static ImageAsset FARM_LAND = new ImageAsset("images/Farm_land.png", AssetType.OBJECT_ASSET);
    public static ImageAsset FEATHER = new ImageAsset("images/Feather.png", AssetType.OBJECT_ASSET);
    public static ImageAsset HOE = new ImageAsset("images/Hoe.png", AssetType.OBJECT_ASSET);
    public static ImageAsset PLANKS = new ImageAsset("images/Planks.png", AssetType.OBJECT_ASSET);
    public static ImageAsset RAW_BEEF = new ImageAsset("images/Raw_Beef.png", AssetType.OBJECT_ASSET);
    public static ImageAsset RAW_CHICKEN = new ImageAsset("images/Raw_Chicken.png", AssetType.OBJECT_ASSET);
    public static ImageAsset RAW_PORK = new ImageAsset("images/Raw_Pork.png", AssetType.OBJECT_ASSET);
    public static ImageAsset SAND_BOULDER = new ImageAsset("images/Sand_Boulder.png", AssetType.OBJECT_ASSET);
    public static ImageAsset SAND_BOULDER_2 = new ImageAsset("images/sand_boulder_2.png", AssetType.OBJECT_ASSET);
    public static ImageAsset SAND_BOULDER_1 = new ImageAsset("images/sand_boulder_1.png", AssetType.OBJECT_ASSET);
    public static ImageAsset SANDSTONE = new ImageAsset("images/sandstone.png", AssetType.OBJECT_ASSET);
    public static ImageAsset SANDSTONE_WALL = new ImageAsset("images/Sandstone_Wall.png", AssetType.OBJECT_ASSET);
    public static ImageAsset SUGARCANE = new ImageAsset("images/Sugarcane.png", AssetType.OBJECT_ASSET);

    public static void initialize() {
        for (ImageAsset imageAsset : ImageAsset.ASSETS_BY_ID.values()) {
            try {
                imageAsset.initialize();
            } catch (IOException e) {
                System.out.println("Couldn't load " + imageAsset.getFileName());
                exit(1);
            }
        }
    }
}

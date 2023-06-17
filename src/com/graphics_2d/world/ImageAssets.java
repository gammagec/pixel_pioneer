package com.graphics_2d.world;

import java.io.IOException;

import static java.lang.System.exit;

public class ImageAssets {

    public static ImageAsset GUY = new ImageAsset("images/guy.png");
    public static ImageAsset DEAD = new ImageAsset("images/dead.png");
    public static ImageAsset STONE = new ImageAsset("images/stone.png");
    public static ImageAsset GRASS = new ImageAsset("images/grass.png");
    public static ImageAsset FOREST_GRASS = new ImageAsset("images/forest_grass.png");
    public static ImageAsset WATER = new ImageAsset("images/Water.png");
    public static ImageAsset DIRT = new ImageAsset("images/Dirt.png");
    public static ImageAsset LAVA = new ImageAsset("images/Lava.png");
    public static ImageAsset SAND = new ImageAsset("images/Sand.png");
    public static ImageAsset TREE = new ImageAsset("images/tree1.png");
    public static ImageAsset TREE_1 = new ImageAsset("images/tree1_1.png");
    public static ImageAsset CACTUS = new ImageAsset("images/cactus.png");
    public static ImageAsset SNOW = new ImageAsset("images/snow.png");
    public static ImageAsset ICE = new ImageAsset("images/ice.png");
    public static ImageAsset SPRUCE_TREE = new ImageAsset("images/Spruce Tree.png");

    public static ImageAsset SPRUCE_TREE_1 = new ImageAsset("images/spruce_1.png");
    public static ImageAsset IRON = new ImageAsset("images/iron.png");
    public static ImageAsset GOLD = new ImageAsset("images/Gold.png");
    public static ImageAsset DIAMOND = new ImageAsset("images/diamond.png");

    public static ImageAsset IN_WATER = new ImageAsset("images/in_water.png");

    public static ImageAsset IN_LAVA = new ImageAsset("images/in_lava.png");

    public static ImageAsset LAVA_MONSTER = new ImageAsset("images/Lava Monster.png");
    public static ImageAsset PORK_CHOP = new ImageAsset("images/Porkchop.png");
    public static ImageAsset SNAKE = new ImageAsset("images/Snake.png");
    public static ImageAsset SEA_DRAGON = new ImageAsset("images/Sea Dragon.png");
    public static ImageAsset BREAD = new ImageAsset("images/Bread.png");
    public static ImageAsset BEEF = new ImageAsset("images/Beef.png");
    public static ImageAsset GUY_FLY = new ImageAsset("images/guy_fly.png");
    public static ImageAsset TWIG = new ImageAsset("images/Twig.png");
    public static ImageAsset ROCK = new ImageAsset("images/rock.png");
    public static ImageAsset AXE = new ImageAsset("images/axe.png");
    public static ImageAsset CHECK = new ImageAsset("images/check.png");
    public static ImageAsset X = new ImageAsset("images/x.png");
    public static ImageAsset PICKAXE = new ImageAsset("images/Pickaxe.png");
    public static ImageAsset BOULDER = new ImageAsset("images/boulder.png");
    public static ImageAsset BOULDER_1 = new ImageAsset("images/boulder_1.png");
    public static ImageAsset BOULDER_2 = new ImageAsset("images/boulder_2.png");
    public static ImageAsset COW = new ImageAsset("images/cow.png");
    public static ImageAsset BRICK = new ImageAsset("images/brick.png");
    public static ImageAsset BASIC_SWORD = new ImageAsset("images/sword.png");
    public static ImageAsset BERRY = new ImageAsset("images/Berry.png");
    public static ImageAsset BERRY_BUSH = new ImageAsset("images/Berry_Bush.png");
    public static ImageAsset BERRY_BUSH_0 = new ImageAsset("images/berry_bush_no_berries.png");
    public static ImageAsset CAMP_FIRE = new ImageAsset("images/Campfire.png");
    public static ImageAsset CHEST = new ImageAsset("images/Chest.png");
    public static ImageAsset CHICKEN = new ImageAsset("images/Chicken_.png");
    public static ImageAsset DOOR = new ImageAsset("images/Door.png");
    public static ImageAsset EGG = new ImageAsset("images/Egg.png");
    public static ImageAsset FARM_LAND = new ImageAsset("images/Farm_land.png");
    public static ImageAsset FEATHER = new ImageAsset("images/Feather.png");
    public static ImageAsset HOE = new ImageAsset("images/Hoe.png");
    public static ImageAsset PLANKS = new ImageAsset("images/Planks.png");
    public static ImageAsset RAW_BEEF = new ImageAsset("images/Raw_Beef.png");
    public static ImageAsset RAW_CHICKEN = new ImageAsset("images/Raw_Chicken.png");
    public static ImageAsset RAW_PORK = new ImageAsset("images/Raw_Pork.png");
    public static ImageAsset SAND_BOULDER = new ImageAsset("images/Sand_Boulder.png");
    public static ImageAsset SAND_BOULDER_2 = new ImageAsset("images/sand_boulder_2.png");
    public static ImageAsset SAND_BOULDER_1 = new ImageAsset("images/sand_boulder_1.png");
    public static ImageAsset SANDSTONE = new ImageAsset("images/sandstone.png");
    public static ImageAsset SANDSTONE_WALL = new ImageAsset("images/Sandstone_Wall.png");
    public static ImageAsset SUGARCANE = new ImageAsset("images/Sugarcane.png");

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

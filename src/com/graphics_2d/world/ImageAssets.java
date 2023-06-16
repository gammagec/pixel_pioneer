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

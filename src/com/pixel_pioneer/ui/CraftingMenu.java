package com.pixel_pioneer.ui;

import com.pixel_pioneer.world.*;
import com.pixel_pioneer.world.entities.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CraftingMenu {

    private final World world;

    private final Player player;
    private final SpriteSheet spriteSheet;
    private BufferedImage craftingBackground = null;
    private BufferedImage image = null;
    private boolean menuOpen = false;
    private int width = 0;
    private int height = 0;

    private int selection = 0;

    public CraftingMenu(World world, SpriteSheet spriteSheet) {
        this.world = world;
        this.player = world.getPlayer();
        this.spriteSheet = spriteSheet;
    }

    public void update() {
        if (craftingBackground == null) {
            File file = new File("images/Crafting_menu.png");
            try {
                craftingBackground = ImageIO.read(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            width = craftingBackground.getWidth();
            height = craftingBackground.getHeight();
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(craftingBackground, 0, 0, null);
        int x = 62;
        int y = 115;
        int i = 0;
        for (Recipe recipe : Recipes.ALL_RECIPES) {
            spriteSheet.drawTile(g2d, x, y + (i * 67), 64, 64,
                    GameObject.OBJECTS_BY_ID.get(recipe.getOutputObjectId()).getImageAsset(0).getId());
            g2d.setColor(Color.BLUE);
            g2d.drawString(String.valueOf(recipe.getAmount()), x, y + (i * 67) + 30);
            int t = 0;
            boolean canMake = true;
            for (Map.Entry<Integer, Integer> entry : recipe.getRequiredObjects().entrySet()) {
                spriteSheet.drawTile(g2d, x + 70 + (t * 67), y + (i * 67), 64, 64,
                        GameObject.OBJECTS_BY_ID.get(entry.getKey()).getImageAsset(0).getId());
                g2d.drawString(String.valueOf(entry.getValue()), x + 70 + (t * 67), y + (i * 67) + 30);
                if (player.getObjectCount(entry.getKey()) < entry.getValue()) {
                    canMake = false;
                }
                t++;
            }
            if (canMake) {
                spriteSheet.drawTile(g2d, x + 70 + (t * 67), y + (i * 67), 64, 64,
                        ImageAssets.CHECK.getId());
            } else {
                spriteSheet.drawTile(g2d, x + 70 + (t * 67), y + (i * 67), 64, 64,
                        ImageAssets.X.getId());
            }
            if (i == selection) {
                g2d.setColor(Color.RED);
                g2d.drawRect(x, y + (i * 67), 64, 64);
            }
            i++;
        }
    }

    public void setOpen(boolean open) {
        this.menuOpen = open;
    }

    public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
        if (menuOpen) {
            int left = screenWidth / 2 - width / 2;
            int top = screenHeight / 2 - height / 2;
            g2d.drawImage(image, left, top, null);
        }
    }

    public void selectionUp() {
        if (selection > 0) {
            selection--;
        }
    }

    public void selectionDown() {
        if (selection < Recipes.ALL_RECIPES.length - 1) {
            selection++;
        }
    }

    public Recipe getSelectedRecipe() {
        return Recipes.ALL_RECIPES[selection];
    }
}

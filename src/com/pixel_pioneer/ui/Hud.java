package com.pixel_pioneer.ui;

import com.pixel_pioneer.Const;
import com.pixel_pioneer.world.ObjectInstance;
import com.pixel_pioneer.world.entities.Player;
import com.pixel_pioneer.world.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Hud {

    static int HEIGHT = 100;
    static int WIDTH = 1000;

    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
    private final Player player;
    private final SpriteSheet spriteSheet;

    public Hud(Player player, SpriteSheet spriteSheet) {
        this.player = player;
        this.spriteSheet = spriteSheet;
    }

    public void update() {
        Graphics2D g2d = image.createGraphics();
        // g2d.setColor(Color.RED);
        // g2d.fillRect(0, 0, WIDTH, HEIGHT);
        int x = 5;
        int y = 5;
        drawStatBar(g2d, x, y, "Health:", player.getHealth(), Const.MAX_HEALTH, Color.BLUE, Color.GREEN);
        y += 35;
        drawStatBar(g2d, x, y, "Hunger:", player.getHunger(), Const.MAX_HUNGER, new Color(150, 75, 0), Color.YELLOW);
        y += 35;
        drawStatBar(g2d, x, y, "Thirst:", player.getThirst(), Const.MAX_THIRST, Color.ORANGE, Color.CYAN);
        x += 155;
        y = 5;
        drawStatBar(g2d, x, y, "Stamina:", player.getStamina(), Const.MAX_STAMINA, Color.CYAN, Color.PINK);
        drawHotBar(g2d, 315, 5);
    }

    void drawStatBar(Graphics2D g2d, int x, int y, String name, int value, int maxValue, Color bgColor, Color fgColor) {
        g2d.setColor(bgColor);
        g2d.fillRect(x, y, 150, 30);
        g2d.setColor(fgColor);
        g2d.drawString(name, x + 7, y + 20);
        for (int i = 0; i < maxValue; i++) {
            if (i == value) {
                g2d.setColor(Color.BLACK);
            }
            g2d.fillRect(x + 55 + (11 * i), y + 4, 10, 20);
        }
    }

    void drawHotBar(Graphics2D g2d, int x, int y) {
        for (int i = 0; i < Const.INVENTORY_WIDTH; i++) {
            int ix = x + 5 + (66 * i);
            int iy = y + 5;
            ObjectInstance obj = player.getInventoryAt(i, 0);
            g2d.setColor(Color.darkGray);
            g2d.fillRect(ix, iy, 64, 64);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(ix, iy, 64, 64);
            if (obj != null) {
                int count = obj.getCount();
                GameObject gObj = GameObject.OBJECTS_BY_ID.get(obj.getObjectId());
                int objImageIndex = gObj.getImageAsset(0).getId();
                spriteSheet.drawTile(g2d, ix, iy, 64, 64, objImageIndex);
                g2d.setColor(Color.RED);
                g2d.drawString(String.valueOf(i + 1), ix, iy + 20);
                g2d.drawString(String.valueOf(count), ix, iy + 60);
                if (i == player.getBuildingIndex() - 1) {
                    g2d.drawRect(ix, iy, 64, 64);
                }
            }
        }
    }

    void draw(Graphics2D g2d, int width, int height) {
        g2d.drawImage(image, width / 2 - (WIDTH / 2), height - 150, null);
    }
}

package com.graphics_2d.ui;

import com.graphics_2d.Const;
import com.graphics_2d.world.entities.Player;
import com.graphics_2d.world.GameObject;
import com.graphics_2d.world.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Hud {

    static int HEIGHT = 100;
    static int WIDTH = 800;

    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
    private final Player player;
    private final SpriteSheet spriteSheet;

    public Hud(Player player, SpriteSheet spriteSheet) {
        this.player = player;
        this.spriteSheet = spriteSheet;
    }

    public void update() {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        drawStatBar(g2d, 5, 5, "Health:", player.getHealth(), Const.MAX_HEALTH, Color.BLUE, Color.GREEN);
        drawStatBar(g2d, 5, 35, "Hunger:", player.getHunger(), Const.MAX_HUNGER, new Color(150, 75, 0), Color.YELLOW);
        drawStatBar(g2d, 5, 65, "Thirst:", player.getThirst(), Const.MAX_THIRST, Color.ORANGE, Color.CYAN);
        drawStatBar(g2d, 155, 5, "Stamina:", player.getStamina(), Const.MAX_STAMINA, Color.CYAN, Color.PINK);
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
        g2d.setColor(Color.darkGray);
        g2d.fillRect(x, y, 400, 90);
        int i = 0;
        g2d.setColor(Color.RED);
        for (Integer objId : player.getObjects()) {
            int count = player.getObjectCount(objId);
            GameObject obj = GameObjects.OBJECTS_BY_ID.get(objId);
            int objImageIndex = obj.getImageAsset().getIndex();
            spriteSheet.drawTile(g2d, x + 5 + (66 * i), y + 5, 64, 64, objImageIndex);
            g2d.drawString(String.valueOf(i + 1), x + 5 + (66 * i), y + 25);
            g2d.drawString(String.valueOf(count), x + 5 + (66 * i), y + 65);
            if (i == player.getBuildingIndex()) {
                g2d.setColor(Color.RED);
                g2d.drawRect(x + (66 * i), y + 4, 65, 65);
            }
            i++;
            if (i == 10) break;
        }
    }

    void draw(Graphics2D g2d, int width, int height) {
        g2d.drawImage(image, width / 2 - (WIDTH / 2), height - 150, null);
    }
}

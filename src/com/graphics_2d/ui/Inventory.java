package com.graphics_2d.ui;

import com.graphics_2d.world.entities.Player;
import com.graphics_2d.world.GameObject;
import com.graphics_2d.world.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Inventory {

    static int HEIGHT = 800;
    static int WIDTH = 100;

    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
    private final Player player;
    private final SpriteSheet spriteSheet;

    private boolean open = false;

    public Inventory(Player player, SpriteSheet spriteSheet) {
        this.player = player;
        this.spriteSheet = spriteSheet;
    }

    public void toggleOpen() {
        open = !open;
    }

    public void update() {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        g2d.setColor(Color.BLACK);

        int x = 7;
        int y = 5;
        for (Integer objId : player.getObjects()) {
            GameObject obj = GameObjects.OBJECTS_BY_ID.get(objId);
            spriteSheet.drawTile(g2d, x, y, 64, 64, obj.getImageAsset().getIndex());
            g2d.drawString(player.getObjectCount(objId).toString(), x + 7 + 64 + 2, y + 12);
            y += 64 + 5;
        }
    }

    void draw(Graphics2D g2d, int width, int height) {
        if (open) {
            g2d.drawImage(image, 5, height / 2 - (HEIGHT / 2), null);
        }
    }
}

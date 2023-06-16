package com.graphics_2d.ui;

import com.graphics_2d.Const;
import com.graphics_2d.world.ObjectInstance;
import com.graphics_2d.world.entities.Player;
import com.graphics_2d.world.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Inventory {

    static int HEIGHT = 800;
    static int WIDTH = 800;

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
        for (int iy = 0; iy < Const.INVENTORY_HEIGHT; iy++) {
            for (int ix = 0; ix < Const.INVENTORY_WIDTH; ix++) {
                ObjectInstance obj = player.getInventoryAt(ix, iy);
                if (obj != null) {
                    GameObject gObj = GameObject.OBJECTS_BY_ID.get(obj.getObjectId());
                    spriteSheet.drawTile(g2d, x + (ix * 69), y + (iy * 69), 64, 64, gObj.getImageAsset(0).getId());
                    g2d.drawString(String.valueOf(obj.getCount()), x + 7 + 64 + 2, y + 12);
                }
            }
        }
    }

    void draw(Graphics2D g2d, int width, int height) {
        if (open) {
            g2d.drawImage(image, 5, height / 2 - (HEIGHT / 2), null);
        }
    }
}

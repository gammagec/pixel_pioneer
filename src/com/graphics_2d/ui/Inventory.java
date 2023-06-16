package com.graphics_2d.ui;

import com.graphics_2d.Const;
import com.graphics_2d.util.PointI;
import com.graphics_2d.world.ObjectInstance;
import com.graphics_2d.world.entities.Player;
import com.graphics_2d.world.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Inventory {

    static int HEIGHT = 562;
    static int WIDTH = 700;

    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
    private final Player player;
    private final SpriteSheet spriteSheet;

    private ObjectInstance selectedObject;

    PointI selection = new PointI(0, 0);

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
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        int x = 5;
        int y = 5;
        for (int iy = 0; iy < Const.INVENTORY_HEIGHT; iy++) {
            for (int ix = 0; ix < Const.INVENTORY_WIDTH; ix++) {
                g2d.setColor(Color.BLACK);
                ObjectInstance obj = player.getInventoryAt(ix, iy);
                g2d.setColor(Color.DARK_GRAY);
                int tx = x + (ix * 69);
                int ty = y + (iy * 69);
                g2d.drawRect(tx, ty, 64, 64);
                if (obj != null) {
                    GameObject gObj = GameObject.OBJECTS_BY_ID.get(obj.getObjectId());
                    spriteSheet.drawTile(g2d, tx, ty, 64, 64, gObj.getImageAsset(0).getId());
                    g2d.drawString(String.valueOf(obj.getCount()), tx, ty + 12);
                }
                if (ix == selection.getX() && iy == selection.getY()) {
                    g2d.setColor(Color.RED);
                    g2d.drawRect(tx, ty, 64, 64);
                    if (selectedObject != null) {
                        GameObject gObj = GameObject.OBJECTS_BY_ID.get(selectedObject.getObjectId());
                        spriteSheet.drawTile(g2d, tx, ty, 32, 32, gObj.getImageAsset(0).getId());
                        g2d.drawString(String.valueOf(selectedObject.getCount()), tx, ty + 12);
                    }
                }
            }
        }
    }

    public void selectObject() {
        if (selectedObject == null) {
            selectedObject = player.getInventoryAt(selection.getX(), selection.getY());
            player.setInventoryAt(selection.getX(), selection.getY(), null);
        } else {
            ObjectInstance at = player.getInventoryAt(selection.getX(), selection.getY());
            if (at == null) {
                player.setInventoryAt(selection.getX(), selection.getY(), selectedObject);
            } else if (at.same(selectedObject)) {
                ObjectInstance inst = player.getInventoryAt(selection.getX(), selection.getY());
                inst.addInstances(selectedObject.getCount());
            }
            selectedObject = null;
        }
    }

    public void moveSelection(int dx, int dy) {
        selection = selection.delta(dx, dy).bound(0, 0, Const.INVENTORY_WIDTH, Const.INVENTORY_HEIGHT);
    }

    void draw(Graphics2D g2d, int width, int height) {
        if (open) {
            g2d.drawImage(image, width / 2 - (WIDTH / 2), height / 2 - (HEIGHT / 2), null);
        }
    }
}

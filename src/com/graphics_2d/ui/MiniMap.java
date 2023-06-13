package com.graphics_2d.ui;

import com.graphics_2d.Const;
import com.graphics_2d.util.PointI;
import com.graphics_2d.world.World;
import com.graphics_2d.world.biomes.Biome;
import com.graphics_2d.world.entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MiniMap {

    private final World world;
    private final Player player;

    BufferedImage image =
            new BufferedImage(Const.WORLD_SIZE * 2, Const.WORLD_SIZE * 2, BufferedImage.TYPE_INT_ARGB);

    private final int width = Const.MINI_MAP_SIZE;
    private final int height = Const.MINI_MAP_SIZE;
    BufferedImage maskImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    public MiniMap(World world) {
        this.world = world;
        this.player = world.getPlayer();
        int cx = width / 2;
        int cy = height / 2;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double distance = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y - cy, 2));
                if (distance < (double) width / 2) {
                    maskImage.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    maskImage.setRGB(x, y, Color.TRANSLUCENT);
                }
            }
        }
    }

    public void update() {
        int left = Const.WORLD_SIZE / 2;
        int top = Const.WORLD_SIZE / 2;
        for (int x = 0; x < Const.WORLD_SIZE; x++) {
            for (int y = 0; y < Const.WORLD_SIZE; y++) {
                Biome biome = world.getBiomeAt(x, y);
                Color color = biome.getMapColor();
                image.setRGB(left + x, top + y, color.getRGB());
            }
        }
    }

    void draw(Graphics2D g2d, int windowWidth, int windowHeight) {
        PointI loc = player.getLocation();

        int left = (Const.WORLD_SIZE / 2) + loc.getX() - (width / 2);
        int top = (Const.WORLD_SIZE / 2) + loc.getY() - (height / 2);
        System.out.println("sub image " + left + " " + top + " " + width + " " + height);
        Image img = image.getSubimage(left, top, width, height);
        BufferedImage dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dst.createGraphics();
        g.drawImage(maskImage, 0, 0, null);
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1.0F);
        g.setComposite(ac);
        g.drawImage(img, 0, 0, null);
        g2d.drawImage(dst, 0, 0, null);
        g2d.setColor(Color.RED);
        g2d.drawRect(width / 2, height / 2, 2, 2);
    }
}

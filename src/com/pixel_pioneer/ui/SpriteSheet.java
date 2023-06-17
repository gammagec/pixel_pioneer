package com.pixel_pioneer.ui;

import com.pixel_pioneer.Const;
import com.pixel_pioneer.world.ImageAsset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteSheet {
    private static final int MAX_IMAGES = 128;
    private final BufferedImage bufferedImage =
            new BufferedImage(Const.TILE_SIZE * MAX_IMAGES, Const.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);

    private VolatileImage volatileImage = null;
    private final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

    private final Map<ImageAsset, Integer> indexMap = new HashMap<>();

    private int nextIndex = 0;

    public SpriteSheet() {
        Graphics2D g = bufferedImage.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.setColor(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setComposite(AlphaComposite.Src);
    }

    public void addImageAsset(ImageAsset imageAsset, BufferedImage mask) {
        int index = nextIndex++;
        indexMap.put(imageAsset, index);
        for (int x = 0; x < Const.TILE_SIZE; x++) {
            for (int y = 0; y < Const.TILE_SIZE; y++) {
                int alpha = (mask.getRGB(x, y) >> 24) & 255;
                if (alpha > 0) {
                    setBlockRGB(index, x, y, imageAsset.getPixelColor(x, y));
                } else {
                    setBlockRGB(index, x, y, new Color(0, 0, 0, 1));
                }
            }
        }
    }

    public void addImageAsset(ImageAsset imageAsset) {
        int index = nextIndex++;
        indexMap.put(imageAsset, index);
        for (int x = 0; x < Const.TILE_SIZE; x++) {
            for (int y = 0; y < Const.TILE_SIZE; y++) {
                setBlockRGB(index, x, y, imageAsset.getPixelColor(x, y));
            }
        }
    }

    public void setBlockRGB(int index, int x, int y, Color color) {
        bufferedImage.setRGB((index * Const.TILE_SIZE) + x, y, color.getRGB());
    }

    public void renderToVolatile() {
        Graphics2D g = volatileImage.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.setColor(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setComposite(AlphaComposite.Src);
        g.drawImage(bufferedImage, 0, 0, null);
    }

    public void updateVolatile() {
        if (volatileImage == null) {
            volatileImage =
                    gc.createCompatibleVolatileImage(Const.TILE_SIZE * MAX_IMAGES, Const.TILE_SIZE, VolatileImage.TRANSLUCENT);
            renderToVolatile();
        }
        int valid = volatileImage.validate(gc);
        if (valid != VolatileImage.IMAGE_OK) {
            renderToVolatile();
        }
    }

    public void drawTile(Graphics2D g2d, int x, int y, int width, int height, ImageAsset imageAsset) {
        drawTile(g2d, x, y, width, height, imageAsset, MaskCorner.NO_MASK);
    }

    public void drawTile(Graphics2D g2d, int x, int y, int width, int height, ImageAsset imageAsset, MaskCorner mask ) {
        updateVolatile();
        Integer index = indexMap.get(imageAsset);
        if (index == null) {
            System.out.println("Image " + imageAsset.getFileName() + " not initialized to this sprite sheet");
            System.exit(1);
        }
        if (mask == MaskCorner.NO_MASK) {
            g2d.drawImage(volatileImage, x, y, x + width, y + height,
                    index * Const.TILE_SIZE, 0, index * Const.TILE_SIZE + Const.TILE_SIZE, Const.TILE_SIZE, null);
        } else {
            switch (mask) {
                case TOP_RIGHT -> g2d.drawImage(volatileImage, x + (width / 2), y, x + width, y + (height / 2),
                        index * Const.TILE_SIZE + (Const.TILE_SIZE / 2), 0, index * Const.TILE_SIZE + Const.TILE_SIZE, Const.TILE_SIZE / 2, null);
                case TOP_LEFT -> g2d.drawImage(volatileImage, x, y, x + width / 2, y + (height / 2),
                        index * Const.TILE_SIZE, 0, index * Const.TILE_SIZE + Const.TILE_SIZE / 2, Const.TILE_SIZE / 2, null);
                case BOTTOM_LEFT -> g2d.drawImage(volatileImage, x, y + (height / 2), x + width / 2, y + height,
                        index * Const.TILE_SIZE, Const.TILE_SIZE / 2, index * Const.TILE_SIZE + Const.TILE_SIZE / 2, Const.TILE_SIZE, null);
                case BOTTOM_RIGHT -> g2d.drawImage(volatileImage, x + (width / 2), y + (height / 2), x + width, y + height,
                        index * Const.TILE_SIZE + Const.TILE_SIZE / 2, Const.TILE_SIZE / 2, index * Const.TILE_SIZE + Const.TILE_SIZE, Const.TILE_SIZE, null);
            }
        }
    }
}

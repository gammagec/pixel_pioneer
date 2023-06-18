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
    private final BufferedImage bufferedImage;

    private VolatileImage volatileImage = null;
    private final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

    private final Map<ImageAsset, Integer> indexMap = new HashMap<>();
    private final int maxImages;

    private int nextIndex = 0;

    public SpriteSheet(int maxImages) {
        this.maxImages = maxImages;
        bufferedImage = new BufferedImage(Const.TILE_SIZE * maxImages, Const.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.setColor(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setComposite(AlphaComposite.Src);
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
                    gc.createCompatibleVolatileImage(Const.TILE_SIZE * maxImages, Const.TILE_SIZE, VolatileImage.TRANSLUCENT);
            renderToVolatile();
        }
        int valid = volatileImage.validate(gc);
        if (valid != VolatileImage.IMAGE_OK) {
            renderToVolatile();
        }
    }

    public void drawTile(Graphics2D g2d, int x, int y, int width, int height, ImageAsset imageAsset) {
        drawTile(g2d, x, y, width, height, imageAsset, null, null, null, null);
    }

    private void checkTileMapped(ImageAsset imageAsset) {
        if (imageAsset == null) return;
        Integer index = indexMap.get(imageAsset);
        if (index == null) {
            System.out.println("Image " + imageAsset.getFileName() + " not initialized to this sprite sheet");
            System.exit(1);
        }
    }

    private final Map<String, Integer> cachedCornerImages = new HashMap<>();

    public int addCachedImage(ImageAsset imageAsset) {
        int index = nextIndex++;
        for (int x = 0; x < Const.TILE_SIZE; x++) {
            for (int y = 0; y < Const.TILE_SIZE; y++) {
                setBlockRGB(index, x, y, imageAsset.getPixelColor(x, y));
            }
        }
        return index;
    }

    public void drawTile(Graphics2D g2d, int x, int y, int width, int height, ImageAsset imageAsset, ImageAsset leftTop, ImageAsset rightTop, ImageAsset leftBottom, ImageAsset rightBottom) {
        updateVolatile();
        Integer index = indexMap.get(imageAsset);
        checkTileMapped(imageAsset);
        checkTileMapped(leftTop);
        checkTileMapped(rightTop);
        checkTileMapped(leftBottom);
        checkTileMapped(rightBottom);

        // Normal Image
        if (leftTop == null && rightTop == null && leftBottom == null && rightBottom == null) {
            g2d.drawImage(volatileImage, x, y, x + width, y + height,
                    index * Const.TILE_SIZE, 0, index * Const.TILE_SIZE + Const.TILE_SIZE, Const.TILE_SIZE, null);
        } else {
            // Rounded corner image
            String str = imageAsset.getId() + ":";
            str += (leftTop == null) ? "n" : leftTop.getId();
            str += ":";
            str += (rightTop == null) ? "n" : rightTop.getId();
            str += ":";
            str += (leftBottom == null) ? "n" : leftBottom.getId();
            str += ":";
            str += (rightBottom == null) ? "n" : rightBottom.getId();

            Integer cacheIndex = cachedCornerImages.get(str);

            if (cacheIndex == null) {
                // draw it!
                cacheIndex = addCachedImage(imageAsset);
                File cornerFile = new File("images/all_corners.png");
                BufferedImage mask;
                try {
                    mask = ImageIO.read(cornerFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (int tx = 0; tx < Const.TILE_SIZE; tx++) {
                    for (int ty = 0; ty < Const.TILE_SIZE; ty++) {
                        int alpha = (mask.getRGB(tx, ty) >> 24) & 255;
                        if (alpha > 0) {
                            if (tx > Const.TILE_SIZE / 2) {
                                if (ty > Const.TILE_SIZE / 2) {
                                    // Bottom Right
                                    if (rightBottom != null) {
                                        setBlockRGB(cacheIndex, tx, ty, rightBottom.getPixelColor(tx, ty));
                                    }
                                } else {
                                    // Top Right
                                    if (rightTop != null) {
                                        setBlockRGB(cacheIndex, tx, ty, rightTop.getPixelColor(tx, ty));
                                    }
                                }
                            } else {
                                if (ty > Const.TILE_SIZE / 2) {
                                    // Bottom Left
                                    if (leftBottom != null) {
                                        setBlockRGB(cacheIndex, tx, ty, leftBottom.getPixelColor(tx, ty));
                                    }
                                } else {
                                    // Top Left
                                    if (leftTop != null) {
                                        setBlockRGB(cacheIndex, tx, ty, leftTop.getPixelColor(tx, ty));
                                    }
                                }
                            }
                        }
                    }
                }
                cachedCornerImages.put(str, cacheIndex);
                renderToVolatile();
            }
            g2d.drawImage(volatileImage, x, y, x + width, y + height,
                    cacheIndex * Const.TILE_SIZE, 0, cacheIndex * Const.TILE_SIZE + Const.TILE_SIZE, Const.TILE_SIZE, null);
        }
    }
}

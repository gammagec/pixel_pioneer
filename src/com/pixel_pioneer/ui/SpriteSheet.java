package com.pixel_pioneer.ui;

import com.pixel_pioneer.Const;
import com.pixel_pioneer.world.ImageAsset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2.0, (newHeight - h) / 2.0);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();

        return rotated;
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
                File cornerFile2 = new File("images/all_corners2.png");
                BufferedImage mask;
                BufferedImage mask2;
                try {
                    mask = ImageIO.read(cornerFile);
                    mask2 = ImageIO.read(cornerFile2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedImage useMask = (new Random().nextInt(2) > 0) ? mask : mask2;
//                int rot = new Random().nextInt(4);
//                rot = switch (rot) {
//                    case 1 -> 90;
//                    case 2 -> 180;
//                    case 3 -> 270;
//                    default -> 0;
//                };
//                if (rot != 0) {
//                    useMask = rotateImageByDegrees(useMask, rot);
//                }

                for (int tx = 0; tx < Const.TILE_SIZE; tx++) {
                    for (int ty = 0; ty < Const.TILE_SIZE; ty++) {
                        int alpha = (useMask.getRGB(tx, ty) >> 24) & 255;
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

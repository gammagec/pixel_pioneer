package com.graphics_2d.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class SpriteSheet {
    private static final int MAX_IMAGES = 128;
    // 32 images of 32x32
    private final BufferedImage bufferedImage =
            new BufferedImage(32 * MAX_IMAGES, 32, BufferedImage.TYPE_INT_ARGB);
    private VolatileImage volatileImage = null;
    private final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

    public SpriteSheet() {
        Graphics2D g = bufferedImage.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.setColor(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setComposite(AlphaComposite.Src);
    }

    public void setBlockRGB(int index, int x, int y, Color color) {
        bufferedImage.setRGB((index * 32) + x, y, color.getRGB());
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
                    gc.createCompatibleVolatileImage(32 * MAX_IMAGES, 32, VolatileImage.TRANSLUCENT);
            renderToVolatile();
        }
        int valid = volatileImage.validate(gc);
        if (valid != VolatileImage.IMAGE_OK) {
            renderToVolatile();
        }
    }

    public void drawTile(Graphics2D g2d, int x, int y, int width, int height, int index) {
        updateVolatile();
        g2d.drawImage(volatileImage, x, y, x + width, y + height,
                index * 32, 0, index * 32 + 32, 32, null);
    }
}

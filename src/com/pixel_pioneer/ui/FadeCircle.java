package com.pixel_pioneer.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class FadeCircle {

    private final Map<Integer, BufferedImage> fadeCircles = new HashMap<>();

    public void drawFade(Graphics2D nightG2d, int mapWidth, int mapHeight, int x, int y, int diameter) {
        int radius = diameter / 2;

        BufferedImage fadeCircle = fadeCircles.get(diameter);
        if (fadeCircle == null) {
            fadeCircle = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            int cx = diameter / 2;
            int cy = diameter / 2;
            for (int ix = 0; ix < diameter; ix++) {
                for (int iy = 0; iy < diameter; iy++) {
                    double distance = Math.sqrt(Math.pow(ix - cx, 2) + Math.pow(iy - cy, 2));
                    if (distance < (double) diameter / 2) {
                        Color color = new Color(0, 0, 0, 255 - ((int) (distance / radius * 255)));
                        fadeCircle.setRGB(ix, iy, color.getRGB());
                    } else {
                        fadeCircle.setRGB(ix, iy, Color.TRANSLUCENT);
                    }
                }
            }
            fadeCircles.put(diameter, fadeCircle);
        }

        nightG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT));
        nightG2d.setColor(Color.RED);
        nightG2d.drawImage(fadeCircle, x - radius, y - radius, null);
        nightG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }
}

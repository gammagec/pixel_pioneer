package com.graphics_2d.world;

import com.graphics_2d.ui.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageAsset {
    private BufferedImage img;

    private final String fileName;

    private final int index;
    private static int nextIndex = 0;

    public ImageAsset(String fileName) {
        this.fileName = fileName;
        this.index = nextIndex++;
    }

    public void initialize() throws IOException {
        File file = new File(fileName);
        img = ImageIO.read(file);
    }

    public String getFileName() {
        return fileName;
    }

    public int getIndex() {
        return this.index;
    }

    public void addToSpriteSheet(SpriteSheet spriteSheet) {
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                spriteSheet.setBlockRGB(index, x, y, getPixelColor(x, y));
            }
        }
    }

    private Color getPixelColor(int x, int y) {
        int RGBA = img.getRGB(x, y);
        int alpha = (RGBA >> 24) & 255;
        int red = (RGBA >> 16) & 255;
        int green = (RGBA >> 8) & 255;
        int blue = RGBA & 255;
        return new Color(red, green, blue, alpha);
    }
}

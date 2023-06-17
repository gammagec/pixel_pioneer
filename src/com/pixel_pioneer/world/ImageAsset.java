package com.pixel_pioneer.world;

import com.pixel_pioneer.ui.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageAsset {
    private BufferedImage img;

    private final String fileName;

    private final int id;
    private static int nextId = 0;

    public static Map<Integer, ImageAsset> ASSETS_BY_ID = new HashMap<>();

    public ImageAsset(String fileName) {
        this.fileName = fileName;
        this.id = nextId++;
        ASSETS_BY_ID.put(this.id, this);
    }

    public void initialize() throws IOException {
        File file = new File(fileName);
        img = ImageIO.read(file);
    }

    public String getFileName() {
        return fileName;
    }

    public int getId() {
        return this.id;
    }

    public void addToSpriteSheet(SpriteSheet spriteSheet) {
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                spriteSheet.setBlockRGB(id, x, y, getPixelColor(x, y));
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

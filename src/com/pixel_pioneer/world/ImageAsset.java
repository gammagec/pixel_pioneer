package com.pixel_pioneer.world;

import com.pixel_pioneer.Const;
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

    private final AssetType type;

    public ImageAsset(String fileName, AssetType type) {
        this.fileName = fileName;
        this.type = type;
        this.id = nextId++;
        ASSETS_BY_ID.put(this.id, this);
    }

    public void initialize() throws IOException {
        File file = new File(fileName);
        img = ImageIO.read(file);
        if (img.getWidth() != Const.TILE_SIZE || img.getHeight() != Const.TILE_SIZE) {
            Image img2 = img.getScaledInstance(Const.TILE_SIZE, Const.TILE_SIZE, Image.SCALE_AREA_AVERAGING);
            img = new BufferedImage(Const.TILE_SIZE, Const.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            g2d.drawImage(img2, 0, 0, null);
            g2d.dispose();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public int getId() {
        return this.id;
    }

    public Color getPixelColor(int x, int y) {
        int RGBA = img.getRGB(x, y);
        int alpha = (RGBA >> 24) & 255;
        int red = (RGBA >> 16) & 255;
        int green = (RGBA >> 8) & 255;
        int blue = RGBA & 255;
        return new Color(red, green, blue, alpha);
    }

    public AssetType getType() {
        return type;
    }
}

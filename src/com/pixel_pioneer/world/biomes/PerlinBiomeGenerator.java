package com.pixel_pioneer.world.biomes;

import com.pixel_pioneer.Const;
import com.pixel_pioneer.util.PointI;
import com.pixel_pioneer.world.LocationInfo;
import com.pixel_pioneer.world.World;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class PerlinBiomeGenerator implements BiomeGenerator {

    public static int WATER_THRESHOLD = 120;

    public static int FOREST_THRESHOLD = 140; // higher = more forest. 255 = None

    public static int VOLCANO_MAX_RADIUS = 35;
    public static int VOLCANO_MIN_RADIUS = 5;

    public static int DESERT_THRESHOLD = 150;

    public static int SNOW_THRESHOLD = 150;

    private final Random random = new Random();
    PerlinNoiseGenerator generator = new PerlinNoiseGenerator(random.nextGaussian() * 255);

    @Override
    public void generateBiomes(LocationInfo[][] locations, World world) {

        iterateMap((PointI pt) -> {
            int x = pt.getX();
            int y = pt.getY();
            double noise = generator.noise(x, y, 32);
            int height = (int) Math.floor((((noise + 1) / 2) * 7));
            switch (height) {
                case 0, 1, 2 -> locations[y][x].setBiomeId(Biomes.WATER.getBiomeId());
                case 3, 4 -> locations[y][x].setBiomeId(Biomes.PLAINS.getBiomeId());
                case 5 -> locations[y][x].setBiomeId(Biomes.MOUNTAIN.getBiomeId());
                case 6 -> locations[y][x].setBiomeId(Biomes.SNOW.getBiomeId());
            }
        });

        generator.setSeed(random.nextGaussian() * 255);

        // Overlay Forests
        iterateMap((PointI pt) -> {
            int x = pt.getX();
            int y = pt.getY();
            double noise = generator.noise(x, y, 32);
            int opt = (int) Math.floor((((noise + 1) / 2) * 5));
            if (locations[y][x].getBiomeId() == Biomes.PLAINS.getBiomeId()) {
                switch (opt) {
                    case 0, 1, 2 -> locations[y][x].setBiomeId(Biomes.PLAINS.getBiomeId());
                    case 3, 4 -> locations[y][x].setBiomeId(Biomes.FOREST.getBiomeId());
                }
            }
        });

        generator.setSeed(random.nextGaussian() * 255);
        PointI center = new PointI(Const.WORLD_SIZE / 2, Const.WORLD_SIZE / 2);
        // Overlay snow & deserts
        iterateMap((PointI pt) -> {
            int x = pt.getX();
            int y = pt.getY();
            double noise = generator.noise(x, y, 32);
            int opt = (int) Math.floor((((noise + 1) / 2) * 6));
            double distanceFromCenter = Math.sqrt(Math.pow(x - center.getX(), 2) + Math.pow(y - center.getY(), 2));
            double distanceRatio = distanceFromCenter / Const.WORLD_SIZE;
            if (locations[y][x].getBiomeId() == Biomes.PLAINS.getBiomeId()) {
                if (opt * distanceRatio > 1.3) {
                    locations[y][x].setBiomeId(Biomes.SNOW.getBiomeId());
                } else if (opt * distanceRatio < 0.2) {
                    locations[y][x].setBiomeId(Biomes.DESERT.getBiomeId());
                }
            }
            if (locations[y][x].getBiomeId() == Biomes.WATER.getBiomeId()) {
                if (opt * distanceRatio > 1.3) {
                    locations[y][x].setBiomeId(Biomes.ICE.getBiomeId());
                }
            }
        });

        // Do rivers
        generator.setSeed(random.nextGaussian() * 255);
        iterateMap((PointI pt) -> {
            int x = pt.getX();
            int y = pt.getY();
            double noise = generator.noise(x, y, 32);
            int opt = (int) Math.floor((((noise + 1) / 2) * 15));
            int biomeId = locations[y][x].getBiomeId();
            if (opt == 6 || opt == 7) {
                if (biomeId == Biomes.PLAINS.getBiomeId() || biomeId == Biomes.FOREST.getBiomeId()
                        || biomeId == Biomes.MOUNTAIN.getBiomeId()) {
                    locations[y][x].setBiomeId(Biomes.WATER.getBiomeId());
                }
                if (biomeId == Biomes.SNOW.getBiomeId()) {
                    locations[y][x].setBiomeId(Biomes.ICE.getBiomeId());
                }
            }
        });
    }

    void iterateMap(Consumer<PointI> func) {
       for (int y = 0; y < Const.WORLD_SIZE; y++) {
           for (int x = 0; x < Const.WORLD_SIZE; x++) {
               func.accept(new PointI(x, y));
           }
       }
    }

    Integer biomeAt(int[][] biomes, int x, int y) {
        if (x > 0 && y > 0 && x < Const.WORLD_SIZE && y < Const.WORLD_SIZE) {
            return biomes[y][x];
        } else {
            return null;
        }
    }

    void makeVolcano(int[][] biomes) {
        int radius = VOLCANO_MIN_RADIUS + random.nextInt(VOLCANO_MAX_RADIUS - VOLCANO_MIN_RADIUS);
        int cx = random.nextInt(Const.WORLD_SIZE);
        int cy = random.nextInt(Const.WORLD_SIZE);
        int x1 = cx - radius;
        int y1 = cy - radius;
        for (int px = x1; px < x1 + radius * 2; px++) {
            for (int py = y1; py < y1 + radius * 2; py++) {
                if (px > 0 && py > 0 && px < Const.WORLD_SIZE && py < Const.WORLD_SIZE) {
                    int distance = (int) Math.sqrt(Math.pow(px - cx, 2) + Math.pow(py - cy, 2));
                    if (distance < radius * 0.4) {
                        biomes[py][px] = Biomes.MOUNTAIN.getBiomeId();
                    } else if (distance < radius) {
                        double noise = generator.noise(px, py, 32);
                        double norm = (noise + 1.0) / 2.0;
                        if (distance / norm < radius) {
                            biomes[py][px] = Biomes.MOUNTAIN.getBiomeId();
                        }
                    }
                }
            }
        }
    }
}

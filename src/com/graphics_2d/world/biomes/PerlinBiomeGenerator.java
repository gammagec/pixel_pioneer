package com.graphics_2d.world.biomes;

import com.graphics_2d.Const;
import com.graphics_2d.world.World;

import java.util.*;

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
    public void generateBiomes(int[][] biomes, World world) {

        // First calculate heights 0-255
        // above Const.WATER_THRESHOLD is land

        // Generate LAND vs Water
        for (int y = 0; y < Const.WORLD_SIZE; y++) {
            for (int x = 0; x < Const.WORLD_SIZE; x++) {
                double noise = generator.noise(x, y, 32);
                int height = (int) Math.floor((((noise + 1) / 2) * 255));
                if (height < WATER_THRESHOLD) {
                    biomes[y][x] = Biomes.WATER.getBiomeId();
                } else {
                    biomes[y][x] = Biomes.PLAINS.getBiomeId();
                }
            }
        }
        //world.growBiomes();
        generator.setSeed(random.nextGaussian() * 255);

        // Overlay Forests
        for (int y = 0; y < Const.WORLD_SIZE; y++) {
            for (int x = 0; x < Const.WORLD_SIZE; x++) {
                double noise = generator.noise(x, y, 32);
                int height = (int) Math.floor((((noise + 1) / 2) * 255));
                if (height >= FOREST_THRESHOLD && biomes[y][x] == Biomes.PLAINS.getBiomeId()) {
                    biomes[y][x] = Biomes.FOREST.getBiomeId();
                }
            }
        }

        generator.setSeed(random.nextGaussian() * 255);
        // Overlay Deserts
        for (int y = 0; y < Const.WORLD_SIZE; y++) {
            for (int x = 0; x < Const.WORLD_SIZE; x++) {
                double noise = generator.noise(x, y, 32);
                int height = (int) Math.floor((((noise + 1) / 2) * 255));
                if (height >= DESERT_THRESHOLD && biomes[y][x] == Biomes.PLAINS.getBiomeId()) {
                    biomes[y][x] = Biomes.DESERT.getBiomeId();
                }
            }
        }

        generator.setSeed(random.nextGaussian() * 255);
        // Overlay Snow
        for (int y = 0; y < Const.WORLD_SIZE; y++) {
            for (int x = 0; x < Const.WORLD_SIZE; x++) {
                double noise = generator.noise(x, y, 32);
                int height = (int) Math.floor((((noise + 1) / 2) * 255));
                if (height >= SNOW_THRESHOLD && biomes[y][x] == Biomes.PLAINS.getBiomeId()) {
                    biomes[y][x] = Biomes.SNOW.getBiomeId();
                }
            }
        }

        // Make volcanoes
        int numVolcanos = 1 + random.nextInt(5);

        for (int i = 0; i < numVolcanos; i++) {
            makeVolcano(biomes);
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
                        biomes[py][px] = Biomes.VOLCANO.getBiomeId();
                    } else if (distance < radius) {
                        double noise = generator.noise(px, py, 32);
                        double norm = (noise + 1.0) / 2.0;
                        if (distance / norm < radius) {
                            biomes[py][px] = Biomes.VOLCANO.getBiomeId();
                        }
                    }
                }
            }
        }
    }
}

package com.graphics_2d.world.biomes;

import com.graphics_2d.Const;
import com.graphics_2d.world.World;

import java.util.Random;

public class OriginalBiomeGenerator implements BiomeGenerator {
    private final Random random = new Random();
    @Override
    public void generateBiomes(int[][] biomes, World world) {
        int numBiomesXorY = Const.WORLD_SIZE / Const.BIOME_SIZE;
        for(int i = 0; i < numBiomesXorY; i++) {
            for (int j = 0; j < numBiomesXorY; j++) {
                int biomeId = random.nextInt(Biomes.ALL_BIOMES.length);
                for (int bx = 0; bx < Const.BIOME_SIZE; bx++) {
                    for (int by = 0; by < Const.BIOME_SIZE; by++) {
                        int x = i * Const.BIOME_SIZE + bx;
                        int y = j * Const.BIOME_SIZE + by;
                        biomes[y][x] = biomeId;
                    }
                }
            }
        }
    }
}
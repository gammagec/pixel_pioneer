package com.pixel_pioneer.world.biomes;

import com.pixel_pioneer.Const;
import com.pixel_pioneer.world.LocationInfo;
import com.pixel_pioneer.world.World;

import java.util.Random;

public class OriginalBiomeGenerator implements BiomeGenerator {
    private final Random random = new Random();
    @Override
    public void generateBiomes(LocationInfo[][] locations, World world) {
        int numBiomesXorY = Const.WORLD_SIZE / Const.BIOME_SIZE;
        for(int i = 0; i < numBiomesXorY; i++) {
            for (int j = 0; j < numBiomesXorY; j++) {
                int biomeId = random.nextInt(Biomes.ALL_BIOMES.length);
                for (int bx = 0; bx < Const.BIOME_SIZE; bx++) {
                    for (int by = 0; by < Const.BIOME_SIZE; by++) {
                        int x = i * Const.BIOME_SIZE + bx;
                        int y = j * Const.BIOME_SIZE + by;
                        locations[y][x].setBiomeId(biomeId);
                    }
                }
            }
        }
    }
}

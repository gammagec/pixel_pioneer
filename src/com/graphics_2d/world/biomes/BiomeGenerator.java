package com.graphics_2d.world.biomes;

import com.graphics_2d.world.World;

public interface BiomeGenerator {

    void generateBiomes(int[][] biomes, World world);
}

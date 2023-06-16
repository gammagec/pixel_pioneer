package com.graphics_2d.world.biomes;

import com.graphics_2d.world.LocationInfo;

public interface BiomeGrower {
    void growBiomes(LocationInfo[][] locations, Integer[] growOnly);
}

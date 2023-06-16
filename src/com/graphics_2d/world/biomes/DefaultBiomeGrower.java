package com.graphics_2d.world.biomes;

import com.graphics_2d.Const;
import com.graphics_2d.world.LocationInfo;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultBiomeGrower implements BiomeGrower {

    private final Random random = new Random();

    @Override
    public void growBiomes(LocationInfo[][] locations, Integer[] growOnly) {
        Set<Integer> growSet = Arrays.stream(growOnly).collect(Collectors.toSet());
        for (int i = 0; i < Const.WORLD_SIZE; i++) {
            for (int j = 0; j < Const.WORLD_SIZE; j++) {
                int b = locations[i][j].getBiomeId();
                if (growSet.size() > 0 && !growSet.contains(b)) {
                    continue;
                }
                int weight = 0;
                int validNeighbors = 0;
                int[][] neighbors = new int[][] {
                        {j - 1, i - 1},
                        {j,     i - 1},
                        {j + 1, i - 1},
                        {j - 1, i    },
                        {j + 1, i    },
                        {j - 1, i + 1},
                        {j,     i + 1},
                        {j + 1, i + 1},
                };
                List<List<Integer>> openNeighbors = new ArrayList<>();
                for (int[] neighbor : neighbors) {
                    int x = neighbor[1];
                    int y = neighbor[0];
                    if (x > 0 && y > 0 && x < Const.WORLD_SIZE && y < Const.WORLD_SIZE) {
                        validNeighbors++;
                        if (locations[y][x].getBiomeId() == b) {
                            weight++;
                        } else {
                            openNeighbors.add(Arrays.asList(y, x));
                        }
                    }
                }
                int emptyNeighbors = openNeighbors.size();
                int invalidNeighbors = 8 - validNeighbors;
                if (emptyNeighbors == 0) {
                    // all same neighbors
                    continue;
                }
                int rand = random.nextInt(10 - invalidNeighbors);
                if (rand < weight) {
                    List<Integer> neighbor = openNeighbors.get(random.nextInt(openNeighbors.size()));
                    locations[neighbor.get(0)][neighbor.get(1)].setBiomeId(b);
                }
            }
        }
    }
}

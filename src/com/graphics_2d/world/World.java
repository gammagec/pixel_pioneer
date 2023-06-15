package com.graphics_2d.world;

import com.graphics_2d.Const;
import com.graphics_2d.util.PointI;
import com.graphics_2d.world.biomes.*;
import com.graphics_2d.world.entities.Mob;
import com.graphics_2d.world.entities.Player;

import java.io.IOException;
import java.util.*;

import static java.lang.System.exit;

public class World {

    private final Player player = new Player();
    private WorldUpdateHandler worldUpdateHandler = null;

    private final int[][] tiles = new int[Const.WORLD_SIZE][Const.WORLD_SIZE];
    private final int[][] objects = new int[Const.WORLD_SIZE][Const.WORLD_SIZE];
    private final int[][] tileBiomes = new int[Const.WORLD_SIZE][Const.WORLD_SIZE];

    private final List<Mob> mobs = new ArrayList<>();
    private final Random random = new Random();

    // private final BiomeGenerator biomeGenerator = new OriginalBiomeGenerator();
    private final BiomeGenerator biomeGenerator = new PerlinBiomeGenerator();
    private final BiomeGrower biomeGrower = new DefaultBiomeGrower();

    public World() {
        for (ImageAsset imageAsset : ImageAssets.ALL_ASSETS) {
            try {
                imageAsset.initialize();
            } catch (IOException e) {
                System.out.println("Couldn't load " + imageAsset.getFileName());
                exit(1);
            }
        }

        generateBiomes();
        generateMap();

        player.reset(this);
    }

    public void addMob(Mob mob) {
        this.mobs.add(mob);
    }

    public Mob getMobAt(int x, int y) {
        for (Mob mob : mobs) {
            PointI p = mob.getLocation();
            if (p.getX() == x && p.getY() == y) {
                return mob;
            }
        }
        return null;
    }

    public Player getPlayer() {
        return player;
    }

    public void generateBiomes() {
        biomeGenerator.generateBiomes(tileBiomes, this);

        for(int i = 0; i < Const.DEFAULT_GROW_BIOMES; i++) {
            growBiomes();
        }
        /*
        for (int i = 0; i < random.nextInt(Const.MAX_RIVER_GENS); i++) {
            generateRiver();
        }*/
    }

    public PointI randomSpawnPoint() {
        return randomSpawnPoint(
                Arrays.stream(Biomes.ALL_BIOMES).map(Biome::getBiomeId).toArray(Integer[]::new), false);
    }

    public PointI randomSpawnPoint(Integer[] biomes, boolean includeSwim) {
        Set<Integer> biomesSet = Set.of(biomes);
        while(true) {
            int x = random.nextInt(Const.WORLD_SIZE);
            int y = random.nextInt(Const.WORLD_SIZE);
            Tile tile = getTileAt(x, y);
            GameObject obj = getObjectAt(x, y);
            boolean objBlocking = false;
            if (obj != null) {
                objBlocking = obj.isBlocking();
            }
            Integer biome = getBiomeAt(x, y).getBiomeId();
            if ((includeSwim || !tile.isSwim()) &&
                    !tile.isBlocking() &&
                    tile.getDamage() == 0 &&
                    !objBlocking &&
                    biomesSet.contains(biome)) {
                return new PointI(x, y);
            }
        }
    }

    public List<Mob> getMobs() {
        return mobs;
    }

    public void growBiomes() {
        biomeGrower.growBiomes(tileBiomes, new Integer[] {});
    }

    public void growBiomes(Integer[] biomes) {
        biomeGrower.growBiomes(tileBiomes, biomes);
    }

    public void removeObject(int x, int y) {
        objects[y][x] = -1;
    }

    public void pickupObject(int x, int y) {
        player.giveObject(GameObjects.OBJECTS_BY_ID.get(objects[y][x]));
        objects[y][x] = -1;
    }

    public Tile getTileAt(int x, int y) {
        return Tiles.TILES_BY_ID.get(tiles[y][x]);
    }

    public Biome getBiomeAt(int x, int y) {
        if (inBounds(x, y)) {
            int biomeId = tileBiomes[y][x];
            return Biomes.BIOMES_BY_ID.get(biomeId);
        }
        return null;
    }

    public GameObject getObjectAt(int x, int y) {
        if (inBounds(x, y)) {
            int objId = objects[y][x];
            if (objId == -1) {
                return null;
            } else {
                return GameObjects.OBJECTS_BY_ID.get(objects[y][x]);
            }
        } else {
            return null;
        }
    }

    public void generateMap() {
        // This is the map generation!
        for(int i = 0; i < Const.WORLD_SIZE; i++) {
            for (int j = 0; j < Const.WORLD_SIZE; j++) {
                int biomeId = tileBiomes[i][j];
                Biome b = Biomes.BIOMES_BY_ID.get(biomeId);
                int tileId = b.getRandomTileIndex();
                tiles[i][j] = tileId;
            }
        }
        for (int i = 0; i < Const.WORLD_SIZE; i++) {
            for (int j = 0; j < Const.WORLD_SIZE; j++) {
                int biomeId = tileBiomes[i][j];
                Biome b = Biomes.BIOMES_BY_ID.get(biomeId);
                GameObject obj = b.getRandomObject();
                if (obj != GameObjects.NO_OBJECT && obj != null) {
                    objects[i][j] = obj.getId();
                } else {
                    objects[i][j] = -1;
                }
            }
        }
    }

    void generateRiver() {
        int x = random.nextInt(Const.WORLD_SIZE);
        int y = random.nextInt(Const.WORLD_SIZE);
        int width = random.nextInt(Const.MAX_RIVER_WIDTH);
        int facing = random.nextInt(4);
        while(x >= 0 && y >= 0 && x < Const.WORLD_SIZE && y < Const.WORLD_SIZE) {
            switch (facing) {
                case 0 -> { // east
                    for (int i = 0; i < width; i++) {
                        if (inBounds(x, y + i)) {
                            tileBiomes[y + i][x] = Biomes.WATER.getBiomeId();
                        }
                    }
                }
                case 1 -> { // south
                    for (int i = 0; i < width; i++) {
                        if (inBounds(x - i, y)) {
                            tileBiomes[y][x - i] = Biomes.WATER.getBiomeId();
                        }
                    }
                }
                case 2 -> { // west
                    for (int i = 0; i < width; i++) {
                        if (inBounds(x, y - i)) {
                            tileBiomes[y - i][x] = Biomes.WATER.getBiomeId();
                        }
                    }
                }
                case 3 -> { // north
                    for (int i = 0; i < width; i++) {
                        if (inBounds(x - i, y)) {
                            tileBiomes[y][x - i] = Biomes.WATER.getBiomeId();
                        }
                    }
                }
            }
            int rand = random.nextInt(100);
            if (rand > 75) {
                // forward
                switch (facing) {
                    case 0 -> x++;
                    case 1 -> y++;
                    case 2 -> x--;
                    case 3 -> y--;
                }
            } else if (rand > 13) {
                facing++;
                if (facing > 3) facing = 0;
            } else { // 12
                facing--;
                if (facing < 0) facing = 3;
            }
        }
    }

    public boolean inBounds(int x, int y) {
        return !(x < 0 || y < 0 || x > Const.WORLD_SIZE - 1 || y > Const.WORLD_SIZE - 1);
    }

    public boolean getBlocking(int x, int y) {
        int objectIndex = objects[y][x];
        if (objectIndex != -1 && GameObjects.OBJECTS_BY_ID.get(objectIndex).isBlocking()) {
            return true;
        }
        return Tiles.TILES_BY_ID.get(tiles[y][x]).isBlocking();
    }

    public void setWorldUpdateHandler(WorldUpdateHandler worldUpdateHandler) {
        this.worldUpdateHandler = worldUpdateHandler;
    }

    public void playerUpdated() {
        if (this.worldUpdateHandler != null) {
            this.worldUpdateHandler.playerUpdated();
        }
    }

    public void worldUpdated() {
        if(this.worldUpdateHandler != null) {
            this.worldUpdateHandler.worldUpdated();
        }
    }

    public void putObject(int x, int y, int objIndex) {
        if (objects[y][x] == -1) {
            objects[y][x] = objIndex;
        }
    }
}

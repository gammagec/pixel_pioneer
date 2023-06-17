package com.pixel_pioneer.world;

import com.pixel_pioneer.Const;
import com.pixel_pioneer.util.PointI;
import com.pixel_pioneer.world.biomes.*;
import com.pixel_pioneer.world.entities.Mob;
import com.pixel_pioneer.world.entities.MobInstance;
import com.pixel_pioneer.world.entities.Player;

import java.util.*;
import java.util.stream.Collectors;

public class World {
    private final Player player = new Player();
    private WorldUpdateHandler worldUpdateHandler = null;

    private final LocationInfo[][] locations = new LocationInfo[Const.WORLD_SIZE][Const.WORLD_SIZE];

    private final List<MobInstance> mobs = new ArrayList<>();
    private final Random random = new Random();

    // private final BiomeGenerator biomeGenerator = new OriginalBiomeGenerator();
    private final BiomeGenerator biomeGenerator = new PerlinBiomeGenerator();
    private final BiomeGrower biomeGrower = new DefaultBiomeGrower();

    public World() {
        ImageAssets.initialize();

        for (int y = 0; y < Const.WORLD_SIZE; y++) {
            for (int x = 0; x < Const.WORLD_SIZE; x++) {
                locations[y][x] = new LocationInfo();
            }
        }
        generateBiomes();
        generateMap();

        player.reset(this);
    }

    public void addMob(MobInstance mob) {
        this.mobs.add(mob);
    }

    public MobInstance getMobAt(int x, int y) {
        for (MobInstance mob : mobs) {
            PointI p = mob.getLocation();
            if (p.getX() == x && p.getY() == y) {
                return mob;
            }
        }
        return null;
    }

    public void killMob(MobInstance mob) {
        mobs.remove(mob);
        PointI loc = mob.getLocation();
        ObjectInstance drop = Mob.MOBS_BY_ID.get(mob.getMobId()).getDrop();
        if (drop != null) {
            putObject(loc.getX(), loc.getY(), drop);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void generateBiomes() {
        biomeGenerator.generateBiomes(locations, this);

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
                Arrays.stream(Biomes.ALL_BIOMES).map(Biome::getBiomeId).collect(Collectors.toSet()), false);
    }

    public PointI randomSpawnPoint(Set<Integer> biomes, boolean includeSwim) {
        while(true) {
            int x = random.nextInt(Const.WORLD_SIZE);
            int y = random.nextInt(Const.WORLD_SIZE);
            Tile tile = getTileAt(x, y);
            ObjectInstance obj = getObjectAt(x, y);
            boolean objBlocking = false;
            if (obj != null) {
                GameObject gObj = GameObject.OBJECTS_BY_ID.get(obj.getObjectId());
                objBlocking = gObj.isBlocking();
            }
            Integer biome = getBiomeAt(x, y).getBiomeId();
            if ((includeSwim || !tile.isSwim()) &&
                    !tile.isBlocking() &&
                    tile.getDamage() == 0 &&
                    !objBlocking &&
                    biomes.contains(biome)) {
                return new PointI(x, y);
            }
        }
    }

    public List<MobInstance> getMobs() {
        return mobs;
    }

    public void growBiomes() {
        biomeGrower.growBiomes(locations, new Integer[] {});
    }

    public void growBiomes(Integer[] biomes) {
        biomeGrower.growBiomes(locations, biomes);
    }

    public void removeObject(int x, int y) {
        locations[y][x].setObjectInstance(null);
    }

    public void pickupObject(int x, int y) {
        ObjectInstance obj = locations[y][x].getObjectInstance();
        if (obj != null) {
            player.giveObject(obj);
            locations[y][x].setObjectInstance(null);
        }
    }

    public Tile getTileAt(int x, int y) {
        return Tiles.TILES_BY_ID.get(locations[y][x].getTileId());
    }

    public Biome getBiomeAt(int x, int y) {
        if (inBounds(x, y)) {
            int biomeId = locations[y][x].getBiomeId();
            return Biomes.BIOMES_BY_ID.get(biomeId);
        }
        return null;
    }

    public ObjectInstance getObjectAt(int x, int y) {
        if (inBounds(x, y)) {
            return locations[y][x].getObjectInstance();
        } else {
            return null;
        }
    }

    public void generateMap() {
        // This is the map generation!
        for(int i = 0; i < Const.WORLD_SIZE; i++) {
            for (int j = 0; j < Const.WORLD_SIZE; j++) {
                int biomeId = locations[i][j].getBiomeId();
                Biome b = Biomes.BIOMES_BY_ID.get(biomeId);
                int tileId = b.getRandomTileIndex();
                locations[i][j].setTileId(tileId);
            }
        }
        for (int i = 0; i < Const.WORLD_SIZE; i++) {
            for (int j = 0; j < Const.WORLD_SIZE; j++) {
                int biomeId = locations[i][j].getBiomeId();
                Biome b = Biomes.BIOMES_BY_ID.get(biomeId);
                GameObject obj = b.getRandomObject();
                if (obj != GameObjects.NO_OBJECT && obj != null) {
                    locations[i][j].setObjectInstance(new ObjectInstance(obj.getId(), obj.getUses()));
                } else {
                    locations[i][j].setObjectInstance(null);
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
                            locations[y + i][x].setBiomeId(Biomes.WATER.getBiomeId());
                        }
                    }
                }
                case 1, 3 -> { // south, north
                    for (int i = 0; i < width; i++) {
                        if (inBounds(x - i, y)) {
                            locations[y][x - i].setBiomeId(Biomes.WATER.getBiomeId());
                        }
                    }
                }
                case 2 -> { // west
                    for (int i = 0; i < width; i++) {
                        if (inBounds(x, y - i)) {
                            locations[y - i][x].setBiomeId(Biomes.WATER.getBiomeId());
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
        ObjectInstance obj = locations[y][x].getObjectInstance();
        if (obj != null && GameObject.OBJECTS_BY_ID.get(obj.getObjectId()).isBlocking()) {
            return true;
        }
        return Tiles.TILES_BY_ID.get(locations[y][x].getTileId()).isBlocking();
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

    public void putObject(int x, int y, ObjectInstance obj) {
        ObjectInstance existingObj = locations[y][x].getObjectInstance();
        if (existingObj == null) {
            locations[y][x].setObjectInstance(obj);
        }
    }
}

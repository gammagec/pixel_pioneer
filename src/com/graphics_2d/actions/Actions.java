package com.graphics_2d.actions;

import com.graphics_2d.Const;
import com.graphics_2d.sound.SoundEngine;
import com.graphics_2d.ui.Hud;
import com.graphics_2d.ui.Inventory;
import com.graphics_2d.ui.MiniMap;
import com.graphics_2d.util.PointI;
import com.graphics_2d.world.GameObject;
import com.graphics_2d.world.GameObjects;
import com.graphics_2d.world.Tile;
import com.graphics_2d.world.World;
import com.graphics_2d.world.entities.Player;

public class Actions {

    private final Player player;
    private final World world;
    private final Hud hud;
    private final Inventory inventory;
    private final SoundEngine soundEngine;
    private final MiniMap miniMap;

    public Actions(World world, Hud hud, Inventory inventory, SoundEngine soundEngine, MiniMap miniMap) {
        this.world = world;
        player = world.getPlayer();
        this.hud = hud;
        this.inventory = inventory;
        this.soundEngine = soundEngine;
        this.miniMap = miniMap;
    }

    public void onEat() {
        System.out.println("Eating");
        player.setEating(true);
    }

    public void onGenerateBiomes() {
        world.generateBiomes();
        world.generateMap();
        world.worldUpdated();
    }

    public void onGrowBiomes() {
        world.growBiomes();
        world.generateMap();
        miniMap.update();
        world.worldUpdated();
    }

    public void onFly() {
        player.toggleFlying();
        world.worldUpdated();
    }

    public void onReset() {
        player.reset(world);
        hud.update();
        world.worldUpdated();
        soundEngine.playNextSong();
    }

    public void onHotBar(int index) {
        if (player.isEating()) {
            player.setEating(false);
            Integer objId = player.getObjectIndexFromHotbarIndex(index);
            System.out.println("hotbar obj " + objId + " at " + index);
            if (objId != null) {
                GameObject obj = GameObjects.OBJECTS_BY_ID.get(objId);
                if (obj.isCanEat()) {
                    player.eatObject(obj);
                    player.removeObject(obj.getId());
                    hud.update();
                    world.worldUpdated();
                }
            }
        } else {
            player.setBuildingIndex(index);
            hud.update();
            world.worldUpdated();
        }
    }

    public void onUp() {
        moveBuildOrDrop(0, -1);
    }

    public void onDown() {
        moveBuildOrDrop(0, 1);
    }

    public void onLeft() {
        moveBuildOrDrop(-1, 0);
    }

    public void onRight() {
        moveBuildOrDrop(1, 0);
    }

    private void move(int dx, int dy) {
        PointI loc = player.getLocation();
        int px = loc.getX();
        int py = loc.getY();
        PointI newLoc = loc.delta(dx, dy);
        if(canMoveHere(newLoc) && player.isAlive()) {
            player.setLocation(newLoc);
            afterMove();
        }
    }

    private void buildOrDrop(int dx, int dy) {
        PointI newLoc = player.getLocation().delta(dx, dy);
        if (canMoveHere(newLoc)) {
            maybePutObject(newLoc.getX(), newLoc.getY());
        }
    }

    private void maybePutObject(int x, int y) {
        Player player = world.getPlayer();
        if (world.getObjectAt(x, y) == null) {
            Integer objIndex = player.getBuildingObjectIndex();
            if (objIndex != null) {
                player.removeObject(objIndex);
                world.putObject(x, y, objIndex);
            }
        }
        player.setBuildingIndex(0);
        player.setEating(false);
        hud.update();
        world.worldUpdated();
    }

    private void moveBuildOrDrop(int dx, int dy) {
        if (player.isBuilding()) {
            buildOrDrop(dx, dy);
        } else {
            move(dx, dy);
        }
    }

    void afterMove() {
        boolean needsHudUpdate = false;
        boolean needsInventoryUpdate = false;
        PointI loc = player.getLocation();
        Tile tile = world.getTileAt(loc.getX(), loc.getY());
        int damage = 0;
        if (!player.isFlying()) {
            damage += tile.getDamage();
        }
        if (player.isExhausted() && tile.isSwim() && !player.isFlying()) {
            damage += 1;
        }
        if (tile.isSwim() && !player.isExhausted() && !player.isFlying()) {
            player.takeStamina(1);
            needsHudUpdate = true;
        }
        GameObject obj = world.getObjectAt(loc.getX(), loc.getY());
        if (obj != null && !player.isFlying()) {
            damage += obj.getDamage();
            if (obj.isCanPickup()) {
                world.pickupObject(loc.getX(), loc.getY());
                needsInventoryUpdate = true;
                needsHudUpdate = true;
            }
        }
        if (damage > 0) {
            needsHudUpdate = true;
            player.takeDamage(damage);
            if (!player.isAlive()) {
                soundEngine.playDeadSong();
            } else {
                soundEngine.playOw();
            }
        } else if (!tile.isSwim() && !player.isStaminaFull()) {
            player.regenStamina(1);
            needsHudUpdate = true;
        }
        if (needsHudUpdate) {
            hud.update();
        }
        if (needsInventoryUpdate) {
            inventory.update();
        }
        player.setEating(false);
        world.worldUpdated();
    }

    boolean canMoveHere(PointI loc) {
        if (loc.getX() < 0) {
            return false;
        }
        if (loc.getY() < 0) {
            return false;
        }
        if (loc.getX() > Const.WORLD_SIZE) {
            return false;
        }
        if (loc.getY() > Const.WORLD_SIZE) {
            return false;
        }
        return !this.world.getBlocking(loc.getX(), loc.getY()) || player.isFlying();
    }

    public void inventory() {
        inventory.toggleOpen();
        world.worldUpdated();
    }
}

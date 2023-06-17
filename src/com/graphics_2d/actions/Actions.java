package com.graphics_2d.actions;

import com.graphics_2d.Const;
import com.graphics_2d.ai.AiEngine;
import com.graphics_2d.sound.SoundEngine;
import com.graphics_2d.ui.CraftingMenu;
import com.graphics_2d.ui.Hud;
import com.graphics_2d.ui.Inventory;
import com.graphics_2d.ui.MiniMap;
import com.graphics_2d.util.Direction;
import com.graphics_2d.util.PointI;
import com.graphics_2d.world.*;
import com.graphics_2d.world.entities.MobInstance;
import com.graphics_2d.world.entities.Player;

import java.util.Map;

public class Actions {

    private final Player player;
    private final World world;
    private final Hud hud;
    private final Inventory inventory;
    private final SoundEngine soundEngine;
    private final AiEngine aiEngine;
    private final MiniMap miniMap;

    private final CraftingMenu craftingMenu;
    private KeyboardHandler keyboardHandler;

    public Actions(
            World world,
            Hud hud,
            Inventory inventory,
            SoundEngine soundEngine,
            AiEngine aiEngine,
            MiniMap miniMap,
            CraftingMenu craftingMenu) {
        this.world = world;
        player = world.getPlayer();
        this.hud = hud;
        this.inventory = inventory;
        this.soundEngine = soundEngine;
        this.aiEngine = aiEngine;
        this.miniMap = miniMap;
        this.craftingMenu = craftingMenu;
    }

    public void setKeyboardHandler(KeyboardHandler keyboardHandler) {
        this.keyboardHandler = keyboardHandler;
    }

    public void craftSelected() {
        player.craftRecipe(craftingMenu.getSelectedRecipe());
        hud.update();
        inventory.update();
        craftingMenu.update();
        world.worldUpdated();
    }

    public void endCraft() {
        craftingMenu.setOpen(false);
        aiEngine.setPaused(false);
        keyboardHandler.setWorldMode();
        world.worldUpdated();
    }

    public void craftSelectionDown() {
        craftingMenu.selectionDown();
        craftingMenu.update();
        world.worldUpdated();
    }

    public void craftSelectionUp() {
        craftingMenu.selectionUp();
        craftingMenu.update();
        world.worldUpdated();
    }

    public void onCraft() {
        craftingMenu.setOpen(true);
        keyboardHandler.setCraftingMode();
        aiEngine.setPaused(true);
        craftingMenu.update();
        world.worldUpdated();
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

    public void use(Direction d) {
        PointI loc = player.getLocation();
        Integer id = player.getObjectIdFromHotbarIndex(player.getBuildingIndex());
        if (id != null) {
            GameObject obj = GameObject.OBJECTS_BY_ID.get(id);
            switch (d) {
                case NORTH -> useLoc(loc.delta(0, -1), obj);
                case SOUTH -> useLoc(loc.delta(0, 1), obj);
                case EAST -> useLoc(loc.delta(1, 0), obj);
                case WEST -> useLoc(loc.delta(-1, 0), obj);
            }
        }
        // after use
        player.setBuildingIndex(0);
        keyboardHandler.setWorldMode();
        hud.update();
        inventory.update();
        world.worldUpdated();
    }

    private void useLoc(PointI loc, GameObject obj) {
        ObjectInstance objUseOn = world.getObjectAt(loc.getX(), loc.getY());
        if (objUseOn != null) {
            GameObject gObjUseOn = GameObject.OBJECTS_BY_ID.get(objUseOn.getObjectId());
            System.out.println("Attempting to use " + obj.getName() + " on " + gObjUseOn.getName());
            for (UseEffect effect : gObjUseOn.getUseEffects()) {
                if (effect.isTriggerObject(obj.getId())) {
                    int uses = objUseOn.getUsesLeft();
                    if (uses < effect.getUsesConsumed()) {
                        return;
                    }
                    uses -= effect.getUsesConsumed();
                    System.out.println("uses left " + uses);
                    if (uses == 0) {
                        System.out.println("remove");
                        world.removeObject(loc.getX(), loc.getY());
                    } else {
                        System.out.println("minus use");
                        objUseOn.setUses(uses);
                    }
                    for (Map.Entry<Integer, Integer> entry : effect.use().entrySet()) {
                        for (int i = 0; i < entry.getValue(); i++) {
                            if (entry.getKey() == GameObjects.SELF.getId()) {
                                player.giveObject(new ObjectInstance(gObjUseOn.getId(), obj.getUses()));
                            } else {
                                player.giveObject(new ObjectInstance(entry.getKey(), obj.getUses()));
                            }
                        }
                    }
                    return;
                }
            }
        } else if (obj.isCanBuild()){
            world.putObject(loc.getX(), loc.getY(), new ObjectInstance(obj.getId(), obj.getUses()));
            player.removeObject(obj.getId());
        } else {
            MobInstance mob = world.getMobAt(loc.getX(), loc.getY());
            if (mob != null) {
                world.killMob(mob);
            }
        }
    }

    public void onHotBar(int index) {
        Integer objId = player.getObjectIdFromHotbarIndex(index);
        if (objId != null) {
            GameObject obj = GameObject.OBJECTS_BY_ID.get(objId);
            if (obj.isCanEat()) {
                player.eatObject(obj);
                player.removeObject(obj.getId());
            } else if (obj.isCanUse()) {
                // Use mode
                player.setBuildingIndex(index);
                keyboardHandler.setUseMode();
            }
            hud.update();
            world.worldUpdated();
        }
    }

    public void onUp() {
        move(0, -1);
    }

    public void onDown() {
        move(0, 1);
    }

    public void onLeft() {
        move(-1, 0);
    }

    public void onRight() {
        move(1, 0);
    }

    private void move(int dx, int dy) {
        PointI loc = player.getLocation();
        PointI newLoc = loc.delta(dx, dy);
        if(canMoveHere(newLoc) && player.isAlive()) {
            player.setLocation(newLoc);
            afterMove();
        }
    }

    private void maybePutObject(int x, int y) {
        Player player = world.getPlayer();
        if (world.getObjectAt(x, y) == null) {
            Integer objIndex = player.getBuildingObjectIndex();
            GameObject gObj = GameObject.OBJECTS_BY_ID.get(objIndex);
            if (objIndex != null) {
                player.removeObject(objIndex);
                world.putObject(x, y, new ObjectInstance(objIndex, gObj.getUses()));
            }
        }
        player.setBuildingIndex(0);
        hud.update();
        world.worldUpdated();
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
        ObjectInstance obj = world.getObjectAt(loc.getX(), loc.getY());
        if (obj != null && !player.isFlying()) {
            GameObject gObj = GameObject.OBJECTS_BY_ID.get(obj.getObjectId());
            damage += gObj.getDamage();
            if (gObj.isCanPickup()) {
                world.pickupObject(loc.getX(), loc.getY());
                needsInventoryUpdate = true;
                needsHudUpdate = true;
            } else if (gObj.isUseOnWalk()) {
                if (obj.getUsesLeft() >= gObj.getWalkOnUseConsume()) {
                    gObj.walkOnUse(player, obj);
                    needsInventoryUpdate = true;
                    needsHudUpdate = true;
                }
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
        inventory.update();
        inventory.toggleOpen();
        keyboardHandler.setInventoryMode();
        aiEngine.setPaused(true);
        world.worldUpdated();
    }

    public void invUp() {
        inventory.moveSelection(0, -1);
        inventory.update();
        world.worldUpdated();
    }

    public void invDown() {
        inventory.moveSelection(0, 1);
        inventory.update();
        world.worldUpdated();
    }

    public void invLeft() {
        inventory.moveSelection(-1, 0);
        inventory.update();
        world.worldUpdated();
    }

    public void invRight() {
        inventory.moveSelection(1, 0);
        inventory.update();
        world.worldUpdated();
    }

    public void invSpace() {
        inventory.selectObject();
        inventory.update();
        hud.update();
        world.worldUpdated();
    }

    public void endInventory() {
        keyboardHandler.setWorldMode();
        inventory.toggleOpen();
        aiEngine.setPaused(false);
        world.worldUpdated();
    }

    public void endUse() {
        keyboardHandler.setWorldMode();
        hud.update();
        world.worldUpdated();
    }
}

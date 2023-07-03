package com.pixel_pioneer;

import com.pixel_pioneer.actions.Actions;
import com.pixel_pioneer.actions.KeyboardHandler;
import com.pixel_pioneer.ai.AiEngine;
import com.pixel_pioneer.clock.Clock;
import com.pixel_pioneer.sound.SoundEngine;
import com.pixel_pioneer.ui.*;
import com.pixel_pioneer.world.World;

public class Main {

    // Pixel Pioneer: Guy's Odyssey
    // - By Tristan Gammage
    // - And Christopher Gammage
    // --- Started May 29, 2023

    public static void main(String[] args) {
        Clock clock = new Clock();
        FadeCircle fadeCircle = new FadeCircle();
        SoundEngine soundEngine = new SoundEngine();
        World world = new World(clock, soundEngine);
        AiEngine aiEngine = new AiEngine(world, soundEngine, clock);
        Hud hud = new Hud(world.getPlayer());
        Inventory inventory = new Inventory(world.getPlayer());
        MiniMap miniMap = new MiniMap(world);
        CraftingMenu craftingMenu = new CraftingMenu(world);
        Actions actions = new Actions(world, hud, inventory, soundEngine, aiEngine, miniMap, craftingMenu, clock);
        KeyboardHandler keyboardHandler = new KeyboardHandler(actions);
        world.getPlayer().setKeyboardHandler(keyboardHandler);
        actions.setKeyboardHandler(keyboardHandler);
        aiEngine.setKeyboardHandler(keyboardHandler);
        soundEngine.playBackgroundMusic();
        GameWindow gameWindow = new GameWindow(
                world, hud, inventory, keyboardHandler, miniMap, craftingMenu, clock,
                fadeCircle);
        aiEngine.populateMobs();
        clock.start();
        gameWindow.setVisible(true);
    }
}
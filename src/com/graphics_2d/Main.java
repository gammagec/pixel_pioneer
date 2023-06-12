package com.graphics_2d;

import com.graphics_2d.actions.Actions;
import com.graphics_2d.actions.KeyboardHandler;
import com.graphics_2d.sound.SoundEngine;
import com.graphics_2d.ui.GameWindow;
import com.graphics_2d.ui.Hud;
import com.graphics_2d.ui.Inventory;
import com.graphics_2d.ui.SpriteSheet;
import com.graphics_2d.world.World;

public class Main {

    public static void main(String[] args) {
        SoundEngine soundEngine = new SoundEngine();
        World world = new World();
        SpriteSheet spriteSheet = new SpriteSheet();
        Hud hud = new Hud(world.getPlayer(), spriteSheet);
        Inventory inventory = new Inventory(world.getPlayer(), spriteSheet);
        Actions actions = new Actions(world, hud, inventory, soundEngine);
        KeyboardHandler keyboardHandler = new KeyboardHandler(actions);
        soundEngine.playBackgroundMusic();
        GameWindow gameWindow = new GameWindow(world, hud, inventory, keyboardHandler, spriteSheet);
        gameWindow.setVisible(true);
    }
}
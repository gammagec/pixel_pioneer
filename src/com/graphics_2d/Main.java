package com.graphics_2d;

import com.graphics_2d.actions.Actions;
import com.graphics_2d.actions.KeyboardHandler;
import com.graphics_2d.ai.AiEngine;
import com.graphics_2d.sound.SoundEngine;
import com.graphics_2d.ui.*;
import com.graphics_2d.world.World;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        SoundEngine soundEngine = new SoundEngine();
        World world = new World();
        AiEngine aiEngine = new AiEngine(world);
        SpriteSheet spriteSheet = new SpriteSheet();
        Hud hud = new Hud(world.getPlayer(), spriteSheet);
        Inventory inventory = new Inventory(world.getPlayer(), spriteSheet);
        MiniMap miniMap = new MiniMap(world);
        Actions actions = new Actions(world, hud, inventory, soundEngine, miniMap);
        KeyboardHandler keyboardHandler = new KeyboardHandler(actions);
        soundEngine.playBackgroundMusic();
        GameWindow gameWindow = new GameWindow(world, hud, inventory, keyboardHandler, spriteSheet, miniMap);
        aiEngine.populateMobs();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                aiEngine.updateMobs();
            }
        }, 500, 500);
        gameWindow.setVisible(true);
    }
}
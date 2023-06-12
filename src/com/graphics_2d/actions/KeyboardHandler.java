package com.graphics_2d.actions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardHandler extends KeyAdapter {

    private final Actions actions;

    public KeyboardHandler(Actions actions) {
        this.actions = actions;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println("Got key event " + keyCode);

        switch (keyCode) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> actions.onUp();
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> actions.onDown();
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> actions.onLeft();
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> actions.onRight();
            case KeyEvent.VK_E -> actions.onEat();
            case KeyEvent.VK_I -> actions.inventory();
            case KeyEvent.VK_G -> {
                if (e.isShiftDown()) {
                    actions.onGenerateBiomes();
                } else {
                    actions.onGrowBiomes();
                }
            }
            case KeyEvent.VK_F -> actions.onFly();
            case KeyEvent.VK_R -> actions.onReset();
            case KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6,
                    KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9 ->
                    actions.onHotBar(keyCode - KeyEvent.VK_0);
        }
    }
}

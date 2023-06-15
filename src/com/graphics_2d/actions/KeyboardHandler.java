package com.graphics_2d.actions;

import com.graphics_2d.util.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardHandler extends KeyAdapter {

    private final Actions actions;

    public KeyboardHandler(Actions actions) {
        this.actions = actions;
    }

    private final KeyAdapter craftingMode = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_C, KeyEvent.VK_ESCAPE -> actions.endCraft();
                case KeyEvent.VK_UP -> actions.craftSelectionUp();
                case KeyEvent.VK_DOWN -> actions.craftSelectionDown();
                case KeyEvent.VK_ENTER -> actions.craftSelected();
            }
        }
    };

    private final KeyAdapter useMode = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_ESCAPE -> actions.endUse();
                case KeyEvent.VK_UP -> actions.use(Direction.NORTH);
                case KeyEvent.VK_DOWN -> actions.use(Direction.SOUTH);
                case KeyEvent.VK_LEFT -> actions.use(Direction.WEST);
                case KeyEvent.VK_RIGHT -> actions.use(Direction.EAST);
            }
        }
    };

    private final KeyAdapter worldMode = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP, KeyEvent.VK_W -> actions.onUp();
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> actions.onDown();
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> actions.onLeft();
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> actions.onRight();
                case KeyEvent.VK_C -> actions.onCraft();
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
    };

    private KeyAdapter activeAdapter = worldMode;

    public void keyPressed(KeyEvent e) {
        activeAdapter.keyPressed(e);
    }

    public void setCraftingMode() {
        this.activeAdapter = craftingMode;
    }

    public void setUseMode() { this.activeAdapter = useMode; }

    public void setWorldMode() {
        this.activeAdapter = worldMode;
    }
}

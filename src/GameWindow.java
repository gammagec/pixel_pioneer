import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static java.lang.System.exit;

public class GameWindow extends JFrame implements WorldUpdateHandler {

    final private World world;
    final private SpriteSheet spriteSheet = new SpriteSheet();

    GameWindow(World world, KeyboardHandler keyboardHandler) {
        super("Game Window");
        this.world = world;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit(0);
            }
        });
        setSize(700 + 16, 700 + 39);
        addKeyListener(keyboardHandler);
        world.setWorldUpdateHandler(this);


        new GrassBlock().addToSpriteSheet(spriteSheet, 1);
        PngBlock[] pngBlocks = {
                new PngBlock("images/guy.png", 31, false),
                new PngBlock("images/stone.png", 0, false),
                new PngBlock("images/forest_grass.png", 2, false),
                new PngBlock("images/Oak_Wood.png", 3, true),
                new PngBlock("images/Water.png", 4, false),
                new PngBlock("images/Dirt.png", 5, false),
                new PngBlock("images/Lava.png", 6, false),
                new PngBlock("images/Sand.png", 7, false),
                new PngBlock("images/Catus.png", 8, false),
                new PngBlock("images/brick.png", 9, true),
        };
        for (PngBlock block : pngBlocks) {
            try {
                block.initialize();
                world.addBlock(block.index, block);
                block.addToSpriteSheet(spriteSheet, block.index);
            } catch (IOException e) {
                System.out.println("Couldn't load " + block.fileName);
                exit(1);
            }
        }
    }

    public void paint(Graphics g) {
        drawBoard(g);
    }

    @Override
    public void worldUpdated() {
        repaint();
    }

    public void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        int headerHeight = 31;
        int borderSize = 8;
        // int startX = borderSize;
        // int startY = headerHeight;
        int mapWidth = getWidth() - borderSize * 2;
        int mapHeight = getHeight() - (headerHeight + borderSize);

        int tileHeight = 128;
        int tileWidth = 128;
        int centerTileX = mapWidth / 2 - (tileWidth / 2);
        int centerTileY = mapHeight / 2 - (tileHeight / 2);
        int numX = Math.ceilDiv(mapWidth, tileWidth);
        int numY = Math.ceilDiv(mapHeight, tileHeight);
        int leftIndex = world.player.loc_x - numX / 2;
        int topIndex = world.player.loc_y - numY / 2;
        int startX = centerTileX - (numX / 2 * tileWidth);
        int startY = centerTileY - (numY / 2 * tileHeight);

        for (int y = 0; y < numY; y++) {
            for (int x = 0; x < numX; x++) {
                // Draw the tile (red or black square), color[0] is black, color[1] is red
                int tx = leftIndex + x;
                int ty = topIndex + y;
                int spriteIndex;
                if (tx >= 0 && tx < 256 && ty >= 0 && ty < 256) {
                    spriteIndex = world.tiles[ty][tx];
                    spriteSheet.drawTile(g2d, x * tileWidth + startX, y * tileHeight + startY,
                            tileWidth, tileHeight, spriteIndex);
                } else {
                    // Off the map
                    g2d.setColor(new Color(255, 255, 255));
                    g2d.fillRect(x * tileWidth + startX, y * tileHeight + startY, tileWidth, tileHeight);
                }
                if (tx == world.player.loc_x && ty == world.player.loc_y) {
                    spriteSheet.drawTile(g2d, x * tileWidth + startX, y * tileHeight + startY,
                            tileWidth, tileHeight, 31); // guy block
                }
            }
        }
    }
}

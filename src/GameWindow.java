import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;

public class GameWindow extends JFrame implements WorldUpdateHandler {

    private World world;

    GameWindow(World world, KeyboardHandler keyboardHandler) {
        super("Game Window");
        this.world = world;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(700 + 16, 700 + 39);
        addKeyListener(keyboardHandler);
        world.setWorldUpdateHandler(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawBoard(g);
    }

    @Override
    public void worldUpdated() {
        repaint();
    }

    final Color[] colors = {
            new Color(0, 0, 0),
            new Color(255, 0, 0)
    };

    public void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int startX = 8;
        int startY = 31;
        System.out.println("bounds are " + startX + " " + startY + " "
                + g.getClipBounds().getWidth() + " " + g.getClipBounds().getHeight());
        int tileWidth = (getWidth() - 16) / 10;
        int tileHeight = (getHeight() - 39) / 10;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                g2d.setColor(colors[world.tiles[y][x]]);
                g2d.fillRect(x * tileWidth + startX, y * tileHeight + startY, tileWidth, tileHeight);
                if (world.player.loc_x == x && world.player.loc_y == y) {
                    // draw the player
                    Ellipse2D.Double circle = new Ellipse2D.Double(
                            x * tileWidth + startX, y * tileHeight + startY, tileWidth, tileHeight);
                    g2d.setColor(new Color(255, 255, 255));
                    g2d.fill(circle);
                }
            }
        }
    }
}

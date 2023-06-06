import java.awt.*;
import java.util.Random;

public class GrassBlock extends Block {
    Color[] colors = {
            new Color(34, 139, 34),  // Dark Green - 0
            new Color(0, 255, 0),    // Lime Green - 1
            new Color(50, 205, 50),  // Medium Green - 2
            new Color(124, 252, 0)   // Light Green - 3
    };

    // Java 2D array of color indexes
    int[][] grassPixelArt = new int[32][32];
    Random random = new Random();

    GrassBlock() {
        // Filling the array with random color indexes to simulate grass
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                grassPixelArt[i][j] = random.nextInt(4);  // Random color index between 0 and 3
            }
        }
    }

    @Override
    public Color getPixelColor(int x, int y) {
        return colors[grassPixelArt[y][x]];
    }
}

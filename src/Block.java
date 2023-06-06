import java.awt.*;

public class Block {

    public void addToSpriteSheet(SpriteSheet spriteSheet, int index) {
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                spriteSheet.setBlockRGB(index, x, y, getPixelColor(x, y));
            }
        }
    }

    boolean isBlocking() {
        return false;
    }

    // x & y should between 0 & 31
    Color getPixelColor(int x, int y) {
        return new Color(0, 0, 0);
    }
}

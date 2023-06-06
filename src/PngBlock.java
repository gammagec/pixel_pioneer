import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PngBlock extends Block {

    BufferedImage img;
    boolean isBlocking;
    String fileName;
    int index;

    PngBlock(String fileName, int index, boolean isBlocking) {
        this.isBlocking = isBlocking;
        this.fileName = fileName;
        this.index = index;
    }

    void initialize() throws IOException {
        File file = new File(fileName);
        img = ImageIO.read(file);
    }

    @Override
    boolean isBlocking() {
        return isBlocking;
    }

    @Override
    Color getPixelColor(int x, int y) {
        int RGBA = img.getRGB(x, y);
        int alpha = (RGBA >> 24) & 255;
        int red = (RGBA >> 16) & 255;
        int green = (RGBA >> 8) & 255;
        int blue = RGBA & 255;
        return new Color(red, green, blue, alpha);
    }
}

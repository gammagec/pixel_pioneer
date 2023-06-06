import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class SpriteSheet {
    // 32 images of 32x32
    BufferedImage bufferedImage = new BufferedImage(32 * 32, 32, BufferedImage.TYPE_INT_ARGB);
    VolatileImage volatileImage = null;
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

    SpriteSheet() {
        Graphics2D g = bufferedImage.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.setColor(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setComposite(AlphaComposite.Src);
    }

    void setBlockRGB(int index, int x, int y, Color color) {
        bufferedImage.setRGB((index * 32) + x, y, color.getRGB());
    }

    void renderToVolatile() {
        Graphics2D g = volatileImage.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.setColor(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setComposite(AlphaComposite.Src);
//        g.setComposite(AlphaComposite.Src);
//        g.setColor(Color.black);
//        g.clearRect(0, 0, volatileImage.getWidth(), volatileImage.getHeight()); // Clears the image.
        g.drawImage(bufferedImage, 0, 0, null);
    }

    void updateVolatile() {
        if (volatileImage == null) {
            volatileImage = gc.createCompatibleVolatileImage(32 * 32, 32, VolatileImage.TRANSLUCENT);
            renderToVolatile();
        }
        int valid = volatileImage.validate(gc);
        if (valid != VolatileImage.IMAGE_OK) {
            renderToVolatile();
        }
    }

    void drawTile(Graphics2D g2d, int x, int y, int width, int height, int index) {
        updateVolatile();
        g2d.drawImage(volatileImage, x, y, x + width, y + height,
                index * 32, 0, index * 32 + 32, 32, null);
    }
}

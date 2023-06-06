import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardHandler extends KeyAdapter {

    World world;

    KeyboardHandler(World world) {
        this.world = world;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println("Got key event " + keyCode);

        if(keyCode == KeyEvent.VK_UP && this.world.player.loc_y != 0) {
            System.out.println("UP!");
            this.world.player.loc_y = this.world.player.loc_y - 1;
            this.world.worldUpdated();
        }
        else if(keyCode == KeyEvent.VK_DOWN && this.world.player.loc_y != 255) {
            System.out.println("DOWN!");
            this.world.player.loc_y = this.world.player.loc_y + 1;
            this.world.worldUpdated();
        }
        else if(keyCode == KeyEvent.VK_LEFT && this.world.player.loc_x != 0) {
            System.out.println("LEFT!");
            this.world.player.loc_x = this.world.player.loc_x - 1;
            this.world.worldUpdated();
        }
        else if(keyCode == KeyEvent.VK_RIGHT && this.world.player.loc_x != 255) {
            System.out.println("RIGHT!");
            this.world.player.loc_x = this.world.player.loc_x + 1;
            this.world.worldUpdated();
        }
    }
}

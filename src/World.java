import java.util.*;

public class World {

    final Player player = new Player();
    private WorldUpdateHandler worldUpdateHandler = null;

    final int[][] tiles = new int[256][256];
    Map<Integer, Block> blocks = new HashMap<>();
    Random random = new Random();

    World() {
        for(int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                int blockId = random.nextInt(10);
                tiles[i][j] = blockId;
            }
        }
    }

    boolean getBlocking(int x, int y) {
        return blocks.get(tiles[y][x]).isBlocking();
    }

    void addBlock(int index, Block block) {
        blocks.put(index, block);
    }

    void setWorldUpdateHandler(WorldUpdateHandler worldUpdateHandler) {
        this.worldUpdateHandler = worldUpdateHandler;
    }

    void worldUpdated() {
        if(this.worldUpdateHandler != null) {
            this.worldUpdateHandler.worldUpdated();
        }
    }
}

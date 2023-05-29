public class World {

    final Player player = new Player();
    private WorldUpdateHandler worldUpdateHandler = null;

    final int[][] tiles = {
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, // 0
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 1}, // 1
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, // 2
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 1}, // 3
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, // 4
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 1}, // 5
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, // 6
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 1}, // 7
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, // 8
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 1}, // 9
    };

    void setWorldUpdateHandler(WorldUpdateHandler worldUpdateHandler) {
        this.worldUpdateHandler = worldUpdateHandler;
    }

    void worldUpdated() {
        if(this.worldUpdateHandler != null) {
            this.worldUpdateHandler.worldUpdated();
        }
    }
}

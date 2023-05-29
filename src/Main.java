public class Main {

    public static void main(String[] args) {
        World world = new World();
        KeyboardHandler keyboardHandler = new KeyboardHandler(world);
        GameWindow gameWindow = new GameWindow(world, keyboardHandler);
        gameWindow.setVisible(true);
    }
}
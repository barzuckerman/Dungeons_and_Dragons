package The_Game;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String path = args[0];

        GameController gameController = new GameController();
        File dir = new File(path);
        gameController.play(gameController.loadLevels(dir));
    }
}

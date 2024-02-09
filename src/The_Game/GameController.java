package The_Game;

import Messages.MessageCallback;
import Players.Player;
import Tile.Position;
import Tile.Tile;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController {
    private CLI cli;
    private Board board;
    private int currentLevel;
    private int numberOfLevels;

    public GameController() {
        cli = new CLI();
        currentLevel = 1;
        numberOfLevels = 1; // temporary
    }

    public void play(List<List<String>> levels) {
        this.numberOfLevels = levels.size();
        Player player = chosenPlayer();

        while (!player.isDead() && currentLevel <= numberOfLevels) { // loop through all levels
            int width = levels.get(currentLevel - 1).get(0).length();
            int height = levels.get(currentLevel - 1).size(); //TODO

            Board b = new Board(levels.get(currentLevel - 1), player, width, height,cli);
            while (!b.getEnemies().isEmpty() && !player.isDead()) { // loop through specific level
                cli.displayMessage(b.toString());
                cli.displayMessage(player.describe());
                b.CharactersTick();

            }
            if (!player.isDead()){
                currentLevel++;
            }
            else {
                cli.displayMessage(b.toString());
                cli.displayMessage("YOU LOST :( BETTER LUCK NEXT TIME");
            }
        }
        if (!player.isDead()) {
            cli.displayMessage("YOU PASSED ALL THE LEVELS! CONGRATS!");
        }

    }


    public List<List<String>> loadLevels(File file) {
        File[] files = file.listFiles();
        List<List<String>> levels = new ArrayList<List<String>>();
        assert files != null;
        for (File f : files) {
            if (f.isFile() && f.getName().contains("level")) {
                List<String> lines = new ArrayList<>();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(f));
                    String next;
                    while ((next = reader.readLine()) != null) {
                        lines.add(next);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File doesn't exist");
                } catch (IIOException e) {
                    System.out.println(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                levels.add(lines);
            }
        }
        return levels;
    }

    public Player chosenPlayer() {
        TileFactory tileFactory = new TileFactory();
        System.out.println("Select a player - ");
        int i = 1;
        List<Player> players = tileFactory.listPlayers();
        for (Player p : players) {
            System.out.println((i) + ".  " + p.describe());
            i++;
        }
        String choose =  cli.readLine();
        int playerNum = Integer.parseInt(choose);
        while (playerNum > 7 || playerNum < 1) {
            System.out.println("Try again");
        }
        return tileFactory.producePlayer(playerNum - 1, new Position(0, 0),cli.m);

    }

}

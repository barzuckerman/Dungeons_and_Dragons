package The_Game;

import Enemies.Enemy;
import Messages.MessageCallback;
import Players.Mage;
import Players.Player;
import Players.Rogue;
import Players.Warrior;
import Resources.Health;
import Tile.Tile;
import Tile.Position;
import Tile.Empty;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private List<Tile> tiles;
    protected Player player;
    private List<Enemy> enemies;
    private int width;
    private int height;
    private CLI cli;


    public Board(List<String> board, Player player, int width, int height, CLI cli) {
//        tiles = new ArrayList<>();
//        for (String line : board) {
//            tiles.addAll(Arrays.asList(line));
//        }
        this.width = width;
        this.height = height;
        this.player = player;
        this.cli = cli;
        loadSpecificLevel(board);
    }

    public void loadSpecificLevel(List<String> lines) {
        tiles = new ArrayList<>();
        TileFactory tileFactory = new TileFactory();
        enemies = new ArrayList<Enemy>();
        MessageCallback messageCallback = (msg) -> System.out.println(msg);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char cTile = lines.get(i).charAt(j);
                switch (cTile) {
                    case '.':
                        tiles.add(tileFactory.produceEmpty(new Position(j, i)));
                        break;
                    case '#':
                        tiles.add(tileFactory.produceWall(new Position(j, i)));
                        break;
                    case '@': {
                        player.setPosition(new Position(j, i));
                        tiles.add(player);
                    }
                    break;
                    case 's':
                    case 'k':
                    case 'q':
                    case 'z':
                    case 'b':
                    case 'g':
                    case 'w':
                    case 'M':
                    case 'C':
                    case 'K':
                    case 'B':
                    case 'Q':
                    case 'D': {
                        enemies.add(tileFactory.produceEnemy(cTile, new Position(j, i), messageCallback));
                        tiles.add(enemies.get(enemies.size() - 1));
                    }
                    break;
                }
            }
        }
        player.setEnemies(enemies);
    }

    public void CharactersTick() {
        // player tick
        Position p = PlayerAction();
        //this.player.interact(get(p.getX(), p.getY()));
        this.player.gameTick();
        if (player.NeedLevelUp()) {
            this.player.specificLevelUp(); // only if needed
        }

        // enemies tick
        for (Enemy enemy : enemies) {
            Position positionEnemy = enemy.gameTick(this.player);
            enemy.interact(get(positionEnemy.getX(), positionEnemy.getY()));
            setEnemies(player.getEnemies());
            for (Enemy e : enemies) {
                reBuildBoard(e);
            }
        }
    }

    public Position PlayerAction() {
        String action = cli.readLine();
        Position current = this.player.getPosition();
        int enemiesSize = enemies.size();
        Position nextStep = new Position(this.player.getPosition().getX(), this.player.getPosition().getY());
        switch (action) {
            case "e":
                this.player.abilityCastAttempt();
                break;
            case "a":
                nextStep.left();
                break;
            case "d":
                nextStep.right();
                break;
            case "s":
                nextStep.down();
                break;
            case "w":
                nextStep.up();
                break;
            case "q":
                return this.player.getPosition();
            default: {
                cli.displayMessage("wrong input");
                PlayerAction();
            }
            break;
        }
        Tile t = get(current.getX(), current.getY());
        if (!action.equals("e")){
            t = get(nextStep.getX(), nextStep.getY());
            this.player.interact(t);
        }
        reBuildBoard(player);

        List<Enemy> update = new ArrayList<>();
        for (Enemy e : enemies) {
            if(e.isDead()) {
                if(!action.equals("e")) {
                    update.add(e);
                    int index = tiles.indexOf(t);
                    Tile tile = new Empty(t.getPosition());
                    tiles.set(index, tile);
                }
                else {
                    update.add(e);
                    int index = tiles.indexOf(e);
                    Tile tile = new Empty(e.getPosition());
                    tiles.set(index, tile);


                }
            }
        }
        for (Enemy e:update) {
            enemies.remove(player.findEnemy(e));
        }

        for (Enemy e : enemies) {
            reBuildBoard(e);
        }
        return player.getPosition();
    }

    public void reBuildBoard(Tile t1) {
        // swap inside the list
        int index1 = tiles.indexOf(t1); //where i am now
        int index2 = t1.getPosition().getX() + (t1.getPosition().getY() * width); //where i want to go
        Tile t2 = tiles.get(index2); //which tile i need to replace
        tiles.set(index1, t2);
        tiles.set(index2, t1);

    }

    //returning what tile am I
    public Tile get(int x, int y) {
        for (Tile t : tiles) {
            if (t.getPosition().equals(new Position(x, y))) {
                return t;
            }
        }
        // Throw an exception if no such tile
        return null;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void remove(Enemy e) {
        tiles.remove(e);
        Position p = e.getPosition();
        tiles.add(new Empty(p));
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }


    @Override
    public String toString() {
        //reBuildBoard(); // update the board after Characters ticks
        StringBuilder output = new StringBuilder();
        //tiles = tiles.stream().sorted().collect(Collectors.toList());
        Iterator<Tile> iter = tiles.iterator();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width & iter.hasNext(); j++) {
                output.append(iter.next().toString());
            }
            output.append("\n");
        }
        return output + "\n";
    }
}
package The_Game;

import Enemies.Enemy;
import Enemies.Monster;
import Enemies.Trap;
import Enemies.Boss;
import Messages.MessageCallback;
import Players.Player;
import Tile.Empty;
import Tile.Wall;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TileFactory {
    private List<Supplier<Player>> playersList;
    private Map<Character, Supplier<Enemy>> enemiesMap;
    private Players.Player selected;

    public TileFactory() {
        playersList = initPlayers();
        enemiesMap = initEnemies();
    }

    private Map<Character, Supplier<Enemy>> initEnemies() {
        List<Supplier<Enemy>> enemies = Arrays.asList(
                () -> new Monster("Lannister Solider", 's', 80, 8, 3, 25, 3),
                () -> new Monster("Lannister Knight", 'k', 200, 14, 8, 50, 4),
                () -> new Monster("Queen's Guard", 'q', 400, 20, 15, 100, 5),
                () -> new Monster("Wright", 'z', 600, 30, 15, 100, 3),
                () -> new Monster("Bear-Wright", 'b', 1000, 75, 30, 250, 4),
                () -> new Monster("Giant-Wright", 'g', 1500, 100, 40, 500, 5),
                () -> new Monster("White Walker", 'w', 2000, 150, 50, 1000, 6),
                () -> new Boss( "The Mountain",'M', 1000, 60, 25,  500, 6, 5),
                () -> new Boss("Queen Cersei",'C',  100, 10, 10,1000, 1, 8),
                () -> new Boss("Night's King",'K',  5000, 300, 150, 5000, 8, 3),
                () -> new Trap("Bonus Enemies.Trap", 'B', 1, 1, 1, 250, 1, 10),
                () -> new Trap("Queen's Enemies.Trap", 'Q', 250, 50, 10, 100, 3, 10),
                () -> new Trap("Death Enemies.Trap", 'D', 500, 100, 20, 250, 1, 10)
        );

        return enemies.stream().collect(Collectors.toMap(s -> s.get().getTile(), Function.identity()));
    }

    private List<Supplier<Player>> initPlayers() {
        return Arrays.asList(
                () -> new Players.Warrior("Jon Snow", 300, 30, 4, 3),
                () -> new Players.Warrior("The Hound", 400, 20, 6, 5),
                () -> new Players.Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6),
                () -> new Players.Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4),
                () -> new Players.Rogue("Arya Stark", 150, 40, 2, 20),
                () -> new Players.Rogue("Bronn", 250, 35, 3, 50),
                () -> new Players.Hunter("Ygritte", 220, 30, 2, 6)
        );
    }

    public List<Players.Player> listPlayers() {
        return playersList.stream().map(Supplier::get).collect(Collectors.toList());
    }


    public Enemies.Enemy produceEnemy(char tile, Tile.Position position, MessageCallback messageCallback) {
        Enemy thisEnemy = initEnemies().get(tile).get();
        thisEnemy.initialize(position,messageCallback);
        return thisEnemy;
    }

    public Players.Player producePlayer(int idx, Tile.Position position,MessageCallback messageCallback) {
        Player thisPlayer = initPlayers().get(idx).get();
        thisPlayer.initialize(position,messageCallback);
        return thisPlayer;
    }

    public Tile.Empty produceEmpty(Tile.Position position) {
        Empty empty = new Empty(position);
        return empty;

    }

    public Tile.Wall produceWall(Tile.Position position) {
        Wall wall = new Wall(position);
        return wall;

    }
}

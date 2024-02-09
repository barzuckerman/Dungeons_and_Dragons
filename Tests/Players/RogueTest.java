package Players;

import Enemies.Monster;
import Tile.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RogueTest {

    Rogue r;
    Monster monster;
    @BeforeEach
    public void setUp(){
        Position p = new Position(3,5);
        this.r = new Players.Rogue("Arya Stark", 150, 40, 2, 20);
        this.monster = new Monster("Lannister Solider", 's', 80, 8, 3, 25, 3);
        r.enemies.add(monster);
    }
    @Test
    void abilityCastAttempt() {

    }

    @Test
    void gameTick() {

    }

    @Test
    void specificLevelUp() {

    }

}
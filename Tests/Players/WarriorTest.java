package Players;

import Enemies.Enemy;
import Enemies.Monster;
import Tile.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest {
    private Warrior JonSnow;
    private Enemy monster;

    @BeforeEach
    public void setUp(){
        Position p = new Position(3,5);
        this.JonSnow = new Warrior("Jon Snow", 300, 30, 4, 3);
        this.monster = new Monster("Lannister Solider", 's', 80, 8, 3, 25, 3);
        JonSnow.enemies.add(monster);
    }

    @Test
    void abilityCastAttempt() {
        //rainy day
        JonSnow.remainingCoolDown= 5;
        JonSnow.abilityCastAttempt();
        assertFalse(5 == JonSnow.remainingCoolDown);

        //sunny day
        JonSnow.remainingCoolDown = 0;
        JonSnow.getHealth().setAmount(50);
        JonSnow.abilityCastAttempt();
        assertEquals(3, JonSnow.remainingCoolDown, 3);
        assertEquals(90,  JonSnow.getHealth().getAmount(), 90);
    }

    @Test
    void gameTick() {
        JonSnow.remainingCoolDown = 4;
        JonSnow.gameTick();
        Integer remainingCooldown = JonSnow.remainingCoolDown;
        assertEquals(4, (int)remainingCooldown, 1);
    }

    @Test
    void specificLevelUp() {
        JonSnow.setExprience(60);
        JonSnow.remainingCoolDown = 2;
        JonSnow.specificLevelUp();
        int level = JonSnow.playerLevel;
        assertTrue(330 == JonSnow.getHealth().getPool());
        assertTrue(0 == JonSnow.remainingCoolDown);
        assertTrue(2 == JonSnow.playerLevel);
        assertTrue(42 == JonSnow.getAttackPoints());

    }

}
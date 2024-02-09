package Tile;

import Enemies.Boss;
import Enemies.Monster;
import Players.Hunter;
import Players.Player;
import Resources.Health;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    private Player p;
    private Empty e;
    private Monster m;
    private Position pos;

    @BeforeEach
    void setUp() {
        pos = new Position(1,2);
        p = new Hunter("yarden", 100,10,20,3);
        m = new Monster("bar", '.', 50,10,10,20,5);
        e = new Empty(pos);
    }

    @Test
    void initialize() {
        assertNotNull(p);
        assertNotNull(m);
        assertNotNull(e);
    }

    @Test
    void battle() {
    }

    @Test
    void isDead() {
        assertFalse(p.isDead());
        p.health.setAmount(0);
        assertTrue(p.isDead());
    }

    @Test
    void getHealth() {
        assertEquals(p.getHealth(), 100);
    }

    @Test
    void getName() {
        assertEquals(p.getName(),"yarden");
        assertEquals(m.getName(),"bar");
    }

    @Test
    void getAttackPoints() {
        assertEquals(p.getAttackPoints(),10);
        assertEquals(m.getAttackPoints(),10);
    }

    @Test
    void getDefensePoints() {
        assertEquals(p.getDefensePoints(),20);
        assertEquals(m.getDefensePoints(),10);
    }

    @Test
    void setAttackPoints() {
        int newAttackPoints = 15;
        p.setAttackPoints(newAttackPoints);
        assertEquals(newAttackPoints, p.getAttackPoints());
    }

    @Test
    void setDefensePoints() {
        int newDefensePoints = 25;
        p.setDefensePoints(newDefensePoints);
        assertEquals(newDefensePoints, p.getDefensePoints());
    }

    @Test
    void describe() {
        String expectedString = String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", m.getName(), m.getHealth().getAmount(), m.getAttackPoints(), m.defensePoints);
        String monsterDescribe = m.describe();
        if(!expectedString.equals(monsterDescribe)){
            throw new RuntimeException("descrive results is not as expected");
        }
    }
}
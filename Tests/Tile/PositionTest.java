package Tile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position p;
    int x;
    int y;

    @BeforeEach
    void setUp() {
        x = 10;
        y = 5;
        p = new Position(x,y);
    }

    @Test
    void getX() {
        assertEquals(10,p.getX());
    }

    @Test
    void getY() {
        assertEquals(5,p.getY());
    }

    @Test
    void setX() {
        p.setX(7);
        assertEquals(7,p.getX());
    }

    @Test
    void setY() {
        p.setY(1);
        assertEquals(1,p.getX());
    }

    @Test
    void left() {
        p.left();
        assertEquals(6,p.getX());
    }

    @Test
    void right() {
        p.right();
        assertEquals(7,p.getX());
    }

    @Test
    void up() {
        p.up();
        assertEquals(0,p.getY());
    }

    @Test
    void down() {
        p.down();
        assertEquals(1,p.getY());
    }


    @Test
    void testEquals() {
        Position notEqual = new Position(7,8);
        Position yes = new Position(1,7);
        assertFalse(notEqual.equals(p));
        assertTrue(yes.equals(p));
    }

    @Test
    void range() {
        Position another = new Position(0,0);
        Position another2 = new Position(1,0);
        assertEquals(1,another2.Range(another));

    }

}
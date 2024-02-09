package Tile;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    private Tile t1;
    private Tile t2;
    private Position p1;
    private Position p2;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        p1 = new Position(1, 2);
        p2 = new Position(4, 3);
        t1 = new Wall(p1);
        t2 = new Empty(p2);
    }

    @org.junit.jupiter.api.Test
    void getTile() {
        assertEquals('#',t1.getTile());
        assertEquals('.',t2.getTile());
    }

    @org.junit.jupiter.api.Test
    void getPosition() {
        assertEquals(p1,t1.getPosition());
        assertEquals(p2,t2.getPosition());
    }

    @org.junit.jupiter.api.Test
    void setPosition() {
        Position newPosition = new Position(10,20);
        t1.setPosition(newPosition);
        assertEquals(newPosition,t1.getPosition());
        assertNotEquals(newPosition,t2.getPosition());
        assertNotEquals(t1.getPosition(),t2.getPosition());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        assertEquals("#",t1.toString());
        assertEquals(".",t2.toString());
    }
}
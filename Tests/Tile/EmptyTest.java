package Tile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyTest {

    Empty empty;

    @BeforeEach
    void setUp() {
        Position position = new Position(7,6);
        empty = new Empty(position);
    }

    @Test
    void testToString() {
        assertEquals(".",empty.toString());

    }
}
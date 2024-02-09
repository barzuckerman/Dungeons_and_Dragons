package Tile;

import Tile.Tile;

public class Empty extends Tile {
    public Empty(Position position) {
        super('.', position);
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }
}

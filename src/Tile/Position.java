package Tile;

public class Position implements Comparable<Position>{
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void left() {
        x = x - 1;
    }

    public void right() {
        x = x + 1;
    }

    public void up() {
        y = y - 1;
    }

    public void down() {
        y = y + 1;
    }

    public Position at(int x, int y) {
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position p = (Position) obj;
            if (p.getX() != this.x)
                return false;
            if (p.getY() != this.y)
                return false;
            return true;
        } else return false;
    }

    public double Range(Position p) {
        return Math.sqrt(Math.pow(this.getX() - p.getX(), 2) + Math.pow(this.getY() - p.getY(), 2));
    }

    @Override
    public int compareTo(Position p) {
        if(x == p.getX() && y == p.getY()){
            return 0;
        } else
            return -1;
    }
}

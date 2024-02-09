package Enemies;

import Players.Player;
import Tile.Position;
import Tile.Unit;

import java.util.Random;


public class Monster extends Enemy {
    private int visionRange; //represents the monster’s vision range, a constructor argument.

    public Monster(String name, char tile, int healthCapacity, int attack, int defense, int experienceValue, int visionRange) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
        this.visionRange = visionRange;
    }


    public Position randomMove() {
        Position move = new Position(this.getPosition().getX(), this.getPosition().getY());
        Random rnd = new Random();
        int randomMove = rnd.nextInt(5);
        switch (randomMove) {
            case 0 -> move.up();
            case 1 -> move.down();
            case 2 -> move.right();
            case 3 -> move.left();
            default -> {
            }
        }
        return move;
    }

    @Override
    public Position gameTick(Player p) {
        Position move = new Position(this.getPosition().getX(), this.getPosition().getY());
        if (Range(this.getPosition(), p.getPosition()) < visionRange) {
            int dx = this.getPosition().getX() - p.getPosition().getX();
            int dy = this.getPosition().getY() - p.getPosition().getY();
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0)
                    move.left();
                else
                    move.right();
            } else {
                if (dy > 0)
                    move.up();
                else
                    move.down();
            }
            return move;
        } else
            return randomMove();
    }

    public String describe() {
        return String.format("%s\nHealth: %s/%s\t\t\tAttack: %s\t\t\tDefense: %s\t\t\texperience:%s",
                this.name, this.health.amount, this.health.pool, this.attackPoints, this.defensePoints, this.experience);
    }
}

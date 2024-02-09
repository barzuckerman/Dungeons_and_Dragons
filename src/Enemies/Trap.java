package Enemies;

import Players.Player;
import Tile.Position;
import Tile.Unit;
import Tile.Position;

public class Trap extends Enemy {
    private int visibilityTime; // amount of ticks that the trap remains visible, a constructor argument
    private int invisibilityTime; //  amount of ticks that the trap remains invisible, a constructor argument
    private int ticksCount; // counts the number of ticks since last visibility state change. Initially 0
    private boolean visible; // indicates whether the trap is currently visible. Initially true

    public Trap(String name, char tile, int healthCapacity, int attack, int defense, int experienceValue, int visibilityTime, int invisibilityTime) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
        this.ticksCount = 0;
        this.visible = true;
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
    }

    @Override
    public Position gameTick(Player p) {
        visible = ticksCount < visibilityTime;
        if (ticksCount == (visibilityTime + invisibilityTime))
            ticksCount = 0;
        else
            ticksCount++;
        if (this.Range(this.position, p.getPosition()) < 2)
            attack();
        return this.position;
    }

    @Override
    public String describe() {
        return String.format("%s\nHealth: %s/%s\t\t\tAttack: %s\t\t\tDefense: %s\t\t\texperience:%s",
                this.name, this.health.amount, this.health.pool, this.attackPoints, this.defensePoints, this.experience);
    }

    @Override
    public String toString() {
        if(visible)
            return "" + this.tile;
        else return ".";
    }
}


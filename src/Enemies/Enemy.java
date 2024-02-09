package Enemies;

import Messages.EnemyDeathCallback;
import Messages.MessageCallback;
import Players.Player;
import Tile.Position;
import Tile.Unit;

public abstract class Enemy extends Unit {
    public boolean isDead;

    protected Enemy(char tile, String name, int healthCapacity, int attack, int defense, int experienceValue) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
    }

    public abstract Position gameTick(Player p);

    public void initialize(Position p, MessageCallback messageCallback) {
        EnemyDeathCallback message = () -> System.out.println(getName() + " enemy died");
        super.initialize(p, messageCallback, message);
    }

    @Override
    public void onDeath(Unit defender) {
        enemyDeathCallback.call();
        messageCallback.send(String.format("%s gained %d experience ",defender.getName(),this.experience));
        defender.setExprience(defender.getExprience() + this.experience);

    }

    @Override
    public void visit(Player p) {
        battle(p);
    }

    @Override
    public void visit(Enemy e) {
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Enemy) {
            Enemy toEqual = (Enemy) obj;
            return (toEqual.getPosition().equals(this.position));

        }
        return false;
    }
}

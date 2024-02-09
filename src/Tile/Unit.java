package Tile;

import Enemies.Enemy;
import Messages.EnemyDeathCallback;
import Messages.MessageCallback;
import Players.Player;
import Resources.Health;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Unit extends Tile {
    protected String name;
    protected Health health;
    protected int attackPoints;
    protected int defensePoints;
    protected int experience;
    protected MessageCallback messageCallback;
    protected EnemyDeathCallback enemyDeathCallback;


    protected Unit(char tile, String name, int healthCapacity, int attack, int defense, int experience) {
        super(tile);
        this.name = name;
        this.health = new Health(healthCapacity, healthCapacity);
        this.attackPoints = attack;
        this.defensePoints = defense;
        this.experience = experience;
    }

    public void initialize(Position position, MessageCallback messageCallback, EnemyDeathCallback enemyDeathCallback) {
        super.setPosition(position);
        this.messageCallback = messageCallback;
        this.enemyDeathCallback = enemyDeathCallback;
    }

    public void SwapPositions(Tile tile) {
        Position thisP = this.getPosition();
        setPosition(tile.position);
        tile.setPosition(thisP);
    }

    public int attack() {
        Random rnd = new Random();
        int attackRoll = rnd.nextInt(attackPoints) + 1;
        String output = getName() + " rolled " + attackRoll + " attack points";
        messageCallback.send(output);
        return attackRoll;
    }

    public int defend() {
        Random rnd = new Random();
        int defenceRoll = rnd.nextInt(defensePoints + 1);
        String output = getName() + " rolled " + defenceRoll + " defence points";
        messageCallback.send(output);
        return defenceRoll;
    }

    // Combat against another unit.
    protected void battle(Unit defender) {
        messageCallback.send(String.format("%s is fighting %s \n%s \n%s ",this.name,defender.name,this.describe(),defender.describe()));
        int attackRoll = this.attack();
        int defendRoll = defender.defend();
        int damage = attackRoll - defendRoll;

        if (damage > 0) { // attacker wins
            messageCallback.send(String.format("%s dealt %d damage to %s ",this.name,damage,defender.name));
            defender.health.loseResource(damage);
            if (defender.health.getAmount() <= 0) { // defender dies
                defender.onDeath(this);
                SwapPositions(defender);
            }
        }

    }

    public boolean isDead() {
        return health.getAmount() <= 0;
    }

    // What happens when the unit dies
    public abstract void onDeath(Unit defender);

    // This unit attempts to interact with another tile.
    public void interact(Tile tile) {
        tile.accept(this);
    }

    public void visit(Empty e) {
        SwapPositions(e);
    }

    public void visit(Wall w) {
    }

    public abstract void visit(Player p);

    public abstract void visit(Enemy e);


    public Health getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public void setAttackPoints(int attack) {
        this.attackPoints = attack;
    }

    public void setDefensePoints(int defensePoints) {
        this.defensePoints = defensePoints;
    }

    public int getExprience() {
        return experience;
    }

    public void setExprience(int experience) {
        this.experience = experience;
    }

    public double Range(Position p1, Position p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public String describe() {
        return String.format("%s\t\tResources.Health: %s/%s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth().amount, getHealth().pool,getAttackPoints(), getDefensePoints());
    }
}

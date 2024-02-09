package Enemies;

import Players.Player;
import Tile.HeroicUnit;
import Tile.Position;

import java.util.Random;

public class Boss extends Enemy implements HeroicUnit {
    private int visionRange; //represents the Boss’s vision range, a constructor argument.
    private int abilityFrequency;
    private int combatTicks;
    private Player playerToFight;

    public Boss(String name, char tile, int healthCapacity, int attack, int defense, int experienceValue, int visionRange, int abilityFrequency) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }

    @Override
    public void abilityCastAttempt() {
        if (this.combatTicks != this.abilityFrequency) {
            messageCallback.send("unable to cast due to lack of resources");
        } else { //can cast the special ability
            this.combatTicks = 0; //initialize
            int playerDefence = this.playerToFight.defend();
            if (playerDefence < this.attackPoints) {
                this.playerToFight.getHealth().loseResource(this.attackPoints);
                if (this.playerToFight.isDead()) {
                    this.playerToFight.onDeath(this);
                    this.playerToFight = null;
                }
            }
        }
    }

    @Override
    public Position gameTick(Player p) {
        this.playerToFight = p;
        Position move = new Position(this.getPosition().getX(), this.getPosition().getY());
        if (Range(this.getPosition(), p.getPosition()) < visionRange) { //player in vision range
            if (this.combatTicks == this.abilityFrequency) { //can cast the special ability
                abilityCastAttempt();
                return p.getPosition();
            } else { //cant cast the special ability. chase after player
                this.combatTicks++;
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
            }
        } else //no player in vision range
            this.combatTicks = 0;
        return randomMove();
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

    public String describe() {
        return String.format("%s\nHealth: %s/%s\t\t\tAttack: %s\t\t\tDefense: %s\t\t\texperience:%s",
                this.name, this.health.amount, this.health.pool, this.attackPoints, this.defensePoints, this.experience);
    }
}

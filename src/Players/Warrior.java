package Players;

import Enemies.Enemy;
import Players.Player;
import Tile.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    public int coolDown; // Represents the number of game ticks required to pass before the warrior can cast the ability again
    public int remainingCoolDown; // Represents the number of ticks remained until the warrior can cast its special ability
    private List<Enemy> myEnemies;
    public static final int ABILITY_RANGE = 3;

    public Warrior(String name, int healthCapacity, int attack, int defense, int coolDown) {
        super(name, healthCapacity, attack, defense, ABILITY_RANGE, "Avenger’s Shield");
        this.coolDown = coolDown;
        this.remainingCoolDown = 0;
        myEnemies = new ArrayList<>();
    }

    @Override
    public void abilityCastAttempt() {
        if (this.remainingCoolDown > 0) {
            messageCallback.send("unable to cast due to lack of resources");
        } else {
            this.remainingCoolDown = this.coolDown;
            this.health.amount = Math.min(this.health.amount + 10 * this.defensePoints, this.health.pool);
            this.myEnemies = new ArrayList<>();
            for (Enemy enemy : this.enemies) {
                if (Range(this.getPosition(), enemy.getPosition()) < this.abilityRange)
                    myEnemies.add(enemy);
            }
            if (myEnemies.isEmpty()) {
                messageCallback.send("There is no enemy in this range");
                return;
            }
            Random pick = new Random();
            Enemy attackEnemy = myEnemies.get(pick.nextInt(myEnemies.size()));
            attackEnemy = findEnemy(attackEnemy);
            if(attackEnemy == null) {
                return;
            }
            int hit = ((this.health.getPool() * 10) / 100);
            //this.health.setAmount(hit);
            attackEnemy.getHealth().loseResource(hit);
            messageCallback.send(getName() + " hit "+ hit + " " +attackEnemy.getName());
            if (attackEnemy.getHealth().getAmount() <= 0) { // defender dies
                this.myEnemies.remove(attackEnemy);
                attackEnemy.onDeath(this);
            }

        }

    }

    @Override
    public void gameTick() {
        if (remainingCoolDown > 0) {
            this.remainingCoolDown = remainingCoolDown - 1;
        }
    }



    @Override
    public void specificLevelUp() {
        this.levelUp();
        this.remainingCoolDown = 0;
        this.health.pool += 5 * playerLevel;
        this.attackPoints += 2 * playerLevel;
        this.defensePoints += playerLevel;

    }


    public String describe() {
        return String.format("%s\nHealth: %s/%s\t\t\tAttack: %s\t\t\tDefense: %s\t\t\tspecial ability: %s \n experience:%s/%s" +
                        "\t\t\trange: %s\t\t\tcooldown: %s\t\t\tremaining CoolDown: %s",
                this.name, this.health.amount, this.health.pool, this.attackPoints, this.defensePoints, this.specialAbilityName, this.experience, this.playerLevel*50, this.abilityRange, this.coolDown, this.remainingCoolDown);
    }
}

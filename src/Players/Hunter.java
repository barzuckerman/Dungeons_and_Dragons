package Players;

import Enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Hunter extends Player {
    private int arrowsCount;
    private int ticksCount;
    private Enemy enemyToAttack;

    public Hunter(String name, int healthCapacity, int attack, int defense, int abilityRange) {
        super(name, healthCapacity, attack, defense, abilityRange,"Shoot");
        this.ticksCount = 0;
        this.arrowsCount = 10 * this.playerLevel;
    }

    @Override
    public void abilityCastAttempt() {
        arrowsCount--;
        int minRange = Integer.MAX_VALUE;

        for (Enemy enemy : this.enemies) {
            if (Range(this.getPosition(), enemy.getPosition()) < minRange)
                this.enemyToAttack = enemy;
        }
        if (enemyToAttack != null) {
            enemyToAttack = findEnemy(enemyToAttack);
            if(enemyToAttack == null) {
                return;
            }
            int enemyDefence = enemyToAttack.defend();
            if (enemyDefence < attackPoints) {
                enemyToAttack.getHealth().loseResource(this.attackPoints);
                if (enemyToAttack.isDead()) {
                    this.removeEnemy(enemyToAttack);
                    enemyToAttack.onDeath(this);
                }
            }
        } else
            messageCallback.send("There is no enemy in this range");

    }

    @Override
    public void gameTick() {
        if (this.ticksCount == 10) {
            this.arrowsCount += playerLevel;
            this.ticksCount = 0;
        } else
            ticksCount += 1;
    }

    @Override
    public void specificLevelUp() {
        super.levelUp();
        arrowsCount += 10 * playerLevel;
        attackPoints += 2 * playerLevel;
        defensePoints += playerLevel;

    }

    @Override
    public String describe() {
        return String.format("%s\nHealth: %s/%s\t\t\tAttack: %s\t\t\tDefense: %s\t\t\tspecial ability: %s \n experience:%s/%s" +
                        "\t\t\trange: %s\t\t\tarrows count: %s\t\t\tcurrent ticks: %s",
                this.name, this.health.amount, this.health.pool, this.attackPoints, this.defensePoints, this.specialAbilityName, this.experience, this.playerLevel*50, this.abilityRange, this.arrowsCount, this.ticksCount);
    }
}

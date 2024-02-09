package Players;

import Enemies.Enemy;
import Players.Player;
import Resources.Energy;
import Tile.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rogue extends Player {
    private int cost; //special ability cost
    private Energy energy;
    private List<Enemy> myEnemies;

    public Rogue(String name, int healthCapacity, int attack, int defense, int cost) {
        super(name, healthCapacity, attack, defense, 2,"Fan of Knives");
        this.cost = cost;
        this.energy = new Energy(100,100);
        this.myEnemies = new ArrayList<>();
    }

    @Override
    public void abilityCastAttempt() {
        if (this.energy.getAmount() < cost) {
            messageCallback.send("unable to cast due to lack of resources");
        } else {
            this.energy.loseResource(this.cost);
            this.myEnemies = new ArrayList<>();
            for (Enemy enemy : this.enemies) {
                if (Range(this.getPosition(), enemy.getPosition()) < this.abilityRange)
                    myEnemies.add(enemy);
            }
            if (myEnemies.isEmpty())
                messageCallback.send("There is no enemy in this range");
            for (Enemy myEnemy : this.myEnemies){

                myEnemy = findEnemy(myEnemy);
                if(myEnemy == null) {
                    return;
                }
                int enemyDefence = myEnemy.defend();
                if (enemyDefence < this.attackPoints) {
                    myEnemy.getHealth().loseResource(this.attackPoints);
                    if (myEnemy.getHealth().getAmount() <= 0) {// myEnemy dies
                        this.myEnemies.remove(myEnemy);
                        myEnemy.onDeath(this);
                    }

                }
            }
        }
    }

    @Override
    public void gameTick() {
        this.energy.setAmount(Math.min(this.energy.getAmount() + 10, 100));
    }

    @Override
    public void specificLevelUp() {
        this.levelUp();
        this.energy.setAmount(100);
        this.setAttackPoints(getAttackPoints() + (3 * this.playerLevel));
    }

    public String describe() {
        return String.format("%s\nHealth: %s/%s\t\t\tAttack: %s\t\t\tDefense: %s\t\t\tspecial ability: %s \n experience:%s/%s" +
                        "\t\t\trange: %s\t\t\tcost: %s\t\t\tenergy: %s",
                this.name, this.health.amount, this.health.pool, this.attackPoints, this.defensePoints, this.specialAbilityName, this.experience, this.playerLevel * 50, this.abilityRange, this.cost, this.energy.getAmount());
    }


}

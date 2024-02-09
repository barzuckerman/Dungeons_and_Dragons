package Players;

import Enemies.Enemy;
import Tile.Unit;
import Tile.HeroicUnit;
import Resources.Mana;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mage extends Player implements HeroicUnit {
    private Mana mana; //pool and amount(current)
    private int manaCost; //ability cost
    private int spellPower; //ability scale factor
    private int hitsCount; //maximum number of times a single cast of the ability can hit
    private List<Enemy> myEnemies;

    public Mage(String name, int healthCapacity, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, healthCapacity, attack, defense, abilityRange, "Blizzard");
        this.mana = new Mana(manaPool, manaPool / 4);
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.myEnemies = new ArrayList<>();
    }

    @Override
    public void abilityCastAttempt() {
        if (getManaAmount() < manaCost) {
            messageCallback.send("unable to cast due to lack of resources");
        }
        else {
            setManaAmount(getManaAmount() - manaCost);
            int hits = 0;
            boolean enemyInRange = false;
            this.myEnemies = new ArrayList<>();
            for (Enemy enemy : this.enemies) {
                if (Range(this.getPosition(), enemy.getPosition()) < this.abilityRange)
                    myEnemies.add(enemy);
            }
            if (myEnemies.isEmpty()) {
                messageCallback.send("There is no enemy in this range");
                return;
            }
            Random rnd = new Random();
            do {
                int index = rnd.nextInt(myEnemies.size());
                Enemy enemyToAttack = myEnemies.get(index);
                enemyToAttack = findEnemy(enemyToAttack);
                if(enemyToAttack == null) {
                    return;
                }
                int enemyDefence = enemyToAttack.defend();
                if (enemyDefence < spellPower) {
                    enemyToAttack.getHealth().loseResource(this.spellPower);
                    if (enemyToAttack.getHealth().getAmount() <= 0) { // attackEnemy dies
                        this.myEnemies.remove(myEnemies.get(index));
                        enemyToAttack.onDeath(this);
                    }
                    hits++;
                }
            }
            while ((hits < this.hitsCount) && !myEnemies.isEmpty());
        }
    }


    @Override
    public void gameTick() {
        setManaAmount(Math.min(getManaPool(), (getManaAmount() + 1) * this.playerLevel));
    }

    @Override
    public void specificLevelUp() {
        this.levelUp();
        this.mana.setPool(this.mana.getPool() + 25 * this.playerLevel);
        this.mana.setAmount(Math.min(this.mana.getAmount() + (this.mana.getPool() / 4), this.mana.getPool()));
        this.spellPower += (10 * playerLevel);

    }

    public int getManaPool() {
        return this.mana.getPool();
    }

    public int getManaAmount() {
        return this.mana.getAmount();
    }

    public void setManaPool(int newPool) {
        this.mana.setPool(newPool);
    }

    public void setManaAmount(int newAmount) {
        this.mana.setAmount(newAmount);
    }

    public String describe() {
        return String.format("%s\nHealth: %s/%s\t\t\tAttack: %s\t\t\tDefense: %s\t\t\tspecial ability: %s \n experience: %s/%s" +
                        "\t\t\trange: %s\t\t\tspell power: %s\t\t\thits count: %s\t\t\tmana: %s/%s\t\t\tmana cost: %s",
                this.name, this.health.amount, this.health.pool, this.attackPoints, this.defensePoints, this.specialAbilityName, this.experience, this.playerLevel * 50, this.abilityRange, this.spellPower, this.hitsCount, this.mana.getAmount(), this.mana.getPool(), this.manaCost);
    }
}

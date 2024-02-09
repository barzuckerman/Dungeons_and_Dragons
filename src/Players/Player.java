package Players;

import Enemies.Enemy;
import Messages.EnemyDeathCallback;
import Messages.MessageCallback;
import Resources.Health;
import Tile.Position;
import Tile.Unit;
import Messages.MessageCallback;


import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Unit {
    //protected int Experience;
    protected int playerLevel;
    protected int abilityRange;
    protected String specialAbilityName;
    protected List<Enemy> enemies;


    public Player(String name, int healthCapacity, int attack, int defense, int abilityRange, String specialAbilityName) {
        super('@', name, healthCapacity, attack, defense, 0);
        this.playerLevel = 1;
        this.abilityRange = abilityRange;
        this.specialAbilityName = specialAbilityName;
        this.enemies = new ArrayList<>();
    }

    public void initialize(Position p, MessageCallback messageCallback) {
        EnemyDeathCallback message = () -> System.out.println(getName() + "YOU HAVE BEEN DEFEATED! GAME OVER :( ");
        super.initialize(p, messageCallback, message);
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public void levelUp() {
            this.experience -= 50 * playerLevel;
            this.playerLevel += 1;
            this.getHealth().healthLevelUpPlayer(playerLevel);
            this.setAttackPoints(this.getAttackPoints() + 4 * playerLevel);
            this.setDefensePoints(this.getAttackPoints() + playerLevel);
            messageCallback.send(getName() + " leveled up to level " + this.playerLevel);
    }

    public abstract void abilityCastAttempt();

    public abstract void gameTick();

    public abstract void specificLevelUp();
    @Override
    public void visit(Player p) {
    }

    public boolean NeedLevelUp(){
        return  (50*playerLevel) <= experience;
    }

    @Override
    public void visit(Enemy enemy) {
        battle(enemy);
        if(enemy.isDead()){
        }
        while(NeedLevelUp()){
            levelUp();
        }
    }

    public Enemy findEnemy(Enemy myEnemy){
        for (Enemy e:enemies) {
            if(e.equals(myEnemy))
                return e;
        }
        return null;

    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void removeEnemy(Enemy enemyToRemove){
        for (Enemy e : this.enemies){
            if (e.getHealth().getAmount() == 0){
                this.enemies.remove(e);
                break;
            }
        }
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    @Override
    public void onDeath(Unit defender) {
        this.tile = 'X';
    }

}

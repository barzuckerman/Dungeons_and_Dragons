package Resources;

public class Health extends Resource {
    public Health (int amount, int pool){
        super(amount, pool);
    }

    public void healthLevelUpPlayer(int level){
        this.setPool(this.getPool()+ 10*level);
        this.setAmount(this.getPool());
    }
}

package Resources;

public class Resource {
    public int amount;
    public int pool;

    public Resource(int amount, int pool) {
        this.amount = amount;
        this.pool = pool;
    }

    public int getAmount() {
        return amount;
    }

    public int getPool() {
        return pool;
    }

    public boolean isPossibleIncrease(int reosourceAdd){
        return (this.amount + reosourceAdd) < this.pool;
    }
    public void addResource(int reosourceAdd){
        this.amount = this.amount + reosourceAdd;
    }

    public void loseResource(int resourceLost){
        if (isPossibleDecrease(resourceLost)){
            this.amount = this.amount - resourceLost;
        }
        else this.amount = 0;
    }
    public boolean isPossibleDecrease(int resourceLost){
        return this.amount - resourceLost > 0;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPool(int pool) {
        this.pool = pool;
    }
}

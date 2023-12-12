package banking;

public abstract class GenericAccount {
    private final double apr;
    private final String id;
    private double balance;
    private boolean canBeWithdrawn;
    private int age = 0;

    protected GenericAccount(double balance, double APR, String id) {
        this.balance = balance;
        this.apr = APR;
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int months) {
        age += months;
    }

    public boolean canBeWithdrawn() {
        return canBeWithdrawn;
    }

    public void setCanBeWithdrawn(boolean canBeWithdrawn) {
        this.canBeWithdrawn = canBeWithdrawn;
    }

    public double getBalance() {
        return balance;
    }

    public double getApr() {
        return apr;
    }

    public void depositMoney(double amountDeposited) {
        balance += amountDeposited;
    }

    public void withdrawMoney(double amountWithdrawn) {
        if (balance <= amountWithdrawn) {
            balance = 0;
        } else {
            balance -= amountWithdrawn;
        }
    }

    public String getId() {
        return id;
    }

}

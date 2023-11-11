public abstract class GenericAccount {
    private double balance;
    private double apr;
    private String id;

    public GenericAccount(double balance, double APR, String id) {
        this.balance = balance;
        this.apr = APR;
        this.id = id;
    }

    // Get account balance
    public double getBalance() {
        return balance;
    }

    // Get account APR value
    public double getApr() {
        return apr;
    }

    // Deposit money into the account
    public void depositMoney(double amountDeposited) {
        balance += amountDeposited;
    }

    // Withdraw money from the account
    public void withdrawMoney(double amountWithdrawn) {
        if (balance <= amountWithdrawn) {
            balance = 0;
        } else {
            balance -= amountWithdrawn;
        }
    }

    // Get accountId
    public String getId() {
        return id;
    }
}

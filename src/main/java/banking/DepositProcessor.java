package banking;

public class DepositProcessor {
    private final Bank bank;

    public DepositProcessor(Bank bank) {
        this.bank = bank;
    }

    public void deposit(String command) {
        String[] parsedCommand = command.split(" ");

        String id = parsedCommand[1];
        double amount = Double.parseDouble(parsedCommand[2]);

        bank.depositMoneyByID(id, amount);
    }
}

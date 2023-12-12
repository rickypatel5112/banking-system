package banking;

public class WithdrawProcessor {
    private final Bank bank;

    public WithdrawProcessor(Bank bank) {
        this.bank = bank;
    }

    public void withdraw(String command) {

        String[] parsedCommand = command.split(" ");

        String id = parsedCommand[1];
        double amount = Double.parseDouble(parsedCommand[2]);

        bank.withdrawMoneyByID(id, amount);

    }
}

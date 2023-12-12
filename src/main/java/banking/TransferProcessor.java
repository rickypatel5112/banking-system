package banking;

public class TransferProcessor {

    private final Bank bank;
    private final DepositProcessor depositProcessor;
    private final WithdrawProcessor withdrawProcessor;

    public TransferProcessor(Bank bank) {
        this.bank = bank;
        depositProcessor = new DepositProcessor(bank);
        withdrawProcessor = new WithdrawProcessor(bank);

    }

    public void transfer(String command) {
        String[] parsedCommand = command.split(" ");
        String fromId = parsedCommand[1];
        String toId = parsedCommand[2];
        double amount = Double.parseDouble(parsedCommand[3]);

        double fromIdBalance = bank.getAccount(fromId).getBalance();

        if (fromIdBalance < amount) {
            amount = fromIdBalance;
        }

        depositProcessor.deposit("deposit " + toId + " " + amount);
        withdrawProcessor.withdraw("withdraw " + fromId + " " + amount);

    }
}

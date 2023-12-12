package banking;

public class CommandProcessor {
    private final Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void runCommand(String command) {

        String[] parsedCommand = command.split(" ");

        if (parsedCommand[0].equalsIgnoreCase("create")) {
            CreateProcessor createProcessor = new CreateProcessor(bank);
            createProcessor.create(command);

        } else if (parsedCommand[0].equalsIgnoreCase("deposit")) {
            DepositProcessor depositProcessor = new DepositProcessor(bank);
            depositProcessor.deposit(command);

        } else if (parsedCommand[0].equalsIgnoreCase("withdraw")) {
            WithdrawProcessor withdrawProcessor = new WithdrawProcessor(bank);
            withdrawProcessor.withdraw(command);

        } else if (parsedCommand[0].equalsIgnoreCase("transfer")) {
            TransferProcessor transferProcessor = new TransferProcessor(bank);
            transferProcessor.transfer(command);
        } else if (parsedCommand[0].equalsIgnoreCase("pass")) {
            PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
            passTimeProcessor.processTime(command);
        }
    }

}

package banking;

public class CommandRunner {
    private Bank bank;
    private GenericAccount genericAccount;

    public CommandRunner(Bank bank) {
        this.bank = bank;
    }

    private void createAccount(String command) {
        String[] parsedCommand = command.split(" ");

        String accountId = parsedCommand[2];
        double apr = Double.parseDouble(parsedCommand[3]);

        if (parsedCommand[1].equalsIgnoreCase("checking")) {
            genericAccount = new CheckingAccount(apr, accountId);
        } else if (parsedCommand[1].equalsIgnoreCase("savings")) {
            genericAccount = new SavingsAccount(apr, accountId);
        } else {
            genericAccount = new CdAccount(apr, accountId, Double.parseDouble(parsedCommand[4]));
        }

        bank.addAccount(genericAccount);
    }


    private void depositMoney(String command) {
        String[] parsedCommand = command.split(" ");

        String accountId = parsedCommand[1];
        double amountToBeDeposited = Double.parseDouble(parsedCommand[2]);

        bank.getAccount(accountId).depositMoney(amountToBeDeposited);
    }

    public void runCommand(String command) {
        if (command.toLowerCase().contains("create")) {
            createAccount(command);
        } else if (command.toLowerCase().contains("deposit")) {
            depositMoney(command);
        }
    }
}

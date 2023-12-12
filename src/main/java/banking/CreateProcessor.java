package banking;

public class CreateProcessor {

    private final Bank bank;
    private GenericAccount account;

    public CreateProcessor(Bank bank) {
        this.bank = bank;
    }

    public void create(String command) {

        String[] parsedCommand = command.split(" ");

        String accountId = parsedCommand[2];
        double apr = Double.parseDouble(parsedCommand[3]);

        if (parsedCommand[1].equalsIgnoreCase("checking")) {
            account = new CheckingAccount(accountId, apr);
        } else if (parsedCommand[1].equalsIgnoreCase("savings")) {
            account = new SavingsAccount(accountId, apr);
            account.setCanBeWithdrawn(true);
        } else {
            account = new CdAccount(accountId, apr, Double.parseDouble(parsedCommand[4]));
        }

        bank.addAccount(account);
    }


}

package banking;

public class DepositValidator extends CommandValidator {

    public DepositValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] parsedCommand = command.split(" ");

        if (parsedCommand.length != 3) {
            return false;
        }

        return doesDepositKeywordExistsInCommand(parsedCommand[0])
                && isIdValid(parsedCommand[1])
                && isAmountToBeDepositedValid(parsedCommand[2], parsedCommand[1]);
    }

    private boolean doesDepositKeywordExistsInCommand(String command) {
        return command.toLowerCase().contains("deposit");
    }

    private boolean isAmountToBeDepositedValid(String balance, String id) {

        String accountType = bank.getAccountType(id);
        double amount;
        try {
            amount = Double.parseDouble(balance);
        } catch (NumberFormatException e) {
            return false;
        }

        if (accountType.equals("banking.SavingsAccount") && ((amount < 0) || (amount > 2500))) {
            return false;
        } else if (accountType.equals("banking.CheckingAccount") && ((amount < 0) || (amount > 1000))) {
            return false;
        } else {
            return !accountType.equals("banking.CdAccount");
        }
    }
}

public class CreateAccountValidator extends CommandValidator {

    CreateAccountValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {

        String[] parsedCommand = command.split(" ");

        if (!command.toLowerCase().contains("cd") && parsedCommand.length != 4) {
            return false;
        } else if (command.toLowerCase().contains("cd") && parsedCommand.length != 5) {
            return false;
        }

        boolean isCdBalanceValid = true;

        if (command.toLowerCase().contains("cd") && parsedCommand.length == 5) {
            isCdBalanceValid = doesCdHaveValidBalance(parsedCommand[4]);
        }

        boolean doesAccountExistInBank = doesAccountAlreadyExistsInTheBank(parsedCommand[2]);
        boolean isCreateKeywordPresent = createKeywordIsPresentInCommand(parsedCommand[0]);
        boolean isAccountTypeValid = super.isAccountTypeValid(parsedCommand[1]);
        boolean isIdValid = super.isIdValid(parsedCommand[2]);
        boolean isAprValid = super.isAprValid(parsedCommand[3]);

        return !doesAccountExistInBank && isCreateKeywordPresent && isAccountTypeValid && isIdValid && isAprValid && isCdBalanceValid;

    }

    private boolean doesCdHaveValidBalance(String cdBalance) {
        double initialBalance;
        try {
            initialBalance = Double.parseDouble(cdBalance);
        } catch (NumberFormatException numberFormatException) {
            return false;
        }

        return !(initialBalance <= 0);
    }

    private boolean createKeywordIsPresentInCommand(String command) {
        return command.toLowerCase().contains("create");
    }

    private boolean doesAccountAlreadyExistsInTheBank(String id) {
        return bank.accountExistsById(id);
    }

}

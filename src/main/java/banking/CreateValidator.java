package banking;

public class CreateValidator extends CommandValidator {

    CreateValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {

        String[] parsedCommand = command.split(" ");

        if ((!command.toLowerCase().contains("cd") && parsedCommand.length != 4) ||
                (command.toLowerCase().contains("cd") && parsedCommand.length != 5)) {
            return false;
        }

        boolean isCdBalanceValid = true;

        if (command.toLowerCase().contains("cd") && parsedCommand.length == 5) {
            isCdBalanceValid = doesCdHaveValidBalance(parsedCommand[4]);
        }

        boolean doesAccountExistInBank = doesAccountAlreadyExistsInTheBank(parsedCommand[2]);
        boolean isCreateKeywordPresent = createKeywordIsPresentInCommand(parsedCommand[0]);
        boolean isAccountTypeValid = super.isAccountTypeValid(parsedCommand[1]);
        boolean isIdValid = isIdValid(parsedCommand[2]);
        boolean isAprValid = super.isAprValid(parsedCommand[3]);

        return !doesAccountExistInBank && isCreateKeywordPresent && isAccountTypeValid && isIdValid && isAprValid && isCdBalanceValid;

    }

    @Override
    public boolean isIdValid(String id) {

        if (id == null || id.length() != 8) {
            return false;
        }

        for (char idChar : id.toCharArray()) {
            if (!Character.isDigit(idChar)) {
                return false;
            }
        }
        return true;
    }

    private boolean doesCdHaveValidBalance(String cdBalance) {
        double initialBalance;
        try {
            initialBalance = Double.parseDouble(cdBalance);
        } catch (NumberFormatException numberFormatException) {
            return false;
        }

        return (initialBalance >= 1000 && initialBalance <= 10000);
    }

    private boolean createKeywordIsPresentInCommand(String command) {
        return command.toLowerCase().contains("create");
    }

    private boolean doesAccountAlreadyExistsInTheBank(String id) {
        return bank.accountExistsById(id);
    }

}

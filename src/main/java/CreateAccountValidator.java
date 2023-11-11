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

        boolean doesAccountExistInBank = does_account_already_exists_in_the_bank(parsedCommand[2]);
        boolean isCreateKeywordPresent = create_keyword_is_present_in_command(parsedCommand[0]);
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

    public boolean create_keyword_is_present_in_command(String command) {
        return command.toLowerCase().contains("create");
    }

    public boolean does_account_already_exists_in_the_bank(String id) {
        return bank.accountExistsById(id);
    }

}

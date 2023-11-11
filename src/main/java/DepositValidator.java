public class DepositValidator extends CommandValidator {
    DepositValidator() {
    }

    @Override
    public boolean validate(String command) {
        String[] parsedCommand = command.split(" ");

        if (parsedCommand.length != 3) {
            return false;
        }

        boolean doesDepositKeywordExist = doesDepositKeywordExistsInCommand(parsedCommand[0]);
        boolean isIdValid = isIdValid(parsedCommand[1]);
        boolean isBalanceValid = isAmountToBeDepositedValid(parsedCommand[2]);

        return doesDepositKeywordExist && isIdValid && isBalanceValid;
    }

    public boolean doesDepositKeywordExistsInCommand(String command) {
        return command.toLowerCase().contains("deposit");
    }

    public boolean isAmountToBeDepositedValid(String balance) {

        double amount;
        try {
            amount = Double.parseDouble(balance);
        } catch (NumberFormatException e) {
            return false;
        }

        return (amount >= 0);
    }
}

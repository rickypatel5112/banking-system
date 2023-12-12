package banking;

public class PassTimeValidator extends CommandValidator {

    public PassTimeValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] parsedCommand = command.split(" ");

        if (parsedCommand.length != 2) {
            return false;
        }

        return doesPassKeywordExistInCommand(parsedCommand[0]) && isMonthValid(parsedCommand[1]);

    }

    private boolean doesPassKeywordExistInCommand(String passKeyword) {
        return passKeyword.equalsIgnoreCase("pass");
    }

    private boolean isMonthValid(String monthString) {
        int month;

        try {
            month = Integer.parseInt(monthString);
        } catch (Exception exception) {
            return false;
        }

        return (month >= 1 && month <= 60);
    }


}

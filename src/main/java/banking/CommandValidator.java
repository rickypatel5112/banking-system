package banking;

public class CommandValidator {
    protected Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {

        CommandValidator validator;

        String[] parsedCommand = command.split(" ");
        String keyword = parsedCommand[0];

        if (keyword.equalsIgnoreCase("create")) {
            validator = new CreateValidator(bank);
        } else if (keyword.equalsIgnoreCase("deposit")) {
            validator = new DepositValidator(bank);
        } else if (keyword.equalsIgnoreCase("transfer")) {
            validator = new TransferValidator(bank);
        } else if (keyword.equalsIgnoreCase("withdraw")) {
            validator = new WithdrawValidator(bank);
        } else if (keyword.equalsIgnoreCase("pass")) {
            validator = new PassTimeValidator(bank);
        } else {
            return false;
        }

        return validator.validate(command);
    }

    public boolean isIdValid(String id) {

        if (id == null || id.length() != 8 || !bank.accountExistsById(id)) {
            return false;
        }

        for (char idChar : id.toCharArray()) {
            if (!Character.isDigit(idChar)) {
                return false;
            }
        }
        return true;
    }

    public boolean isAccountTypeValid(String accountType) {
        if (accountType.equalsIgnoreCase("checking")) {
            return true;
        } else if (accountType.equalsIgnoreCase("savings")) {
            return true;
        } else {
            return accountType.equalsIgnoreCase("cd");
        }
    }

    public boolean isAprValid(String stringApr) {
        double apr;
        try {
            apr = Double.parseDouble(stringApr);
        } catch (NumberFormatException e) {
            return false;
        }

        return !(apr < 0) && !(apr > 10);
    }


}

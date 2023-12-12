package banking;

public class CommandValidator {
    protected Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {

        String[] parsedCommand = command.split(" ");
        String keyword = parsedCommand[0];

        if (keyword.equalsIgnoreCase("create")) {
            CreateValidator createValidator = new CreateValidator(bank);
            return createValidator.validate(command);
        } else if (keyword.equalsIgnoreCase("deposit")) {
            DepositValidator depositValidator = new DepositValidator(bank);
            return depositValidator.validate(command);
        } else if (keyword.equalsIgnoreCase("transfer")) {
            TransferValidator transferValidator = new TransferValidator(bank);
            return transferValidator.validate(command);
        } else if (keyword.equalsIgnoreCase("withdraw")) {
            WithdrawValidator withdrawValidator = new WithdrawValidator(bank);
            return withdrawValidator.validate(command);
        } else if (keyword.equalsIgnoreCase("pass")) {
            PassTimeValidator passTimeValidator = new PassTimeValidator();
            return passTimeValidator.validate(command);
        }

        return false;
    }

    public boolean isIdValid(String id) {

        if (id == null) {
            return false;
        } else if (id.length() != 8) {
            return false;
        } else if (!bank.accountExistsById(id)) {
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

public class CommandValidator {
    Bank bank;

    CommandValidator(Bank bank) {
        this.bank = bank;
    }

    CommandValidator() {
    }

    public boolean validate(String command) {
        return false;
    }

    public boolean isIdValid(String id) {

        if (id == null) {
            return false;
        } else if (id.length() != 8) {
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

package banking;

public class WithdrawValidator extends CommandValidator {

    public WithdrawValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] parsedCommand = command.split(" ");

        if (parsedCommand.length != 3) {
            return false;
        }

        String withdrawKeyword = parsedCommand[0];
        String id = parsedCommand[1];
        String amount = parsedCommand[2];

        if (doesWithdrawKeywordExistInCommand(withdrawKeyword)
                && isIdValid(id)
                && isAmountValid(id, amount)
                && isWithdrawFrequencyValid(id)) {

            bank.getAccount(id).setCanBeWithdrawn(false);

            return true;
        } else {
            return false;
        }
    }

    private boolean isWithdrawFrequencyValid(String id) {

        if (bank.getAccountType(id).equals("banking.CheckingAccount")) {
            return true;
        }

        return bank.getAccount(id).canBeWithdrawn();
    }

    private boolean doesWithdrawKeywordExistInCommand(String withdrawKeyword) {
        return withdrawKeyword.equalsIgnoreCase("Withdraw");
    }

    private boolean isAmountValid(String id, String amountString) {

        double amount;

        try {
            amount = Double.parseDouble(amountString);
        } catch (Exception exception) {
            return false;
        }

        if (bank.getAccountType(id).equals("banking.CheckingAccount")
                && amount >= 0
                && amount <= 400) {
            return true;
        } else if (bank.getAccountType(id).equals("banking.SavingsAccount")
                && amount >= 0
                && amount <= 1000) {
            return true;
        } else {
            return bank.getAccountType(id).equals("banking.CdAccount")
                    && (amount >= bank.getAccount(id).getBalance());
        }
    }
}

package banking;

public class TransferValidator extends CommandValidator {
    public TransferValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] parsedCommand = command.split(" ");

        if (parsedCommand.length != 4) {
            return false;
        }

        String transferKeyword = parsedCommand[0];
        String transferFromAccountId = parsedCommand[1];
        String transferToAccountId = parsedCommand[2];
        String amount = parsedCommand[3];

        return doesTransferKeywordExistInCommand(transferKeyword)
                && areTransferIdsAndAmountValid(transferFromAccountId, transferToAccountId, amount);
    }

    private boolean doesTransferKeywordExistInCommand(String transferKeyword) {
        return transferKeyword.equalsIgnoreCase("transfer");
    }

    private boolean areTransferIdsAndAmountValid(String transferFromId, String transferToId, String amount) {

        if (transferFromId.equals(transferToId)) {
            return false;
        }

        if (bank.accountExistsById(transferFromId) && bank.accountExistsById(transferToId)
                && (bank.getAccountType(transferFromId).equals("banking.CdAccount")
                || bank.getAccountType(transferToId).equals("banking.CdAccount"))) {
            return false;
        }


        DepositValidator depositValidator = new DepositValidator(bank);
        boolean canDeposit = depositValidator.validate("deposit " + transferToId + " " + amount);

        WithdrawValidator withdrawValidator = new WithdrawValidator(bank);
        boolean canWithdraw = withdrawValidator.validate("withdraw " + transferFromId + " " + amount);

        return (canWithdraw && canDeposit);
    }
}

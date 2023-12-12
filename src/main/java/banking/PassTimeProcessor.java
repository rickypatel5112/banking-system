package banking;

import java.util.Iterator;

public class PassTimeProcessor {

    private final Bank bank;

    public PassTimeProcessor(Bank bank) {
        this.bank = bank;
    }

    public void processTime(String command) {

        String[] parsedCommand = command.split(" ");

        int months = Integer.parseInt(parsedCommand[1]);

        for (int i = 0; i < months; i++) {
            accountRemovalOperation();
            minimumBalanceOperation();
            calculateApr();
        }

        setIsWithdrawalPossible(months);
    }

    private void setIsWithdrawalPossible(int months) {

        for (String accountId : bank.getAccounts().keySet()) {

            String accountType = bank.getAccountType(accountId);
            GenericAccount account = bank.getAccount(accountId);

            if (accountType.equals("banking.SavingsAccount") || accountType.equals("banking.CheckingAccount")) {
                account.setCanBeWithdrawn(true);
            } else if (accountType.equals("banking.CdAccount")) {
                account.setAge(months);

                account.setCanBeWithdrawn(account.getAge() >= 12);
            }
        }
    }

    private void accountRemovalOperation() {
        Iterator<String> iterator = bank.getAccounts().keySet().iterator();

        while (iterator.hasNext()) {
            String accountId = iterator.next();
            double balance = bank.getAccount(accountId).getBalance();

            if (balance == 0) {
                iterator.remove();
            }
        }
    }

    private void minimumBalanceOperation() {
        Iterator<String> iterator = bank.getAccounts().keySet().iterator();
        while (iterator.hasNext()) {
            String accountId = iterator.next();
            double balance = bank.getAccount(accountId).getBalance();

            if (balance < 100) {
                bank.getAccount(accountId).withdrawMoney(25);

            }
        }
    }

    private void calculateApr() {

        for (String accountId : bank.getAccounts().keySet()) {
            double apr = bank.getAccount(accountId).getApr();

            int numOfLoops = 1;

            if (bank.getAccountType(accountId).equals("banking.CdAccount")) {
                numOfLoops = 4;
            }

            for (int j = 0; j < numOfLoops; j++) {
                double balance = bank.getAccount(accountId).getBalance();
                double interest = ((apr / 100) / 12) * balance;
                bank.depositMoneyByID(accountId, interest);
            }
        }
    }


}

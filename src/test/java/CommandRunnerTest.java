import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandRunnerTest {

    private static final String ACCOUNT_ID = "59593050";
    private static final String ACCOUNT_APR = "4.3";
    private static final String ACCOUNT_BALANCE = "400";
    private Bank bank;
    private CommandRunner commandRunner;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        commandRunner = new CommandRunner(bank);
    }

    @Test
    public void testing_run_command_for_checking_account() {
        commandRunner.runCommand("create checking " + ACCOUNT_ID + " " + ACCOUNT_APR);

        double checkingAccountApr = bank.getAccount(ACCOUNT_ID).getApr();

        assertTrue(bank.accountExistsById(ACCOUNT_ID));
        assertEquals(checkingAccountApr, Double.parseDouble(ACCOUNT_APR));
    }

    @Test
    public void testing_run_command_for_savings_account() {
        commandRunner.runCommand("create savings " + ACCOUNT_ID + " " + ACCOUNT_APR);

        double savingsAccountApr = bank.getAccount(ACCOUNT_ID).getApr();

        assertTrue(bank.accountExistsById(ACCOUNT_ID));
        assertEquals(savingsAccountApr, Double.parseDouble(ACCOUNT_APR));
    }

    @Test
    public void testing_run_command_for_cd_account() {
        commandRunner.runCommand("create cd " + ACCOUNT_ID + " " + ACCOUNT_APR + " " + ACCOUNT_BALANCE);

        double cdAccountApr = bank.getAccount(ACCOUNT_ID).getApr();
        double cdBalance = bank.getAccount(ACCOUNT_ID).getBalance();

        assertTrue(bank.accountExistsById(ACCOUNT_ID));
        assertEquals(cdAccountApr, Double.parseDouble(ACCOUNT_APR));
        assertEquals(cdBalance, Double.parseDouble(ACCOUNT_BALANCE));
    }

    @Test
    public void testing_run_command_for_depositing_in_a_new_checking_account() {

        GenericAccount checkingAccount = new CheckingAccount(Double.parseDouble(ACCOUNT_APR), ACCOUNT_ID);
        bank.addAccount(checkingAccount);
        commandRunner.runCommand("deposit " + ACCOUNT_ID + " 300");

        assertEquals(checkingAccount.getBalance(), 300);
    }

    @Test
    public void testing_run_command_for_depositing_in_a_new_savings_account() {

        GenericAccount savingsAccount = new SavingsAccount(Double.parseDouble(ACCOUNT_APR), ACCOUNT_ID);
        bank.addAccount(savingsAccount);
        commandRunner.runCommand("deposit " + ACCOUNT_ID + " 300");

        assertEquals(savingsAccount.getBalance(), 300);
    }

    @Test
    public void testing_run_command_for_depositing_in_a_cd_account() {

        GenericAccount cdAccount = new CdAccount(Double.parseDouble(ACCOUNT_APR), ACCOUNT_ID, 200);
        bank.addAccount(cdAccount);
        commandRunner.runCommand("deposit " + ACCOUNT_ID + " 300");

        assertEquals(cdAccount.getBalance(), 500);
    }

    @Test
    public void testing_run_command_for_depositing_money_in_an_already_existing_checking_account_with_balance_greater_than_0() {

        GenericAccount checkingAccount = new CheckingAccount(Double.parseDouble(ACCOUNT_APR), ACCOUNT_ID);
        bank.addAccount(checkingAccount);
        checkingAccount.depositMoney(200);

        commandRunner.runCommand("deposit " + ACCOUNT_ID + " 300");

        assertEquals(checkingAccount.getBalance(), 500);
    }

    @Test
    public void testing_run_command_for_depositing_money_in_an_already_existing_savings_account_with_balance_greater_than_0() {

        GenericAccount savingsAccount = new SavingsAccount(Double.parseDouble(ACCOUNT_APR), ACCOUNT_ID);
        bank.addAccount(savingsAccount);
        savingsAccount.depositMoney(200);

        commandRunner.runCommand("deposit " + ACCOUNT_ID + " 300");

        assertEquals(savingsAccount.getBalance(), 500);
    }
}

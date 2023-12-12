package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandProcessorTest {

    private static final String ACCOUNT_ID = "59593050";
    private static final String ACCOUNT_APR = "4.3";
    private static final String ACCOUNT_BALANCE = "400";
    private Bank bank;
    private CommandProcessor commandProcessor;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    public void testing_create_command_for_checking_account() {
        commandProcessor.runCommand("create checking " + ACCOUNT_ID + " " + ACCOUNT_APR);

        double checkingAccountApr = bank.getAccount(ACCOUNT_ID).getApr();

        assertTrue(bank.accountExistsById(ACCOUNT_ID));
        assertEquals(checkingAccountApr, Double.parseDouble(ACCOUNT_APR));
    }

    @Test
    public void testing_create_command_for_savings_account() {
        commandProcessor.runCommand("create savings " + ACCOUNT_ID + " " + ACCOUNT_APR);

        double savingsAccountApr = bank.getAccount(ACCOUNT_ID).getApr();

        assertTrue(bank.accountExistsById(ACCOUNT_ID));
        assertEquals(savingsAccountApr, Double.parseDouble(ACCOUNT_APR));
    }

    @Test
    public void testing_create_command_for_cd_account() {
        commandProcessor.runCommand("create cd " + ACCOUNT_ID + " " + ACCOUNT_APR + " " + ACCOUNT_BALANCE);

        double cdAccountApr = bank.getAccount(ACCOUNT_ID).getApr();
        double cdBalance = bank.getAccount(ACCOUNT_ID).getBalance();

        assertTrue(bank.accountExistsById(ACCOUNT_ID));
        assertEquals(cdAccountApr, Double.parseDouble(ACCOUNT_APR));
        assertEquals(cdBalance, Double.parseDouble(ACCOUNT_BALANCE));
    }

    @Test
    public void testing_run_command_for_depositing_in_a_new_checking_account() {

        GenericAccount checkingAccount = new CheckingAccount(ACCOUNT_ID, Double.parseDouble(ACCOUNT_APR));
        bank.addAccount(checkingAccount);
        commandProcessor.runCommand("deposit " + ACCOUNT_ID + " 3000");

        assertEquals(checkingAccount.getBalance(), 3000);
    }

    @Test
    public void testing_run_command_for_depositing_in_a_new_savings_account() {

        GenericAccount savingsAccount = new SavingsAccount(ACCOUNT_ID, Double.parseDouble(ACCOUNT_APR));
        bank.addAccount(savingsAccount);
        commandProcessor.runCommand("deposit " + ACCOUNT_ID + " 300");

        assertEquals(savingsAccount.getBalance(), 300);
    }

    @Test
    public void testing_run_command_for_depositing_in_a_cd_account() {

        GenericAccount cdAccount = new CdAccount(ACCOUNT_ID, Double.parseDouble(ACCOUNT_APR), 200);
        bank.addAccount(cdAccount);
        commandProcessor.runCommand("deposit " + ACCOUNT_ID + " 300");

        assertEquals(cdAccount.getBalance(), 500);
    }

    @Test
    public void testing_run_command_for_depositing_money_in_an_already_existing_checking_account_with_balance_greater_than_0() {

        GenericAccount checkingAccount = new CheckingAccount(ACCOUNT_ID, Double.parseDouble(ACCOUNT_APR));
        bank.addAccount(checkingAccount);
        checkingAccount.depositMoney(200);

        commandProcessor.runCommand("deposit " + ACCOUNT_ID + " 300");

        assertEquals(checkingAccount.getBalance(), 500);
    }

    @Test
    public void testing_run_command_for_depositing_money_in_an_already_existing_savings_account_with_balance_greater_than_0() {

        GenericAccount savingsAccount = new SavingsAccount(ACCOUNT_ID, Double.parseDouble(ACCOUNT_APR));
        bank.addAccount(savingsAccount);
        savingsAccount.depositMoney(200);

        commandProcessor.runCommand("deposit " + ACCOUNT_ID + " 300");

        assertEquals(savingsAccount.getBalance(), 500);
    }

    @Test
    public void testing_run_command_for_transferring_money_from_a_checking_account_to_a_checking_account() {

        GenericAccount senderCheckingAccount = new CheckingAccount("12345678", 5.7);
        GenericAccount receiverCheckingAccount = new CheckingAccount("12345679", 5.7);

        senderCheckingAccount.depositMoney(1100);
        senderCheckingAccount.depositMoney(900);

        bank.addAccount(senderCheckingAccount);
        bank.addAccount(receiverCheckingAccount);

        commandProcessor.runCommand("transfer 12345678 12345679 1500");

        assertEquals(senderCheckingAccount.getBalance(), 500);
        assertEquals(receiverCheckingAccount.getBalance(), 1500);

    }

    @Test
    public void testing_run_command_for_transferring_money_from_a_checking_account_to_a_checking_account_but_the_amount_is_greater_than_withdrawing_checking_account() {

        GenericAccount senderCheckingAccount = new CheckingAccount("12345678", 5.7);
        GenericAccount receiverCheckingAccount = new CheckingAccount("12345679", 5.7);

        senderCheckingAccount.depositMoney(100);

        bank.addAccount(senderCheckingAccount);
        bank.addAccount(receiverCheckingAccount);

        commandProcessor.runCommand("transfer 12345678 12345679 200");

        assertEquals(senderCheckingAccount.getBalance(), 0);
        assertEquals(receiverCheckingAccount.getBalance(), 100);

    }

    @Test
    public void testing_run_command_for_transferring_money_from_a_savings_account_to_a_savings_account() {

        GenericAccount senderSavingsAccount = new SavingsAccount("12345678", 5.7);
        GenericAccount receiverSavingsAccount = new SavingsAccount("12345679", 5.7);

        senderSavingsAccount.depositMoney(300);

        bank.addAccount(senderSavingsAccount);
        bank.addAccount(receiverSavingsAccount);

        commandProcessor.runCommand("transfer 12345678 12345679 250");

        assertEquals(senderSavingsAccount.getBalance(), 50);
        assertEquals(receiverSavingsAccount.getBalance(), 250);
    }

    @Test
    public void testing_run_command_for_transferring_money_from_a_savings_account_to_a_savings_account_but_the_amount_is_greater_than_withdrawing_sasvings_account() {

        GenericAccount senderSavingsAccount = new SavingsAccount("12345678", 5.7);
        GenericAccount receiverSavingsAccount = new SavingsAccount("12345679", 5.7);

        senderSavingsAccount.depositMoney(200);

        bank.addAccount(senderSavingsAccount);
        bank.addAccount(receiverSavingsAccount);

        commandProcessor.runCommand("transfer 12345678 12345679 250");

        assertEquals(senderSavingsAccount.getBalance(), 0);
        assertEquals(receiverSavingsAccount.getBalance(), 200);
    }

    @Test
    public void testing_withdrawal_for_a_checking_account() {
        GenericAccount checkingAccount = new CheckingAccount("12345678", 3.2);
        checkingAccount.depositMoney(250);

        bank.addAccount(checkingAccount);

        commandProcessor.runCommand("Withdraw 12345678 100");

        assertEquals(150, checkingAccount.getBalance());
    }

    @Test
    public void testing_withdrawal_for_a_savings_account() {
        GenericAccount savingsAccount = new SavingsAccount("12345678", 3.2);
        savingsAccount.depositMoney(250);

        bank.addAccount(savingsAccount);

        commandProcessor.runCommand("Withdraw 12345678 100");

        assertEquals(150, savingsAccount.getBalance());
    }

    @Test
    public void testing_pass_time_command_for_all_types_of_accounts() {
        GenericAccount checkingAccount = new CheckingAccount("12345678", 1.2);
        GenericAccount savingsAccount = new SavingsAccount("12345679", 1.2);
        GenericAccount cdAccount = new CdAccount("12345670", 1.2, 1500);

        checkingAccount.depositMoney(500);
        savingsAccount.depositMoney(500);

        bank.addAccount(checkingAccount);
        bank.addAccount(savingsAccount);
        bank.addAccount(cdAccount);

        commandProcessor.runCommand("Pass 2");

        List<Double> actual = new ArrayList<>();

        actual.add(checkingAccount.getBalance());
        actual.add(savingsAccount.getBalance());
        actual.add(cdAccount.getBalance());

        List<Double> expected = new ArrayList<>();

        expected.add(501.0005);
        expected.add(501.0005);
        expected.add(1512.042084105084);

        assertEquals(expected, actual);

    }

}

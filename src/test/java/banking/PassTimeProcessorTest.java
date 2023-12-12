package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PassTimeProcessorTest {

    private Bank bank;
    private PassTimeProcessor passTimeProcessor;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        passTimeProcessor = new PassTimeProcessor(bank);
    }

    @Test
    public void removing_checking_account_with_0_balance() {
        GenericAccount checkingAccount = new CheckingAccount("12345678", 4.5);
        bank.addAccount(checkingAccount);

        passTimeProcessor.processTime("Pass 1");

        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void removing_savings_account_with_0_balance() {
        GenericAccount savingsAccount = new SavingsAccount("12345678", 4.5);
        bank.addAccount(savingsAccount);

        passTimeProcessor.processTime("Pass 1");

        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void removing_cd_account_with_0_balance() {
        GenericAccount cdAccount = new CdAccount("12345678", 4.5, 1500);
        cdAccount.withdrawMoney(1500);

        bank.addAccount(cdAccount);

        passTimeProcessor.processTime("Pass 1");

        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void removing_two_checking_accounts_with_0_balance() {
        GenericAccount checkingAccount1 = new CheckingAccount("12345678", 4.5);
        GenericAccount checkingAccount2 = new CheckingAccount("12345677", 4.5);

        bank.addAccount(checkingAccount1);
        bank.addAccount(checkingAccount2);

        passTimeProcessor.processTime("Pass 1");

        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void removing_two_savings_accounts_with_0_balance() {
        GenericAccount savingsAccount1 = new SavingsAccount("12345678", 4.5);
        GenericAccount savingsAccount2 = new SavingsAccount("12345677", 4.5);

        bank.addAccount(savingsAccount1);
        bank.addAccount(savingsAccount2);

        passTimeProcessor.processTime("Pass 1");

        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void removing_two_cd_accounts_with_0_balance() {
        GenericAccount cdAccount1 = new CdAccount("12345678", 4.5, 1509);
        GenericAccount cdAccount2 = new CdAccount("12345677", 4.5, 1509);

        bank.addAccount(cdAccount1);
        bank.addAccount(cdAccount2);

        cdAccount1.withdrawMoney(1509);
        cdAccount2.withdrawMoney(1509);

        passTimeProcessor.processTime("Pass 1");

        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void trying_to_remove_checking_account_with_non_zero_balance() {
        GenericAccount checkingAccount = new CheckingAccount("12345678", 3.4);
        checkingAccount.depositMoney(500);

        bank.addAccount(checkingAccount);

        passTimeProcessor.processTime("Pass 2");

        assertFalse(bank.getAccounts().isEmpty());
        assertEquals(bank.getAccount("12345678"), checkingAccount);
    }

    @Test
    public void trying_to_remove_savings_account_with_non_zero_balance() {
        GenericAccount savingsAccount = new SavingsAccount("12345678", 3.4);
        savingsAccount.depositMoney(2500);

        bank.addAccount(savingsAccount);

        passTimeProcessor.processTime("Pass 2");

        assertFalse(bank.getAccounts().isEmpty());
        assertEquals(bank.getAccount("12345678"), savingsAccount);
    }

    @Test
    public void trying_to_remove_cd_account_with_non_zero_balance() {
        GenericAccount cdAccount = new CdAccount("12345678", 3.4, 1500);
        cdAccount.depositMoney(1500);

        bank.addAccount(cdAccount);

        passTimeProcessor.processTime("Pass 2");

        assertFalse(bank.getAccounts().isEmpty());
        assertEquals(bank.getAccount("12345678"), cdAccount);
    }

    @Test
    public void trying_to_remove_checking_account_with_0_balance_with_a_pre_existing_savings_account_with_non_zero_balance() {
        GenericAccount savingsAccount1 = new SavingsAccount("12345678", 4.5);
        GenericAccount savingsAccount2 = new SavingsAccount("12345679", 4.5);
        GenericAccount checkingAccount = new CheckingAccount("12345670", 4.5);

        savingsAccount1.depositMoney(300);
        savingsAccount2.depositMoney(600);

        bank.addAccount(savingsAccount1);
        bank.addAccount(checkingAccount);
        bank.addAccount(savingsAccount2);

        passTimeProcessor.processTime("pass 12");

        assertEquals(bank.getAccounts().size(), 2);
        assertFalse(bank.accountExistsById("12345670"));

    }

    @Test
    public void deducting_minimum_amount_fee_for_1_month_from_a_checking_account() {
        GenericAccount checkingAccount = new CheckingAccount("12345678", 5.4);
        checkingAccount.depositMoney(40);

        bank.addAccount(checkingAccount);

        passTimeProcessor.processTime("pass 1");

        assertEquals(15.0675, checkingAccount.getBalance());

    }

    @Test
    public void deducting_minimum_amount_fee_for_1_month_from_a_savings_account() {
        GenericAccount savingsAccount = new SavingsAccount("12345678", 5.4);
        savingsAccount.depositMoney(40);

        bank.addAccount(savingsAccount);

        passTimeProcessor.processTime("pass 1");

        assertEquals(15.0675, savingsAccount.getBalance());

    }

    @Test
    public void passing_3_months_on_a_checking_account_with_$70_balance() {
        GenericAccount checkingAccount = new CheckingAccount("12345678", 0.5);
        checkingAccount.depositMoney(70);

        bank.addAccount(checkingAccount);

        passTimeProcessor.processTime("Pass 3");

        assertTrue(bank.accountExistsById("12345678"));
        assertEquals(checkingAccount.getBalance(), 0);
    }

    @Test
    public void passing_3_months_on_a_savings_account_with_$70_balance() {
        GenericAccount savingsAccount = new SavingsAccount("12345678", 0.5);
        savingsAccount.depositMoney(70);

        bank.addAccount(savingsAccount);

        passTimeProcessor.processTime("Pass 3");

        assertEquals(savingsAccount.getBalance(), 0);
    }

    @Test
    public void apr_calculation_of_a_checking_account_of_1_month_with_$1000_balance_and_3_as_apr() {
        GenericAccount checkingAccount = new CheckingAccount("12345678", 3.0);

        checkingAccount.depositMoney(1000);
        bank.addAccount(checkingAccount);

        passTimeProcessor.processTime("Pass 1");

        assertEquals(bank.getAccount("12345678").getBalance(), 1002.50);
    }

    @Test
    public void apr_calculation_of_a_savings_account_of_1_month_with_$1000_balance_and_3_as_apr() {
        GenericAccount savingsAccount = new SavingsAccount("12345678", 3.0);

        savingsAccount.depositMoney(1000);
        bank.addAccount(savingsAccount);

        passTimeProcessor.processTime("Pass 1");

        assertEquals(bank.getAccount("12345678").getBalance(), 1002.50);
    }

    @Test
    public void apr_calculation_of_a_cd_account_with_$2000_balance_and_2_point_1_apr() {
        GenericAccount cdAccount = new CdAccount("12345678", 2.1, 2000);
        bank.addAccount(cdAccount);

        passTimeProcessor.processTime("Pass 1");

        assertEquals(cdAccount.getBalance(), 2014.0367928937578);
    }

    @Test
    public void apr_calculation_of_two_checking_accounts() {
        GenericAccount checkingAccount1 = new CheckingAccount("12345678", 3.0);
        GenericAccount checkingAccount2 = new CheckingAccount("12345679", 3.6);

        checkingAccount1.depositMoney(1000);
        checkingAccount2.depositMoney(1600);
        bank.addAccount(checkingAccount1);
        bank.addAccount(checkingAccount2);

        passTimeProcessor.processTime("Pass 1");

        assertEquals(bank.getAccount("12345678").getBalance(), 1002.50);
        assertEquals(bank.getAccount("12345679").getBalance(), 1604.80);
    }

    @Test
    public void apr_calculation_of_two_savings_accounts() {
        GenericAccount checkingAccount1 = new CheckingAccount("12345678", 3.0);
        GenericAccount checkingAccount2 = new CheckingAccount("12345679", 3.6);

        checkingAccount1.depositMoney(1000);
        checkingAccount2.depositMoney(1600);
        bank.addAccount(checkingAccount1);
        bank.addAccount(checkingAccount2);

        passTimeProcessor.processTime("Pass 2");

        assertEquals(bank.getAccount("12345678").getBalance(), 1005.00625);
        assertEquals(bank.getAccount("12345679").getBalance(), 1609.6144);
    }

    @Test
    public void checking_if_after_1_month_of_time_withdrawal_is_possible_for_a_checking_account() {

        GenericAccount checkingAccount = new CheckingAccount("12345678", 3.4);
        checkingAccount.depositMoney(500);

        bank.addAccount(checkingAccount);

        passTimeProcessor.processTime("Pass 1");

        assertTrue(checkingAccount.canBeWithdrawn());
    }

    @Test
    public void checking_if_after_12_months_of_time_withdrawal_is_possible_for_a_checking_account() {

        GenericAccount checkingAccount = new CheckingAccount("12345678", 3.4);
        checkingAccount.depositMoney(500);

        bank.addAccount(checkingAccount);

        passTimeProcessor.processTime("Pass 12");

        assertTrue(checkingAccount.canBeWithdrawn());
    }

    @Test
    public void checking_if_after_1_month_of_time_withdrawal_is_possible_for_a_savings_account() {

        GenericAccount savingsAccount = new SavingsAccount("12345678", 3.4);
        savingsAccount.depositMoney(500);

        bank.addAccount(savingsAccount);

        passTimeProcessor.processTime("Pass 1");

        assertTrue(savingsAccount.canBeWithdrawn());
    }

    @Test
    public void checking_if_after_12_months_of_time_withdrawal_is_possible_for_a_savings_account() {

        GenericAccount savingsAccount = new SavingsAccount("12345678", 3.4);
        savingsAccount.depositMoney(500);

        bank.addAccount(savingsAccount);

        passTimeProcessor.processTime("Pass 12");

        assertTrue(savingsAccount.canBeWithdrawn());
    }

    @Test
    public void checking_if_after_1_month_of_time_withdrawal_is_possible_for_a_cd_account() {

        GenericAccount cdAccount = new CdAccount("12345678", 3.4, 1500);
        cdAccount.depositMoney(500);

        bank.addAccount(cdAccount);

        passTimeProcessor.processTime("Pass 1");

        assertFalse(cdAccount.canBeWithdrawn());
    }

    @Test
    public void checking_if_after_12_months_of_time_withdrawal_is_possible_for_a_cd_account() {

        GenericAccount cdAccount = new CdAccount("12345678", 3.4, 1500);
        cdAccount.depositMoney(500);

        bank.addAccount(cdAccount);

        passTimeProcessor.processTime("Pass 12");

        assertTrue(cdAccount.canBeWithdrawn());
    }


}

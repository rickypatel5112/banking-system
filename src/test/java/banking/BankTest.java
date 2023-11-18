package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {

    private final String ID = "12673";
    private final double APR = 4.5;
    Bank bank;
    GenericAccount checkingAccount;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        checkingAccount = new CheckingAccount(APR, ID);
    }

    @Test
    public void initially_bank_has_no_accounts() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void one_account_is_added_to_the_bank() {
        bank.addAccount(checkingAccount);

        assertEquals(1, bank.getAccounts().size());
    }

    @Test
    public void two_accounts_are_added_to_the_bank() {
        bank.addAccount(checkingAccount);
        bank.addAccount(new CheckingAccount(7.2, "6747654"));

        assertEquals(2, bank.getAccounts().size());
    }

    @Test
    public void retrieving_one_account_retrieves_the_correct_account() {
        bank.addAccount(checkingAccount);
        GenericAccount retrievedAccount = bank.getAccount(ID);

        assertEquals(checkingAccount, retrievedAccount);
    }

    @Test
    public void depositing_money_by_id_through_bank_adds_money_to_the_correct_account() {
        bank.addAccount(checkingAccount);
        bank.depositMoneyByID(ID, 50.0);

        assertEquals(50, checkingAccount.getBalance());
    }

    @Test
    public void withdrawing_money_by_id_through_bank_withdraws_money_from_the_correct_account() {
        checkingAccount.depositMoney(50);
        bank.addAccount(checkingAccount);
        bank.withdrawMoneyByID(ID, 20);

        assertEquals(30, checkingAccount.getBalance());
    }

    @Test
    public void depositing_twice_through_the_bank_works_as_expected() {
        bank.addAccount(checkingAccount);

        bank.depositMoneyByID(ID, 10.50);
        bank.depositMoneyByID(ID, 10.50);

        assertEquals(21, checkingAccount.getBalance());
    }

    @Test
    public void withdrawing_twice_through_the_bank_works_as_expected() {
        bank.addAccount(checkingAccount);

        bank.depositMoneyByID(ID, 50);
        bank.withdrawMoneyByID(ID, 10);
        bank.withdrawMoneyByID(ID, 10);

        assertEquals(30, checkingAccount.getBalance());
    }

}

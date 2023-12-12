package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericAccountTest {
    public static final double INITIAL_BALANCE = 500.0;
    private final String CHECKING_ID = "12345";
    private final String SAVINGS_ID = "91945";
    private final String CD_ID = "58359";
    private final double APR = 3.5;
    GenericAccount checkingAccount;

    @BeforeEach
    public void setUp() {
        checkingAccount = new CheckingAccount(CHECKING_ID, APR);
    }

    @Test
    public void account_has_supplied_APR() {
        assertEquals(3.5, checkingAccount.getApr());
    }

    @Test
    public void depositing_money_increases_balance_by_the_amount_deposited_in_account() {
        checkingAccount.depositMoney(51.30);

        assertEquals(51.30, checkingAccount.getBalance());
    }

    @Test
    public void withdrawing_money_decreases_balance_by_the_amount_withdrawn_in_account() {
        checkingAccount.depositMoney(60);
        checkingAccount.withdrawMoney(15);

        assertEquals(45, checkingAccount.getBalance());
    }

    @Test
    public void when_withdrawing_balance_does_not_go_below_zero_in_account() {
        checkingAccount.depositMoney(30);
        checkingAccount.withdrawMoney(40);

        assertEquals(0, checkingAccount.getBalance());
    }

    @Test
    public void withdrawing_amount_is_more_than_balance_in_account() {
        checkingAccount.depositMoney(10);
        checkingAccount.withdrawMoney(50);

        assertEquals(0, checkingAccount.getBalance());
    }

    @Test
    public void withdrawing_amount_is_equal_to_the_balance_in_account() {
        checkingAccount.depositMoney(10);
        checkingAccount.withdrawMoney(10);

        assertEquals(0, checkingAccount.getBalance());
    }


    @Test
    public void depositing_twice_in_same_account_works_as_expected() {
        checkingAccount.depositMoney(10.01);
        checkingAccount.depositMoney(100.00);

        assertEquals(110.01, checkingAccount.getBalance());
    }

    @Test
    public void withdrawing_twice_from_same_account_works_as_expected() {
        checkingAccount.depositMoney(40);
        checkingAccount.withdrawMoney(2);
        checkingAccount.withdrawMoney(30);

        assertEquals(8, checkingAccount.getBalance());
    }

}

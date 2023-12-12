package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsAccountTest {

    private final String ID = "38238";
    private final double APR = 5.3;
    SavingsAccount savingsAccount;

    @BeforeEach
    public void setUp() {
        savingsAccount = new SavingsAccount(ID, APR);
    }

    @Test
    public void savings_account_starting_balance_is_zero() {
        assertEquals(0, savingsAccount.getBalance());
    }
}

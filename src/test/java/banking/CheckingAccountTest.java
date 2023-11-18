package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingAccountTest {
    public static final String ID = "12345678";
    public static final double APR = 3.5;
    GenericAccount checkingAccount;

    @BeforeEach
    public void setUp() {
        checkingAccount = new CheckingAccount(APR, ID);
    }

    @Test
    public void checking_account_starting_balance_is_zero() {
        assertEquals(0, checkingAccount.getBalance());
    }

}

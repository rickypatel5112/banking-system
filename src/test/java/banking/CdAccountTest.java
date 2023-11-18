package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CdAccountTest {
    private final double APR = 3.5;
    private final String CD_ID = "345812";
    private final double BALANCE = 500.0;
    CdAccount cdAccount;

    @BeforeEach
    public void setUp() {
        cdAccount = new CdAccount(APR, CD_ID, BALANCE);
    }

    @Test
    public void cd_account_starting_balance_is_whatever_balance_was_supplied() {
        assertEquals(BALANCE, cdAccount.getBalance());
    }
}

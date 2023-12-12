package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateProcessorTest {

    private CreateProcessor createProcessor;
    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        createProcessor = new CreateProcessor(bank);
    }

    @Test
    public void creating_a_checking_account() {

        createProcessor.create("Create checking 12345678 4.5");

        boolean actual = bank.accountExistsById("12345678");
        assertTrue(actual);
    }

    @Test
    public void creating_a_savings_account() {

        createProcessor.create("Create savings 12345678 4.5");

        boolean actual = bank.accountExistsById("12345678");
        assertTrue(actual);
    }

    @Test
    public void creating_a_cd_account() {

        createProcessor.create("Create cd 12345678 4.5 500");

        boolean actual = bank.accountExistsById("12345678");
        assertTrue(actual);
    }

    @Test
    public void creating_two_checking_accounts() {
        createProcessor.create("Create checking 12345679 4.5");
        createProcessor.create("Create checking 12345678 4.5");

        int actual = bank.getAccounts().size();
        assertEquals(2, actual);
    }

    @Test
    public void creating_two_savings_accounts() {
        createProcessor.create("Create savings 12345678 4.5");
        createProcessor.create("Create savings 12345679 4.5");

        int actual = bank.getAccounts().size();
        assertEquals(2, actual);
    }

    @Test
    public void creating_two_cd_accounts() {
        createProcessor.create("Create cd 12345679 4.5 1300");
        createProcessor.create("Create cd 12345678 4.5 1200");

        int actual = bank.getAccounts().size();
        assertEquals(2, actual);
    }
}

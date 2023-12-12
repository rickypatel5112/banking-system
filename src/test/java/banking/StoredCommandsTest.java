package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoredCommandsTest {
    private StoredCommands storedCommands;
    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        storedCommands = new StoredCommands(bank);
    }

    @Test
    public void storing_a_transactional_command_for_checking_account() {

        GenericAccount checkingAccount = new CheckingAccount("12345678", 2.3);
        bank.addAccount(checkingAccount);

        storedCommands.storeTransactionalCommand("deposit 12345678 300");

        List<String> expected = Arrays.asList("Checking 12345678 0.00 2.30", "deposit 12345678 300");

        List<String> actual = storedCommands.getOutputList();

        assertEquals(expected, actual);
    }

    @Test
    public void storing_a_transactional_command_for_savings_account() {

        GenericAccount savingsAccount = new SavingsAccount("12345678", 2.3);
        bank.addAccount(savingsAccount);

        storedCommands.storeTransactionalCommand("deposit 12345678 300");

        List<String> expected = Arrays.asList("Savings 12345678 0.00 2.30", "deposit 12345678 300");

        List<String> actual = storedCommands.getOutputList();

        assertEquals(expected, actual);
    }

    @Test
    public void storing_a_transactional_command_for_cd_account() {

        GenericAccount cdAccount = new CdAccount("12345678", 2.3, 1500);
        bank.addAccount(cdAccount);

        PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
        passTimeProcessor.processTime("Pass 12");

        storedCommands.storeTransactionalCommand("Withdraw 12345678 2000");

        List<String> expected = Arrays.asList("Cd 12345678 1644.40 2.30", "Withdraw 12345678 2000");

        List<String> actual = storedCommands.getOutputList();

        assertEquals(expected, actual);
    }

    @Test
    public void store_an_invalid_command_in_the_list() {
        storedCommands.storeInvalidCommand("Create checking 333");

        String actual = storedCommands.getOutputList().get(0);
        assertEquals("Create checking 333", actual);
    }

    @Test
    public void store_two_invalid_commands_in_the_list() {
        storedCommands.storeInvalidCommand("Create checking 333");
        storedCommands.storeInvalidCommand("Create csavings 333");

        List<String> expected = Arrays.asList("Create checking 333", "Create csavings 333");

        List<String> actual = storedCommands.getOutputList();
        assertEquals(expected, actual);
    }


    @Test
    public void testing_if_the_order_of_accounts_stored_is_same_as_the_order_of_accounts_stored_in_bank() {
        GenericAccount checkingAccount1 = new CheckingAccount("12345678", 3.4);
        GenericAccount checkingAccount2 = new CheckingAccount("12345679", 4.4);

        bank.addAccount(checkingAccount1);
        bank.addAccount(checkingAccount2);

        GenericAccount savingsAccount1 = new SavingsAccount("46463627", 1.2);
        GenericAccount savingsAccount2 = new SavingsAccount("46463127", 1.1);
        GenericAccount savingsAccount3 = new SavingsAccount("46463347", 3.4);

        bank.addAccount(savingsAccount1);
        bank.addAccount(savingsAccount2);
        bank.addAccount(savingsAccount3);

        List<String> actual = new ArrayList<>();

        for (Map.Entry<String, GenericAccount> entry : bank.getAccounts().entrySet()) {
            actual.add(entry.getKey());
        }

        List<String> expected = new ArrayList<>();

        expected.add("12345678");
        expected.add("12345679");
        expected.add("46463627");
        expected.add("46463127");
        expected.add("46463347");

        assertEquals(actual, expected);

    }


}

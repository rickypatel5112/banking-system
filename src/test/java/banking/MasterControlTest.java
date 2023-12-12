package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MasterControlTest {

    MasterControl masterControl;
    List<String> input;

    @BeforeEach
    public void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new StoredCommands(bank));
    }

    @Test
    public void input_list_is_empty() {

        List<String> actual = masterControl.start(input);

        assertTrue(actual.isEmpty());
    }

    @Test
    void typo_in_create_command_is_invalid() {
        input.add("creat checking 12345678 1.4");

        List<String> actual = masterControl.start(input);
        assertSingleCommand("creat checking 12345678 1.4", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);
        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @Test
    void two_typo_commands_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        List<String> expected = Arrays.asList("creat checking 12345678 1.0", "depositt 12345678 100");

        assertEquals(expected, actual);
    }

    @Test
    void invalid_to_create_accounts_with_same_ID() {
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        List<String> expected = Arrays.asList("Checking 12345678 0.00 1.00", "create checking 12345678 1.0");

        assertEquals(expected, actual);
    }

    @Test
    void depositing_more_than_$1000_in_a_checking_account() {
        input.add("create checking 12345678 1.3");
        input.add("deposit 12345678 1300");

        List<String> actual = masterControl.start(input);
        List<String> expected = Arrays.asList("Checking 12345678 0.00 1.30", "deposit 12345678 1300");

        assertEquals(expected, actual);
    }

    @Test
    void depositing_$1000_in_a_checking_account() {
        input.add("create checking 12345678 1.3");
        input.add("deposit 12345678 1000");

        List<String> actual = masterControl.start(input);
        List<String> expected = Arrays.asList("Checking 12345678 1000.00 1.30", "deposit 12345678 1000");

        assertEquals(expected, actual);
    }

    @Test
    public void sample_blackboard_test_case() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");

        List<String> actual = masterControl.start(input);

        List<String> expected = Arrays.asList("Savings 12345678 1000.50 0.60", "Deposit 12345678 700",
                "Transfer 98765432 12345678 300", "Cd 23456789 2000.00 1.20", "Deposit 12345678 5000");

        assertEquals(actual, expected);

    }

    @Test
    public void document_provided_test_case() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");

        List<String> actual = masterControl.start(input);

        List<String> expected = Arrays.asList("Savings 12345678 1000.50 0.60", "Deposit 12345678 700", "Transfer 98765432 12345678 300", "Cd 23456789 2000.00 1.20", "Deposit 12345678 5000");

        assertEquals(expected, actual);

    }

    @Test
    public void multiple_commands_for_savings_account() {
        input.add("create savings 12345678 2.4");
        input.add("deposit 12345678 2000");
        input.add("withdraw 12345678 250");
        input.add("PasS 1");
        input.add("Transfer 56566654 12345678 78");
        input.add("Passs 2");
        input.add("withdraw 12345678 200");
        input.add("pass 1");
        input.add("withdraw 12345678 10");
        input.add("withdraw 12345678 150");
        input.add("pass 2");
        input.add("withdraw 12345678 50");
        List<String> actual = masterControl.start(input);

        List<String> expected = Arrays.asList("Savings 12345678 1502.79 2.40", "deposit 12345678 2000", "withdraw 12345678 250",
                "withdraw 12345678 200", "withdraw 12345678 10", "withdraw 12345678 50", "Transfer 56566654 12345678 78",
                "Passs 2", "withdraw 12345678 150");

        assertEquals(expected, actual);

    }

}

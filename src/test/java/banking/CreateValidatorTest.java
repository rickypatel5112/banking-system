package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {

    private Bank bank;
    private CreateValidator createValidator;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        createValidator = new CreateValidator(bank);
    }

    @Test
    public void create_keyword_is_missing_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("checking 48315594 1.3");
        assertFalse(actual);
    }

    @Test
    public void create_keyword_is_missing_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("savings 48315594 1.3");
        assertFalse(actual);
    }

    @Test
    public void create_keyword_is_missing_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("cd 48315594 1.3 400");
        assertFalse(actual);
    }

    @Test
    public void create_keyword_is_misspelled_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("creaate checking 48315594 1.3");
        assertFalse(actual);
    }

    @Test
    public void create_keyword_is_misspelled_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("creaate savings 48315594 1.3");
        assertFalse(actual);
    }

    @Test
    public void create_keyword_is_misspelled_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("creaate cd 48315594 1.3");
        assertFalse(actual);
    }

    @Test
    public void account_type_is_missing_in_command() {
        boolean actual = createValidator.validate("create 49395923 3.4");
        assertFalse(actual);
    }

    @Test
    public void account_type_is_missing_while_creating_cd_account() {
        boolean actual = createValidator.validate("create 49396829 4.5 400");
        assertFalse(actual);
    }

    @Test
    public void checking_account_type_is_misspelled_in_command() {
        boolean actual = createValidator.validate(" create checkng 48315594 1.3");
        assertFalse(actual);
    }

    @Test
    public void savings_account_type_is_misspelled_in_command() {
        boolean actual = createValidator.validate(" create savngs 48315594 1.3");
        assertFalse(actual);
    }

    @Test
    public void cd_account_type_is_misspelled_in_command() {
        boolean actual = createValidator.validate(" create cda 48315594 1.3");
        assertFalse(actual);
    }

    @Test
    public void account_id_is_null_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("create checking 5.4");
        assertFalse(actual);
    }

    @Test
    public void account_id_is_null_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("create savings 5.4");
        assertFalse(actual);
    }

    @Test
    public void account_id_is_null_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("create cd 5.4");
        assertFalse(actual);
    }

    @Test
    public void account_id_length_is_less_than_8_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("create checking 4829438 4.5");
        assertFalse(actual);
    }

    @Test
    public void account_id_length_is_less_than_8_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("create savings 4829438 4.5");
        assertFalse(actual);
    }

    @Test
    public void account_id_length_is_less_than_8_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("create cd 4829438 4.5 400");
        assertFalse(actual);
    }

    @Test
    public void account_id_length_is_more_than_8_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("create checking 4829438434 4.5");
        assertFalse(actual);
    }

    @Test
    public void account_id_length_is_more_than_8_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("create savings 4829438434 4.5");
        assertFalse(actual);
    }

    @Test
    public void account_id_length_is_more_than_8_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("create cd 4829438434 4.5 599");
        assertFalse(actual);
    }

    @Test
    public void checking_account_id_contains_non_numerical_characters() {
        boolean actual = createValidator.validate("create checking 482943@5 4.5");
        assertFalse(actual);
    }

    @Test
    public void savings_account_id_contains_non_numerical_characters() {
        boolean actual = createValidator.validate("create savings 482943@5 4.5");
        assertFalse(actual);
    }

    @Test
    public void cd_account_id_contains_non_numerical_characters() {
        boolean actual = createValidator.validate("create cd 482943@5 4.5 599");
        assertFalse(actual);
    }

    @Test
    public void creating_a_checking_account_with_duplicate_account_id() {
        bank.addAccount(new CheckingAccount("45432454", 4.5));
        boolean actual = createValidator.validate("create checking 45432454 4.2");
        assertFalse(actual);
    }

    @Test
    public void creating_a_savings_account_with_duplicate_account_id() {
        bank.addAccount(new SavingsAccount("45432454", 4.5));
        boolean actual = createValidator.validate("create savings 45432454 4.2");
        assertFalse(actual);
    }

    @Test
    public void creating_a_cd_account_with_duplicate_account_id() {
        bank.addAccount(new CdAccount("45432454", 4.5, 509));
        boolean actual = createValidator.validate("create cd 45432454 4.5 500");
        assertFalse(actual);
    }

    @Test
    public void apr_is_missing_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("create checking 49393954");
        assertFalse(actual);
    }

    @Test
    public void apr_is_missing_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("create savings 49393954");
        assertFalse(actual);
    }

    @Test
    public void apr_is_missing_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("create cd 49393954 500");
        assertFalse(actual);
    }

    @Test
    public void apr_is_less_than_0_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("create checking 49393954 -4.3");
        assertFalse(actual);
    }

    @Test
    public void apr_is_less_than_0_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("create savings 49393954 -4.3");
        assertFalse(actual);
    }

    @Test
    public void apr_is_less_than_0_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("create cd 49393954 -4.3 400");
        assertFalse(actual);
    }

    @Test
    public void apr_is_greater_than_10_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("create checking 49393954 11.3");
        assertFalse(actual);
    }

    @Test
    public void apr_is_greater_than_10_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("create savings 49393954 11.3");
        assertFalse(actual);
    }

    @Test
    public void apr_is_greater_than_10_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("create cd 49393954 11.3 400");
        assertFalse(actual);
    }

    @Test
    public void apr_is_0_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("create checking 49393954 0");
        assertTrue(actual);
    }

    @Test
    public void apr_is_0_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("create savings 49393954 0");
        assertTrue(actual);
    }

    @Test
    public void apr_is_0_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("create cd 49393954 0 1500");
        assertTrue(actual);
    }

    @Test
    public void apr_is_10_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("create checking 49393954 10");
        assertTrue(actual);
    }

    @Test
    public void apr_is_10_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("create savings 49393954 10");
        assertTrue(actual);
    }

    @Test
    public void apr_is_10_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("create cd 49393954 10 1300");
        assertTrue(actual);
    }

    @Test
    public void apr_is_between_0_and_10_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("create checking 49393954 7");
        assertTrue(actual);
    }

    @Test
    public void apr_is_between_0_and_10_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("create savings 49393954 7");
        assertTrue(actual);
    }

    @Test
    public void apr_is_between_0_and_10_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("create cd 49393954 9 3000");
        assertTrue(actual);
    }

    @Test
    public void apr_contains_non_numeric_values_while_creating_a_checking_account() {
        boolean actual = createValidator.validate("create checking 49393954 9%");
        assertFalse(actual);
    }

    @Test
    public void apr_contains_non_numeric_values_while_creating_a_savings_account() {
        boolean actual = createValidator.validate("create savings 49393954 a%");
        assertFalse(actual);
    }

    @Test
    public void apr_contains_non_numeric_values_while_creating_a_cd_account() {
        boolean actual = createValidator.validate("create cd 49393954 9% 100");
        assertFalse(actual);
    }

    @Test
    public void creating_checking_account_with_initial_balance_greater_than_0() {
        boolean actual = createValidator.validate("create checking 58482949 4.5 599");
        assertFalse(actual);
    }

    @Test
    public void creating_savings_account_with_initial_balance_greater_than_0() {
        boolean actual = createValidator.validate("create savings 58482949 4.5 599");
        assertFalse(actual);
    }

    @Test
    public void creating_cd_account_with_initial_balance_greater_than_0_but_less_than_1000() {
        boolean actual = createValidator.validate("create cd 58482949 4.5 599");
        assertFalse(actual);
    }

    @Test
    public void creating_cd_account_with_initial_balance_greater_than_1000_but_less_than_10000() {
        boolean actual = createValidator.validate("create cd 58482949 4.5 1599");
        assertTrue(actual);
    }

    @Test
    public void creating_cd_account_with_initial_balance_greater_than_10000() {
        boolean actual = createValidator.validate("create cd 58482949 4.5 11599");
        assertFalse(actual);
    }

    @Test
    public void creating_checking_account_with_initial_balance_less_than_0() {
        boolean actual = createValidator.validate("create checking 48315594 6.6 -200");
        assertFalse(actual);
    }

    @Test
    public void creating_savings_account_with_initial_balance_less_than_0() {
        boolean actual = createValidator.validate("create savings 48315594 6.6 -200");
        assertFalse(actual);
    }

    @Test
    public void creating_cd_account_with_initial_balance_less_than_0() {
        boolean actual = createValidator.validate("create cd 48315594 6.6 -200");
        assertFalse(actual);
    }

    @Test
    public void creating_cd_account_with_initial_balance_as_0() {
        boolean actual = createValidator.validate("create cd 48315594 6.6 0");
        assertFalse(actual);
    }

    @Test
    public void testing_case_insensitive_command() {
        boolean actual = createValidator.validate("crEAte ChECking 39488584 4.5");
        assertTrue(actual);
    }

    @Test
    public void testing_case_insensitive_command_for_savings_account() {
        boolean actual = createValidator.validate("CReatE SaVIngs 12345678 4.5");
        assertTrue(actual);
    }

    @Test
    public void testing_case_insensitive_command_for_cd_account() {
        boolean actual = createValidator.validate("CReatE CD 12345678 4.5 1220");
        assertTrue(actual);
    }
}


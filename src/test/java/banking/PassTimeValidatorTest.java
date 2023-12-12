package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeValidatorTest {

    private PassTimeValidator passTimeValidator;

    @BeforeEach
    public void setUp() {
        passTimeValidator = new PassTimeValidator(new Bank());
    }

    @Test
    public void pass_keyword_is_missing_in_command() {
        boolean actual = passTimeValidator.validate("12");
        assertFalse(actual);
    }

    @Test
    public void pass_keyword_is_misspelled_in_command() {
        boolean actual = passTimeValidator.validate("Paes 12");
        assertFalse(actual);
    }

    @Test
    public void month_is_alphanumeric_in_command() {
        boolean actual = passTimeValidator.validate("Pass 1a");
        assertFalse(actual);
    }

    @Test
    public void month_is_missing_in_command() {
        boolean actual = passTimeValidator.validate("Pass");
        assertFalse(actual);
    }

    @Test
    public void month_is_greater_than_60_in_command() {
        boolean actual = passTimeValidator.validate("Pass 61");
        assertFalse(actual);
    }

    @Test
    public void month_is_60_in_command() {
        boolean actual = passTimeValidator.validate("Pass 60");
        assertTrue(actual);
    }

    @Test
    public void month_is_in_between_1_and_60_in_command() {
        boolean actual = passTimeValidator.validate("Pass 12");
        assertTrue(actual);
    }

    @Test
    public void month_is_1_in_command() {
        boolean actual = passTimeValidator.validate("Pass 1");
        assertTrue(actual);
    }

    @Test
    public void month_is_less_than_1_in_command() {
        boolean actual = passTimeValidator.validate("Pass 0");
        assertFalse(actual);
    }

    @Test
    public void month_is_negative_in_command() {
        boolean actual = passTimeValidator.validate("Pass -21");
        assertFalse(actual);
    }

    @Test
    public void testing_case_insensitive_pass_time_command() {
        boolean actual = passTimeValidator.validate("pAsS 12");
        assertTrue(actual);
    }

    @Test
    public void testing_decimal_whole_numbers_as_input() {
        boolean actual = passTimeValidator.validate("Pass 4.0");
        assertFalse(actual);
    }
}

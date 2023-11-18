package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCheckerTest {

    private DepositChecker depositValidator;

    @BeforeEach
    public void setUp() {
        depositValidator = new DepositChecker();
    }

    @Test
    public void deposit_keyword_is_missing_from_command() {
        boolean actual = depositValidator.validate("77676767 900");
        assertFalse(actual);
    }

    @Test
    public void deposit_keyword_is_misspelled_in_command() {
        boolean actual = depositValidator.validate("deposet 48395829 400");
        assertFalse(actual);
    }

    @Test
    public void deposit_money_with_id_length_less_than_8() {
        boolean actual = depositValidator.validate("deposit 483838 400");
        assertFalse(actual);
    }

    @Test
    public void deposit_money_with_id_length_more_than_8() {
        boolean actual = depositValidator.validate("deposit 4835532594 300");
        assertFalse(actual);
    }

    @Test
    public void deposit_money_with_null_id() {
        boolean actual = depositValidator.validate("deposit 400");
        assertFalse(actual);
    }

    @Test
    public void deposit_money_with_non_numeric_id() {
        boolean actual = depositValidator.validate("deposit 483vh594 300");
        assertFalse(actual);
    }

    @Test
    public void deposit_money_with_negative_amount() {
        boolean actual = depositValidator.validate("deposit 48355594 -10.03");
        assertFalse(actual);
    }

    @Test
    public void deposit_money_with_amount_as_0() {
        boolean actual = depositValidator.validate("deposit 48389594 0");
        assertTrue(actual);
    }

    @Test
    public void deposit_money_with_amount_as_null() {
        boolean actual = depositValidator.validate("deposit 45439594");
        assertFalse(actual);
    }

    @Test
    public void deposit_money_with_correct_id_and_amount() {
        boolean actual = depositValidator.validate("deposit 48341594 342.12");
        assertTrue(actual);
    }

    @Test
    public void amount_to_be_deposited_contains_alpha_numeric_characters() {
        boolean actual = depositValidator.validate("deposit 43848392 4o0");
        assertFalse(actual);
    }

    @Test
    public void order_of_arguments_is_different_in_command() {
        boolean actual = depositValidator.validate("deposit 300 39285748");
        assertFalse(actual);
    }
}

package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositValidatorTest {

    private DepositValidator depositValidator;
    private Bank bank;
    private GenericAccount checkingAccount;
    private GenericAccount savingsAccount;
    private GenericAccount cdAccount;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        depositValidator = new DepositValidator(bank);

        checkingAccount = new CheckingAccount("12345678", 3.0);
        savingsAccount = new SavingsAccount("12345679", 3.0);
        cdAccount = new CdAccount("12345670", 3.0, 1500);

        bank.addAccount(cdAccount);
        bank.addAccount(checkingAccount);
        bank.addAccount(savingsAccount);
    }

    @Test
    public void checking_account_does_not_exist_in_bank() {
        boolean actual = depositValidator.validate("deposit 58495949 500");
        assertFalse(actual);
    }

    @Test
    public void savings_account_does_not_exist_in_bank() {
        boolean actual = depositValidator.validate("deposit 58495949 500");
        assertFalse(actual);
    }

    @Test
    public void cd_account_does_not_exist_in_bank() {
        boolean actual = depositValidator.validate("deposit 58495949 500");
        assertFalse(actual);
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
    public void deposit_money_with_alphanumeric_id() {
        boolean actual = depositValidator.validate("deposit 483vh594 300");
        assertFalse(actual);
    }

    @Test
    public void depositing_negative_amount_in_a_savings_account() {

        boolean actual = depositValidator.validate("deposit 12345679 -10");

        assertFalse(actual);
    }

    @Test
    public void depositing_$0_in_a_savings_account() {
        boolean actual = depositValidator.validate("deposit 12345679 0");

        assertTrue(actual);
    }

    @Test
    public void depositing_between_$0_and_$2500_in_a_savings_account() {
        boolean actual = depositValidator.validate("deposit 12345679 1500");

        assertTrue(actual);
    }

    @Test
    public void depositing_$2500_in_a_savings_account() {
        boolean actual = depositValidator.validate("deposit 12345679 2500");

        assertTrue(actual);
    }

    @Test
    public void depositing_more_than_$2500_in_a_savings_account() {

        boolean actual = depositValidator.validate("deposit 12345679 3000");

        assertFalse(actual);
    }

    @Test
    public void depositing_negative_amount_in_a_checking_account() {

        boolean actual = depositValidator.validate("deposit 12345678 -10");

        assertFalse(actual);
    }

    @Test
    public void depositing_$0_in_a_checking_account() {
        boolean actual = depositValidator.validate("deposit 12345678 0");

        assertTrue(actual);
    }

    @Test
    public void depositing_between_$0_and_$1000_in_a_checking_account() {
        boolean actual = depositValidator.validate("deposit 12345678 150");

        assertTrue(actual);
    }

    @Test
    public void depositing_$1000_in_a_checking_account() {
        boolean actual = depositValidator.validate("deposit 12345678 1000");

        assertTrue(actual);
    }

    @Test
    public void depositing_more_than_$1000_in_a_checking_account() {
        boolean actual = depositValidator.validate("deposit 12345678 1400");

        assertFalse(actual);
    }

    @Test
    public void depositing_less_than_$0_in_a_cd_account() {

        boolean actual = depositValidator.validate("deposit 12345670 -4");

        assertFalse(actual);
    }

    @Test
    public void depositing_more_than_$0_in_a_cd_account() {

        boolean actual = depositValidator.validate("deposit 12345670 4000");

        assertFalse(actual);
    }

    @Test
    public void depositing_$0_in_a_cd_account() {

        boolean actual = depositValidator.validate("deposit 12345670 0");

        assertFalse(actual);
    }


    @Test
    public void deposit_money_with_amount_as_null() {
        boolean actual = depositValidator.validate("deposit 45439594");
        assertFalse(actual);
    }

    @Test
    public void amount_to_be_deposited_contains_alpha_numeric_characters_for_checking_account() {
        boolean actual = depositValidator.validate("deposit 12345678 4o0");
        assertFalse(actual);
    }

    @Test
    public void amount_to_be_deposited_contains_alpha_numeric_characters_for_savings_account() {
        boolean actual = depositValidator.validate("deposit 12345679 4o0");
        assertFalse(actual);
    }

    @Test
    public void amount_to_be_deposited_contains_alpha_numeric_characters_for_cd_account() {
        boolean actual = depositValidator.validate("deposit 12345670 4o0");
        assertFalse(actual);
    }

    @Test
    public void order_of_arguments_is_different_in_command() {
        boolean actual = depositValidator.validate("deposit 300 39285748");
        assertFalse(actual);
    }

    @Test
    public void testing_case_insensitive_deposit_command() {
        boolean actual = depositValidator.validate("DePOSit 12345678 200");
        assertTrue(actual);
    }
}

package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferValidatorTest {


    private TransferValidator transferValidator;
    private Bank bank;
    private PassTimeProcessor passTimeProcessor;

    @BeforeEach
    public void setUp() {
        bank = new Bank();

        passTimeProcessor = new PassTimeProcessor(bank);

        GenericAccount checkingAccount1 = new CheckingAccount("12345678", 4.5);
        GenericAccount savingsAccount1 = new SavingsAccount("12345679", 4.5);
        GenericAccount cdAccount1 = new CdAccount("12345670", 4.5, 1500);

        checkingAccount1.depositMoney(2000);
        savingsAccount1.depositMoney(2000);

        bank.addAccount(cdAccount1);
        bank.addAccount(checkingAccount1);
        bank.addAccount(savingsAccount1);

        GenericAccount checkingAccount2 = new CheckingAccount("12345671", 4.5);
        GenericAccount savingsAccount2 = new SavingsAccount("12345672", 4.5);
        GenericAccount cdAccount2 = new CdAccount("12345673", 4.5, 1500);

        checkingAccount2.depositMoney(2000);
        savingsAccount2.depositMoney(2000);

        bank.addAccount(cdAccount2);
        bank.addAccount(checkingAccount2);
        bank.addAccount(savingsAccount2);

        transferValidator = new TransferValidator(bank);

    }

    @Test
    public void transfer_from_checking_to_checking_account_without_passing_time_within_deposit_limit() {
        boolean actual = transferValidator.validate("transfer 12345678 12345671 300");

        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_checking_account_without_passing_time_right_on_deposit_limit() {
        boolean actual = transferValidator.validate("transfer 12345678 12345671 400");

        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_checking_account_without_passing_time_within_withdraw_limit() {
        boolean actual = transferValidator.validate("transfer 12345678 12345671 300");

        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_checking_account_without_passing_time_right_on_withdraw_limit() {
        boolean actual = transferValidator.validate("transfer 12345678 12345671 400");

        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_checking_account_without_passing_time_within_deposit_limit_but_outside_withdraw_limit() {
        boolean actual = transferValidator.validate("transfer 12345678 12345671 500");

        assertFalse(actual);
    }

    @Test
    public void transfer_from_checking_to_checking_account_after_passing_1_month_within_deposit_limit() {
        passTimeProcessor.processTime("Pass 1");
        boolean actual = transferValidator.validate("transfer 12345678 12345671 300");

        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_checking_account_after_passing_1_month_right_on_deposit_limit() {
        passTimeProcessor.processTime("Pass 1");
        boolean actual = transferValidator.validate("transfer 12345678 12345671 400");

        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_checking_account_after_passing_1_month_within_withdraw_limit() {
        passTimeProcessor.processTime("Pass 1");
        boolean actual = transferValidator.validate("transfer 12345678 12345671 300");

        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_checking_account_after_passing_1_month_right_on_withdraw_limit() {
        passTimeProcessor.processTime("Pass 1");
        boolean actual = transferValidator.validate("transfer 12345678 12345671 400");

        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_checking_account_after_passing_1_month_within_deposit_limit_but_outside_withdraw_limit() {
        passTimeProcessor.processTime("Pass 1");
        boolean actual = transferValidator.validate("transfer 12345678 12345671 500");

        assertFalse(actual);
    }

    @Test
    public void transfer_from_checking_to_savings_account_without_passing_time_within_withdraw_limit() {
        boolean actual = transferValidator.validate("transfer 12345678 12345679 300");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_savings_account_without_passing_time_right_on_withdraw_limit() {
        boolean actual = transferValidator.validate("transfer 12345678 12345679 400");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_savings_account_without_passing_time_outside_withdraw_limit() {
        boolean actual = transferValidator.validate("transfer 12345678 12345679 600");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_checking_to_savings_account_after_passing_1_month_within_withdraw_limit() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("transfer 12345678 12345679 300");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_savings_account_after_passing_1_month_right_on_withdraw_limit() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("transfer 12345678 12345679 400");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_checking_to_savings_account_after_passing_1_month_outside_withdraw_limit() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("transfer 12345678 12345679 600");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_to_checking_account_without_passing_time_within_withdraw_and_deposit_limit() {

        boolean actual = transferValidator.validate("transfer 12345679 12345678 800");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_to_checking_account_without_passing_time_outside_withdraw_and_deposit_limit() {

        boolean actual = transferValidator.validate("transfer 12345679 12345678 1800");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_to_checking_account_after_passing_1_month_within_withdraw_and_deposit_limit() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("transfer 12345679 12345678 800");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_savings_to_checking_account_after_passing_1_month_outside_withdraw_and_deposit_limit() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("transfer 12345679 12345678 1800");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_to_savings_account_without_passing_any_time_within_withdraw_limit() {

        boolean actual = transferValidator.validate("Transfer 12345679 12345672 300");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_to_savings_account_without_passing_any_time_right_on_withdraw_limit() {

        boolean actual = transferValidator.validate("Transfer 12345679 12345672 1000");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_to_savings_account_after_passing_1_month_within_withdraw_limit() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("Transfer 12345679 12345672 300");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_savings_to_savings_account_after_passing_1_month_right_on_withdraw_limit() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("Transfer 12345679 12345672 1000");
        assertTrue(actual);
    }

    @Test
    public void transfer_from_cd_to_cd_account_without_passing_time() {

        boolean actual = transferValidator.validate("Transfer 12345670 12345673 1500");

        assertFalse(actual);
    }

    @Test
    public void transfer_from_cd_to_cd_account_after_passing_1_month() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("Transfer 12345670 12345673 1500");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_checking_to_cd_account_without_passing_time() {

        boolean actual = transferValidator.validate("Transfer 12345678 12345670 100");

        assertFalse(actual);
    }

    @Test
    public void transfer_from_checking_to_cd_account_after_passing_1_month() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("Transfer 12345678 12345670 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_to_cd_account_without_passing_time() {

        boolean actual = transferValidator.validate("Transfer 12345679 12345670 100");

        assertFalse(actual);
    }

    @Test
    public void transfer_from_savings_to_cd_account_after_passing_1_month() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("Transfer 12345679 12345670 100");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_cd_to_checking_account_without_passing_time() {

        boolean actual = transferValidator.validate("Transfer 12345670 12345678 1500");

        assertFalse(actual);
    }

    @Test
    public void transfer_from_cd_to_checking_account_after_passing_1_month() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("Transfer 12345670 12345678 1500");
        assertFalse(actual);
    }

    @Test
    public void transfer_from_cd_to_savings_account_without_passing_time() {

        boolean actual = transferValidator.validate("Transfer 12345670 12345679 1500");

        assertFalse(actual);
    }

    @Test
    public void transfer_from_cd_to_savings_account_after_passing_1_month() {
        passTimeProcessor.processTime("Pass 1");

        boolean actual = transferValidator.validate("Transfer 12345670 12345679 1500");
        assertFalse(actual);
    }

    @Test
    public void transfer_keyword_is_missing_in_command() {
        boolean actual = transferValidator.validate("12345678 12345679 400");

        assertFalse(actual);
    }

    @Test
    public void transfer_keyword_is_misspelled_in_command() {
        boolean actual = transferValidator.validate("transf1er 12345678 12345679 400");

        assertFalse(actual);
    }

    @Test
    public void toID_contains_alphanumeric_characters_but_fromID_is_valid() {
        boolean actual = transferValidator.validate("transfer 12345678 1234567y 400");

        assertFalse(actual);
    }

    @Test
    public void fromID_contains_alphanumeric_characters_but_toID_is_valid() {
        boolean actual = transferValidator.validate("transfer 123456f7 123456789 400");

        assertFalse(actual);
    }

    @Test
    public void both_fromID_and_toID_contain_alphanumeric_characters() {
        boolean actual = transferValidator.validate("transfer 123456f7 123456rr 400");

        assertFalse(actual);
    }

    @Test
    public void fromID_has_less_than_8_characters_but_toID_is_valid() {
        boolean actual = transferValidator.validate("transfer 12345 12345678 400");

        assertFalse(actual);
    }

    @Test
    public void toID_has_less_than_8_characters_but_fromID_is_valid() {
        boolean actual = transferValidator.validate("transfer 12345678 1234 400");

        assertFalse(actual);
    }

    @Test
    public void both_fromID_and_toID_has_less_than_8_characters() {
        boolean actual = transferValidator.validate("transfer 12345 1234 400");

        assertFalse(actual);
    }

    @Test
    public void fromID_has_more_than_8_characters_but_toID_is_valid() {
        boolean actual = transferValidator.validate("transfer 1234567899 12345678 400");

        assertFalse(actual);
    }

    @Test
    public void toID_has_more_than_8_characters_but_fromID_is_valid() {
        boolean actual = transferValidator.validate("transfer 123456786 12345678 400");

        assertFalse(actual);
    }

    @Test
    public void both_fromID_and_toID_has_more_than_8_characters() {
        boolean actual = transferValidator.validate("transfer 123456788 12343433334 400");

        assertFalse(actual);
    }

    @Test
    public void one_of_the_ID_is_missing() {
        boolean actual = transferValidator.validate("transfer 12345678 40");

        assertFalse(actual);
    }

    @Test
    public void both_IDs_are_missing() {
        boolean actual = transferValidator.validate("transfer 400");

        assertFalse(actual);
    }

    @Test
    public void amount_to_be_transferred_is_missing() {
        boolean actual = transferValidator.validate("transfer 12345678 12345679");

        assertFalse(actual);
    }

    @Test
    public void amount_to_be_transferred_contains_alphanumeric_characters() {
        boolean actual = transferValidator.validate("transfer 12345678 12345679 9o0");

        assertFalse(actual);
    }

    @Test
    public void amount_to_be_transferred_contains_negative_amount() {
        boolean actual = transferValidator.validate("transfer 12345678 12345679 -90");

        assertFalse(actual);
    }

    @Test
    public void transferring_between_same_checking_to_checking_account() {
        boolean actual = transferValidator.validate("Transfer 12345678 12345678 20");

        assertFalse(actual);
    }

    @Test
    public void transferring_between_same_savings_to_savings_account() {
        passTimeProcessor.processTime("Pass 2");
        boolean actual = transferValidator.validate("Transfer 12345679 12345679 20");

        assertFalse(actual);
    }

    @Test
    public void transferring_between_same_cd_to_cd_account() {
        passTimeProcessor.processTime("Pass 2");
        boolean actual = transferValidator.validate("Transfer 12345670 12345670 20");

        assertFalse(actual);
    }
}


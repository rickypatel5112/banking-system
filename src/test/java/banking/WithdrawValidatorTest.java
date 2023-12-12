package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawValidatorTest {

    private WithdrawValidator withdrawValidator;
    private Bank bank;
    private PassTimeProcessor passTimeProcessor;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        passTimeProcessor = new PassTimeProcessor(bank);
        withdrawValidator = new WithdrawValidator(bank);

        GenericAccount checkingAccount = new CheckingAccount("12345678", 3.4);
        checkingAccount.depositMoney(300);
        bank.addAccount(checkingAccount);

        GenericAccount savingsAccount = new SavingsAccount("12345679", 4.5);
        savingsAccount.depositMoney(2000);
        bank.addAccount(savingsAccount);

        GenericAccount cdAccount = new CdAccount("12345670", 3.4, 1500);
        bank.addAccount(cdAccount);
    }

    @Test
    public void withdraw_keyword_is_missing_in_command() {

        boolean actual = withdrawValidator.validate("12458584 400");

        assertFalse(actual);
    }

    @Test
    public void withdraw_keyword_is_misspelled_in_command() {

        boolean actual = withdrawValidator.validate("Withddaw 12458584 400");

        assertFalse(actual);
    }

    @Test
    public void id_is_missing_in_command() {

        boolean actual = withdrawValidator.validate("Withdraw 500");

        assertFalse(actual);
    }

    @Test
    public void id_length_is_less_than_8_in_command() {

        boolean actual = withdrawValidator.validate("Withdraw 12345 500");

        assertFalse(actual);
    }

    @Test
    public void id_length_is_more_than_8_in_command() {

        boolean actual = withdrawValidator.validate("Withdraw 12344343445 500");

        assertFalse(actual);
    }

    @Test
    public void id_contains_alpha_numeric_characters_in_command() {

        boolean actual = withdrawValidator.validate("Withdraw 12e45678 500");

        assertFalse(actual);
    }

    @Test
    public void amount_is_missing_in_command() {
        boolean actual = withdrawValidator.validate("Withdraw 12345678");

        assertFalse(actual);
    }

    @Test
    public void amount_contains_alphanumeric_characters_for_checking_account_in_command() {
        boolean actual = withdrawValidator.validate("Withdraw 12345678 40o");

        assertFalse(actual);
    }

    @Test
    public void amount_contains_alphanumeric_characters_for_savings_account_in_command() {
        boolean actual = withdrawValidator.validate("Withdraw 12345679 40o");

        assertFalse(actual);
    }

    @Test
    public void amount_contains_alphanumeric_characters_for_cd_account_in_command() {
        boolean actual = withdrawValidator.validate("Withdraw 12345670 40o");

        assertFalse(actual);
    }


    @Test
    public void amount_is_negative_for_checking_account_in_command() {
        boolean actual = withdrawValidator.validate("Withdraw 12345678 -400");

        assertFalse(actual);
    }

    @Test
    public void amount_is_negative_for_savings_account_in_command() {
        boolean actual = withdrawValidator.validate("Withdraw 12345679 -400");

        assertFalse(actual);
    }

    @Test
    public void amount_is_negative_for_cd_account_in_command() {
        boolean actual = withdrawValidator.validate("Withdraw 12345670 -400");

        assertFalse(actual);
    }

    @Test
    public void amount_withdrawn_is_$0_in_checking_account_after_1_month_has_passed() {

        passTimeProcessor.processTime("pass 1");

        boolean actual = withdrawValidator.validate("Withdraw 12345678 0");

        assertTrue(actual);
    }

    @Test
    public void amount_withdrawn_is_$0_in_savings_account_after_1_month_has_passed() {
        passTimeProcessor.processTime("pass 1");

        boolean actual = withdrawValidator.validate("Withdraw 12345679 0");

        assertTrue(actual);
    }

    @Test
    public void amount_withdrawn_is_$0_in_cd_account_after_1_month_has_passed() {
        passTimeProcessor.processTime("pass 1");

        boolean actual = withdrawValidator.validate("Withdraw 12345670 0");

        assertFalse(actual);
    }

    @Test
    public void amount_withdrawn_is_$0_in_checking_account_after_no_month_has_passed() {
        boolean actual = withdrawValidator.validate("Withdraw 12345678 0");

        assertTrue(actual);
    }

    @Test
    public void amount_withdrawn_is_$0_in_savings_account_after_no_month_has_passed() {
        boolean actual = withdrawValidator.validate("Withdraw 12345679 0");

        assertFalse(actual);
    }

    @Test
    public void amount_withdrawn_is_$0_in_cd_account_after_no_month_has_passed() {
        boolean actual = withdrawValidator.validate("Withdraw 12345670 0");

        assertFalse(actual);
    }

    @Test
    public void amount_withdrawn_is_$0_in_cd_account_after_12_months_have_passed() {
        passTimeProcessor.processTime("Pass 12");

        boolean actual = withdrawValidator.validate("Withdraw 12345670 0");

        assertFalse(actual);
    }

    @Test
    public void amount_is_more_than_$400_for_a_checking_command() {
        passTimeProcessor.processTime("pass 1");

        boolean actual = withdrawValidator.validate("Withdraw 12345678 500");

        assertFalse(actual);
    }

    @Test
    public void withdrawn_amount_is_between_0_and_400_for_a_checking_account() {
        passTimeProcessor.processTime("pass 1");

        boolean actual = withdrawValidator.validate("Withdraw 12345678 200");

        assertTrue(actual);
    }

    @Test
    public void amount_is_more_than_$1000_for_a_savings_account() {
        passTimeProcessor.processTime("pass 1");

        boolean actual = withdrawValidator.validate("Withdraw 12345678 1500");

        assertFalse(actual);
    }

    @Test
    public void withdrawn_amount_is_between_0_and_1000_for_a_savings_account() {
        passTimeProcessor.processTime("pass 1");

        boolean actual = withdrawValidator.validate("Withdraw 12345679 10");
        assertTrue(actual);
    }

    @Test
    public void withdrawn_amount_is_less_than_account_balance_in_cd_account() {
        boolean actual = withdrawValidator.validate("Withdraw 12345670 300");

        assertFalse(actual);
    }

    @Test
    public void withdrawing_twice_within_same_month_from_a_checking_account() {
        passTimeProcessor.processTime("Pass 1");

        boolean firstWithdrawal = withdrawValidator.validate("Withdraw 12345678 50");
        boolean secondWithdrawal = withdrawValidator.validate("Withdraw 12345678 30");

        assertTrue(firstWithdrawal);
        assertTrue(secondWithdrawal);
    }

    @Test
    public void withdrawing_twice_within_same_month_from_a_checking_account_but_withdrawing_amount_is_more_than_the_balance() {
        passTimeProcessor.processTime("Pass 1");

        boolean firstWithdrawal = withdrawValidator.validate("Withdraw 12345678 350");
        boolean secondWithdrawal = withdrawValidator.validate("Withdraw 12345678 30");

        assertTrue(firstWithdrawal);
        assertTrue(secondWithdrawal);
    }

    @Test
    public void withdrawing_twice_within_same_month_from_a_savings_account() {
        passTimeProcessor.processTime("Pass 1");

        boolean firstWithdrawal = withdrawValidator.validate("Withdraw 12345679 50");
        boolean secondWithdrawal = withdrawValidator.validate("Withdraw 12345679 30");

        assertTrue(firstWithdrawal);
        assertFalse(secondWithdrawal);
    }

    @Test
    public void withdrawing_twice_within_same_month_from_a_cd_account() {
        passTimeProcessor.processTime("Pass 12");

        boolean firstWithdrawal = withdrawValidator.validate("Withdraw 12345670 1800");
        boolean secondWithdrawal = withdrawValidator.validate("Withdraw 12345670 1900");

        assertTrue(firstWithdrawal);
        assertFalse(secondWithdrawal);
    }

}

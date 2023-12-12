package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawProcessorTest {

    private WithdrawProcessor withdrawProcessor;
    private Bank bank;
    private CdAccount cdAccount;
    private CheckingAccount checkingAccount;
    private SavingsAccount savingsAccount;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        withdrawProcessor = new WithdrawProcessor(bank);

        checkingAccount = new CheckingAccount("12345678", 3.4);
        savingsAccount = new SavingsAccount("12345679", 3.2);
        cdAccount = new CdAccount("12345670", 3.4, 5000);

        checkingAccount.depositMoney(1000);
        savingsAccount.depositMoney(1000);

        bank.addAccount(checkingAccount);
        bank.addAccount(savingsAccount);
        bank.addAccount(cdAccount);

    }

    @Test
    public void withdrawing_money_once_in_a_checking_account() {
        withdrawProcessor.withdraw("withdraw 12345678 300");

        double actual = bank.getAccount("12345678").getBalance();

        assertEquals(actual, 700);
    }

    @Test
    public void withdrawing_money_once_in_a_savings_account() {
        withdrawProcessor.withdraw("withdraw 12345679 300");

        double actual = bank.getAccount("12345679").getBalance();

        assertEquals(actual, 700);
    }

    @Test
    public void withdrawing_money_once_in_a_cd_account() {
        withdrawProcessor.withdraw("withdraw 12345670 300");

        double actual = bank.getAccount("12345670").getBalance();

        assertEquals(actual, 4700);
    }

    @Test
    public void withdrawing_money_twice_in_a_checking_account() {
        withdrawProcessor.withdraw("withdraw 12345678 300");
        withdrawProcessor.withdraw("withdraw 12345678 500");

        double actual = bank.getAccount("12345678").getBalance();

        assertEquals(actual, 200);
    }

    @Test
    public void withdrawing_money_twice_in_a_savings_account() {
        withdrawProcessor.withdraw("withdraw 12345679 300");
        withdrawProcessor.withdraw("withdraw 12345679 100");

        double actual = bank.getAccount("12345679").getBalance();

        assertEquals(actual, 600);
    }

    @Test
    public void withdrawing_money_twice_in_a_cd_account() {
        withdrawProcessor.withdraw("withdraw 12345670 300");
        withdrawProcessor.withdraw("withdraw 12345670 100");

        double actual = bank.getAccount("12345670").getBalance();

        assertEquals(actual, 4600);
    }

}

package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositProcessorTest {

    private DepositProcessor depositProcessor;
    private Bank bank;

    private GenericAccount checkingAccount;
    private GenericAccount savingsAccount;
    private GenericAccount cdAccount;


    @BeforeEach
    public void setUp() {
        bank = new Bank();
        depositProcessor = new DepositProcessor(bank);

        checkingAccount = new CheckingAccount("12345678", 3.4);
        savingsAccount = new SavingsAccount("12345679", 3.2);
        cdAccount = new CdAccount("12345670", 3.4, 5000);

        bank.addAccount(checkingAccount);
        bank.addAccount(savingsAccount);
        bank.addAccount(cdAccount);
    }

    @Test
    public void depositing_money_once_in_a_checking_account() {
        depositProcessor.deposit("deposit 12345678 300");

        double actual = bank.getAccount("12345678").getBalance();

        assertEquals(actual, 300);
    }

    @Test
    public void depositing_money_once_in_a_savings_account() {
        depositProcessor.deposit("deposit 12345679 300");

        double actual = bank.getAccount("12345679").getBalance();

        assertEquals(actual, 300);
    }

    @Test
    public void depositing_money_once_in_a_c_account() {
        depositProcessor.deposit("deposit 12345670 300");

        double actual = bank.getAccount("12345670").getBalance();

        assertEquals(actual, 5300);
    }

    @Test
    public void depositing_money_twice_in_a_checking_account() {
        depositProcessor.deposit("deposit 12345678 300");
        depositProcessor.deposit("deposit 12345678 500");

        double actual = bank.getAccount("12345678").getBalance();

        assertEquals(actual, 800);
    }

    @Test
    public void depositing_money_twice_in_a_savings_account() {
        depositProcessor.deposit("deposit 12345679 300");
        depositProcessor.deposit("deposit 12345679 100");

        double actual = bank.getAccount("12345679").getBalance();

        assertEquals(actual, 400);
    }

    @Test
    public void depositing_money_twice_in_a_cd_account() {
        depositProcessor.deposit("deposit 12345670 300");
        depositProcessor.deposit("deposit 12345670 100");

        double actual = bank.getAccount("12345670").getBalance();

        assertEquals(actual, 5400);
    }

}

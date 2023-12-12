package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferProcessorTest {

    private TransferProcessor transferProcessor;
    private Bank bank;
    private GenericAccount checkingAccount;
    private GenericAccount savingsAccount;
    private GenericAccount cdAccount;


    @BeforeEach
    public void setUp() {
        bank = new Bank();
        transferProcessor = new TransferProcessor(bank);

        checkingAccount = new CheckingAccount("12345678", 0.1);
        savingsAccount = new SavingsAccount("12345679", 0.1);

    }

    @Test
    public void transferring_from_checking_to_checking_account() {
        checkingAccount.depositMoney(100);

        GenericAccount otherCheckingAccount = new CheckingAccount("12345677", 0.1);
        bank.addAccount(otherCheckingAccount);
        bank.addAccount(checkingAccount);

        transferProcessor.transfer("transfer 12345678 12345677 10");

        assertEquals(90, checkingAccount.getBalance());
        assertEquals(10, otherCheckingAccount.getBalance());
    }

    @Test
    public void transferring_from_savings_to_savings_account() {
        savingsAccount.depositMoney(100);

        GenericAccount otherSavingsAccount = new SavingsAccount("12345677", 0.1);
        bank.addAccount(otherSavingsAccount);
        bank.addAccount(savingsAccount);

        transferProcessor.transfer("transfer 12345679 12345677 10");

        assertEquals(90, savingsAccount.getBalance());
        assertEquals(10, otherSavingsAccount.getBalance());
    }

    @Test
    public void transferring_from_checking_to_savings_account() {

        checkingAccount.depositMoney(300);

        bank.addAccount(checkingAccount);
        bank.addAccount(savingsAccount);

        transferProcessor.transfer("Transfer 12345678 12345679 20");

        assertEquals(280, checkingAccount.getBalance());
        assertEquals(20, savingsAccount.getBalance());
    }

    @Test
    public void transferring_from_savings_to_checking_account() {

        savingsAccount.depositMoney(300);

        bank.addAccount(checkingAccount);
        bank.addAccount(savingsAccount);

        transferProcessor.transfer("Transfer 12345679 12345678 20");

        assertEquals(20, checkingAccount.getBalance());
        assertEquals(280, savingsAccount.getBalance());
    }

}

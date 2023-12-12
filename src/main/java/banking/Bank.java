package banking;

import java.util.LinkedHashMap;
import java.util.Map;

public class Bank {
    private final Map<String, GenericAccount> accounts;

    public Bank() {
        accounts = new LinkedHashMap<>();
    }

    // Returns all the accounts in the bank
    public Map<String, GenericAccount> getAccounts() {
        return accounts;
    }

    // Adds an account to the bank
    public void addAccount(GenericAccount genericAccount) {
        accounts.put(genericAccount.getId(), genericAccount);
    }

    // Get account details based on id
    public GenericAccount getAccount(String id) {
        return accounts.get(id);
    }

    // Deposit money through accountId
    public void depositMoneyByID(String id, double amount) {
        accounts.get(id).depositMoney(amount);
    }

    // Withdraw money through accountId
    public void withdrawMoneyByID(String id, double amount) {
        accounts.get(id).withdrawMoney(amount);
    }

    public boolean accountExistsById(String id) {
        return accounts.containsKey(id);
    }

    public String getAccountType(String id) {
        return accounts.get(id).getClass().getName();
    }

}



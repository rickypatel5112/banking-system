import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, GenericAccount> accounts;

	Bank() {
		accounts = new HashMap<>();
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

	public boolean accountExistsById(String id){
		if(accounts.containsKey(id)){
			return true;
		}
		else{
			return false;
		}
	}
}

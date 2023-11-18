package banking;

public class CdAccount extends GenericAccount {
    public CdAccount(double apr, String cd_id, double balance) {
        super(balance, apr, cd_id);
    }
}

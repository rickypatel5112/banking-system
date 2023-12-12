package banking;

public class CdAccount extends GenericAccount {

    public CdAccount(String cd_id, double apr, double balance) {
        super(balance, apr, cd_id);
    }


}

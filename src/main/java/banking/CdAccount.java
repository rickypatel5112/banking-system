package banking;

public class CdAccount extends GenericAccount {

    private int age;

    public CdAccount(String cd_id, double apr, double balance) {
        super(balance, apr, cd_id);
    }


}

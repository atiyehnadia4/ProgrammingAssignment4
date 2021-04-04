/**
 * Child class to Account
 */
public  class Checking extends Account {
    private static int nextCheckingAccountNum;
    /**
     * creates a Checking object and populates attributes accountNumber and balance
     * @param accountNumber of type int
     * @param balance of type double
     */
    public Checking(int accountNumber, double balance) {
        super(accountNumber, balance);
        nextCheckingAccountNum=accountNumber+1;
    }

    /**
     * creates a Checking object and populates attributes accountNumber and balance
     * @param balance
     */
    public Checking(double balance) {
        super(nextCheckingAccountNum,balance);
        nextCheckingAccountNum+=1;
    }




    /**
     * checks if customer wants to send money from an account to that same account and calls super transfer method if this is false
     * @Overrride checks if user wats to transfer from checking to checking and calls super if this is false
     * @param to of type Account
     * @param amount of type Account
     * @return boolean representing if transfer was successful
     */
    public  boolean transfer(Account to, double amount) {
        if (to instanceof Checking) {
            System.out.println("cannot transfer to same account");
            return false;
        }
        return super.transfer(to, amount);
    }

}









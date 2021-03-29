/***
 * Child class to Account
 */
public class Savings extends Account {
    private static int nextSavingsAccountNum;
    /**
     * creates a Checking object and populates attributes accountNumber and balance
     * @param accountNumber of type int
     * @param balance of type double
     */
    public Savings(int accountNumber, double balance) {
        super(accountNumber,balance);
        nextSavingsAccountNum=accountNumber+1;
    }

    /**
     * creates a Checking object and populates attribute balance
     * @param balance
     */
    public Savings(double balance){
        super(nextSavingsAccountNum,balance);
        nextSavingsAccountNum+=1;
    }
    /**
     * checks if customer wants to send money from an account to that same account and calls super transfer method if this is false
     * @Overrride checks if user wants to transfer from savings to savings and calls super if this is false
     * @param to of type Account
     * @param amount of type Account
     * @return boolean representing if transfer was successful
     */
    public boolean transfer(Account to, double amount) {
        if (to instanceof Savings) {
            System.out.println("cannot transfer to same account");
            return false;
        }
        return super.transfer(to, amount);
    }

    /**
     * AccountNUmber setter, updates nextAccountNUm
     * @param accountNumber of type int
     */
    public void setAccountNumber(int accountNumber){
        super.setAccountNumber(accountNumber);
        nextSavingsAccountNum=accountNumber+1;
    }

}

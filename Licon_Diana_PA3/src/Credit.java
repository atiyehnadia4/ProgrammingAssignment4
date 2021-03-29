/**
 * Child class to Account
 */
public class Credit extends Account {
    private double creditMax;
    private static int nextCreditAccountNum;
    /**
     * creates a Credit object and populates attributes accountNumber and balance
     * @param accountNumber
     * @param balance
     */
    public Credit(int accountNumber, double balance, double creditMax) {
        super(accountNumber,balance);
        this.creditMax=creditMax;
        nextCreditAccountNum=accountNumber+1;

    }

    /**
     * creates a Credit object and populates attributes accountNumber and balance
     * @param balance
     */
    public Credit(double balance){
        super(nextCreditAccountNum,balance);
        nextCreditAccountNum+=1;
    }

    /**
     * creditMax getter
     * @return
     */
    public double getCreditMax(){
        return this.creditMax;
    }

    /**
     * @override first check is different because Credit balance can be negative
     * @param recipient of type Customer
     * @param amount of type amount
     * @return boolean representing if paySomeone was successful
     */
    public boolean paySomeone(Customer recipient, double amount){
        if (amount>0){
            setBalance(getBalance() - amount);
            recipient.getChecking().setBalance(recipient.getChecking().getBalance()+amount);

            System.out.println("Succesfully transfered $"+amount+" to "+recipient.getName());
            return true;
        }
        System.out.println("Failed. Amount must be >=0");
        return false;
    }
    /**
     * checks if customer wants to send money from an account to that same account and calls super transfer method if this is false
     * @Overrride checks if user wats to transfer from credit to credit and calls super if this is false
     * @param to of type Account
     * @param amount of type Account
     * @return boolean representing if transfer was successful
     */
    public boolean transfer(Account to, double amount) {
        if (to instanceof Credit) {
            System.out.println("cannot transfer to same account");
            return false;
        }
        return super.transfer(to, amount);
    }

    /**
     * AccountNumber setter
     * @param accountNumber of type int
     */
    public void setAccountNumber(int accountNumber){
        super.setAccountNumber(accountNumber);
        nextCreditAccountNum=accountNumber+1;
    }
}

import java.util.ArrayList;

/**
 * Account Class is an abstract class that holds certain information about a bank account, with this information the user
 * is able to perform certain actions with the information that range from making a balance inquiry, transfer, deposit,
 * withdrawal, or payment on that specific account
 *
 * @author Nadia Atiyeh
 * @version 3.0
 * @since March 15,2021
 *
 */

abstract class Account {
    /**
     * The integer identifier assigned to the account type (1 = Checking, 2 = Savings, 3 = Credit)
     */
    private int accountType;
    /**
     * The current balance the account
     */
    private double currentBalance;
    /**
     * The account number associated to the account
     */
    private int accountNumber;
    /**
     * The starting balance for any user in the HashTab;e
     */
    private double startingBalance;
    /**
     * ArrayList for all the transactions for a given account
     */
    private ArrayList<String> transactions = new ArrayList<String>();

    // Constructors
    /**
     * Default Constructor of the Account Class
     */
    public Account(){

    }

    /**
     * Makes an instance of an Account class, with the accountType, currentBalance, and accountNumber all identified
     *
     * @param accountTypeIn number related to specific account (1 = Checking, 2= Savings, 3 = Credit)
     * @param currentBalanceIn current balance posted for that account
     * @param accountNumberIn account number for specific account
     */
    public Account(int accountTypeIn, double currentBalanceIn, int accountNumberIn){
        this.accountType = accountTypeIn;
        this.currentBalance = currentBalanceIn;
        this.accountNumber = accountNumberIn;
    }

    //getters
    /**
     * Gets specified accounts account number
     * @return accountNumber: number associated to account
     */
    public int getAccountNumber(){
        return this.accountNumber;
    }

    /**
     * Gets the account type for the specified account
     * @return accountType: the number associated to the type of account
     */
    public int getAccountType(){
        return this.accountType;
    }

    /**
     * Gets the current balance for the specified account
     * @return currentBalance: the current posted balance of the specified account
     */
    public double getCurrentBalance(){
        return this.currentBalance;
    }

    /**
     * Gets the starting balance for the specified account
     * @return startingBalance: the current posted starting balance for a specified account
     */
    public double getStartingBalance(){
        return this.startingBalance;
    }

    /**
     * Gers the transactions for a specified account
     * @return transactions: the array lists of transaction Strings associated to an account
     */
    public ArrayList<String> getTransactions() {
        return this.transactions;
    }

    //setters
    /**
     * Sets the account number for the specified instance of an account
     * @param accountNumberIn the account number that is taken in
     */
    public void setAccountNumber(int accountNumberIn){
        this.accountNumber = accountNumberIn;
    }

    /**
     * Sets the current balance for the specified instance of an account
     * @param currentBalanceIn the current balance that is taken in
     */
    public void setCurrentBalance(double currentBalanceIn){
        this.currentBalance = currentBalanceIn;
    }

    /**
     * Sets the account type for the specified instance of an account
     * @param accountTypeIn the account type that is taken in
     */
    public void setAccountType(int accountTypeIn) {
        this.accountType = accountTypeIn;
    }

    /**
     * Sets the starting balance for a specified account
     * @param startingBalance balance read directly from the CSV
     */
    public void setStartingBalance(double startingBalance) {
        this.startingBalance = startingBalance;
    }

    /**
     * Adds transaction strings to the transactions arrayList
     * @param transactions string corresponding to specific transaction
     */
    public void setTransactions(String transactions){
        this.transactions.add(transactions);
    }

    // methods
    /**
     * Performs a balance inquiry on the specified account
     *
     * @param customerIn takes in the Customer instance of the current user
     * @param pin takes in the users pin (last 4 of their identificiation number)
     */
    public void balanceInquiry(Customer customerIn, int pin){
        if(pin != customerIn.getIdentificationNumber()){
            System.out.println("Error: Pin could not be validated");
        }
        String str = "Success: Current Balance for account number " + this.accountNumber + " is $" + String.format("%,.2f",this.currentBalance);
        System.out.println(str);
    }

    /**
     * Performs a balance inquiry for a specified account without the need for a pin
     * @param customerIn takes in the Customer instance of the current user
     */
    public void balanceInquiry(Customer customerIn){
        String str = "Success: Current Balance for account number " + this.accountNumber + " is $" + String.format("%,.2f",this.currentBalance);
        System.out.println(str);
    }

    /**
     * Performs a deposit on the specified account
     * @param amountIn the amount that is to be deposited
     * @return <code> true </code> if the transaction is a success.
     * <br> <code> false </code> if the transaction is a failure
     */
    public boolean deposit(double amountIn){
        if(amountIn < 0){
            System.out.println("Error: Cannot deposit a negative amount");
            return false;
        }
        this.currentBalance += amountIn;
        String str = "Deposit of $" + String.format("%,.2f",amountIn) +" into account number " + this.accountNumber + ". Current Balance is $" + String.format("%,.2f",this.currentBalance);
        System.out.println("Success: " + str);
        this.transactions.add(str);
        return true;
    }

    /**
     * Performs a withdrawal on the specified account
     * @param amountOut amount that is to be withdrawn
     * @return <code> true </code> if the transaction is a success.
     *         <br> <code> false </code> if the transaction is a failure
     */
    public boolean withdraw(double amountOut){
        if(amountOut > this.currentBalance){
            System.out.println("Error: Cannot withdraw more than current posted balance");
            return false;
        }
        if(amountOut < 0){
            System.out.println("Error: Cannot withdraw a negative amount");
            return false;
        }
        currentBalance -= amountOut;
        String str = "Withdrawal of $" + String.format("%,.2f",amountOut) +" from account number " + this.accountNumber + ". Current Balance is $" + String.format("%,.2f",this.currentBalance);
        System.out.println("Success: " + str);
        this.transactions.add(str);
        return true;

    }

    /**
     * Performs a transfer between two specified accounts
     * @param amountOut amount that is to be transferred
     * @param accountIn the account that is going to get the funds transferred in
     * @return <code> true </code> if the transaction is a success.
     *      <br> <code> false </code> if the transaction is a failure
     */
    public boolean transfer(double amountOut, Account accountIn){
        if(this.getAccountType() != accountIn.getAccountType()){
            if(!accountIn.deposit(amountOut)){
                System.out.println("Error: Could not complete transfer.");
                return false;
            }
            else{
                this.withdraw(amountOut);
                accountIn.deposit(amountOut);
                String str = "Transfer of $" + String.format("%,.2f",amountOut) + " into account number " + accountIn.accountNumber + ". Current Balance for Account 1 is $" + String.format("%,.2f",this.currentBalance) + ", Account 2 $" + String.format("%,.2f",accountIn.currentBalance);
                System.out.println("Success: " + str);
                this.transactions.add(str);
                return true;

            }
        }
        return false;
    }

    /**
     * Performs a payment between two specified accounts
     * @param amountOut amount that is to be paid out
     * @param accountIn Account that the payment will be applied to.
     * @return <code> true </code> if the transaction is a success.
     *       <br> <code> false </code> if the transaction is a failure
     */
    public boolean payment(double amountOut, Account accountIn){
        if(!this.withdraw(amountOut)){
            System.out.println("Error: Could not complete payment.");
            return false;
        }

        if(!accountIn.deposit(amountOut)){
            System.out.println("Error: Could not complete payment.");
            return false;
        }

        else {
            String str = "Success: Payment of $" + String.format("%,.2f", amountOut) + " into account number " + accountIn.accountNumber + ". User Current Balance is $" + String.format("%,.2f", this.currentBalance);
            System.out.println(str);
            this.transactions.add(str);
            return true;
        }
    }
}

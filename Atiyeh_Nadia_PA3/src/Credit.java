
/**
 * Credit Class is an child class that inherits information from the Account Class. With the information from the Account
 * class it sets the accountType, currentBalance, and accountNumber for that specific Credit account.
 *
 *
 * @author Nadia Atiyeh
 * @version 3.0
 * @since March 15,2021
 *
 */
public class Credit extends Account{

    /**
     * Stores credit max for credi account
     */
    private int creditMax;

    /**
     * Default Constructor for the Credit Class
     */
    public Credit(){

    }

    /**
     * Makes an instance of the Credit class with accountType, currentBalance, and accountNumber declared
     * @param accountTypeIn number related to specific account (1 = Checking, 2= Savings, 3 = Credit)
     * @param currentBalanceIn current balance posted for the credit account
     * @param accountNumberIn account number for specific credit account
     */
    public Credit(int accountTypeIn, double currentBalanceIn, int accountNumberIn, int creditMax) {
        super(accountTypeIn, currentBalanceIn, accountNumberIn);
        this.creditMax = creditMax;
    }

    //getters

    /**
     * Gets credit max for a specified account
     * @return creditMax: credit max read off of CSV
     */
    public int getCreditMax(){
        return this.creditMax;
    }

    //setters

    /**
     * Sets credit max for a specified account
     * @param creditMaxIn: credit max to be set for account
     */
    public void setCreditMax(int creditMaxIn){
        this.creditMax = creditMaxIn;
    }



    /**
     * Overridden Method of the Account Class deposit method that checks that the deposit does not bring the Credit accounts
     * to anything equal to or above zero
     * @param amountIn the amount that is to be deposited
     * @return <code> true </code> if the transaction is a success.
     *       <br> <code> false </code> if the transaction is a failure
     */
    @Override
    public boolean deposit(double amountIn){
        double amountToDeposit = this.getCurrentBalance() + amountIn;

        if(amountToDeposit > 0 || amountIn < 0){
            System.out.println("Error: Cannot deposit more than what is posted in negative balance");
            return false;
        }
        else{
            this.setCurrentBalance(amountToDeposit);
            String str = "Deposit of $" + String.format("%,.2f", amountIn) +" into account number " + this.getAccountNumber() + ". Current Balance is $" + String.format("%,.2f",this.getCurrentBalance());
            System.out.println("Success: " + str);
            setTransactions(str);
            return true;
        }
    }

    /**
     * Overriden Method of the Account Class withdraw method that ensures that once the withdrawal is complete it adds to the negative
     * balance instead of subtracting from it
     * @param amountOut amount that is to be withdrawn
     * @return <code> true </code> if the transaction is a success.
     *       <br> <code> false </code> if the transaction is a failure
     */
    @Override
    public boolean withdraw(double amountOut){
        if(amountOut < 0){
            System.out.println("Error: Cannot withdraw a negative amount");
            return false;
        }
        double withdrawAmount = this.getCurrentBalance() - amountOut;
        this.setCurrentBalance(withdrawAmount);
        String str = "Withdrawal of $" + String.format("%,.2f",amountOut) +" into account number " + this.getAccountNumber()+ ". Current Balance is $" + String.format("%,.2f",this.getCurrentBalance());
        System.out.println("Success: " + str);
        setTransactions(str);
        return true;
    }

    /**
     * Overridden Method of the Account Class transfer method that ensures that if the withdrawal from the current account
     * does not go through the transfer fails otherwise it succeeds and deposits into the recipient account
     * @param amountOut amount that is to be transferred
     * @param accountIn the account that is going to get the funds transferred in
     * @return <code> true </code> if the transaction is a success.
     *       <br> <code> false </code> if the transaction is a failure
     */
    @Override
    public boolean transfer(double amountOut, Account accountIn){
        if(!this.withdraw(amountOut)){
            System.out.println("Error: Could not complete transfer.");
            return false;
        }
        accountIn.deposit(amountOut);
        String str = "Transfer of $" + String.format("%,.2f",amountOut) + " into account number " + accountIn.getAccountNumber() + ". Current Balance for Account 1 is $" + String.format("%,.2f",this.getCurrentBalance()) + ", Account 2 $" + String.format("%,.2f",accountIn.getCurrentBalance());
        System.out.println("Success: " + str);
        setTransactions(str);
        return true;
    }

}

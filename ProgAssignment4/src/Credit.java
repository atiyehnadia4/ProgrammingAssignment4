public class Credit extends Account{

    /**
     * Stores credit max for credi account
     */
    private int creditMax;

    private static int nextCreditAccountNum;

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
        this.nextCreditAccountNum=accountNumber+1;
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

        if(amountToDeposit > 0){
            System.out.println("Error: Cannot deposit more than what is posted in negative balance");
            return false;
        }
        return super.deposit(amountIn);
    }






    public void setAccountNumber(int accountNumber){
        super.setAccountNumber(accountNumber);
        nextCreditAccountNum=accountNumber+1;
    }

}

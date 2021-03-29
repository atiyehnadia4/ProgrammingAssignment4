/**
 * Savings Class is an child class that inherits information from the Account Class. With the information from the Account
 * class it sets the accountType, currentBalance, and accountNumber for that specific Savings account.
 *
 * @author Nadia Atiyeh
 * @version 3.0
 * @since February 17, 2021
 *
 */
public class Savings extends Account{


    /**
     * Default Constructor for the Savings Class
     */
    public Savings(){

    }

    /**
     * Makes an instance of the Savings class with accountType, currentBalance, and accountNumber declared
     * @param accountTypeIn number related to specific account (1 = Checking, 2= Savings, 3 = Credit)
     * @param currentBalanceIn current balance posted for the savings account
     * @param accountNumberIn account number for specific savings account
     */
    public Savings(int accountTypeIn, double currentBalanceIn, int accountNumberIn) {
        super(accountTypeIn, currentBalanceIn, accountNumberIn);
    }
}

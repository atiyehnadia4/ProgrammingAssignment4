/**
 * Checking Class is an child class that inherits information from the Account Class. With the information from the Account
 * class it sets the accountType, currentBalance, and accountNumber for that specific Checking account
 *
 * @author Nadia Atiyeh
 * @version 3.0
 * @since March 15,2021
 *
 */
public class Checking extends Account{

    /**
     * Default Constructor for the Checking Class
     */
    public Checking(){


    }

    /**
     * Makes an instance of the Checking class with accountType, currentBalance, and accountNumber declared
     * @param accountTypeIn number related to specific account (1 = Checking, 2= Savings, 3 = Credit)
     * @param currentBalanceIn current balance posted for the checking account
     * @param accountNumberIn account number for specific checking account
     */
    public Checking(int accountTypeIn, double currentBalanceIn, int accountNumberIn) {
        super(accountTypeIn, currentBalanceIn, accountNumberIn);
    }
}

import java.util.ArrayList;

/**
 * Customer class is a child class that inherits information from the Person Class, with this information the customer class
 * keeps track of all the persons accounts (Checking, Savings, Credit), a pin number, and the users full name.
 *
 * @author Nadia Atiyeh
 * @version 3.0
 * @since March 15, 2021
 *
 */

public class Customer extends Person{
    /**
     * ArrayList that keeps track of the users checking accounts
     */
    private ArrayList<Checking> checkingAccounts = new ArrayList<Checking>(1);
    /**
     * ArrayList that keeps track of the users savings accounts
     */
    private ArrayList<Savings> savingsAccounts =  new ArrayList<Savings>(1);;
    /**
     * ArrayList that keeps track of the users credit accounts
     */
    private ArrayList<Credit> creditAccounts =  new ArrayList<Credit>(1);;
    /**
     * Keeps track of the users fullName
     */
    private String fullName;
    /**
     * Default Constructor for the Customer Class
     */
    public Customer(){

    }

    /**
     * Makes an instance of the Customer class with the lists of checkingAccounts, savingAccounts, and creditAccounts
     * @param checkingAccountsIn Array list of checking accounts for the prescribed user
     * @param savingsAccountsIn Array list of savings accounts for the prescribed user
     * @param creditAccountsIn Array list of credit accounts for the prescribed user
     */
    public Customer(ArrayList<Checking> checkingAccountsIn, ArrayList<Savings> savingsAccountsIn, ArrayList<Credit> creditAccountsIn){
        this.checkingAccounts = checkingAccountsIn;
        this.savingsAccounts = savingsAccountsIn;
        this.creditAccounts = creditAccountsIn;
    }

    //getters
    /**
     * Gets the ArrayList of checking accounts
     * @return checkingAccounts: the ArrayList of the checking accounts
     */
    public ArrayList<Checking> getCheckingAccounts(){
        return this.checkingAccounts;
    }

    /**
     * Gets the ArrayList of savings accounts
     * @return savingsAccounts: the ArrayList of the savings accounts
     */
    public ArrayList<Savings> getSavingsAccounts() {
        return this.savingsAccounts;
    }

    /**
     * Gets the ArrayList of credit accounts
     * @return creditAccounts: the ArrayList of the credit accounts
     */
    public ArrayList<Credit> getCreditAccounts(){
        return this.creditAccounts;
    }

    /**
     * Gets all the user information with the users full name and number associated to the account type
     * @param fullName users full Name
     * @param accountType users account type
     */
    public void getUserInformation(String fullName, int accountType){
        System.out.println("Full Name: " + fullName);
        System.out.println("DOB: " + getDateOfBirth());
        System.out.println("ID #: " + getIdentificationNumber());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhoneNumber());

        if(accountType == 1){
            System.out.println("CHECKING ACCOUNT");
            System.out.println("Account Number: " + getCheckingAccounts().get(0).getAccountNumber());
            System.out.println("Current Balance: $" + getCheckingAccounts().get(0).getCurrentBalance());
        }
        if(accountType == 2){
            System.out.println("SAVINGS ACCOUNT");
            System.out.println("Account Number: " + getSavingsAccounts().get(0).getAccountNumber());
            System.out.println("Current Balance: $" + getSavingsAccounts().get(0).getCurrentBalance());
        }

        if(accountType == 3){
            System.out.println("CREDIT ACCOUNT");
            System.out.println("Account Number: " + getCreditAccounts().get(0).getAccountNumber());
            System.out.println("Current Balance: $" + getCreditAccounts().get(0).getCurrentBalance());
        }
        if(accountType == 4){
            System.out.println("CHECKING ACCOUNT");
            System.out.println("Account Number: " + getCheckingAccounts().get(0).getAccountNumber());
            System.out.println("Current Balance: $" + getCheckingAccounts().get(0).getCurrentBalance());
            System.out.println();
            System.out.println("SAVINGS ACCOUNT");
            System.out.println("Account Number: " + getSavingsAccounts().get(0).getAccountNumber());
            System.out.println("Current Balance: $" + getSavingsAccounts().get(0).getCurrentBalance());
            System.out.println();
            System.out.println("CREDIT ACCOUNT");
            System.out.println("Account Number: " + getCreditAccounts().get(0).getAccountNumber());
            System.out.println("Current Balance: $" + getCreditAccounts().get(0).getCurrentBalance());
        }
        if(accountType == 0){
            System.out.println();
        }
    }

    /**
     * Gets the users full name
     * @return fullName; users full name
     */
    public String getFullName() {
        return this.fullName;
    }

    //setters
    /**
     * Sets the Checking Accounts for a specific customer
     * @param accountIn Checking account to be added
     */
    public void setCheckingAccounts(Checking accountIn){
        this.checkingAccounts.add(accountIn);
    }

    /**
     * Sets the Savings Accounts for a specific customer
     * @param accountIn Savings account to be added
     */
    public void setSavingsAccounts(Savings accountIn){
        this.savingsAccounts.add(accountIn);
    }

    /**
     * Sets the Credit Accounts for a specific customer
     * @param accountIn Credit account to be added
     */
    public void setCreditAccounts(Credit accountIn){
        this.creditAccounts.add(accountIn);
    }


    /**
     * Sets the full name for a specific customer
     * @param fullNameIn fullName inputted by customer
     */
    public void setFullName(String fullNameIn){
        fullName = fullNameIn;
    }
}

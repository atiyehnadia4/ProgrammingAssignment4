/**
 * parent class to Checking, Savings and Credit
 */
import java.lang.Math;
import java.util.ArrayList;

public abstract class Account {
    private int accountNumber;
    private double balance;
    private Double startingBalance;
    private ArrayList<String> transactions;


    /**
     * creates an account object and populates attributes accountNumber and balance
     * @param accountNumber of type int
     * @param balance of type double
     */
    public Account(int accountNumber, double balance){
        this.accountNumber=accountNumber;
        this.balance=balance;
        this.startingBalance=balance;
        this.transactions=new ArrayList<String>();

    }

    /**
     * accountNumber getter
     * @return accountNumber of type int
     */
    public int getAccountNumber(){
        return this.accountNumber;
    }

    /**
     * accountNumber setter
     * @param accountNumber of type int
     */
    public void setAccountNumber(int accountNumber){
        this.accountNumber=accountNumber;
    }

    /**
     * balance getter
     * @return balance of type double
     */
    public double getBalance(){
        return this.balance;
    }
    public ArrayList<String> getTransactions(){
        return this.transactions;
    }

    /**
     * balance setter
     * @param balance of type double
     */
    public void setBalance(double balance){
        this.balance=balance;
    }
    public double getStartingBalance(){
        return this.startingBalance;
    }
    /**
     * checks conditions to determine if deposit can be preformed
     * @param amount of type double
     * @return Boolean representing if deposit was successful
     */
    public boolean deposit(double amount) {
        if (amount>0){
            this.balance+=amount;
            System.out.println("Deposit successful");
            transactions.add("deposited $"+amount+" into "+this.accountNumber);
            return true;
        }
        System.out.println("Failed. Amount must be >=0");
        return false;
    }

    /**
     * checks conditions to determine if withdraw can be preformed
     * @param amount of type double
     * @return Boolean representing if withdraw was successful
     */
    public boolean withdraw(double amount){
        if (amount>0){
            if (!(getBalance()-amount<0)){
                setBalance(getBalance()-amount);
                System.out.println("Withdraw succesful");
                transactions.add("Withdrew $"+amount+" from "+this.accountNumber);
                return true;
            }
            System.out.println("Failed. Not enough funds to withdraw "+amount);
            return false;
        }
        System.out.println("Filed. Amount must be >=0");
        return false;
    }

    /**
     * checks conditions to determine if paySomeone can be preformed
     * deposits into recipient's checking account
     * @param recipient of type Customer
     * @param amount of type amount
     * @return Boolean representing if paySomeone was successful
     */
    public boolean paySomeone(Customer recipient, double amount){
        if (amount>0){
            if (!(getBalance()-amount<0)) {
                setBalance(getBalance() - amount);
                recipient.getChecking().setBalance(recipient.getChecking().getBalance()+amount);
                transactions.add("payed $"+amount+" from "+this.accountNumber+" to "+recipient.getName());
                System.out.println("Succesfully payed $"+amount+" to "+recipient.getName());
                return true;
            }
            System.out.println("Failed. Not enough funds in your account to transfer $"+amount+" to "+recipient.getName());
            return false;
        }
        System.out.println("Failed. Amount must be >=0");
        return false;
    }

    /**
     * transfers money from a Customers account to another account of the same Customer
     * @param to of type Account
     * @param amount of type Account
     * @return Boolean representing if deposit was successful
     */
    public boolean transfer( Account to,double amount){
       this.balance-=amount;
       to.balance+=amount;
        transactions.add("payed $"+amount+" from "+this.accountNumber+" to "+to.accountNumber);
        return true;
    }

    /**
     * print the balance of the account
     * @param type of type String
     */
    public void inquire(String type) {
        System.out.println("your balance for "+type+" account is " + this.balance);
    }

    }



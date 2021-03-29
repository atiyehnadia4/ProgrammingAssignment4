import java.util.ArrayList;

/**
 * Child class to Person
 */
public class Customer extends Person {
    private int pinNumber;
    private Credit credit;
    private Savings savings;
    private Checking checking;
    //checking,savings,credit
    private Account[] accounts=new Account[3];
    private static int nextIdentificationNum;

    /**
     *creates an object type Customer
     */
    public Customer(){}
    /**
     * creates an object type Customer by calling super
     * @param firstName of type String
     * @param lastName of type String
     * @param dateOfBirth of type String
     * @param identificationNum of type String
     * @param address of type String
     * @param phoneNumber of type String
     */
    public Customer(String firstName, String lastName, String dateOfBirth, int identificationNum, String address, String phoneNumber){
        super(firstName, lastName, dateOfBirth, identificationNum, address, phoneNumber);
        nextIdentificationNum=identificationNum+1;
    }

    /**
     * creates an object type Customer by calling super
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param address
     * @param phoneNumber
     */
    public Customer(String firstName, String lastName, String dateOfBirth, String address, String phoneNumber){
        super(firstName, lastName, dateOfBirth, nextIdentificationNum, address, phoneNumber);
        nextIdentificationNum+=1;
    }

    /**
     * checking setter, adds checking to accounts array
     * @param checking of type Checking
     */
    public void setChecking(Checking checking) {
        this.checking = checking;
        accounts[0]=this.checking;
    }

    /**
     * checking getter
     * @return checking of type Checking
     */
    public Checking getChecking(){
        return this.checking;
    }

    /**
     * savings setter, adds savings to accounts array
     * @param savings of type Savings
     */
    public void setSavings(Savings savings){
        this.savings=savings;
        accounts[1]=this.savings;
    }

    /**
     * savings getter
     * @return savings of type Savings
     */
    public Savings getSavings(){
        return this.savings;
    }

    /**
     * credit setter, adds credit to accounts array
     * @param credit of type Credit
     */
    public void setCredit(Credit credit){
        this.credit=credit;
        accounts[2]=this.credit;
    }

    /**
     * credit getter
     * @return credit of type Credit
     */
    public Credit getCredit(){
        return this.credit;
    }

    /**
     * ID setter
     * @param identificationNum of type String
     */
    public void setIdentificationNum(int identificationNum){
        super.setIdentificationNum(identificationNum);
        nextIdentificationNum=identificationNum+1;

    }

    /**
     * Accounts[] getter
     * some elements might be null
     * @return
     */
    public Account[] getAccounts() {
        return accounts;
    }
}

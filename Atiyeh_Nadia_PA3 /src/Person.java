/**
 * Person class is an abstract class that holds certain information about a person, with this information the user is able
 * to perform certain actions like adding an account to the persons scope, or viewing all the accounts they have
 *
 * @author Nadia Atiyeh
 * @version 3.0
 * @since March 15,2021
 *
 */
abstract class Person {
    /**
     * The first name assigned to a person
     */
    private String firstName;
    /**
     * The last name assigned to a person
     */
    private String lastName;
    /**
     * The date of birth assigned to a person
     */
    private String dateOfBirth;
    /**
     * The identification number assigned to a person
     */
    private int identificationNumber;
    /**
     * The address assigned to a person
     */
    private String address;
    /**
     * The phone number assigned to a person
     */
    private String phoneNumber;

    /**
     * Default Constructor of the Person Class
     */
    public Person(){

    }

    /**
     * Makes an instance of the Person class, with firstname, lastname, DOB, ID number, address, and phone number all identified
     *
     * @param firstNameIn persons first name
     * @param lastNameIn persons last name
     * @param dateOfBirthIn persons date of birth
     * @param identificationNumberIn persons ID/SSN number
     * @param addressIn persons address
     * @param phoneNumberIn persons phone number
     */
    public Person(String firstNameIn, String lastNameIn, String dateOfBirthIn, int identificationNumberIn, String addressIn, String phoneNumberIn){
        this.firstName = firstNameIn;
        this.lastName = lastNameIn;
        this.dateOfBirth = dateOfBirthIn;
        this.identificationNumber = identificationNumberIn;
        this.address = addressIn;
        this.phoneNumber = phoneNumberIn;
    }

    //getters
    /**
     * Gets the current users firstName
     * @return firstName: users first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Gets the current users lastName
     * @return lastName: users last name
     */
    public String getLastName(){
        return this.lastName;
    }

    /**
     * Gets the current users date of birth
     * @return dateOfBirth: users date of birth
     */
    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * Gets the current users identification number
     * @return identificationNumber: users id number
     */
    public int getIdentificationNumber(){
        return this.identificationNumber;
    }

    /**
     * Gets the current users address
     * @return address: gets user address
     */
    public String getAddress(){
        return this.address;
    }

    /**
     * Gets the current users phone number
     * @return phoneNumber: users phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }


    //setters
    /**
     * Sets the current users first name
     * @param firstNameIn users chosen first name
     */
    public void setFirstName(String firstNameIn){
        this.firstName = firstNameIn;
    }

    /**
     * Sets the current users last name
     * @param lastNameIn users chosen last name
     */
    public void setLastName(String lastNameIn){
        this.lastName = lastNameIn;
    }

    /**
     * Sets the current users address
     * @param addressIn users chosen address
     */
    public void setAddress(String addressIn) {
        this.address = addressIn;
    }

    /**
     * Sets the current users date of birth
     * @param dateOfBirthIn users chosen date of birth
     */
    public void setDateOfBirth(String dateOfBirthIn) {
        this.dateOfBirth = dateOfBirthIn;
    }

    /**
     * Sets the current users identification number
     * @param identificationNumberIn users chosen identification number
     */
    public void setIdentificationNumber(int identificationNumberIn) {
        this.identificationNumber = identificationNumberIn;
    }

    /**
     * Sets the current users phone number
     * @param phoneNumberIn users chosen phone number
     */
    public void setPhoneNumber(String phoneNumberIn) {
        this.phoneNumber = phoneNumberIn;
    }

    //methods

    /**
     * Adds an account to the users list of accounts in its scope
     * @param accountIn instance of an Account class that would be added into users list
     * @param client instance of a Customer class that adds name to account
     */
    public void addAccount(Account accountIn, Customer client){
        if(accountIn.getAccountType() == 1){
            Checking newAccountC = new Checking();
            client.setCheckingAccounts(newAccountC);
        }
        if(accountIn.getAccountType() == 2){
            Savings newAccountS = new Savings();
            client.setSavingsAccounts(newAccountS);
        }
        if(accountIn.getAccountType() == 3){
            Credit newAccountCR = new Credit();
            client.setCreditAccounts(newAccountCR);
        }
    }

    /**
     * Views all the accounts a customer has, and prints them out
     * @param customerIn the customer whose accounts are to be evaluated
     */
    public void viewAccounts(Customer customerIn){
        System.out.println(customerIn.getCheckingAccounts());
        System.out.println(customerIn.getSavingsAccounts());
        System.out.println(customerIn.getCreditAccounts());

    }


}

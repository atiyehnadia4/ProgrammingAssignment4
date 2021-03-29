/**
 * parent class to Customer
 */
public abstract class Person {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;
    private int identificationNum;
    private String address;

    /**
     *creates an object type Person
     */
    public Person(){}
    /**
     * creates object of type Person and populates all atributes
     * @param firstName of type String
     * @param lastName of type String
     * @param dateOfBirth of type String
     * @param identificationNum of type String
     * @param address of type String
     * @param phoneNumber of type String
     */
    public Person(String firstName, String lastName, String dateOfBirth, int identificationNum, String address, String phoneNumber){
        this.firstName=firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
        this.identificationNum=identificationNum;
        this.address=address;
        this.phoneNumber=phoneNumber;
    }

    /**
     * name setter for both first and last name
     * @param firstName of type String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * name setter for both first and last name
     * @param lastName of type String
     */
    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    /**
     * name getter for both first and last name
     * @return concatenated String of first nd last name
     */
    public String getName() {
        return firstName+" "+lastName;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return  this.lastName;
    }

    /**
     * dateOfBirth setter
     * @param dateOfBirth of type String
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * dateOfBirth getter
     * @return dateOfBirth of type String
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * phoneNumber setter
     * @param phoneNumber of type String
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * phoneNumber getter
     * @return phoneNumber of type String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * identificationNum setter
     * @param identificationNum of type String
     */
    public void setIdentificationNum(int identificationNum) {
        this.identificationNum = identificationNum;
    }

    /**
     * identificationNum getter
     * @return identificationNUm of type String
     */
    public int getIdentificationNum() {
        return identificationNum;
    }

    public void setAddress(String address){this.address=address;}

    public String getAddress(){return this.address;}
}

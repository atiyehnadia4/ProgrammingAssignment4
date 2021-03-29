import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The RunBank class consists of static methods that operate on or return objects pertaining to another class.
 * The Class functions as a user interface for the user to be able to input answers, this class then stores and manipulates the data to output Success or Error messages.
 * RunBank also has the ability to read and write to a CSV file as well as write to a txt file to let user know what transactions they have performed and update the balances in a .csv file after the program is done running.
 *
 *
 * @author Nadia Atiyeh
 * @version 3.0
 * @since March 15,2021
 *
 */

public class RunBank{
    /**
     * Running num for new accounts added to hashtables
     */
    private static int runningNewAccountNums = 107;
    /**
     * Running num for account ids added to hashtables
     */
    private static int runningNewAccountID = 108;
    /**
     * Universal hashtable for use around runbank
     */
    private static Hashtable<String, Customer> users;
    /**
     * Universal BankStatement for use around runbank
     */
    private static BankStatement userBankStatement;


    public static void main(String[] args) throws IOException {
        File tranactionLog = new File("transactionLog.txt");
        clearFile(tranactionLog);
        users = readCSV(new File("bankUSers.csv"));
        mainMenu();

    }

    /**
     * Shows the user the mainMenu prompting them to enter their preferences, and redirects
     * them to the proper menu.
     *
     *
     * @throws InputMismatchException finds outputs that differ from what is asked and rejects them
     * @throws FileNotFoundException stops program if file that is being manipulated is not found or does not exist
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    public static void mainMenu() throws InputMismatchException, FileNotFoundException, IOException{
        System.out.println("Welcome to Bank of OOP! Before we start are these transactions for a single person, or between two people (Please answer with 1 - 3) ");
        Scanner intInput = new Scanner(System.in);
        Scanner stringScanner = new Scanner(System.in);
        boolean goOn = true;
        int answer;
        try{
            while(goOn){
                System.out.println("1. Single Person");
                System.out.println("2. Two People");
                System.out.println("3. Bank Manager");
                System.out.println("4. Transaction Reader");
                System.out.println("5. Exit");
                System.out.print("Answer : ");
                answer = intInput.nextInt();

                if(answer == 1) {
                    System.out.println();
                    System.out.println("First we will need some information");
                    System.out.println("Please enter your full name");
                    String userFullName = stringScanner.nextLine();
                    System.out.println("Please enter your identification number.");
                    int idNum = intInput.nextInt();
                    if(users.containsKey(userFullName)){
                        Customer currUser = users.get(userFullName);
                        if(currUser.getIdentificationNumber() == idNum){
                            currUser.setFullName(userFullName);
                            onePersonMenu(currUser);
                            break;
                        }
                    }
                    else {
                        System.out.println("Could not authenticate user. Will now exit to main menu");
                        mainMenu();
                    }
                }
                if(answer == 2) {
                    System.out.println();
                    System.out.println("First we will need some information");
                    System.out.println("Please enter your full name");
                    String userFullName = stringScanner.nextLine();
                    System.out.println("Please enter your identification number.");
                    int idNum = intInput.nextInt();

                    System.out.println();
                    System.out.println("Please enter your full name (RECIPIENT)");
                    String recipientFullName = stringScanner.nextLine();

                    if(users.containsKey(userFullName) && users.containsKey(recipientFullName)){
                        Customer currUser = users.get(userFullName);
                        Customer currRecipient = users.get(recipientFullName);
                        if(currUser.getIdentificationNumber() == idNum){
                            currUser.setFullName(userFullName);
                            currRecipient.setFullName(recipientFullName);
                            twoPersonMenu(currUser, currRecipient);
                            break;
                        }
                    }
                    else{
                        System.out.println("Could not authenticate users. Will now exit to main menu");
                        mainMenu();
                    }
                }
                if(answer == 3){
                    System.out.println("Welcome to Bank Manager Checkpoint, these are your options");
                    bankManagerMenu();
                    break;

                }
                if(answer == 4){
                    transactionReaderMenu(new File("transactionActions.csv"));
                }
                if (answer == 5) {
                    System.out.println("Thank you for choosing Bank of OOP, we hope to see you soon! :)");
                    goOn = false;
                }
                else {
                    System.out.println("I didnt quite understand you, please try again.");
                    mainMenu();
                }
            }
            writeToCSV(users);
            System.exit(0);

        } catch (InputMismatchException e){
            System.out.println("Sorry, I didn't quite understand you, I will now exit. Thank you!");
            writeToCSV(users);
            System.exit(0);
        }
    }

    /**
     * Shows the user the onePersonMenu, lists prompts to input their data, and redirects user to the correct transaction menu based on the option they indicated
     *
     * @param currUser takes in the Customer object of the currentUser
     * @throws FileNotFoundException stops program if file that is being manipulated is not found or does not exist
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    public static void onePersonMenu(Customer currUser) throws FileNotFoundException, IOException{
        Scanner intScanner = new Scanner(System.in);

        System.out.println("Welcome " + currUser.getFullName());
        System.out.println("These are your options (Please answer with 1 - 5)");
        System.out.println("1. Balance Inquiry");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Return to Main Menu");
        System.out.println("6. Exit");
        System.out.print("Answer: ");
        int userAnswer = intScanner.nextInt();
        System.out.println();

        if(userAnswer == 1){
            inquiryMenu(currUser);
        }

        if(userAnswer == 2){
            depositMenu(currUser);
        }

        if(userAnswer == 3){
            withdrawMenu(currUser);
        }

        if(userAnswer == 4){
            transferMenu(currUser);
        }

        if(userAnswer == 5){
            mainMenu();
        }

        if(userAnswer == 6){
            System.out.println("Thank you for choosing Bank of OOP, we hope to see you soon! :)");
            writeToCSV(users);
            System.exit(0);
        }

        else{
            System.out.println("I didnt quite understand you, please try again");
            onePersonMenu(currUser);
        }
    }

    /**
     * Shows the user the twoPersonMenu, lists prompts to input their data, and redirects user to the correct transaction menu based on the option they indicated
     *
     * @param currUser takes in the Customer object of the currentUser
     * @param recipient takes in the Customer object of the recipient
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    public static void twoPersonMenu(Customer currUser, Customer recipient) throws IOException{
        Scanner intScanner = new Scanner(System.in);

        System.out.println("Welcome " + currUser.getFullName());
        System.out.println("These are your options (Please answer with 1 - 3)");
        System.out.println("1. Payment");
        System.out.println("2. Return to Main Menu");
        System.out.println("3. Exit");
        System.out.print("Answer: ");
        int userAnswer = intScanner.nextInt();
        System.out.println();

        if(userAnswer == 1){
            paymentMenu(currUser, recipient);
        }

        if(userAnswer == 2){
            mainMenu();
        }

        if(userAnswer == 3){
            System.out.println("Thank you for choosing Bank of OOP, we hope to see you soon! :)");
            writeToCSV(users);
            System.exit(0);
        }
        else{
            System.out.println("I didnt quite understand you, please try again");
            twoPersonMenu(currUser, recipient);
        }

    }

    /**
     * Shows the user the bankMangerMenu, lists prompts to input their data, and redirects user to the menu based on the option they indicated,
     * or displays the information they requested
     *
     * @throws FileNotFoundException stops program if file that is being manipulated is not found or does not exist
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    public static void bankManagerMenu() throws FileNotFoundException, IOException {
        Scanner intScanner = new Scanner(System.in);
        Scanner stringScanner = new Scanner(System.in);
        System.out.println("1. Inquire account by Name");
        System.out.println("2. Inquire account by type and number");
        System.out.println("3. Inquire all");
        System.out.println("4. Add Account");
        System.out.println("5. Write Bank Statement");
        System.out.println("6. Back to Main Menu");
        System.out.print("Answer: ");
        int choice = intScanner.nextInt();
        System.out.println();

        if(choice == 1){
            System.out.println("Whose account would you like to inquire about");
            String userName = stringScanner.nextLine();
            Customer user = users.get(userName);
            user.setFullName(userName);
            if(user.getFullName().equals(userName)){
                user.getUserInformation(userName, 0);
            }
            System.out.println();
            System.out.println("Would you like to inquire about another account (Enter: yes, no)");
            String answer = stringScanner.nextLine();
            if(answer.equalsIgnoreCase("yes")){
                bankManagerMenu();
            }
            if(answer.equalsIgnoreCase("no")){
                System.out.println("Returning to main menu");
                mainMenu();
            }
        }
        if(choice == 2) {
            System.out.println("What is the account type (Enter: Checking, Savings, Credit)");
            String accountType = stringScanner.nextLine();
            System.out.println("What is the account number");
            int accountNum = intScanner.nextInt();
            if (accountType.equalsIgnoreCase("checking")) {
                for(String key: users.keySet()){
                    Customer curr = users.get(key);
                    if(curr.getCheckingAccounts().get(0).getAccountNumber() == accountNum){
                        curr.getUserInformation(key, 1);
                    }
                }
            }
            if (accountType.equalsIgnoreCase("savings")) {
                for(String key: users.keySet()){
                    Customer curr = users.get(key);
                    if(curr.getSavingsAccounts().get(0).getAccountNumber() == accountNum){
                        curr.getUserInformation(key, 2);
                    }
                }
            }
            if (accountType.equalsIgnoreCase("credit")) {
                for(String key: users.keySet()){
                    Customer curr = users.get(key);
                    if(curr.getCreditAccounts().get(0).getAccountNumber() == accountNum){
                        curr.getUserInformation(key, 3);
                    }
                }
            }
            System.out.println();
            System.out.println("Would you like to inquire about another account (Enter: yes, no)");
            String answer = stringScanner.nextLine();
            if(answer.equalsIgnoreCase("yes")){
                bankManagerMenu();
            }
            if(answer.equalsIgnoreCase("no")){
                System.out.println("Returning to main menu");
                mainMenu();
            }
        }
        if(choice == 3){
            for(String key: users.keySet()){
                System.out.println("Name: " + key);
                Customer user = users.get(key);
                user.getUserInformation(key, 4);
                System.out.println();
            }

            System.out.println();
            System.out.println("Would you like to inquire about another account (Enter: yes, no)");
            String answer = stringScanner.nextLine();
            if(answer.equalsIgnoreCase("yes")){
                bankManagerMenu();
            }
            if(answer.equalsIgnoreCase("no")){
                System.out.println("Returning to main menu");
                mainMenu();
            }
        }
        if(choice == 4){
            bankUserMenu();
            System.out.println();
            System.out.println("Would you like to inquire about another account (Enter: yes, no)");
            String answer = stringScanner.nextLine();
            if(answer.equalsIgnoreCase("yes")){
                bankManagerMenu();
            }
            if(answer.equalsIgnoreCase("no")){
                System.out.println("Returning to main menu");
                mainMenu();
            }
        }
        if(choice == 5){
            writeBankStatement();
        }
        if(choice == 6){
            mainMenu();
        }
    }

    /**
     * Creates a new User from the console and adds it to the hashtable
     * @throws IOException
     * @throws InputMismatchException
     */
    public static void bankUserMenu() throws IOException, InputMismatchException{
        Scanner intScanner = new Scanner(System.in);
        Scanner stringScanner = new Scanner(System.in);
        System.out.println("To start we will need some information");
        System.out.println("Please input your Full Name: ");
        String userName = stringScanner.nextLine();
        if(users.containsKey(userName)){
            System.out.println("User already exists.");
            bankManagerMenu();
        }
        System.out.println("Please enter your Date of Birth: ");
        String userDOB = stringScanner.nextLine();
        System.out.println("Please enter your Phone Number: ");
        String userPhoneNum = stringScanner.nextLine();
        System.out.println("Please enter your Address");
        String userAddress = stringScanner.nextLine();

        Customer newUser = new Customer();
        newUser.setFullName(userName);
        newUser.setDateOfBirth(userDOB);
        newUser.setPhoneNumber(userPhoneNum);
        newUser.setIdentificationNumber(runningNewAccountID);
        newUser.setAddress(userAddress);

        Savings userSavings = new Savings();
        userSavings.setAccountNumber(2000 + runningNewAccountNums);
        userSavings.setAccountType(2);
        System.out.println("Enter your starting balance");
        double userSavingsStartBal = intScanner.nextDouble();
        userSavings.setCurrentBalance(userSavingsStartBal);
        newUser.setSavingsAccounts(userSavings);
        System.out.println("Savings Account Created");


        boolean goOn = true;

        while(goOn){
            System.out.println("Would you like to create another account (Yes or No) ");
            String answer = stringScanner.nextLine();
            if (answer.equalsIgnoreCase("Yes")) {
                System.out.println("Please enter which other account (Checking or Credit)");
                String newAccount = stringScanner.nextLine();

                if (newAccount.equalsIgnoreCase("Checking") && newUser.getCheckingAccounts().size() < 1) {
                    Checking userChecking = new Checking();
                    userChecking.setAccountNumber(1000 + runningNewAccountNums);
                    userChecking.setAccountType(1);
                    System.out.println("Enter your starting balance");
                    double userCheckingsStartBal = intScanner.nextDouble();
                    userChecking.setCurrentBalance(userCheckingsStartBal);
                    System.out.println("Checking Account Created");
                    newUser.setCheckingAccounts(userChecking);
                    goOn = true;
                }
                if (newAccount.equalsIgnoreCase("Credit") && newUser.getCreditAccounts().size() < 1) {
                    Credit userCredit = new Credit();
                    userCredit.setAccountNumber(3000 + runningNewAccountNums);
                    userCredit.setAccountType(3);
                    userCredit.setCreditMax(1000);
                    System.out.println("Max Credit is: " + userCredit.getCreditMax());
                    userCredit.setCurrentBalance(0);
                    System.out.println("Credit Account Created");
                    newUser.setCreditAccounts(userCredit);
                    goOn = true;
                }
            }
            if(answer.equalsIgnoreCase("No")){
                goOn = false;
            }
        }


        users.put(userName, newUser);
        runningNewAccountNums++;
        runningNewAccountID++;
        System.out.println("Going back to bank manager menu. Thank you!");
        bankManagerMenu();

    }

    /**
     * Writes a bank statment for specified user user BankStatment Class
     * @throws IOException
     */
    public static void writeBankStatement() throws IOException{
        Scanner strScan = new Scanner(System.in);
        System.out.println("Please enter the name of the person, that will generate a bank statement");
        String fullName = strScan.nextLine();

        if(users.containsKey(fullName)){
            Customer currUser = users.get(fullName);
            currUser.setFullName(fullName);
            String [] fullNameArr = fullName.split(" ");
            String fileName = fullNameArr[1]+fullNameArr[0]+"BankStatement.txt";
            File bankStmtFile = new File(fileName);
            BankStatement userBankStatement = new BankStatement(currUser, bankStmtFile);
            userBankStatement.writeToBankStatement();
        }
        else{
            System.out.println("Authentication Failed, going back to main menu");
            bankManagerMenu();
        }

    }

    /**
     *  Displays to the users the inquiryMenu, lists prompts to the user to input their account data, and displays the current
     *  balance for their chosen account
     *
     * @param currUser takes in the Customer object of the currentUser
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    public static void inquiryMenu(Customer currUser) throws IOException{
        File transactionLog = new File("transactionLog.txt");

        int userAccountType = chooseAccount(1);
        System.out.println();

        if(userAccountType == 1 || userAccountType == 2 || userAccountType == 3){
            Checking userC = currUser.getCheckingAccounts().get(0);
            Savings userS = currUser.getSavingsAccounts().get(0);
            Credit userCR = currUser.getCreditAccounts().get(0);
            int pin = inputPin();

            if(userAccountType == 1){
                userC.balanceInquiry(currUser,pin);
                messagesToWriteToCSV(currUser, null, userC, null, transactionLog, 0, 1);

            }

            if(userAccountType == 2){
                userS.balanceInquiry(currUser,pin);
                messagesToWriteToCSV(currUser, null, userS, null, transactionLog, 0, 1);
            }

            if(userAccountType == 3){
                userCR.balanceInquiry(currUser,pin);
                messagesToWriteToCSV(currUser, null, userCR, null,  transactionLog, 0, 1);
            }

            rerunMethodCode(currUser,1);
        }

        if(userAccountType == 4){
            System.out.println("Returning to main menu.");
            onePersonMenu(currUser);
        }

        if(userAccountType == 5){
            System.out.println("Thank you for choosing Bank of OOP, we hope to see you soon! :)");
            writeToCSV(users);
            System.exit(0);
        }
    }

    /**
     * Displays to the users the depositMenu, lists prompts to the user to input their account data, validates the date and displays
     * a success or error message when a user tries to deposit to a certain account.
     *
     * @param currUser takes in the Customer object of the currentUser
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    public static void depositMenu(Customer currUser) throws IOException{
        Scanner intScanner = new Scanner(System.in);
        int userAccountType = chooseAccount(2);
        File transactionLog = new File("transactionLog.txt");

        if(userAccountType == 1 || userAccountType == 2 || userAccountType == 3) {
            Checking userC = currUser.getCheckingAccounts().get(0);
            Savings userS = currUser.getSavingsAccounts().get(0);
            Credit userCR = currUser.getCreditAccounts().get(0);
            System.out.print("Enter an amount to deposit: ");
            double amountIn = intScanner.nextDouble();

            if(userAccountType == 1){
                if(userC.deposit(amountIn)){
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountIn, 2);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountIn, 6);
                }
            }

            if(userAccountType == 2){
                if(userS.deposit(amountIn)) {
                    messagesToWriteToCSV(currUser, null, userS, null, transactionLog, amountIn, 2);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountIn, 6);
                }
            }

            if(userAccountType == 3){
                if(userCR.deposit(amountIn)){
                    messagesToWriteToCSV(currUser, null, userCR, null, transactionLog, amountIn, 2);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountIn, 6);
                }
            }
            rerunMethodCode(currUser,2);
        }

        if(userAccountType == 4){
            System.out.println("Returning to main menu.");
            onePersonMenu(currUser);
        }

        if(userAccountType == 5){
            System.out.println("Thank you for choosing Bank of OOP, we hope to see you soon! :)");
            writeToCSV(users);
            System.exit(0);
        }

    }

    /**
     * Displays to the users the withdrawMenu, lists prompts for the user to input their account data, validates it and displays a Success or
     * Error message when an amount is withdrawn from the selected account
     *
     * @param currUser takes in the Customer object of the currentUser
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    public static void withdrawMenu(Customer currUser) throws IOException{
        Scanner intScanner = new Scanner(System.in);
        int userAccountType = chooseAccount(3);
        File transactionLog = new File("transactionLog.txt");

        if(userAccountType == 1 || userAccountType == 2 || userAccountType == 3){
            Checking userC = currUser.getCheckingAccounts().get(0);
            Savings userS = currUser.getSavingsAccounts().get(0);
            Credit userCR = currUser.getCreditAccounts().get(0);
            System.out.print("Enter an amount to withdraw: ");
            double amountOut = intScanner.nextDouble();

            if(userAccountType == 1){
                if(userC.withdraw(amountOut)){
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 3);
                }else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                }
            }

            if(userAccountType == 2){
                if(userS.withdraw(amountOut)){
                    messagesToWriteToCSV(currUser, null,  userS, null, transactionLog, amountOut, 3);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                }
            }

            if(userAccountType == 3){
                if(userCR.withdraw(amountOut)){
                    messagesToWriteToCSV(currUser, null, userCR, null, transactionLog, amountOut, 3);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                }
            }

            rerunMethodCode(currUser,3);
        }

        if(userAccountType == 4){
            System.out.println("Returning to main menu.");
            onePersonMenu(currUser);
        }

        if(userAccountType == 5){
            System.out.println("Thank you for choosing Bank of OOP, we hope to see you soon! :)");
            writeToCSV(users);
            System.exit(0);
        }
    }

    /**
     * Displays to the users the transferMenu, lists prompts for the user to input their account data, validates it and displays a success or
     * error message when a transfer is made between two of the current users accounts
     *
     * @param currUser takes in the Customer object of the currentUser
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    public static void transferMenu(Customer currUser) throws IOException{
        Scanner intScanner = new Scanner(System.in);
        File transactionLog = new File("transactionLog.txt");

        int userAccountType1 = chooseAccount(4);
        System.out.print("Account 2: ");
        int userAccountType2 = intScanner.nextInt();
        System.out.println();

        if(userAccountType1 == userAccountType2){
            if(userAccountType1 == 4 && userAccountType2 == 4){
                System.out.println("Returning to main menu.");
                onePersonMenu(currUser);
            }
            if(userAccountType1 == 5 && userAccountType2 == 5){
                System.out.println("Thank you for choosing Bank of OOP, we hope to see you soon! :)");
                writeToCSV(users);
                System.exit(0);
            }

            System.out.println("Error: Cannot Transfer to same account");
            transferMenu(currUser);
        }

        if(userAccountType1 == 4 && userAccountType2 == 5 || userAccountType1 == 5 && userAccountType2 == 4){
            if(userAccountType1 == 4){
                System.out.println("Returning to main menu.");
                onePersonMenu(currUser);
            }

            if(userAccountType1 == 5){
                System.out.println("Thank you for choosing Bank of OOP, we hope to see you soon! :)");
                writeToCSV(users);
                System.exit(0);
            }
        }

        else{
            System.out.print("Enter an amount to transfer: ");
            double amountOut = intScanner.nextDouble();
            Checking userC = currUser.getCheckingAccounts().get(0);
            Savings userS = currUser.getSavingsAccounts().get(0);
            Credit userCR = currUser.getCreditAccounts().get(0);

            if (userAccountType1 == 1 || userAccountType1 == 2 || userAccountType1 == 3){
                if(userAccountType1 == 1){
                    if(userAccountType2 == 2){
                        if(userC.transfer(amountOut, userS)){
                            messagesToWriteToCSV(currUser, null, userS, userC, transactionLog, amountOut, 4);
                        }
                        else{
                            messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                        }
                    }
                    if(userAccountType2 == 3){
                        if(userC.transfer(amountOut, userCR)){
                            messagesToWriteToCSV(currUser, null, userCR, userC, transactionLog, amountOut, 4);
                        }
                        else{
                            messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                        }
                    }
                }

                if(userAccountType1 == 2){
                    if(userAccountType2 == 1){
                        if(userS.transfer(amountOut, userC)){
                            messagesToWriteToCSV(currUser, null, userC, userS, transactionLog, amountOut, 4);
                        }
                        else{
                            messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                        }

                    }
                    if(userAccountType2 == 3){
                        if(userS.transfer(amountOut, userCR)){
                            messagesToWriteToCSV(currUser, null, userCR, userS, transactionLog, amountOut, 4);
                        }
                        else{
                            messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                        }
                    }
                }

                if(userAccountType1 == 3){
                    if(userAccountType2 == 1){
                        if(userCR.transfer(amountOut, userC)) {
                            messagesToWriteToCSV(currUser, null, userC, userCR, transactionLog, amountOut, 4);
                        }
                        else{
                            messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                        }
                    }
                    if(userAccountType2 == 2){
                        if(userCR.transfer(amountOut, userS)) {
                            messagesToWriteToCSV(currUser, null, userS, userCR, transactionLog, amountOut, 4);
                        }
                        else{
                            messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                        }
                    }
                }
                rerunMethodCode(currUser, 4);
            }
        }
    }

    /**
     * Displays to the users the paymentMenu, lists prompts for the user to input the account data for both the main user and the recipient, and displays a success or failure
     * message when a payment is made from one user to the other
     *
     * @param currUser takes in the Customer object of the currentUser
     * @param recipient takes in the Customer object of the recipient
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    public static void paymentMenu(Customer currUser, Customer recipient) throws IOException{
        Scanner intScanner = new Scanner(System.in);
        File transactionLog = new File("transactionLog.txt");
        System.out.println("USER");
        int userAccountType = chooseAccount(5);
        System.out.println();


        System.out.println("RECIPIENT");
        int recipientAccountType = chooseAccount(5);
        System.out.println();


        if(userAccountType == 1 || userAccountType == 2 || userAccountType == 3){
            System.out.print("Enter an amount to pay: ");
            double amountOut = intScanner.nextDouble();
            Checking userC = currUser.getCheckingAccounts().get(0);
            Savings userS = currUser.getSavingsAccounts().get(0);
            Credit userCR = currUser.getCreditAccounts().get(0);

            Checking recipientC = recipient.getCheckingAccounts().get(0);
            Savings recipientS = recipient.getSavingsAccounts().get(0);
            Credit recipientCR = recipient.getCreditAccounts().get(0);
            if(userAccountType == 1){
                if(recipientAccountType == 1){
                    if(userC.payment(amountOut,recipientC)) {
                        messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 5 );
                    }
                    else{
                        messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                    }
                }
                if(recipientAccountType == 2){
                    if(userC.payment(amountOut, recipientS)){
                        messagesToWriteToCSV(currUser, recipient,recipientS, userC, transactionLog, amountOut, 5 );
                    }
                    else{
                        messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                    }
                }
                if(recipientAccountType == 3){
                    if(userC.payment(amountOut, recipientCR)){
                        messagesToWriteToCSV(currUser, recipient,recipientCR, userC, transactionLog, amountOut, 5 );
                    }
                    else{
                        messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                    }
                }
            }
            if(userAccountType == 2){
                if(recipientAccountType == 1){
                    if(userS.payment(amountOut, recipientC)) {
                        messagesToWriteToCSV(currUser, recipient,recipientC, userS, transactionLog, amountOut, 5 );
                    }
                    else{
                        messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                    }
                }
                if(recipientAccountType == 2){
                    if(userS.payment(amountOut,recipientS)) {
                        messagesToWriteToCSV(currUser, recipient,recipientS, userS, transactionLog, amountOut, 5 );
                    }
                    else{
                        messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                    }
                }
                if(recipientAccountType == 3){
                    if(userS.payment(amountOut,recipientCR)) {
                        messagesToWriteToCSV(currUser, recipient,recipientCR, userS, transactionLog, amountOut, 5 );
                    }
                    else{
                        messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                    }
                }
            }
            if(userAccountType == 3){
                if(recipientAccountType == 1){
                    if(userCR.payment(amountOut,recipientC)) {
                        messagesToWriteToCSV(currUser, recipient, recipientC, userCR, transactionLog, amountOut, 5 );
                    }
                    else{
                        messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                    }
                }
                if(recipientAccountType == 2){
                    if(userCR.payment(amountOut,recipientS)){
                        messagesToWriteToCSV(currUser, recipient, recipientS, userCR, transactionLog, amountOut, 5 );
                    }
                    else{
                        messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                    }
                }
                if(recipientAccountType == 3){
                    if(userCR.payment(amountOut,recipientCR)) {
                        messagesToWriteToCSV(currUser, recipient, recipientCR, userCR, transactionLog, amountOut, 5);
                    }
                    else{
                        messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                    }
                }
            }

            rerunMethodCodeTwoPersonMenu(currUser, recipient, 5);
        }

        if(userAccountType == 4){
            System.out.println("Returning to main menu.");
            twoPersonMenu(currUser, recipient);
        }

        if(userAccountType == 5){
            System.out.println("Thank you for choosing Bank of OOP, we hope to see you soon! :)");
            writeToCSV(users);
            System.exit(0);
        }
    }

    /**
     * Transfer menu for transaction actions
     * @param currUser
     * @param accountFrom
     * @param accountTo
     * @param amountOut
     * @param transactionLog
     * @throws IOException
     */
    public static void transferMenu(Customer currUser, String accountFrom, String accountTo, double amountOut, File transactionLog) throws IOException {
        Checking userC = currUser.getCheckingAccounts().get(0);
        Savings userS = currUser.getSavingsAccounts().get(0);
        Credit userCR = currUser.getCreditAccounts().get(0);



        int userAccountType1 = getAccountFromString(accountFrom);
        int userAccountType2 = getAccountFromString(accountTo);
        if(userAccountType1 == userAccountType2){
            System.out.println("Cannot Perform Transaction");
        }

        if(userAccountType1 == 1){
            if(userAccountType2 == 2){
                if(userC.transfer(amountOut, userS)){
                    messagesToWriteToCSV(currUser, null, userS, userC, transactionLog, amountOut, 4);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                }
            }
            if(userAccountType2 == 3){
                if(userC.transfer(amountOut, userCR)){
                    messagesToWriteToCSV(currUser, null, userCR, userC, transactionLog, amountOut, 4);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                }
            }
        }

        if(userAccountType1 == 2){
            if(userAccountType2 == 1){
                if(userS.transfer(amountOut, userC)){
                    messagesToWriteToCSV(currUser, null, userC, userS, transactionLog, amountOut, 4);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                }

            }
            if(userAccountType2 == 3){
                if(userS.transfer(amountOut, userCR)){
                    messagesToWriteToCSV(currUser, null, userCR, userS, transactionLog, amountOut, 4);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                }
            }
        }

        if(userAccountType1 == 3){
            if(userAccountType2 == 1){
                if(userCR.transfer(amountOut, userC)) {
                    messagesToWriteToCSV(currUser, null, userC, userCR, transactionLog, amountOut, 4);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                }
            }
            if(userAccountType2 == 2){
                if(userCR.transfer(amountOut, userS)) {
                    messagesToWriteToCSV(currUser, null, userS, userCR, transactionLog, amountOut, 4);
                }
                else{
                    messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
                }
            }
        }
    }

    /**
     * Payment Menu for transaction Actions
     * @param currUser
     * @param recipient
     * @param accountFrom
     * @param accountTo
     * @param amountOut
     * @param transactionLog
     * @throws IOException
     */
    public static void paymentMenu(Customer currUser, Customer recipient, String accountFrom, String accountTo, double amountOut, File transactionLog) throws IOException {
        Checking userC = currUser.getCheckingAccounts().get(0);
        Savings userS = currUser.getSavingsAccounts().get(0);
        Credit userCR = currUser.getCreditAccounts().get(0);

        Checking recipientC = recipient.getCheckingAccounts().get(0);
        Savings recipientS = recipient.getSavingsAccounts().get(0);
        Credit recipientCR = recipient.getCreditAccounts().get(0);
        int userAccountType = getAccountFromString(accountFrom);
        int recipientAccountType = getAccountFromString(accountTo);

        if(userAccountType == 1){
            if(recipientAccountType == 1){
                if(userC.payment(amountOut,recipientC)) {
                    messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 5 );
                }
                else{
                    messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                }
            }
            if(recipientAccountType == 2){
                if(userC.payment(amountOut, recipientS)){
                    messagesToWriteToCSV(currUser, recipient,recipientS, userC, transactionLog, amountOut, 5 );
                }
                else{
                    messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                }
            }
            if(recipientAccountType == 3){
                if(userC.payment(amountOut, recipientCR)){
                    messagesToWriteToCSV(currUser, recipient,recipientCR, userC, transactionLog, amountOut, 5 );
                }
                else{
                    messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                }
            }
        }
        if(userAccountType == 2){
            if(recipientAccountType == 1){
                if(userS.payment(amountOut, recipientC)) {
                    messagesToWriteToCSV(currUser, recipient,recipientC, userS, transactionLog, amountOut, 5 );
                }
                else{
                    messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                }
            }
            if(recipientAccountType == 2){
                if(userS.payment(amountOut,recipientS)) {
                    messagesToWriteToCSV(currUser, recipient,recipientS, userS, transactionLog, amountOut, 5 );
                }
                else{
                    messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                }
            }
            if(recipientAccountType == 3){
                if(userS.payment(amountOut,recipientCR)) {
                    messagesToWriteToCSV(currUser, recipient,recipientCR, userS, transactionLog, amountOut, 5 );
                }
                else{
                    messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                }
            }
        }
        if(userAccountType == 3){
            if(recipientAccountType == 1){
                if(userCR.payment(amountOut,recipientC)) {
                    messagesToWriteToCSV(currUser, recipient, recipientC, userCR, transactionLog, amountOut, 5 );
                }
                else{
                    messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                }
            }
            if(recipientAccountType == 2){
                if(userCR.payment(amountOut,recipientS)){
                    messagesToWriteToCSV(currUser, recipient, recipientS, userCR, transactionLog, amountOut, 5 );
                }
                else{
                    messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                }
            }
            if(recipientAccountType == 3){
                if(userCR.payment(amountOut,recipientCR)) {
                    messagesToWriteToCSV(currUser, recipient, recipientCR, userCR, transactionLog, amountOut, 5);
                }
                else{
                    messagesToWriteToCSV(currUser, recipient,recipientC, userC, transactionLog, amountOut, 6 );
                }
            }
        }

    }

    /**
     * Inquiry menu for transaction actions
     * @param currUser
     * @param account
     * @param transactionLog
     * @throws IOException
     */
    public static void inquiryMenu(Customer currUser, String account, File transactionLog) throws IOException {
        Checking userC = currUser.getCheckingAccounts().get(0);
        Savings userS = currUser.getSavingsAccounts().get(0);
        Credit userCR = currUser.getCreditAccounts().get(0);

        int userAccountType = getAccountFromString(account);

        if(userAccountType == 1){
            userC.balanceInquiry(currUser);
            messagesToWriteToCSV(currUser, null, userC, null, transactionLog, 0, 1);

        }

        if(userAccountType == 2){
            userS.balanceInquiry(currUser);
            messagesToWriteToCSV(currUser, null, userS, null, transactionLog, 0, 1);
        }

        if(userAccountType == 3){
            userCR.balanceInquiry(currUser);
            messagesToWriteToCSV(currUser, null, userCR, null,  transactionLog, 0, 1);
        }
    }

    /**
     * Deposit menu for transaction actions
     * @param currUser
     * @param account
     * @param amount
     * @param transactionLog
     * @throws IOException
     */
    public static void depositMenu(Customer currUser, String account, double amount, File transactionLog) throws IOException {
        Checking userC = currUser.getCheckingAccounts().get(0);
        Savings userS = currUser.getSavingsAccounts().get(0);
        Credit userCR = currUser.getCreditAccounts().get(0);
        double amountIn = amount;
        int userAccountType = getAccountFromString(account);

        if(userAccountType == 1){
            if(userC.deposit(amountIn)){
                messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountIn, 2);
            }
            else{
                messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountIn, 6);
            }
        }

        if(userAccountType == 2){
            if(userS.deposit(amountIn)) {
                messagesToWriteToCSV(currUser, null, userS, null, transactionLog, amountIn, 2);
            }
            else{
                messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountIn, 6);
            }
        }

        if(userAccountType == 3){
            if(userCR.deposit(amountIn)){
                messagesToWriteToCSV(currUser, null, userCR, null, transactionLog, amountIn, 2);
            }
            else{
                messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountIn, 6);
            }
        }
    }

    /**
     * Withdraw menu for transaction actions
     * @param currUser
     * @param account
     * @param amount
     * @param transactionLog
     * @throws IOException
     */
    public static void withdrawMenu(Customer currUser, String account, double amount, File transactionLog) throws IOException {
        Checking userC = currUser.getCheckingAccounts().get(0);
        Savings userS = currUser.getSavingsAccounts().get(0);
        Credit userCR = currUser.getCreditAccounts().get(0);
        double amountOut = amount;
        int userAccountType =  getAccountFromString(account);

        if(userAccountType == 1){
            if(userC.withdraw(amountOut)){
                messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 3);
            }else{
                messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
            }
        }

        if(userAccountType == 2){
            if(userS.withdraw(amountOut)){
                messagesToWriteToCSV(currUser, null,  userS, null, transactionLog, amountOut, 3);
            }
            else{
                messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
            }
        }

        if(userAccountType == 3){
            if(userCR.withdraw(amountOut)){
                messagesToWriteToCSV(currUser, null, userCR, null, transactionLog, amountOut, 3);
            }
            else{
                messagesToWriteToCSV(currUser, null, userC, null, transactionLog, amountOut, 6);
            }
        }
    }

    /**
     * Reads csv file, parses and performs transactions specified on csv
     * @param transActions
     * @throws IOException
     */
    public static void transactionReaderMenu(File transActions) throws IOException {
        Scanner fileScanner = new Scanner(new FileInputStream(transActions));
        File transactionLog = new File("transactionLog.txt");
        fileScanner.nextLine(); // ignores first line
        while(fileScanner.hasNextLine()){
            String[] readInfo = fileScanner.nextLine().split(",");
            String action = readInfo[3];
            if(action.equalsIgnoreCase("Pays")){
                if(users.containsKey(readInfo[0] + " " + readInfo[1]) && users.containsKey(readInfo[4] + " " + readInfo[5])){
                    Customer sender = users.get(readInfo[0] + " " + readInfo[1]);
                    Customer receiver = users.get(readInfo[4] + " " + readInfo[5]);
                    String sendingAccount  = readInfo[2];
                    String receivingAccount = readInfo[6];
                    double amountSent = Double.parseDouble(readInfo[7]);
                    paymentMenu(sender, receiver, sendingAccount, receivingAccount, amountSent, transactionLog);
                }
            }
            if(action.equalsIgnoreCase("Transfers")){
                if(users.containsKey(readInfo[0] + " " + readInfo[1])){
                    Customer currUser = users.get(readInfo[0] + " " + readInfo[1]);
                    String sendingAccount  = readInfo[2];
                    String receivingAccount = readInfo[6];
                    double amountSent = Double.parseDouble(readInfo[7]);
                    transferMenu(currUser ,sendingAccount, receivingAccount, amountSent, transactionLog);
                }
            }

            if(action.equalsIgnoreCase("Inquires")){
                if(users.containsKey(readInfo[0] + " " + readInfo[1])){
                    Customer currUser = users.get(readInfo[0] + " " + readInfo[1]);
                    String account  = readInfo[2];
                    inquiryMenu(currUser , account, transactionLog);
                }
            }

            if(action.equalsIgnoreCase("Deposits")){
                if(users.containsKey(readInfo[4] + " " + readInfo[5])){
                    Customer currUser = users.get(readInfo[4] + " " + readInfo[5]);
                    String account  = readInfo[6];
                    double amount = Double.parseDouble(readInfo[7]);
                    depositMenu(currUser, account, amount, transactionLog);
                }
            }
            if(action.equalsIgnoreCase("Withdraws")){
                if(users.containsKey(readInfo[0] + " " + readInfo[1])){
                    Customer currUser = users.get(readInfo[0] + " " + readInfo[1]);
                    String account  = readInfo[2];
                    double amount = Double.parseDouble(readInfo[7]);
                    withdrawMenu(currUser, account, amount, transactionLog);
                }
            }
        }
    }

    /**
     * Reads csv file, parses and sorts data into Hashtable.
     *
     * @param file takes in file that is to be read
     * @return bankUsers Hashtable populated with data from the csv file
     * @throws FileNotFoundException stops program if file that is being manipulated is not found or does not exist
     */
    private static Hashtable<String, Customer> readCSV(File file) throws FileNotFoundException {
        // initializes a file scanner to read through the csv file
        Scanner input = new Scanner(new FileInputStream(file));

        // initializes a new hashtable that will map the users name to their corresponding checking account
        Hashtable<String, Customer> bankUsers = new Hashtable<>();
        String[] identifiers = input.nextLine().split(",");

        int addressIndex = 0;
        for(int i = 0; i < identifiers.length; i++){
            if(identifiers[i].equalsIgnoreCase("Address")){
                addressIndex = i;
            }
        }
        while (input.hasNextLine()) {
            String firstName = "";
            String lastName = "";
            String[] readInfo = input.nextLine().split(",");
            Customer user = new Customer();
            Checking userC = new Checking();
            Savings userS = new Savings();
            Credit userCR = new Credit();

            int j = 0;
            for(int i = 0; i < identifiers.length; i++){
                if(i <= addressIndex){
                    if (identifiers[i].equalsIgnoreCase("First Name")) {
                        user.setFirstName(readInfo[i]);
                        firstName = readInfo[i];
                    }
                    if (identifiers[i].equalsIgnoreCase("Last Name")) {
                        user.setLastName(readInfo[i]);
                        lastName = readInfo[i];
                    }
                    if (identifiers[i].equalsIgnoreCase("Date of Birth")) {
                        user.setDateOfBirth(readInfo[i]);
                    }
                    if (identifiers[i].equalsIgnoreCase("Identification Number")) {
                        user.setIdentificationNumber(Integer.parseInt(readInfo[i]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Address")) {
                        user.setAddress(readInfo[i] + readInfo[i + 1] + readInfo[i + 2]);
                    }
                    if (identifiers[i].equalsIgnoreCase("Phone Number")) {
                        user.setPhoneNumber(readInfo[i]);
                    }
                    if (identifiers[i].equalsIgnoreCase("Checking Account Number")) {
                        userC.setAccountType(1);
                        userC.setAccountNumber(Integer.parseInt(readInfo[i]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Savings Account Number")) {
                        userS.setAccountType(2);
                        userS.setAccountNumber(Integer.parseInt(readInfo[i]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Credit Account Number")) {
                        userCR.setAccountType(3);
                        userCR.setAccountNumber(Integer.parseInt(readInfo[i]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Checking Starting Balance")) {
                        userC.setCurrentBalance(Double.parseDouble(readInfo[i]));
                        userC.setStartingBalance(Double.parseDouble(readInfo[i]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Savings Starting Balance")) {
                        userS.setCurrentBalance(Double.parseDouble(readInfo[i]));
                        userS.setStartingBalance(Double.parseDouble(readInfo[i]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Credit Starting Balance")) {
                        userCR.setCurrentBalance(Double.parseDouble(readInfo[i]));
                        userCR.setStartingBalance(Double.parseDouble(readInfo[i]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Credit Max")) {
                        userCR.setCreditMax(Integer.parseInt(readInfo[i]));
                    }
                }
                else{
                    j = i + 2;
                    if (identifiers[i].equalsIgnoreCase("First Name")) {
                        user.setFirstName(readInfo[j]);
                        firstName = readInfo[j];
                    }
                    if (identifiers[i].equalsIgnoreCase("Last Name")) {
                        user.setLastName(readInfo[j]);
                        lastName = readInfo[j];
                    }
                    if (identifiers[i].equalsIgnoreCase("Date of Birth")) {
                        user.setDateOfBirth(readInfo[j]);
                    }
                    if (identifiers[i].equalsIgnoreCase("Identification Number")) {
                        user.setIdentificationNumber(Integer.parseInt(readInfo[j]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Phone Number")) {
                        user.setPhoneNumber(readInfo[j]);
                    }
                    if (identifiers[i].equalsIgnoreCase("Checking Account Number")) {
                        userC.setAccountType(1);
                        userC.setAccountNumber(Integer.parseInt(readInfo[j]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Savings Account Number")) {
                        userS.setAccountType(2);
                        userS.setAccountNumber(Integer.parseInt(readInfo[j]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Credit Account Number")) {
                        userCR.setAccountType(3);
                        userCR.setAccountNumber(Integer.parseInt(readInfo[j]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Checking Starting Balance")) {
                        userC.setCurrentBalance(Double.parseDouble(readInfo[j]));
                        userC.setStartingBalance(Double.parseDouble(readInfo[j]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Savings Starting Balance")) {
                        userS.setCurrentBalance(Double.parseDouble(readInfo[j]));
                        userS.setStartingBalance(Double.parseDouble(readInfo[j]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Credit Starting Balance")) {
                        userCR.setCurrentBalance(Double.parseDouble(readInfo[j]));
                        userCR.setStartingBalance(Double.parseDouble(readInfo[j]));
                    }
                    if (identifiers[i].equalsIgnoreCase("Credit Max")) {
                        userCR.setCreditMax(Integer.parseInt(readInfo[j]));
                    }
                }

            }

            user.setFullName(firstName + " " + lastName);
            user.setCheckingAccounts(userC);
            user.setSavingsAccounts(userS);
            user.setCreditAccounts(userCR);
            bankUsers.put(firstName + " " + lastName, user);
        }
        return bankUsers;
    }

    /**
     * Clears the transaction log of any writing to start with a fresh log
     *
     * @param transactionFile takes in the file that is to be cleared
     */
    private static void clearFile(File transactionFile){
        if(transactionFile.delete()){
            System.out.println("File cleared");
        }
        else{
            System.out.println("File not cleared");
        }

    }

    /**
     * Writes message to file.
     *
     * @param message takes in a messgae that is to be written to the transaction log
     * @param transactionFile the file that is to be written to
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    private static void writeToFile(String message, File transactionFile) throws IOException{
        // uses fileWriter to access the transactionFile that is passed in the method and permits it to be modified
        FileWriter fw = new FileWriter(transactionFile, true);
        // uses printwriter to format text
        PrintWriter pw = new PrintWriter(fw);

        //Checks if file is empty, and if it is uses the .write function as its the first line in the txt file
        if(transactionFile.length() == 0){
            pw.write(message);
            pw.println();
        }

        //otherwise it will use the .append function to add onto every other line. This allows for continuous file writing
        else{
            pw.append(message);
            pw.println();
        }

        //closes the stream
        pw.close();
    }

    /**
     * Generates a new csv file with the updated balances
     *
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    private static void writeToCSV(Hashtable<String, Customer> users) throws IOException {
        System.out.println("UPDATED CSV GENERATED");
        File csv = new File("bankUsersUpdated.csv");
        FileWriter fw = new FileWriter(csv);
        File ogCSV = new File("bankUsers.csv");
        Scanner input = new Scanner(ogCSV);

        String[] identifiers = input.nextLine().split(",");
        int addressIndex = 0;
        for (int i = 0; i < identifiers.length; i++) {
            if (identifiers[i].equalsIgnoreCase("Address")) {
                addressIndex = i;
            }
        }
        for(String key: identifiers){
            fw.append(key + ", ");
        }
        fw.append("\n"); // appends first line (CSV column names)

        String[] writeInfo = new String[identifiers.length];
        String address = "";
        String firstName = "";
        String lastName = "";
        String DOB = "";
        String id = "";
        String phoneNum = "";
        String checkingAccNum = "";
        String savingAccNum = "";
        String creditAccNum = "";
        String checkingBal = "";
        String savingBal = "";
        String creditBal = "";
        String creditMax = "";
        int j = 0;

        for (String key : users.keySet()) {
            Customer curr = users.get(key);
            String[] fullName = key.split(" ");
            firstName = fullName[0];
            lastName = fullName[1];
            DOB = curr.getDateOfBirth();
            id = String.valueOf(curr.getIdentificationNumber());
            address = curr.getAddress();
            phoneNum = curr.getPhoneNumber();
            checkingAccNum = String.valueOf(curr.getCheckingAccounts().get(0).getAccountNumber());
            checkingBal = String.valueOf(curr.getCheckingAccounts().get(0).getCurrentBalance());
            savingAccNum = String.valueOf(curr.getSavingsAccounts().get(0).getAccountNumber());
            savingBal = String.valueOf(curr.getSavingsAccounts().get(0).getCurrentBalance());
            creditAccNum = String.valueOf(curr.getCreditAccounts().get(0).getAccountNumber());
            creditBal = String.valueOf(curr.getCreditAccounts().get(0).getCurrentBalance());
            creditMax = String.valueOf(curr.getCreditAccounts().get(0).getCreditMax());


            for (int i = 0; i < identifiers.length; i++) {
                if (identifiers[i].equalsIgnoreCase("First Name")) {
                    writeInfo[i] = firstName;
                }
                if (identifiers[i].equalsIgnoreCase("Last Name")) {
                    writeInfo[i] = lastName;
                }
                if (identifiers[i].equalsIgnoreCase("Date of Birth")) {
                    writeInfo[i] = DOB;
                }
                if (identifiers[i].equalsIgnoreCase("Identification Number")) {
                    writeInfo[i] = id;
                }
                if (identifiers[i].equalsIgnoreCase("Address")) {
                    writeInfo[i] = address;
                }
                if (identifiers[i].equalsIgnoreCase("Phone Number")) {
                    writeInfo[i] = phoneNum;
                }
                if (identifiers[i].equalsIgnoreCase("Checking Account Number")) {
                    writeInfo[i] = checkingAccNum;
                }
                if (identifiers[i].equalsIgnoreCase("Savings Account Number")) {
                    writeInfo[i] = savingAccNum;
                }
                if (identifiers[i].equalsIgnoreCase("Credit Account Number")) {
                    writeInfo[i] = creditAccNum;
                }
                if (identifiers[i].equalsIgnoreCase("Checking Starting Balance")) {
                    writeInfo[i] = checkingBal;
                }
                if (identifiers[i].equalsIgnoreCase("Savings Starting Balance")) {
                    writeInfo[i] = savingBal;
                }
                if (identifiers[i].equalsIgnoreCase("Credit Starting Balance")) {
                    writeInfo[i] = creditBal;
                }
                if (identifiers[i].equalsIgnoreCase("Credit Max")) {
                    writeInfo[i] = creditMax;
                }
            }
            for(String val: writeInfo){
                fw.append(val + ", ");
            }
            fw.append("\n");

        }
        fw.close();

    }

    /**
     * Generates a message for the given choice,based off of which action the user wants to take, that is then written into the generated txt file
     *
     * @param currUser takes in the Customer object of the currentUser
     * @param recipient takes in the Customer object of the recipient
     * @param accountIn takes in the specific Account object that is being dealth with (Credit, Savings, Checkings)
     * @param accountOut takes in the specific Account object that is being dealt with (Credit, Savings, Checkings)
     * @param fileIn takes in the file to be written to
     * @param amount the amount the user is working with for choices (2 - 5)
     * @param choice determines which message will be generated based off of what action user takes.
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    private static void messagesToWriteToCSV(Customer currUser, Customer recipient, Account accountIn, Account accountOut, File fileIn, double amount, int choice) throws IOException{
        if(choice == 1){
            String message = currUser.getFullName() + " made a balance inquiry on " + accountIn.getAccountNumber() + ". " + currUser.getFullName() + " balance is $" + accountIn.getCurrentBalance();
            writeToFile(message, fileIn);
        }
        if(choice == 2) {
            String message = currUser.getFullName() + " deposited $" + amount + " in cash from " + accountIn.getAccountNumber() + ". " + currUser.getFullName() + " Balance for " + accountIn.getAccountNumber() +  " is : " + accountIn.getCurrentBalance();
            writeToFile(message, fileIn);
        }

        if(choice == 3){
            String message = currUser.getFullName() + " withdrew $" + amount + " in cash from " + accountIn.getAccountNumber() + ". " + currUser.getFullName() + " Balance for " + accountIn.getAccountNumber() +  " is : " + accountIn.getCurrentBalance();
            writeToFile(message, fileIn);
        }

        if(choice == 4){
            String message = currUser.getFullName() + " transferred $" + amount + " in cash from " + accountOut.getAccountNumber() + " to " + accountIn.getAccountNumber() + ". " + currUser.getFullName() + " Balance for " + accountOut.getAccountNumber() +  " is $" + accountOut.getCurrentBalance() + ". Balance for " + accountIn.getAccountNumber() + " is $ " + accountIn.getCurrentBalance();
            writeToFile(message, fileIn);
        }
        if(choice == 5){
            String message = currUser.getFullName() + " made a payment in the amount of $" + amount + " from account " + accountOut.getAccountNumber() + ", to " + recipient.getFullName() + ". " + currUser.getFullName() + "'s new balance for account " + accountOut.getAccountNumber() + " is $" + accountOut.getCurrentBalance();
            writeToFile(message, fileIn);
        }
        if(choice == 6){
            String message = "Error: Could not perform transaction";
            writeToFile(message, fileIn);
        }

    }

    /**
     * Helper method allows user to choose which account or accounts they want to use.
     *
     * @param chosenAction indicating choice based off of which action user wants to take
     * @return userAccountType: user choice of account
     */
    private static int chooseAccount(int chosenAction){
        Scanner intScanner = new Scanner(System.in);
        if(chosenAction == 1 || chosenAction == 2 || chosenAction == 3 || chosenAction == 5){
            System.out.println("What account did you want to access?");
            System.out.println("1. Checking");
            System.out.println("2. Savings");
            System.out.println("3. Credit");
            System.out.println("4. Return to Main Menu");
            System.out.println("5. Exit");
            System.out.print("Account: ");
            int userAccountType = intScanner.nextInt();
            return userAccountType;
        }

        System.out.println("What accounts did you want to transfer to");
        System.out.println("1. Checking");
        System.out.println("2. Savings");
        System.out.println("3. Credit");
        System.out.println("4. Return to Main Menu");
        System.out.println("5. Exit");
        System.out.println("If answer is 4 or 5 enter for both accounts");
        System.out.print("Account 1: ");
        int userAccountType1 = intScanner.nextInt();
        return userAccountType1;

    }

    /**
     * Helper method that gets the int associated with the account from a string
     * @param account: String representation of an account
     * @return int representation of an account
     */
    private static int getAccountFromString(String account){
        if(account.equals("Checking")){
           return 1;
        }
        if(account.equals("Savings")){
            return 2;
        }
        if(account.equals("Credit")){
            return 3;
        }
        return 0;
    }

    /**
     * Helper method prompts user for pin number and returns answer back to them
     *
     * @return pin: pin number entered by user
     */
    private static int inputPin(){
        Scanner intScanner = new Scanner(System.in);
        System.out.print("Please enter your identification number: ");
        int pin = intScanner.nextInt();
        return pin;
    }

    /**
     * Helper method that reruns certain code, and returns user to their desired menu
     *
     * @param currUser takes in the Customer object of the currentUser
     * @param chosenAction takes in the action associated to the menu the user is currently in
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    private static void rerunMethodCode(Customer currUser, int chosenAction) throws IOException{
        Scanner stringScanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Check another account? (enter yes or no)");
        String answer = stringScanner.nextLine();
        if(answer.equalsIgnoreCase("yes")){
            if(chosenAction == 1){
                inquiryMenu(currUser);
            }
            if(chosenAction == 2){
                depositMenu(currUser);
            }

            if(chosenAction == 3){
                withdrawMenu(currUser);
            }
            if(chosenAction == 4){
                transferMenu(currUser);
            }
        }
        if(answer.equalsIgnoreCase("no")){
            System.out.println("Returning to main menu.");
            onePersonMenu(currUser);
        }
    }

    /**
     * Helper method that reruns code to loop through menu several times, and returns user to their desired menu
     *
     * @param currUser takes in the Customer object of the currentUser
     * @param recipient takes in the Customer object of the recipient
     * @param chosenAction takes in the action associated to the menu the user is currently in
     * @throws IOException finds if the input file stream, that is being written to is not corrupt
     */
    private static void rerunMethodCodeTwoPersonMenu(Customer currUser, Customer recipient, int chosenAction) throws IOException{
        Scanner stringScanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Check another account?");
        String answer = stringScanner.nextLine();
        if(answer.equalsIgnoreCase("yes")){
            if(chosenAction == 5){
                paymentMenu(currUser, recipient);
            }
        }
        if(answer.equalsIgnoreCase("no")){
            System.out.println("Returning to main menu.");
            twoPersonMenu(currUser, recipient);
        }
    }
}



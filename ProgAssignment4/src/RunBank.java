/**
 * @author diana licon
 * Instructor: Daniel Mejia
 * @version 1.0
 * @since 2/22
 * description
 */

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.DoubleToIntFunction;

public class RunBank {

    public static void main(String args[]){
        //read file, create Checking objects and put those object into HashThable ht
        try {
            File bankUsers = new File("BankUsers.csv");
            Scanner input = new Scanner(System.in);
            //declare filewriter for logging Checking actions
            File log = new File("log.txt");
            FileWriter fileWriter = new FileWriter(log);
            Hashtable<String, Customer> ht;
            ht = createHashTable(bankUsers);
            System.out.println(ht);
            char useAgain='y';
            while(useAgain=='y') {
                System.out.println("Hello! Thank you for choosing Disney Bank!");
                //information to verify is user is in the system(ht)
                System.out.println("Are you a bank manager y/n");
                char isManager = input.next().charAt(0);
                int managerAction;
                if (isManager == 'y') {
                    System.out.println("would you like to...\n1)Inquire account by name\n2)Inquire account by type/number\n3)create a new bank user\n4)run transaction actions\n5)Generate Bank Statement");
                    managerAction = input.nextInt();
                    if (managerAction == 1) {
                        System.out.println("Whose account would you like to inquire");
                        System.out.print("First name:");
                        String customerFirstName = input.next();
                        System.out.print("Last name:");
                        String customerLastName = input.next();
                        if (ht.containsKey(customerFirstName + " " + customerLastName)) {
                            System.out.println("hii");
                            inquireByName(ht.get(customerFirstName + " " + customerLastName));
                        }
                    } else if (managerAction == 2) {
                        System.out.println("What type of account? \n1)Checking\n2)Savings\n3)Credit");
                        int accountType = input.nextInt() - 1;
                        System.out.println("What is the account number:");
                        int accountNumber = input.nextInt();
                        inquireByType(accountType, accountNumber, bankUsers, ht);
                    } else if (managerAction == 3) {
                        System.out.println("Please input the following information about the new bank user");
                        System.out.print("First name: ");
                        String firstName = input.next();
                        System.out.print("Last name: ");
                        String lastName = input.next();
                        System.out.print("Date of birth(mm/dd/yyyy): ");
                        String DOB = input.next();
                        System.out.print("Address: ");
                        String address = input.next();
                        System.out.print("Phone number: ");
                        String phoneNum = input.next();
                        Customer newCustomer = new Customer(firstName, lastName, DOB, address, phoneNum);
                        ht.put(firstName + " " + lastName, newCustomer);
                        Savings savings = new Savings(0);
                        newCustomer.setSavings(savings);
                    } else if (managerAction == 4) {
                        File transactionActions = new File("TransactionActions.csv");
                        doTransactionActions(fileWriter, transactionActions, ht);
                        System.out.println("done");
                    } else if (managerAction == 5) {
                        System.out.println("who would you like to generate a bank statement for");
                        System.out.println("First Name:");
                        String firstName = input.next();
                        System.out.println("Last Name:");
                        String lastName = input.next();
                        if (ht.containsKey(firstName + " " + lastName)) {
                            LocalDate date = LocalDate.now(); // Gets the current date
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            Customer customer = ht.get(firstName + " " + lastName);
                            BankStatement statement=new BankStatement(customer,date.format(formatter));
                            statement.addTransactions();
                        }

                    } else {
                        System.out.println("You did not enter a valid input");
                    }

                } else {
                    System.out.print("First name: ");
                    String userFirstName = input.next();
                    System.out.print("last Name: ");
                    String userLastName = input.next();
                    String userName = userFirstName + " " + userLastName;
                    if (ht.containsKey(userName)) {
                        try {

                            Customer user = ht.get(userName);

                            int action;
                            //while allows user to make more than one action during their session
                            System.out.print("Would you like to \n 1)View your balance \n 2)Transfer to someone \n 3)Deposit \n 4)Withdraw \n 5)Transfer \n");
                            Account[] accounts = user.getAccounts();
                            if (accounts[0] == null) {
                                System.out.println(" 6)create a checking Account");
                            }
                            if (accounts[2] == null) {
                                System.out.println(" 7)create a credit Account");
                            }
                            action = input.nextInt();
                            if (action == 1) {
                                char tryAgain = 'y';
                                while (tryAgain == 'y') {
                                    System.out.println("Which account would you like to view?");
                                    System.out.println("1)Checking\n2)Savings\n3)Credit");
                                    int accountIndex = input.nextInt() - 1;
                                    tryToInquire(fileWriter, user, accountIndex);
                                    System.out.println("would you like to inquire another account y/n?");
                                    tryAgain = input.next().charAt(0);
                                }
                            } else if (action == 2) {
                                //information to check if recipient is in the system(ht
                                System.out.println("Who would you like to send money to?");
                                System.out.print("First Name: ");
                                String recipientFirstName = input.next();
                                System.out.print("Last Name: ");
                                String recipientLastName = input.next();
                                String recipientName = recipientFirstName + " " + recipientLastName;
                                if (ht.containsKey(recipientName)) {
                                    Customer recipient = ht.get(recipientName);
                                    char tryAgain = 'y';
                                    while (tryAgain == 'y') {
                                        System.out.println("what account would you like to use\n1)Checking\n2)Savings\n3)Credit");
                                        int accountIndex = input.nextInt() - 1;
                                        System.out.println("How much money would you like to send to " + recipient.getName() + "?");
                                        double amount = input.nextDouble();
                                        tryToPaySomeone(user, accountIndex, recipient, amount, fileWriter);
                                        System.out.println("would you like to transfer again  y/n?");
                                        tryAgain = input.next().charAt(0);
                                    }

                                } else
                                    System.out.println("hmm... this person is not in our system.");
                            } else if (action == 3) {
                                char tryAgain = 'y';
                                while (tryAgain == 'y') {
                                    System.out.println("To what account would you like to deposit?\n1)Checking\n2)Savings\n3)Credit");
                                    int accountIndex = input.nextInt() - 1;
                                    System.out.println("How much money would you like to deposit?");
                                    double amount = input.nextDouble();
                                    tryToDeposit(user, accountIndex, amount, fileWriter);
                                    System.out.println("would you like to deposit again y/n?");
                                    tryAgain = input.next().charAt(0);
                                }


                            } else if (action == 4) {

                                char tryAgain = 'y';
                                while (tryAgain == 'y') {
                                    System.out.println("What account would you like to withdraw from?\n1)Checking\n2)Savings\n3)Credit");
                                    int accountIndex = input.nextInt() - 1;
                                    System.out.println("How much money would you like to withdraw?");
                                    double amount = input.nextDouble();
                                    tryToWithdraw(user, accountIndex, amount, fileWriter);
                                    System.out.println("would you like to withdraw again y/n?");
                                    tryAgain = input.next().charAt(0);
                                }
                            } else if (action == 5) {
                                char tryAgain = 'y';
                                while (tryAgain == 'y') {
                                    System.out.println("What account would you like to transfer from?\n1)Checking\n2)Savings\n3)Credit");
                                    int accountIndexFrom = input.nextInt() - 1;
                                    System.out.println("What account would you like to transfer to?\n1)Checking\n2)Savings\n3)Credit");
                                    int accountIndexTo = input.nextInt() - 1;
                                    System.out.println("How much money would you like to transfer?");
                                    double amount = input.nextDouble();
                                    tryToTransfer(user, accountIndexFrom, accountIndexTo, amount, fileWriter);
                                    tryAgain = input.next().charAt(0);
                                }

                            } else if (action == 6 && accounts[0] == null) {
                                Checking checking = new Checking(0);
                                user.setChecking(checking);
                                System.out.println("new checking account created successfully");
                            } else if (action == 7 && accounts[2] == null) {
                                Credit credit = new Credit(0);
                                user.setCredit(credit);
                                System.out.println(user.getCredit().getAccountNumber());
                                System.out.println("new checking account created successfully");
                            } else {
                                System.out.println("Please enter a valid character");
                            }


                        } catch (IOException e) {
                            System.out.println("File not found");
                        }
                    } else {
                        System.out.println("Sorry, you are not in our system");
                    }

                }
                System.out.println("would you like to use again?");
                useAgain=input.next().charAt(0);
            }
            try {
                writeCSV(ht, bankUsers);
            }catch (Exception e){
                System.out.println("file not found");
            }

            input.close();
            fileWriter.close();

        }catch(FileNotFoundException e){
            System.out.println("Bank users file not found");
        }catch(IOException e){
            System.out.println("log.txt not found");
        }

    }

    /**
     * creates  hash table storing the information of all the bank users with name as Key
     *
     * @param  file of type File
     * @return Hashtable containing the information of all users
     * @throws FileNotFoundException
     */
    public static Hashtable<String, Customer> createHashTable(File file)throws FileNotFoundException{

        Scanner scanner = new Scanner(file);
        scanner.nextLine();
        //count lines in file to know how big ht should be
        int users = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            users += 1;
        }
        Scanner newScanner = new Scanner(file);
        String[] header= newScanner.nextLine().split(",");
        double checkingBalance=0;
        double savingsBalance=0;
        double creditBalance=0;
        double creditMax=0;
        String firstName="";
        String lastName="";

        Hashtable<String, Customer> ht = new Hashtable<>(users);
        while (newScanner.hasNextLine()) {
            String[] fields = newScanner.nextLine().split(",");
            Customer customer=new Customer();
            int fieldsIndex=0;
            int headerIndex=0;
            while(fieldsIndex<fields.length){
                String str = header[headerIndex];
                switch(str) {
                    case "Checking Starting Balance":
                        checkingBalance=Double.parseDouble(fields[fieldsIndex]);
                        break;
                    case "Savings Starting Balance":
                        savingsBalance=Double.parseDouble(fields[fieldsIndex]);
                        break;
                    case "Credit Starting Balance":
                        creditBalance=Double.parseDouble(fields[fieldsIndex]);
                        break;
                    case "Credit Max":
                        creditMax=Double.parseDouble(fields[fieldsIndex]);
                        break;
                    case "Address":
                        fieldsIndex=fieldsIndex+2;
                        break;
                    default:
                        break;
                }
                fieldsIndex=fieldsIndex+1;
                headerIndex=headerIndex+1;
            }
            fieldsIndex=0;
            headerIndex=0;
            while(fieldsIndex<fields.length){
                String str = header[headerIndex];
                switch(str)
                {
                    case "First Name":
                        firstName=fields[fieldsIndex];
                        customer.setFirstName(fields[fieldsIndex]);
                        break;
                    case "Last Name":
                        lastName=fields[fieldsIndex];
                        customer.setLastName(fields[fieldsIndex]);
                        break;
                    case "Identification Number":
                        customer.setIdentificationNum(Integer.parseInt(fields[fieldsIndex]));
                        break;
                    case "Phone Number":
                        customer.setPhoneNumber(fields[fieldsIndex]);
                        break;
                    case "Date of Birth":
                        customer.setDateOfBirth(fields[fieldsIndex]);
                        break;
                    case "Address":
                        customer.setAddress(fields[fieldsIndex]+","+fields[fieldsIndex+1]+","+fields[fieldsIndex+2]);
                        fieldsIndex=fieldsIndex+2;
                        break;
                    case "Checking Account Number":
                        if(!fields[fieldsIndex].equals("")){
                            Checking checking=new Checking(Integer.parseInt(fields[fieldsIndex]),checkingBalance);
                            customer.setChecking(checking);

                        }
                        break;
                    case "Savings Account Number":
                        if(!fields[fieldsIndex].equals("")){
                            Savings savings=new Savings(Integer.parseInt(fields[fieldsIndex]),checkingBalance);
                            customer.setSavings(savings);
                        }
                        break;
                    case "Credit Account Number":
                        if(!fields[fieldsIndex].equals("")){
                            Credit credit=new Credit(Integer.parseInt(fields[fieldsIndex]),checkingBalance,creditMax);
                            customer.setCredit(credit);
                        }
                        break;
                    default:
                        break;
                }
                fieldsIndex=fieldsIndex+1;
                headerIndex=headerIndex+1;

            }

            //firstName lastName is the key to the HashTable, object of type Checking, user, is the value
            ht.put(firstName + " " + lastName, customer);
        }
        scanner.close();
        return ht;


    }

    /**
     * Collects information needed to Pay someone, logs to log.txt
     * @param user of type Customer
     * @param recipient of type Customer
     * @param fileWriter of type FileWriter
     * @throws IOException
     */
    public static void tryToPaySomeone(Customer user, int accountIndex, Customer recipient, double amount,FileWriter fileWriter)throws IOException{
        Scanner input=new Scanner(System.in);
        char tryAgain='y';
        Account userAccount;
        String accountType;
        //while loop allows user to attempt to pay someone again is they made  mistake

        accountType=getAccountType(accountIndex);
        if(accountIndex<3 && accountIndex>-1) {
            if(user.getAccounts()[accountIndex]!=null)
                userAccount = user.getAccounts()[accountIndex];
            else {
                System.out.println("account does not exist");
                return;
            }

        }
        else {
            System.out.println("please enter a valid input");
            return;
        }


        if (!userAccount.paySomeone(recipient, amount)) {
            fileWriter.write(user.getName() + " failed to pay $" + amount + " from "+accountType+"-"+userAccount.getAccountNumber()+" to "+recipient.getName()+" \n");
        } else {
            fileWriter.write(user.getName() + " paid $" + amount + " from "+accountType+"-"+userAccount.getAccountNumber()+" to "+recipient.getName()+". "+user.getName()+"'s new balance for "+accountType+"-"+userAccount.getAccountNumber()+": $"+userAccount.getBalance()+"\n");
            fileWriter.write(recipient.getName()+" received $"+amount+" from "+user.getName()+". "+recipient.getName()+"’s New Balance for Checking-"+recipient.getChecking().getAccountNumber()+": $"+recipient.getChecking().getBalance()+"\n");

        }


    }

    /**
     * collects information needed to deposit. Logs to log.txt
     * @param user of type Customer
     * @param fileWriter of type FileWriter
     * @throws IOException
     */
    public static void tryToDeposit(Customer user, int accountIndex, double amount, FileWriter fileWriter) throws IOException {

        Scanner input=new Scanner(System.in);
        Account userAccount;
        String accountType;
        char tryAgain='y';
        //while loop allows user to attempt to deposit again is they made  mistake

        if(accountIndex<3 && accountIndex>-1) {
            if(user.getAccounts()[accountIndex]!=null) {
                userAccount = user.getAccounts()[accountIndex];
                accountType = getAccountType(accountIndex);
            }else{
                System.out.println("account does not exist");
                return;
            }
        }
        else {
            System.out.println("please enter a valid input");
            return;
        }

        if (!userAccount.deposit(amount)) {
            fileWriter.write(user.getName()+" failed to Deposit $"+amount+" into "+accountType+"-"+userAccount.getAccountNumber()+"\n");
            System.out.println("Would you like to try again y/n");
            tryAgain = input.next().charAt(0);
        }else {
            fileWriter.write(user.getName()+" Deposited $"+amount+" into  "+accountType+"-"+userAccount.getAccountNumber()+". "+user.getName()+"'s new balance for "+accountType+"-"+userAccount.getAccountNumber()+": "+userAccount.getBalance()+"\n");
            tryAgain = 'n';
        }
    }


    /**
     * collects information to withdraw, logs to log.txt
     * @param user of type Customer
     * @param fileWriter of type FileWriter
     * @throws IOException
     */
    public static void tryToWithdraw(Customer user, int accountIndex, double amount,FileWriter fileWriter) throws IOException {
        Scanner input=new Scanner(System.in);
        Account userAccount;
        String accountType;

        if(accountIndex<3 && accountIndex>-1) {
            if (user.getAccounts()[accountIndex] != null) {
                userAccount = user.getAccounts()[accountIndex];
                accountType = getAccountType(accountIndex);
            }else{
                System.out.println("account does not exist");
                return;
            }
        }else {
            System.out.println("please enter a valid input");
            return;
        }


        if (!userAccount.withdraw(amount)) {
            fileWriter.write(user.getName()+" failed to withdraw $"+amount+" from checking-"+userAccount.getAccountNumber()+"\n");
            System.out.println("Would you like to try again y/n");
        }else {
            fileWriter.write(user.getName()+" Withdrew $"+amount+" from "+accountType+"-"+userAccount.getAccountNumber()+". "+user.getName()+"'s new balance for "+accountType+"-"+userAccount.getAccountNumber()+": "+userAccount.getBalance()+"\n");
        }

    }

    /**
     * collects information to inquire a balance from any account, logs to log.txt
     * @param fileWriter of type FileWriter
     * @param user of type Customer
     * @throws IOException
     */
    public static void tryToInquire(FileWriter fileWriter, Customer user, int accountIndex)throws IOException{
        String accountType=getAccountType(accountIndex);
        if (accountIndex >=0 && accountIndex<3) {
            if(user.getAccounts()[accountIndex]!=null) {
                Account account = user.getAccounts()[accountIndex];
                account.inquire(accountType);
                fileWriter.write(user.getName() + " made a balance inquiry on " + accountType + "-" + account.getAccountNumber() + ". " + user.getName() + "’s Balance for Checking-" + account.getAccountNumber() + ": $" + account.getBalance() + ".\n");
            }
            else
                fileWriter.write(user.getName() + " attempted a balance inquiry on " + accountType + "which does not exist.\n");

        }
        else{
            System.out.println("please enter a valid input");
        }



    }

    /**
     * collects information to transfer, logs to log.txt
     * @param user of type Customer
     * @param fileWriter of type FileWriter
     * @throws IOException
     */
    public static void tryToTransfer(Customer user, int accountIndexFrom, int accountIndexTo, double amount, FileWriter fileWriter)throws IOException{
        Scanner input=new Scanner(System.in);
        Account userAccount;
        Account userAccountTo;
        String accountType;
        String accountTypeTo;
        char tryAgain='y';
        //while loop allows user to attempt to withdraw again is they made  mistake

        if (accountIndexFrom >=0 && accountIndexFrom<3) {
            if(user.getAccounts()[accountIndexFrom]!=null) {
                userAccount = user.getAccounts()[accountIndexFrom];
                accountType = getAccountType(accountIndexFrom);
            }
            else {
                System.out.println("account does not exist");
                return;
            }
        }
        else {
            System.out.println("please enter a valid input");
            return;
        }

        if (accountIndexTo >=0 && accountIndexTo<3) {
            if(user.getAccounts()[accountIndexTo]!=null) {
                userAccountTo = user.getAccounts()[accountIndexTo];
                accountTypeTo = getAccountType(accountIndexTo);
            }else{
                System.out.println("account does not exist");
                return;
            }
        }
        else {
            System.out.println("please enter a valid input");
            return;
        }


        if (!userAccount.transfer(userAccountTo, amount)) {
            fileWriter.write(user.getName()+" failed to withdraw $"+amount+" from checking-"+userAccount.getAccountNumber()+"\n");
            System.out.println("Would you like to try again y/n");
        }else {
            fileWriter.write(user.getName()+" transfered $"+amount+" from "+accountType+"-"+userAccount.getAccountNumber()+" to "+accountTypeTo+"-"+userAccountTo.getAccountNumber()+". "+user.getName()+"'s new balance for "+accountType+"-"+userAccount.getAccountNumber()+": $"+userAccount.getBalance()+", "+user.getName()+"'s new balance for "+accountTypeTo+"-"+userAccountTo.getAccountNumber()+": $"+userAccountTo.getBalance()+"\n");
        }


    }

    /**
     * creates a csv file with the same structure as BankUsers.csc, but with updated information after banking session
     * @param ht of type Hashtable
     * @param bankUsers of type File
     * @throws IOException
     */
    public static void writeCSV(Hashtable<String,Customer> ht, File bankUsers)throws IOException {
        File  csv=new File("BankUsersUpdated.csv");
        FileWriter fileWriter = new FileWriter(csv);
        Scanner read=new Scanner(bankUsers);
        fileWriter.append("First Name,Last Name,Identification Number,Date Of Birth,Address,Phone Number,Saving Account Number,Savings Starting Balance,Checking Account NUmber,Checking Starting Balance,Credit Account Number,Credit Starting Balance,Credit Max");
        //looking first for where first and last name are placed in the original csv to preserve the order of customers.
        String[] oldFields=read.nextLine().split(",");
        int firstNameIndex=-1;
        int lastNameIndex=-1;
        int oldFieldsIndex=0;
        int addressIndex=-1;
        while ((firstNameIndex<0 || lastNameIndex<0 || addressIndex<0) && oldFieldsIndex<oldFields.length){
            if(oldFields[oldFieldsIndex].equals("First Name"))
                firstNameIndex=oldFieldsIndex;
            if(oldFields[oldFieldsIndex].equals("Last Name"))
                lastNameIndex=oldFieldsIndex;
            if(oldFields[oldFieldsIndex].equals("Address"))
                addressIndex=oldFieldsIndex;
            oldFieldsIndex+=1;
        }
        if(addressIndex<firstNameIndex)
            firstNameIndex+=2;
        if(addressIndex<lastNameIndex)
            lastNameIndex+=2;
        while(read.hasNextLine()){
            String[] currFields=read.nextLine().split(",");
            String currKey=currFields[firstNameIndex]+" "+currFields[lastNameIndex];
            Customer currCustomer=ht.get(currKey);
            fileWriter.append('\n');
            fileWriter.append(currFields[firstNameIndex]+","+currFields[lastNameIndex]+","+currCustomer.getIdentificationNum()+","+currCustomer.getDateOfBirth()+","+currCustomer.getAddress()+","+currCustomer.getPhoneNumber()+","+currCustomer.getSavings().getAccountNumber()+","+currCustomer.getSavings().getBalance());
            if(currCustomer.getAccounts()[0]!=null)
                fileWriter.append(","+currCustomer.getChecking().getAccountNumber()+","+currCustomer.getChecking().getBalance());
            if(currCustomer.getAccounts()[2]!=null)
                fileWriter.append(","+currCustomer.getCredit().getAccountNumber()+","+currCustomer.getCredit().getBalance()+","+currCustomer.getCredit().getCreditMax());
            ht.remove(currKey);
        }
        Set<String> setOfCountries = ht.keySet();
        for(String key : setOfCountries) {
            Customer currCustomer=ht.get(key);
            fileWriter.append('\n');
            fileWriter.append(currCustomer.getFirstName()+","+currCustomer.getLastName()+","+currCustomer.getIdentificationNum()+","+currCustomer.getDateOfBirth()+","+currCustomer.getAddress()+","+currCustomer.getPhoneNumber()+","+currCustomer.getSavings().getAccountNumber()+","+currCustomer.getSavings().getBalance());
            if(currCustomer.getAccounts()[0]!=null)
                fileWriter.append(","+currCustomer.getChecking().getAccountNumber()+","+currCustomer.getChecking().getBalance());
            if(currCustomer.getAccounts()[2]!=null)
                fileWriter.append(","+currCustomer.getCredit().getAccountNumber()+","+currCustomer.getCredit().getBalance()+","+currCustomer.getCredit().getCreditMax());

        }

        fileWriter.close();
    }

    /**
     * method for Bank managers that allows them to view all bank information for a customer
     * @param customer of type Customer
     */
    public static void inquireByName(Customer customer){
        System.out.println("\t"+customer.getName()+"'s account information");
        System.out.println("Checking-"+customer.getChecking().getAccountNumber()+": $"+customer.getChecking().getBalance());
        System.out.println("Savings-"+customer.getSavings().getAccountNumber()+": $"+customer.getSavings().getBalance());
        System.out.println("Credit-"+customer.getCredit().getAccountNumber()+": $"+customer.getCredit().getBalance());
    }
    public static void inquireByType(int accountType, int accountNumber, File bankUsers,Hashtable<String,Customer> ht)throws FileNotFoundException{
        Set<String> setOfCountries = ht.keySet();
        for(String key : setOfCountries) {
            Customer curCustomer=ht.get(key);
            if(accountType<3 && accountType>-1){
                if(curCustomer.getAccounts()[accountType]!=null){
                    Account account=curCustomer.getAccounts()[accountType];
                    if(account.getAccountNumber()==accountNumber)
                        System.out.println("Checking-"+account.getAccountNumber()+": $"+account.getBalance());
                }
            }
        }


    }

    public static void doTransactionActions(FileWriter filewriter,File transactionActions,Hashtable<String,Customer> ht)throws FileNotFoundException, IOException{
        Scanner scanner=new Scanner(transactionActions);
        scanner.nextLine();
        while(scanner.hasNextLine()) {
            String fields[] = scanner.nextLine().split(",");
            String action = fields[3];
            switch(action){
                case "pays":
                    if (ht.containsKey(fields[0]+" "+fields[1])){
                        if(ht.containsKey(fields[4]+" "+fields[5])){
                            Customer from=ht.get(fields[0]+" "+fields[1]);
                            Customer to= ht.get(fields[4]+" "+fields[5]);
                            int fromAccountIndex=getAccountIndex(fields[2]);
                            double amount=Double.parseDouble(fields[7]);
                            tryToPaySomeone(from,fromAccountIndex,to,amount,filewriter);
                            break;
                        }
                        else{
                            System.out.println(fields[4]+" "+fields[5]+" is not in the system");
                            break;
                        }
                    }
                    else{
                        System.out.println(fields[0]+" "+fields[1]+" is not in the system");
                        break;
                    }
                case "transfers":
                    if (ht.containsKey(fields[0]+" "+fields[1])){
                        Customer user=ht.get(fields[0]+" "+fields[1]);
                        int fromAccountIndex=getAccountIndex(fields[2]);
                        int toAccountIndex=getAccountIndex(fields[6]);
                        double amount=Double.parseDouble(fields[7]);
                        tryToTransfer(user,toAccountIndex,fromAccountIndex,amount,filewriter);
                        break;
                    }else{
                        System.out.println(fields[0]+" "+fields[1]+" is not in the system");
                        break;
                    }
                case "inquires":
                    if (ht.containsKey(fields[0]+" "+fields[1])){
                        Customer user=ht.get(fields[0]+" "+fields[1]);
                        int accountIndex=getAccountIndex(fields[2]);
                        tryToInquire(filewriter, user,accountIndex);
                    }else{
                        System.out.println(fields[0]+" "+fields[1]+" is not in the system");
                    }
                    break;
                case "deposits":
                    if (ht.containsKey(fields[4]+" "+fields[5])){
                        Customer user=ht.get(fields[4]+" "+fields[5]);
                        int AccountIndex=getAccountIndex(fields[6]);
                        double amount=Double.parseDouble(fields[7]);
                        tryToDeposit(user,AccountIndex,amount,filewriter);
                        break;
                    }else{
                        System.out.println(fields[0]+" "+fields[1]+" is not in the system");
                        break;
                    }
                case "withdraws":
                    if (ht.containsKey(fields[0]+" "+fields[1])){
                        Customer user=ht.get(fields[0]+" "+fields[1]);
                        int AccountIndex=getAccountIndex(fields[2]);
                        double amount=Double.parseDouble(fields[7]);
                        tryToWithdraw(user,AccountIndex,amount,filewriter);
                    }else{
                        System.out.println(fields[0]+" "+fields[1]+" is not in the system");
                    }
                    break;
                default:
                    System.out.println("defult of transaction methods");
            }

        }
    }
    public static int getAccountIndex(String account){
        switch (account){
            case "Checking":
                return 0;
            case "Savings":
                return 1;
            case "Credit":
                return 2;
            default:
                return -1;
        }
    }
    public static String getAccountType(int accountIndex){
        switch (accountIndex){
            case 0:
                return "Checkings";
            case 1:
                return "Savings";
            case 2:
                return "Credit";
            default:
                return "";
        }
    }

}

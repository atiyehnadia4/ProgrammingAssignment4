import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Bank Statement class is a class that creates and writes to file that will store a specified customers bank statment for all their respective account
 *
 *
 * @author Nadia Atiyeh
 * @version 3.0
 * @since March 15,2021
 *
 */
public class BankStatement {
    /**
     * The specified customer that will generate a bank statment for
     */
    private Customer custInfo;
    /**
     * The stored account for the specified customer
     */
    private Account[] acctInfo = new Account[3];
    /**
     * The stored starting balances for each stored account for a specified customer
     */
    private double[] startBal = new double[3];
    /**
     * The stored ending balances for each stored account for a specified customer
     */
    private double[] endBal = new double[3];
    /**
     * The stored transaction arraylists for a specified customer
     */
    private ArrayList<String>[] transactions = new ArrayList[3];
    /**
     * The file for the users bank statement
     */
    private File bankStatement;
    /**
     * The generated date for bank statment
     */
    private String date;

    /**
     * Deafult Constructor
     */
    public BankStatement(){

    }

    /**
     * Populates all bank statement attributes with the given customer
     * @param custInfo specified customer
     * @param userBankStatement file for specified customer
     */
    public BankStatement(Customer custInfo){
        this.custInfo = custInfo;
        this.acctInfo[0] = custInfo.getCheckingAccounts().get(0);
        this.acctInfo[1] = custInfo.getSavingsAccounts().get(0);
        this.acctInfo[2] = custInfo.getCreditAccounts().get(0);
        this.startBal[0] = acctInfo[0].getStartingBalance();
        this.startBal[1] = acctInfo[1].getStartingBalance();
        this.startBal[2] = acctInfo[2].getStartingBalance();
        this.endBal[0] = acctInfo[0].getCurrentBalance();
        this.endBal[1] = acctInfo[1].getCurrentBalance();
        this.endBal[2] = acctInfo[2].getCurrentBalance();
        this.transactions[0] = acctInfo[0].getTransactions();
        this.transactions[1] = acctInfo[1].getTransactions();
        this.transactions[2] = acctInfo[2].getTransactions();
        SimpleDateFormat ft = new SimpleDateFormat("E MM-dd-yyyy");
        Date currDate = new Date();
        this.date = ft.format(currDate);
        this.bankStatement =new File(custInfo.getName()+".txt");

    }

    /**
     * Method that takes attributes from the class to acquire all the data from a specified customer and create a formatted bank statement
     * according to what the bank manager specifies in the main
     * @throws IOException throws incase File is not read correctly
     */
    public void writeToBankStatement() throws IOException {
        if(this.bankStatement.length() != 0){
            this.bankStatement.delete();
        }

        FileWriter fw = new FileWriter(this.bankStatement, true);
        // uses printwriter to format text
        PrintWriter pw = new PrintWriter(fw);


        pw.write("BANK OF OOP");
        pw.println();
        pw.write("1801 Hawthore St.");
        pw.println();
        pw.write("El Paso, TX, 79902");
        pw.println();
        pw.write("=========================================================");
        pw.println();
        pw.write(this.custInfo.getFullName() + "                     " + this.date);
        pw.println();
        pw.write(this.custInfo.getAddress());
        pw.println();
        pw.write(this.custInfo.getPhoneNumber());
        pw.println();
        pw.write(this.custInfo.getDateOfBirth());
        pw.println();
        pw.write(this.custInfo.getAddress());
        pw.println();
        pw.write("Identification Number: " + String.valueOf(this.custInfo.getIdentificationNumber()));
        pw.println();

        if(this.transactions[0].size() > 0){
            pw.write("=========================================================");
            pw.println();
            pw.write("CHECKINGS                                  " + "Account #: " + this.acctInfo[0].getAccountNumber());
            pw.println();
            pw.write("Starting Balance: $"  + String.format("%.2f", this.acctInfo[0].getStartingBalance()));
            pw.println();
            for (String str: this.transactions[0]) {
                pw.write(str);
                pw.println();
            }
            pw.println();
            pw.write("Ending Balance: $"  + String.format("%.2f", this.acctInfo[0].getCurrentBalance()));
            pw.println();
        }
        if(this.transactions[1].size() > 0){
            pw.write("=========================================================");
            pw.println();
            pw.write("SAVINGS                                  " + "Account #: " + this.acctInfo[1].getAccountNumber());
            pw.println();
            pw.write("Starting Balance: $"  + String.format("%.2f", this.acctInfo[1].getStartingBalance()));
            pw.println();
            for (String str: this.transactions[1]) {
                pw.write(str);
                pw.println();
            }
            pw.println();
            pw.write("Ending Balance: $"  + String.format("%.2f", this.acctInfo[1].getCurrentBalance()));
            pw.println();

        }
        if(this.transactions[2].size() > 0){
            pw.write("=========================================================");
            pw.println();
            pw.write("CREDIT                                 " + "Account #: " + this.acctInfo[2].getAccountNumber());
            pw.println();
            pw.write("Starting Balance: $" + String.format("%.2f", this.acctInfo[2].getStartingBalance()));
            pw.println();
            for (String str: this.transactions[2]) {
                pw.write(str);
                pw.println();
            }
            pw.println();
            pw.write("Ending Balance: $" + String.format("%.2f",this.acctInfo[2].getCurrentBalance()));
            pw.println();
        }
        else{
            pw.println();
        }
        //closes the stream
        pw.close();
    }
}

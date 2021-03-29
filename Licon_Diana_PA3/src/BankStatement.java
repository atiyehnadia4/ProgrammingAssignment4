import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * class BankStatement creates a bank statement for a given Customer. It keeps the customer as n attribute to write info to BankStatement
 */

public class BankStatement {
    private File file;
    private FileWriter fileWriter;
    private String statement;
    private Customer customer;

    /**
     * populates Bank Statement with personal info as well as initial balances
     * @param customer
     * @param date
     * @throws IOException
     */
    public BankStatement(Customer customer, String date) throws IOException {
        this.customer=customer;
        String fileName=customer.getFirstName()+"_"+customer.getLastName()+"_"+"Statement.txt";
        this.file=new File(fileName);
        this.fileWriter=new FileWriter(file);
        fileWriter.write("Disney Bank");
        fileWriter.write("\n\n"+customer.getName());
        fileWriter.write("\nDOB: "+customer.getDateOfBirth());
        fileWriter.write("\nPhone Num: "+customer.getPhoneNumber());
        fileWriter.write("\nID: "+customer.getIdentificationNum());
        fileWriter.write("\n\nAccounts\n");
        Account accounts[]=customer.getAccounts();
        String accountType;
        for (int i =0;i<3;i++){
            accountType=getAccountType(i);
            if (accounts[i]!=null){
                fileWriter.write("\t"+accountType+"-"+accounts[i].getAccountNumber()+" starting balance: $"+accounts[i].getStartingBalance()+'\n');
            }
        }
        fileWriter.write("Statement Date: "+date+"\n\n");
    }

    /**
     * adds all trnsctions stored in all transactions arralist (one pertaining to each account) as well as adds the ending balances.
     * @throws IOException
     */
    public void addTransactions() throws IOException {
        fileWriter.write("\t\t\tTransactions\n");

        for(int i=0;i<3;i++){
            if(customer.getAccounts()[i]!=null) {
                ArrayList<String> list=customer.getAccounts()[i].getTransactions();
                for (int j = 0; j < list.size(); j++) {
                    fileWriter.write(list.get(j) + '\n');
                }
            }
        }
        String accountType;
        Account accounts[]=customer.getAccounts();
        fileWriter.write("\nending balances\n");
        for (int i =0;i<3;i++){
            accountType=getAccountType(i);
            if (accounts[i]!=null){
                fileWriter.write("\t"+accountType+"-"+accounts[i].getAccountNumber()+" ending balance: $"+accounts[i].getBalance()+'\n');
            }
        }

        fileWriter.close();
    }

    /**
     * only for internal use, given an index, return the corresponding account Type
     * @param i
     * @return
     */
    public String getAccountType(int i){
        String accountType;
        if(i==0)
            accountType="checking";
        else if(i==1)
            accountType="savings";
        else
            accountType="credit";
        return accountType;
    }

}

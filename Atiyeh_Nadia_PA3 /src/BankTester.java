/**
 * Tester Class for RunBank
 *
 * @author Nadia Atiyeh
 * @version 3.0
 * @since March 15, 2021
 *
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class BankTester {


    private static final double DELTA = 1e-15;

    public Checking userC = new Checking(1, 300.00, 10013);
    public Savings userS = new Savings(2, 900.00, 20013);
    public Credit userCR = new Credit(3, -700.00, 30013, 0);

    public Checking recipientC = new Checking(1, 500.00, 10014);
    public Savings recipientS = new Savings(2, 1000.00, 20014);
    public Credit recipientCR = new Credit(3, -1600.00, 30014, 0);



    //Test for Deposit Method
    @Test
    public void depositCheckingTest(){
        userC.deposit(200.00);
        assertEquals("Balance is now $500.00", userC.getCurrentBalance(), 500.00, DELTA);
    }

    @Test
    public void depositSavingsTest(){
        assertTrue("Deposit is a success", userS.deposit(200.00));
    }

    @Test
    public void depositCreditTest(){
        assertFalse("Deposit is a success", userCR.deposit(701.00));
    }

    @Test
    public void depositNegativeTest(){
        assertFalse("Deposit is not a success", userCR.deposit(-20.00));
    }

    //Test for Withdraw Method
    @Test
    public void withdrawCheckingTest(){
        userC.withdraw(200.00);
        assertEquals("Balance is now $100.00", userC.getCurrentBalance(), 100.00, DELTA);
    }

    @Test
    public void withdrawSavingsTest(){
        assertTrue("Withdrawal is a success", userS.withdraw(200.00));
    }

    @Test
    public void withdrawCreditTest(){
        assertTrue("Withdraw is a success", userCR.withdraw(100.00));
    }

    @Test
    public void withdrawNegativeTest(){
        assertFalse("Withdrawl is not a success", userC.withdraw(-20.00));
    }

    //Test for transfer method
    @Test
    public void transferCheckingtoSavingsTest(){
        assertTrue(userC.transfer(150.00, userS));
    }

    @Test
    public void transferSavingstoCreditTest(){
        assertFalse(userS.transfer(701.00, userCR));
    }

    @Test
    public void transferCheckingToChecking(){
        assertFalse(userC.transfer(300.00, userC));
    }

    @Test
    public void transferCheckingToSavingsNegativeTest(){
        assertFalse(userC.transfer(-300.00, userS));
    }

    //Test payment method
    @Test
    public void payCheckingToCredit(){
        assertTrue(userC.payment(150,recipientC));
    }

    @Test
    public void payCreditToChecking(){
        assertTrue(recipientCR.payment(1600, userC));
    }

    @Test
    public void paySavingsToCredit(){
        assertFalse(recipientS.payment(701.00, userCR));
    }

    @Test
    public void payCheckingsToSavingsNegativeTest(){
        assertFalse(recipientC.payment(-20.00, userS));
    }


}

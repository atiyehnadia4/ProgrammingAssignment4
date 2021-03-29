import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTester {
    public Checking checking=new Checking(123, 1000);
    Customer customer=new Customer("john", "Smith", "02/04/1998", 0, "1234 ave", "915-342-4543");
    public Savings savings=new Savings(231,9000);

    public Credit credit=new Credit(234,-8,0);

    //deposit
    @Test
    public void depositTest1(){
        checking.deposit(100);
        assertEquals(1100,checking.getBalance());
    }
    @Test
    public void depositTest2(){
        checking.deposit(-100);
        assertEquals(1000,checking.getBalance());
    }
    @Test
    public void depostiTest3(){
        credit.deposit(0);
        assertEquals(-8,credit.getBalance());

    }
    @Test
    public void depostiTest4(){
        credit.deposit(1000);
        assertEquals(992,credit.getBalance());

    }

    //withdraw
    @Test
    public void withdraw1(){
        savings.withdraw(10000);
        assertEquals(9000,savings.getBalance());
    }
    @Test
    public void withdraw2(){
        savings.withdraw(1000);
        assertEquals(8000,savings.getBalance());
    }
    @Test
    public void withdraw3(){
        checking.withdraw(1000);
        assertEquals(0,checking.getBalance());
    }
    @Test
    public void withdraw4(){
        credit.withdraw(0);
        assertEquals(-8,credit.getBalance());
    }

    //transfer
    @Test
    public void transfer1(){
        credit.transfer(checking,10);
        assertEquals(-18,credit.getBalance());
    }
    @Test
    public void transfer2(){
        credit.transfer(credit,10);
        assertEquals(-8,credit.getBalance());
    }
    @Test
    public void transfer3(){
        savings.transfer(credit,10);
        assertEquals(2,credit.getBalance());
    }
    @Test
    public void transfer4(){
        savings.transfer(credit,10000);
        assertEquals(9992,credit.getBalance());
    }

    //pay someone
    @Test
    public void paySomeone1(){
        customer.setSavings(savings);
        checking.paySomeone(customer,10000);
        assertEquals(1000,checking.getBalance());
    }
    @Test
    public void paySomeone2(){
        customer.setChecking(checking);
        credit.paySomeone(customer,1000);
        assertEquals(-1008,credit.getBalance());


    }


}
package junitlab.bank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junitlab.bank.impl.FirstNationalBank;

public class BankTestFixture {
	Bank bank;
	String account1, account2;
	@Before
	public void init() throws Exception {
		this.bank = new FirstNationalBank(); 
		this.account1 = bank.openAccount();
		this.account2 = bank.openAccount();
		bank.deposit(account1, 1500);
		bank.deposit(account2, 12000);
	}
	
	@Test
	public void testTransfer() throws Exception {
		bank.transfer(account2, account1, 3456);
		Assert.assertEquals(4956, bank.getBalance(account1));
		Assert.assertEquals(8544, bank.getBalance(account2));
	}
	
	@Test(expected = NotEnoughFundsException.class)
	public void testTransferWithoutEnoughFunds() throws Exception {
		bank.transfer(account1, account2, 3456);
	}
}

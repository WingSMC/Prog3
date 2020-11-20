package junitlab.bank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junitlab.bank.impl.FirstNationalBank;


public class BankTest {
	Bank bank;
	@Before
	public void init() {
		this.bank = new FirstNationalBank(); 
	}

	@Test
	public void testOpenAccount() throws Exception {
		Assert.assertEquals(0, bank.getBalance(bank.openAccount()));
	}

	@Test
	public void testUniqueAccount() {
		String account1 = bank.openAccount();
		String account2 = bank.openAccount();
		Assert.assertNotEquals(account1, account2);
	}
	
	@Test(expected = AccountNotExistsException.class)
	public void testInvalidAccount() throws AccountNotExistsException {
		bank.getBalance("asd");
	}
	
	@Test
	public void testDeposit() throws AccountNotExistsException {
		String account = bank.openAccount();
		bank.deposit(account, 1000);
		Assert.assertEquals(1000,  bank.getBalance(account));
	}
}

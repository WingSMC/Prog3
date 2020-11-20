package junitlab.bank;

import org.junit.Test;
import org.junit.Assert;

import junitlab.bank.impl.FirstNationalBank;


public class BankTest {
	@Test
	public void testOpenAccount() throws AccountNotExistsException {
		Bank b = new FirstNationalBank();
		String accNumber = b.openAccount();
		Assert.assertEquals(0, b.getBalance(accNumber));
	}
}

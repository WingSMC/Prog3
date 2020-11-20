package junitlab.bank;

/**
 * Egy kezdetleges takarékszövetkezet interfésze.
 * Alapvető számlakezelési műveleteket definiál.  
 */
public interface Bank {

	/**
	 * Egy új számla megnyitása. A számlán kezdetben nulla az egyenleg.
	 * @return A frissen létrejött számla száma.
	 */
	public String openAccount();
	
	/**
	 * Üres számla megszüntetése.
	 * @param accountNumber A megszüntetendő számlaszám.
	 * @return Sikerült-e elvégezni a műveletet. Ha a számla egyenlege nem nulla, nem lehet megsz�ntetni.
	 * @throws AccountNotExistsException Ha a megadott számlaszám nem létezik.
	 */
	public boolean closeAccount(String accountNumber) throws AccountNotExistsException;
	
	/**
	 * Az aktuális egyenleg lekérdezése.
	 * @param accountNumber A kérdéses számlaszám.
	 * @return Az egyenleg.
	 * @throws AccountNotExistsException Ha a megadott számlaszám nem létezik.
	 */
	public long getBalance(String accountNumber) throws AccountNotExistsException;
	
	/**
	 * Egy adott összeg befizet�se a számlára. Technikai okokb�l csak 100-zal
	 * oszthat� összegeket lehet befizetni a számlára. Az ett�l elt�r� összegeket
	 * a kerek�t�s szabályai szerint kell kezelni.
	 * @param accountNumber A felt�ltend� számlaszám.
	 * @param amount A befizetend� összeg. Mindig pozit�vnak kell lennie.
	 * @throws AccountNotExistsException Ha a megadott számlaszám nem létezik.
	 */
	public void deposit(String accountNumber, long amount) throws AccountNotExistsException;
	
	/**
	 * Egy adott összeg kivétele a számlár�l. Technikai okokb�l csak 100-zal
	 * oszthat� összegeket lehet kifizetni a számlár�l. Az ett�l elt�r� összegeket
	 * a kerek�t�s szabályai szerint kell kezelni.
	 * @param accountNumber A terhelend� számlaszám.
	 * @param amount A kifizetend� összeg. Mindig pozit�vnak kell lennie.
	 * @throws AccountNotExistsException Ha a megadott számlaszám nem létezik.
	 * @throws NotEnoughFundsException Ha kevesebb p�nz van a számlán, mint amit megadtunk.
	 */
	public void withdraw(String accountNumber, long amount) throws AccountNotExistsException, NotEnoughFundsException;
	
	/**
	 * Egy adott összeg �tutal�sa egyik számlár�l a m�sikra. Az �tutal�sokra
	 * nem vonatkozik a sz�zas kerek�t�si szab�ly, tetsz�leges összeg �tutalhat�. 
	 * @param sourceAccount A terhelend� számlaszám.
	 * @param targetAccount A c�l számlaszám, amire utalunk.
	 * @param amount A kifizetend� összeg. Mindig pozit�vnak kell lennie.
	 * @throws AccountNotExistsException Ha a megadott számlaszám nem létezik.
	 * @throws NotEnoughFundsException Ha kevesebb p�nz van a forr�sszámlán, mint amit �t akarunk utalni.
	 */
	public void transfer(String sourceAccount, String targetAccount, long amount) throws AccountNotExistsException, NotEnoughFundsException;
}

package test.model;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.bean.Account;
import main.bean.Ruoli;
import main.bean.UtenteRegistrato;
import main.model.AccountDAO;
import main.utils.Utility;
import test.model.stub.AccountDAOStub;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountDAOTest {
    private AccountDAO accountDAO;
    private AccountDAOStub accountDAOStub;
    private static IDatabaseTester tester;
    
    @BeforeAll
    static void setUpAll() throws ClassNotFoundException {
        // mem indica che il DB deve andare in memoria
        // test indica il nome del DB
        // DB_CLOSE_DELAY=-1 impone ad H2 di eliminare il DB solo quando il processo della JVM termina
        tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:test/resources/schema.sql'",
                "sa",
                ""
        );
        // Refresh permette di svuotare la cache dopo un modifica con setDataSet
        // DeleteAll ci svuota il DB manteneno lo schema
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
    }
    
    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(CategoriaDAOTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("test/resources/initAccountDAOTest.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        accountDAO = new AccountDAO(ds);
        accountDAOStub = new AccountDAOStub(ds);
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    
    @Test
    public void testAuthenticateSuccess() throws SQLException {
    	String username = "us";
    	String password = "pw";
    	
    	Account expected = new Account(0, username, password, 0);
    	
        Account actual = accountDAOStub.authenticate(username, password);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAuthenticateUsernameNotExisting() throws SQLException {
    	String username = "usernameNE";
    	String password = "pw";
    	
        Account actual = accountDAOStub.authenticate(username, password);
        assertEquals(null, actual);
    }
    
    @Test
    public void testAuthenticatePasswordError() throws SQLException {
    	String username = "us";
    	String password = "test";
    	
        Account actual = accountDAOStub.authenticate(username, password);
        assertEquals(null, actual);
    }
    
    @Test
    public void testCheckUsernameExisting() throws SQLException {
    	String username = "us";
    	
        boolean actual = accountDAO.checkUsername(username);
        assertEquals(true, actual);
    }

    @Test
    public void testCheckUsernameNotExisting() throws SQLException {
    	String username = "username";
    	
        boolean actual = accountDAO.checkUsername(username);
        assertEquals(false, actual);
    }
    
    @Test
    public void testRegisterSuccess() throws Exception {
    	String username = "gmagenta";
    	String password = "P4ssword!";
    	String sesso = "M";
    	String nome = "Gianni";
    	String cognome = "Magenta";
    	String email = "gmagenta@gmail.com";
    	String nascita = "1980-12-12";
    	
    	Account acc = new Account(0, username, password, 0);
		UtenteRegistrato user = new UtenteRegistrato(sesso, nome, cognome, email, Utility.toSqlDate(Utility.formatStringToDate(nascita)), 0);
		Ruoli rol = new Ruoli();
		rol.addRuolo(Ruoli.Ruolo.CL);
		
		ITable expected = new FlatXmlDataSetBuilder()
                .build(AccountDAOTest.class.getClassLoader().getResourceAsStream("test/resources/testRegisterSuccess.xml"))
                .getTable("Account");

		accountDAOStub.register(acc, user, rol);

		ITable actual = tester.getConnection().createDataSet().getTable("Account");

		String[] ignoreCol = new String[1];
		ignoreCol[0] = "id";
		Assertion.assertEqualsIgnoreCols(new SortedTable(expected), new SortedTable(actual), ignoreCol);
    }  
    
    
    @Test
    public void testRegisterUsernameExisting() throws Exception {
    	String username = "us";
    	String password = "P4ssword!";
    	String sesso = "M";
    	String nome = "Gianni";
    	String cognome = "Magenta";
    	String email = "gmagenta@gmail.com";
    	String nascita = "1980-12-12";
    	
    	Account acc = new Account(0, username, password, 0);
		UtenteRegistrato user = new UtenteRegistrato(sesso, nome, cognome, email, Utility.toSqlDate(Utility.formatStringToDate(nascita)), 0);
		Ruoli rol = new Ruoli();
		rol.addRuolo(Ruoli.Ruolo.CL);
		
		assertThrows(SQLException.class, ()->accountDAOStub.register(acc, user, rol));
    }
    
    @Test
    public void testRegisterEmailExisting() throws Exception {
    	String username = "gmagenta";
    	String password = "P4ssword!";
    	String sesso = "M";
    	String nome = "Gianni";
    	String cognome = "Magenta";
    	String email = "m@rossi.com";
    	String nascita = "1980-12-12";
    	
    	Account acc = new Account(0, username, password, 0);
		UtenteRegistrato user = new UtenteRegistrato(sesso, nome, cognome, email, Utility.toSqlDate(Utility.formatStringToDate(nascita)), 0);
		Ruoli rol = new Ruoli();
		rol.addRuolo(Ruoli.Ruolo.CL);
		
		assertThrows(SQLException.class, ()->accountDAOStub.register(acc, user, rol));
    }
    
    
}
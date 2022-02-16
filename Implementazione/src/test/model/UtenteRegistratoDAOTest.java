package test.model;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.bean.UtenteRegistrato;
import main.model.RuoliDAO;
import main.model.UtenteRegistratoDAO;
import main.utils.Utility;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

public class UtenteRegistratoDAOTest {
    private UtenteRegistratoDAO utenteRegistratoDAO;
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
	    refreshDataSet("test/resources/initUtenteRegistratoDAOTest.xml");
	    DataSource ds = Mockito.mock(DataSource.class);
	    Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
	    utenteRegistratoDAO = new UtenteRegistratoDAO(ds);
	}
	
	@AfterEach
	public void tearDown() throws Exception {
	    tester.onTearDown();
	}

    @Test
    public void testDoRetrieveByAccountExisting() throws SQLException {
		int accountID = 0;
    	String sesso = "M";
    	String nome = "Gianni";
    	String cognome = "Magenta";
    	String email = "gmagenta@gmail.com";
    	String nascita = "1980-12-12";
    	
		UtenteRegistrato expected = new UtenteRegistrato(sesso, nome, cognome, email, Utility.toSqlDate(Utility.formatStringToDate(nascita)), accountID);
        
		UtenteRegistrato actual = utenteRegistratoDAO.doRetrieveByAccount(accountID);
        assertEquals(expected, actual);
    }

    @Test
    public void testDoRetrieveByAccountNotExisting() throws SQLException {
		int accountID = 100;

		UtenteRegistrato expected = new UtenteRegistrato();
		
		UtenteRegistrato actual = utenteRegistratoDAO.doRetrieveByAccount(accountID);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testCheckEmailExisting() throws SQLException {
    	String email = "gmagenta@gmail.com";
    	
        boolean actual = utenteRegistratoDAO.checkEmail(email);
        assertEquals(true, actual);
    }
    
    @Test
    public void testCheckEmailNotExisting() throws SQLException {
    	String email = "m@bianchi.com";
    	
        boolean actual = utenteRegistratoDAO.checkEmail(email);
        assertEquals(false, actual);
    }
}

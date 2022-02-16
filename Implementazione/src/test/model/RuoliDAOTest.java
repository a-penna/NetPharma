package test.model;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.bean.Ruoli;
import main.model.RuoliDAO;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

public class RuoliDAOTest {
    private RuoliDAO ruoliDAO;
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
	    refreshDataSet("test/resources/init.xml");
	    DataSource ds = Mockito.mock(DataSource.class);
	    Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
	    ruoliDAO = new RuoliDAO(ds);
	}
	
	@AfterEach
	public void tearDown() throws Exception {
	    tester.onTearDown();
	}

    @Test
    public void testDoRetrieveByAccountExisting() throws SQLException {
    	Ruoli expected = new Ruoli();
		expected.addRuolo(Ruoli.Ruolo.CL);
		
		int accountID = 0;
        Ruoli actual = ruoliDAO.doRetrieveByAccount(accountID);
        assertEquals(expected, actual);
    }

    @Test
    public void testDoRetrieveByAccountNotExisting() throws SQLException {
    	Ruoli expected = new Ruoli();
		
		int accountID = 100;
        Ruoli actual = ruoliDAO.doRetrieveByAccount(accountID);
        assertEquals(expected, actual);
    }
}
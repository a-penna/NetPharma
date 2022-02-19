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

import main.bean.RigaOrdine;
import main.model.RigaOrdineDAO;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RigaOrdineDAOTest {
    private RigaOrdineDAO rigaDAO;
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
        refreshDataSet("test/resources/initRigaOrdineDAOTest.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        rigaDAO = new RigaOrdineDAO(ds);
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @Test
    public void testDoRetrieveAllByOrderExisting() throws SQLException {
    	String orderID = "1-1";
    	
    	Collection<RigaOrdine> expected = new LinkedList<RigaOrdine>();
    	RigaOrdine bean = new RigaOrdine();
    	bean.setOrdine(orderID);
    	bean.setPrezzoAlPezzo(new BigDecimal("4.35"));
    	bean.setQuantity(3);
    	bean.setProdotto(883);
    	expected.add(bean);
    	
    	Collection<RigaOrdine> actual = rigaDAO.doRetrieveAllByOrder(orderID);
    	assertEquals(expected, actual);
    }
    
    @Test
    public void testDoRetrieveAllByOrderNotExisting() throws SQLException {
    	String orderID = "000";
    	
    	Collection<RigaOrdine> expected = new LinkedList<RigaOrdine>();
    	
    	Collection<RigaOrdine> actual = rigaDAO.doRetrieveAllByOrder(orderID);
    	assertEquals(expected, actual);
    }
    
    @Test
    public void testDoSaveSuccess() throws Exception {
    	RigaOrdine bean = new RigaOrdine();
    	bean.setProdotto(884);
    	bean.setOrdine("1-1");
    	bean.setQuantity(4);
    	bean.setPrezzoAlPezzo(new BigDecimal("4.35"));
    	

    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountDAOTest.class.getClassLoader().getResourceAsStream("test/resources/doSaveSuccessRigaTest.xml"))
                .getTable("Riga_Ordine");

    	Boolean actual = rigaDAO.doSave(bean);

		ITable actualTable = tester.getConnection().createDataSet().getTable("Riga_Ordine");

		assertEquals(Boolean.TRUE, actual);
		Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoSaveProdottoNotExisting() throws Exception {
    	RigaOrdine bean = new RigaOrdine();
    	bean.setProdotto(8888888);
    	bean.setOrdine("1-1");
    	bean.setQuantity(4);
    	bean.setPrezzoAlPezzo(new BigDecimal("4.35"));
    	

    	Boolean actual = rigaDAO.doSave(bean);
    	assertEquals(Boolean.FALSE, actual);
    } 
}
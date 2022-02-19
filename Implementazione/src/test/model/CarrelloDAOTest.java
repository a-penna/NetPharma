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

import main.bean.Carrello;
import main.bean.Prodotto;
import main.model.CarrelloDAO;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarrelloDAOTest {
    private CarrelloDAO carrelloDAO;
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
        refreshDataSet("test/resources/initCarrelloDAOTest.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        carrelloDAO = new CarrelloDAO(ds);
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    
    @Test
    public void testDoRetrieveByUsernameExisting() throws SQLException {
    	String username = "us";
    	
    	Carrello expected = new Carrello();
    	
    	Prodotto bean = new Prodotto();
    	bean.setId(883);
    	bean.setNome("prodotto1");
    	bean.setMarchio("marchio1");
    	bean.setProduttore("produttore1");
    	bean.setFormato("formato1");
    	bean.setDescrizione("descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1");
    	bean.setDisponibilita(100);
    	bean.setPrezzo(new BigDecimal("4.35"));
    	
    	expected.setItem(bean,2);
    	
    	Prodotto bean2 = new Prodotto();
    	bean2.setId(884);
    	bean2.setNome("prodotto2");
    	bean2.setMarchio("marchio2");
    	bean2.setProduttore("produttore2");
    	bean2.setFormato("formato2");
    	bean2.setDescrizione("descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2");
    	bean2.setDisponibilita(50);
    	bean2.setPrezzo(new BigDecimal("4"));
    	
    	expected.setItem(bean2,1);
    	
        Carrello actual = carrelloDAO.doRetrieveByUsername(username);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testDoRetrieveByUsernameNotExisting() throws SQLException {
    	String username = "usernameNE";
    	
    	Carrello expected = new Carrello();
    	
        Carrello actual = carrelloDAO.doRetrieveByUsername(username);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testUpdateQuantitySuccess() throws Exception {
    	String username = "us";
    	int prodottoID = 883;
    	int quantity = 10;
    	
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountDAOTest.class.getClassLoader().getResourceAsStream("test/resources/updateQuantitySuccessCarrelloTest.xml"))
                .getTable("Carrello");


		boolean actualValue = carrelloDAO.updateQuantity(username, prodottoID, quantity);

		ITable actualTable = tester.getConnection().createDataSet().getTable("Carrello");

		Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
        assertEquals(true, actualValue);
    }
    
    @Test
    public void testUpdateQuantityUsernameNotExisting() throws Exception {
    	String username = "usernameNE";
    	int prodottoID = 883;
    	int quantity = 10;

		boolean actualValue = carrelloDAO.updateQuantity(username, prodottoID, quantity);

        assertEquals(false, actualValue);
    }
    
    @Test
    public void testRemoveProdottoSuccess() throws Exception {
    	String username = "us";
    	int prodottoID = 884;
    	
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountDAOTest.class.getClassLoader().getResourceAsStream("test/resources/removeProdottoCarrelloTest.xml"))
                .getTable("Carrello");


    	boolean actualValue = carrelloDAO.removeProdotto(username, prodottoID);

		ITable actualTable = tester.getConnection().createDataSet().getTable("Carrello");

		Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
        assertEquals(true, actualValue);
    }
    
    @Test
    public void testRemoveProdottoUsernameNotExisting() throws Exception {
    	String username = "usernameNE";
    	int prodottoID = 884;
    	
    	boolean actualValue = carrelloDAO.removeProdotto(username, prodottoID);

    	assertEquals(false, actualValue);
    }
    
    @Test
    public void testInserProdottoSuccess() throws Exception {
    	String username = "us";
    	int prodottoID = 885;
    	int quantity = 3;
    	
    	ITable expectedTable = new FlatXmlDataSetBuilder()
    			.build(AccountDAOTest.class.getClassLoader().getResourceAsStream("test/resources/insertProdottoCarrelloTest.xml"))
    			.getTable("Carrello");

    	boolean actualValue = carrelloDAO.insertProdotto(username, prodottoID, quantity);

		ITable actualTable = tester.getConnection().createDataSet().getTable("Carrello");

		Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    	assertEquals(true, actualValue);
    }
    
    @Test
    public void testInsertProdottoUsernameNotExisting() throws Exception {
    	String username = "usernameNE";
    	int prodottoID = 885;
    	int quantity = 3;
    	
    	assertThrows(SQLException.class,
			      () -> carrelloDAO.insertProdotto(username, prodottoID, quantity));

    }
    
    @Test
    public void testClearCartSuccess() throws Exception {
    	String username = "us";
    	
    	ITable expectedTable = new FlatXmlDataSetBuilder()
    			.build(AccountDAOTest.class.getClassLoader().getResourceAsStream("test/resources/clearCarrelloTest.xml"))
    			.getTable("Carrello");


    	boolean actual = carrelloDAO.clearCart(username);

    	ITable actualTable = tester.getConnection().createDataSet().getTable("Carrello");
    	
    	Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    	assertEquals(true, actual);
    }
    
    @Test
    public void testClearCartUsernameNotExisting() throws Exception {
    	String username = "usernameNE";
    	
    	boolean actual = carrelloDAO.clearCart(username);

    	assertEquals(false, actual);
    }
    
}
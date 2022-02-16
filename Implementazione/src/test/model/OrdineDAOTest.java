package test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedList;
import java.sql.SQLException;
import java.util.Collection;
import javax.sql.DataSource;

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

import main.bean.Ordine;
import main.model.OrdineDAO;
import main.utils.Utility;


public class OrdineDAOTest {

	private OrdineDAO ordine;
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
	    ordine = new OrdineDAO(ds);
	}
	
	@AfterEach
	public void tearDown() throws Exception {
	    tester.onTearDown();
	}

    @Test
    public void doSaveCheckTrueChecking() throws SQLException {
    	Ordine bean = new Ordine();
		bean.setCliente("m@rossi.com");
		bean.setData_ordine(new Date(System.currentTimeMillis()));
		bean.setNomeRicevente("Mario");
		bean.setCognomeRicevente("Rossi");
		bean.setEmail("m@rossi.com");
		bean.setCellulare("333333");
		bean.setNcivico(11);
		bean.setVia("Via");
		bean.setCitta("Citt?1");
		bean.setPaese("Paese1");
		bean.setProvincia("Provincia1");
		bean.setCAP("80001");
		
		bean.setId("1");
		bean.setStato("Si");
		bean.setPrezzo(new BigDecimal(30));
		
		
		
        boolean actual = ordine.doSaveCheck(bean);
        
        assertEquals(true, actual);
    }
    
    @Test
    public void doSaveCheckFalseChecking() throws SQLException {
    	Ordine bean = new Ordine();
    	bean.setCliente("m@rossi.com");
		bean.setData_ordine(new Date(System.currentTimeMillis()));
		bean.setNomeRicevente("Mario");
		bean.setCognomeRicevente("Rossi");
		bean.setEmail("m@rossi.com");
		bean.setCellulare("333333");
		bean.setNcivico(11);
		bean.setVia("Via");
		bean.setCitta("Citt?1");
		bean.setPaese("Paese1");
		bean.setProvincia("Provincia1");
		bean.setCAP("80001");
		
		bean.setId("5");
		bean.setStato("Si");
		bean.setPrezzo(new BigDecimal(30));
		
		
		
        boolean actual = ordine.doSaveCheck(bean);
        
        assertEquals(false, actual);
        
    }
   
    @Test
    public void doUpdateStatusTrue() throws SQLException {
    	Ordine bean = new Ordine();
		bean.setCliente("g@nappi.com"); 
		
		bean.setId("3"); 
		
        boolean actual = ordine.doUpdateStatus(bean,Utility.MILLIS_IN_DAY * 3);
        
        assertEquals(true, actual);
    }
    
    
    @Test
    public void doUpdateStatusFalse() throws SQLException {
    	Ordine bean = new Ordine();
		
		bean.setId("100"); 
		
        boolean actual = ordine.doUpdateStatus(bean,System.currentTimeMillis());
        
        assertEquals(false, actual);
    }
    
    @Test
    public void doRetriveByIDTrue() throws SQLException {
    	Ordine bean = new Ordine();
    	bean.setId("5");
    	Ordine actual = ordine.doRetrieveByKey("5");
    	assertEquals(bean.getId(),actual.getId());
    }
    
    
    
    @Test
    public void doRetriveByIDFalse() throws SQLException {
    	Ordine actual = ordine.doRetrieveByKey("1000");
    	assertEquals(null,actual);
    }
    
    @Test
    public void doRetriveAll() throws SQLException {
    	
    	Collection<Ordine> lista = new LinkedList<Ordine>();
    	Ordine bean = new Ordine();
    	Ordine bean2 = new Ordine();
    	bean.setId("5");
    	bean.setData_ordine(Utility.toSqlDate(Utility.formatStringToDate("2022-02-10")));
    	bean.setData_arrivo(Utility.toSqlDate(Utility.formatStringToDate("2022-02-13")));
    	bean.setPrezzo(new BigDecimal(50));
    	bean.setStato("Si");
    	bean.setCognomeRicevente("Nappi");
    	bean.setNomeRicevente("Gaia");
    	bean.setNcivico(31);
    	bean.setVia("vvv");
    	bean.setCitta("a");
    	bean.setEmail("g@nappi.com");
    	bean.setProvincia("b");
    	bean.setCellulare("33353");
    	bean.setCAP("80000");
    	bean.setPaese("a");
    
    	bean.setCliente("g@nappi.com");
    	
    	bean2.setId("3");
    	bean2.setData_ordine(Utility.toSqlDate(Utility.formatStringToDate("2022-02-10")));
    	bean2.setPrezzo(new BigDecimal(50));
    	bean2.setStato("No");
    	bean2.setCognomeRicevente("Nappi");
    	bean2.setNomeRicevente("Gaia");
    	bean2.setNcivico(31);
    	bean2.setVia("vvv");
    	bean2.setCitta("a");
    	bean2.setEmail("g@nappi.com");
    	bean2.setProvincia("b");
    	bean2.setCAP("80000");
    	bean2.setCliente("g@nappi.com");
    	bean2.setCellulare("33353");
    	bean2.setPaese("a");
    	lista.add(bean);
    	
    	lista.add(bean2);
    	Collection<Ordine> actual = ordine.doRetrieveAll("");
    	assertEquals(lista,actual);
    	
    }
    
    @Test
    public void doRetriveAllDaSpedire() throws SQLException {
    	
    	Collection<Ordine> lista = new LinkedList<Ordine>();
    	Ordine bean = new Ordine();
    	bean.setId("3");
    	bean.setData_ordine(Utility.toSqlDate(Utility.formatStringToDate("2022-02-10")));
    	bean.setPrezzo(new BigDecimal(50));
    	bean.setStato("No");
    	bean.setCognomeRicevente("Nappi");
    	bean.setNomeRicevente("Gaia");
    	bean.setNcivico(31);
    	bean.setVia("vvv");
    	bean.setCitta("a");
    	bean.setEmail("g@nappi.com");
    	bean.setProvincia("b");
    	bean.setCAP("80000");
    	bean.setCliente("g@nappi.com");
    	bean.setCellulare("33353");
    	bean.setPaese("a");
    	lista.add(bean);
    	Collection<Ordine> actual = ordine.doRetrieveAllDaSpedire();
    	assertEquals(lista,actual);
    	
    }
   

    
}
	
	
	
	


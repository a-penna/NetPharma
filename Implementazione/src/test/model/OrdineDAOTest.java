package test.model;

import static org.junit.Assert.assertArrayEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedList;
import java.sql.SQLException;
import java.util.Collection;
import javax.sql.DataSource;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.bean.Ordine;

import main.model.OrdineDAO;


public class OrdineDAOTest extends DataSourceBasedDBTestCase {

	private OrdineDAO ordine;
private JdbcDataSource dataSource;
    
    @Override
    protected DataSource getDataSource() {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:test/resources/schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("test/resources/init.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @BeforeEach
    public void setUp() throws Exception {
        // setUp del padre serve a (1) chiamare il nostro getSetUpOperation, e (2) il nostro getDataSet() per inizializzare il DB
        super.setUp();
        ordine = new OrdineDAO(dataSource);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // tearDown del padre serve a chiamare il nostro getTearDownOperation
        super.tearDown();
    }

    @Test
    public void doSaveCheckTrueChecking() throws SQLException {
    	Ordine bean = new Ordine();
		bean.setCliente("m@rossi.com");
		bean.setData_ordine(new Date(System.currentTimeMillis()));
		
		bean.setId("5");
		bean.setStato("Si");
		bean.setPrezzo(new BigDecimal(30));
		
		int id = 1;
		
        boolean actual = ordine.doSaveCheck(bean);
        
        assertEquals(true, actual);
        
    }
    
    @Test
    public void doSaveCheckFalseChecking() throws SQLException {
    	Ordine bean = new Ordine();
		bean.setCliente("m@rossi.com");
		bean.setData_ordine(new Date(System.currentTimeMillis()));
		
		bean.setId("5");
		bean.setStato("Si");
		bean.setPrezzo(new BigDecimal(30));
		
		int id = 100;
		
        boolean actual = ordine.doSaveCheck(bean);
        
        assertEquals(false, actual);
        
    }
   
    @Test
    public void doUpdateStatusTrue() throws SQLException {
    	Ordine bean = new Ordine();
		//bean.setCliente("m@rossi.com"); Serve altro utente
		
		bean.setId("2"); //Id dell'altro ordine
		
		
		
		
        boolean actual = ordine.doUpdateStatus(bean,System.currentTimeMillis());
        
        assertEquals(true, actual);
        
    }
    
    
    @Test
    public void doUpdateStatusFalse() throws SQLException {
    	Ordine bean = new Ordine();
		//bean.setCliente("m@rossi.com"); Serve altro utente
		
		bean.setId("3"); //Id dell'altro ordine
		
		
		
		
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
    
    	Ordine bean = new Ordine();
    	bean.setId("1000");
    	Ordine actual = ordine.doRetrieveByKey("5");
    	assertEquals(bean.getId(),actual.getId());
    	
    	
    }
    
    @Test
    public void doRetriveAll() throws SQLException {
    
    	Collection<Ordine> lista = new LinkedList<Ordine>();
    	Ordine bean = new Ordine();
    	bean.setId("5");
    	bean.setData_ordine(new Date(2022,2,10));
    	bean.setData_arrivo(new Date(2022,2,13));
    	bean.setPrezzo(new BigDecimal(50));
    	bean.setStato("Si");
    	//Inserire dati spedizione
    	bean.setCliente("g@nappi.com");
    	lista.add(bean);
    	Collection<Ordine> actual = ordine.doRetrieveAll("");
    	assertEquals(1,actual.size());
    	assertArrayEquals(lista.toArray(),actual.toArray());
    	
    }
    
    
}
	
	
	
	


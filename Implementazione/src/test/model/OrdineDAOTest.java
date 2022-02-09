package test.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

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
import main.bean.Ruoli;
import main.model.OrdineDAO;
import main.model.RuoliDAO;

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
		
		bean.setId(5);
		bean.setStato("Si");
		bean.setPrezzo(new BigDecimal(30));
		
		int id = 1;
		
        boolean actual = ordine.doSaveCheck(bean,id);
        
        assertEquals(true, actual);
        
    }

   
}
	
	
	
	


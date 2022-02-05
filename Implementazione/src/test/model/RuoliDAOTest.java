package test.model;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.bean.Ruoli;
import main.model.RuoliDAO;

import javax.sql.DataSource;
import java.sql.SQLException;

public class RuoliDAOTest extends DataSourceBasedDBTestCase {
    private RuoliDAO ruoliDAO;
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
        ruoliDAO = new RuoliDAO(dataSource);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // tearDown del padre serve a chiamare il nostro getTearDownOperation
        super.tearDown();
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
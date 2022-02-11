package test.model;

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

import main.bean.Categoria;
import main.model.CategoriaDAO;

public class CategoriaDAOTest extends DataSourceBasedDBTestCase {
    private CategoriaDAO categoriaDAO;
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
        categoriaDAO = new CategoriaDAO(dataSource);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // tearDown del padre serve a chiamare il nostro getTearDownOperation
        super.tearDown();
    }


    @Test
    public void doRetrieveByKeyExisting()  throws SQLException {
    	int id = 1;
    	Categoria bean = new Categoria();
    	bean.setId(1);
    	Categoria actual = categoriaDAO.doRetrieveByKey(id);
    	assertEquals(bean,actual);
    }
    
    @Test
    public void doRetrieveByKeyNotExisting()  throws SQLException {
    	int id = 2;
    	Categoria bean = new Categoria();
    	bean.setId(1);
    	Categoria actual = categoriaDAO.doRetrieveByKey(id);
    	assertEquals(bean,actual);
    }
    

    @Test
    public void doRetrieveByNameExisting()  throws SQLException {
    	String nome = "Cosmetici";
    	Categoria bean = new Categoria();
    	bean.setNome("Cosmetici");
     	Categoria actual = categoriaDAO.doRetrieveByName(nome);
    	assertEquals(bean,actual);
    }
    
    @Test
    public void doRetrieveByNameNotExisting()  throws SQLException {
    	String nome = "Vestiti";
    	Categoria bean = new Categoria();
    	bean.setNome("Cosmetici");
     	Categoria actual = categoriaDAO.doRetrieveByName(nome);
    	assertEquals(bean,actual);
    }
    

    @Test
    public void doRetrieveAllExisting()  throws SQLException {
    	;
    }
    
    @Test
    public void doRetrieveAllNotExisting()  throws SQLException {
    	;
    }
    

    @Test
    public void doSaveTrue()  throws SQLException {
    	Categoria bean = new Categoria();
    	bean.setNome("Cosmetici");
    
    	Categoria actual = new Categoria();
    	actual.setNome("Cosmetici");
    	categoriaDAO.doSave(actual);
    	assertEquals(bean,actual);
    }
    
    @Test
    public void doSaveFalse()  throws SQLException {
    	Categoria bean = new Categoria();
    	bean.setNome("Vestiti");
    
    	Categoria actual = new Categoria();
    	actual.setNome("Cosmetici");
    	categoriaDAO.doSave(actual);
    	assertEquals(bean,actual);
    }
    

    @Test
    public void doDeleteTrue()  throws SQLException {
    	;
    }
    
    @Test
    public void doDeleteFalse()  throws SQLException {
    	;
    }
    
}
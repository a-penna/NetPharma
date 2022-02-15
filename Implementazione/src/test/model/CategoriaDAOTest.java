package test.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
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
    	String nome = "Raffreddore";
    	Categoria bean = new Categoria();
    	bean.setNome("Raffreddore");
     	Categoria actual = categoriaDAO.doRetrieveByName(nome);
    	assertEquals(bean,actual);
    }
    
    @Test
    public void doRetrieveByNameNotExisting()  throws SQLException {
    	String nome = "Raffreddore";
    	Categoria bean = new Categoria();
    	bean.setNome("Protezione Antivirale");
     	Categoria actual = categoriaDAO.doRetrieveByName(nome);
    	assertEquals(bean,actual);
    }
    

    @Test
    public void doRetrieveAllExisting()  throws SQLException {
    	Collection<Categoria> expected = new LinkedList<Categoria>();
    	expected.add(new Categoria("Mamma&bambino"));
    	expected.add(new Categoria("Cosmetici"));
    	expected.add(new Categoria("Protezione antivirale"));
    	
    	Collection<Categoria> actual =
    	categoriaDAO.doRetrieveAll("");
    	assertEquals(3, actual.size());
    	assertArrayEquals(expected.toArray(), actual.toArray());
    }
    
    @Test
    public void doRetrieveAllNotExisting()  throws SQLException {
    	Collection<Categoria> expected = new LinkedList<Categoria>();
    	expected.add(new Categoria("Mamma & bambino"));
    	expected.add(new Categoria("Raffreddore"));
       	expected.add(new Categoria("Protezione Antivirale"));
    	
    	
    	Collection<Categoria> actual =
    	categoriaDAO.doRetrieveAll("");
    	assertEquals(3, actual.size());
    	assertArrayEquals(expected.toArray(), actual.toArray());
    }
    

    @Test
    public void doSaveTrue()  throws Exception {

        IDatabaseTester tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/schema.sql'",
                "sa",
                ""
        );
        // Refresh permette di svuotare la cache dopo un modifica con setDataSet
        // DeleteAll ci svuota il DB manteneno lo schema
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(CategoriaDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/doSaveTestTrue.xml"))
    	 .getTable("Categoria");

    	 // (omesso) Prepara e lancia metodo sotto test
    	 Categoria categoria = new Categoria();
    	 categoria.setNome("raffreddore");
    	 categoriaDAO.doSave(categoria); 
    	 
    	 // Ottieni lo stato post-inserimento
    	 ITable actual = tester.getConnection()
    	 .createDataSet().getTable("CATEGORIA");
    	 // Assert di DBUnit (debole all'ordinamento)
    	 Assertion.assertEquals(
    	 new SortedTable(expected),
    	 new SortedTable(actual)
    	 );
    	 
    }
    
    @Test
    public void doSaveFalse()  throws Exception {

        IDatabaseTester tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/schema.sql'",
                "sa",
                ""
        );
        // Refresh permette di svuotare la cache dopo un modifica con setDataSet
        // DeleteAll ci svuota il DB manteneno lo schema
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(CategoriaDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/doSaveTestFalse.xml"))
    	 .getTable("Categoria");

    	 // (omesso) Prepara e lancia metodo sotto test
    	 Categoria categoria = new Categoria();
    	 categoria.setNome("raffreddore");
    	 categoriaDAO.doSave(categoria); 
    	 
    	 // Ottieni lo stato post-inserimento
    	 ITable actual = tester.getConnection()
    	 .createDataSet().getTable("CATEGORIA");
    	 // Assert di DBUnit (debole all'ordinamento)
    	 Assertion.assertEquals(
    	 new SortedTable(expected),
    	 new SortedTable(actual)
    	 );
    	 
    }
    

    @Test
    public void doDeleteTrue()  throws Exception {

        IDatabaseTester tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/schema.sql'",
                "sa",
                ""
        );
        // Refresh permette di svuotare la cache dopo un modifica con setDataSet
        // DeleteAll ci svuota il DB manteneno lo schema
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(CategoriaDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/doDeleteTestTrue.xml"))
    	 .getTable("Categoria");

    	 // (omesso) Prepara e lancia metodo sotto test
    	 Categoria categoria1 = new Categoria();
    	 categoria1.setNome("raffreddore");
    	 Categoria categoria2 = new Categoria();
    	 categoria2.setNome("protezione antivirale");
    	 categoriaDAO.doDelete(categoria2); 
    	 
    	 // Ottieni lo stato post-inserimento
    	 ITable actual = tester.getConnection()
    	 .createDataSet().getTable("CATEGORIA");
    	 // Assert di DBUnit (debole all'ordinamento)
    	 Assertion.assertEquals(
    	 new SortedTable(expected),
    	 new SortedTable(actual)
    	 );
    	 
    }
    
    @Test
    public void doDeleteFalse()  throws Exception {

        IDatabaseTester tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/schema.sql'",
                "sa",
                ""
        );
        // Refresh permette di svuotare la cache dopo un modifica con setDataSet
        // DeleteAll ci svuota il DB manteneno lo schema
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(CategoriaDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/doDeleteTestFalse.xml"))
    	 .getTable("Categoria");

    	 // (omesso) Prepara e lancia metodo sotto test
    	 Categoria categoria1 = new Categoria();
    	 categoria1.setNome("raffreddore");
    	 Categoria categoria2 = new Categoria();
    	 categoria2.setNome("protezione antivirale");
    	 categoriaDAO.doDelete(categoria2); 
    	 
    	 // Ottieni lo stato post-inserimento
    	 ITable actual = tester.getConnection()
    	 .createDataSet().getTable("CATEGORIA");
    	 // Assert di DBUnit (debole all'ordinamento)
    	 Assertion.assertEquals(
    	 new SortedTable(expected),
    	 new SortedTable(actual)
    	 );	 
    }
    
    @Test
    public void checkCategoriaTrue() throws SQLException {
    	String nome = "Raffreddore";
    	boolean actual = categoriaDAO.checkCategoria(nome);
    	assertEquals(true, actual);
    }
    
    @Test
    public void checkCategoriaFalse() throws SQLException {
    	String nome = "Vestiti";
    	boolean actual = categoriaDAO.checkCategoria(nome);
    	assertEquals(false, actual);
    }
    
}
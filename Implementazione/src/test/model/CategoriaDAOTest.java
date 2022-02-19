package test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

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

import main.bean.Categoria;
import main.model.CategoriaDAO;

public class CategoriaDAOTest {
    private CategoriaDAO categoriaDAO;
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
        categoriaDAO = new CategoriaDAO(ds);
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
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
    public void doRetrieveByNameExisting()  throws SQLException {
    	String nome = "protezione antivirale";
    	Categoria bean = new Categoria();
    	bean.setNome("protezione antivirale");
     	Categoria actual = categoriaDAO.doRetrieveByName(nome);
    	assertEquals(bean,actual);
    }
  

    @Test
    public void doRetrieveAllExisting()  throws SQLException {
    	Collection<Categoria> expected = new LinkedList<Categoria>();
    	Categoria categoria1 = new Categoria("mammaebambino");
    	categoria1.setId(0);
    	Categoria categoria2 = new Categoria("protezione antivirale");
    	categoria2.setId(1);
    	expected.add(categoria1);
    	expected.add(categoria2);
    	
    	Collection<Categoria> actual = categoriaDAO.doRetrieveAll("");
    	assertEquals(2, actual.size());
    	assertArrayEquals(expected.toArray(), actual.toArray());
    }
    

    @Test
    public void doSaveTrue()  throws Exception {
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(CategoriaDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/doSaveTestTrue.xml"))
    	 .getTable("Categoria");

    	 // (omesso) Prepara e lancia metodo sotto test
    	 Categoria categoria = new Categoria();
    	 categoria.setNome("raffreddore");
    	 categoriaDAO.doSave(categoria); 
    	 
     	 String[] ignoreCol = new String[1];
     	 ignoreCol[0] = "id";
     	
    	 // Ottieni lo stato post-inserimento
    	 ITable actual = tester.getConnection()
    	 .createDataSet().getTable("CATEGORIA");
    	 // Assert di DBUnit (debole all'ordinamento)
    	 Assertion.assertEqualsIgnoreCols(
    	 new SortedTable(expected),
    	 new SortedTable(actual),
    	 ignoreCol
    	 );
    	 
    }
    
    @Test
    public void doDeleteExisting()  throws Exception {
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(CategoriaDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/doDeleteTestTrue.xml"))
    	 .getTable("Categoria");

    	 // (omesso) Prepara e lancia metodo sotto test
    	 Categoria categoria2 = new Categoria();
    	 categoria2.setId(1);
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
    public void doDeleteNotExisting()  throws Exception {
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(CategoriaDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/doDeleteTestFalse.xml"))
    	 .getTable("Categoria");

    	 // (omesso) Prepara e lancia metodo sotto test
    	 Categoria categoria2 = new Categoria();
    	 categoria2.setId(100);
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
    	String nome = "mammaebambino";
    	boolean actual = categoriaDAO.checkCategoria(nome);
    	assertEquals(true, actual);
    }
    
    @Test
    public void checkCategoriaFalse() throws SQLException {
    	String nome = "vestiti";
    	boolean actual = categoriaDAO.checkCategoria(nome);
    	assertEquals(false, actual);
    }
    
}
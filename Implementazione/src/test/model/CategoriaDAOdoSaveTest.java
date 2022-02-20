package test.model;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.bean.Categoria;
import main.model.CategoriaDAO;

public class CategoriaDAOdoSaveTest {
	@Test
    public void doSaveTrue()  throws Exception {
    	// Prepara stato atteso sottoforma di ITable
    	IDatabaseTester tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
    			"jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:test/resources/schema.sql'",
    			"sa",
    			""
    			);
    	// Refresh permette di svuotare la cache dopo un modifica con setDataSet
    	// DeleteAll ci svuota il DB manteneno lo schema
    	tester.setSetUpOperation(DatabaseOperation.REFRESH);
    	tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
    	IDataSet initialState = new FlatXmlDataSetBuilder()
    			.build(CategoriaDAOTest.class.getClassLoader().getResourceAsStream("test/resources/doSaveInit.xml"));
    	tester.setDataSet(initialState);
    	tester.onSetup();
    	DataSource ds = Mockito.mock(DataSource.class);
    	Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
    	CategoriaDAO categoriaDAO = new CategoriaDAO(ds);
    	
    	ITable expected = new FlatXmlDataSetBuilder()
    			.build(CategoriaDAOTest.class.getClassLoader()
    					.getResourceAsStream("test/resources/doSaveTestTrue.xml"))
    			.getTable("Categoria");
    	
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
}

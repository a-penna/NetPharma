package test.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.sql.DataSource;

import main.bean.Prodotto;
import main.model.ProdottoDAO;


public class ProdottoDAOTest {
    private ProdottoDAO prodottoDAO;
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
	    prodottoDAO = new ProdottoDAO(ds);
	}
	
	@AfterEach
	public void tearDown() throws Exception {
	    tester.onTearDown();
	}

    @Test
    public void doRetrieveByKeyExisting()  throws SQLException {
    	int id = 883;
    	Prodotto bean = new Prodotto();
    	bean.setId(883);
    	bean.setNome("prodotto1");
    	bean.setMarchio("marchio1");
    	bean.setProduttore("produttore1");
    	bean.setFormato("formato1");
    	bean.setDescrizione("descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1");
    	bean.setDisponibilita(100);
    	bean.setCategoria("mammaebambino");
    	bean.setPrezzo(new BigDecimal("4.35"));
    	
    	Prodotto actual = prodottoDAO.doRetrieveByKey(id);
    	assertEquals(bean,actual);
    }
    
    @Test
    public void doRetrieveByKeyNotExisting()  throws SQLException {
    	int id = 887;
    	
    	Prodotto expected = new Prodotto();
    	
    	Prodotto actual = prodottoDAO.doRetrieveByKey(id);
    	assertEquals(expected,actual);
    }    

    @Test
    public void doRetrieveAllExisting()  throws SQLException {
    	Collection<Prodotto> expected = new LinkedList<Prodotto>();
    	Prodotto bean = new Prodotto();
    	bean.setId(883);
    	bean.setNome("prodotto1");
    	bean.setMarchio("marchio1");
    	bean.setProduttore("produttore1");
    	bean.setFormato("formato1");
    	bean.setDescrizione("descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1");
    	bean.setDisponibilita(100);
    	bean.setPrezzo(new BigDecimal("4.35"));
    	expected.add(bean);
    	
    	Prodotto bean2 = new Prodotto();
    	bean2.setId(884);
    	bean2.setNome("prodotto2");
    	bean2.setMarchio("marchio2");
    	bean2.setProduttore("produttore2");
    	bean2.setFormato("formato2");
    	bean2.setDescrizione("descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2");
    	bean2.setDisponibilita(50);
    	bean2.setPrezzo(new BigDecimal("4"));
    	expected.add(bean2);
    	
    	Prodotto bean3 = new Prodotto();
    	bean3.setId(891);
    	bean3.setNome("prodotto10");
    	bean3.setMarchio("marchio10");
    	bean3.setProduttore("produttore10");
    	bean3.setFormato("formato10");
    	bean3.setDescrizione("descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10");
    	bean3.setDisponibilita(40);
    	bean3.setPrezzo(new BigDecimal(7));
    	expected.add(bean3);
    	
    	Collection<Prodotto> actual = prodottoDAO.doRetrieveAll("");
    	assertEquals(expected,actual);
    }

    @Test
    public void doSaveTrue()  throws Exception {
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(ProdottoDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/doSaveProdottoTest.xml"))
    	 .getTable("Prodotto");

    	 Prodotto prodotto = new Prodotto();
    	 prodotto.setId(885);
     	 prodotto.setNome("prodotto3");
     	 prodotto.setMarchio("marchio3");
     	 prodotto.setProduttore("produttore3");
     	 prodotto.setFormato("formato3");
     	 prodotto.setDescrizione("descrizione3");
     	 prodotto.setDisponibilita(50);
     	 prodotto.setPrezzo(new BigDecimal(4));
    	 prodottoDAO.doSave(prodotto); 
    	 
    	 // Ottieni lo stato post-inserimento
    	 ITable actual = tester.getConnection()
    	 .createDataSet().getTable("PRODOTTO");
    	 // Assert di DBUnit (debole all'ordinamento)
    	 String[] ignoreCol = new String[1];
  		 ignoreCol[0] = "foto";
  		 Assertion.assertEqualsIgnoreCols(new SortedTable(expected), new SortedTable(actual), ignoreCol);
   
    }
    
    @Test
    public void doSaveFalse()  throws SQLException {
    	    	
    	Prodotto bean = new Prodotto();
		bean.setId(883);
		bean.setNome("prodotto1");
		bean.setMarchio("marchio1");
		bean.setProduttore("produttore1");
		bean.setFormato("formato1");
		bean.setDescrizione("descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1");
		bean.setDisponibilita(100);
		bean.setPrezzo(new BigDecimal(4));
		
		assertThrows(SQLException.class,
			      () -> prodottoDAO.doSave(bean));
    }
    
    @Test
    public void doUpdate()  throws Exception {
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(ProdottoDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/doUpdateProdottoTest.xml"))
    	 .getTable("Prodotto");

    	 Prodotto bean = new Prodotto();
	     bean.setId(884);
	     bean.setNome("prodottoUpdated");
	     bean.setMarchio("marchioUpdated");
	     bean.setProduttore("produttoreUpdated");
	     bean.setFormato("formatoUpdated");
	     bean.setDescrizione("Updated");
	     bean.setDisponibilita(10);
	     bean.setPrezzo(new BigDecimal(5));
    	 prodottoDAO.doUpdate(bean); 
    	 
    	 // Ottieni lo stato post-inserimento
    	 ITable actual = tester.getConnection()
    	 .createDataSet().getTable("PRODOTTO");
    	 // Assert di DBUnit (debole all'ordinamento)
    	 String[] ignoreCol = new String[1];
  		 ignoreCol[0] = "foto";
  		 Assertion.assertEqualsIgnoreCols(new SortedTable(expected), new SortedTable(actual), ignoreCol);
   
    }
    
    
    @Test
    public void updateCategoriaTrue()  throws Exception {
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(ProdottoDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/Prodotto.xml"))
    	 .getTable("Prodotto");

    	 prodottoDAO.updateCategoria(883,"protezione antivirale"); 
    	 
    	 // Ottieni lo stato post-inserimento
    	 ITable actual = tester.getConnection()
    	 .createDataSet().getTable("PRODOTTO");
    	 // Assert di DBUnit (debole all'ordinamento)
    	 String[] ignoreCol = new String[1];
 		ignoreCol[0] = "foto";
 		Assertion.assertEqualsIgnoreCols(new SortedTable(expected), new SortedTable(actual), ignoreCol);
  
    }

    @Test
    public void doDeleteTrue() throws Exception {
    	 // Prepara stato atteso sottoforma di ITable
    	 ITable expected = new FlatXmlDataSetBuilder()
    	 .build(ProdottoDAOTest.class.getClassLoader()
    	 .getResourceAsStream("test/resources/doDeleteProdottoTest.xml"))
    	 .getTable("Prodotto");
    	 
    	 Prodotto prodotto2 = new Prodotto();
    	 prodotto2.setId(883);
    	 prodottoDAO.doDelete(prodotto2); 
    	 
    	 // Ottieni lo stato post-inserimento
    	 ITable actual = tester.getConnection()
    	 .createDataSet().getTable("PRODOTTO");
    	 // Assert di DBUnit (debole all'ordinamento)
    	 String[] ignoreCol = new String[1];
  		 ignoreCol[0] = "foto";
  		 Assertion.assertEqualsIgnoreCols(new SortedTable(expected), new SortedTable(actual), ignoreCol);
   
    }    

    @Test
    public void doRetrieveAllByCategoria()  throws SQLException {
    	Collection<Prodotto> expected = new LinkedList<Prodotto>();
    	Prodotto bean = new Prodotto();
    	bean.setId(884);
    	bean.setNome("prodotto2");
    	bean.setMarchio("marchio2");
    	bean.setProduttore("produttore2");
    	bean.setFormato("formato2");
    	bean.setDescrizione("descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2");
    	bean.setDisponibilita(50);
    	bean.setPrezzo(new BigDecimal("4"));
    	expected.add(bean);
    	Collection<Prodotto> actual = prodottoDAO.doRetrieveAllByCategoria(1);
    	assertEquals(1,actual.size());
    	assertArrayEquals(expected.toArray(),actual.toArray());
    }
    
    @Test
    public void doRicercaTrue()  throws SQLException {
    	String nome = "prodotto";
    	
    	Collection<Prodotto> expected = new LinkedList<Prodotto>();
    	Prodotto bean = new Prodotto();
    	bean.setId(883);
    	bean.setNome("prodotto1");
    	bean.setMarchio("marchio1");
    	bean.setProduttore("produttore1");
    	bean.setFormato("formato1");
    	bean.setDescrizione("descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1");
    	bean.setDisponibilita(100);
    	bean.setPrezzo(new BigDecimal("4.35"));
    	bean.setCategoria("mammaebambino");
    	expected.add(bean);
    	
    	Prodotto bean2 = new Prodotto();
    	bean2.setId(884);
    	bean2.setNome("prodotto2");
    	bean2.setMarchio("marchio2");
    	bean2.setProduttore("produttore2");
    	bean2.setFormato("formato2");
    	bean2.setDescrizione("descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2 descrizione2");
    	bean2.setDisponibilita(50);
    	bean2.setPrezzo(new BigDecimal("4"));
    	bean2.setCategoria("protezione antivirale");
    	expected.add(bean2);
    	
    	Prodotto bean3 = new Prodotto();
    	bean3.setId(891);
    	bean3.setNome("prodotto10");
    	bean3.setMarchio("marchio10");
    	bean3.setProduttore("produttore10");
    	bean3.setFormato("formato10");
    	bean3.setDescrizione("descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10");
    	bean3.setDisponibilita(40);
    	bean3.setPrezzo(new BigDecimal(7));
    	expected.add(bean3);
    	
    	Collection<Prodotto> actual = prodottoDAO.doRicerca(nome,"");
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRicercaFalse()  throws SQLException {
    	
    	String nome = "articolo2";
    	
    	Collection<Prodotto> expected = new LinkedList<Prodotto>();
    	
    	Collection<Prodotto> actual = prodottoDAO.doRicerca(nome,"");
    	assertEquals(expected, actual);
    	
    }
    
    @Test
    public void doRetrieveSvincolati()  throws SQLException {
    	
    	Collection<Prodotto> expected = new LinkedList<Prodotto>();
    	Prodotto bean = new Prodotto();
    	bean.setId(891);
    	bean.setNome("prodotto10");
    	bean.setMarchio("marchio10");
    	bean.setProduttore("produttore10");
    	bean.setFormato("formato10");
    	bean.setDescrizione("descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10 descrizione10");
    	bean.setDisponibilita(40);
    	bean.setPrezzo(new BigDecimal(7));
    	expected.add(bean);
    	Collection<Prodotto> actual = prodottoDAO.doRetrieveSvincolati();
    	assertEquals(1,actual.size());
    	assertArrayEquals(expected.toArray(),actual.toArray());
    }
    
}
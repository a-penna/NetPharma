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

import main.bean.DatiSpedizione;
import main.model.DatiSpedizioneDAO;


public class DatiSpedizioneDAOTest extends DataSourceBasedDBTestCase {

	private DatiSpedizioneDAO dati;
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
        dati = new DatiSpedizioneDAO(dataSource);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // tearDown del padre serve a chiamare il nostro getTearDownOperation
        super.tearDown();
    }
    
    @Test
    public void doRetriveIdByEmailTrue()  throws SQLException {
    	String email = "m@rossi.com";
    	//<DATI_SPEDIZIONE id='1' nome_ricevente='Mario' cognome_ricevente='rossi' email='m@rossi.com' cellulare='3333' ncivico='3' via='vvv' city='a' paese='a' provincia='b' cap='80000' cliente='m@rossi.com'/>
    	DatiSpedizione bean = new DatiSpedizione();
    	bean.setId(1);
    	bean.setNomeRicevente("Mario");
    	bean.setCognomeRicevente("rossi");
    	bean.setEmail("m@rossi.com");
    	bean.setCellulare("3333");
    	bean.setNcivico(3);
    	bean.setVia("vvv");
    	bean.setCitta("a");
    	bean.setPaese("a");
    	bean.setProvincia("b");
    	bean.setCAP("80000");
    	bean.setCliente("m@rossi.com");
    	DatiSpedizione actual = dati.doRetriveIdByEmail(email);
    	assertEquals(bean,actual);
    }
    
    @Test
    public void doRetriveIdByEmailFalse()  throws SQLException {
    	String email = "s@verdi.com";
    	//<DATI_SPEDIZIONE id='1' nome_ricevente='Mario' cognome_ricevente='rossi' email='m@rossi.com' cellulare='3333' ncivico='3' via='vvv' city='a' paese='a' provincia='b' cap='80000' cliente='m@rossi.com'/>
    	DatiSpedizione bean = new DatiSpedizione();
    	bean.setId(1);
    	bean.setNomeRicevente("Mario");
    	bean.setCognomeRicevente("rossi");
    	bean.setEmail("m@rossi.com");
    	bean.setCellulare("3333");
    	bean.setNcivico(3);
    	bean.setVia("vvv");
    	bean.setCitta("a");
    	bean.setPaese("a");
    	bean.setProvincia("b");
    	bean.setCAP("80000");
    	bean.setCliente("m@rossi.com");
    	DatiSpedizione actual = dati.doRetriveIdByEmail(email);
    	assertEquals(bean,actual);
    }
    
}
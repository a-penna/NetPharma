package main.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import main.bean.Ordine;
import main.utils.Utility;


public class OrdineDAO {

	private DataSource ds = null;
	
	public OrdineDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public Ordine doRetrieveByKey(String key) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM ordine WHERE id = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setString(1, key);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				
				Ordine ordine = new Ordine();
				ordine.setNomeRicevente(rs.getString("nome_ricevente"));
				ordine.setCognomeRicevente(rs.getString("cognome_ricevente"));
				ordine.setEmail(rs.getString("email"));
				ordine.setCellulare(rs.getString("cellulare"));
				ordine.setCitta(rs.getString("city"));
				ordine.setNcivico(rs.getInt("ncivico"));
				ordine.setVia(rs.getString("via"));
				ordine.setPaese(rs.getString("paese"));
				ordine.setProvincia(rs.getString("provincia"));
				ordine.setCAP(rs.getString("cap"));
				ordine.setCliente(rs.getString("cliente"));
				ordine.setData_ordine(rs.getDate("data_ordine"));
				ordine.setStato(rs.getString("Stato"));
				ordine.setId(rs.getString("id"));
				ordine.setPrezzo(rs.getBigDecimal("prezzo"));
				ordine.setData_arrivo(rs.getDate("data_arrivo"));
				
				return ordine;
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return null;
	}

	public Collection<Ordine> doRetrieveAll(String order) throws SQLException {
		Collection<Ordine> lista = new LinkedList<Ordine>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM ordine";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
			Ordine ordine = new Ordine();
			ordine.setNomeRicevente(rs.getString("nome_ricevente"));
			ordine.setCognomeRicevente(rs.getString("cognome_ricevente"));
			ordine.setEmail(rs.getString("email"));
			ordine.setCellulare(rs.getString("cellulare"));
			ordine.setCitta(rs.getString("city"));
			ordine.setNcivico(rs.getInt("ncivico"));
			ordine.setVia(rs.getString("via"));
			ordine.setPaese(rs.getString("paese"));
			ordine.setProvincia(rs.getString("provincia"));
			ordine.setCAP(rs.getString("cap"));
			ordine.setCliente(rs.getString("cliente"));
			ordine.setData_ordine(rs.getDate("data_ordine"));
			ordine.setStato(rs.getString("Stato"));
			ordine.setId(rs.getString("id"));
			ordine.setPrezzo(rs.getBigDecimal("prezzo"));
			ordine.setData_arrivo(rs.getDate("data_arrivo"));
			lista.add(ordine);
			
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return lista;
	}

	public Boolean doSaveCheck(Ordine bean) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		

		String insertSQL = "INSERT INTO Ordine(id,nome_ricevente,cognome_ricevente,email,cellulare,ncivico,via,city,paese,provincia,cap,data_ordine,prezzo,stato,cliente) "
						+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, bean.getId());
			preparedStatement.setString(2, bean.getNomeRicevente());
			preparedStatement.setString(3, bean.getCognomeRicevente());
			preparedStatement.setString(4, bean.getEmail());
			preparedStatement.setString(5, bean.getCellulare());
			preparedStatement.setInt(6, bean.getNcivico());
			preparedStatement.setString(7, bean.getVia());
			preparedStatement.setString(8, bean.getCitta());
			preparedStatement.setString(9, bean.getPaese());
			preparedStatement.setString(10, bean.getProvincia());
			preparedStatement.setString(11, bean.getCAP());
			preparedStatement.setDate(12, bean.getData_ordine());
			preparedStatement.setBigDecimal(13, bean.getPrezzo());
			preparedStatement.setString(14, bean.getStato());
			preparedStatement.setString(15, bean.getCliente());
	
			preparedStatement.executeUpdate();
			
			connection.commit();
		} 
		catch(SQLException e) {
			Utility.printSQLException(e);
			return false;
		}
		
		
		return true;
	}
	
	public Boolean doUpdateStatus(Ordine bean, long millis) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		if(millis>=Utility.MILLIS_IN_DAY * 2 && millis <= Utility.MILLIS_IN_DAY * 10) {
		Date data = new Date(System.currentTimeMillis()+millis); 
		
		String insertSQL = "UPDATE Ordine SET stato='Si', data_arrivo = ? WHERE id = ?";
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setDate(1, data);
			preparedStatement.setString(2, bean.getId());
			preparedStatement.executeUpdate();
			connection.commit();
			
		} catch(SQLException e) {
			Utility.printSQLException(e);
			return false;
				}
		
		return true;
	}
	return false;
	}
	
	public Collection<Ordine> doRetrieveAllDaSpedire() throws SQLException {
		Collection<Ordine> lista = new LinkedList<Ordine>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM ordine WHERE stato='No'";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
			Ordine ordine = new Ordine();
			ordine.setNomeRicevente(rs.getString("nome_ricevente"));
			ordine.setCognomeRicevente(rs.getString("cognome_ricevente"));
			ordine.setEmail(rs.getString("email"));
			ordine.setCellulare(rs.getString("cellulare"));
			ordine.setCitta(rs.getString("city"));
			ordine.setNcivico(rs.getInt("ncivico"));
			ordine.setVia(rs.getString("via"));
			ordine.setPaese(rs.getString("paese"));
			ordine.setProvincia(rs.getString("provincia"));
			ordine.setCAP(rs.getString("cap"));
			ordine.setCliente(rs.getString("cliente"));
			ordine.setData_ordine(rs.getDate("data_ordine"));
			ordine.setStato(rs.getString("Stato"));
			ordine.setId(rs.getString("id"));
			ordine.setPrezzo(rs.getBigDecimal("prezzo"));
			ordine.setData_arrivo(rs.getDate("data_arrivo"));
			lista.add(ordine);
			
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return lista;
	}
	
}

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;


import javax.sql.DataSource;

import bean.DatiSpedizione;
import utils.Utility;

public class DatiSpedizioneDAO {

private DataSource ds;
	
	public DatiSpedizioneDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public DatiSpedizione doRetrieveByKey(String id) throws SQLException {
		return null;
		
	}
	
	
	public Collection<DatiSpedizione> doRetrieveAll(String order) throws SQLException {
		return null;
		
	}
	
	
	public DatiSpedizione doRetriveIdByEmail(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT id FROM DatiSpedizione WHERE email=?";
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				DatiSpedizione dati = (DatiSpedizione) rs.getObject(0);
				return dati;
			}
		}  catch(SQLException e) {
			Utility.printSQLException(e);
		}
		return null;
	}

	public void doSave(DatiSpedizione dati) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		

		String insertSQL = "INSERT INTO DatiSpedizione(nome_ricevente,cognome_ricevente,email,cellulare,ncivico,via,city,paese,provincia,cap,cliente) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, dati.getNomeRicevente());
			preparedStatement.setString(2, dati.getCognomeRicevente());
			preparedStatement.setString(3, dati.getEmail());
			preparedStatement.setString(4, dati.getCellulare());
			preparedStatement.setInt(5, dati.getNcivico());
			preparedStatement.setString(6, dati.getVia());
			preparedStatement.setString(7, dati.getCitta());
			preparedStatement.setString(8, dati.getPaese());
			preparedStatement.setString(9, dati.getProvincia());
			preparedStatement.setString(10, dati.getCAP());
			preparedStatement.setString(11, dati.getCliente());
			preparedStatement.executeUpdate();
			

			
			
				
			

			connection.commit();
		} 
			 catch(SQLException e) {
				Utility.printSQLException(e);
					}
		
		
	}


	
	public void doUpdate(DatiSpedizione dati) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void doDelete(DatiSpedizione dati) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	
	





	
}

package main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import main.bean.UtenteRegistrato;

public class UtenteRegistratoDAO{

	private DataSource ds = null;
	
	public UtenteRegistratoDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public UtenteRegistrato doRetrieveByAccount(int accountID) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UtenteRegistrato bean = new UtenteRegistrato();
		
		String selectSQL = "SELECT * FROM utente_registrato WHERE account = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, accountID);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setGenere(rs.getString("genere"));
				bean.setEmail(rs.getString("email"));
				bean.setNascita(rs.getDate("nascita"));
				bean.setAccount(rs.getInt("account"));
			}
			
			rs.close();
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
		return bean;
	}

	
	public boolean checkEmail(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT * FROM Utente_registrato "
						 + "WHERE email = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				return true;
			}
			
			rs.close();
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
		return false;
	}	
}

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import bean.UtenteRegistrato;
import utils.Utility;

public class UtenteRegistratoDAO{

	private DataSource ds = null;
	
	public UtenteRegistratoDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public UtenteRegistrato doRetrieveByKey(String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UtenteRegistrato bean = new UtenteRegistrato();
		
		String selectSQL = "SELECT * FROM utente_registrato WHERE username = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, key);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
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

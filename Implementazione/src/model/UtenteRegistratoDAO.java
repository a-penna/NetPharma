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

public class UtenteRegistratoDAO implements Model<UtenteRegistrato>{

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

	@Override
	public Collection<UtenteRegistrato> doRetrieveAll(String order) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doSave(UtenteRegistrato bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doUpdate(UtenteRegistrato bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(UtenteRegistrato bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}

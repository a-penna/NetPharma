package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.sql.DataSource;


import bean.Account;
import bean.Ruoli;
import bean.UtenteRegistrato;
import utils.Utility;

public class AccountDAO {

	private DataSource ds = null;
	
	public AccountDAO(DataSource ds) {
		this.ds = ds;
	}
	
	
	public Account authenticate(String username, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Account bean = null;
		
		String selectSQL = "SELECT * FROM Account "
						 + "WHERE username = ? AND password = MD5(?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				bean = new Account(rs.getInt("id"),rs.getString("username"), rs.getString("password"));
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

	
	public boolean checkUsername(String username) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT * FROM Account "
						 + "WHERE username = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			
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
	
	
	public boolean register(Account account, UtenteRegistrato user, Ruoli r) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		
		try {
			connection = ds.getConnection();	
			connection.setAutoCommit(false);		
			
			String insertSQL = "INSERT INTO Account(username,password) "
							 + "VALUES (?,MD5(?))";

			preparedStatement1 = connection.prepareStatement(insertSQL);
			preparedStatement1.setString(1, account.getUsername());
			preparedStatement1.setString(2, account.getPassword());
         	if (preparedStatement1.executeUpdate() != 1) {
				try {
					connection.rollback();
				} catch(SQLException e) {
					Utility.printSQLException(e);
				}
				return false;
			}
            
            insertSQL = "INSERT INTO Utente_registrato(genere,nome,cognome,email,nascita,account) "
            		  + "VALUES (?,?,?,?,?,(SELECT id FROM account WHERE username=?))";
            
			preparedStatement2 = connection.prepareStatement(insertSQL);
            preparedStatement2.setString(1, user.getGenere());
            preparedStatement2.setString(2, user.getNome());
            preparedStatement2.setString(3, user.getCognome());
            preparedStatement2.setString(4, user.getEmail());
            preparedStatement2.setDate(5, user.getNascita());
            preparedStatement2.setString(6, account.getUsername());
            if (preparedStatement2.executeUpdate() != 1) {
            	try {
    				connection.rollback();
    			} catch (SQLException e) {
    				Utility.printSQLException(e);
    			}
            	return false;
			}
            
            Collection<Ruoli.Ruolo> ruoli = r.getRuoli();
            Iterator<Ruoli.Ruolo> it = ruoli.iterator();
            
            while (it.hasNext()) {
            	
	            insertSQL = "INSERT INTO ruoli(account, ruolo) "
	          		   	  + "VALUES ((SELECT id FROM account WHERE username=?),?)";
	                
	            preparedStatement3 = connection.prepareStatement(insertSQL);
	            preparedStatement3.setString(1, account.getUsername());
	            preparedStatement3.setString(2, Ruoli.roleToString(it.next()));
	            
	            if (preparedStatement3.executeUpdate() != 1) {
	            	try {
	            		connection.rollback();
	            	} catch (SQLException e) {
	            		Utility.printSQLException(e);
	            	} finally {
	            		preparedStatement3.close();
	            	}
	            	return false;
	            }
	            preparedStatement3.close();
            }

            connection.commit();
		} finally {
			try {
				if (preparedStatement3 != null)
					preparedStatement3.close();
				if (preparedStatement2 != null)
					preparedStatement2.close();
				if (preparedStatement1 != null)
					preparedStatement1.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		} 
		return true;
		
	}
}

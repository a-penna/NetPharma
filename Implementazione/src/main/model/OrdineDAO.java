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


public class OrdineDAO implements Model<Ordine>{

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
			
			preparedStatement.setInt(1, Integer.parseInt(key));
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				
			Ordine ordine = new Ordine();
			ordine.setCliente(rs.getString("cliente"));
			ordine.setData_ordine(rs.getDate("data_ordine"));
			ordine.setDati_spedizione(rs.getInt("dati_spedizione"));
			ordine.setStato(rs.getString("Stato"));
			ordine.setId(rs.getInt("id"));
			ordine.setPrezzo(rs.getFloat("prezzo"));
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

	@Override
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
			ordine.setCliente(rs.getString("cliente"));
			ordine.setData_ordine(rs.getDate("data_ordine"));
			ordine.setDati_spedizione(rs.getInt("dati_spedizione"));
			ordine.setStato(rs.getString("Stato"));
			ordine.setId(rs.getInt("id"));
			ordine.setPrezzo(rs.getFloat("prezzo"));
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

	@Override
	public void doSave(Ordine bean) throws SQLException {
		
	}

	
	

	@Override
	public void doUpdate(Ordine bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(Ordine bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public Boolean doSaveCheck(Ordine bean, int id) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		

		String insertSQL = "INSERT INTO Ordine(data_ordine,id,prezzo,stato,cliente,dati_spedizione) VALUES (?,?,?,?,?,?)";
		

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setDate(1, bean.getData_ordine());
			
			preparedStatement.setInt(2,bean.getId() );
			preparedStatement.setFloat(3,bean.getPrezzo());
			preparedStatement.setString(4, bean.getStato());
			preparedStatement.setString(5,bean.getCliente());
			preparedStatement.setInt(6, id);
	
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
		if(millis>172800000 && millis < 864000000) {
		Date data = new Date(System.currentTimeMillis()+millis); 
		
		String insertSQL = "UPDATE Ordine SET stato='Si', data_arrivo = ? WHERE id = ?";
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setDate(1, data);
			preparedStatement.setInt(2, bean.getId());
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
	
		
	
}

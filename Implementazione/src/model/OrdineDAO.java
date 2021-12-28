package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import bean.Ordine;


public class OrdineDAO implements Model<Ordine>{

	private DataSource ds = null;
	
	public OrdineDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public Ordine doRetrieveByKey(String key) throws SQLException {
		
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
		// TODO Auto-generated method stub
		
	}

	
	

	@Override
	public void doUpdate(Ordine bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(Ordine bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}

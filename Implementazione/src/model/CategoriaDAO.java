package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import bean.Categoria;
import utils.Utility;

public class CategoriaDAO {
    
	private DataSource ds = null;

	public CategoriaDAO(DataSource ds) {
		this.ds = ds;
	}


	public Categoria doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Categoria bean = new Categoria();

		String selectSQL = "SELECT * FROM categoria WHERE id = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setId(rs.getInt(id));
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

		return bean;
	}
	
	public Categoria doRetrieveByUsername(String nome) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Categoria bean = new Categoria();

		String selectSQL = "SELECT * FROM categoria WHERE nome = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, nome);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setNome(rs.getString("nome"));
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

		return bean;
	}

	private boolean checkOrder(String order) {
		return order != null 
			   && !order.equals("") 
			   && (order.equals("nome"));
	}
	
	public Collection<Categoria> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Categoria> categorie = new LinkedList<Categoria>();

		String selectSQL = "SELECT * FROM categoria";

		if (checkOrder(order)) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Categoria bean = new Categoria();

				bean.setNome(rs.getString("nome"));

				categorie.add(bean);
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

		return categorie;

	}

	public void doSave(Categoria categoria) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO Categoria (nome) VALUES (?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, categoria.getNome());
	

            int result = preparedStatement.executeUpdate();
            if (result != 1) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					Utility.printSQLException(e);
				}
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
	}
	

	public void doUpdate(Categoria categoria) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void doDelete(Categoria bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		
		String deleteSQL = "DELETE FROM Categoria "
						+ "WHERE nome=?";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			String categoria = bean.getNome();
	
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, categoria);
			
			int result = preparedStatement.executeUpdate();
			if (result != 1) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					Utility.printSQLException(e);
				}
			}
			
			connection.commit();
		} 
		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

}
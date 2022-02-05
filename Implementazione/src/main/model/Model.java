package main.model;

import java.sql.SQLException;
import java.util.Collection;

public interface Model<T> {
	
	public T doRetrieveByKey(String key) throws SQLException;
	
	public Collection<T> doRetrieveAll(String order) throws SQLException;
	
	public void doSave(T bean) throws SQLException;
	
	public void doUpdate(T bean) throws SQLException;
	
	public void doDelete(T bean) throws SQLException;

}

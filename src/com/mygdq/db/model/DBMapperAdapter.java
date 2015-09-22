package com.mygdq.db.model;

/**
 * General Input/Output mapper.
 * Gives access to the database object, makes child classes look cleaner.
 * Autocloseable allows for try(with) statements.
 * 
 * @author Michael
 */
public abstract class DBMapperAdapter implements AutoCloseable {
	protected DBDataSourceAdapter database;
	
	public DBMapperAdapter(DBDataSourceAdapter database) {
		this.database = database;
		this.database.open();
	}

	@Override public void close() throws Exception {
		this.database.close();
	}
}

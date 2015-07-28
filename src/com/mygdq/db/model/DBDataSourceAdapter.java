package com.mygdq.db.model;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * General Data Source adapter.
 * Gives access to the database and open helper objects, makes child classes look cleaner.
 * 
 * @author Michael
 */
public abstract class DBDataSourceAdapter {
	protected SQLiteDatabase database;
	protected SQLiteOpenHelper dbHelper;

	public DBDataSourceAdapter(SQLiteOpenHelper helper) {
		dbHelper = helper;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
}
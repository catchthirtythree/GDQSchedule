package com.mygdq.db.model.run;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.mygdq.db.model.DBDataSourceAdapter;
import com.mygdq.db.util.DBUtil;

/**
 * @author Michael
 */
public class RunDataSource extends DBDataSourceAdapter {
	public RunDataSource(Context context) {
		super(new RunHelper(context));
	}
	
	public void createTable() throws SQLException {
		dbHelper.onCreate(database);
	}

	final String TRUNCATE_TABLE = "DELETE FROM " + RunHelper.TABLE_NAME + ";";
	final String DROP_TABLE = "DROP TABLE " + RunHelper.TABLE_NAME + ";";
	public void dropTable() {
		database.execSQL(TRUNCATE_TABLE);
		database.execSQL(DROP_TABLE);
	}
	
	final String FIND_ALL = "SELECT " + RunHelper.COLUMNS + " FROM " + RunHelper.TABLE_NAME + ";";
	public Cursor findAll() throws SQLException {
		return database.rawQuery(FIND_ALL, null);
	}
	
	/* TODO */
	final String FIND_BY_STRING = "SELECT " + RunHelper.COLUMNS + " FROM " + RunHelper.TABLE_NAME + " where ?=? or ?=? ... ;";
	public Cursor findByString(String search) {
		return database.rawQuery(FIND_BY_STRING, new String[] { search });
	}
	
	public long insert(Date date, String game, String runners, String console, String estimate, 
			String setup, String comments, String commentators, String prizes, String channels) {
		ContentValues values = new ContentValues();
		values.put(RunHelper.COLUMN_DATE, DBUtil.persistDate(date));
		values.put(RunHelper.COLUMN_GAME, game);
		values.put(RunHelper.COLUMN_RUNNERS, runners);
		values.put(RunHelper.COLUMN_CONSOLE, console);
		values.put(RunHelper.COLUMN_ESTIMATE, estimate);
		values.put(RunHelper.COLUMN_SETUP, setup);
		values.put(RunHelper.COLUMN_COMMENTS, comments);
		values.put(RunHelper.COLUMN_COMMENTATORS, commentators);
		values.put(RunHelper.COLUMN_PRIZES, prizes);
		values.put(RunHelper.COLUMN_CHANNELS, channels);
		return database.insert(RunHelper.TABLE_NAME, null, values);
	}
	
	public void truncateTable() {
		database.execSQL(TRUNCATE_TABLE);
	}
	
	final String UPDATE = "UPDATE " + RunHelper.TABLE_NAME + " SET date=?, game=?, runners=?, console=?, estimate=?, setup=?, comments=?, commentators=?, prizes=?, channels=? WHERE id=?;";
	public int update(long id, Date date, String game, String runners, String console, String estimate, 
			String setup, String comments, String commentators, String prizes, String channels) {
		SQLiteStatement stmt = database.compileStatement(UPDATE);
		stmt.bindLong(1, DBUtil.persistDate(date));
		stmt.bindString(2, game);
		stmt.bindString(3, runners);
		stmt.bindString(4, console);
		stmt.bindString(5, estimate);
		stmt.bindString(6, setup);
		stmt.bindString(7, comments);
		stmt.bindString(8, commentators);
		stmt.bindString(9, prizes);
		stmt.bindString(10, channels);
		stmt.bindLong(11, id);
		
		int count = stmt.executeUpdateDelete();
		stmt.close();
		
		return count;
	}
}
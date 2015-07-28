package com.mygdq.db.model.gdq;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.mygdq.db.model.DBDataSourceAdapter;
import com.mygdq.db.util.DBUtil;

/**
 * @author Michael
 */
public class GDQDataSource extends DBDataSourceAdapter {
	public GDQDataSource(Context context) {
		super(new GDQHelper(context));
	}

	final String FIND = "SELECT " + GDQHelper.COLUMNS + " FROM " + GDQHelper.TABLE_NAME + ";";
	public Cursor find() {
		return database.rawQuery(FIND, null);
	}
	
	public long insert(Date updated) {
		ContentValues values = new ContentValues();
		values.put(GDQHelper.COLUMN_UPDATE, DBUtil.persistDate(updated).toString());
		return database.insert(GDQHelper.TABLE_NAME, null, values);
	}
	
	final String UPDATE = "UPDATE " + GDQHelper.TABLE_NAME + " SET updated=? WHERE id=?;";
	public int update(long id, Date updated) {
		SQLiteStatement stmt = database.compileStatement(UPDATE);
		stmt.bindLong(1, DBUtil.persistDate(updated));
		stmt.bindLong(2, id);
		
		int count = stmt.executeUpdateDelete();
		stmt.close();
		
		return count;
	}
}
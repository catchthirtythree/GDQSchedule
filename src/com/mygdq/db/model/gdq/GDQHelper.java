package com.mygdq.db.model.gdq;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Michael
 */
public class GDQHelper extends SQLiteOpenHelper {
	static final String TABLE_NAME = "gdq";
	static final String COLUMN_ID = "id";
	static final String COLUMN_UPDATE = "updated";
	static final String COLUMNS = "id, updated";

	static final String DATABASE_NAME = TABLE_NAME + ".db";
	static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table if not exists " + TABLE_NAME + "(" 
			+ COLUMN_ID 	+ " integer primary key autoincrement, " 
			+ COLUMN_UPDATE + " timestamp not null);";

	public GDQHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
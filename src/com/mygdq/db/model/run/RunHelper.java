package com.mygdq.db.model.run;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Michael
 */
public class RunHelper extends SQLiteOpenHelper {
	static final String TABLE_NAME = "runs";
	static final String COLUMN_ID = "id";
	static final String COLUMN_DATE = "date";
	static final String COLUMN_GAME = "game";
	static final String COLUMN_RUNNERS = "runners";
	static final String COLUMN_CONSOLE = "console";
	static final String COLUMN_ESTIMATE = "estimate";
	static final String COLUMN_SETUP = "setup";
	static final String COLUMN_COMMENTS = "comments";
	static final String COLUMN_COMMENTATORS = "commentators";
	static final String COLUMN_PRIZES = "prizes";
	static final String COLUMN_CHANNELS = "channels";
	static final String COLUMNS = "id, date, game, runners, console, estimate, setup, comments, commentators, prizes, channels";

	static final String DATABASE_NAME = TABLE_NAME + ".db";
	static final int DATABASE_VERSION = 1;

	static final String DATABASE_CREATE = "create table " + TABLE_NAME + "(" 
			+ COLUMN_ID 	+ " integer primary key autoincrement, " 
			+ COLUMN_DATE + " timestamp not null, "
			+ COLUMN_GAME + " char(255) not null, "
			+ COLUMN_RUNNERS + " char(255) not null, "
			+ COLUMN_CONSOLE + " char(255) not null, "
			+ COLUMN_ESTIMATE + " char(255) not null, "
			+ COLUMN_SETUP + " char(255) not null, "
			+ COLUMN_COMMENTS + " char(255), "
			+ COLUMN_COMMENTATORS + " char(255), "
			+ COLUMN_PRIZES + " char(255), "
			+ COLUMN_CHANNELS + " char(255));";

	public RunHelper(Context context) {
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
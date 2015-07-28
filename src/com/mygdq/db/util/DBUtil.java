package com.mygdq.db.util;

import java.util.Date;

import android.database.Cursor;

/**
 * Database utility class.
 * 
 * @author Michael
 */
public class DBUtil {
	/**
	 * Converts a timestamp to a date.
	 * @param cursor
	 * @param index
	 * @return
	 */
	public static Date loadDate(Cursor cursor, int index) {
	    if (cursor.isNull(index)) {
	        return null;
	    } 
	    
	    return new Date(cursor.getLong(index));
	}
	
	/**
	 * Converts a date to a timestamp.
	 * @param date
	 * @return
	 */
	public static Long persistDate(Date date) {
	    if (date == null) {
	        return null;
	    }
	    
	    return date.getTime();
	}
}
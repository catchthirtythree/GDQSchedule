package com.mygdq.db.model.gdq;

import android.content.Context;
import android.database.Cursor;

import com.mygdq.db.model.DBMapperAdapter;
import com.mygdq.db.util.DBUtil;

/**
 * Input/Output mapper for the GQD class.
 * 
 * @author Michael
 */
public class GDQMapper extends DBMapperAdapter {
	public GDQMapper(Context context) {
		super(new GDQDataSource(context));
	}
	
	/**
	 * Finds the only row in the table.
	 * @return
	 */
	public GDQ find() {
		Cursor cursor = ((GDQDataSource) database).find();
		
		// If the cursor has at least one row in it, return it, otherwise return empty gdq.
		return (cursor != null && cursor.getCount() > 0) ? new GDQ(cursor.getLong(0), DBUtil.loadDate(cursor, 1)) : new GDQ();
	}
	
	/**
	 * Inserts a row.
	 * @param gdq
	 * @return
	 */
	public long insert(GDQ gdq) {
		return ((GDQDataSource) database).insert(gdq.getDate());
	}
	
	/**
	 * Updates a row.
	 * @param gdq
	 * @return
	 */
	public int update(GDQ gdq) {
		return ((GDQDataSource) database).update(gdq.getId(), gdq.getDate());
	}
}
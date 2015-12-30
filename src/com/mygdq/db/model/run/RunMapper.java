package com.mygdq.db.model.run;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.mygdq.db.model.DBMapperAdapter;
import com.mygdq.db.util.DBUtil;

/**
 * Input/Output mapper for the Run class.
 * 
 * @author Michael
 */
public class RunMapper extends DBMapperAdapter {
	public RunMapper(Context context) {
		super(new RunDataSource(context));
	}
	
	/**
	 * Creates table.
	 */
	public void createTable() {
		((RunDataSource) database).createTable();
	}
	
	/**
	 * Drops table.
	 */
	public void dropTable() {
		((RunDataSource) database).dropTable();
	}
	
	/**
	 * Find all runs.
	 * @return
	 */
	public Run[] findAll() {
		Run[] runs = null;
		
		try (Cursor cursor = ((RunDataSource) database).findAll()) {
			cursor.moveToFirst();
			runs = new Run[cursor.getCount()];
			
			int index = 0;
			while (!cursor.isAfterLast()) {
				Run run = new Run();
				run.setId(cursor.getLong(0));
				run.setDate(DBUtil.loadDate(cursor, 1));
				run.setGame(cursor.getString(2));
				run.setRunners(cursor.getString(3));
				run.setEstimate(cursor.getString(4));
				run.setCategory(cursor.getString(5));
				run.setSetup(cursor.getString(6));
				run.setDescription(cursor.getString(7));
				cursor.moveToNext();
				
				runs[index++] = run;
			}
		} catch (Exception e) {
			runs = new Run[0];
		}
		
		return runs;
	}
	
	/**
	 * Finds runs that match the string.
	 * @param search
	 * @return
	 */
	public List<Run> findByString(String search) {
		List<Run> runs = new ArrayList<Run>();
		
		try (Cursor cursor = ((RunDataSource) database).findByString(search)) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Run run = new Run();
				run.setId(cursor.getLong(0));
				run.setDate(DBUtil.loadDate(cursor, 1));
				run.setGame(cursor.getString(2));
				run.setRunners(cursor.getString(3));
				run.setEstimate(cursor.getString(4));
				run.setCategory(cursor.getString(5));
				run.setSetup(cursor.getString(6));
				run.setDescription(cursor.getString(7));
				
				runs.add(run);
			}
		}
		
		return runs;
	}
	
	/**
	 * Inserts a run.
	 * @param run
	 * @return
	 */
	public long insert(Run run) {
		return ((RunDataSource) database).insert(run.getDate(), run.getGame(), run.getRunners(), 
				run.getEstimate(), run.getCategory(), run.getSetup(),
				run.getDescription());
	}
	
	/**
	 * Truncates the table.
	 */
	public void truncateTable() {
		((RunDataSource) database).truncateTable();
	}
	
	/**
	 * Updates a run.
	 */
	public int update(Run run) {
		return ((RunDataSource) database).update(run.getId(), run.getDate(), run.getGame(), 
				run.getRunners(), run.getEstimate(), run.getCategory(), run.getSetup(),
				run.getDescription());
	}
}
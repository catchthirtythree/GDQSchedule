package com.mygdq.db.model.gdq;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Michael
 */
public class GDQ implements Serializable {
	private static final long serialVersionUID = 7817003827094620864L;
	
	private long id;
	private Date update;
	
	public GDQ() {}
	
	public GDQ(long id, Date update) {
		this.id = id;
		this.update = update;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	@Override public String toString() {
		return update.toString();
	}
}
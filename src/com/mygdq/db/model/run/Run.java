package com.mygdq.db.model.run;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Michael
 */
public class Run {
	/**
	 * DateTime Formatter to parse dates coming from HTML.
	 */
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
	{
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	private long id;
	private Date date;
	private String game, runners, estimate, category, setup, description;

	/**
	 * Default empty constructor.
	 */
	public Run() {
		this.id = -1;
		this.date = new Date();
		this.game = "";
		this.runners = "";
		this.estimate = "";
		this.category = "";
		this.setup = "";
		this.description = "";
	}

	/**
	 * Format a table row from HTML.
	 * @param cells
	 * @throws ParseException
	 */
	public Run(Elements cells) throws ParseException {
		setDate(cells.get(0));
		setGame(cells.get(1));
		setRunners(cells.get(2));
		setEstimate(cells.get(3));
		setCategory(cells.get(4));
		setSetup(cells.get(5));
		setDescription(cells.get(6));
	}

	/**
	 * Update run with new run.
	 * @param run
	 */
	public void setRun(Run run) {
		this.date = run.getDate();
		this.game = run.getGame();
		this.runners = run.getRunners();
		this.estimate = run.getEstimate();
		this.category = run.getCategory();
		this.setup = run.getSetup();
		this.description = run.getDescription();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Element date) throws ParseException {
		this.date = format.parse(date.text());
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getGame() {
		return game;
	}

	public void setGame(Element game) {
		this.game = game.text();
	}
	
	public void setGame(String game) {
		this.game = game;
	}

	public String getRunners() {
		return runners;
	}

	public void setRunners(Element runners) {
		this.runners = runners.text();
	}
	
	public void setRunners(String runners) {
		this.runners = runners;
	}

	public String getEstimate() {
		return estimate;
	}

	public void setEstimate(Element estimate) {
		this.estimate = estimate.text().isEmpty() ? "0:00:00" : estimate.text();
	}
	
	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}

	public String getCategory() {
		return category;
	}
	
	public void setCategory(Element category) {
		this.category = category.text();
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

	public String getSetup() {
		return setup;
	}

	public void setSetup(Element setup) {
		this.setup = setup.text().isEmpty() ? "0:00:00" : setup.text();
	}
	
	public void setSetup(String setup) {
		this.setup = setup;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(Element description) {
		this.description = description.text();
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return date + ", " + game + ", " + runners + ", " + estimate + ", " + category + ", " + setup + ", " + description;
	}
}
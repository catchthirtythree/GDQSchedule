package com.mygdq.db.model.run;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.jsoup.select.Elements;

import com.mygdq.util.Util;

/**
 * @author Michael
 */
public class Run {
	/**
	 * DateTime Formatter to parse dates coming from HTML.
	 */
	private final SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy H:mm:ss");
	
	private long id;
	private Date date;
	private String game, runners, console, estimate, setup, comments, commentators, prizes, channels;

	/**
	 * Default empty constructor.
	 */
	public Run() {
		this.id = -1;
		this.date = new Date();
		this.game = "";
		this.runners = "";
		this.console = "";
		this.estimate = "";
		this.setup = "";
		this.comments = "";
		this.commentators = "";
		this.prizes = "";
		this.channels = "";
	}
	
	/**
	 * Format a table row from HTML.
	 * @param cells
	 * @throws ParseException
	 */
	public Run(Elements cells) throws ParseException {
		this.date = Util.getRealDate(formatter.parse(cells.get(0).text()));
		this.game = cells.get(1).text();
		this.runners = cells.get(2).text();
		this.console = cells.get(3).text();
		this.estimate = cells.get(4).text();
		this.setup = cells.get(5).text();
		this.comments = cells.get(6).text();
		this.commentators = cells.get(7).text();
		this.prizes = cells.get(8).text();
		this.channels = Arrays.toString(cells.get(9).text().split(", "));
	}
	
	public void setRun(Run run) {
		this.date = run.getDate();
		this.game = run.getGame();
		this.runners = run.getRunners();
		this.console = run.getConsole();
		this.estimate = run.getEstimate();
		this.setup = run.getSetup();
		this.comments = run.getComments();
		this.commentators = run.getCommentators();
		this.prizes = run.getPrizes();
		this.channels = run.getChannels();
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

	public void setDate(Date date) {
		this.date = date;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getRunners() {
		return runners;
	}

	public void setRunners(String runners) {
		this.runners = runners;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public String getEstimate() {
		return estimate;
	}

	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}

	public String getSetup() {
		return setup;
	}

	public void setSetup(String setup) {
		this.setup = setup;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCommentators() {
		return commentators;
	}

	public void setCommentators(String commentators) {
		this.commentators = commentators;
	}

	public String getPrizes() {
		return prizes;
	}

	public void setPrizes(String prizes) {
		this.prizes = prizes;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}
}
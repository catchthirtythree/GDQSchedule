package com.mygdq.activity.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import android.content.Context;

import com.mygdq.db.model.DBMapperAdapter;
import com.mygdq.db.model.run.Run;
import com.mygdq.db.model.run.RunMapper;
import com.mygdq.util.Util;

/**
 * Used to scrape the GDQ site and hold the document and list of runs statically for the app.
 * 
 * @author Michael
 */
public class GDQScraper {
	private static final String URL_SGDQ = "https://gamesdonequick.com/schedule";
	
	/**
	 * Find all the runs from the database.
	 * @param context
	 * @return
	 */
	public static Run[] getRuns(Context context) {
		Run[] runs = null;
		
		try (DBMapperAdapter mapper = new RunMapper(context)) {
			runs = ((RunMapper) mapper).findAll();
		} catch (Exception e) { 
			/* Autocloseable requires a catch. */
		}
		
		return runs;
	}

	/**
	 * Find the list of runs from the scraped/parsed document.
	 * @return
	 * @throws ParseException
	 */
	private static Run[] findListOfRuns(Document doc) throws ParseException {
		Run[] runs;
		
		/* Get run table from document (store in phone's db) */ 
        Element table = doc.getElementById("runTable");
        Elements rows = table.getElementsByTag("tr");
        
        runs = new Run[rows.size()];
        /* For each row, store list of td elements in Run object  */
		for (int i = 0; i < rows.size(); ++i) {
			Elements tds = rows.get(i).getElementsByTag("td");
			
			// Silly fix for now.
			if (tds.size() < 6) {
				while (tds.size() != 6) {
					tds.add(new Element(Tag.valueOf("td"), ""));
				}
			}
			
			runs[i] = new Run(tds);
		}
		
		return runs;
	}

	/**
	 * Scrape and parse the site as a Jsoup document.
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public static Document scrape(Context context) throws MalformedURLException, IOException, ParseException {
		return Jsoup.parse(Util.getHtmlFrom(URL_SGDQ));
	}
	
	/**
	 * Update the database with the list of runs.
	 * @param context
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void updateDatabase(Context context) throws MalformedURLException, IOException, ParseException {
		// Scrape the GDQ site.
		Document document = scrape(context);
		
		// Get the list of runs from the document.
		Run[] runs = findListOfRuns(document);
		
		// Update the table.
		try (DBMapperAdapter mapper = new RunMapper(context)) {
			RunMapper rMapper = ((RunMapper) mapper);
			
			// Get the list of old runs.
			Run[] oldRuns = rMapper.findAll();
			
			for (int i = 0; i < runs.length; ++i) {
				// If we're still in range for the old runs, update the old runs in order.
				// Otherwise, add all new / out of range runs.
				// This keep things in a certain order.
				if (i < oldRuns.length) {
					Run oldRun = oldRuns[i];
					oldRun.setRun(runs[i]);
					
					rMapper.update(oldRun);
				} else {
					rMapper.insert(runs[i]);
				}
			}
		} catch (Exception e) { 
			/* Autocloseable requires a catch. */ 
		}
	}
}
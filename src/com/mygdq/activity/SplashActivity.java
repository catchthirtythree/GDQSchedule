package com.mygdq.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.mygdq.R;
import com.mygdq.activity.model.GDQScraper;
import com.mygdq.db.model.run.Run;
import com.mygdq.util.Util;

/**
 * Splash screen for the app.
 * 
 * @author Michael
 */
public class SplashActivity extends Activity {
	/**
	 * The {@link ProgressDialog} that will tell the user the app is in progress of searching.
	 */
	private ProgressDialog progressDialog;
	
	@Override public void onBackPressed() {}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		// Initialize and start the loading process.
		new LoadViewTask().execute();
	}

	private class LoadViewTask extends AsyncTask<Void, Progress, List<Run>> {
		@Override protected void onPreExecute() {
			// Create a new progress dialog
			progressDialog = new ProgressDialog(SplashActivity.this);
			// Change the positioning of the progress dialog.
			progressDialog.getWindow().setGravity(Gravity.BOTTOM);
			// Clear the screen dimming action on the progress dialog.
			progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			// Set the progress dialog to display a horizontal progress bar
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// This dialog can't be canceled by pressing the back key
			progressDialog.setCancelable(false);
			// This dialog isn't indeterminate
			progressDialog.setIndeterminate(false);
			// Display the progress dialog
			progressDialog.show();
		}

		@Override protected List<Run> doInBackground(Void... params) {
			// Create the list of runs that will be sent to the onPostExecute method.
			List<Run> runs = null;
			
			try {
				synchronized (this) {
					// Check if the phone has a network connection.
					// TODO: Allow the user to specify whether this should work on mobile or wifi, etc...
					publishProgress(Progress.NETWORK_CHECK);
					if (Util.testNetworkConnection(2000)
							&& Util.haveNetworkConnection(SplashActivity.this, Util.NetworkType.MOBILE) 
							|| Util.haveNetworkConnection(SplashActivity.this, Util.NetworkType.WIFI)) {
						// Scrape the website.
						publishProgress(Progress.SCRAPING);
						GDQScraper.scrape();

						// Update the database.
						publishProgress(Progress.UPDATING_DATABASE);
						GDQScraper.updateDatabase(SplashActivity.this);
					}
					
					// Retrieve the list of runs.
					publishProgress(Progress.RETRIEVING_RUNS);
					runs = GDQScraper.getRuns(SplashActivity.this);
				}
			} catch (MalformedURLException e) {
				// This shouldn't thrown unless the url is changed or the site goes down.
				// Counts as a connection issue and should go into else by default.
				System.exit(0);
			} catch (IOException e) {
				// This shouldn't thrown unless something weird happened when querying the site.
				// Counts as a connection issue and should go into else by default.
				System.exit(0);
			} catch (ParseException e) {
				// This shouldn't be thrown unless GDQ changes the way they display their date modified.
			}
			
			return runs;
		}
		
		@Override protected void onProgressUpdate(Progress... values) {
			// Set the current progress of the progress dialog.
			progressDialog.setMessage(values[0].message);
		}

		@Override protected void onPostExecute(List<Run> result) {
			// close the progress dialog
			progressDialog.dismiss();
			
			// Create an intent to send the user to the MyGdqActiity.
			Intent intent = new Intent(SplashActivity.this, GDQScheduleActivity.class);
			
			// Fire the intent.
			SplashActivity.this.startActivity(intent);
		}
	}
	
	/**
	 * @author Michael
	 * 
	 * Cute way to send messages to the progress dialog. 
	 */
	private enum Progress { 
		NETWORK_CHECK("Testing network connection."), 
		SCRAPING("Scraping GDQ site."),
		UPDATING_DATABASE("Updating the run database."),
		RETRIEVING_RUNS("Retrieving list of runs."); 
		
		String message;
		Progress(String message) {
			this.message = message;
		}
	}
}
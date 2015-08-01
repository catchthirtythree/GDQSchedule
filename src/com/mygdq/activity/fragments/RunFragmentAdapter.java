package com.mygdq.activity.fragments;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.mygdq.R;
import com.mygdq.activity.GDQScheduleActivity;
import com.mygdq.activity.model.GDQScraper;
import com.mygdq.db.model.run.Run;
import com.mygdq.util.Util.TypefaceSingleton;

/**
 * Lazy adapter class.
 * {@link UpcomingRunsFragment} and {@link PreviousRunsFragment} work nearly the same way except for onCreateView.
 * 
 * @author Michael
 */
public abstract class RunFragmentAdapter extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	/**
	 * The {@link SwipeRefreshLayout} allows for pulling down to refresh the application.
	 */
	SwipeRefreshLayout swipeLayout;
	
	@Override public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
	@Override public void onRefresh() {
	    new Handler().post(new Runnable() {
	        @Override public void run() {
	        	new UpdateDatabaseTask().execute();
	        }
	    });
	}

	/**
	 * Create a row for the date.
	 * @param inflater
	 * @param container
	 * @param date
	 * @return
	 */
	public View createDateRow(LayoutInflater inflater, ViewGroup container, Date date) {
		View dateView = inflater.inflate(R.layout.date, container, false);
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
		TextView tv = (TextView) dateView.findViewById(R.id.calendar_text);
		tv.setTextColor(Color.WHITE);
		tv.setText(df.format(date));
		tv.setTypeface(TypefaceSingleton.instance(getActivity(), "fonts/UBUNTU-REGULAR.TTF"));
		
		return dateView;
	}
	
	/**
	 * Create a row for runs.
	 * @param inflater
	 * @param container
	 * @param position
	 * @param run
	 * @return
	 */
	public View createRunRow(LayoutInflater inflater, ViewGroup container, int position, Run run) {
		Typeface tf = TypefaceSingleton.instance(getActivity(), "fonts/UBUNTU-REGULAR.TTF");
		
		View runView = null;
		if (position % 2 == 0) {
			runView = inflater.inflate(R.layout.run_light, container, false);
		} else {
			runView = inflater.inflate(R.layout.run_dark, container, false);
		}

		DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
		TextView run_time = (TextView) runView.findViewById(R.id.run_time_text);
		run_time.setText(df.format(run.getDate()));
		run_time.setTypeface(tf);

		TextView game_text = (TextView) runView.findViewById(R.id.run_game_text);
		game_text.setText(run.getGame());
		game_text.setTypeface(tf);

		TextView runner_text = (TextView) runView.findViewById(R.id.run_runner_text);
		runner_text.setText(run.getRunners());
		runner_text.setTypeface(tf);

		if (run.getCommentators().isEmpty()) {
			TableRow couch_row = (TableRow) runView.findViewById(R.id.run_couch);
			((ViewGroup) couch_row.getParent()).removeView(couch_row);
		} else {
			TextView couch_text = (TextView) runView.findViewById(R.id.run_couch_text);
			couch_text.setText(run.getCommentators());
			couch_text.setTypeface(tf);
		}
		
		return runView;
	}
	
	private class UpdateDatabaseTask extends AsyncTask<Void, Void, Void> {
		@Override protected void onPreExecute() {}

		@Override protected Void doInBackground(Void... params) {
			try {
				GDQScraper.updateDatabase(getActivity());
			} catch (IOException e) {
				// This shouldn't thrown unless something weird happened when querying the site.
				// This will count as a connection issue and we will ignore it.
			} catch (ParseException e) {
				// This shouldn't be thrown unless GDQ changes the way they display their date modified.
			}
			
			return null;
		}

		@Override protected void onPostExecute(Void result) {
			// Start activity or redraw fragment.
	        // Create an intent to send the user to the MyGdqActiity.
			Intent intent = new Intent(getActivity(), GDQScheduleActivity.class);
			
			// Fire the intent.
			RunFragmentAdapter.this.startActivity(intent);
        	
			// Stop refreshing.
            swipeLayout.setRefreshing(false);
		}
	}
}
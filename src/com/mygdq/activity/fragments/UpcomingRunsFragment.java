package com.mygdq.activity.fragments;

import java.util.Calendar;
import java.util.Date;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.mygdq.R;
import com.mygdq.activity.model.GDQScraper;
import com.mygdq.db.model.run.Run;
import com.mygdq.util.Util;

/**
 * A placeholder fragment containing a simple view.
 * 
 * @author Michael
 */
public class UpcomingRunsFragment extends RunFragmentAdapter {		
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static UpcomingRunsFragment newInstance(int sectionNumber) {
		UpcomingRunsFragment fragment = new UpcomingRunsFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
	
	@SuppressWarnings("deprecation")
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ur_gdq, container, false);
		
		// Setup the swipe on refresh.
	    swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
	    swipeLayout.setOnRefreshListener(this);
	    swipeLayout.setColorScheme(android.R.color.holo_blue_bright, 
	            android.R.color.holo_green_light, 
	            android.R.color.holo_orange_light, 
	            android.R.color.holo_red_light);
		
		TableLayout tl = (TableLayout) rootView.findViewById(R.id.URTableLayout);

		Date calendar = null;
		Date now = new Date();
		Run[] runs = GDQScraper.getRuns(getActivity());
		
		// Retrieve the list of runs sent in when a new instance is made.
		int index = 0;
		for (Run run : runs) {
			Date runDate = run.getDate();
			
			String[] time = run.getEstimate().split(":");
			Calendar cal = Calendar.getInstance();
			cal.setTime(runDate);
			cal.add(Calendar.HOUR, Integer.parseInt(time[0]));
			cal.add(Calendar.MINUTE, Integer.parseInt(time[1]));
			cal.add(Calendar.SECOND, Integer.parseInt(time[2]));
			Date add = cal.getTime();
			
			if (now.before(runDate) || now.before(add) && now.after(runDate)) {
				if (calendar == null || Util.getDayFrom(calendar) != Util.getDayFrom(runDate)) {
					tl.addView(createDateRow(inflater, container, calendar = runDate), new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				}
				
				tl.addView(createRunRow(inflater, container, index++, run), new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}
		}
		
		if (tl.getChildCount() == 0) {
			// Come here if gdq is over basically.
			tl.addView(createFinishedRow(inflater, container));
		}
	
		return rootView;
	}
}
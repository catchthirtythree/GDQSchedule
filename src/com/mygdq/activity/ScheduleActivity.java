package com.mygdq.activity;

import java.util.Locale;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mygdq.R;
import com.mygdq.activity.fragments.PlaceholderFragment;
import com.mygdq.activity.fragments.PreviousRunsFragment;
import com.mygdq.activity.fragments.UpcomingRunsFragment;

/**
 * Main ViewPager for the app.
 * 
 * @author Michael
 */
@SuppressWarnings("deprecation") public class ScheduleActivity extends ActionBarActivity implements ActionBar.TabListener {
	/**
	 * The {@link ProgressDialog} that will tell the user the app is in progress of searching.
	 */
	ProgressDialog mProgressDialog;
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded
	 * fragment in memory. If this becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	@Override public void onBackPressed() {}
	
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gdq);
		
		// Override the default font.
		//Util.setDefaultFont(this, "DEFAULT", "fonts/UBUNTU-REGULAR.TTF");

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mygdq, menu);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
	@Override public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
	 * sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		private static final int UPCOMING_RUNS_FRAGMENT = 0;
		private static final int PREVIOUS_RUNS_FRAGMENT = 1;
		
		private static final int NUM_PAGES = 2;
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override public Fragment getItem(int position) {
			// GetItem is called to instantiate the fragment for the given page.
			switch (position) {
			case UPCOMING_RUNS_FRAGMENT:
				return UpcomingRunsFragment.newInstance(position + 1);
			case PREVIOUS_RUNS_FRAGMENT:
				return PreviousRunsFragment.newInstance(position + 1);
			}

			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override public int getCount() {
			return NUM_PAGES;
		}

		@Override public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}
}
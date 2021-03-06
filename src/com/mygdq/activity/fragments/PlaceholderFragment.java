package com.mygdq.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mygdq.R;

/**
 * A placeholder fragment containing a simple view.
 * 
 * @author Michael
 */
public class PlaceholderFragment extends Fragment {		
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PreviousRunsFragment newInstance(int sectionNumber) {
		PreviousRunsFragment fragment = new PreviousRunsFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ur_gdq, container, false);
		return rootView;
	}
}
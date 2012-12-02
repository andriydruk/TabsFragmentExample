package com.example.newtabfragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SimpleFragment extends Fragment {
	
	private int index;
	private String tab;
	private SeparateTabStacks stack;
	
	public static SimpleFragment newInstance(String tab, int index){
		SimpleFragment fragment = new SimpleFragment();
		Bundle argsBundle = new Bundle();
		argsBundle.putInt("INDEX", index);
		argsBundle.putString("TAB", tab);
		fragment.setArguments(argsBundle);
		fragment.stack = null;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		index = getArguments().getInt("INDEX", 0);
		tab = getArguments().getString("TAB");
		if (getActivity() instanceof SeparateTabStacks){
			stack = (SeparateTabStacks) getActivity();
		}
		Log.d("Tab","OnCreate: tab "+tab+" index "+index);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d("TAG", "OnConfigChanged: tab "+tab+" index "+index);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("Tab","OnCreateView: tab "+tab+" index "+index);
		View view = inflater.inflate(R.layout.simple_fragment, container, false);
		TextView indexView = (TextView) view.findViewById(R.id.index);
		TextView tabView = (TextView) view.findViewById(R.id.tab);
		Button addButton = (Button) view.findViewById(R.id.add);
		indexView.setText("Index "+index);
		tabView.setText(tab);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stack.addFragment(SimpleFragment.newInstance(tab, index+1), tab);
			}
		});
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.d("Tab","OnDestroyView: tab "+tab+" index "+index);
	}
	
	

}

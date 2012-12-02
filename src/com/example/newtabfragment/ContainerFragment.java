package com.example.newtabfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContainerFragment extends Fragment {
	
	private Fragment startFragment;
	
	public static ContainerFragment newInstance(Fragment fragment){
		ContainerFragment container = new ContainerFragment();
		container.startFragment = fragment;
		return container;
	}
	
	public void addFragment(Fragment fragment){
		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.replace(R.id.content, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (startFragment!=null) {
			addFragment(startFragment);
			startFragment = null;
		}
		
	}

	public void pop(){
		getChildFragmentManager().popBackStack();
	}
	
	public int getFragmentCount(){
		return getChildFragmentManager().getBackStackEntryCount();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.container_fragment, container, false);
		return view;
	}

}

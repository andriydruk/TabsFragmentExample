package com.example.newtabfragment;

import android.support.v4.app.Fragment;

public interface SeparateTabStacks {

	// add fragment in stack
	public void addFragment(Fragment fragment, String tab);

	// pop fragment from the current stack
	public void popFragment();

	// change current stack
	public void changeTab(String tab);
}

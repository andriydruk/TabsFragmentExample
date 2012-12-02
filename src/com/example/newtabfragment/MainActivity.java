package com.example.newtabfragment;

import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabsFragmentActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		initTabHost();
		if (savedInstanceState != null) {
			tabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", tabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}

	private void initTabHost() {
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		TabInfo tabInfo = null;
		Bundle args = new Bundle();
		args.putInt("INDEX", 0);
		addTab(this, this.tabHost, this.tabHost.newTabSpec("Tab1").setIndicator("Tab 1"), (tabInfo = new TabInfo(
				"Tab1", SimpleFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		addTab(this, this.tabHost, this.tabHost.newTabSpec("Tab2").setIndicator("Tab 2"), (tabInfo = new TabInfo(
				"Tab2", SimpleFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		addTab(this, this.tabHost, this.tabHost.newTabSpec("Tab3").setIndicator("Tab 3"), (tabInfo = new TabInfo(
				"Tab3", SimpleFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);

		// Default to first tab
		this.onTabChanged("Tab1");
		tabHost.setOnTabChangedListener(this);
	}

}

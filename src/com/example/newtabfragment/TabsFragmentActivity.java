package com.example.newtabfragment;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

public class TabsFragmentActivity extends FragmentActivity implements TabContentFactory, TabHost.OnTabChangeListener, SeparateTabStacks {

	protected TabHost tabHost;
	protected HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
	private TabInfo mLastTab = null;

	protected class TabInfo {
		
		 protected String tag;
		 private Class<? extends Fragment> clss;
         private Bundle args;
         private Fragment fragment;
         
         TabInfo(String tag, Class<? extends Fragment> clazz, Bundle args) {
        	 this.tag = tag;
        	 this.clss = clazz;
        	 this.args = args;
         }

	}

    
	@Override
	public void onBackPressed() {
		ContainerFragment fragment = (ContainerFragment) mLastTab.fragment;
		if (fragment.getFragmentCount()>1) {
			fragment.pop();
		}
		else {
			super.onBackPressed();
		}
	}

	protected void addTab(TabsFragmentActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {

		tabSpec.setContent(this);
        String tag = tabSpec.getTag();

        tabInfo.fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.fragment);
            ft.commit();
            activity.getSupportFragmentManager().executePendingTransactions();
        }

        tabHost.addTab(tabSpec);
	}
	
	/** (non-Javadoc)
	 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
	 */
	@Override
	public View createTabContent(String tag) {
        View v = new View(this);
        v.setMinimumWidth(0);
        v.setMinimumHeight(0);
        return v;
	}

	/** (non-Javadoc)
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	public void onTabChanged(String tag) {
		TabInfo newTab = this.mapTabInfo.get(tag);
		if (mLastTab != newTab) {
			FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                	ft.detach(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                	newTab.args.putString("TAB", newTab.tag);
                    Fragment newfragment = Fragment.instantiate(this,
                            newTab.clss.getName(), newTab.args);
                    newTab.fragment = ContainerFragment.newInstance(newfragment);
                    ft.add(android.R.id.tabcontent, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }

            mLastTab = newTab;
            ft.commit();
            this.getSupportFragmentManager().executePendingTransactions();
		}
    }
	
	/** (non-Javadoc)
	 * implementation SeparateTabStacks
	 */

	@Override
	public void addFragment(Fragment fragment, String tab) {
		ContainerFragment container = (ContainerFragment) mapTabInfo.get(tab).fragment;
		container.addFragment(fragment);
	}

	@Override
	public void popFragment() {
		ContainerFragment container = (ContainerFragment) mLastTab.fragment;
		container.pop();
	}

	@Override
	public void changeTab(String tab) {
		onTabChanged(tab);
	}



}

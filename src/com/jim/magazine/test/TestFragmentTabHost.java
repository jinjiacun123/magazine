package com.jim.magazine.test;

import com.jim.magazine.R;
import com.jim.magazine.fragment.BookselfFragment;
import com.jim.magazine.fragment.HomeFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.Button;
import android.widget.TabHost.TabSpec;

public class TestFragmentTabHost extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_top);

	    //set up FragmentTabhost
	    FragmentTabHost host = (FragmentTabHost)findViewById(android.R.id.tabhost);
	    host.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

	    //add tabspec
	    TabSpec tabSpec1 = host.newTabSpec("tab1");
	    Button btn1 = new Button(this);
	    btn1.setBackgroundResource(R.drawable.homepage_a);
	    tabSpec1.setIndicator(btn1);
	    Bundle bundle1 = new Bundle();
	    bundle1.putString("name", "tab1");
	    host.addTab(tabSpec1,HomeFragment.class,bundle1);


	    
	    TabSpec tabSpec2 = host.newTabSpec("tab2");
	    Button btn2 = new Button(this);
	    btn2.setBackgroundResource(R.drawable.homepage_a);
	    tabSpec2.setIndicator(btn2);
	    Bundle bundle2 = new Bundle();
	    bundle2.putString("name", "tab2");
	    host.addTab(tabSpec2,BookselfFragment.class,bundle2);
	}
}

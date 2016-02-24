package com.jim.magazine;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class SlidingActivity extends SlidingFragmentActivity
{
	private FragmentTabHost ftabhost;// 定义一个fragmenttabhost
	private String[] tabtag = { "home_page",
			                    };// 给tab取得别名
	private String[] tabtext = { "首页"};// 选项卡里面设置的名字
	private static Class[] cla = { HomeFragment.class};
	// 没有被选中的图片的id
	private int[] imageid = { R.drawable.homepage_a,
			                  };
	private LayoutInflater inflater;// 布局解析器
	
	ListView listView ;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initDate();
		setBehindContentView(R.layout.leftmenu);
		// configure the SlidingMenu
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);

		// 璁剧疆婊戝姩鑿滃崟瑙嗗浘鐨勫搴�
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 璁剧疆娓愬叆娓愬嚭鏁堟灉鐨勫�
		menu.setFadeDegree(0.35f);
		/**
		 * SLIDING_WINDOW will include the Title/ActionBar in the content
		 * section of the SlidingMenu, while SLIDING_CONTENT does not.
		 */
		// menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// menu.setMenu(R.layout.leftmenu);
		
		 // Get ListView object from xml
        listView = (ListView) findViewById(R.id.navigate_list);
        
        // Defined Array values to show in ListView
        String[] values = new String[] { "Android List View", 
                                         "Adapter implementation",
                                         "Simple List View In Android",
                                         "Create List View Android", 
                                         "Android Example", 
                                         "List View Source Code", 
                                         "List View Array Adapter", 
                                         "Android Example List View" 
                                        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter); 
	}
	
	
	
	
	private void initDate() {
		inflater = LayoutInflater.from(SlidingActivity.this);
		ftabhost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
		FragmentManager t = getSupportFragmentManager();
		ftabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		
		for (int i = 0; i < tabtext.length; i++) {
			ftabhost.addTab(
					ftabhost.newTabSpec(this.tabtag[i]).setIndicator(getView(i)),
					SlidingActivity.cla[i], null);
		}
	}

	private View getView(int i) {
		View view = inflater.inflate(R.layout.activity_main_tabview, null);
		TextView tabtext = (TextView) view.findViewById(R.id.tvtext);
		ImageView tabicon = (ImageView) view.findViewById(R.id.tabicon);
		tabtext.setText(this.tabtext[i]);
		switch (i) {
		case 0:
			tabicon.setBackgroundResource(R.drawable.selector_tab_homepage);
			break;
		}
		return view;
	}

}

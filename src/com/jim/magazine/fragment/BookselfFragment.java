package com.jim.magazine.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jim.magazine.R;
import com.jim.magazine.view.CustomGrid;
import com.jim.magazine.view.TitleBarView;

public class BookselfFragment extends Fragment
{
	GridView grid;
	
	String[] title= {
			"2015","2015","2015","2015","2015",
			"2015","2015","2015","2015","2015",
			"2015","2015","2015","2015","2015",
	};
	
    String[] web = {
            "Google",            "Github",            "Instagram",            "Facebook",            "Flickr",
            "Pinterest",            "Quora",            "Twitter",            "Vimeo",            "WordPress",
            "Youtube",            "Stumbleupon",            "SoundCloud",            "Reddit",            "Blogger"
 
    } ;
    int[] imageId = {
            R.drawable.cover_txt,            R.drawable.cover_txt,            R.drawable.cover_txt,            R.drawable.cover_txt,            R.drawable.cover_txt,
            R.drawable.cover_txt,            R.drawable.cover_txt,            R.drawable.cover_txt,            R.drawable.cover_txt,            R.drawable.cover_txt,
            R.drawable.cover_txt,            R.drawable.cover_txt,            R.drawable.cover_txt,            R.drawable.cover_txt,            R.drawable.cover_txt

    };
    
    private View mBaseView;
	private TitleBarView mTitleBarView;
    
    public BookselfFragment create(String address)
    {
        //ELog.i(TAG, "@--> MyFragment.create()");
    	BookselfFragment f = new BookselfFragment();
        Bundle b = new Bundle();
        b.putString("address", address);
        f.setArguments(b);
        return f;
    }
    @SuppressLint("NewApi")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        //Random r = new Random(System.currentTimeMillis());
        Bundle b = getArguments();
        mBaseView=inflater.inflate(R.layout.fragment_bookself, null);
        CustomGrid adapter = new CustomGrid(getActivity(), web, imageId,title);
        grid=(GridView)mBaseView.findViewById(R.id.grid);
                grid.setAdapter(adapter);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(getActivity(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
 
                    }
                });
       
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
		init();
		return mBaseView;
    }
    
	private void init(){//GONE-隐藏,VISIBLE-显示
		mTitleBarView.setCommonTitle(View.GONE,
                View.VISIBLE, 
                View.VISIBLE,
                View.GONE);
		mTitleBarView.setTitleText(R.string.brand);
		
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//mTitleBarView.setPopWindow(mPopupWindow, mTitleBarView);
				//mCanversLayout.setVisibility(View.VISIBLE);
			}
		});
	}
}

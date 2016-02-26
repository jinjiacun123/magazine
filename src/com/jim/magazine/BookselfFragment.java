package com.jim.magazine;

import java.util.Random;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.jim.magazine.R;
import com.jim.magazine.view.CustomGrid;

public class BookselfFragment extends Fragment
{
	GridView grid;
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"
 
    } ;
    int[] imageId = {
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt,
            R.drawable.cover_txt
 
    };
    
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
        Random r = new Random(System.currentTimeMillis());
        Bundle b = getArguments();
        View v = inflater.inflate(R.layout.fragment_bookself, null);
        CustomGrid adapter = new CustomGrid(getActivity(), web, imageId);
        grid=(GridView)v.findViewById(R.id.grid);
                grid.setAdapter(adapter);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(getActivity(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
 
                    }
                });
        /*
        v.setBackgroundColor(r.nextInt() >> 8 | 0xFF << 24);
        TextView txvAddress = (TextView) v.findViewById(R.id.textView1);
        txvAddress.setTextColor(r.nextInt() >> 8 | 0xFF << 24);
        txvAddress.setBackgroundColor(r.nextInt() >> 8 | 0xFF << 24);
        txvAddress.setText(b.getString("address", ""));
        */
        
        return v;
    }
}

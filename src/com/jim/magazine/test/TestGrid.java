package com.jim.magazine.test;

import com.jim.magazine.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
 
public class TestGrid extends Activity {
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
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gridview);
 
        TestCustomGrid adapter = new TestCustomGrid(TestGrid.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
                grid.setAdapter(adapter);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(TestGrid.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
 
                    }
                });
 
    }
 
}
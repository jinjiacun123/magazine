package com.jim.magazine.view;

import java.util.ArrayList;

import com.jim.magazine.R;
import com.jim.magazine.entity.MagazineEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class CustomGrid extends BaseAdapter{
      private Context mContext;
      ArrayList<MagazineEntity> magazine_array=new ArrayList<MagazineEntity>() ;
 
        public CustomGrid(Context c, ArrayList<MagazineEntity> magazine_array) {
            mContext = c;
            MagazineEntity my_magazine_entity;
           this.magazine_array = magazine_array;
        }
 
        @Override
        public int getCount() {
            return magazine_array.size();
        }
 
        @Override
        public Object getItem(int position) {
            return magazine_array.get(position);
        }
 
        @Override
        public long getItemId(int position) {
            return 0;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            if (convertView == null) {
 
                grid = new View(mContext);
                grid = inflater.inflate(R.layout.view_grid_single, null);
                TextView titleView = (TextView)grid.findViewById(R.id.grid_title);
                TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
                titleView.setText(String.valueOf(magazine_array.get(position).getYear()));
                textView.setText(magazine_array.get(position).getTitle());
               // imageView.setImageBitmap(magazine_array.get(position).getImg());
            } else {
                grid = convertView;
            }
 
            return grid;
        }
}
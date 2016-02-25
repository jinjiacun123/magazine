package com.jim.magazine;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

public class Bookself extends Fragment{
	public View view;// 缓存Fragment view
	private GridView bookShelf;
	private int[] data= {
			R.drawable.cover_txt,R.drawable.cover_txt,
			R.drawable.cover_txt,R.drawable.cover_txt
	};
	
	private String[] name={
			"book1","book2","book3","book4"
	};
	
	private Button iv;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
			                 ViewGroup container,
			                 Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.view, null);
			
			//bookShelf = (GridView)view.findViewById(R.id.bookShelf);
			//ShlefAdapter adapter = new ShlefAdapter();
			//bookShelf.setAdapter(adapter);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}
	
	class ShlefAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			return null;
		}

		/*
		@Override
		public View getView(int position, View contentView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			//contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item1, null);
			contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item1, null);
			
			TextView view = (TextView)contentView.findViewById(R.id.imageView1);
			if(data.length>position)
			{
				if(position<name.length)
				{
					view.setText(name[position]);
				}
			}
			else
			{
				view.setClickable(false);
				view.setVisibility(View.INVISIBLE);
			}
			return contentView;
		}
		*/
		
	}
	
	
	
	
	
	
	
	
	
	
}

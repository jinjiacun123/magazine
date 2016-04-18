package com.jim.magazine.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
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
import com.jim.magazine.entity.MagazineEntity;
import com.jim.magazine.view.CustomGrid;
import com.jim.magazine.view.TitleBarView;

public class BookselfFragment extends Fragment
{
	GridView grid;
	
	private ArrayList<MagazineEntity> magazine_array = new ArrayList<MagazineEntity>();
	
    
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
    	//初始化数据
    	MagazineEntity my_magazine_entity = new MagazineEntity();
    	my_magazine_entity.setYear(2016);
    	my_magazine_entity.setTitle("2月刊");
    	my_magazine_entity.setImg(BitmapFactory.decodeResource(getResources(),
										R.drawable.ic_launcher));
    	magazine_array.add(my_magazine_entity);
    	
        //Bundle b = getArguments();
        mBaseView=inflater.inflate(R.layout.fragment_bookself, null);
        CustomGrid adapter = new CustomGrid(getActivity(), magazine_array);
		grid = (GridView) mBaseView.findViewById(R.id.grid);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(
						getActivity(),
						"You Clicked at "
								+ magazine_array.get(position).getTitle(),
						Toast.LENGTH_SHORT).show();

			}
		});
       
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
		init();
		return mBaseView;
    }
    
	public GridView getGrid() {
		return grid;
	}

	public void setGrid(GridView grid) {
		this.grid = grid;
	}

	public ArrayList<MagazineEntity> getMagazine_array() {
		return magazine_array;
	}

	public void setMagazine_array(ArrayList<MagazineEntity> magazine_array) {
		this.magazine_array = magazine_array;
	}

	public View getmBaseView() {
		return mBaseView;
	}

	public void setmBaseView(View mBaseView) {
		this.mBaseView = mBaseView;
	}

	public TitleBarView getmTitleBarView() {
		return mTitleBarView;
	}

	public void setmTitleBarView(TitleBarView mTitleBarView) {
		this.mTitleBarView = mTitleBarView;
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

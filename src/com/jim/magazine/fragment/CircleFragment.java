package com.jim.magazine.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jim.magazine.R;
import com.jim.magazine.view.TitleBarView;

public class CircleFragment extends Fragment
{
	 
    private View mBaseView;
	private TitleBarView mTitleBarView;
	
	//ico
	private int[] ico={
			R.drawable.circle_ico,
			
	};
    //title
	private String[] title={
			"Pchouse家具杂志"
	};
	//content
	private String[] content={
			"[畅网圈子必读]在圈子分享好产品,发帖即可赚积分!"
	};
	//time
	private String[] time={
			"2016-02-28  13:58:00"
	};
	//message
	private String[] message={
			"100"
	};
   
	 String inflater = Context.LAYOUT_INFLATER_SERVICE; 
	 LayoutInflater layoutInflater; 
	 private RatingAdapter raAdapter; 
    
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
        mBaseView=inflater.inflate(R.layout.fragment_circle, null);
        
        //初始化listview
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
		
		raAdapter = new RatingAdapter(getActivity()); 
		ListView listView = (ListView)mBaseView.findViewById(R.id.list);
		//listView.setListAdapter(raAdapter);
		listView.setAdapter(raAdapter);
		
		init();
		return mBaseView;
    }
    
	private void init(){//GONE-隐藏,VISIBLE-显示
		mTitleBarView.setCommonTitle(View.VISIBLE,
				                                                       View.GONE, 
				                                                       View.VISIBLE,
				                                                       View.GONE);
		mTitleBarView.setBtnLeft(R.drawable.ctl_menu, 0);
		mTitleBarView.setTitleText(R.string.brand);
		
		
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//mTitleBarView.setPopWindow(mPopupWindow, mTitleBarView);
				//mCanversLayout.setVisibility(View.VISIBLE);
			}
		});
	}
	
	 //自定义一个Adapter继承BaseAdapter，要重写getCount(),getItem(),getItemId(),getView()四种方法 
	 private class RatingAdapter extends BaseAdapter 
	 { 
		  	private Context context; 
		  	//构造函数 
			  public RatingAdapter(Context context) 
			  { 
				   this.context = context; 
				   layoutInflater = (LayoutInflater) context 
				       .getSystemService(inflater); 
			  } 
	   
			  //@Override 
			  @Override
			public int getCount() 
			  { 
				  return title.length; 
			  } 
	   
			   // @Override 
			  @Override
			public Object getItem(int position) 
			  { 
				  return title[position]; 
			  } 
	   
			   // @Override 
			  @Override
			public long getItemId(int position) 
			  { 
				  return position; 
			  } 
		  
			  //设置星行分数 
			  public void setRating(int position, float rating) 
			  { 
				  
			  } 
	   
			   // @Override 
			  @Override
			public View getView(int position, View convertView, ViewGroup parent) 
			  {  
				   //对listview布局 
				   LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate( 
				       R.layout.view_circle, null);
				   
				   ImageView ivIco = (ImageView)linearLayout.findViewById(R.id.ico);
				   TextView ivTitle = (TextView)linearLayout.findViewById(R.id.title);
				   TextView ivContent = (TextView)linearLayout.findViewById(R.id.content);
				   TextView ivTime = (TextView)linearLayout.findViewById(R.id.time);
				   TextView ivMessage = (TextView)linearLayout.findViewById(R.id.number);
				   
				   ivIco.setImageResource(ico[position]);
				   ivTitle.setText(title[position]);
				   ivContent.setText(content[position]);
				   ivTime.setText(time[position]);
				   ivMessage.setText(message[position]);
				   
				   return linearLayout; 
			  } 
	 } 
}

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
import android.widget.RatingBar;
import android.widget.TextView;

import com.jim.magazine.R;
import com.jim.magazine.view.TitleBarView;

public class CircleFragment extends Fragment
{
	 
    private View mBaseView;
	private TitleBarView mTitleBarView;
	
	 //定义四个数组，分别做显示用 
	 private static String[] applicationNames = new String[] 
	   {
		 "this is just a button",
		 "made by forrest", 
		 "you can do it",
		 "come on",
		 "you will success" 
		}; 
	 private static String[] authors = new String[] 
	   { 
		 "邵洋江",
		 "邵洋江",
		 "邵洋江",
		 "邵洋江",
		 "邵洋江" 
		}; 
	 private static int[] resIds = new int[] 
	   {
		 R.drawable.cover_txt,
		 R.drawable.cover_txt,
		 R.drawable.cover_txt, 
	    R.drawable.cover_txt,
	    R.drawable.cover_txt,
	    R.drawable.cover_txt 
	   }; 
	 private static float[] applicationRating = new float[] 
	   { (float) 5.0,
		 (float) 5.0,
		 (float) 3.5,
		 (float) 5.0,
		 (float) 4.0 
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
				  return applicationNames.length; 
			  } 
	   
			   // @Override 
			  @Override
			public Object getItem(int position) 
			  { 
				  return applicationNames[position]; 
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
				   applicationRating[position] = rating; 
				   //在adapter的数据发生变化以后通知UI主线程根据新的数据重新画图 
				   notifyDataSetChanged(); 
			  } 
	   
			   // @Override 
			  @Override
			public View getView(int position, View convertView, ViewGroup parent) 
			  {  
				   //对listview布局 
				   LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate( 
				       R.layout.view_circle, null); 
				   //分别得到五个组件 
				   ImageView ivLogo = (ImageView) linearLayout 
				       .findViewById(R.id.ivLogo); 
				   TextView tvApplicationName = ((TextView) linearLayout 
				       .findViewById(R.id.tvApplicationName)); 
				   TextView tvAuthor = (TextView) linearLayout 
				       .findViewById(R.id.tvAuthor); 
				   TextView tvRating = (TextView) linearLayout 
				       .findViewById(R.id.tvRating); 
				   RatingBar ratingBar = (RatingBar) linearLayout 
				       .findViewById(R.id.ratingbar); 
				   //五个组件分别得到内容 
				   ivLogo.setImageResource(resIds[position]); 
				   tvApplicationName.setText(applicationNames[position]); 
				   tvAuthor.setText(authors[position]); 
				   tvRating.setText(String.valueOf(applicationRating[position])); 
				   ratingBar.setRating(applicationRating[position]); 
				   return linearLayout; 
			  } 
	 } 
}

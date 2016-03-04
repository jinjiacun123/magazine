package com.jim.magazine.view;

import java.util.ArrayList; 
import java.util.List; 
   

import com.jim.magazine.R;

import android.app.AlertDialog; 
import android.app.ListActivity; 
import android.content.Context; 
import android.content.DialogInterface; 
import android.content.DialogInterface.OnClickListener; 
import android.os.Bundle; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.BaseAdapter; 
import android.widget.ImageView; 
import android.widget.LinearLayout; 
import android.widget.ListView; 
import android.widget.RatingBar; 
import android.widget.TextView; 
   
public class ListCircleView extends ListActivity { 
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
   
	 @Override 
	 protected void onListItemClick(ListView l, View view, final int position,long id) 
	 { 
	  View myView = getLayoutInflater().inflate(R.layout.rating, null); 
	  final RatingBar ratingBar = (RatingBar) myView.findViewById(R.id.ratingbar); 
	  ratingBar.setRating(applicationRating[position]); 
	  new AlertDialog.Builder(this).setTitle(applicationNames[position]) 
	      .setMessage("给应用程序打分").setIcon(resIds[position]) 
	      .setView(myView).setPositiveButton("确定", new OnClickListener() 
	      { 
	   
	      // @Override 
	       @Override
		public void onClick(DialogInterface dialog, int which) 
	       { 
	        raAdapter.setRating(position, ratingBar.getRating());         
	       } 
	      }).setNegativeButton("取消", null).show(); 
	 } 
    
	 //@Override首先先new出listview，再对每个listview进行布局，再产生一个自定义适配器， 
	 //再把这个适配器映射到listview中 
	 @Override
	public void onCreate(Bundle savedInstanceState) 
	 { 
		  super.onCreate(savedInstanceState); 
		 // List<View> viewList = new ArrayList<View>(); 
		  //viewList.add(getLayoutInflater().inflate(R.layout.view_circle, null)); 
		  raAdapter = new RatingAdapter(this); 
		  setListAdapter(raAdapter); 
	 } 
}
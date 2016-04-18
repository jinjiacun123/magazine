package com.jim.magazine.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jim.magazine.R;
import com.jim.magazine.entity.HomeArticle;
import com.jim.magazine.entity.ImageEntity;

//下拉杂志列表
//自定义一个Adapter继承BaseAdapter，要重写getCount(),getItem(),getItemId(),getView()四种方法 
public class HomeArticleAdapter extends BaseAdapter 
 { 
		  	private Context context; 
		  	LayoutInflater layout_inflater; 
		  	String inflater = Context.LAYOUT_INFLATER_SERVICE;
		  	private ViewPager viewPager;
		  	private ImageView[] mImageViews;//切换图片显示控件
		  	private ArrayList<HomeArticle> article_array = new ArrayList<HomeArticle>();//文章列表
		  	private ArrayList<ImageEntity> image_entity_array = new ArrayList<ImageEntity>();//图片列表
		  	//构造函数 
			  public HomeArticleAdapter(Context context, ArrayList<HomeArticle> article_array) 
			  { 
				   this.context = context;
				   layout_inflater = (LayoutInflater) context 
				       .getSystemService(inflater);
				 this.article_array = article_array;
			  } 
	   
			  @Override
			public int getCount() 
			  {  
				  return article_array.size(); 
			  } 
	   
			   // @Override 
			  @Override
			public Object getItem(int position) 
			  { 
				  return article_array.get(position); 
			  } 
	   
			   // @Override 
			  @Override
			public long getItemId(int position) 
			  { 
				  return position; 
			  } 
	   
			  @Override
			public View getView(int position, View convertView, ViewGroup parent) 
			  {  
				  LinearLayout linearLayout = null;
				  if(position == 0)
				  {
					  //对listview布局 
					  linearLayout  = (LinearLayout) layout_inflater.inflate( 
					       R.layout.home_list_item_first, null);
					  
						//广告左滑
						viewPager = (ViewPager) linearLayout.findViewById(R.id.home_ad);
						
						//5张图片切换
						for (int i = 0; i < 5; i++) {
							ImageEntity b = new ImageEntity();
							b.setImage(BitmapFactory.decodeResource(this.context.getResources(),
									R.drawable.ic_launcher));

							image_entity_array.add(b);
						}
						
						mImageViews = new ImageView[image_entity_array.size()];
						for(int i=0; i<mImageViews.length; i++){
							ImageView imageView = new ImageView(this.context);
							mImageViews[i] = imageView;
							imageView.setImageBitmap(image_entity_array.get(i).getImage());
						}
					  HomeImgAdapter home_img_adapter = new HomeImgAdapter(this.context, image_entity_array, mImageViews); 
						
						viewPager.setAdapter(home_img_adapter);
						viewPager.setOnPageChangeListener(null);
						viewPager.setCurrentItem((mImageViews.length) * 100);
						//new ImageLoadTask(this.context, home_img_adapter).execute(image_url);
				  }
				  else
				  {
					  //对listview布局 
					    linearLayout = (LinearLayout) layout_inflater.inflate( 
					       R.layout.view_home_list_item, null);
					   
	                   ImageView home_list_item_refresh   = (ImageView)linearLayout.findViewById(R.id.home_list_item_refresh);
					   TextView     home_list_item_title          = (TextView)linearLayout.findViewById(R.id.home_list_item_title);
					   ImageView home_list_item_edit          = (ImageView)linearLayout.findViewById(R.id.home_list_item_edit);
					   ImageView home_list_item_p_1           = (ImageView)linearLayout.findViewById(R.id.home_list_item_p_1);
					  TextView home_list_item_content       = (TextView)linearLayout.findViewById(R.id.home_list_item_content);
					  ImageView home_list_item_p_2            = (ImageView)linearLayout.findViewById(R.id.home_list_item_p_2);
					   
					   home_list_item_refresh.setVisibility(View.GONE);
					   home_list_item_edit.setVisibility(View.GONE);
					   
					  if(1 == position)
					   {
						   home_list_item_refresh.setVisibility(View.VISIBLE);
						   home_list_item_edit.setVisibility(View.VISIBLE);
						   home_list_item_refresh.setImageResource(R.drawable.home_btn_fresh);
						   home_list_item_edit.setImageResource(R.drawable.home_edit);
					   }
					   
					   home_list_item_title.setText(article_array.get(position).getTitle());
					   home_list_item_content.setText(article_array.get(position).getContent());
					   //p1
					   if(article_array.get(position).getP1_e().getImage() != null)
					   {
						   home_list_item_p_1.setImageBitmap(article_array.get(position).getP1_e().getImage());
					   }
					   //p2
					   if(article_array.get(position).getP2_e().getImage() != null)
					   {
						   home_list_item_p_2.setImageBitmap(article_array.get(position).getP2_e().getImage());
					   }	                   
				  }
				  
				  return linearLayout;
			  } 
	 } 
package com.jim.magazine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MineFragment extends Fragment implements OnClickListener {

	private View mBaseView;
	public MineFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

    @SuppressLint("NewApi")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        //Random r = new Random(System.currentTimeMillis());
        Bundle b = getArguments();
        mBaseView=inflater.inflate(R.layout.fragment_mine, null);
        
        //登陆
       ImageView login = (ImageView)mBaseView.findViewById(R.id.my_login);
        login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent itent = new Intent(getActivity(), LoginActivity.class);
				startActivity(itent);
			}        	
        });
        
        
        //如何成为会员
       ImageView how_to_mem = (ImageView)mBaseView.findViewById(R.id.how_to_mem);
        how_to_mem.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View arg0)
        	{
        		Intent intent = new Intent(getActivity(), HowmemActivity.class);
        		startActivity(intent);
        	}
        });
    
		return mBaseView;
}
}

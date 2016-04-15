package com.jim.magazine.help;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.jim.magazine.adapter.HomeImgAdapter;
import com.jim.magazine.entity.ImageEntity;

public class ImageLoadTask extends AsyncTask<String, Void, Void> {
	private HomeImgAdapter adapter;

	public ImageLoadTask(Context context, HomeImgAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	protected Void doInBackground(String... params) {
		String url = params[0];
		String p2 = params[1];
		for (int i = 0; i < adapter.getCount(); i++) {
			ImageEntity bean = (ImageEntity) adapter.getItem(i);
			Bitmap bitmap = BitmapFactory.decodeStream(Request
					.HandlerData(params[i]));
			bean.setImage(bitmap);
			publishProgress(); 
		}

		return null;
	}

	public void onProgressUpdate(Void... voids) {
		if (isCancelled())
			return;
		adapter.notifyDataSetChanged();
	}
}

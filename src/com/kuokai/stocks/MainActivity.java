package com.kuokai.stocks;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private final String TAG = "MainActivity";
	
	//define ui controls
	private ViewPager mPager;
	private List<View> listViews;
	private ImageView cursor;
	private TextView status, sip, box, carton;
	private int offset = 0;
	private int currIndex =0;
	private int pngW;
	private int titleWidth = 0;
	

	/** Called when the activity is first created.*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initImageView();
		initTextView();
		initViewPager();
	}
	
	//Initial animate
	private void initImageView(){
		cursor = (ImageView) this.findViewById(R.id.img_cursor);
		pngW = BitmapFactory.decodeResource(getResources(), R.drawable.cursor).getWidth();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenW = displayMetrics.widthPixels;
		Log.d(TAG, "screenW = " + screenW);
		titleWidth = screenW / 4;
		Log.d(TAG, "titleWidth = " + titleWidth);
		if(pngW > titleWidth)
		{
			cursor.getLayoutParams().width = titleWidth;
			pngW = titleWidth;
		}
		offset = (titleWidth - pngW) /2;
		//offset = (screenW / 3 - pngW) / 2;
		Log. d(TAG, "offest = " + offset);
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);
	}
	//Initial all TextView
	private void initTextView()
	{
		status = (TextView) this.findViewById(R.id.txt_status);
		sip = (TextView) this.findViewById(R.id.txt_sip);
		box = (TextView) this.findViewById(R.id.txt_box);
		carton = (TextView) this.findViewById(R.id.txt_carton);
		
		status.setOnClickListener(new textViewOnClickListener(0));
		sip.setOnClickListener(new textViewOnClickListener(1));
		box.setOnClickListener(new textViewOnClickListener(2));
		carton.setOnClickListener(new textViewOnClickListener(3));
	}
	
	public class textViewOnClickListener implements View.OnClickListener{

		//Show that index of page
		private int index = 0;
		
		public textViewOnClickListener(int i) {
			// TODO Auto-generated constructor stub
			this.index = i;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mPager.setCurrentItem(index);
		}
	};
	
	//Initial ViewPager
	private void initViewPager(){
		mPager = (ViewPager) this.findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		listViews.add(mInflater.inflate(R.layout.layout_status, null));
		listViews.add(mInflater.inflate(R.layout.layout_sip, null));
		listViews.add(mInflater.inflate(R.layout.layout_box, null));
		listViews.add(mInflater.inflate(R.layout.layout_carton, null));
		mPager.setAdapter(new kkPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new kkOnPageChangeListener(){});
		
	}
	
	//ViewPager Adapter
	public class kkPagerAdapter extends PagerAdapter{
		
		public List<View> kkListViews;

		public kkPagerAdapter(List<View> listViews) {
			// TODO Auto-generated constructor stub
			this.kkListViews = listViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2){
			((ViewPager)arg0).removeView(kkListViews.get(arg1));
		}
		
		@Override
		public void finishUpdate(View arg0){
			
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return kkListViews.size();
		}
		
		@Override
		public Object instantiateItem(View arg0, int arg1){
			((ViewPager) arg0).addView(kkListViews.get(arg1), 0);
			return kkListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == (arg1);
		}
		
		@Override
		public Parcelable saveState(){
			return null;
		}
		
		@Override
		public void startUpdate(View arg0){
			
		}
	}
	
	//Page Change Listener
	public class kkOnPageChangeListener implements OnPageChangeListener{

		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			
			/*Animation animation = null;
			switch(arg0){
			case 0:
				if(currIndex == 1){
					animation
				}
			}*/
			Animation animation = new TranslateAnimation(titleWidth * currIndex + offset, titleWidth * arg0 + offset, 0, 0);
			Log.d(TAG, "titleWidth * currIndex + offset = " + (titleWidth * currIndex + offset));
			Log.d(TAG, "titleWidth * arg0 + offset = " + (titleWidth * arg0 + offset));
			currIndex = arg0;
			Log.d(TAG, "currIndex = " + currIndex);
			animation.setFillAfter(true);
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

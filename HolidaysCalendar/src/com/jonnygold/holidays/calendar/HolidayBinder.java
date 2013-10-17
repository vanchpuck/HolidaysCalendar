package com.jonnygold.holidays.calendar;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import com.jonnygold.holidays.calendar.R;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;





public class HolidayBinder implements ViewBinder {
	//Typeface tf = Typeface.createFromAsset(getAssets(),"sansBold.ttf");
	FlagPanel flags;
	//int[] flagArr = new int[3];
	int flag = -1;
	Resources res;
	
	public HolidayBinder(FlagPanel flags, Resources res){
		this.flags = flags;
		this.res = res;
	}
	
	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		//Log.w("setViewValue", "START!!!");
		//Log.w("setViewValue", columnIndex+"");
		//Log.w("setViewValue", view.getId()+"");
		//Log.w("setViewValue", R.id.ivImg+"");
		
		if(view.getId() == R.id.tvText){
			TextView titleView = ((TextView)view);
			//titleView.setTypeface(tf);
			String text = cursor.getString(0);
			text = text.toUpperCase();
			
			//Log.w("setViewValue", "st_1 = " + titleView.getText());
			if(text.length() > 47){
				titleView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 16);
				text = text.subSequence(0, 47) + "...";
				titleView.setText(text);
			}
			titleView.setText(text);
			//titleView = null;
			return true;
		}
		if(view.getId() == R.id.ivImg){
			ByteArrayInputStream inputStream = new ByteArrayInputStream(cursor.getBlob(columnIndex));
			Drawable icon = new BitmapDrawable(BitmapFactory.decodeStream(inputStream)); 
			try {
				inputStream.close();
			} catch (IOException e) {
				Log.w("setViewValue", "Ошибка закрытия потока");
				e.printStackTrace();
			}
			//icon = BitmapFactory.decodeStream(inputStream); 
			((ImageView)view).setImageDrawable(icon);
			int h = ((ImageView)view).getDrawable().getIntrinsicHeight();
			res.getDrawable(R.drawable.border).setBounds(0, 0, 133, 133);
			Log.w("setViewValue", "H = "+h);
			icon = null;
			return true; //true because the data was bound to the view
	    }
		if(view.getId() == R.id.flag_img_1){
			if (((ImageView)view).getDrawable() == null){
				Log.w("FlagView!!!!", "Выбор флага для id: "+cursor.getInt(columnIndex));
				//flagArr[0] = -0;
				try {
					flag = flags.getFlag(cursor.getInt(columnIndex), 0);
					Log.w("Flag_1", flag+"");
					((ImageView)view).setImageDrawable(res.getDrawable(flag));
					//((ImageView)view).setImageDrawable(res.getDrawable(R.drawable.russia_circle_s));
				} catch (Exception e) {
					((ImageView)view).setImageDrawable(res.getDrawable(R.drawable.black_circle_s));
				}
			}
			return true;
		}
		if(view.getId() == R.id.flag_img_2){
			if (((ImageView)view).getDrawable() == null){
				Log.w("FlagView!!!!", "Выбор флага для id: "+cursor.getInt(columnIndex));
				//flagArr[1] = -0;
				try {
					flag = flags.getFlag(cursor.getInt(columnIndex), 1);
					Log.w("Flag_2", flag+"");
					((ImageView)view).setImageDrawable(res.getDrawable(flag));
					//((ImageView)view).setImageDrawable(res.getDrawable(R.drawable.russia_circle_s));
				} catch (Exception e) {
					((ImageView)view).setImageDrawable(res.getDrawable(R.drawable.black_circle_s));
				}
			}
			return true;
		}
		if(view.getId() == R.id.flag_img_3){
			if (((ImageView)view).getDrawable() == null){
				Log.w("FlagView!!!!", "Выбор флага для id: "+cursor.getInt(columnIndex));
				//flagArr[2] = -0;
				try {
					flag = flags.getFlag(cursor.getInt(columnIndex), 2);
					Log.w("Flag_3", flag+"");
					((ImageView)view).setImageDrawable(res.getDrawable(flag));
					//((ImageView)view).setImageDrawable(res.getDrawable(R.drawable.russia_circle_s));
				} catch (Exception e) {
					((ImageView)view).setImageDrawable(res.getDrawable(R.drawable.black_circle_s));
				}
			}
			return true;
		}
		//Arrays.fill(flagArr, 0);
		return false;
	}

}

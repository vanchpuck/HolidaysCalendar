package com.jonnygold.holidays.calendar;

import java.io.ByteArrayInputStream;

import com.jonnygold.holidays.calendar.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.SpannableString;

public class DetailActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.w("DetailActivity", "start");
        String text = getIntent().getExtras().getString("text");
        String title = getIntent().getExtras().getString("title");
        String actDate = getIntent().getExtras().getString("actDate");
        String holidayTipe = getIntent().getExtras().getString("holidayTipe");
        Log.w("DetailActivity", "messageView " + text);        
        
        TextView titleDate = (TextView) findViewById(R.id.holiday_info);
        //titleDate.setTypeface(Typeface.createFromAsset(getAssets(),"sans.ttf"));
        titleDate.setText(actDate + " - " + holidayTipe);
        
        TextView titleView = (TextView) findViewById(R.id.holiday_title);
        //titleView.setTypeface(Typeface.createFromAsset(getAssets(),"sansBold.ttf"));
        titleView.setText(title.toUpperCase() + "");
        
        TextView textView = (TextView) findViewById(R.id.holiday_text);
        //textView.setTypeface(Typeface.createFromAsset(getAssets(),"sans.ttf"));
        textView.setText(text + "");
        
        //Установка флагов из CommonResource
        try{
	        ImageView flagImg_1 = (ImageView)findViewById(R.id.flag_img_1);
	        flagImg_1.setImageDrawable(getResources().getDrawable(getIntent().getExtras().getInt("flagId_1")));
		} catch (Exception e) {}
		
        try{
	        ImageView flagImg_2 = (ImageView)findViewById(R.id.flag_img_2);
	        flagImg_2.setImageDrawable(getResources().getDrawable(getIntent().getExtras().getInt("flagId_2")));
		} catch (Exception e) {}
		
        try{
	        ImageView flagImg_3 = (ImageView)findViewById(R.id.flag_img_3);
	        flagImg_3.setImageDrawable(getResources().getDrawable(getIntent().getExtras().getInt("flagId_3")));
    	} catch (Exception e) {}
        
        ImageView icon =  (ImageView) findViewById(R.id.holiday_icon);
        
        //icon.setImageBitmap(CommonResources.bitmap_2);
        //icon.setImageDrawable(CommonResources.drawable_1);
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream( (byte[])getIntent().getExtras().get("img"));
        icon.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeStream(inputStream))); 
    }

	public void onStop(){
    	super.onStop();
	}


    
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    
    private void setData(String text){
    	
    	

        // Получаем иконку и ее ширину
        //Drawable dIcon = getResources().getDrawable(R.drawable.icon);
//        int leftMargin = image.getIntrinsicWidth() + 10;
//
//        // Устанавливаем иконку в R.id.icon
//        ImageView icon = (ImageView) findViewById(R.id.icon);
//        icon.setBackgroundDrawable(image);
//
//        SpannableString ss = new SpannableString(text);
//        // Выставляем отступ для первых трех строк абазца
//        ss.setSpan(new LeadingMargin(4, leftMargin), 0, ss.length(), 0);
//
//        TextView messageView = (TextView) findViewById(R.id.message_view);
//        messageView.setText(ss);
    	
    	
    	
    }

}

package com.jonnygold.holidays.calendar;



import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;

import com.jonnygold.holidays.calendar.R;




import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class HolidaysWidget_4x1 extends AppWidgetProvider{
	
	public static ListView hListView;
	SimpleCursorAdapter hAdapter;
	Cursor holidays, countries, preferences;
	
	DataBaseHelper db;
	
	Date currDate;
	
	String msg;
	
	int backColor;
	int textColor;
	
	int chk_bel=0;
	int chk_rus=0;
	int chk_ukr=0;
	int chk_wrld = 1;
	
	int id;
	
	
//	public HolidaysWidget_4x1(ListView hListView){
//		this.hListView = hListView;
//	}
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		try{
//		boolean[] data = null;
//		for(int i=0;i<appWidgetIds.length;i++){
//			Log.w("WIDGET_ID_1", appWidgetIds[i]+"");
//			try{
//			data = preferences.getWidgetData(appWidgetIds[i]);
//			chk_rus = data[0];
//			chk_bel = data[1];
//			chk_ukr = data[2];
//			chk_wrld = data[3];
//			}catch(Exception ex){}
//		}
		
		
		Log.w("!!!TEST!!!", "------");		
		Log.w("!!!TEST!!!", chk_rus+"");
		Log.w("!!!TEST!!!", chk_bel+"");
		Log.w("!!!TEST!!!", chk_ukr+"");
		Log.w("!!!TEST!!!", chk_wrld+"");
		Log.w("!!!TEST!!!", "------");
		//appWidgetManager.getAppWidgetInfo(appWidgetId{0]).
		
		RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
		/*
		GradientDrawable dr = (GradientDrawable)context.getResources().getDrawable(R.drawable.widget_style_1);
		((GradientDrawable)context.getResources().getDrawable(R.drawable.widget_style_1)).setAlpha(155);
		((GradientDrawable)context.getResources().getDrawable(R.drawable.widget_style_1)).setStroke(90, 0);
		((GradientDrawable)context.getResources().getDrawable(R.drawable.widget_style_1)).setColor(backColor);
		((GradientDrawable)context.getResources().getDrawable(R.drawable.widget_style_1)).setCornerRadius(10);
		*/
		
		//updateViews.setInt(R.id.widget_container_1, "setBackgroundColor", context.getResources().getColor(R.color.widget_back));
		//updateViews.setFloat(R.id.date_1, "setAlpha", (float)0.5);
		
		/*
		updateViews.setTextColor(R.id.date_1, textColor);
		updateViews.setTextColor(R.id.date_2, textColor);
		updateViews.setTextColor(R.id.date_3, textColor);
		updateViews.setTextColor(R.id.date_4, textColor);
		*/         
		//appWidgetManager.updateAppWidget(appWidgetIds, updateViews);
		
		DecimalFormat formatter = new DecimalFormat("00");
		
		currDate = new Date(Calendar.getInstance());
    
		db = new DataBaseHelper(context);
		
		db.openDataBase();
		
		preferences = db.getWidgetPreferences();
		preferences.moveToFirst();
		chk_rus = preferences.getInt(1);
		chk_bel = preferences.getInt(2);
		chk_ukr = preferences.getInt(3);
		chk_wrld = preferences.getInt(4);
		preferences.close();
//		Log.w("!!!TEST2!!!", "------");		
//		Log.w("!!!TEST2!!!", chk_rus+"");
//		Log.w("!!!TEST2!!!", chk_bel+"");
//		Log.w("!!!TEST2!!!", chk_ukr+"");
//		Log.w("!!!TEST2!!!", chk_wrld+"");
//		Log.w("!!!TEST2!!!", "------");
		
		for (int i=0; i<appWidgetIds.length; i++) {
			
            //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            
            Intent activityIntent = new Intent(context, MainActivity.class);
           
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0/*PendingIntent.FLAG_UPDATE_CURRENT*/);
            
            updateViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
                        
            appWidgetManager.updateAppWidget(appWidgetIds[i], updateViews);      
            
        }
		
		
        // Для начала получим названия праздников
		holidays = db.getWidgetData(
				4, 
				currDate.getMonth(), 
				currDate.getDay(),
				chk_rus,
				chk_bel,
				chk_ukr,
				chk_wrld
		);
        
		//Очистим панель флагов
        updateViews.setImageViewResource(R.id.flag_11, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_12, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_13, getFlagResource(R.drawable.black_circle_s));
        
        updateViews.setImageViewResource(R.id.flag_21, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_22, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_23, getFlagResource(R.drawable.black_circle_s));
        
        updateViews.setImageViewResource(R.id.flag_31, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_32, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_33, getFlagResource(R.drawable.black_circle_s));
        
        updateViews.setImageViewResource(R.id.flag_41, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_42, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_43, getFlagResource(R.drawable.black_circle_s));
		
        // Для промежуточного хранения
        String text; 
        
        // Максимальная отображаемая длина названия праздника
        int textLen = 30;
        
        //Заголовок
        updateViews.setTextViewText(R.id.curr_date, "  "+currDate.getDateStr()+" ");
        
        // 1-й праздник
        holidays.moveToFirst();
        
        countries = db.getHolidayCountry(holidays.getInt(1), chk_rus, chk_bel, chk_ukr, chk_wrld); 
        try{
	        countries.moveToFirst();
	        updateViews.setImageViewResource(R.id.flag_11, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_12, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_13, getFlagResource(countries.getInt(0)));
	        textLen--;
		} catch (Exception ex) {}
        
        if(holidays.getString(0).length()>textLen)
        	text = holidays.getString(0).substring(0, textLen).toUpperCase()+"...";
        else
        	text = holidays.getString(0).toUpperCase();
        updateViews.setTextViewText(R.id.date_1, formatter.format(holidays.getInt(2))+"."+formatter.format(holidays.getInt(3)+1)+" - ");
        updateViews.setTextViewText(R.id.text_1, text);
        
        textLen = 30;
        
        // 2-й праздник
        holidays.moveToNext();
        
        countries = db.getHolidayCountry(holidays.getInt(1), chk_rus, chk_bel, chk_ukr, chk_wrld); 
        try{
			countries.moveToFirst();
	        updateViews.setImageViewResource(R.id.flag_21, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_22, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_23, getFlagResource(countries.getInt(0)));
	        textLen--;
		} catch (Exception ex) {}
        
        if(holidays.getString(0).length()>textLen)
        	text = holidays.getString(0).substring(0, textLen).toUpperCase()+"...";
        else
        	text = holidays.getString(0).toUpperCase();
        updateViews.setTextViewText(R.id.date_2, formatter.format(holidays.getInt(2))+"."+formatter.format(holidays.getInt(3)+1)+" - ");
        updateViews.setTextViewText(R.id.text_2, text);
		
        textLen = 30;
        
		// 3-й праздник
		holidays.moveToNext();
		
		countries = db.getHolidayCountry(holidays.getInt(1), chk_rus, chk_bel, chk_ukr, chk_wrld); 
		try{
			countries.moveToFirst();
	        updateViews.setImageViewResource(R.id.flag_31, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_32, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_33, getFlagResource(countries.getInt(0)));
	        textLen--;
		} catch (Exception ex) {}
		
		if(holidays.getString(0).length()>textLen)
        	text = holidays.getString(0).substring(0, textLen).toUpperCase()+"...";
        else
        	text = holidays.getString(0).toUpperCase();
		
		updateViews.setTextViewText(R.id.date_3, formatter.format(holidays.getInt(2))+"."+formatter.format(holidays.getInt(3)+1)+" - ");
		
		updateViews.setTextViewText(R.id.text_3, text);
        
		textLen = 30;
        
		// 4-й праздник
		holidays.moveToNext();
		
		countries = db.getHolidayCountry(holidays.getInt(1), chk_rus, chk_bel, chk_ukr, chk_wrld); 
		try{
	        countries.moveToFirst();
	        updateViews.setImageViewResource(R.id.flag_41, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_42, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_43, getFlagResource(countries.getInt(0)));
	        textLen--;
		} catch (Exception ex) {}
		
		if(holidays.getString(0).length()>textLen)
        	text = holidays.getString(0).substring(0, textLen).toUpperCase()+"...";
        else
        	text = holidays.getString(0).toUpperCase();
		updateViews.setTextViewText(R.id.date_4, formatter.format(holidays.getInt(2))+"."+formatter.format(holidays.getInt(3)+1)+" - ");
        updateViews.setTextViewText(R.id.text_4, text);
        
        
        appWidgetManager.updateAppWidget(appWidgetIds, updateViews);
  
        holidays.close();
        countries.close();
        db.close();
        
        
        super.onUpdate(context, appWidgetManager, appWidgetIds);
		}catch(Exception ex){}
	}
	
//	public void onReceive (Context context, Intent intent){
//		Log.w("ON_RECEIVE", intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)+"");
//		
////		if(intent.getExtras().containsKey("chk_bel")) {chk_bel = intent.getExtras().getBoolean("chk_bel");Log.w("ON_RECEIVE", "BEL");}
////		if(intent.getExtras().containsKey("chk_rus")) {chk_rus = intent.getExtras().getBoolean("chk_rus");Log.w("ON_RECEIVE", "RUS");}
////		if(intent.getExtras().containsKey("chk_ukr")) {chk_ukr = intent.getExtras().getBoolean("chk_ukr");Log.w("ON_RECEIVE", "UKR");}
////		if(intent.getExtras().containsKey("chk_wrld")) {chk_wrld = intent.getExtras().getBoolean("chk_wrld");Log.w("ON_RECEIVE", "WRL");}
//		
//		if(intent != null){
//			//int id = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
//			try{
//				Log.w("WIDGET_NEW", intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)+"");
////				if(intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID) != 0){
////				db = new DataBaseHelper(context);
////				db.openDataBase();
////				db.saveWidgetPreferences(
////						intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID), 
////						intent.getExtras().getInt("chk_rus"), 
////						intent.getExtras().getInt("chk_bel"), 
////						intent.getExtras().getInt("chk_ukr"), 
////						intent.getExtras().getInt("chk_wrld")
////				);
////				db.close();
////				}
//			}catch(Exception exc){}
////			chk_bel = intent.getExtras().getBoolean("chk_bel");//getBooleanExtra("chk_bel", true);
////			chk_rus = intent.getBooleanExtra("chk_rus", true);
////			chk_ukr = intent.getBooleanExtra("chk_ukr", true);
////			chk_wrld = intent.getBooleanExtra("chk_wrld", true);
//		}
//		
////		backColor = intent.getIntExtra("backColor", 0);
////		textColor = intent.getIntExtra("textColor", 0);
//		
//		
//		super.onReceive(context, intent);
//	}
	
	protected int getFlagResource(int id_country){
		switch(id_country){
			case 1: return R.drawable.earth_s_1;
			case 2: return R.drawable.russia_circle_s_1;
			case 3: return R.drawable.bel_circle_s_1;
			case 4: return R.drawable.ukrane_circle_s_1;
			default: return R.drawable.black_circle_s;
		}
	}
}

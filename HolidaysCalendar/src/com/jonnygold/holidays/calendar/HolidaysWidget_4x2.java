package com.jonnygold.holidays.calendar;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SimpleCursorAdapter;

public class HolidaysWidget_4x2 extends AppWidgetProvider {
	
	public static ListView hListView;
	SimpleCursorAdapter hAdapter;
	Cursor holidays, countries, preferences;
	DataBaseHelper db;
	Date currDate;
	
	String msg;
	
	int backColor;
	int textColor;
	
	int chk_bel;
	int chk_rus;
	int chk_ukr;
	int chk_wrld;
	
	int id;
	
	
//	public HolidaysWidget_4x1(ListView hListView){
//		this.hListView = hListView;
//	}
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		try{		
		//Log.w("!!!TEST!!!", getResultExtras(true).getInt("test")+"");
		
		RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_4x2);
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
		
        // ƒл€ начала получим названи€ праздников
		holidays = db.getWidgetData(
				8, 
				currDate.getMonth(), 
				currDate.getDay(),
				chk_rus,
				chk_bel,
				chk_ukr,
				chk_wrld
		);
        
		//ќчистим панель флагов
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
        
        updateViews.setImageViewResource(R.id.flag_51, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_52, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_53, getFlagResource(R.drawable.black_circle_s));
        
        updateViews.setImageViewResource(R.id.flag_61, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_62, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_63, getFlagResource(R.drawable.black_circle_s));
        
        updateViews.setImageViewResource(R.id.flag_71, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_72, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_73, getFlagResource(R.drawable.black_circle_s));
        
        updateViews.setImageViewResource(R.id.flag_81, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_82, getFlagResource(R.drawable.black_circle_s));
        updateViews.setImageViewResource(R.id.flag_83, getFlagResource(R.drawable.black_circle_s));
		
        // ƒл€ промежуточного хранени€
        String text; 
        
        // ћаксимальна€ отображаема€ длина названи€ праздника
        int textLen = 30;
        
        //«аголовок
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
        
        textLen = 30;
        
		// 5-й праздник
		holidays.moveToNext();
		
		countries = db.getHolidayCountry(holidays.getInt(1), chk_rus, chk_bel, chk_ukr, chk_wrld); 
		try{
	        countries.moveToFirst();
	        updateViews.setImageViewResource(R.id.flag_51, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_52, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_53, getFlagResource(countries.getInt(0)));
	        textLen--;
		} catch (Exception ex) {}
		
		if(holidays.getString(0).length()>textLen)
        	text = holidays.getString(0).substring(0, textLen).toUpperCase()+"...";
        else
        	text = holidays.getString(0).toUpperCase();
		updateViews.setTextViewText(R.id.date_5, formatter.format(holidays.getInt(2))+"."+formatter.format(holidays.getInt(3)+1)+" - ");
        updateViews.setTextViewText(R.id.text_5, text);
        
        textLen = 30;
        
		// 6-й праздник
		holidays.moveToNext();
		
		countries = db.getHolidayCountry(holidays.getInt(1), chk_rus, chk_bel, chk_ukr, chk_wrld); 
		try{
	        countries.moveToFirst();
	        updateViews.setImageViewResource(R.id.flag_61, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_62, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_63, getFlagResource(countries.getInt(0)));
	        textLen--;
		} catch (Exception ex) {}
		
		if(holidays.getString(0).length()>textLen)
        	text = holidays.getString(0).substring(0, textLen).toUpperCase()+"...";
        else
        	text = holidays.getString(0).toUpperCase();
		updateViews.setTextViewText(R.id.date_6, formatter.format(holidays.getInt(2))+"."+formatter.format(holidays.getInt(3)+1)+" - ");
        updateViews.setTextViewText(R.id.text_6, text);
        
        textLen = 30;
        
		// 7-й праздник
		holidays.moveToNext();
		
		countries = db.getHolidayCountry(holidays.getInt(1), chk_rus, chk_bel, chk_ukr, chk_wrld); 
		try{
	        countries.moveToFirst();
	        updateViews.setImageViewResource(R.id.flag_71, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_72, getFlagResource(countries.getInt(0)));
	        textLen--;
	        countries.moveToNext();
	        updateViews.setImageViewResource(R.id.flag_73, getFlagResource(countries.getInt(0)));
	        textLen--;
		} catch (Exception ex) {}
		
		if(holidays.getString(0).length()>textLen)
        	text = holidays.getString(0).substring(0, textLen).toUpperCase()+"...";
        else
        	text = holidays.getString(0).toUpperCase();
		updateViews.setTextViewText(R.id.date_7, formatter.format(holidays.getInt(2))+"."+formatter.format(holidays.getInt(3)+1)+" - ");
        updateViews.setTextViewText(R.id.text_7, text);
        
        // 8-й праздник
		holidays.moveToNext();
		
		countries = db.getHolidayCountry(holidays.getInt(1), chk_rus, chk_bel, chk_ukr, chk_wrld); 
		try{
		    countries.moveToFirst();
		    updateViews.setImageViewResource(R.id.flag_81, getFlagResource(countries.getInt(0)));
		    textLen--;
		    countries.moveToNext();
		    updateViews.setImageViewResource(R.id.flag_82, getFlagResource(countries.getInt(0)));
		    textLen--;
		    countries.moveToNext();
		    updateViews.setImageViewResource(R.id.flag_83, getFlagResource(countries.getInt(0)));
		    textLen--;
		} catch (Exception ex) {}
		
		if(holidays.getString(0).length()>textLen)
		 	text = holidays.getString(0).substring(0, textLen).toUpperCase()+"...";
		else
		 	text = holidays.getString(0).toUpperCase();
		updateViews.setTextViewText(R.id.date_8, formatter.format(holidays.getInt(2))+"."+formatter.format(holidays.getInt(3)+1)+" - ");
		updateViews.setTextViewText(R.id.text_8, text);
        
        appWidgetManager.updateAppWidget(appWidgetIds, updateViews);
  
        holidays.close();
        countries.close();
        preferences.close();
        db.close();
//        Intent theActivity = new Intent(Intent.ACTION_MAIN)
//        .addCategory(Intent.CATEGORY_LAUNCHER)
//        .setComponent(new ComponentName("com.jonnygold.holidyas.calendar", "com.jonnygold.holidyas.calendar.MainActivity"));
//        theActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(theActivity);
        
     // Perform this loop procedure for each App Widget that belongs to this provider
        
        for (int i=0; i<appWidgetIds.length; i++) {
        	
            //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            
            Intent activityIntent = new Intent(context, MainActivity.class);
            
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            
            updateViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
            //if(idWidget == appWidgetIds[i]){
            appWidgetManager.updateAppWidget(appWidgetIds[i], updateViews);      
            //}
        }
        
        
        super.onUpdate(context, appWidgetManager, appWidgetIds);
		}catch(Exception ex){}
	}
	
	public void onReceive (Context context, Intent intent){
		
		//intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
//		if(intent != null){
//			chk_bel = intent.getBooleanExtra("chk_bel", true);
//			chk_rus = intent.getBooleanExtra("chk_rus", true);
//			chk_ukr = intent.getBooleanExtra("chk_ukr", true);
//			chk_wrld = intent.getBooleanExtra("chk_wrld", true);
//		}
		
//		backColor = intent.getIntExtra("backColor", 0);
//		textColor = intent.getIntExtra("textColor", 0);
		
		
		super.onReceive(context, intent);
	}
	
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


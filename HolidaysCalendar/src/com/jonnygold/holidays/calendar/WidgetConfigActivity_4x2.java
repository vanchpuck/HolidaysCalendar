package com.jonnygold.holidays.calendar;

import java.io.IOException;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class WidgetConfigActivity_4x2 extends Activity /*implements ColorPickerDialog.OnColorChangedListener*/ {

	int mAppWidgetId = 0;
	
	int target;
	
	int backColor;
	int textColor;
	
	DataBaseHelper db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //findViewById(R.id.button_ok).setBackgroundColor(-14475085);
        
        
        setContentView(R.layout.activity_widget_config);
        
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        
    }

    
    public void startWidget(View view){
    	try{
    	if( 
    			!((CheckBox)findViewById(R.id.chk_bel)).isChecked() &&
    			!((CheckBox)findViewById(R.id.chk_rus)).isChecked() &&
    			!((CheckBox)findViewById(R.id.chk_ukr)).isChecked() &&
    			!((CheckBox)findViewById(R.id.chk_wrld)).isChecked() 
    	){
    		Toast.makeText(this, "Не выбрана ни одна категория", Toast.LENGTH_LONG).show();
    		return;
    	}
    	
    	//AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        
        //RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.widget);
    	
        //Intent resultValue = new Intent();
        //resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        //setResult(RESULT_OK, resultValue);
        
        Intent intent = new Intent(this,HolidaysWidget_4x2.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = {mAppWidgetId};//AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), HolidaysWidget_4x1.class));
        
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
//        intent.putExtra("Test", "Test");
//        intent.putExtra("chk_bel", ((CheckBox)findViewById(R.id.chk_bel)).isChecked());
//        intent.putExtra("chk_rus", ((CheckBox)findViewById(R.id.chk_rus)).isChecked());
//        intent.putExtra("chk_ukr", ((CheckBox)findViewById(R.id.chk_ukr)).isChecked());
//        intent.putExtra("chk_wrld", ((CheckBox)findViewById(R.id.chk_wrld)).isChecked());
        //intent.putExtra("textColor", textColor);
        //intent.putExtra("backColor", backColor);
        
        db = new DataBaseHelper(this);
        
        db.openDataBase();
        Log.w("WIDGET_NEW!!!", mAppWidgetId+"");
        		
        db.saveWidgetPreferences(
				(int)(((CheckBox)findViewById(R.id.chk_rus)).isChecked() == true ? 1 : 0), 
				(int)(((CheckBox)findViewById(R.id.chk_bel)).isChecked() == true ? 1 : 0), 
				(int)(((CheckBox)findViewById(R.id.chk_ukr)).isChecked() == true ? 1 : 0), 
				(int)(((CheckBox)findViewById(R.id.chk_wrld)).isChecked() == true ? 1 : 0)
		);
        db.close();
//        Log.w("WIDGET_DATA!!!", (int)(((CheckBox)findViewById(R.id.chk_rus)).isChecked() == true ? 1 : 0)+"");
//        Log.w("WIDGET_DATA!!!", (int)(((CheckBox)findViewById(R.id.chk_bel)).isChecked() == true ? 1 : 0)+"");
//        Log.w("WIDGET_DATA!!!", (int)(((CheckBox)findViewById(R.id.chk_ukr)).isChecked() == true ? 1 : 0)+"");
//        Log.w("WIDGET_DATA!!!", (int)(((CheckBox)findViewById(R.id.chk_wrld)).isChecked() == true ? 1 : 0)+"");
        
        //ComponentName thisWidget = new ComponentName(this, HolidaysWidget_4x1.class);
        
        //appWidgetManager.updateAppWidget(thisWidget, views);
        
        setResult(RESULT_OK, intent);
        
        finish();
        sendBroadcast(intent);
    	}catch(Exception ex){Toast.makeText(this, "Не создана база праздников. Запутите главное окно программы и после этого повторите попытку.", Toast.LENGTH_LONG).show();}
    }

//    public boolean onBackColorClick(View view){
//    	Log.w("COLOR", "WORKING");
//    	Log.w("COLOR", getResources().getColor(R.color.white)+"");
//        target = 0;
//    	new ColorPickerDialog(this, this, "key", getResources().getColor(R.color.blue_title), getResources().getColor(R.color.white)).show();
//        return true;
//    }
//    
//    public boolean onTextColorClick(View view){
//    	Log.w("COLOR", "WORKING");
//    	Log.w("COLOR", getResources().getColor(R.color.white)+"");
//        target = 1;
//    	new ColorPickerDialog(this, this, "key", getResources().getColor(R.color.blue_title), getResources().getColor(R.color.white)).show();
//        return true;
//    }
//
//    
//	@Override
//	public void colorChanged(String key, int color) {
//		Log.w("COLOR", color+"");
//		Log.w("KEY", key+"");
//		if(target == 0){
//			findViewById(R.id.button_ok).setBackgroundColor(color);
//			backColor = color;
//		}
//		else{
//			Button b = (Button)findViewById(R.id.button_ok);
//			b.setTextColor(color);
//			textColor = color;
//		}
//	}
}

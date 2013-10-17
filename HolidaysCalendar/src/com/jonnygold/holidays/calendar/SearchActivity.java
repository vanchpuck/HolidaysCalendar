package com.jonnygold.holidays.calendar;

//import java.io.IOException;

import com.jonnygold.holidays.calendar.R;

//import com.example.androidapp.DisplayMessageActivity;
//import com.example.androidapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class SearchActivity extends Activity {
	
	DataBaseHelper db;
	Cursor cursor;
	FlagPanel flags;
	SimpleCursorAdapter scAdapter;
	ListView holidays;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        db = new DataBaseHelper(this);
        
        db.openDataBase();
    }

    
    @Override
    public void onStop(){
    	if (flags != null){
    		flags.close();
    	}
    	if (cursor != null){
    		cursor.close();
    	}
    	db.close();
    	super.onStop();
    }
    
    public void onResume(){
    	//Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
    	//Calendar caledar = Calendar.getInstance();
    	db.openDataBase();
    	super.onResume();
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
    
    public void findHoliday(View view){
        EditText textField = (EditText) findViewById(R.id.search_text);
        String key = textField.getText().toString().toLowerCase();
        if (key.length() > 0){
	        flags = new FlagPanel(db);
	        
	        String[] from = new String[] { "image", "hTitle", "id_holiday", "id_holiday", "id_holiday" };
	        int[] to = new int[] { R.id.ivImg, R.id.tvText, R.id.flag_img_1, R.id.flag_img_2, R.id.flag_img_3 };
	        
	        cursor = db.findHolidaysByStr(key);
	        
	        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
	        scAdapter.setViewBinder(new HolidayBinder(flags, getResources()));

	    	
	    	
	        holidays = (ListView) findViewById(R.id.search_list);
	        
	        holidays.setAdapter(scAdapter);
	        
	        holidays.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
					//Intent intent = new Intent(this, DetailActivity.class);
					getDetailActivity((int)position);
				}
			});
	        
	        Toast toast = Toast.makeText(this, "Список содержит первые 6 записей результата запроса", Toast.LENGTH_LONG);
	        toast.setGravity(Gravity.CLIP_HORIZONTAL, 0, 0);
	        toast.show();
        }
        
    }

    private void getDetailActivity(int position){
    	//Cursor c = scAdapter.getCursor();
    	//Object a = scAdapter.getItem(0);
    	//Log.w("Проверка класса", a.getClass().getName());
    	Date today = Date.getToday();
    	int year = today.getYear();
    	if(cursor.getInt(6) < today.getMonth() || (cursor.getInt(6) == today.getMonth() && cursor.getInt(3) < today.getDay())){
    		year = today.getYear()+1;
    	}
    		
    	String msg = "Ближайшая дата - "+Date.getDateStr(cursor.getInt(3), cursor.getInt(6), year);
    	Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    	Intent intent = new Intent(this, DetailActivity.class);
    	Log.w("Вызов", "moveToPosition");
    	cursor.moveToPosition(position);
    	
    	//передаем картинку
    	byte[] imgData = cursor.getBlob(8);
    	intent.putExtra("img", imgData);    	
    	
    	String title = cursor.getString(0);
    	String text = cursor.getString(1);
    	String actDate = cursor.getString(2);
    	String holidayType = cursor.getString(4);
    	
    	intent.putExtra("title", title);
    	intent.putExtra("text", text);
    	intent.putExtra("actDate", actDate);
    	intent.putExtra("holidayTipe", holidayType);
    	
    	//Передаем флаги
    	try {
			intent.putExtra("flagId_1", flags.getFlag(cursor.getInt(5), 0));
		} catch (Exception e1) {}
    	try {
			intent.putExtra("flagId_2", flags.getFlag(cursor.getInt(5), 1));
		} catch (Exception e1) {}
    	try {
			intent.putExtra("flagId_3", flags.getFlag(cursor.getInt(5), 2));
		} catch (Exception e1) {}
    	//flags.close();
    	    	
    	Log.w("Вызов", "startActivity");
    	title = null;
    	text = null;
    	actDate = null;
    	holidayType = null;
    	startActivity(intent);
    }
}

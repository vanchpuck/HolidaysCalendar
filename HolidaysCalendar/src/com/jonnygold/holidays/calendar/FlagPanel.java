package com.jonnygold.holidays.calendar;

import com.jonnygold.holidays.calendar.R;

import android.database.Cursor;
import android.util.Log;

class FlagPanel{
	private DataBaseHelper db;
	protected int id_holiday;
	public Cursor countries;
	
	public FlagPanel (DataBaseHelper db){
		this.db = db;
	}
	
	protected void getCountries(int id_holiday){
		this.id_holiday = id_holiday;
		//db.openDataBase();
		Log.w("Flags", "Выборка из БД");
		countries = db.getHolidayCountry(id_holiday, 0, 0, 0, 0);
		//db.close();
	}
	
	public int getCountryId(int id_holiday, int order) throws Exception{
		if(id_holiday != this.id_holiday){
			getCountries(id_holiday);
		}
		
		
		//try{
			//countries.moveToFirst();
		countries.moveToPosition(order);
		Log.w("Flags", "Флаг: "+countries.getInt(0));
////		}catch(CursorIndexOutOfBoundsException ex){
////			Log.w("Flags", "Exception");
////			return -1;
////		}
		return countries.getInt(0);
	}
	
	public int getFlag(int id_holiday, int order) throws Exception{    
		int flag;
		switch(getCountryId(id_holiday, order)){
			case 2: flag = R.drawable.russia_circle_s; break;
			case 3: flag = R.drawable.bel_circle_s; break;
			case 4: flag = R.drawable.ukrane_circle_s; break;
			default: flag = R.drawable.earth_s; break; //getResources().getDrawable(R.drawable.earth_s); break;
		}
		Log.w("Flags", "Флаг выбран!");
		return flag;
	}
	
	public void close(){
		try{
			countries.close();
			db.close();
		}catch(Exception ex){};
	}
}

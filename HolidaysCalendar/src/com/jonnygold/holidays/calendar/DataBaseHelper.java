package com.jonnygold.holidays.calendar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
//import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.jonnygold.holidays.calendar/databases/";
 
    private static String DB_NAME = "mydb";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 11);
        this.myContext = context;
//        try {
//			copyDataBase();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase(int version) throws IOException{
    	Log.w("Create", "Создание базы");
    	boolean dbExist = checkDataBase(version);
    	//boolean dbExist = false;
 
    	if(dbExist){
    		Log.w("Create", "База существует");
    		//do nothing - database already exist
    	}else{
    		Log.w("Create", "База не существует");
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
        		Log.w("Create", "Копирование базы");
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
        	//openDataBase();
        	//Log.w("CreateDB", "Определение дат скользящих празников");
        	//updateMonthFloatDates();
        	//close();
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(int version){
    	 
    	SQLiteDatabase checkDB = null;
    	int check;
    	//Toast.makeText(myContext, "Try", Toast.LENGTH_LONG).show();
    	try{
    		
    		String myPath = DB_PATH + DB_NAME;
    		Log.w("Check", myPath);
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    		String query = 
    				"select count(_id) from t_images where _id = 159" 
    		;		
    		Cursor cursor = checkDB.rawQuery(query, new String[]{});
    		cursor.moveToFirst();
    		check = cursor.getInt(0);
    		cursor.close();
    		checkDB.close();
    		if(check == 1) return true;
    		else return false;
    	}catch(SQLiteException e){
    		Toast.makeText(myContext, "Error", Toast.LENGTH_LONG).show();
    		Log.w("Check", "Ошибка при проверке");
    		return false;
    		//database does't exist yet.
 
    	}
 
//    	if(checkDB != null){
// 
//    		checkDB.close();
//    		//if (version < 2) return false;
//    		if(!versionTest()) return false;
//    	}
// 
//    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    public void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
    	Log.w("Opening", "Открытие базы");
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		Toast.makeText(myContext, "old: "+oldVersion+", new: "+newVersion, Toast.LENGTH_LONG).show();
//		String query = ""+
//		"CREATE TABLE t_images ( "+
//		"		_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
//		"		image BLOB(51200) NOT NULL, "+
//		"		description VARCHAR(32) "+
//		"	)"
//		;
//		db.execSQL(query);
//		query = "insert into t_images values(1,1,1,1,1)";
//		db.execSQL(query);
//		this.getReadableDatabase();
//		 
//    	try {
//    		Log.w("Create", "Копирование базы");
//			copyDataBase();
//
//		} catch (IOException e) {
//
//    		throw new Error("Error copying database");
//
//    	}
    	
	}
	
	public  Cursor getAllData(){
		String query = 
				"SELECT" 												+
				"		TH.title as hTitle" 							+
				"	,	TH.description as hDesc" 						+
				"	,	TH.actualDateStr" 								+
				"	,	TH.day" 										+
				"	,	TP.title"										+
				"	,	TI.*" 											+
				"FROM" 													+
				"		T_HOLIDAYS TH" 									+
				"	LEFT JOIN T_IMAGES TI ON TH.id_image = TI._id" 		+
				"	LEFT JOIN T_PRIORITY TP ON TH.id_priority = TP._id "
		;
		Log.w("QUERY", query);
		//String query = "SELECT * FROM t_holidays";
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		return cursor;
	}
	
	public  Cursor getDataOnDay(int month, int day){		
		String query = 
				"SELECT" 												+
				"		TH.title as hTitle" 							+
				"	,	TH.description as hDesc" 						+
				"	,	TH.actualDateStr" 								+
				"	,	TH.day" 										+
				"	,	TP.title" 										+
				"	,	TH._id as id_holiday"							+
				"	,	TI.*" 											+
				"FROM" 													+
				"		T_HOLIDAYS TH" 									+
				"	LEFT JOIN T_IMAGES TI ON TH.id_image = TI._id" 		+
				"	LEFT JOIN T_PRIORITY TP ON TH.id_priority = TP._id "+
				"WHERE " 												+
				"		TH.month = "+month+" " 							+
				"	AND TH.day = "+day+" " //+
//				"ORDER BY (" +
//				"		SELECT" +
//				"				count(id_country)" +
//				"		FROM" +
//				"				t_countryHolidays" +
//				"		WHERE" +
//				"				id_holiday = TH._id" +
//				") desc"
		;
		Log.w("QUERY", query);
		//String query = "SELECT * FROM t_holidays";
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		return cursor;
	}
	
	public Cursor findHolidaysByStr(String strKey){
		String query = 
				"SELECT" 												+
				"		TH.title as hTitle" 							+
				"	,	TH.description as hDesc" 						+
				"	,	TH.actualDateStr" 								+
				"	,	TH.day" 										+
				"	,	TP.title as pTitle" 							+
				"	,	TH._id as id_holiday"							+
				"	,	TH.month"							+
				"	,	TI.*" 											+
				"FROM" 													+
				"		T_HOLIDAYS TH" 									+
				"	LEFT JOIN T_IMAGES TI ON TH.id_image = TI._id" 		+
				"	LEFT JOIN T_PRIORITY TP ON TH.id_priority = TP._id "+
				"WHERE " 												+
				"		TH.title LIKE '%"+strKey+"%'" +
				"ORDER BY" +
				"		TH.month" +
				"	,	TH.day " +
				"LIMIT 6" //+
//				"ORDER BY (" +
//				"		SELECT" +
//				"				count(id_country)" +
//				"		FROM" +
//				"				t_countryHolidays" +
//				"		WHERE" +
//				"				id_holiday = TH._id" +
//				") desc"
		;
		Log.w("QUERY", query);
		Log.w("3", "11");
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		Log.w("3", "22");
		return cursor;
	}
	
	public Cursor getWidgetData(int numRows, int month, int day, int rus, int bel, int ukr, int wrld){
		boolean needJoin = false;
		String query = 
				"SELECT DISTINCT " 							+
				"		TH.title " 					+
				"	,	TH._id " 					+
				"	,	TH.day " 					+
				"	,	TH.month " 					
		;
		if(rus==1 || bel==1 || ukr==1 || wrld==1/*country != CommonResources.WORLD*/){
			needJoin = true;
			query += "" +
					//"	,	TCH.id_country "			+
					"FROM " 							+
					"		T_HOLIDAYS TH " 			+
					"	LEFT JOIN T_COUNTRYHOLIDAYS TCH ON TH._id = TCH.id_holiday " 		
			;
		}
		else{
			query += "" 								+
					"FROM " 							+
					"		T_HOLIDAYS TH " 		
			;
		}
		
		query += "" 								+				
				"WHERE " 							+
				"		date('now','start of year','+'||TH.month||' months','+'||TH.day||' days') >= date('now','start of year','+"+month+" months','+"+day+" days') "		
				//"	AND TH.day >= "+day+" "	
		;
		if(needJoin){
			query += "" +
					"	AND ( 1>2 ";
		}
		if(rus==1){
			query += "" +
					"	OR TCH.id_country = 2 ";
		}
		if(bel==1){
			query += "" +
					"	OR TCH.id_country = 3 ";
		}
		if(ukr==1){
			query += "" +
					"	OR TCH.id_country = 4 ";
		}
		if(wrld==1){
			query += "" +
					"	OR TCH.id_country = 1 ";
		}
		if(needJoin){
			query += "" +
					"	) ";
		}
		query += ""							+		
				"ORDER BY " 				+
				"		TH.month " 			+
				"	,	TH.day "				+	
				"LIMIT "+numRows
		;
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		
		Log.w("!!!QUERY!!!", query);
		
		return cursor;
	}
	
	public Cursor findHoliday(String hTitle){
		String query = 
				"SELECT" 												+
				"		TH.title as hTitle" 							+
				"	,	TH.description as hDesc" 						+
				"	,	TH.actualDateStr" 								+
				"	,	TI.*"											+
				"FROM" 													+
				"		T_HOLIDAYS TH" 									+
				"	LEFT JOIN T_IMAGES TI ON TH.id_image = TI._id"		+
				"WHERE" 												+
				"		TH.title = '" + hTitle + "'"									
		;		
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		return cursor;
	}
	
	public Cursor getHolidayCountry(int id_holiday, int rus, int bel, int ukr, int wrld){
		String query = 
				"SELECT " 							+
				"		id_country " 				+
				"FROM " 							+
				"		t_countryHolidays " 		+
				"WHERE " 							+
				"		id_holiday = "+id_holiday
		;	
		if(rus==1||bel==1||ukr==1||wrld==1){
			query += "" +
					"	AND ( 1>2 ";
		}
		if(rus==1){
			query += "" +
					"	OR id_country = 2 ";
		}
		if(bel==1){
			query += "" +
					"	OR id_country = 3 ";
		}
		if(ukr==1){
			query += "" +
					"	OR id_country = 4 ";
		}
		if(wrld==1){
			query += "" +
					"	OR id_country = 1 ";
		}
		if(rus==1||bel==1||ukr==1||wrld==1){
			query += "" +
					"	) ";
		}
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		return cursor;
	}
	
	public void updateYearFloatDates(int year){
		String query = 
				"UPDATE " 																				+
				"		t_holidays " 																	+
				"SET " 																					+
				"		day = ( " 																		+
				"				SELECT " 																+
				"						strftime('%d', date('"+year+"-01-01','+'||day||' days')) " 		+
				"				FROM " 																	+
				"						t_YearFloatHolidays " 											+
				"				WHERE " 																+
				"						id_holiday = t_holidays._id " 									+
				"		) " 																			+
				"WHERE " 																				+
				"		_id in ( " 																		+
				"				SELECT " 																+
				"						id_holiday " 													+
				"				FROM " 																	+
				"						t_YearFloatHolidays "											+
				"		) "
		;	
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		cursor.moveToFirst();
		cursor.close();
	}
	
	public void saveWidgetPreferences(int rus, int bel, int ukr, int wrld){
		String query = 
				"UPDATE " +
				"		t_widgetConfig " +
				"SET " +
				"		rus = " +rus+" "+
				"	,	bel = " +bel+" "+
				"	,	ukr = " +ukr+" "+
				"	,	wrld = " +wrld+" "+
				"WHERE " 			+
				"		_id = 1"
		;	
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		cursor.moveToFirst();
		cursor.close();
	}
	
	public Cursor getWidgetPreferences(){
		String query = 
				"SELECT " 							+
				"		* "							+
				"FROM " 							+
				"		t_widgetConfig " 			+
				"WHERE " 							+
				"		_id = 1"
		;		
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		return cursor;
	}
	
	public void updateEasterDate(int year){
		//Определим дату пасхи
		int day, month;		
        int a = year % 19;
        int b = year % 4;
        int c = year % 7;
        int d = (19 * a + 15) % 30;
        int e = (2*b + 4*c + 6*d + 6) % 7;        
        int marchDay = 22 + d + e;
        int aprilDay = d + e - 9;     
        //Log.w("marchDay", marchDay+"");
        //Log.w("aprilDay", aprilDay+"");
        if(marchDay < 31 && marchDay > 0 ){
        	day = marchDay;
        	month = 3;
        }
        //else if(aprilDay > 0 && aprilDay < 30){
        else{
        	day = aprilDay;
        	month = 4;
        }
        //else day = 0;
        Log.w("easter", year+"/"+month+"/"+day);
        DecimalFormat formatter = new DecimalFormat("00");
        //Обновим записи в базе для всех праздников, связанных с пасхой
        String query = 
				"UPDATE " 																	+
				"		t_holidays " 														+
				"SET " 																		+
				"		day = ( " 															+
				"				SELECT " 													+
				"						strftime('%d', date('"+year+"-"+formatter.format(month)+"-"+formatter.format(day)+"', ''||(13 + dayOffset)||' days')) "  +
				"				FROM " 														+
				"						t_easterHolidays " 									+
				"				WHERE " 													+
				"						id_holiday = t_holidays._id " 						+
				"		) " +
				"	,	month = ( " 														+
				"				SELECT " 													+
				"						strftime('%m', date('"+year+"-"+formatter.format(month)+"-"+formatter.format(day)+"', ''||(13 + dayOffset)||' days')) - 1 "  +
				"				FROM " 														+
				"						t_easterHolidays " 									+
				"				WHERE " 													+
				"						id_holiday = t_holidays._id " 						+
				"		) " 																+
				"WHERE " 																	+
				"		_id in ( " 															+
				"				SELECT " 													+
				"						id_holiday " 										+
				"				FROM " 														+
				"						t_easterHolidays "									+
				"		) "
		;
        Log.w("easter", query);
        //myDataBase.beginTransaction();
        //try {
        	Cursor cursor = myDataBase.rawQuery(query, new String[]{});
    		cursor.moveToFirst();
    		cursor.close();
    	//	myDataBase.setTransactionSuccessful();
        //} finally {
        //	myDataBase.endTransaction();
        //}

//        myDataBase.beginTransaction();
//        Cursor cursor = myDataBase.rawQuery(query, new String[]{});
//		cursor.moveToFirst();
//		cursor.close();
//		myDataBase.setTransactionSuccessful();
//		myDataBase.endTransaction();
	}
	
	public boolean versionTest(){
		myDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
		String query = 
				"SELECT" 												+
				"		count(*)"										+
				"FROM" 													+
				"		T_IMAGES "										+
				"WHERE" 												+
				"		_id = 150"									
		;		
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		cursor.moveToFirst();
		
		myDataBase.close();
		if(cursor.getInt(0) == 1) {
			cursor.close();
			return true;
		}
		else{
			cursor.close();
			return false;
		}
	}
	
	public void updateMonthFloatDates(int year){
		String query = 
				"UPDATE " 																																			+
				"		t_holidays " 																																+
				"SET " 																																				+
				"		day = ( " 																																	+
				"				SELECT " 																															+
				"						strftime('%d', date('"+year+"-01-01','+'||month||' months','weekday '||weekDay||'', ''||dayOffset||' days')) " 			+
				"				FROM " 																																+
				"						t_MonthFloatHolidays " 																										+
				"				WHERE " 																																+
				"						id_holiday = t_holidays._id " 																								+
				"		) " 																																			+
				"	,	month = ( " 																																	+
				"				SELECT " 																															+
				"						CASE WHEN dayOffset < 0 THEN month-1 " +
				"							WHEN dayOffset >= 0 THEN month" +
				"						END" 																													+
				"				FROM " 																																+
				"						t_MonthFloatHolidays " 																										+
				"				WHERE " 																																+
				"						id_holiday = t_holidays._id " 																								+
				"		) " +
				"WHERE " 																																			+
				"		_id in ( " 																																	+
				"				SELECT " 																															+
				"						id_holiday " 																												+
				"				FROM " 																																+
				"						t_MonthFloatHolidays "																							 			+
				"		) "
		;	
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		cursor.moveToFirst();
		cursor.close();
	}
 
	public void saveParameters(int curYear){
		
	}
	
	public int getYear(){
		return 0;
	}
	
	public String getCurrDate(){
		String query = "SELECT date('now')";
		Cursor cursor = myDataBase.rawQuery(query, new String[]{});
		cursor.moveToFirst();
		return cursor.getString(0);
	}
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
 
}

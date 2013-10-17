package com.jonnygold.holidays.calendar;

//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayInputStream;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.jonnygold.holidays.calendar.R;

//import org.w3c.dom.Text;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
//import android.app.PendingIntent;
import android.app.DatePickerDialog.OnDateSetListener;
import android.appwidget.AppWidgetManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
//import android.graphics.BitmapFactory;
//import android.graphics.Typeface;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
//import android.widget.ImageView;
import android.widget.ListView;
//import android.widget.RemoteViews;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
//import android.widget.SimpleCursorAdapter.ViewBinder;
//import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int CM_DELETE_ID = 1;
	private static final String ImageView = null;
    
	ListView lvData;
    ListView hListView;
    
    HolidayBinder binder;
    
    String[] from = new String[] {  "image", "hTitle", "id_holiday", "id_holiday", "id_holiday" };
    int[] to = new int[] { R.id.ivImg, R.id.tvText, R.id.flag_img_1, R.id.flag_img_2, R.id.flag_img_3 };
    
    DataBaseHelper db;
    SimpleCursorAdapter scAdapter;
    
    Menu appMenu;
    
    Cursor cursor;
    
    Date currDate;
    
    GestureDetector mGestureDetector;
    
    FlagPanel flags;// = new FlagPanel();
    
    Vector<int[]> flagVect;
    
    
    
    //Объявим массив ImgeView флагов для последующего перебора их в цикле
	//int[] flagViewArr = new int[] {R.id.flag_img_1, R.id.flag_img_2, R.id.flag_img_3};
    //Bitmap icon;
    //Drawable icon;
    

    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Убираем панель заголовка
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_main);
        
        // Поиск AdView в качестве ресурса и загрузка запроса.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
        
        if(savedInstanceState != null)
        	savedInstanceState.clear();
        
        int version = 1;
        try {
			PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionCode;
        } catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //int version = pInfo.versionCode;
        
        Log.w("CreateActivity", "Создание объекта базы данных");
        db = new DataBaseHelper(this);
        Log.w("CreateActivity", "Объект базы данных создан");
        
        //Определяем текущую дату
        currDate = new Date(Calendar.getInstance());
        
        try {
			db.createDataBase(version);
			db.openDataBase();
			db.updateMonthFloatDates(currDate.getYear());
			db.updateEasterDate(currDate.getYear());
        	db.updateYearFloatDates(currDate.getYear());
			db.close();
			//Обновляем виджеты
			updateWidgets();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.w("tag", "Ошибка при создании базы");
			e.printStackTrace();
		}
        
        Log.w("tag", "1");
        db.openDataBase();
        Log.w("tag", "2");
        
        flags = new FlagPanel(db);
        
        binder = new HolidayBinder(flags, getResources());
//        flags = new FlagPanel();
//        
//        Log.w("tag", "3");
//        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to);
//        scAdapter.setViewBinder(binder);
//        Log.w("tag", "4");
//        lvData = (ListView) findViewById(R.id.lvData);
//        Log.w("tag", "5");
//        lvData.setAdapter(scAdapter);
//        Log.w("tag", "6");
//        lvData.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
//				//Intent intent = new Intent(this, DetailActivity.class);
//				getDetailActivity((int)position);
//			}
//		});
        
        //Инициализируем компоненты активности
        initialActivity(currDate);
        
        
        
        
    }
    
//    public boolean onTouchEvent(MotionEvent event) {
//    	
//    	if(mGestureDetector.onTouchEvent(event)){
//    		return true;
//    	}
////    	if(event.getAction() == MotionEvent.ACTION_MOVE){
////			if (mGestureDetector.onTouchEvent(event)){
////				initialActivity(Date.getYesterday(currDate));
////				return true;
////			}
////			else{
////				initialActivity(Date.getTomorrow(currDate));
////				return true;
////			}
////    	}
//    	return super.onTouchEvent(event);
//    	//return false;
//  	}
    
    public void onStop(){
    	cursor.close();
    	db.close();
    	
    	//flags.close();
    	super.onStop();
    	//Toast.makeText(this, "onStop", Toast.LENGTH_LONG).show();
    	//Calendar calendar = Calendar.getInstance();
    	//db.saveParameters(calendar.get(Calendar.YEAR));
    	//db.close(); 
    	//cursor = null;
    	//lvData = null;
    	//hListView.destroyDrawingCache();
    	//hListView = null;
        //scAdapter = null;
        //hAdapter = null;
        //CommonResources.clearAll();
        //onDestroy();
        //Bitmap icon;
        //icon = null;
    	
    }
    
    public void onResume(){
    	//Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
    	//Calendar caledar = Calendar.getInstance();
    	db.openDataBase();
    	//flags = new FlagPanel(db);
    	//Log.w("onResume", "Toast");
    	Date resumeDate = new Date(Calendar.getInstance());
    	/*if(currDate != resumeDate){
    		initialActivity(resumeDate);
    	}*/
    	initialActivity(currDate);
    	
    	
//    	//Toast.makeText(this, db.getCurrDate() + "", Toast.LENGTH_LONG).show();
//    	Toast.makeText(
//    			this
//    		,	caledar.get(Calendar.YEAR) + "-" + (caledar.get(Calendar.MONTH)+1) + "-" + caledar.get(Calendar.DATE)
//    		,	Toast.LENGTH_LONG
//    	).show();
//    	if(db.getYear() != caledar.get(Calendar.YEAR)){
//    		
//    	}
    	super.onResume();
    }
    
    protected void initialActivity(Date actualDate){
    	Log.w("InitialActivity_start", "Инициализация компонентов активности");
    	
    	//Если с момента последнего открытия изменился год - переопределим даты плавающих праздников
        if(actualDate.getYear() != currDate.getYear()){
        	Log.w("InitialActivity", "Определение дыт плавающих праздников");
        	db.updateMonthFloatDates(actualDate.getYear());
        	db.updateEasterDate(actualDate.getYear());
        	db.updateYearFloatDates(actualDate.getYear());
        	Log.w("InitialActivity", "Даты плавающих праздников определены");
        }
    	
        flagVect = new Vector<int[]>(10);
        
    	Log.w("InitialActivity", "Инициализация заголовка");
        TextView titleText = (TextView) findViewById(R.id.act_main_title);
        titleText.setText(actualDate.getDateStr());
//////        titleText.setTypeface(Typeface.createFromAsset(getAssets(),"sansBold.ttf"));
        Log.w("InitialActivity", "Заголовок инициализирован");
    	
        Log.w("InitialActivity", "Открытие базы данных");
        //db.openDataBase();
        Log.w("InitialActivity", "База данных открыта");        
        
        Log.w("InitialActivity", "Получение курсора");
        cursor = db.getDataOnDay(actualDate.getMonth(), actualDate.getDay());
        Log.w("InitialActivity", "Курсор получен");
        
        
        Log.w("InitialActivity", "Менеджер старт");
        //startManagingCursor(cursor);
        Log.w("InitialActivity", "Менеджер конец");
        
        Log.w("InitialActivity", "Создание адаптера");
        // формируем столбцы сопоставления
        String[] from = new String[] {  "image", "hTitle", "id_holiday", "id_holiday", "id_holiday" };
        int[] to = new int[] { R.id.ivImg, R.id.tvText, R.id.flag_img_1, R.id.flag_img_2, R.id.flag_img_3 };
        
        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        //scAdapter.changeCursorAndColumns(cursor, from, to);
        scAdapter.setViewBinder(binder);
        Log.w("InitialActivity", "Адаптер создан");
        
        Log.w("InitialActivity", "Инициализация списка");
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);
        lvData.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
				//Intent intent = new Intent(this, DetailActivity.class);
				getDetailActivity((int)position);
			}
		});
        Log.w("InitialActivity", "Список инициализирован");
        
//        Log.w("InitialActivity", "Закрытие базы данных");
        //cursor.close();
        //db.close();
//        Log.w("InitialActivity", "База данных закрыта");
        
        //Запомним дату на которую выводились данные в последний раз
    	currDate = actualDate;
        Log.w("InitialActivity_end", "Компоненты активности инициализированы");
    }
    
    private void getDetailActivity(int position){
    	//Cursor c = scAdapter.getCursor();
    	//Object a = scAdapter.getItem(0);
    	//Log.w("Проверка класса", a.getClass().getName());
    	
    	Intent intent = new Intent(this, DetailActivity.class);
    	Log.w("Вызов", "moveToPosition");
    	cursor.moveToPosition(position);
    	//Log.w("ViewType", scAdapter.getItemViewType(position)+ "");
    	//ImageView v = (ImageView) scAdapter.getView(position, (ImageView) findViewById(R.id.ivImg), lvData);
    	//CommonResources.drawable_1 = v.getDrawable();
    	//v = null;
    	//CommonResources.bitmap_1 = BitmapFactory.decodeResource(this.getResources(), R.id.ivImg);
    	//ByteArrayInputStream inputStream = new ByteArrayInputStream(cursor.getBlob(7));
    	byte[] imgData = cursor.getBlob(7);
    	intent.putExtra("img", imgData);
    	//inputStream.read(b)
    	//CommonResources.bitmap_1 = BitmapFactory.decodeStream(inputStream);
    	//if(CommonResources.bitmap_1 == null){
        //	Log.w("MainActivity", "bitmap_1 is null");
        //}
    	//CommonResources.bitmap_1 = cursor.getBlob(4);
    	
    	
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

//    // обработка нажатия кнопки
//    public void onButtonClick(View view) {
//      // добавляем запись
//      db.addRec("sometext " + (cursor.getCount() + 1), R.drawable.ic_launcher);
//      // обновляем курсор
//      cursor.requery();
//    }

    public boolean onCreateOptionsMenu(Menu menu) {  
        
        appMenu = menu;  
              
        MenuInflater mInflater = getMenuInflater();  
        mInflater.inflate(R.menu.activity_main, menu);  
        return true;  
    }  
    
    public boolean onOptionsItemSelected (MenuItem item) {  
    	  
        
        if (item.getItemId() == R.id.menu_find) {   
            Intent toFindAct = new Intent(this, SearchActivity.class); 
            startActivity(toFindAct);
            
        }
//        else if(item.getItemId() == R.id.menu_exit){
//        	finish();
//        	//onDestroy();
//        }
//        else if(item.getItemId() == R.id.menu_tomorrow){
//    		initialActivity(Date.getTomorrow(currDate));
//        }
//        else if(item.getItemId() == R.id.menu_yesterday){
//    		initialActivity(Date.getYesterday(currDate));
//        }
        else if(item.getItemId() == R.id.menu_to_date){
        	showDialog(1);
    		//initialActivity(Date.getYesterday(currDate));
        }
		return true;  
        
    } 
    
    protected Dialog onCreateDialog(int id) {
		if (id == 1) {
			OnDateSetListener myCallBack = new OnDateSetListener() {
	    		@Override
	    	    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
	    			Calendar calendar = Calendar.getInstance();
	    			calendar.clear();
	    			calendar.set(year, monthOfYear, dayOfMonth);
	    			initialActivity(new Date(calendar));
	    	    }
        	};
        	Date today = Date.getToday();
			DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, today.getYear(), today.getMonth(), today.getDay());
			return tpd;
		}
		return super.onCreateDialog(id);
	}
    
    public void getYesterday(View view){
    	Log.w("!!!!!!", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	initialActivity(Date.getYesterday(currDate));
    }
    
    public void getTomorrow(View view){
    	Log.w("!!!!!!", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	initialActivity(Date.getTomorrow(currDate));
    }
    
    public void getToday(View view){
    	Log.w("!!!!!!", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	initialActivity(Date.getToday());
    }
//    
//    public boolean onContextItemSelected(MenuItem item) {
//      if (item.getItemId() == CM_DELETE_ID) {
//        // получаем из пункта контекстного меню данные по пункту списка 
//        AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
//        // извлекаем id записи и удаляем соответствующую запись в БД
//        db.delRec(acmi.id);
//        // обновляем курсор
//        cursor.requery();
//        return true;
//      }
//      return super.onContextItemSelected(item);
//    }
    
    
    
    protected void onDestroy() {
      // закрываем подключение при выходе
    	
    	db.close();
    	super.onDestroy();
    }
    
    public void updateWidgets(){
    	//Обновляем виждеты
    	//if(getIntent().getExtras()!=null){
    	//if(getIntent().getExtras().containsKey("id")){
    	//int[] ids = {getIntent().getIntExtra("id", -1)};
        int[] ids_1 = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), HolidaysWidget_4x1.class));
        int[] ids_2 = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), HolidaysWidget_4x2.class));
//        int[] ids_3 = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), HolidaysWidgetBel.class));
//        int[] ids_4 = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), HolidaysWidgetUkr.class));
        
        Intent intent = new Intent(this,HolidaysWidget_4x1.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids_1);
        //intent.putExtra("chk_rus", getIntent().getBooleanExtra("chk_rus", true));
        //intent.putExtra("chk_bel", getIntent().getBooleanExtra("chk_bel", true));
        //intent.putExtra("chk_ukr", getIntent().getBooleanExtra("chk_ukr", true));
        //intent.putExtra("chk_wrld", getIntent().getBooleanExtra("chk_wrld", true));
        sendBroadcast(intent);
        
        intent = new Intent(this,HolidaysWidget_4x2.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids_2);
        sendBroadcast(intent);
    	//}}
//        
//        Intent intent_bel = new Intent(this,HolidaysWidgetBel.class);
//        intent_bel.setAction("android.appwidget.action.APPWIDGET_UPDATE");
//        intent_bel.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids_3);
//        sendBroadcast(intent_bel);
//        
//        Intent intent_ukr = new Intent(this,HolidaysWidgetUkr.class);
//        intent_ukr.setAction("android.appwidget.action.APPWIDGET_UPDATE");
//        intent_ukr.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids_4);
//        sendBroadcast(intent_ukr);
    }
    
//    public class ActivityChangeGesture extends GestureDetector.SimpleOnGestureListener{
//    	
//    	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//    		
//    		if(e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 200) {
//                // Справа налево
//    			initialActivity(Date.getTomorrow(currDate));
//                return true;
//            }  else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 200) {
//                // Слева направо
//            	initialActivity(Date.getYesterday(currDate));
//                return true;
//            }
//            return false;
//    	}
//    }

}

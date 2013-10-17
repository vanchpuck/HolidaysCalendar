package com.jonnygold.holidays.calendar;

import java.util.Calendar;

public class Date {
	
	protected int year;
	protected int month;
	protected int day;
	protected int weekDay;
	
	public Date(Calendar calendar){
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONDAY);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		weekDay = calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public int getYear(){
		return year;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getWeekDay(){
		return weekDay;
	}
	
	public static Date getTomorrow(Date today){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(today.getYear(), today.getMonth(), today.getDay()+1);
		//calendar.roll(Calendar.DAY_OF_MONTH, true);
		//calendar.set(year, month, day+1);
		return new Date(calendar);
	}
	
	public static Date getYesterday(Date today){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(today.getYear(), today.getMonth(), today.getDay()-1);
		//calendar.roll(Calendar.DAY_OF_MONTH, false);
		return new Date(calendar);
	}
	
	public static Date getToday(){
		Calendar calendar = Calendar.getInstance();
		return new Date(calendar);
	}
	
	public String getDateStr(){
    	String dateStr = null;
    	switch(month){
    		case 0: dateStr = day + " ������ " + year + " �. "; break;
    		case 1: dateStr = day + " ������� " + year + " �. "; break;
    		case 2: dateStr = day + " ����� " + year + " �. "; break;
    		case 3: dateStr = day + " ������ " + year + " �. "; break;
    		case 4: dateStr = day + " ��� " + year + " �. "; break;
    		case 5: dateStr = day + " ���� " + year + " �. "; break;
    		case 6: dateStr = day + " ���� " + year + " �. "; break;
    		case 7: dateStr = day + " ������� " + year + " �. "; break;
    		case 8: dateStr = day + " �������� " + year + " �. "; break;
    		case 9: dateStr = day + " ������� " + year + " �. "; break;
    		case 10: dateStr = day + " ������ " + year + " �. "; break;
    		case 11: dateStr = day + " ������� " + year + " �. "; break;
    	}
    	switch(weekDay){
			case 1: dateStr = dateStr + "- ����������� "; break;
			case 2: dateStr = dateStr + "- ����������� "; break;
			case 3: dateStr = dateStr + "- ������� "; break;
			case 4: dateStr = dateStr + "- ����� "; break;
			case 5: dateStr = dateStr + "- ������� "; break;
			case 6: dateStr = dateStr + "- ������� "; break;
			case 7: dateStr = dateStr + "- ������� "; break;
    	}
    	return dateStr;
    }
	
	public static String getDateStr(int day, int month, int year){
    	String dateStr = null;
    	switch(month){
    		case 0: dateStr = day + " ������ " + year + " �. "; break;
    		case 1: dateStr = day + " ������� " + year + " �. "; break;
    		case 2: dateStr = day + " ����� " + year + " �. "; break;
    		case 3: dateStr = day + " ������ " + year + " �. "; break;
    		case 4: dateStr = day + " ��� " + year + " �. "; break;
    		case 5: dateStr = day + " ���� " + year + " �. "; break;
    		case 6: dateStr = day + " ���� " + year + " �. "; break;
    		case 7: dateStr = day + " ������� " + year + " �. "; break;
    		case 8: dateStr = day + " �������� " + year + " �. "; break;
    		case 9: dateStr = day + " ������� " + year + " �. "; break;
    		case 10: dateStr = day + " ������ " + year + " �. "; break;
    		case 11: dateStr = day + " ������� " + year + " �. "; break;
    	}
    	return dateStr;
    }
}

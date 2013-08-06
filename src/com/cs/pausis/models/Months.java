package com.cs.pausis.models;

import java.util.ArrayList;

import com.core.pausis.R;

import android.content.Context;

/**
 * This class models the calendar months of the year, which is used in the application for populating lists
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * @since August, 2013
 * 
 */
public class Months {
	private String id,
				   month;
	
	public Months(){
	   id = "";
	   month = "";
	}
	
	public Months(String  id, String month){
	   this.id = id;
	   this.month = month;
	}

	/********************************************************************
	 * Getters and Setters
	 ************************************
	 */
	public void setID(String value){
		id = value;
	}
	public String getID(){
		return id;
	}
	
	public void setMonth(String value){
		month = value;
	}
	public String getMonth(){
		return month;
	}
	
	/**
	 * Method for getting all the 12 Calendar months, this is mainly used for populating the months spinner
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<Months> getAll(Context context){
		ArrayList<Months> months = new ArrayList<Months>();
		
		Months months2 = new Months();
		months2.setID("0");
		months2.setMonth(context.getString(R.string.choosebirthmonth));
		months.add(months2);
		
		months2 = new Months();
		months2.setID("1");
		months2.setMonth("January");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("2");
		months2.setMonth("February");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("3");
		months2.setMonth("March");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("4");
		months2.setMonth("April");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("5");
		months2.setMonth("May");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("6");
		months2.setMonth("June");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("7");
		months2.setMonth("July");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("8");
		months2.setMonth("August");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("9");
		months2.setMonth("September");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("10");
		months2.setMonth("October");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("11");
		months2.setMonth("November");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("12");
		months2.setMonth("December");
		months.add(months2);
		
		return months;
	}
	
	public String toString()
	{
		return month;
	}
}

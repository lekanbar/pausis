package com.cs.pausis.models;

import java.util.ArrayList;

import com.core.pausis.R;

import android.content.Context;

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
	
	public static ArrayList<Months> getAll(Context context){
		ArrayList<Months> months = new ArrayList<Months>();
		
		Months months2 = new Months();
		months2.setID("");
		months2.setMonth(context.getString(R.string.choosebirthmonth));
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("January");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("February");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("March");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("April");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("May");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("June");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("July");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("August");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("September");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("October");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("November");
		months.add(months2);
		
		months2 = new Months();
		months2.setID("");
		months2.setMonth("December");
		months.add(months2);
		
		return months;
	}
	
	public String toString()
	{
		return month;
	}
}

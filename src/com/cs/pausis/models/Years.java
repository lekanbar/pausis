package com.cs.pausis.models;

import java.util.ArrayList;

import com.core.pausis.R;

import android.content.Context;

/**
 * This class models the acceptable years of birth (from 1990-1959), which is used in the application for populating lists.
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * @since August, 2013
 * 
 */
public class Years {
	private String id,
				   year;
	
	public Years(){
	   id = "";
	   year = "";
	}
	
	public Years(String  id, String year){
	   this.id = id;
	   this.year = year;
	}

	public void setID(String value){
		id = value;
	}
	public String getID(){
		return id;
	}
	
	public void setYear(String value){
		year = value;
	}
	public String getYear(){
		return year;
	}
	
	public static ArrayList<Years> getAll(Context context){
		ArrayList<Years> years = new ArrayList<Years>();
		
		Years years2 = new Years();
		years2.setID("");
		years2.setYear(context.getString(R.string.choosebirthyear));
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1990");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1989");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1988");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1987");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1986");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1985");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1984");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1983");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1982");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1981");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1980");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1979");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1978");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1977");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1976");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1975");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1974");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1973");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1972");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1971");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1970");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1969");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1968");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1967");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1966");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1965");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1964");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1963");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1962");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1961");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1960");
		years.add(years2);
		
		years2 = new Years();
		years2.setID("");
		years2.setYear("1959");
		years.add(years2);
		
		return years;
	}
	
	public String toString()
	{
		return year;
	}
}

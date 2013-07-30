package com.cs.pausis.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * This model software representation was created based on the model developed by Marca A. et al, 2011
 * 
 * Article can be accessed at: http://www.sciencedirect.com/science/article/pii/S0015028210021953
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * 
 */
public class AFC {
	private AFC loadedItem;
	private int Age, Percentile;
	
	private double ObservedAfcValue,
	               Fifth,
	               TwentyFifth,
	               Fiftieth,
	               SeventyFifth,
	               NinetyFifth;
	private double[] sdvalues = {5, 25, 50, 75, 95};
	Context context;
	
	/**
	 * This is the default constructor for the AFC model
	 */
	public AFC(){
		
	}
	
	public AFC(Context context){
		this.context = context;
		
		if(!check())
			initializeAFC();
	}
	
	private void initializeAFC(){
	    //Initialize json object
		JSONObject json = null;
		String datax = "" ;
        try {
        	InputStream fIn = context.getAssets().open("afclookup.json");
            InputStreamReader isr = new InputStreamReader (fIn);
            BufferedReader buffreader = new BufferedReader (isr);

            //Read in each line of string from json file
            String readString = buffreader.readLine();
            while (readString != null){
                datax = datax + readString;
                readString = buffreader.readLine();
            }
            //close input stream and its reader
            isr.close();
            fIn.close();
            
            // Create a new row of values to insert.
    	    // Insert the row.
            json = new JSONObject(datax);
            JSONArray values = json.getJSONArray("LookupTable");
            
            //Open database instance
            SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);            
            for (int i = 0; i < values.length(); i++) {
    			JSONObject value = values.getJSONObject(i);
	            
    			//Prepare Insert SQL statement
	            String sql = "INSERT INTO " + DB.TABLE_AFC_LOOKUP + "(" + DB.KEY_AGE + ", " + DB.KEY_5TH_PERCENTILE + ", " + DB.KEY_25TH_PERCENTILE + ", " + 
	                                          DB.KEY_50TH_PERCENTILE + ", " + DB.KEY_75TH_PERCENTILE + ", " + DB.KEY_95TH_PERCENTILE + 
	                         ") VALUES('"   + 
	            		                      value.getString("Age (y)") + "','" 
                                            + value.getString("5th") + "','" 
                                            + value.getString("25th") + "','" 
                                            + value.getString("50th") + "','" 
                                            + value.getString("75th") + "','"
                                            + value.getString("95th") + "');";
	            db.execSQL(sql);
            }
            
            //close connection
            db.close();
        } catch (IOException ioe){
            ioe.printStackTrace ();
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean check(){
	   SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
	   
	   Cursor lookup = db.query(DB.TABLE_AFC_LOOKUP, null, null, null, null, null, null);
		   
	   if (lookup.moveToFirst()){
		   lookup.close();
		   db.close();
		   return true;
	   }
	   
	   lookup.close();
	   db.close();
	   return false;
	}
	
	private void vLookUp() {	
		SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
		Cursor value = db.query(true, DB.TABLE_AFC_LOOKUP,  null, DB.KEY_AGE + " = ?", new String[]{String.valueOf(this.getAge())}, null, null, null, null);
		   
	    if ((value.getCount() == 0) || !value.moveToFirst()) {
	    	value.close();
		    db.close();
		    loadedItem = null;
	    }
	   
	    loadedItem = new AFC();
	    loadedItem.setFifth(Double.parseDouble(value.getString(DB.KEY_5TH_PERCENTILE_COLUMN)));
	    loadedItem.setTwentyFifth(Double.parseDouble(value.getString(DB.KEY_25TH_PERCENTILE_COLUMN)));
	    loadedItem.setFiftieth(Double.parseDouble(value.getString(DB.KEY_50TH_PERCENTILE_COLUMN)));
	    loadedItem.setSeventyFifth(Double.parseDouble(value.getString(DB.KEY_75TH_PERCENTILE_COLUMN)));
	    loadedItem.setNinetyFifth(Double.parseDouble(value.getString(DB.KEY_95TH_PERCENTILE_COLUMN)));
	    
	    value.close();
	    db.close();
    }
	
	private boolean checkInputValues() {
		if(this.getAge() <= 0.0)
			return false;
		
		return true;
	}
	
	public void calculateAFC() throws Exception{
		//Assert input values are valid before proceeding
		if(checkInputValues()){	
			//perform a look up of the age related values
			vLookUp();
			
			if(loadedItem != null){
				//the percentile of the follicle count
				int percentile = 0;
				
				double afcInput = this.getObservedAfcValue();
				
				if(afcInput < loadedItem.getTwentyFifth())
					percentile = 5;
				else if(afcInput >= loadedItem.getTwentyFifth() && afcInput < loadedItem.getFiftieth())
					percentile = 25;
				else if(afcInput >= loadedItem.getFiftieth() && afcInput < loadedItem.getSeventyFifth())
					percentile = 50;
				else if(afcInput >= loadedItem.getSeventyFifth() && afcInput < loadedItem.getNinetyFifth())
					percentile = 75;
				else
					percentile = 95;
				
				setPercentile(percentile);
			}
			else{
				throw new Exception("Age does not exist in table.");
			}
		}
		else {
			throw new Exception("Invalid inputs entered.");
		}
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public double getObservedAfcValue() {
		return ObservedAfcValue;
	}

	public void setObservedAfcValue(double observedAfcValue) {
		ObservedAfcValue = observedAfcValue;
	}

	public int getPercentile() {
		return Percentile;
	}

	public void setPercentile(int percentile) {
		Percentile = percentile;
	}

	public double getFifth() {
		return Fifth;
	}

	public void setFifth(double fifth) {
		Fifth = fifth;
	}

	public double getTwentyFifth() {
		return TwentyFifth;
	}

	public void setTwentyFifth(double twentyFifth) {
		TwentyFifth = twentyFifth;
	}

	public double getFiftieth() {
		return Fiftieth;
	}

	public void setFiftieth(double fiftieth) {
		Fiftieth = fiftieth;
	}

	public double getSeventyFifth() {
		return SeventyFifth;
	}

	public void setSeventyFifth(double seventyFifth) {
		SeventyFifth = seventyFifth;
	}

	public double getNinetyFifth() {
		return NinetyFifth;
	}

	public void setNinetyFifth(double ninetyFifth) {
		NinetyFifth = ninetyFifth;
	}

	public double[] getSdvalues() {
		return sdvalues;
	}

	public void setSdvalues(double[] sdvalues) {
		this.sdvalues = sdvalues;
	}
}

package com.cs.pausis.models;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * This is the main model class mainly used for tracking generic indicators within the app such as storing the record of user's privacy policy agreement,
 * it is a direct one-to-one mapping of the corresponding database table(TABLE_TRACKER).
 * 
 * This class implements the Parcelable interface, which makes the class portable for transportation from one activity 
 * to another through serialization and de-serialization.
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * @since August, 2013
 * 
 */
public class Tracker {
	private String iD,
				   isfirsttime,
				   termsagreed,
				   extra;
	Context context;
	
	/**
	 * Class contructor
	 */
	public Tracker() {
		
	}
	
	/**
	 * Default class constructor 
	 */
	public Tracker(Context context){
		iD = "";
		isfirsttime = "";
		termsagreed = "";
		extra = "";
		this.context = context;
	}
	
	/**
	    * Method for inserting the user preferences
	    * 
	    * @param _pref
	    */
	   public void insertTracker() {
		   // Create a new row of values to insert.
		   // Insert the row.
		   SQLiteDatabase db;
	       db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
	                
	       String sql = "INSERT INTO " + DB.TABLE_TRACKER + " (" + DB.KEY_ISFIRSTTIME + ", " + DB.KEY_TERMS_AGREED + ", " + DB.KEY_EXTRA + ") VALUES('" 
	                                                                                    + this.getIsFirstTime() + "','" 
	                                                                                    + this.getTermsAgreed() + "','"
	                                                                                    + this.getExtra() + "');";
	       db.execSQL(sql);
	       db.close();
	   }
	   
	   /**
	    * Method for updating the user's preferences
	    * 
	    * @param _rowIndex
	    * @param pref
	    */
	   public void updateTracker(long _rowIndex, Tracker pref) {	
		   // Update the row.
		   SQLiteDatabase db;
	       db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
	                
	       String sql = "Update " + DB.TABLE_TRACKER + " set " + DB.KEY_ISFIRSTTIME + " = '" + pref.getIsFirstTime()
		                        + "', " + DB.KEY_TERMS_AGREED + " = '" + pref.getTermsAgreed()
		                        + "', " + DB.KEY_EXTRA + " = '" + pref.getExtra()
		                        + "' where _id = " + _rowIndex;
	       db.execSQL(sql);
	       db.close();
	   }
	   
	   /**
	    * Method for getting the user's preference 
	    * 
	    * @param _rowIndex
	    * @return
	    * @throws SQLException
	    */
	   public Tracker getTracker(long _rowIndex) throws SQLException {
		   SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
		   Cursor history = db.query(true, DB.TABLE_TRACKER,  null, DB.KEY_ID + "=" + _rowIndex, null, null, null, null, null);
		   
		   if ((history.getCount() == 0) || !history.moveToFirst()) {
			   history.close();
			   return null;
		   }
		   
		   Tracker newItem = new Tracker();
		   newItem.setID(history.getString(0));
		   newItem.setIsFirstTime(history.getString(DB.ISFIRSTTIME_COLUMN));
		   newItem.setTermsAgreed(history.getString(DB.TERMS_AGREED_COLUMN));
		   newItem.setExtra(history.getString(DB.EXTRA_COLUMN));
		   
		   history.close();
		   db.close();
		   
		   return newItem;
	   }
	
	/********************************************************************
	 * Getters and Setters
	 ************************************
	 */
	public void setID(String value){
		iD = value;
	}
	public String getID(){
		return iD;
	}
	
	public void setIsFirstTime(String value){
		isfirsttime = value;
	}
	public String getIsFirstTime(){
		return isfirsttime;
	}
	
	public void setTermsAgreed(String value){
		termsagreed = value;
	}
	public String getTermsAgreed(){
		return termsagreed;
	}
	
	public void setExtra(String value){
		extra = value;
	}
	public String getExtra(){
		return extra;
	}
}

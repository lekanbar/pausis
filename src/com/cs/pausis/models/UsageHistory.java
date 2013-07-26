package com.cs.pausis.models;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

public class UsageHistory implements Parcelable {
	private String iD,
				   age,
				   amhvolume,
				   ovarianvolume,
				   afc,
				   years,
				   months;

	public UsageHistory(){
		iD = "";
		age = "0";
		amhvolume = "0";
		ovarianvolume = "0";
		afc = "0";
		years = "0";
		months = "0";
	}

   // Insert a new history
   public void insertHistory(Context context) {
	   // Create a new row of values to insert.
	   // Insert the row.
	   SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
                
       String sql = "INSERT INTO " + DB.TABLE_USAGE + "(" + DB.KEY_AGE + ", " + DB.KEY_AMH_VOLUME + ", " + DB.KEY_OVARIAN_VOLUME + ", " 
					 		       + DB.KEY_AF_COUNT + ", " + DB.KEY_YEARS + ", " + DB.KEY_MONTHS + ") VALUES('" 
    		                       + this.getAge() + "','" 
					               + this.getAmhvolume() + "','" 
					               + this.getOvarianvolume() + "','" 
					               + this.getAfc() + "','"
					               + this.getYears() + "','"
					               + this.getMonths() + "');";
       db.execSQL(sql);
       db.close();
   }
   
   public UsageHistory getHistory(long _rowIndex, Context context) throws SQLException {
	   SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
	   
	   Cursor history = db.query(true, DB.TABLE_USAGE,  null, DB.KEY_ID + "=" + _rowIndex, null, null, null, null, null);
	   
	   if ((history.getCount() == -1) || !history.moveToFirst()) {
		   history.close();
		   db.close();
		   return null;
	   }
	   
	   UsageHistory newItem = new UsageHistory();
	   newItem.setID(history.getString(0));
	   newItem.setAge(history.getString(DB.AGE_COLUMN));
	   newItem.setAmhvolume(history.getString(DB.AMH_VOLUME_COLUMN));
	   newItem.setOvarianvolume(history.getString(DB.OVARIAN_VOLUME_COLUMN));
	   newItem.setAfc(history.getString(DB.AF_COUNT_COLUMN));
	   newItem.setYears(history.getString(DB.YEARS_COLUMN));
	   newItem.setMonths(history.getString(DB.MONTHS_COLUMN));
	   
	   history.close();
	   db.close();
	   
	   return newItem;
	}
   
   //Get All stored History
   public ArrayList<UsageHistory> getAllHistory(Context context) {
	   SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
	   
	   Cursor histories = db.query(DB.TABLE_USAGE, null, null, null, null, null, DB.KEY_ID + " DESC");
	   
	   ArrayList<UsageHistory> result = new ArrayList<UsageHistory>();
	    if (histories.moveToFirst()){
		   do {
			   UsageHistory newItem = new UsageHistory();
			   newItem.setID(histories.getString(0));
			   newItem.setAge(histories.getString(DB.AGE_COLUMN));
			   newItem.setAmhvolume(histories.getString(DB.AMH_VOLUME_COLUMN));
			   newItem.setOvarianvolume(histories.getString(DB.OVARIAN_VOLUME_COLUMN));
			   newItem.setAfc(histories.getString(DB.AF_COUNT_COLUMN));
			   newItem.setYears(histories.getString(DB.YEARS_COLUMN));
			   newItem.setMonths(histories.getString(DB.MONTHS_COLUMN));
			   
			   result.add(newItem);
		   } while(histories.moveToNext());
	    }
	    
	    histories.close();
	    db.close();
	    
	   return result;
	}
	
	// Remove history based on its index
    public void removeHistory(long _rowIndex, Context context) {
	   // Delete the row.
	   SQLiteDatabase db;
       db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
                
       String sql = "Delete from " + DB.TABLE_USAGE + " where _id = " + _rowIndex; 
       db.execSQL(sql);
       db.close();
    }
   
    // Remove all history
    public void removeAllHistory(Context context) {
	   // Delete all.
	   SQLiteDatabase db;
       db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
                
       String sql = "Delete from " + DB.TABLE_USAGE;
       db.execSQL(sql);
       db.close();
    }
	
	public void setID(String value){
		iD = value;
	}
	public String getID(){
		return iD;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAmhvolume() {
		return amhvolume;
	}

	public void setAmhvolume(String amhvolume) {
		this.amhvolume = amhvolume;
	}

	public String getOvarianvolume() {
		return ovarianvolume;
	}

	public void setOvarianvolume(String ovarianvolume) {
		this.ovarianvolume = ovarianvolume;
	}

	public String getAfc() {
		return afc;
	}

	public void setAfc(String afc) {
		this.afc = afc;
	}
	
	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(age);
		out.writeString(amhvolume);
		out.writeString(ovarianvolume);
		out.writeString(afc);
	}
	
	private UsageHistory(Parcel in) {
		this.setAge(in.readString());
		this.setAmhvolume(in.readString());
		this.setOvarianvolume(in.readString());
		this.setAfc(in.readString());
	}
	
	public static final Parcelable.Creator<UsageHistory> CREATOR = new Parcelable.Creator<UsageHistory>() {
		public UsageHistory createFromParcel(Parcel in) {
			return new UsageHistory(in);
		}
		public UsageHistory[] newArray(int size) {
			return new UsageHistory[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}

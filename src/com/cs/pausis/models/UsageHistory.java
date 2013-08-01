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
				   amhvolume, ovarianvolume,
				   afc, birthyear, birthmonth,
				   fsh, mothermenopauseage, regularperiods,
				   height, weight;

	public UsageHistory(){
		iD = "";
		age = "";
		amhvolume = "";
		ovarianvolume = "";
		afc = "";
		birthyear = "";
		birthmonth = "";
		fsh = "";
		mothermenopauseage = "";
		regularperiods = "";
		height = "";
		weight = "";
	}

   // Insert a new history
   public void insertHistory(Context context) {
	   // Create a new row of values to insert.
	   // Insert the row.
	   SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
       
       String sql = "INSERT INTO " + DB.TABLE_USAGE + "(" + DB.KEY_AGE + ", " + DB.KEY_AMH_VOLUME + ", " + DB.KEY_OVARIAN_VOLUME + ", " 
					 		       + DB.KEY_AF_COUNT + ", " + DB.KEY_BIRTH_YEAR + ", " + DB.KEY_BIRTH_MONTH + ", " + DB.KEY_FSH + ", "
					 		       + DB.KEY_MOTHER_MENOPAUSE_AGE + ", " + DB.KEY_REGULAR_PERIODS + ", " + DB.KEY_HEIGHT + ", "
					 		       + DB.KEY_WEIGHT +
    		        ") VALUES('" 
    		                       + this.getAge() + "','" 
					               + this.getAmhvolume() + "','" 
					               + this.getOvarianvolume() + "','" 
					               + this.getAfc() + "','"
					               + this.getBirthYear() + "','"
					               + this.getBirthMonth() + "','"
					               + this.getFsh() + "','"
					               + this.getMotherMenopauseAge() + "','"
					               + this.getRegularPeriods() + "','"
					               + this.getHeight() + "','"
					               + this.getWeight() + "');";
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
	   newItem.setBirthYear(history.getString(DB.BIRTH_YEAR_COLUMN));
	   newItem.setBirthMonth(history.getString(DB.BIRTH_MONTH_COLUMN));
	   newItem.setFsh(history.getString(DB.FSH_COLUMN));
	   newItem.setMotherMenopauseAge(history.getString(DB.MENOPAUSE_AGE_COLUMN));
	   newItem.setRegularPeriods(history.getString(DB.REGULAR_PERIODS_COLUMN));
	   newItem.setHeight(history.getString(DB.HEIGHT_COLUMN));
	   newItem.setWeight(history.getString(DB.WEIGHT_COLUMN));
	   
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
			   newItem.setBirthYear(histories.getString(DB.BIRTH_YEAR_COLUMN));
			   newItem.setBirthMonth(histories.getString(DB.BIRTH_MONTH_COLUMN));
			   newItem.setFsh(histories.getString(DB.FSH_COLUMN));
			   newItem.setMotherMenopauseAge(histories.getString(DB.MENOPAUSE_AGE_COLUMN));
			   newItem.setRegularPeriods(histories.getString(DB.REGULAR_PERIODS_COLUMN));
			   newItem.setHeight(histories.getString(DB.HEIGHT_COLUMN));
			   newItem.setWeight(histories.getString(DB.WEIGHT_COLUMN));
			   
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
	
	public String getBirthYear() {
		return birthyear;
	}

	public void setBirthYear(String years) {
		this.birthyear = years;
	}

	public String getBirthMonth() {
		return birthmonth;
	}

	public void setBirthMonth(String months) {
		this.birthmonth = months;
	}
	
	public String getFsh() {
		return fsh;
	}

	public void setFsh(String fsh) {
		this.fsh = fsh;
	}

	public String getMotherMenopauseAge() {
		return mothermenopauseage;
	}

	public void setMotherMenopauseAge(String mothermenopause) {
		this.mothermenopauseage = mothermenopause;
	}

	public String getRegularPeriods() {
		return regularperiods;
	}

	public void setRegularPeriods(String regularperiods) {
		this.regularperiods = regularperiods;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
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

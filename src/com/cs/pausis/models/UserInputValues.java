package com.cs.pausis.models;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is the main model class for all the usage history generated throughout the lifetime of the the application,
 * it is a direct one-to-one mapping of the corresponding database table(TABLE_USAGE). Also this class is a blueprint that helps to collect the user's input values.
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
public class UserInputValues implements Parcelable {
	private String iD,
				   age,
				   amhvolume, ovarianvolume,
				   afc, birthyear, birthmonth,
				   fsh, mothermenopauseage, regularperiods,
				   height, weight, datetime, resultindicator;

	/**
	 * Default constructor instantiates all the attributes of the object
	 */
	public UserInputValues(){
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
		datetime = "";
		resultindicator = "";
	}

   /**
    * Method for inserting the values collected into the database
    * 
    * @param context - The context of the calling activity
    */
   public void insertHistory(Context context) {
	   // Create a new row of values to insert.
	   // Insert the row.
	   SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
       
       String sql = "INSERT INTO " + DB.TABLE_USAGE + "(" + DB.KEY_AGE + ", " + DB.KEY_AMH_VOLUME + ", " + DB.KEY_OVARIAN_VOLUME + ", " 
					 		       + DB.KEY_AF_COUNT + ", " + DB.KEY_BIRTH_YEAR + ", " + DB.KEY_BIRTH_MONTH + ", " + DB.KEY_FSH + ", "
					 		       + DB.KEY_MOTHER_MENOPAUSE_AGE + ", " + DB.KEY_REGULAR_PERIODS + ", " + DB.KEY_HEIGHT + ", "
					 		       + DB.KEY_WEIGHT + ", " + DB.KEY_DATETIME + ", " + DB.KEY_RESULT_INDICATOR +
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
					               + this.getWeight() + "','" 
					               + this.getDateTime() + "','" 
					               + this.getResultIndicator() + "');";
       db.execSQL(sql);
       db.close();
   }
   
   /**
    * Method for getting a single usage history from the database
    * 
    * @param _rowIndex - Index of the row to be retrieved
    * @param context - The context of the calling activity class
    * @return - A single object  representing the retrieved table row, null if the row doesn't exist
    * @throws SQLException
    */
   public UserInputValues getHistory(long _rowIndex, Context context) throws SQLException {
	   SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
	   
	   Cursor history = db.query(true, DB.TABLE_USAGE,  null, DB.KEY_ID + "=" + _rowIndex, null, null, null, null, null);
	   
	   if ((history.getCount() == -1) || !history.moveToFirst()) {
		   history.close();
		   db.close();
		   return null;
	   }
	   
	   UserInputValues newItem = new UserInputValues();
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
	   newItem.setDateTime(history.getString(DB.DATETIME_COLUMN));
	   newItem.setResultIndicator(history.getString(DB.RESULT_INDICATOR_COLUMN));
	   
	   history.close();
	   db.close();
	   
	   return newItem;
	}
   
   /**
    * Method for getting all the stored usage history
    * 
    * @param context
    * @return - An ArrayList of UserInputValues class, an empty ArrayList is returned if there are no usage history.
    */
   public ArrayList<UserInputValues> getAllHistory(Context context) {
	   SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
	   
	   Cursor histories = db.query(DB.TABLE_USAGE, null, null, null, null, null, DB.KEY_ID + " DESC");
	   
	   ArrayList<UserInputValues> result = new ArrayList<UserInputValues>();
	    if (histories.moveToFirst()){
		   do {
			   UserInputValues newItem = new UserInputValues();
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
			   newItem.setDateTime(histories.getString(DB.DATETIME_COLUMN));
			   newItem.setResultIndicator(histories.getString(DB.RESULT_INDICATOR_COLUMN));
			   
			   result.add(newItem);
		   } while(histories.moveToNext());
	    }
	    
	    histories.close();
	    db.close();
	    
	   return result;
	}
	
	/**
	 * Method for removing a particular usage history from the database 
	 * 
	 * @param _rowIndex
	 * @param context
	 */
    public void removeHistory(long _rowIndex, Context context) {
	   // Delete the row.
	   SQLiteDatabase db;
       db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
                
       String sql = "Delete from " + DB.TABLE_USAGE + " where _id = " + _rowIndex; 
       db.execSQL(sql);
       db.close();
    }
   
    /**
     * Method for removing/erasing all history from the database
     * 
     * @param context
     */
    public void removeAllHistory(Context context) {
	   // Delete all.
	   SQLiteDatabase db;
       db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
                
       String sql = "Delete from " + DB.TABLE_USAGE;
       db.execSQL(sql);
       db.close();
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
	
	public String getDateTime() {
		return datetime;
	}

	public void setDateTime(String datetime) {
		this.datetime = datetime;
	}
	
	public String getResultIndicator() {
		return resultindicator;
	}

	public void setResultIndicator(String resultindicator) {
		this.resultindicator = resultindicator;
	}

	/**
	 * Overidden method for serializing the class attributes
	 */
	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(age);
		out.writeString(amhvolume);
		out.writeString(ovarianvolume);
		out.writeString(afc);
		out.writeString(birthyear);
		out.writeString(birthmonth);
		out.writeString(fsh);
		out.writeString(mothermenopauseage);
		out.writeString(regularperiods);
		out.writeString(height);
		out.writeString(weight);
		out.writeString(datetime);
		out.writeString(resultindicator);
	}
	
	/**
	 * This is a constructor for de-serializing the object
	 */
	private UserInputValues(Parcel in) {
		this.setAge(in.readString());
		this.setAmhvolume(in.readString());
		this.setOvarianvolume(in.readString());
		this.setAfc(in.readString());
		this.setBirthYear(in.readString());
		this.setBirthMonth(in.readString());
		this.setFsh(in.readString());
		this.setMotherMenopauseAge(in.readString());
		this.setRegularPeriods(in.readString());
		this.setHeight(in.readString());
		this.setWeight(in.readString());
		this.setDateTime(in.readString());
		this.setResultIndicator(in.readString());
	}
	
	public static final Parcelable.Creator<UserInputValues> CREATOR = new Parcelable.Creator<UserInputValues>() {
		public UserInputValues createFromParcel(Parcel in) {
			return new UserInputValues(in);
		}
		public UserInputValues[] newArray(int size) {
			return new UserInputValues[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}

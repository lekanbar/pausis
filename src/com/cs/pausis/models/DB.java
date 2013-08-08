package com.cs.pausis.models;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
 
/**
 * This class instantiates the main SQLlite database which is used for storing the user data and all the statistical model values. 
 * 
 * Also this class facilitates the connection to the database for reading and writing.
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class DB { 
   public static final String DATABASE_NAME = "pausis.db";
   private static final int DATABASE_VERSION = 1;
   
   public static final String TABLE_TRACKER = "tracker";
   public static final String TABLE_USAGE = "usage_history";
   public static final String TABLE_AMH_LOOKUP = "amh_lookup";
   public static final String TABLE_AFC_LOOKUP = "afc_lookup";
   
   public static final String KEY_ID = "_id";
   
   //Columns for Usage history
   public static final String KEY_AGE = "age";
   public static final int AGE_COLUMN = 1;
   public static final String KEY_AMH_VOLUME = "amhvolume";
   public static final int AMH_VOLUME_COLUMN = 2;
   public static final String KEY_OVARIAN_VOLUME = "ovarianvolume";
   public static final int OVARIAN_VOLUME_COLUMN = 3;
   public static final String KEY_AF_COUNT = "afcount";
   public static final int AF_COUNT_COLUMN = 4;
   public static final String KEY_BIRTH_YEAR = "birthyear";
   public static final int BIRTH_YEAR_COLUMN = 5;
   public static final String KEY_BIRTH_MONTH = "birthmonth";
   public static final int BIRTH_MONTH_COLUMN = 6;
   public static final String KEY_FSH = "fsh";
   public static final int FSH_COLUMN = 7;
   public static final String KEY_MOTHER_MENOPAUSE_AGE = "menopauseage";
   public static final int MENOPAUSE_AGE_COLUMN = 8;
   public static final String KEY_REGULAR_PERIODS = "periods";
   public static final int REGULAR_PERIODS_COLUMN = 9;
   public static final String KEY_HEIGHT = "height";
   public static final int HEIGHT_COLUMN = 10;
   public static final String KEY_WEIGHT = "weight";
   public static final int WEIGHT_COLUMN = 11;
   public static final String KEY_DATETIME = "datetime";
   public static final int DATETIME_COLUMN = 12;
   public static final String KEY_RESULT_INDICATOR = "resultindicator";
   public static final int RESULT_INDICATOR_COLUMN = 13;
   
   //Columns for AMH Lookup
   public static final String KEY_LOG_ADJUSTED_PRED_AMH = "logadjustedpredamh";
   public static final int LOG_ADJUSTED_PRED_AMH_COLUMN = 2;
   public static final String KEY_95_PRED_LIM1 = "predlim1";
   public static final int KEY_95_PRED_LIM1_COLUMN = 3;
   public static final String KEY_95_PRED_LIM2 = "predlim2";
   public static final int KEY_95_PRED_LIM2_COLUMN = 4;
   
   //Columns for AFC Lookup
   public static final String KEY_5TH_PERCENTILE = "fifthpercentile";
   public static final int KEY_5TH_PERCENTILE_COLUMN = 2;
   public static final String KEY_25TH_PERCENTILE = "twentyfifthpercentile";
   public static final int KEY_25TH_PERCENTILE_COLUMN = 3;
   public static final String KEY_50TH_PERCENTILE = "fiftiethpercentile";
   public static final int KEY_50TH_PERCENTILE_COLUMN = 4;
   public static final String KEY_75TH_PERCENTILE = "seventyfifthpercentile";
   public static final int KEY_75TH_PERCENTILE_COLUMN = 5;
   public static final String KEY_95TH_PERCENTILE = "ninetyfifthpercentile";
   public static final int KEY_95TH_PERCENTILE_COLUMN = 6;
   
   //Columns for User Preferences table
   public static final String KEY_ISFIRSTTIME = "isfirsttime";
   public static final int ISFIRSTTIME_COLUMN = 1;
   public static final String KEY_TERMS_AGREED = "termsagreed";
   public static final int TERMS_AGREED_COLUMN = 2;
   public static final String KEY_EXTRA = "extra";
   public static final int EXTRA_COLUMN = 3;
   
   //Other Initializations
   private Context context;
   private SQLiteDatabase db;
   private OpenHelper dbHelper;
 
   /*
    * This is the main constructor
    */
   public DB(Context context) {
      this.context = context;
      dbHelper = new OpenHelper(this.context);
   }
   
   /**
    * Method for opening the database for reading or writing
    * 
    * @return
    * @throws SQLiteException
    */
   public DB open() throws SQLiteException {	   
	   try {
		   db = dbHelper.getWritableDatabase();
	   } catch (SQLiteException ex) {
		   db = dbHelper.getReadableDatabase();
	   }
	   return this;
   }
   
   /**
    * Method for closing the database connection
    */
   public void close() {
	   db.close();
   }
 
   /**
    * 
    * This is a helper class that facilitates the creation/upgrade of the database tables.
    * 
    * It extends the SQLiteOpenHelper
    *
    */
   public static class OpenHelper extends SQLiteOpenHelper {
 
      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }
      
      private static final String DATABASE_CREATE = "create table " + TABLE_TRACKER + " (" + KEY_ID + " integer primary key autoincrement, " + 
     		  KEY_ISFIRSTTIME + " text not null, " + KEY_TERMS_AGREED + " text not null, " + KEY_EXTRA + " text not null);";
      
      private static final String DATABASE_CREATE2 = "create table " + TABLE_USAGE + " (" + KEY_ID + " integer primary key autoincrement, " + 
    		  KEY_AGE + " text not null, " + KEY_AMH_VOLUME + " text not null, " + KEY_OVARIAN_VOLUME + " text not null, " + 
    		  KEY_AF_COUNT + " text not null, " + KEY_BIRTH_YEAR + " text not null, " + KEY_BIRTH_MONTH + " text not null, " + 
    		  KEY_FSH + " text not null, " + KEY_MOTHER_MENOPAUSE_AGE + " text not null, " + KEY_REGULAR_PERIODS + " text not null, " + 
    		  KEY_HEIGHT + " text not null, " + KEY_WEIGHT + " text not null, " + KEY_DATETIME + " text not null, " + 
    		  KEY_RESULT_INDICATOR + " text not null);";
      
      private static final String DATABASE_CREATE3 = "create table " + TABLE_AMH_LOOKUP + " (" + KEY_ID + " integer primary key autoincrement, " + 
    		  KEY_AGE + " text not null, " + KEY_LOG_ADJUSTED_PRED_AMH + " text not null, " + KEY_95_PRED_LIM1 + " text not null, " + 
    		  KEY_95_PRED_LIM2 + " text not null);";
      
      private static final String DATABASE_CREATE4 = "create table " + TABLE_AFC_LOOKUP + " (" + KEY_ID + " integer primary key autoincrement, " + 
    		  KEY_AGE + " text not null, " + KEY_5TH_PERCENTILE + " text not null, " + KEY_25TH_PERCENTILE + " text not null, " + 
    		  KEY_50TH_PERCENTILE + " text not null, " + KEY_75TH_PERCENTILE + " text not null, " + KEY_95TH_PERCENTILE + " text not null);";
 
      @Override
      public void onCreate(SQLiteDatabase db) {
         db.execSQL(DATABASE_CREATE);
         db.execSQL(DATABASE_CREATE2);
         db.execSQL(DATABASE_CREATE3);
         db.execSQL(DATABASE_CREATE4);
      }
 
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_USAGE);
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKER);
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_AMH_LOOKUP);
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_AFC_LOOKUP);
         onCreate(db);
      }
   }
}

package com.cs.pausis.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AMH {
	
	//Kelsey et al 2011
	//High Precision Polynomial Order 20
	//private double[] highPrecision = {6070, 0.460157176, 0.456975391, 0.250533758, 151.8960797};
	
	private BigDecimal[] amhModelParameters = {new BigDecimal(0.268973252734819), new BigDecimal(0.276929215744691), new BigDecimal(-0.187935754536451), new BigDecimal(-0.0298686809952054), 
												new BigDecimal(0.0685103384848424), new BigDecimal(-0.0299566581771031),
												new BigDecimal(0.00725379256607949), new BigDecimal(-0.00116185183747773), new BigDecimal(0.000132572801449005), 
												new BigDecimal(-0.0000112147896142044), new BigDecimal(7.20267962610566E-07), 
												new BigDecimal(-3.56280816279906E-08), new BigDecimal(1.36770126169061E-09), new BigDecimal(-4.08232757263641E-11),
												new BigDecimal(9.43766141083695E-13), new BigDecimal(-1.67214080125876E-14), new BigDecimal(2.22660762771979E-16),
												new BigDecimal(-2.15523415470886E-18), new BigDecimal(1.43098640088323E-20), new BigDecimal(-5.82661241989873E-23),
												new BigDecimal(1.0967733955294E-25)};
	
	private AMH loadedItem;
	private double age, observedAmhValue,
	               zScore, lapa,
	               pl1, pl2;
	private double[] sdvalues;
	Context context;
	
	public AMH(){
		
	}
	
	public AMH(Context context){
		this.context = context;
		age = 0.0;
		observedAmhValue = 0.0;
		zScore = 0.0;
		lapa = 0.0;
		pl1 = 0.0;
		pl2 = 0.0;
		sdvalues = new double[7];
		
		if(!check())
			initializeAMH();
	}
	
	public void calculateAMH() throws Exception{
		//Assert input values are valid before proceeding
		if(checkInputValues()){	
			//perform a look up of the age related values
			vLookUp();
			
			if(loadedItem != null){
				//Log Adjusted Obs AMH
				double logAdjustedObsAMH = Math.log10(this.getObservedAmhValue() + 1);
				
				//Log Adjusted Pred AMH
				double ageInput = this.getAge();
				double logAdjustedPredAMH = 0.0;
				for(int i = 0; i < amhModelParameters.length; i++) {
					logAdjustedPredAMH += (amhModelParameters[i].doubleValue() * Math.pow(ageInput, i));
				}
				
				double standardDeviation = (loadedItem.getPl2() - loadedItem.getLapa()) / 1.96;
				
				//Calculate zScore
				double calcValue = (logAdjustedObsAMH - logAdjustedPredAMH) / standardDeviation; 
				setZScore(calcValue);
				
				//Calculate SD values
				int count = 3;
				for (int i = 0; i < sdvalues.length; i++) {
					if(i < 3){
						sdvalues[i] = Math.pow(10, (logAdjustedPredAMH - count * standardDeviation)) - 1;
						count--;
					}
					else if(i == 3) {
						sdvalues[i] = Math.pow(10, (logAdjustedPredAMH)) - 1;
						count++;
					}
					else {
						sdvalues[i] = Math.pow(10, (logAdjustedPredAMH + count * standardDeviation)) - 1;
						count++;
					}						
				}
			}
			else{
				throw new Exception("Age does not exist in table.");
			}
		}
		else {
			throw new Exception("Invalid inputs entered.");
		}
	}
	
	public void initializeAMH(){
	    // Create a new row of values to insert.
	    // Insert the row.
		JSONObject json = null;
		String datax = "" ;
        try {
        	InputStream fIn = context.getAssets().open("amhlookup.json");
            InputStreamReader isr = new InputStreamReader (fIn);
            BufferedReader buffreader = new BufferedReader (isr);

            String readString = buffreader.readLine();
            while ( readString != null ){
                datax = datax + readString;
                readString = buffreader.readLine();
            }

            isr.close();
            
            json = new JSONObject(datax);
            JSONArray values = json.getJSONArray("LookupTable");
            SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);;
            
            for (int i = 0; i < values.length(); i++) {
    			JSONObject value = values.getJSONObject(i);
	                     
	            String sql = "INSERT INTO " + DB.TABLE_AMH_LOOKUP + "(" + DB.KEY_AGE + ", " + DB.KEY_LOG_ADJUSTED_PRED_AMH + ", " + DB.KEY_95_PRED_LIM1
	            		      + ", " + DB.KEY_95_PRED_LIM2 + ") VALUES('" + value.getString("Age") + "','" 
	                                                                      + value.getString("Log Adusted Pred AMH") + "','" 
	                                                                      + value.getString("95% Pred Lim1") + "','" 
	                                                                      + value.getString("95% Pred Lim2") + "');";
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
	
	public boolean check(){
	   SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DB.DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
	   
	   Cursor lookup = db.query(DB.TABLE_AMH_LOOKUP, null, null, null, null, null, null);
		   
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
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
    	String aged = df.format(this.getAge());
		Cursor value = db.query(true, DB.TABLE_AMH_LOOKUP,  null, DB.KEY_AGE + " = ?", new String[]{aged}, null, null, null, null);
		   
	    if ((value.getCount() == 0) || !value.moveToFirst()) {
	    	value.close();
		    db.close();
		    loadedItem = null;
	    }
	   
	    loadedItem = new AMH();
	    loadedItem.setLapa(Double.parseDouble(value.getString(DB.LOG_ADJUSTED_PRED_AMH_COLUMN)));
	    loadedItem.setPl1(Double.parseDouble(value.getString(DB.KEY_95_PRED_LIM1_COLUMN)));
	    loadedItem.setPl2(Double.parseDouble(value.getString(DB.KEY_95_PRED_LIM2_COLUMN)));
	    
	    value.close();
	    db.close();
    }
	
	private boolean checkInputValues() {
		if(this.getAge() <= 0.0)
			return false;
		if(this.getObservedAmhValue() <= 0.0)
			return false;
		
		return true;
	}
	
	public void setAge(double value){
		age = value;
	}
	public double getAge(){
		return age;
	}
	
	public void setObservedAmhValue(double value){
		observedAmhValue = value;
	}
	public double getObservedAmhValue(){
		return observedAmhValue;
	}
	
	public void setZScore(double value){
		zScore = value;
	}
	public double getZScore(){
		return zScore;
	}
	
	public void setLapa(double value){
		lapa = value;
	}
	public double getLapa(){
		return lapa;
	}
	
	public void setPl1(double value){
		pl1 = value;
	}
	public double getPl1(){
		return pl1;
	}
	
	public void setPl2(double value){
		pl2 = value;
	}
	public double getPl2(){
		return pl2;
	}

	public double[] getSdvalues() {
		return sdvalues;
	}
}

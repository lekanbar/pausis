package com.cs.pausis.controllers;

import java.util.ArrayList;
import java.util.Calendar;

import com.cs.pausis.MainActivity;
import com.cs.pausis.models.AFC;
import com.cs.pausis.models.AMH;
import com.cs.pausis.models.OvarianVolume;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class OvaryReserve_Calculator extends AsyncTask<String, Integer, Drawable>
{
	private Drawable d;
    Context context;
    int birthYear, birthMonth; 
    Double observedAMH, observedVolume, observedAFC;
    
    AMH amh;
    OvarianVolume ova;
    AFC afc;
	
    ArrayList<String> results = null;
    
	public OvaryReserve_Calculator(int birthYear, int birthMonth, Double observedAMH, Double observedVolume, Double observedAFC, Context context){
		this.birthYear = birthYear;
		this.birthMonth = birthMonth;
		this.observedAFC = observedAFC;
		this.observedAMH = observedAMH;
		this.observedVolume = observedVolume;
		
		this.context = context;
	}
	
    @Override
    protected Drawable doInBackground(String... args)
    {
        try
        {
            if (this.observedAMH != null) {
				amh = new AMH(this.context);
				amh.setAge(calculateAgeWithMonth());
				amh.setObservedAmhValue(this.observedAMH.doubleValue());
				amh.calculateAMH();
				
				((MainActivity)this.context).updateDialog();
			}
            if (this.observedVolume != null) {
            	ova = new OvarianVolume(this.context);
				ova.setAge(calculateAgeWithMonth());
				ova.setObservedVolume(this.observedAMH.doubleValue());
				ova.calculateOvarianVolume();
				
				((MainActivity)this.context).updateDialog();
			}
            if (this.observedAFC != null) {
            	afc = new AFC(this.context);
				afc.setAge(calculateAge());
				afc.calculateAFC();
            	
            	((MainActivity)this.context).updateDialog();
			}
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }// end of catch
        
        d = null;
        return d;
 	}

    @Override
    protected void onPostExecute(Drawable result){
    	
    	results = new ArrayList<String>();
    	try {
    		if(this.observedAMH != null)
    			results.add(String.valueOf(amh.getZScore()));
    		if(this.observedVolume != null)
    			results.add(String.valueOf(ova.getZScore()));
    		if(this.observedAFC != null)
    			results.add(String.valueOf(afc.getPercentile()));
    		
    		((MainActivity)this.context).done(results);
		} catch (Exception e) {
			((MainActivity)this.context).done(results);
		}
    }
    
    private double calculateAgeWithMonth(){
    	Calendar c = Calendar.getInstance();
		double calcage = (c.get(Calendar.YEAR) - this.birthYear) + (this.birthMonth/12);
    	
    	return calcage;
    }
    
    private int calculateAge(){
    	Calendar c = Calendar.getInstance();
		int calcage = c.get(Calendar.YEAR) - this.birthYear;
    	
    	return calcage;
    }
}

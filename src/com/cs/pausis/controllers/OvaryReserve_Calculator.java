package com.cs.pausis.controllers;

import java.util.ArrayList;
import java.util.Calendar;

import com.cs.pausis.MainActivity;
import com.cs.pausis.models.AFC;
import com.cs.pausis.models.AMH;
import com.cs.pausis.models.OvarianVolume;
import com.cs.pausis.models.Result;

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
	
    ArrayList<Result> results = null;
    
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
    	results = new ArrayList<Result>();
    	try {
    		Result result2;
    		
    		if(this.observedAMH != null){
    			result2 = new Result();
    			double zscore = amh.getZScore();
    			
    			if(zscore <= 0)
    				result2.setStatus(Result.Status.RED.toString());
    			else if(zscore >= 1  && zscore <= 2)
    				result2.setStatus(Result.Status.ORANGE.toString());
    			else
    				result2.setStatus(Result.Status.GREEN.toString());
    			
    			result2.setValue(String.valueOf(zscore));
    			result2.setType(Result.Type.AMH.toString());
    			
    			results.add(result2);
    		}
    		if(this.observedVolume != null){
    			result2 = new Result();
    			double zscore = ova.getZScore();
    			
    			if(zscore <= 0)
    				result2.setStatus(Result.Status.RED.toString());
    			else if(zscore >= 1  && zscore <= 2)
    				result2.setStatus(Result.Status.ORANGE.toString());
    			else
    				result2.setStatus(Result.Status.GREEN.toString());
    			
    			result2.setValue(String.valueOf(zscore));
    			result2.setType(Result.Type.OVA.toString());
    			
    			results.add(result2);
    		}
    		if(this.observedAFC != null){
    			result2 = new Result();
    			int percentile = afc.getPercentile();
    			
    			if(percentile == 5)
    				result2.setStatus(Result.Status.RED.toString());
    			else if(percentile == 25)
    				result2.setStatus(Result.Status.YELLOW.toString());
    			else if(percentile == 50 || percentile == 75)
    				result2.setStatus(Result.Status.ORANGE.toString());
    			else
    				result2.setStatus(Result.Status.GREEN.toString());
    			
    			result2.setValue(String.valueOf(percentile));
    			result2.setType(Result.Type.AFC.toString());
    			
    			results.add(result2);
    		}
    		
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

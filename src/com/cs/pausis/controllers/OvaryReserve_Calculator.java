package com.cs.pausis.controllers;

import java.util.ArrayList;
import java.util.Calendar;

import com.cs.pausis.MainActivity;
import com.cs.pausis.models.AFC;
import com.cs.pausis.models.AMH;
import com.cs.pausis.models.FSH;
import com.cs.pausis.models.MMAge;
import com.cs.pausis.models.OvarianVolume;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.UserInputValues;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class OvaryReserve_Calculator extends AsyncTask<String, Integer, Drawable>
{
	private Drawable d;
    Context context;
    int birthYear, birthMonth; 
    Double observedAMH, observedOvarianVolume, observedAFC, observedFSH,
           mothersMenopauseAge;
    
    AMH amh;
    OvarianVolume ova;
    AFC afc;
    FSH fsh;
    MMAge mmAge;
	
    ArrayList<Result> results = null;
    
	public OvaryReserve_Calculator(UserInputValues userInputValues, Context context){
		this.birthYear = Integer.parseInt(userInputValues.getBirthYear());
		this.birthMonth = Integer.parseInt(userInputValues.getBirthMonth());
		this.observedAFC = (!userInputValues.getAfc().equals("") ? Double.valueOf(userInputValues.getAfc()) : null);
		this.observedAMH = (!userInputValues.getAmhvolume().equals("") ? Double.valueOf(userInputValues.getAmhvolume()) : null);
		this.observedOvarianVolume = (!userInputValues.getOvarianvolume().equals("") ? Double.valueOf(userInputValues.getOvarianvolume()) : null);
		this.observedFSH = (!userInputValues.getFsh().equals("") ? Double.valueOf(userInputValues.getFsh()) : null);
		this.mothersMenopauseAge = (!userInputValues.getMotherMenopauseAge().equals("") ? Double.valueOf(userInputValues.getMotherMenopauseAge()) : null);
		
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
            if (this.observedOvarianVolume != null) {
            	ova = new OvarianVolume(this.context);
				ova.setAge(calculateAgeWithMonth());
				ova.setObservedVolume(this.observedAMH.doubleValue());
				ova.calculateOvarianVolume();
				
				((MainActivity)this.context).updateDialog();
			}
            if (this.observedAFC != null) {
            	afc = new AFC(this.context);
				afc.setAge(calculateAge());
				afc.setObservedAfcValue(this.observedAFC.doubleValue());
				afc.calculateAFC();
            	
            	((MainActivity)this.context).updateDialog();
			}
            if (this.observedFSH != null) {
            	fsh = new FSH(this.context);
				fsh.setAge(calculateAge());
				fsh.setObservedFsh(this.observedFSH.doubleValue());
				fsh.calculateFsh();
            	
            	((MainActivity)this.context).updateDialog();
			}
            if (this.mothersMenopauseAge != null) {
            	mmAge = new MMAge(this.context);
				mmAge.setChildage(calculateAge());
				mmAge.setMotherage(this.mothersMenopauseAge.doubleValue());
			    mmAge.calculateMMage();
            	
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
    			result2.setSdvalues(amh.getSdvalues());
    			
    			results.add(result2);
    		}
    		if(this.observedOvarianVolume != null){
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
    			result2.setSdvalues(ova.getSdvalues());
    			
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
    			result2.setSdvalues(afc.getSdvalues());
    			
    			results.add(result2);
    		}
    		if(this.observedFSH != null){
    			result2 = new Result();    			
    			result2.setValue(String.valueOf(fsh.getPercentage()));
    			result2.setType(Result.Type.FSH.toString());
    			//result2.setSdvalues(afc.getSdvalues());
    			
    			results.add(result2);
    		}
    		if(this.mothersMenopauseAge != null){
    			result2 = new Result();    			
    			result2.setValue(String.valueOf(mmAge.getPercentage()));
    			result2.setType(Result.Type.MMA.toString());
    			//result2.setSdvalues(afc.getSdvalues());
    			
    			results.add(result2);
    		}
    		
    		((MainActivity)this.context).done(results);
		} catch (Exception e) {
			((MainActivity)this.context).done(results);
		}
    }
    
    private double calculateAgeWithMonth(){
    	Calendar c = Calendar.getInstance();
		double calcage = (c.get(Calendar.YEAR) - this.birthYear), 
			   month = (this.birthMonth/12.0);
		calcage += month;
		
    	return calcage;
    }
    
    private int calculateAge(){
    	Calendar c = Calendar.getInstance();
		int calcage = c.get(Calendar.YEAR) - this.birthYear;
    	
    	return calcage;
    }
}

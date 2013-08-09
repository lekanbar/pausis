package com.cs.pausis.controllers;

import java.util.ArrayList;
import java.util.Calendar;

import com.cs.pausis.History;
import com.cs.pausis.MainActivity;
import com.cs.pausis.models.AFC;
import com.cs.pausis.models.AMH;
import com.cs.pausis.models.FSH;
import com.cs.pausis.models.MMAge;
import com.cs.pausis.models.NGF;
import com.cs.pausis.models.OvarianVolume;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.UserInputValues;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

/**
 * This is the main controller class that collates results from all the various factor classes (AMH, Ovarian Volume, Antral Follicle Count etc.) by
 * instantiating all the objects and calling the methods that do the execution.
 * 
 * This class operates on a separate thread from the main UI, so that the User interface remains responsive.
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class OvaryReserve_Calculator extends AsyncTask<String, Integer, Drawable>
{
	private Drawable d;
    Context context;
    int birthYear, birthMonth, pageType; 
    Double observedAMH, observedOvarianVolume, observedAFC, observedFSH,
           mothersMenopauseAge;
    
    AMH amh;
    OvarianVolume ova;
    AFC afc;
    FSH fsh;
    MMAge mmAge;
    NGF ngf;
    
    public static final int MAIN_TYPE = 1;
    public static final int SUMMARY_TYPE = 2;
	
    ArrayList<Result> results = null;
    
    /**
     * This is the main constructor of the class which gets the input values to be processed 
     * 
     * @param userInputValues
     * @param context
     * @param pageType
     */
	public OvaryReserve_Calculator(UserInputValues userInputValues, Context context, int pageType){
		this.birthYear = Integer.parseInt(userInputValues.getBirthYear());
		this.birthMonth = Integer.parseInt(userInputValues.getBirthMonth());
		this.observedAFC = (!userInputValues.getAfc().equals("") ? Double.valueOf(userInputValues.getAfc()) : null);
		this.observedAMH = (!userInputValues.getAmhvolume().equals("") ? Double.valueOf(userInputValues.getAmhvolume()) : null);
		this.observedOvarianVolume = (!userInputValues.getOvarianvolume().equals("") ? Double.valueOf(userInputValues.getOvarianvolume()) : null);
		this.observedFSH = (!userInputValues.getFsh().equals("") ? Double.valueOf(userInputValues.getFsh()) : null);
		this.mothersMenopauseAge = (!userInputValues.getMotherMenopauseAge().equals("") ? Double.valueOf(userInputValues.getMotherMenopauseAge()) : null);
		
		this.pageType = pageType;
		this.context = context;
	}
	
    @Override
    protected Drawable doInBackground(String... args)
    {
    	//Execute the various classes based on the input values specified
    	if(birthMonth != 0 && birthYear != 0){
    		try{
        		ngf = new NGF(this.context);
        		ngf.setAge(calculateAge());
        		ngf.calculateNgf();
        	}
			catch (Exception e)
	        {
				
	        }
    		
    		if(pageType == MAIN_TYPE)
				((MainActivity)this.context).updateDialog();
			else
				((History)this.context).updateDialog();
    	}        	
        if (this.observedAMH != null) {
        	try{
				amh = new AMH(this.context);
				amh.setAge(calculateAgeWithMonth());
				amh.setObservedAmhValue(this.observedAMH.doubleValue());
				amh.calculateAMH();
            }
			catch (Exception e)
	        {
				
	        }
			
			if(pageType == MAIN_TYPE)
				((MainActivity)this.context).updateDialog();
			else
				((History)this.context).updateDialog();
		}
        if (this.observedOvarianVolume != null) {
        	try{
            	ova = new OvarianVolume(this.context);
				ova.setAge(calculateAgeWithMonth());
				ova.setObservedVolume(this.observedAMH.doubleValue());
				ova.calculateOvarianVolume();
            }
			catch (Exception e)
	        {
				
	        }
			
			if(pageType == MAIN_TYPE)
				((MainActivity)this.context).updateDialog();
			else
				((History)this.context).updateDialog();
		}
        if (this.observedAFC != null) {
        	try {
            	afc = new AFC(this.context);
				afc.setAge(calculateAge());
				afc.setObservedAfcValue(this.observedAFC.doubleValue());
				afc.calculateAFC();
            }
			catch (Exception e)
	        {
				
	        }
        	
			if(pageType == MAIN_TYPE)
				((MainActivity)this.context).updateDialog();
			else
				((History)this.context).updateDialog();
		}
        if (this.observedFSH != null) {
        	try{
            	fsh = new FSH(this.context);
				fsh.setAge(calculateAge());
				fsh.setObservedFsh(this.observedFSH.doubleValue());
				fsh.calculateFsh();
        	}
			catch (Exception e)
	        {
				
	        }
        	
			if(pageType == MAIN_TYPE)
				((MainActivity)this.context).updateDialog();
			else
				((History)this.context).updateDialog();
		}
        if (this.mothersMenopauseAge != null) {
        	mmAge = new MMAge(this.context);
			mmAge.setChildage(calculateAge());
			mmAge.setMotherage(this.mothersMenopauseAge.doubleValue());
		    mmAge.calculateMMage();
        	
		    if(pageType == MAIN_TYPE)
				((MainActivity)this.context).updateDialog();
			else
				((History)this.context).updateDialog();
		}
        
        d = null;
        return d;
 	}

    @Override
    protected void onPostExecute(Drawable result){    	
    	results = new ArrayList<Result>();
    	try {
    		Result result2;
    		
    		//Collate results from the different classes
    		if(birthMonth != 0 && birthYear != 0){
    			result2 = new Result();    			
    			result2.setValue(String.valueOf(ngf.getPercentage()));
    			result2.setType(Result.Type.NGF.toString());
    			result2.setResultAvailable(ngf.isResultAvailable());
    			
    			results.add(result2);
    		}    		
    		if(this.observedAMH != null){//AMH result
    			result2 = new Result();
    			double zscore = amh.getZScore();
    			
    			if(zscore < 0)
    				result2.setStatus(Result.Status.RED.toString());
    			else if(zscore >= 0  && zscore < 2)
    				result2.setStatus(Result.Status.ORANGE.toString());
    			else
    				result2.setStatus(Result.Status.GREEN.toString());
    			
    			result2.setValue(String.valueOf(zscore));
    			result2.setType(Result.Type.AMH.toString());
    			result2.setResultAvailable(amh.isResultAvailable());
    			//result2.setSdvalues(amh.getSdvalues());
    			
    			results.add(result2);
    		}
    		if(this.observedOvarianVolume != null){//Ovarian Volume result
    			result2 = new Result();
    			double zscore = ova.getZScore();
    			
    			if(zscore < 0)
    				result2.setStatus(Result.Status.RED.toString());
    			else if(zscore >= 0  && zscore < 2)
    				result2.setStatus(Result.Status.ORANGE.toString());
    			else
    				result2.setStatus(Result.Status.GREEN.toString());
    			
    			result2.setValue(String.valueOf(zscore));
    			result2.setType(Result.Type.OVA.toString());
    			result2.setResultAvailable(ova.isResultAvailable());
    			//result2.setSdvalues(ova.getSdvalues());
    			
    			results.add(result2);
    		}
    		if(this.observedAFC != null){//AFC result
    			result2 = new Result();
    			int percentile = afc.getPercentile();
    			
    			if(percentile <= 5)
    				result2.setStatus(Result.Status.RED.toString());
    			else if(percentile == 25)
    				result2.setStatus(Result.Status.ORANGE.toString());
    			else if(percentile == 50 || percentile == 75)
    				result2.setStatus(Result.Status.YELLOW.toString());
    			else
    				result2.setStatus(Result.Status.GREEN.toString());
    			
    			result2.setValue(String.valueOf(percentile));
    			result2.setType(Result.Type.AFC.toString());
    			result2.setResultAvailable(afc.isResultAvailable());
    			//result2.setSdvalues(afc.getSdvalues());
    			
    			results.add(result2);
    		}
    		if(this.observedFSH != null){//FSH result
    			result2 = new Result();    			
    			result2.setValue(String.valueOf(fsh.getPercentage()));
    			result2.setType(Result.Type.FSH.toString());
    			result2.setResultAvailable(fsh.isResultAvailable());
    			//result2.setSdvalues(afc.getSdvalues());
    			
    			results.add(result2);
    		}
    		if(this.mothersMenopauseAge != null){
    			result2 = new Result();    			
    			result2.setValue(String.valueOf(mmAge.getPercentage()));
    			result2.setType(Result.Type.MMA.toString());
    			result2.setResultAvailable(mmAge.isResultAvailable());
    			//result2.setSdvalues(afc.getSdvalues());
    			
    			results.add(result2);
    		}
    		
    		//Run done immediately after
    		if(pageType == MAIN_TYPE)
    			((MainActivity)this.context).done(results);
			else
				((History)this.context).done(results);
		} catch (Exception e) {
			//Run done when an exception is caught
			if(pageType == MAIN_TYPE)
    			((MainActivity)this.context).done(results);
			else
				((History)this.context).done(results);
		}
    }
    
    /**
     * Method for calculating the age of the user down to the specific month
     * 
     * @return age (double)
     */
    private double calculateAgeWithMonth(){
    	Calendar c = Calendar.getInstance();
		double calcage = (c.get(Calendar.YEAR) - this.birthYear), 
			   month = (this.birthMonth/12.0);
		calcage += month;
		
    	return calcage;
    }
    
    /**
     * Method for calculating the age, just the number of years
     * 
     * @return age (double)
     */
    private int calculateAge(){
    	Calendar c = Calendar.getInstance();
		int calcage = c.get(Calendar.YEAR) - this.birthYear;
    	
    	return calcage;
    }
}

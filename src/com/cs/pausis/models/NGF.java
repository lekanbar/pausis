package com.cs.pausis.models;

import android.content.Context;

/**
 * This class facilitates the calculation of the NGF based on the input age
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * @since August, 2013
 * 
 */
public class NGF {
	
	private double Age,
	               Percentage;
	
	Context context;

	private boolean resultAvailable;
	
	/**
	 * The default constructor
	 */
	public NGF(){
		
	}
	
	/**
	 * Constructor that take in the context of the caller
	 * 
	 * @param context
	 */
	public NGF(Context context){
		this.context = context;
		Age = 0.0;
		Percentage = 40.0;
		resultAvailable = true;
	}
	
	/**
	 * Method for checking the user inputs before commencing calculations
	 * @return
	 */
	private boolean checkInputValues() {
		if(this.getAge() <= 0.0)
			return false;
		
		return true;
	}
	
	/**
	 * Method for processing the user inputs(i.e. age and observed AMh value) in order to perform the necessary lookup and calculations based on the model
	 * 
	 * @throws Exception
	 */
	public void calculateNgf() throws Exception{
		if(checkInputValues()){
			
		}
		else{
			throw new Exception("Required values were not specified.");
		}
	}

	/********************************************************************
	 * Getters and Setters
	 ************************************
	 */
	public double getAge() {
		return Age;
	}

	public void setAge(double age) {
		Age = age;
	}

	public double getPercentage() {
		return Percentage;
	}

	public void setPercentage(double percentage) {
		Percentage = percentage;
	}
	
	public boolean isResultAvailable() {
		return resultAvailable;
	}

	public void setResultAvailable(boolean resultAvailable) {
		this.resultAvailable = resultAvailable;
	}
}

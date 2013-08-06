package com.cs.pausis.models;

import android.content.Context;

/**
 * This class is for handling the Mother's age of Menopause statistical calculations
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * 
 */
public class MMAge {
	private double childage, motherage, percentage;	
	private String result;
	
	Context context;
	
	/**
	 * This is the default constructor for the BMI model
	 */
	public MMAge(){
		
	}
	
	/**
	 * Constructor that take in the context of the caller
	 * 
	 * @param context
	 */
	public MMAge(Context context){
		this.context = context;
		result = "MMAge percentage goes  here";
		percentage = 40.0;
	}
	
	/**
	 * Method for processing user inputs(i.e. age and observed Fsh value) in order to perform the necessary lookup and calculations based on the User's mother's age
	 * 
	 * @throws Exception
	 */
	public void calculateMMage() {
		
	}

	/********************************************************************
	 * Getters and Setters
	 ************************************
	 */
	public double getChildage() {
		return childage;
	}

	public void setChildage(double childage) {
		this.childage = childage;
	}

	public double getMotherage() {
		return motherage;
	}

	public void setMotherage(double motherage) {
		this.motherage = motherage;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}

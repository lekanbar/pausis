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
	
	public MMAge(Context context){
		this.context = context;
	}
	
	public void calculateMMage() {
		
	}

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

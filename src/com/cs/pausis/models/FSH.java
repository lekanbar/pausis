package com.cs.pausis.models;

import android.content.Context;

/** 
 * This class facilitates the calculation of the FSH results 
 * 
 * Article can be accessed at:
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * @since August, 2013
 * 
 */
public class FSH {
	private double Age,
	               ObservedFsh,
	               Percentage;
	private double[] sdvalues;
	Context context;
	private boolean resultAvailable;
	
	/**
	 * Default constructor
	 */
	public FSH(){
		
	}
	
	/**
	 * Constructor that take in the context of the caller
	 * 
	 * @param context
	 */
	public FSH(Context context){
		this.context = context;
		Age = 0.0;
		ObservedFsh = 0.0;
		Percentage = 40.0;
		sdvalues = new double[7];
		resultAvailable = true;
	}
	
	/**
	 * Method for checking the user's inputs
	 * 
	 * @return
	 */
	private boolean checkInputValues() {
		if(this.getAge() <= 0.0)
			return false;
		if(this.getObservedFsh() <= 0.0)
			return false;
		
		return true;
	}
	
	/**
	 * Method for processing user inputs(i.e. age and observed Fsh value) in order to perform the necessary lookup and calculations based on the model
	 * 
	 * @throws Exception
	 */
	public void calculateFsh() throws Exception{
		if(checkInputValues()){
			//if(this.getAge() > 30.0 && this.getObservedFsh() > 15)
				
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

	public double getObservedFsh() {
		return ObservedFsh;
	}

	public void setObservedFsh(double observedFsh) {
		ObservedFsh = observedFsh;
	}

	public double getPercentage() {
		return Percentage;
	}

	public void setPercentage(double percentage) {
		Percentage = percentage;
	}

	public double[] getSdvalues() {
		return sdvalues;
	}

	public void setSdvalues(double[] sdvalues) {
		this.sdvalues = sdvalues;
	}
	
	public boolean isResultAvailable() {
		return resultAvailable;
	}

	public void setResultAvailable(boolean resultAvailable) {
		this.resultAvailable = resultAvailable;
	}
}

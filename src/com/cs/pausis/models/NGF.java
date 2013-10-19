package com.cs.pausis.models;

import java.text.DecimalFormat;

import android.content.Context;

/**
 * This class facilitates the calculation of the NGF based on the input age
 * 
 * @author Olalekan Baruwa and Tom Kelsey
 * @email oab@st-andrews.ac.uk
 * @version 1.2
 * @since Oct, 2013
 * 
 */
public class NGF {
	
	private double Age,
	               Percentage,
	               Percentage2;
	// constants from Age8092percentage.xls - TK Aug 2013
			final double ALPHA =  1.302283826;
			final double BETA =  -1.302670052;
			final double GAMMA = 11.63827086;
			final double DELTA =  11.40317967;
			final double EPS = 2.638788548;
			
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
		Percentage = 0.0;
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
	 * Method taking age and returning solution of the Wallace-Kelsey ski slope model.
	 * See the Claus life of graft paper for derivation of the formula. 
	 * 
	 * @throws Exception
	 */
	public void calculateNgf() throws Exception{
		if(checkInputValues()){
			
			double t1 = Math.pow(2, 1/EPS);
			double t2 = DELTA*Math.log(t1 - 1);
			double t3 = this.getAge() + t2 - GAMMA;
			double t4 = t3/DELTA;
			double t5 = 1+ Math.exp(t4);
			double t6 = Math.pow(t5, -1*EPS);
			double t7 = BETA*(1 - t6);
			
			DecimalFormat dFormat = new DecimalFormat("##.#");
			this.setPercentage(Double.parseDouble(dFormat.format((ALPHA + t7)*100)));
			
		}
		else{
			throw new Exception("Required values were not specified.");
		}
	}
	
	/**
	 * Method taking age and returning NGF percent in three years time
	 * 
	 * @throws Exception
	 */
	public void calculateFutureNgf() throws Exception{
		if(checkInputValues()){
			
			double t1 = Math.pow(2, 1/EPS);
			double t2 = DELTA*Math.log(t1 - 1);
			double t3 = this.getAge() + 3 + t2 - GAMMA;
			double t4 = t3/DELTA;
			double t5 = 1+ Math.exp(t4);
			double t6 = Math.pow(t5, -1*EPS);
			double t7 = BETA*(1 - t6);
			
			DecimalFormat dFormat = new DecimalFormat("##.#");
			this.setPercentage2(Double.parseDouble(dFormat.format((ALPHA + t7)*100)));
			
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
	
	public double getPercentage2() {
		return Percentage2;
	}

	public void setPercentage2(double percentage) {
		Percentage2 = percentage;
	}	
	
	public boolean isResultAvailable() {
		return resultAvailable;
	}

	public void setResultAvailable(boolean resultAvailable) {
		this.resultAvailable = resultAvailable;
	}
}

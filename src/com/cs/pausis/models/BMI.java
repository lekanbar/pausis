package com.cs.pausis.models;

import android.content.Context;

/**
 * This class is for handling the body mass index calculations
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * 
 */
public class BMI {
	private double age, weight, height, bmi;
	
	private String result;
	Context context;
	
	/**
	 * This is the default constructor for the BMI model
	 */
	public BMI(){
		
	}
	
	public BMI(Context context){
		this.context = context;
	}
	
	public void calculateBMI() {
		double calvalue = weight / (Math.pow(getHeight(), 2));
		
		setBmi(calvalue);
	}
	
	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getBmi() {
		return bmi;
	}

	public void setBmi(double bmi) {
		this.bmi = bmi;
	}

	public String getResult() {
		return result;
	}
}

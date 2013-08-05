package com.cs.pausis.models;

import android.content.Context;

public class NGF {
	
	private double Age,
	               Percentage;
	Context context;
	
	public NGF(){
		
	}
	
	public NGF(Context context){
		this.context = context;
		Age = 0.0;
		Percentage = 40.0;
	}
	
	private boolean checkInputValues() {
		if(this.getAge() <= 0.0)
			return false;
		
		return true;
	}
	
	public void calculateNgf() throws Exception{
		if(checkInputValues()){
			
		}
		else{
			throw new Exception("Required values were not specified.");
		}
	}

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
}

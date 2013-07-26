package com.cs.pausis.models;

import android.content.Context;

public class OvarianVolume {
	private double[] ovarianModelParameters = {0.089238481, 0.110468789, -0.030505776, 0.005094503, -0.000434519, 0.0000248607385503967,
			                                   -0.000001228073192789340000000000, 0.000000054349899895093800000000, -0.000000001885656863491330000000,
			                                   0.000000000046563925396300600000, -0.000000000000787021809607684000, 0.000000000000008865632202665070, 
			                                   -0.000000000000000063602270361560, 0.000000000000000000262939134526, -0.000000000000000000000477026953};
	
	//private OvarianVolume loadedItem;
	private final double SD = 0.0878060117148268; 
	private double Age,
	               ObservedVolume,
	               ZScore;
	Context context;
	
	public OvarianVolume(){
		
	}
	
	public OvarianVolume(Context context){
		this.context = context;
		Age = 0.0;
		ObservedVolume = 0.0;
		ZScore = 0.0;
	}
	
	private boolean checkInputValues() {
		if(this.getAge() <= 0.0)
			return false;
		if(this.getObservedVolume() <= 0.0)
			return false;
		
		return true;
	}
	
	public void calculateOvarianVolume() throws Exception{
		if(checkInputValues()){
			//Log Adjusted Obs AMH
			double logAdjustedObsVolume = Math.log10(this.getObservedVolume() + 1);
			
			//Log Adjusted Pred AMH
			double logAdjustedPredVolume = 0.0;
			for(int i = 0; i < ovarianModelParameters.length; i++){
				logAdjustedPredVolume += (ovarianModelParameters[i] * Math.pow(this.getAge(), i));
			}
			
			//Calculate zScore
			double calcValue = (logAdjustedObsVolume - logAdjustedPredVolume) / SD;
			setZScore(calcValue);
		}
		else{
			throw new Exception("Age does not exist in table");
		}
	}

	public double getAge() {
		return Age;
	}

	public void setAge(double age) {
		Age = age;
	}

	public double getObservedVolume() {
		return ObservedVolume;
	}

	public void setObservedVolume(double observedVolume) {
		ObservedVolume = observedVolume;
	}

	public double getZScore() {
		return ZScore;
	}

	public void setZScore(double zScore) {
		ZScore = zScore;
	}
}

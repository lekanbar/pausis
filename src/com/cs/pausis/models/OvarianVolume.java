package com.cs.pausis.models;

import java.math.BigDecimal;

import android.content.Context;

/**
 * This model software representation was created based on the model developed by Kelsey et al
 * 
 * This class facilitates the calculation of the Ovarian volume results based on the statistical model by Kelsey et al 2011 
 * 
 * Article can be accessed at: http://www.ncbi.nlm.nih.gov/pubmed/20111701
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * @since August, 2013
 * 
 */
public class OvarianVolume {
	//Inorder to have more precise values the big decimal class was used
	private BigDecimal[] ovarianModelParameters = {new BigDecimal(0.0892384806470678), new BigDecimal(0.110468789001642), new BigDecimal(-0.0305057761704427), new BigDecimal(0.00509450267640864), 
			new BigDecimal(-0.000434519218703733), new BigDecimal(0.0000248607385503967),
			new BigDecimal(-1.22807319278934E-06), new BigDecimal(5.43498998950938E-08), new BigDecimal(-1.88565686349133E-09),
			new BigDecimal(4.65639253963006E-11), new BigDecimal(-7.87021809607684E-13), new BigDecimal(8.86563220266507E-15), 
			new BigDecimal(-6.36022703615603E-17), new BigDecimal(2.62939134526092E-19), new BigDecimal(-4.77026953014042E-22)};
	
	//private OvarianVolume loadedItem;
	private final double SD = 0.0878060117148268; 
	private double Age,
	               ObservedVolume,
	               ZScore;
	//private double[] sdvalues;
	Context context;

	private boolean resultAvailable;
	
	public OvarianVolume(){
		
	}
	
	/**
	 * Constructor that takes in the context of the caller
	 * 
	 * @param context
	 */
	public OvarianVolume(Context context){
		this.context = context;
		Age = 0.0;
		ObservedVolume = 0.0;
		ZScore = 0.0;
		//sdvalues = new double[7];
	}
	
	/**
	 * Method for checking the user inputs before commencing calculations
	 * @return true for good values or false otherwise
	 */
	private boolean checkInputValues() {
		if(this.getAge() <= 0.0)
			return false;
		if(this.getObservedVolume() < 0.0)
			return false;
		
		return true;
	}
	
	/**
	 * Method for processing the user inputs(i.e. age and observed Ovarian volume value) in order to perform the necessary lookup and calculations based on the model
	 * 
	 * @throws Exception
	 */
	public void calculateOvarianVolume() throws Exception{
		if(checkInputValues()){
			//Log Adjusted Obs AMH
			double logAdjustedObsVolume = Math.log10(this.getObservedVolume() + 1);
			
			//Log Adjusted Pred AMH
			double logAdjustedPredVolume = 0.0;
			for(int i = 0; i < ovarianModelParameters.length; i++) {
				logAdjustedPredVolume += (ovarianModelParameters[i].doubleValue() * Math.pow(this.getAge(), i));
			}
			
			//Calculate zScore
			double calcValue = (logAdjustedObsVolume - logAdjustedPredVolume) / SD;
			setZScore(calcValue);
			setResultAvailable(true);
		}
		else{
			setZScore(-3);//set to lowest value
			setResultAvailable(false);
			throw new Exception("Age does not exist in table");
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
	
	public boolean isResultAvailable() {
		return resultAvailable;
	}

	public void setResultAvailable(boolean resultAvailable) {
		this.resultAvailable = resultAvailable;
	}
}

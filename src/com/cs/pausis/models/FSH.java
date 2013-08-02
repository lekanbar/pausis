package com.cs.pausis.models;

import android.content.Context;

public class FSH {
	/*private BigDecimal[] ovarianModelParameters = {new BigDecimal(0.0892384806470678), new BigDecimal(0.110468789001642), new BigDecimal(-0.0305057761704427), new BigDecimal(0.00509450267640864), 
			new BigDecimal(-0.000434519218703733), new BigDecimal(0.0000248607385503967),
			new BigDecimal(-1.22807319278934E-06), new BigDecimal(5.43498998950938E-08), new BigDecimal(-1.88565686349133E-09),
			new BigDecimal(4.65639253963006E-11), new BigDecimal(-7.87021809607684E-13), new BigDecimal(8.86563220266507E-15), 
			new BigDecimal(-6.36022703615603E-17), new BigDecimal(2.62939134526092E-19), new BigDecimal(-4.77026953014042E-22)};*/
	
	//private OvarianVolume loadedItem;
	//private final double SD = 0.0878060117148268; 
	private double Age,
	               ObservedFsh,
	               Percentage;
	private double[] sdvalues;
	Context context;
	
	public FSH(){
		
	}
	
	public FSH(Context context){
		this.context = context;
		Age = 0.0;
		ObservedFsh = 0.0;
		Percentage = 40.0;
		sdvalues = new double[7];
	}
	
	private boolean checkInputValues() {
		if(this.getAge() <= 0.0)
			return false;
		if(this.getObservedFsh() <= 0.0)
			return false;
		
		return true;
	}
	
	public void calculateFsh() throws Exception{
		if(checkInputValues()){
			//Log Adjusted Obs AMH
			/*double logAdjustedObsVolume = Math.log10(this.getObservedFsh() + 1);
			
			//Log Adjusted Pred AMH
			double logAdjustedPredVolume = 0.0;
			for(int i = 0; i < ovarianModelParameters.length; i++) {
				logAdjustedPredVolume += (ovarianModelParameters[i].doubleValue() * Math.pow(this.getAge(), i));
			}
			
			//Calculate zScore
			double calcValue = (logAdjustedObsVolume - logAdjustedPredVolume) / SD;
			setPercentage(calcValue);
			
			//Calculate SD values
			int count = 3;
			for (int i = 0; i < sdvalues.length; i++) {
				if(i < 3) {
					sdvalues[i] = Math.pow(10, (logAdjustedPredVolume - count * SD)) - 1;
					count--;
				}
				else if(i == 3) {
					sdvalues[i] = Math.pow(10, (logAdjustedPredVolume)) - 1;
					count++;
				}
				else {
					sdvalues[i] = Math.pow(10, (logAdjustedPredVolume + count * SD)) - 1;
					count++;
				}						
			}*/
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
}

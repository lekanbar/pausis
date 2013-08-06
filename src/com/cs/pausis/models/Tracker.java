package com.cs.pausis.models;

/**
 * This is the main model class mainly used for tracking generic indicators within the app such as storing the record of user's privacy policy agreement,
 * it is a direct one-to-one mapping of the corresponding database table(TABLE_TRACKER).
 * 
 * This class implements the Parcelable interface, which makes the class portable for transportation from one activity 
 * to another through serialization and de-serialization.
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * @since August, 2013
 * 
 */
public class Tracker {
	private String iD,
				   isfirsttime,
				   termsagreed,
				   extra;

	/**
	 * Default class constructor 
	 */
	public Tracker(){
		iD = "";
		isfirsttime = "";
		termsagreed = "";
		extra = "";
	}
	
	/********************************************************************
	 * Getters and Setters
	 ************************************
	 */
	public void setID(String value){
		iD = value;
	}
	public String getID(){
		return iD;
	}
	
	public void setIsFirstTime(String value){
		isfirsttime = value;
	}
	public String getIsFirstTime(){
		return isfirsttime;
	}
	
	public void setTermsAgreed(String value){
		termsagreed = value;
	}
	public String getTermsAgreed(){
		return termsagreed;
	}
	
	public void setExtra(String value){
		extra = value;
	}
	public String getExtra(){
		return extra;
	}
}

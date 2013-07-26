		package com.cs.pausis.models;

public class UserPreference {
	private String iD,
				   isfirsttime,
				   termsagreed,
				   extra;

	public UserPreference(){
		iD = "";
		isfirsttime = "";
		termsagreed = "";
		extra = "";
	}
	
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

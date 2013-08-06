package com.cs.pausis.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is the main model class for all the results generated from the different models, all the results are kept in this model 
 * so as to allow for consistency in the presentation.
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
public class Result implements Parcelable {
	private String iD,
				   status,
				   value,
				   description,
				   type;
	//private double[] sdvalues;
	
	/**
	 * 
	 * This is an enumeration of all the different statuses that result objects can have
	 *
	 */
	public enum Status {
	   GREEN, ORANGE, RED, YELLOW
	}
	
	/**
	 * 
	 * This is an enumeration of all the different types of result
	 *
	 */
	public enum Type {
	   AFC, AMH, OVA, FSH, MMA, NGF
	}

	/**
	 * The default constructor
	 */
	public Result(){
		iD = "";
		status = "0";
		value = "0";
		description = "";
		type = "";
	}
	
	/********************************************************************
	 * Getters and Setters
	 ************************************
	 */
	public String getiD() {
		return iD;
	}

	public void setiD(String iD) {
		this.iD = iD;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*public double[] getSdvalues() {
		return sdvalues;
	}

	public void setSdvalues(double[] sdvalues) {
		this.sdvalues = sdvalues;
	}*/

	/**
	 * Overidden method for serializing the class attributes
	 */
	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(iD);
		out.writeString(status);
		out.writeString(value);
		out.writeString(type);
		
		/*if (this.getType().equals(Result.Type.AFC.toString()) || this.getType().equals(Result.Type.AMH.toString()) 
				|| this.getType().equals(Result.Type.OVA.toString()))
			out.writeDoubleArray(getSdvalues());*/
	}
	
	/**
	 * This is a constructor for de-serializing the object
	 */
	private Result(Parcel in) {
		this.setiD(in.readString());
		this.setStatus(in.readString());
		this.setValue(in.readString());
		this.setType(in.readString());
		
		/*if (this.getType().equals(Result.Type.AFC.toString()) || this.getType().equals(Result.Type.AMH.toString()) 
				|| this.getType().equals(Result.Type.OVA.toString())){
			double[] values = null;
			if (!this.getType().equals(Result.Type.AFC.toString()))
				values = new double[7];
			else
				values = new double[5];
			
			in.readDoubleArray(values);
			this.setSdvalues(values);
		}*/
	}
	
	public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
		public Result createFromParcel(Parcel in) {
			return new Result(in);
		}
		public Result[] newArray(int size) {
			return new Result[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}

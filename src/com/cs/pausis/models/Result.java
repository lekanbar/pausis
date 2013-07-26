package com.cs.pausis.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {
	private String iD,
				   status,
				   value,
				   description,
				   type;
	
	public enum Status {
	   GREEN, ORANGE, RED, YELLOW
	}
	
	public enum Type {
		AFC, AMH, OVA
	}

	public Result(){
		iD = "";
		status = "0";
		value = "0";
	}
	
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

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(iD);
		out.writeString(status);
		out.writeString(value);
	}
	
	private Result(Parcel in) {
		this.setiD(in.readString());
		this.setStatus(in.readString());
		this.setValue(in.readString());
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

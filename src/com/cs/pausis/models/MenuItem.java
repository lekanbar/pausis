package com.cs.pausis.models;

/** 
 * This is a model class for the menu items
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk
 * @version 1.0
 * @since August, 2013
 * 
 */
public class MenuItem {
	private int iD;//Menu ID

	public MenuItem(){
		iD = 0;
	}
	
	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}
}

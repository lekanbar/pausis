package com.cs.pausis;

import java.util.ArrayList;

import com.core.pausis.R;
import com.cs.pausis.controllers.OvaryReserve_Calculator;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.UserInputValues;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This Activity class facilitates the show-casing of the usage history, so that the user can view, reprocess, or delete the items.
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class History extends Activity {
	
	public boolean isfirsttime = true;
	ArrayList<UserInputValues> values;
	HistoryAdapter aa;
	ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		
		if(isfirsttime){
			InitializeUI();
			isfirsttime = false;
		}
	}
	
	/**
     * This method initializes the User Interface controls
     */
	public void InitializeUI(){
		//Get all usage history from DB
		values = new UserInputValues().getAllHistory(this);

		//if the database contains values the display the usage history items in the list
    	if(values.size() > 0)
    	{
    		ListView historyList = (ListView)findViewById(R.id.list);
    		int resID = R.layout.expandable_list_item2;
	        aa = new HistoryAdapter(History.this, resID, values);
	        historyList.setAdapter(aa);
	        
	        //Set the action listener for the list
	        historyList.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            	final int pos = position;
	            	
	            	//Display the alert dialog which displays options to the user   
	            	AlertDialog.Builder alert = new AlertDialog.Builder(History.this);
	    			alert.setTitle(R.string.menu);
	    			alert.setMessage(R.string.todo);
	    			alert.setNegativeButton(R.string.view, new DialogInterface.OnClickListener(){
	    				//View button click
	    				public void onClick(DialogInterface arg0, int arg1) {
	    					UserInputValues userInputValues = values.get(pos);
	    	            	
	    	            	//Show dialog to indicate progress
	    					dialog = new ProgressDialog(History.this);
	    			        dialog.setTitle(getString(R.string.calcing));
	    			        dialog.setMessage(getString(R.string.plswait));
	    			        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	    			        dialog.setProgress(0);
	    			        dialog.setMax(60);
	    			        dialog.show();
	    			        
	    	            	OvaryReserve_Calculator ovcalc = new OvaryReserve_Calculator(userInputValues, History.this, OvaryReserve_Calculator.SUMMARY_TYPE);
	    			        ovcalc.execute("");
	    				}
	    			});
	    			alert.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener(){
	    				//Delete button click
	    				public void onClick(DialogInterface arg0, int arg1) {
	    					AlertDialog.Builder alert = new AlertDialog.Builder(History.this);
	    					alert.setTitle(R.string.delete);
	    					alert.setMessage(R.string.delItem);
	    					alert.setNegativeButton(R.string.delete, new AlertDialog.OnClickListener(){
	    		
	    						public void onClick(DialogInterface arg0, int arg1) {
	    							UserInputValues userInputValues = values.get(pos);	    							
	    							userInputValues.removeHistory(Long.parseLong(userInputValues.getID()), History.this);
	    							InitializeUI();
	    						}
	    					});
	    					alert.setPositiveButton(R.string.goback, new AlertDialog.OnClickListener(){
	    		
	    						public void onClick(DialogInterface arg0, int arg1) {
	    							
	    						}
	    					});
	    					alert.show();
	    				}
	    			});
	    			alert.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener(){
	    				//Cancel button click do nothing
	    				public void onClick(DialogInterface arg0, int arg1) {
	    					
	    				}
	    			});
	    			alert.show();
	            }
	        });
    	}
	    else{//Show alert, if there are no items in the database (i.e. no usage history)
	    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Oops");
			alert.setMessage(R.string.nousage);
			alert.setPositiveButton(R.string.ok, new OnClickListener(){
	
				public void onClick(DialogInterface arg0, int arg1) {
					finish();//close activity on click
				}
			});
			alert.show();
	    }
	}
	
	/**
	 * Method for getting the results from the ovary reserve calculator
	 * 
	 * @param results - The returned array list of results
	 */
	public void done(ArrayList<Result> results){
		dialog.setProgress(dialog.getProgress() + 10);
		
		//If there are results, go to the summary page
		if(results.size() > 0){
			//Go to summary page
			Intent i = new Intent(History.this, Summary.class);
        	i.putExtra("results", results);
			startActivity(i);
		}
		else {//Tell the user there are no results generated for the inputs given
			AlertDialog.Builder alert = new AlertDialog.Builder(History.this);
			alert.setTitle(R.string.resulttitle);
			alert.setMessage(R.string.resultmessage);
			alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){

				public void onClick(DialogInterface arg0, int arg1) {
					
				}
			});
			alert.show();
		}
		
		//close dialog
		dialog.dismiss();
	}
	
	public void updateDialog(){
		dialog.setProgress(dialog.getProgress() + 10);
	}
}

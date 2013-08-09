package com.cs.pausis;

import java.util.ArrayList;

import com.core.pausis.R;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.UserInputValues;
import com.cs.pausis.models.Tracker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * This is the activity class that facilitates summary page for the generated results. 
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
 public class Summary extends Activity {	 
	 Tracker pref;
	 UserInputValues usage;
	 static boolean isfirsttime = true;
	 static boolean isviolated = false;
	 double result;
	 ArrayList<Result> results;
	 ResultAdapter adapter;
	 
	 Result fshResult, mmaResult, ngfResult;
	 
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          
		setContentView(R.layout.summary);
	
		InitializeUI();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    // Create and add new menu items.
	    MenuItem itemHis = menu.add(0, Menu.FIRST, Menu.NONE, R.string.history);
	    itemHis.setShortcut('1', 'h'); itemHis.setIcon(R.drawable.history);
	    
	    MenuItem itemTop = menu.add(0, Menu.FIRST + 1, Menu.NONE, R.string.aboutus);
	    itemTop.setShortcut('2', 'a'); itemTop.setIcon(R.drawable.about_us);
	    
	    MenuItem itemPri = menu.add(0, Menu.FIRST + 2, Menu.NONE, R.string.privacytitle);
	    itemPri.setShortcut('3', 'p'); itemPri.setIcon(R.drawable.privacypolicy);
	    return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    super.onOptionsItemSelected(item);
	    switch (item.getItemId()) {
		    case (Menu.FIRST): {
		    	Intent i = new Intent(getApplicationContext(), History.class);
				startActivity(i);
		    	return true;
		    }
		    case (Menu.FIRST + 1): {
		    	Intent i = new Intent(getApplicationContext(), AboutUs.class);
				startActivity(i);
		    	return true;
		    }
		    case (Menu.FIRST + 2): {
			    Intent i = new Intent(getApplicationContext(), PrivacyInfo.class);
				startActivityForResult(i, 0);
		    	return true;
		    }
		    default: return false;
	    }
    }
    
    /**
     * This method initializes the User Interface controls
     */
    private void InitializeUI(){
    	//get the array of results
    	results = getIntent().getExtras().getParcelableArrayList("results");
    	
    	String fshresultString = "", mmaresultString = "", ngfresultString = "";
    	
    	//Check for the FSH, NGF and MMA results and remove them from the list 
    	for(int i = 0;i < results.size();i++){
    		if(results.get(i).getType().equals(Result.Type.FSH.toString())){
    			fshresultString = getString(R.string.fshresult) + results.get(i).getValue() + "%";
    			fshResult = results.get(i);
    		}
    		else if(results.get(i).getType().equals(Result.Type.MMA.toString())) {
    			mmaresultString = getString(R.string.mmaresult) + results.get(i).getValue() + "%";
    			mmaResult = results.get(i);
			}
    		else if(results.get(i).getType().equals(Result.Type.NGF.toString())) {
    			ngfresultString = getString(R.string.ngfresult) + results.get(i).getValue() + "%";
    			ngfResult = results.get(i);
			}
    	}
    	
    	//FSH result information
    	if(!fshresultString.equals("")){
    		ImageView imgFsh = (ImageView)findViewById(R.id.imgFSH);
    		imgFsh.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(), InformationPage.class);
					i.putExtra("type", InformationPage.SUMMARY_TYPE);
					i.putExtra("result", fshResult);
					startActivity(i);
				}
			});
    		
	    	TextView txtFshResult = (TextView)findViewById(R.id.txtFshResult);
	    	txtFshResult.setText(fshresultString);
	    	
	    	//Remove fsh from the list
	    	results.remove(fshResult);
    	}
    	else {
    		RelativeLayout layfsh = (RelativeLayout)findViewById(R.id.layfsh);
			layfsh.setVisibility(View.GONE);
		}
    	
    	//MMA result information
    	if(!mmaresultString.equals("")){
    		ImageView imgMma = (ImageView)findViewById(R.id.imgMMA);
    		imgMma.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(), InformationPage.class);
					i.putExtra("type", InformationPage.SUMMARY_TYPE);
					i.putExtra("result", mmaResult);
					startActivity(i);
				}
			});
    		
	    	TextView txtMMAResult = (TextView)findViewById(R.id.txtMMAResult);
	    	txtMMAResult.setText(mmaresultString);
	    	
	    	//Remove mma from the list
	    	results.remove(mmaResult);
    	}
    	else {
    		RelativeLayout laymma = (RelativeLayout)findViewById(R.id.laymma);
			laymma.setVisibility(View.GONE);
		}
    	
    	//FSH result information
    	if(!ngfresultString.equals("")){
    		ImageView imgNgf = (ImageView)findViewById(R.id.imgNGF);
    		imgNgf.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(), InformationPage.class);
					i.putExtra("type", InformationPage.SUMMARY_TYPE);
					i.putExtra("result", ngfResult);
					startActivity(i);
				}
			});
    		
	    	TextView txtNgfResult = (TextView)findViewById(R.id.txtNGFResult);
	    	txtNgfResult.setText(ngfresultString);
	    	
	    	//Remove fsh from the list
	    	results.remove(ngfResult);
    	}
    	else {
    		RelativeLayout layfsh = (RelativeLayout)findViewById(R.id.layfsh);
			layfsh.setVisibility(View.GONE);
		}
    	
    	//Set up the expandable list
    	adapter = new ResultAdapter(this, R.layout.expandable_list_item, results);
    	ActionSlideExpandableListView list = (ActionSlideExpandableListView)this.findViewById(R.id.list);
    	// fill the list with data
		list.setAdapter(adapter);
		list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

			@Override
			public void onClick(View listView, View buttonview, int position) {
				Result result = results.get(position);
				
				if(buttonview.getId() == R.id.buttonA) {
					Intent intent = new Intent(Summary.this, ResultVisualizer.class);
			        intent.putExtra("result", result);
		      	    startActivity(intent);
				} else {
					Intent i = new Intent(getApplicationContext(), InformationPage.class);
					i.putExtra("type", InformationPage.SUMMARY_TYPE);
					i.putExtra("result", result);
					startActivity(i);
				}
			}
		}, R.id.buttonA, R.id.buttonB);
		
		Button cmdOverall_button = (Button)findViewById(R.id.cmdOverall);
		cmdOverall_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), OverAllAdvice.class);
				i.putExtra("results", results);
				startActivity(i);
			}        	
        });
	}
    
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
        setContentView(R.layout.summary);
		InitializeUI();
    }
}

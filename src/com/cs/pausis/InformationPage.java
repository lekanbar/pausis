package com.cs.pausis;

import com.core.pausis.R;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.Tracker;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This is an activity class that facilitates the display of information to the user
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
 public class InformationPage extends Activity {
	 
	 Tracker pref;
	 Result result;
	 int type;
	 
	 //Page type constants for choosing app theme
	 public static final int MAIN_PAGE_TYPE = 1;
	 public static final int SUMMARY_TYPE = 2;
	 
	 public static final int AMH_INFO = 1;
	 public static final int AFC_INFO = 2;
	 public static final int OVARIAN_VOL_INFO = 3;
	 public static final int FSH_INFO = 4;
	 public static final int BMI_INFO = 5;
	 public static final int MMAge_INFO = 6;
	 public static final int PERIOD_INFO = 7;
	 public static final int NGF_INFO = 8;
	 
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	type = getIntent().getIntExtra("type", 0);
    	
    	if(type == MAIN_PAGE_TYPE)
    		setTheme(android.R.style.Theme_Dialog);    		
    	
        super.onCreate(savedInstanceState);
          	
    	setContentView(R.layout.infopage);
    	
    	InitializeUI();
    }
    
    /**
     * This method initializes the User Interface controls
     */
    private void InitializeUI(){
    	int infotype = 0; //This variable helps to know which information is requested
    	
    	//If the request is coming from the main page get the information request type directly from the intent
    	if(type == MAIN_PAGE_TYPE)
    		infotype = getIntent().getIntExtra("infotype", 0);
    	else {//else get it from the result object sent into the intent, usually from the summary page
			result = getIntent().getParcelableExtra("result");
			
			if(result.getType().equals(Result.Type.AFC.toString()))
				infotype = AFC_INFO;
			else if(result.getType().equals(Result.Type.AMH.toString()))
				infotype = AMH_INFO;
			else if(result.getType().equals(Result.Type.FSH.toString()))
				infotype = FSH_INFO;
			else if(result.getType().equals(Result.Type.MMA.toString()))
				infotype = MMAge_INFO;
			else if(result.getType().equals(Result.Type.OVA.toString()))
				infotype = OVARIAN_VOL_INFO;
			else if(result.getType().equals(Result.Type.NGF.toString()))
				infotype = NGF_INFO;
		}
    	
    	//Switch between the different information types and display as appropriate
    	if (infotype == AMH_INFO) {
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.amhvalue));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
    	else if (infotype == NGF_INFO) {
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.ngfvalue));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
		else if (infotype == OVARIAN_VOL_INFO) {
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.volvalue));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
		else if (infotype == AFC_INFO) {
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.afcvalue));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
		else if (infotype == FSH_INFO) {
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.fshvalue));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
		else if (infotype == MMAge_INFO) {
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.menopause2));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
		else if (infotype == PERIOD_INFO) {
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.periods));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
    	else if(infotype == BMI_INFO){
    		TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.bmi));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
    	}
    	
    	//Set the action listener for the button 
    	Button cmdSend_button = (Button)findViewById(R.id.cmdOk);
    	cmdSend_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				finish();
			}
    	});
    }
    
    /**
     * Handles the orientation change 
     */
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	setContentView(R.layout.infopage);
    }
}

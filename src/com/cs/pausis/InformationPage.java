package com.cs.pausis;

import com.core.pausis.R;
import com.cs.pausis.models.UserPreference;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

 public class InformationPage extends Activity {
	 
	 UserPreference pref;
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
    
    private void InitializeUI(){
    	int infotype = getIntent().getIntExtra("infotype", 0);
    	//Result result = getIntent().getParcelableExtra("result");
    	
    	if (infotype == AMH_INFO) {
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.amhvalue));
			
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
    	
    	Button cmdSend_button = (Button)findViewById(R.id.cmdOk);
    	cmdSend_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				finish();
			}
    	});
    }
    
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	setContentView(R.layout.infopage);
    }
}

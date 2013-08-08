package com.cs.pausis;

import java.util.ArrayList;

import com.core.pausis.R;
import com.cs.pausis.models.Result;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This is an activity that facilitates the show-casing of the overall advice based on the results generated
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
 public class OverAllAdvice extends Activity {
	 
	 ArrayList<Result> results;
	 
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          	
    	setContentView(R.layout.overalladvice);
    	
    	InitializeUI();
    }
    
    /**
     * Initialize the User Interface controls
     */
    private void InitializeUI(){
    	results = getIntent().getExtras().getParcelableArrayList("results");
    	
    	Button cmdOk = (Button)findViewById(R.id.cmdOk);
        cmdOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	finish();
            }
        });
    }
    
    /**
     * Handles the orientation change
     */
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	setContentView(R.layout.overalladvice);
    }
}

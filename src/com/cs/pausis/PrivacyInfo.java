package com.cs.pausis;

import com.core.pausis.R;
import com.cs.pausis.models.Tracker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This is an activity class that facilitates the display of the application's privacy policy 
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
 public class PrivacyInfo extends Activity {
	 
	 Tracker pref;
	 
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          	
    	setContentView(R.layout.privacyinfo);
    	
    	InitializeUI();
    }
    
    /**
     * This method initializes the User Interface controls.
     */
    private void InitializeUI(){
    	//Set up the privacy information
    	TextView lblPrivacy = (TextView)findViewById(R.id.lblPrivacy);
    	lblPrivacy.setText(Html.fromHtml(getString(R.string.privacy) + getString(R.string.privacy2)));
    	/*
    	 * + "<a href=\"http://www.plosmedicine.org/article/info%3Adoi%2F10.1371%2Fjournal.pmed.1000386\">"
        + getString(R.string.paper) + "</a>" 
    	 */
    	lblPrivacy.setMovementMethod(LinkMovementMethod.getInstance());//Ensure the web browser is opened when the lick is clicked
    	
    	//Set the listener for the proceed button
    	Button cmdProceed_button = (Button)findViewById(R.id.cmdProceed);
    	cmdProceed_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Tracker track = new Tracker(PrivacyInfo.this);
		    	pref = track.getTracker(1);
		    	
		    	if(pref == null){
		    		//Store the first time use information page into the DB
		    		pref = new Tracker(PrivacyInfo.this);
			    	pref.setTermsAgreed("true");
			    	pref.setIsFirstTime("false");
			    	pref.setExtra("");
			    	pref.insertTracker();
			    	
			    	//Go to the main menu page
			    	Intent i = new Intent(getApplicationContext(), MainMenu.class);
					startActivity(i);
					finish();
		    	}
		    	else{
		    		finish();
		    	}
			}
    	});
    	
    	//Set the listener for the proceed button
    	Button cmdExit_button = (Button)findViewById(R.id.cmdExit);
    	cmdExit_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
    	});
    }
    
    /**
     * Handle the screen orientation change
     */
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	setContentView(R.layout.privacyinfo);
        InitializeUI();
    }
}

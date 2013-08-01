package com.cs.pausis;

import com.core.pausis.R;
import com.cs.pausis.models.DB;
import com.cs.pausis.models.UserPreference;

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

 public class PrivacyInfo extends Activity {
	 
	 UserPreference pref;
	 
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          	
    	setContentView(R.layout.privacyinfo);
    	
    	InitializeUI();
    }
    
    private void InitializeUI(){
    	TextView lblPrivacy = (TextView)findViewById(R.id.lblPrivacy);
    	lblPrivacy.setText(Html.fromHtml(getString(R.string.privacy) + "<a href=\"http://www.plosmedicine.org/article/info%3Adoi%2F10.1371%2Fjournal.pmed.1000386\">"
        + getString(R.string.paper) + "</a>" + getString(R.string.privacy2)));
    	lblPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
    	
    	Button cmdSend_button = (Button)findViewById(R.id.cmdProceed);
    	cmdSend_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				DB db = new DB(PrivacyInfo.this);
		        db.open();
		    	pref = db.getPreference(1);
		    	
		    	if(pref == null){
		    		pref = new UserPreference();
			    	pref.setTermsAgreed("true");
			    	pref.setIsFirstTime("false");
			    	pref.setExtra("");
			    	db.insertUserPreference(pref);
			    	db.close();
			    	
			    	Intent i = new Intent(getApplicationContext(), MainMenu.class);
					startActivity(i);
					finish();
		    	}
		    	else{
		    		db.close();
		    		finish();
		    	}
			}
    	});
    }
    
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	if (_newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
    		setContentView(R.layout.privacyinfo);
    		InitializeUI();
        }
    	if (_newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
    		setContentView(R.layout.privacyinfo);
    		InitializeUI();
        }
    }
}

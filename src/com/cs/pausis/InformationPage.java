package com.cs.pausis;

import com.core.pausis.R;
import com.cs.pausis.models.UserPreference;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

 public class InformationPage extends Activity {
	 
	 UserPreference pref;
	 
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          	
    	setContentView(R.layout.infopage);
    	
    	InitializeUI();
    }
    
    private void InitializeUI(){
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

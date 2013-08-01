package com.cs.pausis;

import com.core.pausis.R;
import com.cs.pausis.models.UserPreference;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

 public class GeneralAdvice extends Activity {
	 
	 UserPreference pref;
	 
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          	
    	setContentView(R.layout.generaladvice);
    	
        Button cmdOk = (Button)findViewById(R.id.cmdOk);
        cmdOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	finish();
            }
        });
    }
    
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	setContentView(R.layout.generaladvice);
    }
}

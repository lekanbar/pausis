package com.cs.pausis;

import com.core.pausis.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This Activity class shows the About Us page by 
 * displaying the profile of the development team and setting the links to their full profiles. 
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 *
 */
 public class AboutUs extends Activity {
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          	
    	setContentView(R.layout.aboutus);
    	
    	InitializeUI();
    }
    
    private void InitializeUI(){
    	Button cmdSend_button = (Button)findViewById(R.id.cmdOk);
    	cmdSend_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				finish();
			}
    	});
    	
    	TextView lblLearnMore1 = (TextView)findViewById(R.id.readmore1);
    	lblLearnMore1.setText(Html.fromHtml("<a href=\"http://www.gla.ac.uk/schools/medicine/staff/scottnelson/\">"
                              + getString(R.string.readmore) + "</a>"));
    	lblLearnMore1.setMovementMethod(LinkMovementMethod.getInstance());
    	
    	TextView lblLearnMore2 = (TextView)findViewById(R.id.readmore2);
    	lblLearnMore2.setText(Html.fromHtml("<a href=\"http://www.cs.st-andrews.ac.uk/~tom/\">"
                              + getString(R.string.readmore) + "</a>"));
    	lblLearnMore2.setMovementMethod(LinkMovementMethod.getInstance());
    	
    	TextView lblLearnMore3 = (TextView)findViewById(R.id.readmore3);
    	lblLearnMore3.setText(Html.fromHtml("<a href=\"http://uk.linkedin.com/in/lekanbaruwa/\">"
                              + getString(R.string.readmore) + "</a>"));
    	lblLearnMore3.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	setContentView(R.layout.aboutus);
    	InitializeUI();
    }
}

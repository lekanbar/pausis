package com.cs.pausis;

import java.util.Calendar;

import com.core.pausis.R;
import com.cs.pausis.models.AFC;
import com.cs.pausis.models.AMH;
import com.cs.pausis.models.DB;
import com.cs.pausis.models.UserPreference;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

 public class SplashScreen extends Activity {
	 
	 UserPreference pref;
	 ProgressDialog  dialog;
	 
	 public SplashScreen(){
		 super();
     }
    
    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Splash screen view
        setContentView(R.layout.splash);
        
        //Set copyright information
        Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		
        TextView lblCopyright = (TextView)findViewById(R.id.lblCopyright);
        lblCopyright.setText(getString(R.string.copyright) + year + " ");
        
        TextView lblName = (TextView)findViewById(R.id.lblName);
        lblName.setText(Html.fromHtml("<a href=\"http://uk.linkedin.com/in/lekanbaruwa/\">" + getString(R.string.name) + "</a>"));
        lblName.setMovementMethod(LinkMovementMethod.getInstance());
        
        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        wait(3000);
                    }
                }
                catch(InterruptedException ex){                    
                }
                
                GoToNext();
            }
        };
        mSplashThread.start();
    }
    
    Runnable showDialog = new Runnable() {
	    @Override
	    public void run() {
	    	dialog = new ProgressDialog(SplashScreen.this);
	        dialog.setTitle(getString(R.string.firstusetitle));
	        dialog.setMessage(getString(R.string.firstuse));
	        dialog.setIndeterminate(true);
	        dialog.show();
	    }
    };
    
    Runnable dismissDialog = new Runnable() {
	    @Override
	    public void run() {
	    	dialog.dismiss();
	    }
    };
    
    //Go to next
    public void GoToNext(){
    	// Run next activity
    	DB db = new DB(this);
        db.open();
    	pref = db.getPreference(1);
    	db.close();    	
    	
    	if(pref == null){
    		runOnUiThread(showDialog);
	        
    		//Run all necessary bootstrap for app's first time use
    		AMH amh = new AMH(SplashScreen.this);
    		if(!amh.check()){
    	        //initialize amh preset values from json
    			amh.initializeAMH();
    		}
    		
    		AFC afc = new AFC(SplashScreen.this);
    		if(!afc.check()){    	        
    	        //initialize antral-follicle count preset values from json
    			afc.initializeAFC();
    		}
    		runOnUiThread(dismissDialog);
    		
    		Intent i = new Intent(getApplicationContext(), PrivacyInfo.class);
			startActivity(i);
			finish();
    	}
    	else{
    		Intent i = new Intent(getApplicationContext(), MainMenu.class);
			startActivity(i);
			finish();
    	}
    }
    
    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN || evt.getAction() == MotionEvent.ACTION_UP ||
           evt.getAction() == MotionEvent.ACTION_POINTER_DOWN || evt.getAction() == MotionEvent.ACTION_POINTER_UP){
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        
        return true;
    }
    
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	setContentView(R.layout.splash);
    }
}

package com.cs.pausis;

import com.core.pausis.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;

/**
 * This is the activity class that facilitates the show-casing of the web page. 
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class WebPage extends Activity{
	
	ScrollView mScrollView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.infopage);
    	
    	InitializeUI();
	}
	
	public void InitializeUI(){
		setTitle(getString(R.string.info));
		
		//WebView mWebView2 = (WebView) findViewById(R.id.webview1);		 		           
		//mWebView2.loadUrl("file:///android_asset/about.html");
		
		Button cmdSend_button = (Button)findViewById(R.id.cmdOk);
    	cmdSend_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				finish();
			}
    	});
    }
	
	public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
		setContentView(R.layout.webpage);
		InitializeUI();
    }
	
	@Override
    public void onDestroy() {
    	super.onDestroy();
    }
}

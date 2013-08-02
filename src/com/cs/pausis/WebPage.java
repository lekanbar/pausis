package com.cs.pausis;

import com.core.pausis.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class WebPage extends Activity{
	
	ScrollView mScrollView;
	
	public static final int AMH_INFO = 1;
	public static final int AFC_INFO = 2;
	public static final int FSH_INFO = 3;
	public static final int MMA_INFO = 4;
	public static final int PERIOD_INFO = 5;
	public static final int OVARIAN_VOL_INFO = 6;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.infopage);
    	
    	InitializeUI();
	}
	
	public void InitializeUI(){
		setTitle(getString(R.string.info));
		
		int page = getIntent().getIntExtra("page", 0);
		
		if (page == AMH_INFO) {
			//WebView mWebView2 = (WebView) findViewById(R.id.webview1);		 		           
			//mWebView2.loadUrl("file:///android_asset/about.html");
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.amhvalue));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
		else if (page == OVARIAN_VOL_INFO) {
			//WebView mWebView2 = (WebView) findViewById(R.id.webview1);		 		           
			//mWebView2.loadUrl("file:///android_asset/about.html");
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.volvalue));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
		else if (page == AFC_INFO) {
			//WebView mWebView2 = (WebView) findViewById(R.id.webview1);		 		           
			//mWebView2.loadUrl("file:///android_asset/about.html");
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.afcvalue));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
		else if (page == FSH_INFO) {
			//WebView mWebView2 = (WebView) findViewById(R.id.webview1);		 		           
			//mWebView2.loadUrl("file:///android_asset/about.html");
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.fshvalue));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
		else if (page == MMA_INFO) {
			//WebView mWebView2 = (WebView) findViewById(R.id.webview1);		 		           
			//mWebView2.loadUrl("file:///android_asset/about.html");
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.menopause2));
			
			TextView lblDetails = (TextView)findViewById(R.id.lblDetails);
			lblDetails.setText(getString(R.string.sampletext));
		}
		else if (page == PERIOD_INFO) {
			//WebView mWebView2 = (WebView) findViewById(R.id.webview1);		 		           
			//mWebView2.loadUrl("file:///android_asset/about.html");
			TextView lblTitle = (TextView)findViewById(R.id.lblTitle);
			lblTitle.setText(getString(R.string.periods));
			
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
    	
		setContentView(R.layout.webpage);
		InitializeUI();
    }
	
	@Override
    public void onDestroy() {
    	super.onDestroy();
    }
}

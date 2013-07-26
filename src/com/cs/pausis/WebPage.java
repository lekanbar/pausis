package com.cs.pausis;

import com.core.pausis.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ScrollView;

public class WebPage extends Activity{
	
	ScrollView mScrollView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.webpage);
    	
    	InitializeUI();
	}
	
	public void InitializeUI(){
		setTitle(getString(R.string.info));
		
		int page = getIntent().getIntExtra("page", 0);
		
		if (page == 1) {
			WebView mWebView2 = (WebView) findViewById(R.id.webview1);		 		           
			mWebView2.loadUrl("file:///android_asset/about.html");
		}
		else if (page == 2) {
			WebView mWebView2 = (WebView) findViewById(R.id.webview1);		 		           
			mWebView2.loadUrl("file:///android_asset/about.html");
		}
		else if (page == 3) {
			WebView mWebView2 = (WebView) findViewById(R.id.webview1);		 		           
			mWebView2.loadUrl("file:///android_asset/about.html");
		}
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

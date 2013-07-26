package com.cs.pausis;

import java.util.ArrayList;

import com.core.pausis.R;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.UsageHistory;
import com.cs.pausis.models.UserPreference;
import com.cs.pausis.models.Years;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

 public class Summary extends Activity {	 
	 UserPreference pref;
	 UsageHistory usage;
	 static boolean isfirsttime = true;
	 static boolean isviolated = false;
	 double result;
	 ArrayList<Result> results;
	 ResultAdapter adapter;
	 
	Runnable updateTextView = new Runnable() {
	   @Override
	   public void run() {
	    	
	   }
	};
	 
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          	
        Configuration conf = this.getResources().getConfiguration();    	
    	if(conf.orientation == 2)
    		setContentView(R.layout.summary);
    	//else
    		//setContentView(R.layout.summaryport);
    	
    	if(isfirsttime){
    		Bundle b = getIntent().getExtras();
        	usage = b.getParcelable("usage");
        	
        	//Calculate percentage
        	InitializeUI();
        	CalculatePercentage();
        	
        	isfirsttime = false;
    	}
    	else
    		InitializeUI();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    // Create and add new menu items.
	    MenuItem itemTop = menu.add(0, Menu.FIRST, Menu.NONE, R.string.aboutus);
	    itemTop.setShortcut('1', 'a'); itemTop.setIcon(R.drawable.about_us);
	    
	    MenuItem itemHis = menu.add(0, Menu.FIRST + 1, Menu.NONE, R.string.privacytitle);
	    itemHis.setShortcut('2', 'p'); itemHis.setIcon(R.drawable.privacypolicy);
	    return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    super.onOptionsItemSelected(item);
	    switch (item.getItemId()) {
		    case (Menu.FIRST): {
		    	Intent i = new Intent(getApplicationContext(), AboutUs.class);
				startActivity(i);
		    	return true;
		    }
		    case (Menu.FIRST + 1): {
		    	Intent i = new Intent(getApplicationContext(), PrivacyInfo.class);
				startActivityForResult(i, 0);
		    	return true;
		    }
		    default: return false;
	    }
    }
    
    private void CalculatePercentage(){
    	if(!isviolated){
	        //show result
	        runOnUiThread(updateTextView);
    	}
    }
    
    private void InitializeUI(){
    	results = getIntent().getExtras().getParcelableArrayList("results");
    	adapter = new ResultAdapter(this, R.layout.expandable_list_item, results);
    	
    	ActionSlideExpandableListView list = (ActionSlideExpandableListView)this.findViewById(R.id.list);
    	// fill the list with data
		list.setAdapter(adapter);
		list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

			@Override
			public void onClick(View listView, View buttonview, int position) {
				Result res = results.get(position);
				
				if(buttonview.getId() == R.id.buttonA) {
					
				} else {
					
				}
			}
		}, R.id.buttonA, R.id.buttonB);
	}
    
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	//TextView lblResult = (TextView)findViewById(R.id.lblResult);
        //lblResult.setText(result + " %");
    	
        setContentView(R.layout.summary);
		InitializeUI();
    }
}

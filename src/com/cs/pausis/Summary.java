package com.cs.pausis;

import java.util.ArrayList;

import com.core.pausis.R;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.UserInputValues;
import com.cs.pausis.models.UserPreference;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

 public class Summary extends Activity {	 
	 UserPreference pref;
	 UserInputValues usage;
	 static boolean isfirsttime = true;
	 static boolean isviolated = false;
	 double result;
	 ArrayList<Result> results;
	 ResultAdapter adapter;
	 
	 Result fshResult, mmaResult;
	 
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          
		setContentView(R.layout.summary);
	
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
    
    private void InitializeUI(){
    	results = getIntent().getExtras().getParcelableArrayList("results");
    	
    	String fshresultString = "", mmaresultString = "";
    	for(Result result : results){
    		if(result.getType().equals(Result.Type.FSH.toString())){
    			fshresultString = getString(R.string.fshresult) + result.getValue() + "%";
    			fshResult = result;
    			results.remove(result);
    		}
    		else if(result.getType().equals(Result.Type.MMA.toString())) {
    			mmaresultString = getString(R.string.mmaresult) + result.getValue() + "%";
    			mmaResult = result;
    			results.remove(result);
			}
    	}
    	
    	//FSH result information
    	if(!fshresultString.equals("")){
    		ImageView imgFsh = (ImageView)findViewById(R.id.imgFSH);
    		imgFsh.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(), InformationPage.class);
					i.putExtra("type", InformationPage.SUMMARY_TYPE);
					i.putExtra("result", fshResult);
					startActivity(i);
				}
			});
    		
	    	TextView txtFshResult = (TextView)findViewById(R.id.txtFshResult);
	    	txtFshResult.setText(fshresultString);
    	}
    	else {
    		LinearLayout layfsh = (LinearLayout)findViewById(R.id.layfsh);
			layfsh.setVisibility(View.GONE);
		}
    	
    	//MMA result information
    	if(!mmaresultString.equals("")){
    		ImageView imgMma = (ImageView)findViewById(R.id.imgMMA);
    		imgMma.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(), InformationPage.class);
					i.putExtra("type", InformationPage.SUMMARY_TYPE);
					i.putExtra("result", mmaResult);
					startActivity(i);
				}
			});
    		
	    	TextView txtMMAResult = (TextView)findViewById(R.id.txtMMAResult);
	    	txtMMAResult.setText(mmaresultString);
    	}
    	else {
			LinearLayout laymma = (LinearLayout)findViewById(R.id.laymma);
			laymma.setVisibility(View.GONE);
		}
    	
    	adapter = new ResultAdapter(this, R.layout.expandable_list_item, results);
    	
    	ActionSlideExpandableListView list = (ActionSlideExpandableListView)this.findViewById(R.id.list);
    	// fill the list with data
		list.setAdapter(adapter);
		list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

			@Override
			public void onClick(View listView, View buttonview, int position) {
				if(buttonview.getId() == R.id.buttonA) {
					Intent intent = new Intent(Summary.this, ResultVisualizer.class);
			        intent.putExtra("result", result);
		      	    startActivity(intent);
				} else {
					Result result = results.get(position);
					Intent i = new Intent(getApplicationContext(), InformationPage.class);
					i.putExtra("type", InformationPage.SUMMARY_TYPE);
					i.putExtra("result", result);
					startActivity(i);
				}
			}
		}, R.id.buttonA, R.id.buttonB);
	}
    
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
        setContentView(R.layout.summary);
		InitializeUI();
    }
}

package com.cs.pausis;

import java.util.ArrayList;

import com.core.pausis.R;
import com.cs.pausis.models.UserInputValues;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class History extends Activity {
	
	public boolean isfirsttime = true;
	ArrayList<UserInputValues> values;
	HistoryAdapter aa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		
		if(isfirsttime){
			InitializeUI();
			isfirsttime = false;
		}
	}
	
	public void InitializeUI(){
		//Get all usage history from DB
		values = new ArrayList<UserInputValues>();
		values = new UserInputValues().getAllHistory(this);

    	if(values.size() > 0)
    	{
    		ListView historyList = (ListView)findViewById(R.id.list);
    		int resID = R.layout.expandable_list_item2;
	        aa = new HistoryAdapter(History.this, resID, values);
	        historyList.setAdapter(aa);
	        
	        historyList.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
	            	
	            	final UserInputValues history = values.get(position);
	            	
	            	Intent i = new Intent(getApplicationContext(), Summary.class);
					Bundle b = new Bundle();
	    			b.putInt("index", Integer.parseInt(history.getID()));
	    			i.putExtras(b);
					startActivity(i);
	            }
	        });
    	}
	    else{
	    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Oops");
			alert.setMessage(R.string.nousage);
			alert.setPositiveButton(R.string.ok, new OnClickListener(){
	
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
				}
			});
			alert.show();
	    }
	}
}

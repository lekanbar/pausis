package com.cs.pausis;

import java.util.ArrayList;

import com.core.pausis.R;
import com.cs.pausis.models.UsageHistory;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ListView;

public class History extends ListActivity {
	
	public boolean isfirsttime = true;
	ArrayList<UsageHistory> values;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		if(isfirsttime){
			InitializeUI();
			isfirsttime = false;
		}
	}
	
	public void InitializeUI(){
		//Get all usage history from DB
		values = new ArrayList<UsageHistory>();
		values = new UsageHistory().getAllHistory(this);

        HistoryAdapter adapter = new HistoryAdapter(this, R.layout.expandable_list_item2, values);
        // Assign adapter to List
        setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {        
	   super.onListItemClick(l, v, position, id);
	   // ListView Clicked item index
	   
	}
}

package com.cs.pausis;

import com.core.pausis.R;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainMenu extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
				
		InitializeUI();
	}
	
	public void InitializeUI(){
		String[] values = new String[] { "General Advice", "Personalized Calculator", "About Us", "Privacy Policy" };

       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
       // Assign adapter to List
       setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {        
	   super.onListItemClick(l, v, position, id);
	   // ListView Clicked item index
	   int itemPosition = position;
	   
	   if (itemPosition == 0) {
		   Intent i = new Intent(MainMenu.this, GeneralAdvice.class);
		   startActivity(i);
	   }
	   else if (itemPosition == 1) {
		   Intent i = new Intent(MainMenu.this, MainActivity.class);
		   startActivity(i);
	   }
	   else if (itemPosition == 2) {
		   Intent i = new Intent(MainMenu.this, AboutUs.class);
		   startActivity(i);
	   }
	   else if (itemPosition == 3) {
		   Intent i = new Intent(MainMenu.this, PrivacyInfo.class);
		   startActivity(i);
	   }
	}
}

package com.cs.pausis;

import java.util.ArrayList;
import java.util.Calendar;

import com.core.pausis.R;
import com.cs.pausis.models.MenuItem;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainMenu extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
				
		InitializeUI();
	}
	
	public void InitializeUI(){
		ArrayList<MenuItem> values = new ArrayList<MenuItem>();
		MenuItem value = new MenuItem();
		value.setiD(0); values.add(value);
		
		value = new MenuItem();
		value.setiD(1); values.add(value);
		
		value = new MenuItem();
		value.setiD(2); values.add(value);
		
		value = new MenuItem();
		value.setiD(3); values.add(value);

        MenuAdapter adapter = new MenuAdapter(this, R.layout.expandable_list_item3, values);
        // Assign adapter to List
        setListAdapter(adapter);
        
        //Set copyright information
        Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		
        TextView lblCopyright = (TextView)findViewById(R.id.lblCopyright);
        lblCopyright.setText(getString(R.string.copyright) + year + " ");
        
        TextView lblName = (TextView)findViewById(R.id.lblName);
        lblName.setText(Html.fromHtml("<a href=\"http://uk.linkedin.com/in/lekanbaruwa/\">" + getString(R.string.name) + "</a>"));
        lblName.setMovementMethod(LinkMovementMethod.getInstance());
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

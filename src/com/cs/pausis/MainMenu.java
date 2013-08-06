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

/**
 * This is the Main Menu Activity class that facilitates the display of the menu.
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class MainMenu extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
				
		InitializeUI();
	}
	
	/**
     * This method initializes the User Interface controls
     */
	public void InitializeUI(){
		//Set up the menu items, which are differentiated by their IDs
		ArrayList<MenuItem> values = new ArrayList<MenuItem>();
		MenuItem value = new MenuItem();
		value.setiD(0); values.add(value);
		
		value = new MenuItem();
		value.setiD(1); values.add(value);
		
		value = new MenuItem();
		value.setiD(2); values.add(value);
		
		value = new MenuItem();
		value.setiD(3); values.add(value);
		
		value = new MenuItem();
		value.setiD(4); values.add(value);

        MenuAdapter adapter = new MenuAdapter(this, R.layout.expandable_list_item3, values);
        
        // Assign adapter to List
        setListAdapter(adapter);
        
        //Set copyright information
        Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		
		//Set up the copyright message which links to the author's linkedin page
        TextView lblCopyright = (TextView)findViewById(R.id.lblCopyright);
        lblCopyright.setText(getString(R.string.copyright) + year + " ");
        
        TextView lblName = (TextView)findViewById(R.id.lblName);
        lblName.setText(Html.fromHtml("<a href=\"http://uk.linkedin.com/in/lekanbaruwa/\">" + getString(R.string.name) + "</a>"));
        lblName.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	/**
	 * Overidden Method for handling the list item clicks 
	 */
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
		   Intent i = new Intent(MainMenu.this, History.class);
		   startActivity(i);
	   }
	   else if (itemPosition == 3) {
		   Intent i = new Intent(MainMenu.this, AboutUs.class);
		   startActivity(i);
	   }
	   else if (itemPosition == 4) {
		   Intent i = new Intent(MainMenu.this, PrivacyInfo.class);
		   startActivity(i);
	   }
	}
}

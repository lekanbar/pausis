package com.cs.pausis;

import java.util.ArrayList;

import com.core.pausis.R;
import com.cs.pausis.models.Months;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This Activity class facilitates the birth month selection.
 * <br/><br/>It returns the specific choice made by setting the result before finishing the activity session
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class ChooseMonth extends Activity {
	ArrayList<Months> months;
	ArrayAdapter<Months> adapter;
	EditText monthSearch;
	ListView lstMonths;
	int monthindex;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.months);
        
        //get the index of the previously chosen month, if it's available
        monthindex = getIntent().getExtras().getInt("monthindex", 0);
    	
    	InitializeUI();
    }
    
    /**
     * TextWatcher for filtering the adapter based on the user inputs.
     */
    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.getFilter().filter(s);
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
        }
    };
    
    /**
     * This method initializes the User Interface controls
     */
    public void InitializeUI(){
    	//Setting up the Edittext with the text Watcher
        monthSearch = (EditText) findViewById(R.id.countrysearch_box);
        monthSearch.addTextChangedListener(filterTextWatcher);
        
        //Set up the contents for the months list
    	months = Months.getAll(ChooseMonth.this);
    	lstMonths = (ListView)findViewById(R.id.listBirth);
    	adapter = new ArrayAdapter<Months>(this, android.R.layout.simple_list_item_1, months){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                /*set the color of the text view*/
                textView.setTextColor(Color.BLACK);

                return view;
            }
        };
        lstMonths.setAdapter(adapter);
        lstMonths.setSelection(monthindex);
        lstMonths.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int pos, long arg3) {
				//get the chosen month, if it's a valid month set it as a result and finish activity
				Months chosenmonth = (Months)adapter.getItemAtPosition(pos);				
				int month = Integer.parseInt(chosenmonth.getID());
				if(month > 0){
					Intent i = new Intent();
		        	i.putExtra("monthindex", pos);
		        	i.putExtra("birthmonth", month);
		        	i.putExtra("birthmonthtext", chosenmonth.getMonth());
		        	setResult(RESULT_OK, i);//set result
		        	finish();
				}
			}
    	});
    }
    
    @Override
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	//Handle the orientation change
    	setContentView(R.layout.months);
		InitializeUI();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
}
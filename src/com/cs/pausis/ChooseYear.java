package com.cs.pausis;

import java.util.ArrayList;

import com.core.pausis.R;
import com.cs.pausis.models.Years;

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
 * This Activity class facilitates the birth year selection.
 * <br/><br/>It returns the specific choice made by setting the result before finishing the activity session
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class ChooseYear extends Activity {
	ArrayList<Years> years;
	ArrayAdapter<Years> adapter;
	int yearindex;
	EditText yearSearch;
	ListView lstYears;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.years);
        
        //get the index of the previously chosen month, if it's available
        yearindex = getIntent().getExtras().getInt("yearindex", 0);
    	
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
    private void InitializeUI(){
    	//Setting up the Edittext with the text Watcher
    	yearSearch = (EditText) findViewById(R.id.countrysearch_box);
    	yearSearch.addTextChangedListener(filterTextWatcher);
        
    	//Set up the contents for the years list
    	years = Years.getAll(ChooseYear.this);
    	lstYears = (ListView)findViewById(R.id.listBirth);
    	adapter = new ArrayAdapter<Years>(this, android.R.layout.simple_list_item_1, years){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLACK);

                return view;
            }
        };
        lstYears.setAdapter(adapter);
        lstYears.setSelection(yearindex);
        lstYears.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int pos, long arg3) {
				//get the chosen year, if it's a valid month set it as a result and finish activity
				Years chosenyear = (Years)adapter.getItemAtPosition(pos);				
				String yearID = chosenyear.getID();
				if(!yearID.toString().equals("-1")){
					Intent i = new Intent();
		        	i.putExtra("yearindex", pos);
		        	i.putExtra("birthyear", chosenyear.getYear());
		        	setResult(RESULT_OK, i);//set the result
		        	finish();
				}
			}
    	});
    }
    
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	//Handle the orientation change
    	setContentView(R.layout.years);
		InitializeUI();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
}
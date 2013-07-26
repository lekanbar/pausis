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
        
        String cou = getIntent().getExtras().getString("yearindex");
        if(cou != null)
        	monthindex = Integer.parseInt(cou);
        else
        	monthindex = 0;
    	
    	InitializeUI();
    }
    
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
    
    public void InitializeUI(){
    	setTitle(getString(R.string.choosebirthmonth));
        
        monthSearch = (EditText) findViewById(R.id.countrysearch_box);
        monthSearch.addTextChangedListener(filterTextWatcher);
        
    	months = Months.getAll(ChooseMonth.this);
    	lstMonths = (ListView)findViewById(R.id.listBirth);
    	adapter = new ArrayAdapter<Months>(this, android.R.layout.simple_list_item_1, months){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLACK);

                return view;
            }
        };
        lstMonths.setAdapter(adapter);
        lstMonths.setSelection(monthindex);
        lstMonths.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int pos, long arg3) {
				// TODO Auto-generated method stub
				Months chosenmonth = (Months)adapter.getItemAtPosition(pos);				
				String month = chosenmonth.getMonth();
				if(!month.toString().startsWith("Choose")){
					Intent i = new Intent();
		        	i.putExtra("yearindex", pos);
		        	i.putExtra("birthmonth", month);
		        	setResult(RESULT_OK, i);
		        	finish();
				}
			}
    	});
    }
    
    public void onConfigurationChanged(Configuration _newConfig) {
    	super.onConfigurationChanged(_newConfig);
    	
    	setContentView(R.layout.months);
		InitializeUI();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
}
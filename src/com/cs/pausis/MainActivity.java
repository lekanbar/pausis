package com.cs.pausis;

import java.util.ArrayList;

import com.core.pausis.R;
import com.cs.pausis.controllers.OvaryReserve_Calculator;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.UsageHistory;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends Activity {

	UsageHistory usage;
	static boolean isfirsttime = true;
	ProgressDialog dialog;
	
	String monthText = "";
	int birthYear, birthMonth;
	
	public static final int DO_CHOOSE_YEAR = 1;
	public static final int DO_CHOOSE_MONTH = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(isfirsttime){
			usage = new UsageHistory();
			ArrayList<UsageHistory> history = usage.getAllHistory(this);
			
			if(history.size() > 0)
				usage = history.get(0);
			else
				usage = null;
		}
		
		InitializeUI();
	}
	
	public void InitializeUI(){
		if(usage != null){
			EditText txtAmh = (EditText)findViewById(R.id.txtAMH);
	        txtAmh.setText(usage.getAmhvolume());
	        
	        EditText txtVolume = (EditText)findViewById(R.id.txtVolume);
	        txtVolume.setText(usage.getOvarianvolume());
	        
	        EditText txtAfc = (EditText)findViewById(R.id.txtAfc);
	        txtAfc.setText(usage.getAfc());
		}
		
		//show on imgGlobe
        ImageView imgAMH = (ImageView)findViewById(R.id.imgAMH);
        imgAMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, WebPage.class);
            	i.putExtra("page", 1);
				startActivity(i);
            }
        });
        
        ImageView imgVolume = (ImageView)findViewById(R.id.imgVolume);
        imgVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, WebPage.class);
            	i.putExtra("page", 2);
				startActivity(i);
            }
        });
        
        ImageView imgAfc = (ImageView)findViewById(R.id.imgAfc);
        imgAfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, WebPage.class);
            	i.putExtra("page", 3);
				startActivity(i);
            }
        });
        
        String year = "";
        if(birthYear == 0)
        	year = getString(R.string.choosebirthyear);
        else
			year = birthYear + "";        	
    	String[] theList = {year};
        Spinner spinner = (Spinner) findViewById(R.id.spinBirthYear);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, theList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent evt) {
				// TODO Auto-generated method stub
				if(evt.getAction() == MotionEvent.ACTION_DOWN || evt.getAction() == MotionEvent.ACTION_POINTER_DOWN){
					Intent i = new Intent(getApplicationContext(), ChooseYear.class);
					i.putExtra("yearindex", 0);
					startActivityForResult(i, DO_CHOOSE_YEAR);
				}
				return true;
			}
        });
        spinner.setOnKeyListener(new View.OnKeyListener(){

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
					Intent i = new Intent(getApplicationContext(), ChooseYear.class);
					i.putExtra("yearindex", 0);
					startActivityForResult(i, DO_CHOOSE_YEAR);
				}
				return true;
			}
        });
        
        if(monthText.equals(""))
        	monthText = getString(R.string.choosebirthmonth);
        String[] theList2 = {monthText};
        Spinner spinner2 = (Spinner) findViewById(R.id.spinBirthMonth);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, theList2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent evt) {
				// TODO Auto-generated method stub
				if(evt.getAction() == MotionEvent.ACTION_DOWN || evt.getAction() == MotionEvent.ACTION_POINTER_DOWN){
					Intent i = new Intent(getApplicationContext(), ChooseMonth.class);
					i.putExtra("monthindex", 0);
					startActivityForResult(i, DO_CHOOSE_MONTH);
				}
				return true;
			}
        	
        });
        spinner2.setOnKeyListener(new View.OnKeyListener(){

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
					Intent i = new Intent(getApplicationContext(), ChooseMonth.class);
					i.putExtra("monthindex", 0);
					startActivityForResult(i, DO_CHOOSE_MONTH);
				}
				return true;
			}
        });
        
        Button cmdProceed_button = (Button)findViewById(R.id.cmdProceed);
        cmdProceed_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Double obamh = null, obvol = null, obafc = null;
				
				EditText txtAmh = (EditText)findViewById(R.id.txtAMH);
				EditText txtVol = (EditText)findViewById(R.id.txtVolume);
				EditText txtVolRight = (EditText)findViewById(R.id.txtVolumeRight);
				EditText txtAfc = (EditText)findViewById(R.id.txtAfc);
				EditText txtAfcRight = (EditText)findViewById(R.id.txtAfcRight);
				
				int max = 10;
				if(!txtAmh.getText().toString().equals("")){
					obamh = Double.valueOf(txtAmh.getText().toString());
					max += 10;
				}
				if(!txtVol.getText().toString().equals("")){
					obvol = (Double.valueOf(txtVol.getText().toString()) + Double.valueOf(txtVolRight.getText().toString())) / 2;
					max += 10;
				}
				if(!txtAfc.getText().toString().equals("")){
					obafc = (Double.valueOf(txtAfc.getText().toString()) + Double.valueOf(txtAfcRight.getText().toString())) / 2;
					max += 10;
				}
				
				dialog = new ProgressDialog(MainActivity.this);
		        dialog.setTitle(getString(R.string.calcing));
		        dialog.setMessage(getString(R.string.plswait));
		        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		        dialog.setProgress(0);
		        dialog.setMax(max);
		        dialog.show();
		        
		        OvaryReserve_Calculator ovcalc = new OvaryReserve_Calculator(birthYear, birthMonth, obamh, obvol, obafc, MainActivity.this);
		        ovcalc.execute("");
			}
    	});
	}
	
	public void done(ArrayList<Result> results){
		dialog.setProgress(dialog.getProgress() + 10);
		
		if(results.size() > 0){
			//Go to summary page
			Intent i = new Intent(MainActivity.this, Summary.class);
        	i.putExtra("results", results);
			startActivity(i);
		}
		else {
			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
			alert.setTitle(R.string.resulttitle);
			alert.setMessage(R.string.resultmessage);
			alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){

				public void onClick(DialogInterface arg0, int arg1) {
					
				}
			});
			alert.show();
		}
		
		//close dialog
		dialog.dismiss();
	}
	
	public void updateDialog(){
		dialog.setProgress(dialog.getProgress() + 10);
	}
	
	@Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
	    super.onActivityResult(reqCode, resCode, data);
	    switch(reqCode) {
		    case (DO_CHOOSE_YEAR) : {
			    if (resCode == Activity.RESULT_OK) {
			    	//int index = Integer.parseInt(data.getStringExtra("yearindex"));
			    	birthYear = Integer.parseInt(data.getStringExtra("birthyear"));
			    	InitializeUI();
			    }
			    break;
		    }
		    case (DO_CHOOSE_MONTH) : {
			    if (resCode == Activity.RESULT_OK) {
			    	//int index = Integer.parseInt(data.getStringExtra("monthindex"));
			    	birthMonth = data.getIntExtra("birthmonth", 0);
			    	monthText = data.getStringExtra("birthmonthtext");
			    	InitializeUI();
			    }
			    break;
		    }
	    }
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
}

package com.cs.pausis;

import java.util.ArrayList;
import java.util.Calendar;

import com.core.pausis.R;
import com.cs.pausis.controllers.OvaryReserve_Calculator;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.UserInputValues;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity {

	UserInputValues usage;
	static boolean isfirsttime = true;
	ProgressDialog dialog;
	
	String monthText = "", birthYear = "", birthMonth = "", observedAmh = "", observedOvarianVolume = "", observedAfc = "", observedFsh = "", motherMenopauseAge = "",
		   regularPeriods = "", height = "", weight = "";
	UserInputValues userInputValues;
	int chosenAmhUnit, chosenWeightUnit; 
	double chosenHeight;//height in meters
	ScrollView sc;
	
	public static final int DO_CHOOSE_YEAR = 1;
	public static final int DO_CHOOSE_MONTH = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(isfirsttime){
			usage = new UserInputValues();
			ArrayList<UserInputValues> history = usage.getAllHistory(this);
			
			if(history.size() > 0)
				usage = history.get(0);
			else
				usage = null;
			
			//Instantiate scroll view
			sc = (ScrollView)findViewById(R.id.scrollView2);
		}
		
		InitializeUI();
	}
	
	public void InitializeUI(){
		//Close sot keyboard by default
		EditText txtWeight = (EditText)findViewById(R.id.txtWeight);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtWeight.getWindowToken(), 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        
		/*if(usage != null){
			EditText txtAmh = (EditText)findViewById(R.id.txtAMH);
	        txtAmh.setText(usage.getAmhvolume());
	        
	        EditText txtVolume = (EditText)findViewById(R.id.txtVolume);
	        txtVolume.setText(usage.getOvarianvolume());
	        
	        EditText txtAfc = (EditText)findViewById(R.id.txtAfc);
	        txtAfc.setText(usage.getAfc());
	        
	        EditText txtFsh = (EditText)findViewById(R.id.txtFSH);
	        txtFsh.setText(usage.getFsh());
	        
	        txtWeight = (EditText)findViewById(R.id.txtWeight);
	        txtWeight.setText(usage.getWeight());
		}*/
		
		//Weight units
        Spinner spinWeightUnits = (Spinner) findViewById(R.id.spinWeightUnit);
        ArrayAdapter<CharSequence> weightsAdapter = ArrayAdapter.createFromResource(this, R.array.array_weight_units, android.R.layout.simple_spinner_item);
        weightsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWeightUnits.setAdapter(weightsAdapter);
        spinWeightUnits.setSelection(0, true);
        spinWeightUnits.setOnItemSelectedListener(new OnItemSelectedListener(){
        	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        		chosenWeightUnit = pos;
            }

            public void onNothingSelected(AdapterView<?> parent) {
              // Do nothing.
            }
        });
        
        //Heights
        Spinner spinHeight = (Spinner) findViewById(R.id.spinHeight);
        ArrayAdapter<CharSequence> heightsAdapter = ArrayAdapter.createFromResource(this, R.array.array_heights, android.R.layout.simple_spinner_item);
        heightsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHeight.setAdapter(heightsAdapter);
        spinHeight.setSelection(0, true);
        spinHeight.setOnItemSelectedListener(new OnItemSelectedListener(){
        	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        		if(pos > 0)
        			chosenHeight = Double.parseDouble(getResources().getStringArray(R.array.array_heights_in_cm)[pos]) * 0.01;//cONVERT HEIGHT to meters (1cm = 0.01m)
        		else
        			chosenHeight = 0.0;
            }

            public void onNothingSelected(AdapterView<?> parent) {
              // Do nothing.
            }
        });
        
        //Set AMH unit list
        Spinner spinAmhUnit = (Spinner) findViewById(R.id.spinAmhUnit);
        ArrayAdapter<CharSequence> amhUnits = ArrayAdapter.createFromResource(this, R.array.array_amh_units, android.R.layout.simple_spinner_item);
        amhUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAmhUnit.setAdapter(amhUnits);
        spinAmhUnit.setSelection(0, true);
        spinAmhUnit.setOnItemSelectedListener(new OnItemSelectedListener(){
        	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	chosenAmhUnit = pos;
            }

            public void onNothingSelected(AdapterView<?> parent) {
              // Do nothing.
            }
        });
        
        //Set up information images
        ImageView imgAMH = (ImageView)findViewById(R.id.imgAMH);
        imgAMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, InformationPage.class);
            	i.putExtra("type", InformationPage.MAIN_PAGE_TYPE);
            	i.putExtra("infotype", InformationPage.AMH_INFO);
				startActivity(i);
            }
        });
        
        ImageView imgVolume = (ImageView)findViewById(R.id.imgVolume);
        imgVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, InformationPage.class);
            	i.putExtra("type", InformationPage.MAIN_PAGE_TYPE);
            	i.putExtra("infotype", InformationPage.OVARIAN_VOL_INFO);
				startActivity(i);
            }
        });
        
        ImageView imgAfc = (ImageView)findViewById(R.id.imgAfc);
        imgAfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, InformationPage.class);
            	i.putExtra("type", InformationPage.MAIN_PAGE_TYPE);
            	i.putExtra("infotype", InformationPage.AFC_INFO);
				startActivity(i);
            }
        });
        
        ImageView imgMMAge = (ImageView)findViewById(R.id.imgMMage);
        imgMMAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, InformationPage.class);
            	i.putExtra("type", InformationPage.MAIN_PAGE_TYPE);
            	i.putExtra("infotype", InformationPage.MMAge_INFO);
				startActivity(i);
            }
        });
        
        ImageView imgPeriod = (ImageView)findViewById(R.id.imgPeriod);
        imgPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, InformationPage.class);
            	i.putExtra("type", InformationPage.MAIN_PAGE_TYPE);
            	i.putExtra("infotype", InformationPage.PERIOD_INFO);
				startActivity(i);
            }
        });
        
        ImageView imgFSH = (ImageView)findViewById(R.id.imgFSH);
        imgFSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, InformationPage.class);
            	i.putExtra("type", InformationPage.MAIN_PAGE_TYPE);
            	i.putExtra("infotype", InformationPage.FSH_INFO);
				startActivity(i);
            }
        });
        
        String year = "";
        if(birthYear.equals(""))
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
				if(birthYear.equals("") || birthYear.startsWith("Select") || birthMonth.equals("") || birthMonth.startsWith("Select")){
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
					alert.setTitle(R.string.ageerrortitle);
					alert.setMessage(R.string.ageerror);
					alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){

						public void onClick(DialogInterface arg0, int arg1) {
							
						}
					});
					alert.show();
					return;
				}
				
				EditText txtAmh = (EditText)findViewById(R.id.txtAMH);
				EditText txtVol = (EditText)findViewById(R.id.txtVolume);
				EditText txtVolRight = (EditText)findViewById(R.id.txtVolumeRight);
				EditText txtAfc = (EditText)findViewById(R.id.txtAfc);
				EditText txtAfcRight = (EditText)findViewById(R.id.txtAfcRight);
				EditText txtFsh = (EditText)findViewById(R.id.txtFSH);
				EditText txtMMenopauseAge = (EditText)findViewById(R.id.txtMMenopauseAge);
				EditText txtWeight = (EditText)findViewById(R.id.txtWeight);
				
				RadioButton radioYes = (RadioButton)findViewById(R.id.radioYes);
				RadioButton radioNo = (RadioButton)findViewById(R.id.radioNo);
		        
				int max = 10;
				if(!txtAmh.getText().toString().equals("")){
					if(!txtAmh.getText().toString().equals("0")){
						//for dialog max value
						observedAmh = convertAMH(Double.valueOf(txtAmh.getText().toString()));
						max += 10;
					}
				}
				if(!txtVol.getText().toString().equals("") || !txtVolRight.getText().toString().equals("")){
					if(!txtVol.getText().toString().equals("0") && !txtVolRight.getText().toString().equals("0")){
						double left = Double.valueOf(txtVol.getText().toString()),
							   right = Double.valueOf(txtVolRight.getText().toString());					
						
						if(left > right)
							observedOvarianVolume =  String.valueOf(left);
						else if(right > left)
							observedOvarianVolume =  String.valueOf(right);
						else if(left == right)
							observedOvarianVolume =  String.valueOf(left);
						
						//for dialog max value
						max += 10;
					}
				}
				if(!txtAfc.getText().toString().equals("")){
					if(!txtAfc.getText().toString().equals("0") && !txtAfcRight.getText().toString().equals("0")){
						double left = Double.valueOf(txtAfc.getText().toString()),
							   right = Double.valueOf(txtAfcRight.getText().toString());					
						
						if(left > right)
							observedAfc =  String.valueOf(left);
						else if(right > left)
							observedAfc =  String.valueOf(right);
						else if(left == right)
							observedAfc =  String.valueOf(left);
						
						//for dialog max value
						max += 10;
					}
				}
				if(!txtFsh.getText().toString().equals("")){
					//for dialog max value
					observedFsh = txtFsh.getText().toString();
					max += 10;
				}
				if(!txtMMenopauseAge.getText().toString().equals("")){
					//for dialog max value
					if(!txtMMenopauseAge.getText().toString().equals("0")){
						motherMenopauseAge = txtMMenopauseAge.getText().toString();
						max += 10;
					}
				}
				if(radioYes.isChecked() || radioNo.isChecked()){
					//for dialog max value
					regularPeriods = (radioYes.isChecked() ? "yes" : "no");
					max += 10;
				}
				if(chosenHeight > 0.0 && !txtWeight.getText().toString().equals("")){
					if(!txtWeight.getText().toString().equals("0")){
						//for dialog max value
						weight = convertWeight(Double.valueOf(txtWeight.getText().toString()));
						height = String.valueOf(chosenHeight);
						max += 10;
					}
				}
				
				//Show dialog to indicate progress
				dialog = new ProgressDialog(MainActivity.this);
		        dialog.setTitle(getString(R.string.calcing));
		        dialog.setMessage(getString(R.string.plswait));
		        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		        dialog.setProgress(0);
		        dialog.setMax(max);
		        dialog.show();
		        
		        //Set input values and insert into DB
		        userInputValues = new UserInputValues();
		        userInputValues.setAge(String.valueOf(calculateAge()));
		        userInputValues.setBirthYear(birthYear);
		        userInputValues.setBirthMonth(birthMonth);
		        userInputValues.setAmhvolume(observedAmh);
		        userInputValues.setAfc(observedAfc);
		        userInputValues.setOvarianvolume(observedOvarianVolume);
		        userInputValues.setFsh(observedFsh);
		        userInputValues.setMotherMenopauseAge(motherMenopauseAge);
		        userInputValues.setRegularPeriods(regularPeriods);
		        userInputValues.setWeight(weight);
		        userInputValues.setHeight(height);
		        Calendar c = Calendar.getInstance();
				String hour = (String.valueOf(c.get(Calendar.HOUR_OF_DAY)).length() == 1 ? "0" + String.valueOf(c.get(Calendar.HOUR_OF_DAY)) : String.valueOf(c.get(Calendar.HOUR_OF_DAY))); 
		    	String minute = (String.valueOf(c.get(Calendar.MINUTE)).length() == 1 ? "0" + String.valueOf(c.get(Calendar.MINUTE)) : String.valueOf(c.get(Calendar.MINUTE)));
				String datetime = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" +
						                         c.get(Calendar.YEAR) + " " + hour + ":" + 
						                         minute + (c.get(Calendar.AM_PM) == 0 ? " AM" : " PM"));
		        userInputValues.setDateTime(datetime);
		        
		        OvaryReserve_Calculator ovcalc = new OvaryReserve_Calculator(userInputValues, MainActivity.this, OvaryReserve_Calculator.MAIN_TYPE);
		        ovcalc.execute("");
			}
    	});
	}
	
	private String convertAMH(double amhvalue){
		String convertedValue = "";
		
		if(chosenAmhUnit == 0){
			convertedValue = String.valueOf(amhvalue);
		}
		else {
			convertedValue = String.valueOf((amhvalue * 7.143)); //since 1pmol/L = 7.143ng/mL 
		}
		
		return convertedValue;
	}
	
	private String convertWeight(double weightvalue){
		String convertedValue = "";
		
		if(chosenWeightUnit == 0){
			convertedValue = String.valueOf(weightvalue);
		}
		else {
			convertedValue = String.valueOf((weightvalue * 0.453592)); //since 1lbs = 0.453592kg 
		}
		
		return convertedValue;
	}
	
	private void saveUsageHistory(ArrayList<Result> results) {
        //calculate indicator based on dominance
        int greencount = 0, redcount = 0;
        for(Result result : results){
	        if(result.getStatus().equals(Result.Status.GREEN.toString()))
				greencount++;
			else if(result.getStatus().equals(Result.Status.RED.toString()))
				redcount++;
        }
        
        if(redcount == results.size())
        	userInputValues.setResultIndicator(Result.Status.RED.toString());
        else {
			if(greencount == results.size())
				userInputValues.setResultIndicator(Result.Status.GREEN.toString());
			else
				userInputValues.setResultIndicator(Result.Status.ORANGE.toString());
		}
        
        userInputValues.insertHistory(MainActivity.this);		
	}
	
	public void done(ArrayList<Result> results){
		dialog.setProgress(dialog.getProgress() + 10);
		
		if(results.size() > 0){
			//Save usage history
			saveUsageHistory(results);
			
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
			    	birthYear = data.getStringExtra("birthyear");
			    	InitializeUI();
			    	
			    	//Scroll up when done
			    	sc.post(new Runnable() { 
	        	        public void run() { 
	        	             sc.scrollTo(0, sc.getTop());
	        	        } 
	        		});
			    }
			    break;
		    }
		    case (DO_CHOOSE_MONTH) : {
			    if (resCode == Activity.RESULT_OK) {
			    	//int index = Integer.parseInt(data.getStringExtra("monthindex"));
			    	birthMonth = String.valueOf(data.getIntExtra("birthmonth", 0));
			    	monthText = data.getStringExtra("birthmonthtext");
			    	InitializeUI();
			    	
			    	//Scroll up when done
			    	sc.post(new Runnable() { 
	        	        public void run() { 
	        	             sc.scrollTo(0, sc.getTop());
	        	        } 
	        		});
			    }
			    break;
		    }
	    }
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    // Create and add new menu items.
	    MenuItem itemHis = menu.add(0, Menu.FIRST, Menu.NONE, R.string.history);
	    itemHis.setShortcut('1', 'h'); itemHis.setIcon(R.drawable.history);
	    
	    MenuItem itemTop = menu.add(0, Menu.FIRST + 1, Menu.NONE, R.string.aboutus);
	    itemTop.setShortcut('2', 'a'); itemTop.setIcon(R.drawable.about_us);
	    
	    MenuItem itemPri = menu.add(0, Menu.FIRST + 2, Menu.NONE, R.string.privacytitle);
	    itemPri.setShortcut('3', 'p'); itemPri.setIcon(R.drawable.privacypolicy);
	    return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    super.onOptionsItemSelected(item);
	    switch (item.getItemId()) {
		    case (Menu.FIRST): {
		    	Intent i = new Intent(getApplicationContext(), History.class);
				startActivity(i);
		    	return true;
		    }
		    case (Menu.FIRST + 1): {
		    	Intent i = new Intent(getApplicationContext(), AboutUs.class);
				startActivity(i);
		    	return true;
		    }
		    case (Menu.FIRST + 2): {
			    Intent i = new Intent(getApplicationContext(), PrivacyInfo.class);
				startActivityForResult(i, 0);
		    	return true;
		    }
		    default: return false;
	    }
    }
    
    private int calculateAge(){
    	Calendar c = Calendar.getInstance();
		int calcage = c.get(Calendar.YEAR) - Integer.parseInt(this.birthYear);
    	
    	return calcage;
    }
}

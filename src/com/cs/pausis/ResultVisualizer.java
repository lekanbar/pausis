package com.cs.pausis;

import java.text.DecimalFormat;
import java.util.Hashtable;

import org.achartengine.GraphicalView;

import com.core.pausis.R;
import com.cs.pausis.models.Result;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This is the activity class that facilitates the visualization of the result on a gauge, and also displays the recommendations for the generated result. 
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class ResultVisualizer extends Activity  {
    
	//Reused variables declaration
    TextView content;
    Result result;
    GraphicalView view;
    static boolean isfirsttime = true;
    public static Hashtable<Integer, Float> DEFAULT_GAUGE_FULL_RANGE_VALUES;
    public static Hashtable<Integer, Float> AFC_GAUGE_FULL_RANGE_VALUES;
    
    protected void onCreate(Bundle savedInstanceState) {    
       super.onCreate(savedInstanceState);
       setContentView(R.layout.visualizer);       
       
       result = getIntent().getParcelableExtra("result");
       
       if(isfirsttime) {
    	   //initialize the hashtable of gauge values
    	   DEFAULT_GAUGE_FULL_RANGE_VALUES = new Hashtable<Integer, Float>();
    	   DEFAULT_GAUGE_FULL_RANGE_VALUES.put(-3, 0.0f); DEFAULT_GAUGE_FULL_RANGE_VALUES.put(-2, 16.667f);
    	   DEFAULT_GAUGE_FULL_RANGE_VALUES.put(-1, 33.333f); DEFAULT_GAUGE_FULL_RANGE_VALUES.put(Integer.valueOf(0), 50.0f);
    	   DEFAULT_GAUGE_FULL_RANGE_VALUES.put(1, 66.667f); DEFAULT_GAUGE_FULL_RANGE_VALUES.put(2, 83.333f);
    	   DEFAULT_GAUGE_FULL_RANGE_VALUES.put(3, 100.0f);
    	   
    	   AFC_GAUGE_FULL_RANGE_VALUES = new Hashtable<Integer, Float>();
    	   AFC_GAUGE_FULL_RANGE_VALUES.put(5, 0.0f); AFC_GAUGE_FULL_RANGE_VALUES.put(25, 20.0f);
    	   AFC_GAUGE_FULL_RANGE_VALUES.put(50, 40.0f); AFC_GAUGE_FULL_RANGE_VALUES.put(75, 60.0f);
    	   AFC_GAUGE_FULL_RANGE_VALUES.put(95, 80.0f);
       }
       InitializeUI();
    }
    
    /**
     * This method initializes the User Interface controls
     */
    private void InitializeUI(){    
    	//String variables for getting the message
    	String resultmessage = "", researchpaper = "", recommendation = "";
    	
    	LinearLayout layafc = (LinearLayout)findViewById(R.id.layafc);
    	LinearLayout laydefault = (LinearLayout)findViewById(R.id.laydefault);
    	
    	//Setup the gauge based on the result type
    	if(result.getType().equals(Result.Type.AFC.toString())){
    		researchpaper = "http://www.sciencedirect.com/science/article/pii/S0015028210021953";
    		
    		//Show the gauge views for AFC
    		GaugeViewPercentiles mGaugeView3 = (GaugeViewPercentiles) findViewById(R.id.gauge_view3);
    		GaugeViewPercentiles mGaugeView4 = (GaugeViewPercentiles) findViewById(R.id.gauge_view4);
        	
    		//get the value from the array
    		Integer sd = Integer.valueOf(result.getValue());
    		mGaugeView3.setTargetValue(AFC_GAUGE_FULL_RANGE_VALUES.get(sd).floatValue());
    		mGaugeView4.setTargetValue(sd);
    		
    		resultmessage = getString(R.string.afcdescription) + sd + "th" + getString(R.string.percentile);
    		
    		//Display the AFC view and hide the other view
    		layafc.setVisibility(View.VISIBLE);
    		laydefault.setVisibility(View.GONE);
    	}
    	else{
			GaugeViewStandardDeviations mGaugeView1 = (GaugeViewStandardDeviations) findViewById(R.id.gauge_view1);
			GaugeViewStandardDeviations mGaugeView2 = (GaugeViewStandardDeviations) findViewById(R.id.gauge_view2);
	    	
			//Get value to be set on the gauge
			float fl = calculateGValue(Float.parseFloat(result.getValue()));//Float.parseFloat(result.getValue()) < 0 ? Math.floor(Float.parseFloat(result.getValue())) : Math.ceil(Float.parseFloat(result.getValue()));
			
			DecimalFormat dFormat = new DecimalFormat("###.#");
			float sd = Float.parseFloat(dFormat.format(Double.parseDouble(result.getValue())));
			mGaugeView1.setTargetValue(fl);//DEFAULT_GAUGE_FULL_RANGE_VALUES.get(sd).floatValue());
    		mGaugeView2.setTargetValue(sd);
    		
    		//put together the message
    		if(result.getType().equals(Result.Type.AMH.toString())){
				resultmessage = getString(R.string.amhdescription) + (sd <= 0 ? sd : "+" + sd) + (sd < 0 ? getString(R.string.belowmean) : getString(R.string.abovemean));
				researchpaper = "http://www.ncbi.nlm.nih.gov/pmc/articles/PMC3137624/";
			}
			else{
				resultmessage = getString(R.string.ovadescription) + (sd <= 0 ? sd : "+" + sd) + (sd < 0 ? getString(R.string.belowmean) : getString(R.string.abovemean));
				researchpaper = "http://www.ncbi.nlm.nih.gov/pmc/articles/PMC3760857";
			}
    		
    		layafc.setVisibility(View.GONE);
    		laydefault.setVisibility(View.VISIBLE);
		}
        
        TextView lblStatus = (TextView)findViewById(R.id.lblstatus);
        if(result.getStatus().equals(Result.Status.GREEN.toString())){
        	lblStatus.setText(Html.fromHtml("<b>" + getString(R.string.resultstatus) + "</b>" + getString(R.string.positive)));
        	lblStatus.setTextColor(Color.GREEN);
        	
        	recommendation = getString(R.string.positiverecommendation);
        }
        else if(result.getStatus().equals(Result.Status.YELLOW.toString())){
			lblStatus.setText(Html.fromHtml("<b>" + getString(R.string.resultstatus) + "</b>" + getString(R.string.neutral)));
        	lblStatus.setTextColor(Color.parseColor("#E9F507"));
        	
        	recommendation = getString(R.string.neutralrecommendation);
		}
		else if(result.getStatus().equals(Result.Status.ORANGE.toString())){
			lblStatus.setText(Html.fromHtml("<b>" + getString(R.string.resultstatus) + "</b>" + getString(R.string.neutral)));
			
			if(result.getType().equals(Result.Type.AFC.toString()))
				lblStatus.setTextColor(Color.parseColor("#F59207"));
			else
				lblStatus.setTextColor(Color.parseColor("#05FA6B"));
        	
        	recommendation = getString(R.string.neutralrecommendation);
		}
		else{
			lblStatus.setText(Html.fromHtml("<b>" + getString(R.string.resultstatus) + "</b>" + getString(R.string.negative)));
        	lblStatus.setTextColor(Color.RED);
        	
        	recommendation = getString(R.string.negativerecommendation);
		}
        
        //Show the description of the message
        TextView lblDesc = (TextView)findViewById(R.id.lbldescription);
        lblDesc.setText(Html.fromHtml("<b>" + getString(R.string.descriptiontitle) + "</b>" + resultmessage));
        
        TextView lblRecomm = (TextView)findViewById(R.id.lblrecommendation);
        lblRecomm.setText(recommendation);
        
        TextView lblResearch = (TextView)findViewById(R.id.lblresearch);
        lblResearch.setText(Html.fromHtml(getString(R.string.research) + "<a href=\"" + researchpaper + "\">" + getString(R.string.here) + "</a>"));
        lblResearch.setMovementMethod(LinkMovementMethod.getInstance());
        
        //close activity
        Button cmdOk = (Button)findViewById(R.id.cmdOk);
        cmdOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	finish();
            }
        });
    }
    
    private float calculateGValue(float zvalue){
    	float g = (-1*(-3 - zvalue)*100)/6;
    	return g;
    }
}

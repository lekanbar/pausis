package com.cs.pausis;

import java.util.Hashtable;

import org.achartengine.GraphicalView;

import com.core.pausis.R;
import com.cs.pausis.models.Result;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultVisualizer extends Activity  {
    
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
       
       if(isfirsttime){
    	   DEFAULT_GAUGE_FULL_RANGE_VALUES = new Hashtable<Integer, Float>();
    	   DEFAULT_GAUGE_FULL_RANGE_VALUES.put(-3, 0.0f); DEFAULT_GAUGE_FULL_RANGE_VALUES.put(-2, 14.3f);
    	   DEFAULT_GAUGE_FULL_RANGE_VALUES.put(-1, 28.6f); DEFAULT_GAUGE_FULL_RANGE_VALUES.put(Integer.valueOf(0), 42.9f);
    	   DEFAULT_GAUGE_FULL_RANGE_VALUES.put(1, 57.2f); DEFAULT_GAUGE_FULL_RANGE_VALUES.put(2, 71.5f);
    	   DEFAULT_GAUGE_FULL_RANGE_VALUES.put(3, 85.8f);
    	   
    	   AFC_GAUGE_FULL_RANGE_VALUES = new Hashtable<Integer, Float>();
    	   AFC_GAUGE_FULL_RANGE_VALUES.put(5, 0.0f); AFC_GAUGE_FULL_RANGE_VALUES.put(25, 20.0f);
    	   AFC_GAUGE_FULL_RANGE_VALUES.put(50, 40.0f); AFC_GAUGE_FULL_RANGE_VALUES.put(75, 60.0f);
    	   AFC_GAUGE_FULL_RANGE_VALUES.put(95, 80.0f);
       }
       InitializeUI();
    }
    
    private void InitializeUI(){    	
    	String val = "";
    	
    	LinearLayout layafc = (LinearLayout)findViewById(R.id.layafc);
    	LinearLayout laydefault = (LinearLayout)findViewById(R.id.laydefault);
    	
    	if(result.getType().equals(Result.Type.AFC.toString())){
    		val = getString(R.string.afc);
    		
    		GaugeViewForAFC mGaugeView3 = (GaugeViewForAFC) findViewById(R.id.gauge_view3);
    		GaugeViewForAFC mGaugeView4 = (GaugeViewForAFC) findViewById(R.id.gauge_view4);
        	
    		Integer sd = Integer.valueOf(result.getValue());
    		mGaugeView3.setTargetValue(AFC_GAUGE_FULL_RANGE_VALUES.get(sd).floatValue());
    		mGaugeView4.setTargetValue(sd);
    		
    		layafc.setVisibility(View.VISIBLE);
    		laydefault.setVisibility(View.GONE);
    	}
    	else{
			if(result.getType().equals(Result.Type.AMH.toString()))
				val = getString(R.string.amh);
			else
				val = getString(R.string.vol);
			
			GaugeView mGaugeView1 = (GaugeView) findViewById(R.id.gauge_view1);
			GaugeView mGaugeView2 = (GaugeView) findViewById(R.id.gauge_view2);
	    	
			Integer sd = (int)Float.parseFloat(result.getValue());
			mGaugeView1.setTargetValue(DEFAULT_GAUGE_FULL_RANGE_VALUES.get(sd).floatValue());
    		mGaugeView2.setTargetValue(sd);
    		
    		layafc.setVisibility(View.GONE);
    		laydefault.setVisibility(View.VISIBLE);
		}
    	
		TextView lblDesc = (TextView)findViewById(R.id.lbldescription);
        lblDesc.setText(Html.fromHtml("<b>") + getString(R.string.descriptiontitle) + Html.fromHtml("</b>") + getString(R.string.description) + val);
        
        TextView lblStatus = (TextView)findViewById(R.id.lblstatus);
        if(result.getStatus().equals(Result.Status.GREEN.toString())){
        	lblStatus.setText(Html.fromHtml("<b>") + getString(R.string.resultstatus) + Html.fromHtml("</b>") + getString(R.string.positive));
        	lblStatus.setTextColor(Color.GREEN);
        }
        else if(result.getStatus().equals(Result.Status.YELLOW.toString())){
			lblStatus.setText(Html.fromHtml("<b>") + getString(R.string.resultstatus) + Html.fromHtml("</b>") + getString(R.string.neutral));
        	lblStatus.setTextColor(Color.parseColor("#E9F507"));
		}
		else if(result.getStatus().equals(Result.Status.ORANGE.toString())){
			lblStatus.setText(Html.fromHtml("<b>") + getString(R.string.resultstatus) + Html.fromHtml("</b>") + getString(R.string.neutral));
        	lblStatus.setTextColor(Color.parseColor("#F59207"));
		}
		else{
			lblStatus.setText(Html.fromHtml("<b>") + getString(R.string.resultstatus) + Html.fromHtml("</b>") + getString(R.string.negative));
        	lblStatus.setTextColor(Color.RED);
		}
        
        TextView lblResearch = (TextView)findViewById(R.id.lblresearch);
        
    }
}

package com.cs.pausis;

import org.achartengine.GraphicalView;
import org.codeandmagic.android.gauge.GaugeView;

import com.core.pausis.R;
import com.cs.pausis.models.Result;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class ResultVisualizer extends Activity  {
    
    TextView content;
    Result result;
    GraphicalView view;
    
    protected void onCreate(Bundle savedInstanceState) {    
       super.onCreate(savedInstanceState);
       setContentView(R.layout.visualizer);       
       
       result = getIntent().getParcelableExtra("result");
       
       InitializeUI();
    }
    
    private void InitializeUI(){    	
    	String val = "";
    	GaugeView mGaugeView1 = (GaugeView) findViewById(R.id.gauge_view1);
		
    	GaugeView mGaugeView2 = (GaugeView) findViewById(R.id.gauge_view2);
    	
    	if(result.getType().equals(Result.Type.AFC.toString())){
    		val = getString(R.string.afc);
    		mGaugeView1.setTargetValue(Float.parseFloat(result.getValue()));
    		mGaugeView2.setTargetValue(Float.parseFloat(result.getValue()));
    	}
		else if(result.getType().equals(Result.Type.AMH.toString())){
			val = getString(R.string.amh);
			mGaugeView1.setTargetValue((float) result.getSdvalues()[3]);
    		mGaugeView2.setTargetValue((float) result.getSdvalues()[3]);
		}
		else{
			val = getString(R.string.vol);
			mGaugeView1.setTargetValue((float) result.getSdvalues()[3]);
    		mGaugeView2.setTargetValue((float) result.getSdvalues()[3]);
		}
    	
		TextView lblDesc = (TextView)findViewById(R.id.lbldescription);
        lblDesc.setText(Html.fromHtml("<b>") + getString(R.string.descriptiontitle) + Html.fromHtml("</b>") + getString(R.string.description) + val);
        
        TextView lblStatus = (TextView)findViewById(R.id.lblstatus);
        if(result.getStatus().equals(Result.Status.GREEN.toString())){
        	lblStatus.setText(Html.fromHtml("<b>") + getString(R.string.resultstatus) + Html.fromHtml("</b>") + getString(R.string.positive));
        	lblStatus.setTextColor(Color.GREEN);
        }
		else if(result.getStatus().equals(Result.Status.ORANGE.toString())){
			lblStatus.setText(Html.fromHtml("<b>") + getString(R.string.resultstatus) + Html.fromHtml("</b>") + getString(R.string.neutral));
        	lblStatus.setTextColor(Color.parseColor("#FF9900"));
		}
		else{
			lblStatus.setText(Html.fromHtml("<b>") + getString(R.string.resultstatus) + Html.fromHtml("</b>") + getString(R.string.negative));
        	lblStatus.setTextColor(Color.RED);
		}
    }
}

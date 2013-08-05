package com.cs.pausis;

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
    	String resultmessage = "", researchpaper = "", recommendation = "";
    	
    	LinearLayout layafc = (LinearLayout)findViewById(R.id.layafc);
    	LinearLayout laydefault = (LinearLayout)findViewById(R.id.laydefault);
    	
    	if(result.getType().equals(Result.Type.AFC.toString())){
    		researchpaper = "http://www.sciencedirect.com/science/article/pii/S0015028210021953";
    		
    		GaugeViewForAFC mGaugeView3 = (GaugeViewForAFC) findViewById(R.id.gauge_view3);
    		GaugeViewForAFC mGaugeView4 = (GaugeViewForAFC) findViewById(R.id.gauge_view4);
        	
    		Integer sd = Integer.valueOf(result.getValue());
    		mGaugeView3.setTargetValue(AFC_GAUGE_FULL_RANGE_VALUES.get(sd).floatValue());
    		mGaugeView4.setTargetValue(sd);
    		
    		resultmessage = getString(R.string.afcdescription) + sd + "th" + getString(R.string.percentile);
    		
    		layafc.setVisibility(View.VISIBLE);
    		laydefault.setVisibility(View.GONE);
    	}
    	else{
			GaugeView mGaugeView1 = (GaugeView) findViewById(R.id.gauge_view1);
			GaugeView mGaugeView2 = (GaugeView) findViewById(R.id.gauge_view2);
	    	
			double fl = Float.parseFloat(result.getValue()) < 0 ? Math.floor(Float.parseFloat(result.getValue())) : Math.ceil(Float.parseFloat(result.getValue()));
			Integer sd = (int)fl;
			mGaugeView1.setTargetValue(DEFAULT_GAUGE_FULL_RANGE_VALUES.get(sd).floatValue());
    		mGaugeView2.setTargetValue(sd);
    		
    		if(result.getType().equals(Result.Type.AMH.toString())){
				resultmessage = getString(R.string.amhdescription) + (sd <= 0 ? sd : "+" + sd) + (sd < 0 ? getString(R.string.belowmean) : getString(R.string.abovemean));
				researchpaper = "http://www.pubmedcentral.nih.gov/articlerender.fcgi?artid=3137624&tool=pmcentrez&rendertype=abstract";
			}
			else{
				resultmessage = getString(R.string.ovadescription) + (sd <= 0 ? sd : "+" + sd) + (sd < 0 ? getString(R.string.belowmean) : getString(R.string.abovemean));
				researchpaper = "http://www.ncbi.nlm.nih.gov/pubmed/20111701";
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
        	lblStatus.setTextColor(Color.parseColor("#F59207"));
        	
        	recommendation = getString(R.string.neutralrecommendation);
		}
		else{
			lblStatus.setText(Html.fromHtml("<b>" + getString(R.string.resultstatus) + "</b>" + getString(R.string.negative)));
        	lblStatus.setTextColor(Color.RED);
        	
        	recommendation = getString(R.string.negativerecommendation);
		}
        
        TextView lblDesc = (TextView)findViewById(R.id.lbldescription);
        lblDesc.setText(Html.fromHtml("<b>" + getString(R.string.descriptiontitle) + "</b>" + resultmessage + " " + recommendation));
        
        TextView lblResearch = (TextView)findViewById(R.id.lblresearch);
        lblResearch.setText(Html.fromHtml(getString(R.string.research) + "<a href=\"" + researchpaper + "\">" + getString(R.string.here) + "</a>"));
        lblResearch.setMovementMethod(LinkMovementMethod.getInstance());
        
        Button cmdOk = (Button)findViewById(R.id.cmdOk);
        cmdOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	finish();
            }
        });
    }
}

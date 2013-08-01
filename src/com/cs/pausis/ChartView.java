package com.cs.pausis;

import org.achartengine.GraphicalView;
import org.codeandmagic.android.gauge.GaugeView;

import com.core.pausis.R;
import com.cs.pausis.models.Result;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ChartView extends Activity  {
    
    TextView content;
    Result result;
    GraphicalView view;
    int type;
    
    protected void onCreate(Bundle savedInstanceState) {    
       super.onCreate(savedInstanceState);
       setContentView(R.layout.gaugeview);       
       
       type = getIntent().getIntExtra("type", 0);
       result = getIntent().getParcelableExtra("result");
       
       InitializeUI();
    }
    
    protected void onResume() {
    	super.onResume();
    	
    	/*if(view == null){
	    	if(type == 0){
	      	   ReserveXYBarChart mBarChart = new ReserveXYBarChart(ChartView.this, result);
	  		   view = mBarChart.execute();
	        }
	        else {
	      	   ReserveGaugeChart mGaugeChart = new ReserveGaugeChart(result);
	      	   view = mGaugeChart.execute(ChartView.this, 0);
	  	    }
	    	
	    	LinearLayout graphLayout = (LinearLayout)findViewById(R.id.chart);
	    	graphLayout.addView(view);
    	}
    	else {
			view.repaint();
		}*/
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
    	
    	/*if(view == null){
	    	if(type == 0){
	      	   ReserveXYBarChart mBarChart = new ReserveXYBarChart(ChartView.this, result);
	  		   //view = mBarChart.execute();
	        }
	        else {
	      	   ReserveGaugeChart mGaugeChart = new ReserveGaugeChart(result);
	      	   //view = mGaugeChart.execute(ChartView.this, 0);
	  	    }
	    	
	    	//if( false == view.isDrawingCacheEnabled()){
	    		   //view.setDrawingCacheEnabled(true);
	    		//}
	    	Bitmap bitmap = view.toBitmap();
	    	//LinearLayout graphLayout = (LinearLayout)findViewById(R.id.chart);
	    	//graphLayout.addView(view);
	    	ImageView imgc = (ImageView)findViewById(R.id.imgchart);
	    	imgc.setImageBitmap(bitmap);
    	}
    	else {
			view.repaint();
		}
    	
    	ReserveXYBarChart mBarChart = new ReserveXYBarChart(ChartView.this, result);
		   Intent view2 = mBarChart.execute();
		   
		   startActivity(view2);
    	
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
		}*/
    }
}

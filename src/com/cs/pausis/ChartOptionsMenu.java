package com.cs.pausis;

import java.util.ArrayList;

import org.achartengine.GraphicalView;

import com.core.pausis.R;
import com.cs.pausis.models.Result;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChartOptionsMenu extends ListActivity  {
    
    TextView content;
    ArrayList<Result> results;
    GraphicalView view;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {    
       super.onCreate(savedInstanceState);
       setContentView(R.layout.list_activity);
        
       String[] values = new String[] { "Bar Chart", "Gauge Chart" };

       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
       // Assign adapter to List
       setListAdapter(adapter);
       
       results = getIntent().getExtras().getParcelableArrayList("results");
   }

   @Override
   protected void onListItemClick(ListView l, View v, int position, long id) {        
	   super.onListItemClick(l, v, position, id);
	   // ListView Clicked item index
	   int itemPosition = position;	   
	   int pos = getIntent().getIntExtra("position", 0);
	   Result result = results.get(pos);
	   
	   if (itemPosition == 0) {
		   ReserveXYBarChart mBarChart = new ReserveXYBarChart(ChartOptionsMenu.this, result);
	  	   Intent intent = mBarChart.execute();
	  	   startActivity(intent);
		   /*Intent intent = new Intent(ChartOptionsMenu.this, ChartView.class);
	       intent.putExtra("type", itemPosition);
	       intent.putExtra("result", result);
		   startActivity(intent);*/
	   }
	   else {
      	   //ReserveGaugeChart mGaugeChart = new ReserveGaugeChart(result);
      	   //Intent intent = mGaugeChart.execute(ChartOptionsMenu.this, 0);
		   Intent intent = new Intent(ChartOptionsMenu.this, ChartView.class);
	       intent.putExtra("type", itemPosition);
	       intent.putExtra("result", result);
      	   startActivity(intent);
  	    }
	   
	   /*if (itemPosition == 0) {
		   Intent intent = new Intent(ChartOptionsMenu.this, XYChartBuilder.class);
	       intent.putExtra("type", itemPosition);
	       intent.putExtra("result", result);
		   startActivity(intent);
	   }
	   else {
		   //Start intent
	       Intent intent = new Intent(ChartOptionsMenu.this, ChartView.class);
	       intent.putExtra("type", itemPosition);
	       intent.putExtra("result", result);
		   startActivity(intent);
	   }*/
       
   } 
}

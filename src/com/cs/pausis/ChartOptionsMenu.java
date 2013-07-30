package com.cs.pausis;

import java.util.ArrayList;

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {    
       super.onCreate(savedInstanceState);
       setContentView(R.layout.list_activity);
        
       String[] values = new String[] { "Bar Chart", "Gauge Chart", "Line Chart"};

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
	   Intent intent;
	   
	   int pos = getIntent().getIntExtra("position", 0);
	   Result result = results.get(pos);	   
	   
       if(itemPosition == 0){
    	   ReserveXYBarChart mBarChart = new ReserveXYBarChart(ChartOptionsMenu.this, result);
		   intent = mBarChart.execute();
       }
       else {
    	   ReserveGaugeChart mGaugeChart = new ReserveGaugeChart(result);			
    	   intent = mGaugeChart.execute(ChartOptionsMenu.this);
	   }
       
       //Start intent
	   startActivity(intent);
   }
}

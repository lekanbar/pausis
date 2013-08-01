/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cs.pausis;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.core.pausis.R;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.Result.Type;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class XYChartBuilder extends Activity {
  /** The main dataset that includes all the series that go into a chart. */
  private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
  /** The main renderer that includes all the renderers customizing a chart. */
  private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
  /** The most recently added series. */
  private XYSeries mCurrentSeries;
  /** The most recently created renderer, customizing the current series. */
  private XYSeriesRenderer mCurrentRenderer;
  /** Button for creating a new series of data. */
  private Button mNewSeries;
  /** Button for adding entered data to the current series. */
  private Button mAdd;
  /** Edit text field for entering the X value of the data to be added. */
  private EditText mX;
  /** Edit text field for entering the Y value of the data to be added. */
  private EditText mY;
  /** The chart view that displays the data. */
  private GraphicalView mChartView;
  
  Result result;
  
  Runnable updateTextView = new Runnable() {
	   @Override
	   public void run() {
		   LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		   layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
			          LayoutParams.FILL_PARENT));
	   }
	};

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    // save the current data, for instance when changing screen orientation
    outState.putSerializable("dataset", mDataset);
    outState.putSerializable("renderer", mRenderer);
    outState.putSerializable("current_series", mCurrentSeries);
    outState.putSerializable("current_renderer", mCurrentRenderer);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedState) {
    super.onRestoreInstanceState(savedState);
    // restore the current data, for instance when changing the screen
    // orientation
    mDataset = (XYMultipleSeriesDataset) savedState.getSerializable("dataset");
    mRenderer = (XYMultipleSeriesRenderer) savedState.getSerializable("renderer");
    mCurrentSeries = (XYSeries) savedState.getSerializable("current_series");
    mCurrentRenderer = (XYSeriesRenderer) savedState.getSerializable("current_renderer");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.chart_main);

    // the top part of the UI components for adding new data points
    //mX = (EditText) findViewById(R.id.xValue);
    //mY = (EditText) findViewById(R.id.yValue);
    //mAdd = (Button) findViewById(R.id.add);
    result = getIntent().getExtras().getParcelable("result");

    // set some properties on the main renderer
    mRenderer.setApplyBackgroundColor(true);
    mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
    mRenderer.setAxisTitleTextSize(16);
    mRenderer.setChartTitleTextSize(20);
    mRenderer.setLabelsTextSize(15);
    mRenderer.setLegendTextSize(15);
    mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
    mRenderer.setZoomButtonsVisible(true);
    mRenderer.setPointSize(5);
    
    mRenderer.setXAxisMin(0);
    mRenderer.setXAxisMax(11);
    mRenderer.setYAxisMin(0);
    mRenderer.setYLabels(10);
    mRenderer.setXLabels(0);
    
    if(!result.getType().equals(Result.Type.AFC.toString())){
    	mRenderer.setYTitle("SD");
    	mRenderer.setYAxisMax(Math.ceil(result.getSdvalues()[6]));
    }
    else{
    	mRenderer.setYTitle("Percentile");
    	mRenderer.setYAxisMax(Math.ceil(result.getSdvalues()[4]));
    }
  }
  
  private void setSeriesForDataSet(){
		CategorySeries series = new CategorySeries("Group");
		
		if(!result.getType().equals(Result.Type.AFC.toString())){
		    for (int k = 0; k < result.getSdvalues().length; k++) {
		    	if(k == 3){
		    		series.add(0);
		    		series.add(0);
		    		continue;
		    	}
		    	series.add(result.getSdvalues()[k]);
		    }
		    mDataset.addSeries(series.toXYSeries());
		      
		    series = new CategorySeries("You");
		    series.add(0);
		    series.add(0);
		    series.add(0);
		    series.add(0);
		    series.set(3, "", result.getSdvalues()[3]);
		    mDataset.addSeries(series.toXYSeries());
		}
		else {
			int position = 0;
		    for (int k = 0; k < result.getSdvalues().length; k++) {
		    	if(Double.parseDouble(result.getValue()) == result.getSdvalues()[k]){
		    		series.add(0);
		    		series.add(0);
		    		position = k;
		    		continue;
		    	}
		    	series.add(result.getSdvalues()[k]);
		    }
		    mDataset.addSeries(series.toXYSeries());
		      
		    series = new CategorySeries("You");
		    for (int i = 0; i <= position; i++) {
				series.add(0);
			}
		    series.set(position, "", result.getSdvalues()[position]);
		    mDataset.addSeries(series.toXYSeries());
		}
	}
  
  public void getBarRenderer() {
	    //XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    mRenderer.setAxisTitleTextSize(16);
	    mRenderer.setChartTitleTextSize(20);
	    mRenderer.setLabelsTextSize(15);
	    mRenderer.setLegendTextSize(15);
	    mRenderer.setMargins(new int[] {20, 30, 15, 5});
	    mRenderer.setApplyBackgroundColor(true);
	    mRenderer.setBackgroundColor(Color.parseColor("#33FFCC"));
	    mRenderer.setShowGridX(true);
	    
	    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	    r.setColor(Color.WHITE);
	    mRenderer.addSeriesRenderer(r);
	    
	    r = new SimpleSeriesRenderer();
	    r.setHighlighted(true);
	    
	    if(result.getStatus().equals(Result.Status.GREEN.toString()))
	    	r.setColor(Color.GREEN);
		else if(result.getStatus().equals(Result.Status.ORANGE.toString()))
			r.setColor(Color.parseColor("#FF9900"));
		else
			r.setColor(Color.RED);
	    
	    mRenderer.addSeriesRenderer(r);
	    
	    //return mRenderer;
	}

  @Override
  protected void onResume() {
    super.onResume();
    if (mChartView == null) {
      //LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
      getBarRenderer();
      setSeriesForDataSet();
      mChartView = ChartFactory.getLineChartView(this, mDataset, mRenderer);
      // enable the chart click events
      mRenderer.setClickEnabled(true);
      mRenderer.setSelectableBuffer(10);
      mChartView.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          // handle the click event on the chart
          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
          if (seriesSelection == null) {
            Toast.makeText(XYChartBuilder.this, "No chart element", Toast.LENGTH_SHORT).show();
          } else {
            // display information of the clicked point
            Toast.makeText(
                XYChartBuilder.this,
                "Chart element in series index " + seriesSelection.getSeriesIndex()
                    + " data point index " + seriesSelection.getPointIndex() + " was clicked"
                    + " closest point value X=" + seriesSelection.getXValue() + ", Y="
                    + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
          }
        }
      });
      runOnUiThread(updateTextView);
      //layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
        //  LayoutParams.FILL_PARENT));
      boolean enabled = mDataset.getSeriesCount() > 0;
      setSeriesWidgetsEnabled(enabled);
    } else {
      mChartView.repaint();
    }
  }

  /**
   * Enable or disable the add data to series widgets
   * 
   * @param enabled the enabled state
   */
  private void setSeriesWidgetsEnabled(boolean enabled) {
    
  }
}
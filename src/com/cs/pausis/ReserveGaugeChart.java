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
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.DialRenderer.Type;

import com.cs.pausis.models.Result;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Budget demo pie chart.
 */
public class ReserveGaugeChart extends AbstractDemoChart {
	
	double current, maximum, minimum;
	DialRenderer renderer;
	CategorySeries category;
	Result result;
	
	public ReserveGaugeChart(Result result) {
		this.result = result;
	}
	
  /**
   * Returns the chart name.
   * @return the chart name
   */
  public String getName() {
    return "Ovary Reserve";
  }
  
  /**
   * Returns the chart description.
   * @return the chart description
   */
  public String getDesc() {
    return "Gauge for visulaizing the ovary reserve.";
  }
  
  public double getCurrent() {
	return current;
  }

  public void setCurrent(double current) {
	this.current = current;
  }

  public double getMaximum() {
	return maximum;
  }

  public void setMaximum(double maximum) {
	this.maximum = maximum;
  }

  public double getMinimum() {
	return minimum;
  }

  public void setMinimum(double minimum) {
	this.minimum = minimum;
  }
  
  private void setSeriesForRenderer(){
	  SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	  
	  if(!result.getType().equals(Result.Type.AFC.toString())){
	      for (int i = 0; i < result.getSdvalues().length; i++) {
	    	  category.add("", result.getSdvalues()[i]);
	    	
	    	  if(i == 3){
	    		 r = new SimpleSeriesRenderer();
	    		 r.setColor(Color.GREEN);
	    	     renderer.addSeriesRenderer(r);
	    	  }
	    	  else {
	    		 r = new SimpleSeriesRenderer();
	    	     r.setColor(Color.TRANSPARENT);
	    	     renderer.addSeriesRenderer(r);
			  }
		  }
	  }
	  else {
		  String legendName = "";
		  for (int i = 0; i < result.getSdvalues().length; i++) {
	    	  if(Double.parseDouble(result.getValue()) == result.getSdvalues()[i]){
	    		 r = new SimpleSeriesRenderer();
	    		 r.setColor(Color.GREEN);
	    	     renderer.addSeriesRenderer(r);
	    	     legendName = "You";
	    	  }
	    	  else {
	    		 r = new SimpleSeriesRenderer();
	    	     r.setColor(Color.TRANSPARENT);
	    	     renderer.addSeriesRenderer(r);
	    	     legendName = "";
			  }
	    	  
	    	  category.add(legendName, result.getSdvalues()[i]);
		  }
	  }
  }

  /**
   * Executes the chart demo.
   * @param context the context
   * @return the built intent
   */
  @Override
  public Intent execute(Context context) {	  
      category = new CategorySeries("Ovaries gauge");
      category.add("Current", getCurrent());
    
      renderer = new DialRenderer();
      renderer.setChartTitleTextSize(20);
      renderer.setLabelsTextSize(15);
      renderer.setLegendTextSize(15);
      renderer.setMargins(new int[] {20, 30, 15, 0});
    
      //Set the chart series and their corresponding values
      setSeriesForRenderer();
     
      renderer.setChartTitle("Your position by the provided " + result.getType() + " value");
      renderer.setLabelsColor(Color.WHITE);
      renderer.setAxesColor(Color.RED);
      renderer.setShowLabels(true);
      renderer.setVisualTypes(new DialRenderer.Type[] {Type.NEEDLE});
      
      renderer.setMinValue(0);
      if(!result.getType().equals(Result.Type.AFC.toString())) {
	      renderer.setMaxValue(Math.pow(10, Math.ceil(Math.log10(result.getSdvalues()[6]))));
	      renderer.setMajorTicksSpacing(1);
	      renderer.setMinorTicksSpacing(0.5);
      }
      else {
	      renderer.setMaxValue(result.getSdvalues()[4]);
	      renderer.setMajorTicksSpacing(5);
	      renderer.setMinorTicksSpacing(5);
	  }
    
      return ChartFactory.getDialChartIntent(context, category, renderer, "Indicator");
  }
}

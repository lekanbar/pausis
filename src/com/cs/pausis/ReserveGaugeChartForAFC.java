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

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Budget demo pie chart.
 */
public class ReserveGaugeChartForAFC extends AbstractDemoChart {
	
	double current, maximum, minimum;
	
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

  /**
   * Executes the chart demo.
   * @param context the context
   * @return the built intent
   */
  public Intent execute(Context context) {
    CategorySeries category = new CategorySeries("Ovary Gauge");
    category.add("Current", getCurrent());
    category.add("Minimum", 5);
    category.add("Maximum", 95);
    
    DialRenderer renderer = new DialRenderer();
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setMargins(new int[] {20, 30, 15, 0});
    
    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
    r.setColor(Color.BLUE);
    renderer.addSeriesRenderer(r);
    
    r = new SimpleSeriesRenderer();
    r.setColor(Color.RED);//.rgb(0, 150, 0)
    renderer.addSeriesRenderer(r);
    
    r = new SimpleSeriesRenderer();
    r.setColor(Color.GREEN);
    renderer.addSeriesRenderer(r);
    
    renderer.setLabelsTextSize(10); 
    renderer.setChartTitle("Chart of life");
    renderer.setLabelsColor(Color.YELLOW);
    renderer.setAxesColor(Color.RED);
    renderer.setShowLabels(true);
    renderer.setVisualTypes(new DialRenderer.Type[] {Type.ARROW, null, Type.NEEDLE, Type.NEEDLE});
    renderer.setMinValue(-2);
    renderer.setMaxValue(2);
    renderer.setMajorTicksSpacing(1);
    renderer.setMinorTicksSpacing(0.25);
    
    return ChartFactory.getDialChartIntent(context, category, renderer, "Weight indicator");
  }

}

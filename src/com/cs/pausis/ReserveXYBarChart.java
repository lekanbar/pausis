package com.cs.pausis;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.cs.pausis.models.Result;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class ReserveXYBarChart {
	
	Result result;
	Context context;
	XYMultipleSeriesDataset dataset;
	
	public ReserveXYBarChart(Context context, Result result){
		this.context = context;
		this.result = result;
	}
	
	public Intent execute(){
		XYMultipleSeriesRenderer renderer = getBarRenderer();
	    setChartSettings(renderer);
	    Intent intent = ChartFactory.getBarChartIntent(context, getBarDataset(), renderer, Type.DEFAULT);
	    return intent;
	}
	/*public GraphicalView execute(){
		XYMultipleSeriesRenderer renderer = getBarRenderer();
	    setChartSettings(renderer);
	    GraphicalView intent = ChartFactory.getBarChartView(context, getBarDataset(), renderer, Type.DEFAULT);
	    return intent;
	}*/
	
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
		    dataset.addSeries(series.toXYSeries());
		      
		    series = new CategorySeries("You");
		    series.add(0);
		    series.add(0);
		    series.add(0);
		    series.add(0);
		    series.set(3, "", result.getSdvalues()[3]);
		    dataset.addSeries(series.toXYSeries());
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
		    dataset.addSeries(series.toXYSeries());
		      
		    series = new CategorySeries("You");
		    for (int i = 0; i <= position; i++) {
				series.add(0);
			}
		    series.set(position, "", result.getSdvalues()[position]);
		    dataset.addSeries(series.toXYSeries());
		}
	}
	
	private void setChartSettings(XYMultipleSeriesRenderer renderer) {
	    renderer.setChartTitle("Your position by the provided " + result.getType() + " value");
	    //renderer.setXTitle("X");
	    renderer.setXAxisMin(0);
	    renderer.setXAxisMax(11);
	    renderer.setYAxisMin(0);
	    renderer.setYLabels(10);
	    renderer.setXLabels(0);
	    
	    if(!result.getType().equals(Result.Type.AFC.toString())){
	    	renderer.setYTitle("SD");
	    	renderer.setYAxisMax(Math.ceil(result.getSdvalues()[6]));
	    }
	    else{
	    	renderer.setYTitle("Percentile");
	    	renderer.setYAxisMax(Math.ceil(result.getSdvalues()[4]));
	    }
    }

	private XYMultipleSeriesDataset getBarDataset() {
	    dataset = new XYMultipleSeriesDataset();
	    //Set renderer
	    setSeriesForDataSet();
	    return dataset;
	}

	public XYMultipleSeriesRenderer getBarRenderer() {
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] {20, 30, 15, 5});
	    renderer.setApplyBackgroundColor(true);
	    renderer.setBackgroundColor(Color.BLACK);//.parseColor("#33FFCC")
	    renderer.setShowGridX(true);
	    
	    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	    r.setColor(Color.WHITE);
	    renderer.addSeriesRenderer(r);
	    
	    r = new SimpleSeriesRenderer();
	    r.setHighlighted(true);
	    
	    if(result.getStatus().equals(Result.Status.GREEN.toString()))
	    	r.setColor(Color.GREEN);
		else if(result.getStatus().equals(Result.Status.ORANGE.toString()))
			r.setColor(Color.parseColor("#FF9900"));
		else
			r.setColor(Color.RED);
	    
	    renderer.addSeriesRenderer(r);
	    
	    return renderer;
	}
}

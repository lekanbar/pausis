package com.cs.pausis;

import android.content.Context;

import java.util.*;

import com.core.pausis.R;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.UserInputValues;

import android.view.*;
import android.widget.*;

public class HistoryAdapter extends ArrayAdapter<UserInputValues> {
	int resource;
	Context context;
	
	public HistoryAdapter(Context _context, int _resource, List<UserInputValues> _items) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LinearLayout historyView;
		UserInputValues item = getItem(position);
		
	    String byear = item.getBirthYear(),
	    	   bmonth = item.getBirthMonth(),
	    	   indicator = item.getResultIndicator(),
	    	   dateString = item.getDateTime();
		
		if(convertView == null){
			historyView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
			vi.inflate(resource, historyView, true);
		}
		else{
			historyView = (LinearLayout)convertView;
		}
		
		//Set menu item values
		TextView textYearView = (TextView)historyView.findViewById(R.id.birthyear);
		TextView textMonthView = (TextView)historyView.findViewById(R.id.birthmonth);
		TextView dateView = (TextView)historyView.findViewById(R.id.rowDate);
		TextView timeView = (TextView)historyView.findViewById(R.id.rowTime);
		ImageView indicatorView = (ImageView)historyView.findViewById(R.id.imgIndicator);
		
		textYearView.setText(context.getString(R.string.birthyear) + ": " + byear);
		textMonthView.setText(context.getString(R.string.birthmonth) + ": " + bmonth);
		
		//Set the menu title
		if(indicator.equals(Result.Status.GREEN.toString()))
			indicatorView.setImageDrawable(historyView.getResources().getDrawable(R.drawable.res_green));
		else if(indicator.equals(Result.Status.ORANGE.toString()))
			indicatorView.setImageDrawable(historyView.getResources().getDrawable(R.drawable.res_orange));
		else
			indicatorView.setImageDrawable(historyView.getResources().getDrawable(R.drawable.res_red));
		
		dateView.setText(dateString.substring(0, dateString.indexOf(' ')));
		timeView.setText(dateString.substring(dateString.indexOf(' ')));
		
		return historyView;
	}
}

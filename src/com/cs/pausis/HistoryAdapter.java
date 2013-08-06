package com.cs.pausis;

import android.content.Context;

import java.util.*;

import com.core.pausis.R;
import com.cs.pausis.models.Result;
import com.cs.pausis.models.UserInputValues;

import android.view.*;
import android.widget.*;

/**
 * This is a customized array adapter class which helps to format each of the values to be displayed on the History list.
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class HistoryAdapter extends ArrayAdapter<UserInputValues> {
	int resource;
	Context context;
	
	/**
	 * Default constructor for the adapter 
	 * 
	 * @param _context - the calling class's context
	 * @param _resource - the resource id
	 * @param _items - the items to be displayed
	 */
	public HistoryAdapter(Context _context, int _resource, List<UserInputValues> _items) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LinearLayout historyView;
		
		//Get the current item to be formatted
		UserInputValues item = getItem(position);
		
		//get the values
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
		
		//Get the controls on the layout
		TextView textYearView = (TextView)historyView.findViewById(R.id.birthyear);
		TextView textMonthView = (TextView)historyView.findViewById(R.id.birthmonth);
		TextView dateView = (TextView)historyView.findViewById(R.id.rowDate);
		TextView timeView = (TextView)historyView.findViewById(R.id.rowTime);
		ImageView indicatorView = (ImageView)historyView.findViewById(R.id.imgIndicator);
		
		textYearView.setText(context.getString(R.string.birthyear) + byear + ", " + context.getString(R.string.birthmonth) + bmonth);
		
		//Set the values into the respective views
		String base = "";
		if(!item.getAfc().equals(""))
			base += "AFC|";
		if(!item.getAmhvolume().equals(""))
			base += "AMH|";
		if(!item.getOvarianvolume().equals(""))
			base += "OVA|";
		if(!item.getFsh().equals(""))
			base += "FSH|";
		textMonthView.setText(base);
		
		//Set the icons based on the calculated result
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

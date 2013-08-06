package com.cs.pausis;

import android.content.Context;

import java.util.*;

import com.core.pausis.R;
import com.cs.pausis.models.Result;

import android.view.*;
import android.widget.*;

/**
 * This is a customized array adapter class which helps to format each of the values to be displayed on the results summary page.
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class ResultAdapter extends ArrayAdapter<Result> {
	int resource;
	Context context;
	
	/**
	 * Default constructor for the adapter 
	 * @param _context
	 * @param _resource
	 * @param _items
	 */
	public ResultAdapter(Context _context, int _resource, List<Result> _items) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LinearLayout resultView;
		
		//Get the current result item to be formatted
		Result item = getItem(position);
		
		String statusString = item.getStatus(),
			   //valueString = item.getValue(),
		       //descriptionString = item.getDescription(),
		       typeString = item.getType();
		
		if(convertView == null){
			resultView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
			vi.inflate(resource, resultView, true);
		}
		else{
			resultView = (LinearLayout)convertView;
		}
		
		TextView textView = (TextView)resultView.findViewById(R.id.text);
		//TextView calcView = (TextView)resultView.findViewById(R.id.calcvalue);
		ImageView indicatorView = (ImageView)resultView.findViewById(R.id.imgIndicator);
		
		//Get status based on color
		String status = "";
		if(statusString.equals(Result.Status.GREEN.toString()))
			status = context.getString(R.string.positive);
		else if(statusString.equals(Result.Status.ORANGE.toString()))
			status = context.getString(R.string.neutral);
		else
			status = context.getString(R.string.negative);
		
		//Set result status image
		if(statusString.equals(Result.Status.GREEN.toString()))
			indicatorView.setImageDrawable(resultView.getResources().getDrawable(R.drawable.res_green));
		else if(statusString.equals(Result.Status.ORANGE.toString()))
			indicatorView.setImageDrawable(resultView.getResources().getDrawable(R.drawable.res_orange));
		else
			indicatorView.setImageDrawable(resultView.getResources().getDrawable(R.drawable.res_red));
		
		//Set the result description
		if(typeString.equals(Result.Type.AFC.toString()))
			textView.setText(context.getString(R.string.afcresult) + status);
		else if(typeString.equals(Result.Type.AMH.toString()))
			textView.setText(context.getString(R.string.amhresult) + status);
		else
			textView.setText(context.getString(R.string.ovaresult) + status);
		
		//Set result view
		//calcView.setText(context.getString(R.string.calcvalue) + valueString);		
		
		return resultView;
	}
}

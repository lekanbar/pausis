package com.cs.pausis;

import android.content.Context;

import java.util.*;

import com.core.pausis.R;
import com.cs.pausis.models.MenuItem;

import android.view.*;
import android.widget.*;

/**
 * This is a customized array adapter class which helps to format each of the values to be displayed on the Main Menu.
 * 
 * @author Olalekan Baruwa
 * @email oab@st-andrews.ac.uk or baruwa.lekan@gmail.com
 * @version v1.0
 * @since August, 2013
 *
 */
public class MenuAdapter extends ArrayAdapter<MenuItem> {
	int resource;
	Context context;
	
	/**
	 * Default constructor for the adapter 
	 * @param _context
	 * @param _resource
	 * @param _items
	 */
	public MenuAdapter(Context _context, int _resource, List<MenuItem> _items) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LinearLayout resultView;
		
		//Get the current item to be formatted
		MenuItem item = getItem(position);
		
		//get the id of the menu item
	    int id = item.getiD();
		
		if(convertView == null){
			resultView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
			vi.inflate(resource, resultView, true);
		}
		else{
			resultView = (LinearLayout)convertView;
		}
		
		//Set menu item values
		TextView textView = (TextView)resultView.findViewById(R.id.text);
		ImageView indicatorView = (ImageView)resultView.findViewById(R.id.imgMenu);
		
		//Set result status image
		indicatorView.setImageDrawable(resultView.getResources().getDrawable(R.drawable.imenu));
		
		//Set the menu item title based on the id
		if(id == 0)
			textView.setText(context.getString(R.string.generaladvice));
		else if(id == 1)
			textView.setText(context.getString(R.string.pcalc));
		else if(id == 2)
			textView.setText(context.getString(R.string.history));
		else if(id == 3)
			textView.setText(context.getString(R.string.aboutus));
		else if(id == 4)
			textView.setText(context.getString(R.string.privacytitle));
		
		return resultView;
	}
}

package com.cs.pausis;

import android.content.Context;

import java.util.*;

import com.core.pausis.R;
import com.cs.pausis.models.MenuItem;

import android.view.*;
import android.widget.*;

public class MenuAdapter extends ArrayAdapter<MenuItem> {
	int resource;
	Context context;
	
	public MenuAdapter(Context _context, int _resource, List<MenuItem> _items) {
		super(_context, _resource, _items);
		resource = _resource;
		context = _context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LinearLayout resultView;
		MenuItem item = getItem(position);
		
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
		
		//Set the menu title
		if(id == 0)
			textView.setText(context.getString(R.string.generaladvice));
		else if(id == 1)
			textView.setText(context.getString(R.string.pcalc));
		else if(id == 2)
			textView.setText(context.getString(R.string.aboutus));
		else if(id == 3)
			textView.setText(context.getString(R.string.privacytitle));
		
		return resultView;
	}
}

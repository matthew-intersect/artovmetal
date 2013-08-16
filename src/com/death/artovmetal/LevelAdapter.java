package com.death.artovmetal;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LevelAdapter extends ArrayAdapter<Level>
{
	private ArrayList<Level> levels;
	
	public LevelAdapter(Context context, int textViewResourceId, ArrayList<Level> objects)
	{
		super(context, textViewResourceId, objects);
		this.levels = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		
		if (v == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.level_list_item, null);
		}
		
		Level team = levels.get(position);
		
		if(team != null)
		{
			TextView levelName = (TextView) v.findViewById(R.id.level_name);
			TextView levelCompletion = (TextView) v.findViewById(R.id.level_completion);
			
			levelName.setText("Level " + levels.get(position).getNumber());
			levelCompletion.setText(levels.get(position).getCorrect() + "/" +
					levels.get(position).getAlbumCount());
		}
		return v;
	}
}

package com.death.artovmetal;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter
{
	private Context context;
	private LayoutInflater inflater;
	 
    public ArrayList<Image> images;
 
    public ImageAdapter(Context c, ArrayList<Image> imgs)
    {
        context = c;
        images = imgs;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    @Override
    public int getCount()
    {
        return images.size();
    }
 
    @Override
    public Object getItem(int position)
    {
        return images.get(position);
    }
 
    @Override
    public long getItemId(int position)
    {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	convertView = inflater.inflate(R.layout.image_board_item, null);
    	convertView.setLayoutParams(new GridView.LayoutParams(200, 200));
    	ImageView art = (ImageView) convertView.findViewById(R.id.albumArt);
    	art.setScaleType(ImageView.ScaleType.CENTER_CROP);
    	ImageView status = (ImageView) convertView.findViewById(R.id.albumStatus);
    	art.setImageResource(context.getResources().getIdentifier(images.get(position).getFilename(),
        		"drawable", context.getPackageName()));
    	
    	if(images.get(position).getImageStatus()==ImageStatus.CORRECT)
    	{
    		status.setImageResource(R.drawable.correct);
    	}
    	else if(images.get(position).getImageStatus()==ImageStatus.INCORRECT)
    	{
    		status.setImageResource(R.drawable.incorrect);
    	}
    	
    	return convertView;
    }
}

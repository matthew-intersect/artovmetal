package com.death.artovmetal;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter
{
	private Context context;
	 
    public ArrayList<Image> images;
 
    public ImageAdapter(Context c, ArrayList<Image> imgs)
    {
        context = c;
        images = imgs;
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
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(context.getResources().getIdentifier(images.get(position).getFilename(),
        		"drawable", context.getPackageName()));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
        return imageView;
    }
}

package com.death.artovmetal;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoaderTask extends AsyncTask<Object, Void, Integer>
{
	private ImageView img, statusImg;
	private Context context;
	private String filename;
	private ImageStatus status;

	public ImageLoaderTask(ImageView imv, ImageView statusView, Context context, String filename, ImageStatus status)
	{
        this.img = imv;
        this.statusImg = statusView;
        this.context = context;
        this.filename = filename;
        this.status = status;
	}
	
	@Override
	protected Integer doInBackground(Object... params)
	{
		return context.getResources().getIdentifier(filename, "drawable", context.getPackageName());
	}
	
	@Override
    protected void onPostExecute(Integer result)
	{
		img.setImageResource(result);
		if(status==ImageStatus.CORRECT)
    	{
    		statusImg.setImageResource(R.drawable.correct);
    	}
    	else if(status==ImageStatus.INCORRECT)
    	{
    		statusImg.setImageResource(R.drawable.incorrect);
    	}
    }
}

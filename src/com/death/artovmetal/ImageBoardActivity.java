package com.death.artovmetal;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class ImageBoardActivity extends Activity
{
	ImageDatabaseAdapter imageDatabaseAdapter;
	Button back;
	ArrayList<Image> images;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_board);
		
		imageDatabaseAdapter = new ImageDatabaseAdapter(this);
		imageDatabaseAdapter = imageDatabaseAdapter.open();
		
		back = (Button) findViewById(R.id.btnBackToMenu);
		GridView imageGrid = (GridView) findViewById(R.id.image_grid);
		
		images = new ArrayList<Image>();
		Bundle extras = getIntent().getExtras();
		images = imageDatabaseAdapter.getImagesByLevel(extras.getInt("level"));
		ImageAdapter imageAdapter = new ImageAdapter(this, images);
		imageGrid.setAdapter(imageAdapter);
		
		imageGrid.setOnItemClickListener(new OnItemClickListener()
		{
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	        {
	        	if(images.get(position).getImageStatus() == ImageStatus.INCORRECT ||
	        			images.get(position).getImageStatus() == ImageStatus.UNANSWERED)
	        	{
		        	Intent album = new Intent(ImageBoardActivity.this, GuessImageActivity.class);
		            album.putExtra("albumID", images.get(position).getId());
		            startActivity(album);
	        	}
	        	else
	        	{
	        		final Dialog dialog = new Dialog(ImageBoardActivity.this);
	        		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    				dialog.setContentView(R.layout.correct_album);
    				
    				TextView correct = (TextView) dialog.findViewById(R.id.correct);
    				correct.setVisibility(View.GONE);
    				TextView albumSummary = (TextView) dialog.findViewById(R.id.albumSummary);
    				albumSummary.setText(images.get(position).getArtist() + "\n" + images.get(position).getAlbum());
    				Button ok = (Button) dialog.findViewById(R.id.correctOk);
    				
    				ok.setOnClickListener(new View.OnClickListener() 
        			{
						@Override
						public void onClick(View v)
						{
							dialog.dismiss();
						}
        			});
        			dialog.show();
	        	}
	        }
	    });
		
		back.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent home = new Intent(ImageBoardActivity.this, LevelListActivity.class);
				startActivity(home);
				finish();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.score_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
	    MenuItem score = menu.findItem(R.id.score);
	    score.setTitle("Score: " + getLevelScore());
	    return true;
	}
	
	private String getLevelScore()
	{
		int correct = 0;
		for(Image img : images)
		{
			if(img.getImageStatus()==ImageStatus.CORRECT)
			{
				correct++;
			}
		}
		return String.valueOf(correct) + "/" +  images.size();
	}
}

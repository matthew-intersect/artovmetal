package com.death.artovmetal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GuessImageActivity extends Activity
{
	ImageDatabaseAdapter imageDatabaseAdapter;
	Image image;
	ImageView album;
	Button check;
	EditText albumAnswer;
	TextView back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		
		album = (ImageView) findViewById(R.id.albumView);
		check = (Button) findViewById(R.id.btnCheck);
		albumAnswer = (EditText) findViewById(R.id.albumAnswer);
		back = (TextView) findViewById(R.id.returnToList);
		
		imageDatabaseAdapter = new ImageDatabaseAdapter(this);
		imageDatabaseAdapter = imageDatabaseAdapter.open();
		
		Bundle extras = getIntent().getExtras();
	    image = imageDatabaseAdapter.getImageById(extras.getInt("albumID"));
	    
	    album.setImageResource(getResources().getIdentifier(image.getFilename(),
        		"drawable", getPackageName()));
	    
	    check.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				String answer = albumAnswer.getText().toString().trim();
				if(answer.equalsIgnoreCase(image.getAlbum()))
				{
					imageDatabaseAdapter.setAlbumStatus(image, ImageStatus.CORRECT);
					final Dialog dialog = new Dialog(GuessImageActivity.this);
    				dialog.setContentView(R.layout.correct_album);
    				
    				dialog.setTitle("Correct");
    				TextView albumSummary = (TextView) dialog.findViewById(R.id.albumSummary);
    				albumSummary.setText(image.getArtist() + " - " + image.getAlbum());
    				Button ok = (Button) dialog.findViewById(R.id.correctOk);
    				
    				ok.setOnClickListener(new View.OnClickListener() 
        			{
						@Override
						public void onClick(View v)
						{
							Intent imageBoard = new Intent(GuessImageActivity.this, ImageBoardActivity.class);
							imageBoard.putExtra("level", image.getLevel());
							startActivity(imageBoard);
							dialog.dismiss();
							finish();
						}
        			});
        			dialog.show();
				}
				else
				{
					imageDatabaseAdapter.setAlbumStatus(image, ImageStatus.INCORRECT);
					Toast.makeText(GuessImageActivity.this, "Incorrect", Toast.LENGTH_LONG).show();
				}
			}
		});
	    
	    back.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent albumBoard = new Intent(GuessImageActivity.this, ImageBoardActivity.class);
				startActivity(albumBoard);
			}
		});
	}
	
}

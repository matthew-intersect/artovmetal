package com.death.artovmetal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GuessImageActivity extends Activity
{
	ImageDatabaseAdapter imageDatabaseAdapter;
	Image image;
	ImageView album;
	Button check;
	EditText albumAnswer;
	TextView answerError, back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		
		album = (ImageView) findViewById(R.id.albumView);
		check = (Button) findViewById(R.id.btnCheck);
		albumAnswer = (EditText) findViewById(R.id.albumAnswer);
		answerError = (TextView) findViewById(R.id.answerError);
		back = (TextView) findViewById(R.id.returnToList);
		
		imageDatabaseAdapter = new ImageDatabaseAdapter(this);
		imageDatabaseAdapter = imageDatabaseAdapter.open();
		
		Bundle extras = getIntent().getExtras();
	    image = imageDatabaseAdapter.getImageById(extras.getInt("albumID"));
	    
	    album.setImageResource(getResources().getIdentifier(image.getFilename(),
        		"drawable", getPackageName()));
	    
	}
	
}

package com.death.artovmetal;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
	    
	    if(image.getLastIncorrect().length()!=0)
	    {
	    	albumAnswer.setText(image.getLastIncorrect());
	    }
	    
	    album.setImageResource(getResources().getIdentifier(image.getFilename(),
        		"drawable", getPackageName()));
	    
	    check.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				String answer = albumAnswer.getText().toString().trim();
				if(answer.equalsIgnoreCase(image.getAlbum()))
				{
					imageDatabaseAdapter.setAlbumStatus(image, ImageStatus.CORRECT, null);
					final Dialog dialog = new Dialog(GuessImageActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    				dialog.setContentView(R.layout.correct_album);
    				
    				dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
    				{
    					@Override
    				    public void onCancel(DialogInterface dialog)
    				    {
							Intent levelList = new Intent(getApplicationContext(), ImageBoardActivity.class);
							levelList.putExtra("level", image.getLevel());
							GuessImageActivity.this.finish();
							startActivity(levelList);
    				    }
    				});
    				
    				TextView albumSummary = (TextView) dialog.findViewById(R.id.albumSummary);
    				albumSummary.setText(image.getArtist() + "\n" + image.getAlbum());
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
					imageDatabaseAdapter.setAlbumStatus(image, ImageStatus.INCORRECT, 
							albumAnswer.getText().toString().trim());
					Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
			        findViewById(R.id.albumAnswer).startAnimation(shake);
					Toast toast = Toast.makeText(GuessImageActivity.this, "Incorrect", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 100);
					toast.show();
				}
			}
		});
	    
	    back.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent albumBoard = new Intent(GuessImageActivity.this, ImageBoardActivity.class);
				albumBoard.putExtra("level", image.getLevel());
				startActivity(albumBoard);
			}
		});
	}
	
	@Override
	public void onBackPressed()
	{
		Intent levelList = new Intent(this, ImageBoardActivity.class);
		levelList.putExtra("level", image.getLevel());
		startActivity(levelList);
		finish();
	}
}

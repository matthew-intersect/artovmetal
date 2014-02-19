package com.death.artovmetal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
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
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		album = (ImageView) findViewById(R.id.albumView);
		check = (Button) findViewById(R.id.btnCheck);
		albumAnswer = (EditText) findViewById(R.id.albumAnswer);
		
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
	    
	    albumAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if(actionId == EditorInfo.IME_ACTION_GO)
				{
					check.performClick();
					return true;
				}
				return false;
			}
		});
	    
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
							Intent imageBoard = new Intent(getApplicationContext(), ImageBoardActivity.class);
							imageBoard.putExtra("level", image.getLevel());
							GuessImageActivity.this.finish();
							startActivity(imageBoard);
							finish();
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
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		Intent imageBoard = new Intent(GuessImageActivity.this, ImageBoardActivity.class);
		imageBoard.putExtra("level", image.getLevel());
		startActivity(imageBoard);
		finish();
		return true;
	}
	
	@Override
	public void onBackPressed()
	{
		Intent imageBoard = new Intent(this, ImageBoardActivity.class);
		imageBoard.putExtra("level", image.getLevel());
		startActivity(imageBoard);
		finish();
	}
}

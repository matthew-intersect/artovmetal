package com.death.artovmetal;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity
{
	ImageDatabaseAdapter imageDatabaseAdapter;
	Button play;
	TextView score;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		imageDatabaseAdapter = new ImageDatabaseAdapter(this);
		imageDatabaseAdapter = imageDatabaseAdapter.open();
		
		play = (Button) findViewById(R.id.btnPlay);
		score = (TextView) findViewById(R.id.totalScore);
		ImageView logo = (ImageView) findViewById(R.id.logo);
		logo.setImageResource(R.drawable.dfm);
		score.setText("Score: " + imageDatabaseAdapter.getTotalScore() + "/100");
		
		play.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent imageBoard = new Intent(getApplicationContext(), LevelListActivity.class);
				startActivity(imageBoard);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.reset_game:
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
				    .setNegativeButton("No", dialogClickListener).show();
				return true;
			}
		}
		return false;
	}
	
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
	{
	    @Override
	    public void onClick(DialogInterface dialog, int which)
	    {
	        switch (which)
	        {
		        case DialogInterface.BUTTON_POSITIVE:
		            imageDatabaseAdapter.resetGame();
		            finish();
		            startActivity(getIntent());
		            break;
	
		        case DialogInterface.BUTTON_NEGATIVE:
		            dialog.dismiss();
		            break;
	        }
	    }
	};

}

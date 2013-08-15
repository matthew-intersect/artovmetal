package com.death.artovmetal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity
{
	ImageDatabaseAdapter imageDatabaseAdapter;
	Button play;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		imageDatabaseAdapter = new ImageDatabaseAdapter(this);
		imageDatabaseAdapter = imageDatabaseAdapter.open();
		
		play = (Button) findViewById(R.id.btnPlay);
		
		play.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent imageBoard = new Intent(getApplicationContext(), ImageBoardActivity.class);
				startActivity(imageBoard);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}

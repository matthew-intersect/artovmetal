package com.death.artovmetal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
		ImageView logo = (ImageView) findViewById(R.id.logo);
		logo.setImageResource(R.drawable.dfm);
		
		play.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent imageBoard = new Intent(getApplicationContext(), LevelListActivity.class);
				startActivity(imageBoard);
			}
		});
	}

}

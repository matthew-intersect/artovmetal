package com.death.artovmetal;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

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
		images = imageDatabaseAdapter.getImages();
		imageGrid.setAdapter(new ImageAdapter(this, images));
		
		back.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent home = new Intent(ImageBoardActivity.this, HomeActivity.class);
				startActivity(home);
				finish();
			}
		});
		
	}
}

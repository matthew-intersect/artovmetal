package com.death.artovmetal;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

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
		ImageAdapter imageAdapter = new ImageAdapter(this, images);
		imageGrid.setAdapter(imageAdapter);
		
		imageGrid.setOnItemClickListener(new OnItemClickListener()
		{
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	        {
	            Toast.makeText(ImageBoardActivity.this, images.get(position).getAlbum(), Toast.LENGTH_SHORT).show();
	        }
	    });
		
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

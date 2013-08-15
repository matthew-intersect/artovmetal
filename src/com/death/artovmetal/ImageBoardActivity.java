package com.death.artovmetal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ImageBoardActivity extends Activity
{
	Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_board);
		
		back = (Button) findViewById(R.id.btnBackToMenu);
		
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

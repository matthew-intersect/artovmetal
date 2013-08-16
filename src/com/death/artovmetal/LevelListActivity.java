package com.death.artovmetal;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

@SuppressLint("HandlerLeak")
public class LevelListActivity extends ListActivity
{
	private ArrayList<Level> levels = new ArrayList<Level>();
	ImageDatabaseAdapter imageDatabaseAdapter;
	private Runnable viewParts;
	private LevelAdapter levelAdapter;
	Button back;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_list);
		
		imageDatabaseAdapter = new ImageDatabaseAdapter(this);
		imageDatabaseAdapter = imageDatabaseAdapter.open();
		
		back = (Button) findViewById(R.id.back_to_menu);
		
		
		back.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view) 
			{
				Intent home = new Intent(LevelListActivity.this, HomeActivity.class);
				startActivity(home);
				finish();
			}
		});
		
		viewParts = new Runnable()
		{
        	public void run()
        	{
        		handler.sendEmptyMessage(0);
        	}
        };
        
        Thread thread =  new Thread(null, viewParts, "MagentoBackground");
        thread.start();
	}
	
	public void onListItemClick(ListView l, View v, int pos, long id)
	{
		Intent imageBoard = new Intent(LevelListActivity.this, ImageBoardActivity.class);
		imageBoard.putExtra("level", levels.get(pos).getNumber());
		startActivity(imageBoard);
	}
	
	private Handler handler = new Handler()
   	{
    	public void handleMessage(Message msg)
   		{
			levels = imageDatabaseAdapter.getLevels();
   			
   			levelAdapter = new LevelAdapter(LevelListActivity.this, R.layout.level_list_item, levels);

   	        setListAdapter(levelAdapter);
   		}
   	};
}

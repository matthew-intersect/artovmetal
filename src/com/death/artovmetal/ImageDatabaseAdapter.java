package com.death.artovmetal;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ImageDatabaseAdapter 
{
	static final String DATABASE_NAME = "artovmetal.db";
	static final int DATABASE_VERSION = 5;
	
	static final String IMAGE_TABLE_CREATE = "create table image"+
	                             "( id integer primary key autoincrement, filename text, artist text," +
	                             "album text, status integer, level integer); ";
	public  SQLiteDatabase db;
	private final Context context;
	private DatabaseHelper dbHelper;
	
	public  ImageDatabaseAdapter(Context _context) 
	{
		context = _context;
		dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public ImageDatabaseAdapter open() throws SQLException 
	{
		db = dbHelper.getWritableDatabase();
		if(emptyDatabase())
		{
			populateImages();
		}
		return this;
	}
	
	public void close() 
	{
		db.close();
	}
	
	public  SQLiteDatabase getDatabaseInstance()
	{
		return db;
	}
	
	private void populateImages()
	{
		try
		{
			InputStream xml = getClass().getResourceAsStream("images.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xml);
			doc.getDocumentElement().normalize();
			
			NodeList images = doc.getElementsByTagName("image");
			
			for(int i=0; i<images.getLength(); i++)
			{
				Node image = images.item(i);
				if(image.getNodeType() == Node.ELEMENT_NODE)
				{
					Element img = (Element) image;
					String filename = img.getElementsByTagName("filename").item(0).getTextContent();
					String artist = img.getElementsByTagName("artist").item(0).getTextContent();
					String album = img.getElementsByTagName("album").item(0).getTextContent();
					int level = Integer.parseInt(img.getElementsByTagName("level").item(0).getTextContent());
					addImage(filename, artist, album, level);
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean emptyDatabase()
	{
		return db.query("image", null, null, null, null, null, null).getCount() == 0;
	}
	
	public void addImage(String filename, String artist, String album, int level)
	{
	   ContentValues newValues = new ContentValues();
		newValues.put("filename", filename);
		newValues.put("artist", artist);
		newValues.put("album", album);
		newValues.put("status", 0); // default status for image
		newValues.put("level",level);
	
		db.insert("image", null, newValues);
	}
	
	public int deleteImage(int id)
	{
	    String where="id=?";
	    int numberOFEntriesDeleted = db.delete("image", where, new String[]{String.valueOf(id)});
	    return numberOFEntriesDeleted;
	}

	public ArrayList<Image> getImages()
	{
		ArrayList<Image> images = new ArrayList<Image>();
		Cursor imgCursor = db.query("image", null, null, null, null, null, null);
		while(imgCursor.moveToNext())
		{
			int id = imgCursor.getInt(imgCursor.getColumnIndex("id"));
			String filename = imgCursor.getString(imgCursor.getColumnIndex("filename"));
			String artist = imgCursor.getString(imgCursor.getColumnIndex("artist"));
			String album = imgCursor.getString(imgCursor.getColumnIndex("album"));
			int status = imgCursor.getInt(imgCursor.getColumnIndex("status"));
			int level = imgCursor.getInt(imgCursor.getColumnIndex("level"));
			
			Image image = new Image(id, filename, artist, album, status, level);
			images.add(image);
		}
		imgCursor.close();
		return images;
	}

	public Image getImageById(int id)
	{
		Cursor imgCursor = db.query("image", null, " id=?", new String[]{String.valueOf(id)}, null, null, null);
		imgCursor.moveToFirst();
		int albumId = imgCursor.getInt(imgCursor.getColumnIndex("id"));
		String filename = imgCursor.getString(imgCursor.getColumnIndex("filename"));
		String artist = imgCursor.getString(imgCursor.getColumnIndex("artist"));
		String album = imgCursor.getString(imgCursor.getColumnIndex("album"));
		int status = imgCursor.getInt(imgCursor.getColumnIndex("status"));
		int level = imgCursor.getInt(imgCursor.getColumnIndex("level"));
		imgCursor.close();
		return new Image(albumId, filename, artist, album, status, level);
	}

	public void setAlbumStatus(Image image, ImageStatus correct)
	{
		ContentValues updatedValues = new ContentValues();
		updatedValues.put("status", correct.getStatusNumber());
		String where="id = ?";
		
		db.update("image", updatedValues, where, new String[]{String.valueOf(image.getId())});
	}
		
}
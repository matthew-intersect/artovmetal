package com.death.artovmetal;

public class Image
{
	private int id;
	private String filename;
	private String artist;
	private String album;
	private String lastIncorrect;
	private ImageStatus imageStatus;
	private int level;
	
	public Image()
	{
	}
	
	public Image(int id, String filename, String artist, String album, 
			String lastIncorrect, int status, int level)
	{
		this.id = id;
		this.filename = filename;
		this.artist = artist;
		this.album = album;
		this.lastIncorrect = lastIncorrect;
		this.level = level;
		switch (status)
		{
			case 0: 
				this.imageStatus = ImageStatus.UNANSWERED;
				break;
			case 1: 
				this.imageStatus = ImageStatus.CORRECT;
				break;
			case 2: 
				this.imageStatus = ImageStatus.INCORRECT;
				break;
		}
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public String getArtist()
	{
		return artist;
	}

	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	public String getAlbum()
	{
		return album;
	}

	public void setAlbum(String album)
	{
		this.album = album;
	}

	public String getLastIncorrect()
	{
		return lastIncorrect;
	}

	public void setLastIncorrect(String lastIncorrect)
	{
		this.lastIncorrect = lastIncorrect;
	}

	public ImageStatus getImageStatus()
	{
		return imageStatus;
	}

	public void setImageStatus(ImageStatus imageStatus)
	{
		this.imageStatus = imageStatus;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}
}

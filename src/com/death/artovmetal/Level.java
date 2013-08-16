package com.death.artovmetal;

public class Level
{
	int number;
	int albumCount;
	int correct;
	
	Level()
	{
	}
	
	Level(int number, int albumCount, int correct)
	{
		this.number = number;
		this.albumCount = albumCount;
		this.correct = correct;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public int getAlbumCount()
	{
		return albumCount;
	}

	public void setAlbumCount(int albumCount)
	{
		this.albumCount = albumCount;
	}

	public int getCorrect()
	{
		return correct;
	}

	public void setCorrect(int correct)
	{
		this.correct = correct;
	}
}

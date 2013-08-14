package com.death.artovmetal;

public enum ImageStatus
{
	UNANSWERED(0), CORRECT(1), INCORRECT(2);
	
	private final int statusNumber;
	
	private ImageStatus(int statusNumber)
	{
		this.statusNumber = statusNumber;
	}
	
	public int getStatusNumber()
	{
		return this.statusNumber;
	}

}

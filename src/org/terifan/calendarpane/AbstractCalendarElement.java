package org.terifan.calendarpane;

import java.awt.Color;
import java.io.Serializable;
import org.terifan.util.Calendar;


public abstract class AbstractCalendarElement implements Serializable, CalendarWidget
{
	private final static long serialVersionUID = 1L;

	protected Calendar mDate;
	protected Color mColor;
	protected int mOffset;


	public AbstractCalendarElement(Calendar aDate, Color aColor, int aOffset)
	{
		mDate = aDate;
		mColor = aColor;
		mOffset = aOffset;
	}


	public Calendar getDate()
	{
		return mDate;
	}


	public Color getColor()
	{
		return mColor;
	}


	public int getOffset()
	{
		return mOffset;
	}
}

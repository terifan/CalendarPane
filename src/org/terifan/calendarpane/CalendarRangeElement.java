package org.terifan.calendarpane;

import org.terifan.util.Calendar;


public class CalendarRangeElement
{
	protected Calendar mFromDate;
	protected Calendar mToDate;


	public CalendarRangeElement(Calendar aFromDate, Calendar aToDate)
	{
		mFromDate = aFromDate;
		mToDate = aToDate;
	}


	public Calendar getFromDate()
	{
		return mFromDate;
	}


	public void setFromDate(Calendar aFromDate)
	{
		mFromDate = aFromDate;
	}


	public Calendar getToDate()
	{
		return mToDate;
	}


	public void setToDate(Calendar aToDate)
	{
		mToDate = aToDate;
	}
}

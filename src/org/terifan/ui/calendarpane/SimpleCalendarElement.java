package org.terifan.ui.calendarpane;

import org.terifan.util.Calendar;



public class SimpleCalendarElement extends CalendarElement
{
	public SimpleCalendarElement(Calendar aFromDate, Calendar aToDate, String aText)
	{
		super.setText(aText);
		mFromDate = aFromDate;
		mToDate = aToDate;
	}
}

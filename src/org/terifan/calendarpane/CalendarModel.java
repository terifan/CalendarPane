package org.terifan.calendarpane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class CalendarModel implements Serializable
{
	private final static long serialVersionUID = 1L;

	private ArrayList<CalendarWidget> mItems;


	public CalendarModel()
	{
		mItems = new ArrayList<>();
	}


	public void add(CalendarWidget... aElement)
	{
		mItems.addAll(Arrays.asList(aElement));
	}


	public ArrayList<CalendarWidget> getItems()
	{
		return mItems;
	}
}

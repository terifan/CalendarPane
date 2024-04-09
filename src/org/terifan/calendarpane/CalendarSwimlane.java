package org.terifan.calendarpane;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;


public class CalendarSwimlane implements CalendarWidget
{
	protected ArrayList<CalendarWidget> mItems;
	protected final Color mColor0;
	protected final Color mColor1;


	public CalendarSwimlane(Color aColor0, Color aColor1)
	{
		mColor0 = aColor0;
		mColor1 = aColor1;
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


	@Override
	public void paint(CalendarPane aCalendarPane, Graphics2D aGraphics, int aWidth, int aHeight)
	{
		for (CalendarWidget item : getItems())
		{
			item.paint(aCalendarPane, aGraphics, aWidth, aHeight);
		}

//		aGraphics.setPaint(new GradientPaint(0, mOffset0, mColor0, 0, mOffset1, mColor1));
//		aGraphics.fillRect(0, mOffset0, aWidth, mOffset1 - mOffset0);
	}
}

package org.terifan.calendarpane;

import java.awt.Color;
import java.awt.Graphics2D;
import org.terifan.util.Calendar;


public class CalendarDot extends AbstractCalendarElement
{
	private final static long serialVersionUID = 1L;


	public CalendarDot(Calendar aDate, Color aColor, int aOffset)
	{
		super(aDate, aColor, aOffset);
	}


	@Override
	public void paint(CalendarPane aCalendarPane, Graphics2D aGraphics, int aWidth, int aHeight)
	{
		int x = aCalendarPane.dateToOffset(mDate);
		int y = mOffset;

		int r = 4;
		aGraphics.setColor(mColor);
		aGraphics.fillOval(x - r, y - r, 1 + r * 2, 1 + r * 2);
		r /= 2;
		aGraphics.setColor(Color.WHITE);
		aGraphics.fillOval(x - r, y - r, 1 + r * 2, 1 + r * 2);
	}
}

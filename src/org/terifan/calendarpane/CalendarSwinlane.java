package org.terifan.calendarpane;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;


public class CalendarSwinlane implements CalendarWidget
{
	protected final int mOffset0;
	protected final Color mColor0;
	protected final int mOffset1;
	protected final Color mColor1;


	public CalendarSwinlane(int aOffset0, Color aColor0, int aOffset1, Color aColor1)
	{
		mOffset0 = aOffset0;
		mColor0 = aColor0;
		mOffset1 = aOffset1;
		mColor1 = aColor1;
	}


	@Override
	public void paint(CalendarPane aCalendarPane, Graphics2D aGraphics, int aWidth, int aHeight)
	{
		aGraphics.setPaint(new GradientPaint(0, mOffset0, mColor0, 0, mOffset1, mColor1));
		aGraphics.fillRect(0, mOffset0, aWidth, mOffset1 - mOffset0);
	}
}

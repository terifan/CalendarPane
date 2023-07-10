package org.terifan.calendarpane;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;


public class CalendarLine implements CalendarWidget
{
	private ArrayList<AbstractCalendarElement> mElements;


	public CalendarLine(AbstractCalendarElement... aElements)
	{
		mElements = new ArrayList<>(Arrays.asList(aElements));
	}


	public CalendarLine add(AbstractCalendarElement aElement)
	{
		mElements.add(aElement);
		return this;
	}


	@Override
	public void paint(CalendarPane aCalendarPane, Graphics2D aGraphics, int aWidth, int aHeight)
	{
		AbstractCalendarElement prev = mElements.get(0);
		for (int i = 1; i < mElements.size(); i++)
		{
			AbstractCalendarElement next = mElements.get(i);
			paintGradientLine(aCalendarPane, aGraphics, prev, next);
			prev = next;
		}
	}


	protected void paintGradientLine(CalendarPane aCalendarPane, Graphics2D aGraphics, AbstractCalendarElement aFrom, AbstractCalendarElement aTo)
	{
		int x0 = aCalendarPane.dateToOffset(aFrom.getDate());
		int x1 = aCalendarPane.dateToOffset(aTo.getDate());

		aGraphics.setPaint(new GradientPaint(x0, aFrom.getOffset(), aFrom.getColor(), x1, aTo.getOffset(), aTo.getColor()));
		aGraphics.drawLine(x0, aFrom.getOffset(), x1, aTo.getOffset());
	}
}

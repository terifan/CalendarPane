package org.terifan.calendarpane;

import java.awt.Color;
import java.awt.Graphics2D;
import org.terifan.util.Calendar;


public class CalendarText extends AbstractCalendarElement
{
	private final static long serialVersionUID = 1L;
	private String mText;


	public CalendarText(Calendar aDate, Color aColor, int aOffset, String aText)
	{
		super(aDate, aColor, aOffset);
		mText = aText;
	}


	@Override
	public void paint(CalendarPane aCalendarPane, Graphics2D aGraphics, int aWidth, int aHeight)
	{
		int w = 2 * 5 + aGraphics.getFontMetrics().stringWidth(mText);
		int h = 2 * 2 + aGraphics.getFontMetrics().getHeight();

		int x = aCalendarPane.dateToOffset(mDate);
		int y = mOffset;

		aGraphics.setColor(Color.WHITE);
		aGraphics.fillRect(x - w / 2, y-h/2, w, h);
		aGraphics.setColor(Color.BLACK);
		aGraphics.drawRect(x - w / 2, y-h/2, w, h);
		aGraphics.drawString(mText, x - w / 2 + 5, y + h/2 - aGraphics.getFontMetrics().getDescent() - 2);
	}
}

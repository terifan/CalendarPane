package org.terifan.ui.calendarpane;

import java.awt.Color;
import javax.swing.BorderFactory;
import org.terifan.util.Calendar;


public class SimpleCalendarElement extends CalendarElement
{
	public enum Style
	{
		YELLOW,
		BLUE,
		GREEN
	}


	public SimpleCalendarElement(Calendar aFromDate, Calendar aToDate, String aText)
	{
		super.setText(aText);
		super.setFromDate(aFromDate);
		super.setToDate(aToDate);

		setColorStyle(Style.YELLOW);
	}


	public SimpleCalendarElement setColorStyle(Style aStyle)
	{
		switch (aStyle)
		{
			case YELLOW:
				super.setForegroundColor(Color.BLACK);
				super.setBackgroundColor(new Color(255,255,240));
				super.setBackgroundHighlightColor(new Color(255,255,255,200));
				super.setBorder(BorderFactory.createLineBorder(new Color(240,240,240), 1));
				super.setSelectedForegroundColor(Color.BLACK);
				super.setSelectedBackgroundColor(new Color(255,255,200));
				super.setSelectedBackgroundHighlightColor(new Color(255,255,255,200));
				super.setSelectedBorder(BorderFactory.createLineBorder(new Color(255,255,64), 2));
				break;

			case BLUE:
				super.setForegroundColor(Color.BLACK);
				super.setBackgroundColor(new Color(240,250,255));
				super.setBackgroundHighlightColor(new Color(255,255,255,200));
				super.setBorder(BorderFactory.createLineBorder(new Color(240,240,240), 1));
				super.setSelectedForegroundColor(Color.BLACK);
				super.setSelectedBackgroundColor(new Color(200,215,255));
				super.setSelectedBackgroundHighlightColor(new Color(255,255,255,180));
				super.setSelectedBorder(BorderFactory.createLineBorder(new Color(180,200,255), 3));
				break;

			case GREEN:
				super.setForegroundColor(Color.BLACK);
				super.setBackgroundColor(new Color(240,255,240));
				super.setBackgroundHighlightColor(new Color(255,255,255,200));
				super.setBorder(BorderFactory.createLineBorder(new Color(240,240,240), 1));
				super.setSelectedForegroundColor(Color.BLACK);
				super.setSelectedBackgroundColor(new Color(200,255,200));
				super.setSelectedBackgroundHighlightColor(new Color(255,255,255,180));
				super.setSelectedBorder(BorderFactory.createLineBorder(new Color(200,235,200), 3));
				break;
		}

		return this;
	}
}

package org.terifan.calendarpane;

import java.awt.Graphics2D;


public interface CalendarWidget
{
	void paint(CalendarPane aCalendarPane, Graphics2D aGraphics, int aWidth, int aHeight);
}

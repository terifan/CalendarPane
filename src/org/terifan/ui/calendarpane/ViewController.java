package org.terifan.ui.calendarpane;


public interface ViewController<T extends CalendarElement>
{
	boolean isVisible(T aCalendarElement);
}

package org.terifan.ui.calendarpane;


public interface CalendarChangeListener
{
	default void elementDragged(CalendarPane aCalendarPane, CalendarElement aCalendarElement)
	{
	}

	default void elementReleased(CalendarPane aCalendarPane, CalendarElement aCalendarElement)
	{
	}
}

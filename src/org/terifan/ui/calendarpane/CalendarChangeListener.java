package org.terifan.ui.calendarpane;


public interface CalendarChangeListener<T extends CalendarElement>
{
	default void elementDragged(CalendarPane<T> aCalendarPane, T aCalendarElement)
	{
	}

	default void elementReleased(CalendarPane<T> aCalendarPane, T aCalendarElement)
	{
	}
}

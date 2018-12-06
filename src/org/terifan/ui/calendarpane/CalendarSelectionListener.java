package org.terifan.ui.calendarpane;


@FunctionalInterface
public interface CalendarSelectionListener<T extends CalendarElement>
{
	void onSelection(T aCalendarElement);
}

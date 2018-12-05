package org.terifan.ui.calendarpane.demo;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.terifan.ui.calendarpane.CalendarPane;
import org.terifan.ui.calendarpane.CalendarPaneRowHeaderView;
import org.terifan.ui.calendarpane.SimpleCalendarElement;
import org.terifan.ui.calendarpane.SimpleCalendarElement.Style;
import org.terifan.util.Calendar;



public class Test
{
	public static void main(String ... args)
	{
		try
		{
			CalendarPane calendarPane = new CalendarPane(new Calendar("2018-11-01"), new Calendar("2018-12-01"));

			calendarPane.addElement(new SimpleCalendarElement(new Calendar("2018-11-01 04:00"), new Calendar("2018-11-01 08:00"), "sleeping...").setColorStyle(Style.GREEN));
			calendarPane.addElement(new SimpleCalendarElement(new Calendar("2018-11-01 08:00"), new Calendar("2018-11-01 12:00"), "hello world"));
			calendarPane.addElement(new SimpleCalendarElement(new Calendar("2018-11-01 12:30"), new Calendar("2018-11-01 15:00"), "blablabla blablabla blablabla blablabla blablabla").setColorStyle(Style.BLUE));

			calendarPane.layoutCalendar();

			JScrollPane scrollPane = new JScrollPane(calendarPane);
			scrollPane.setRowHeaderView(new CalendarPaneRowHeaderView(calendarPane));
			scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			scrollPane.getVerticalScrollBar().setBlockIncrement(200);
			scrollPane.setBorder(null);

			JFrame frame = new JFrame();
			frame.add(scrollPane);
			frame.setSize(1024, 768);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}

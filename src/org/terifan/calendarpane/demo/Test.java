package org.terifan.calendarpane.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import org.terifan.calendarpane.CalendarDot;
import org.terifan.calendarpane.CalendarLine;
import org.terifan.calendarpane.CalendarModel;
import org.terifan.calendarpane.CalendarPane;
import org.terifan.calendarpane.CalendarSwimlane;
import org.terifan.calendarpane.CalendarText;
import org.terifan.util.Calendar;


public class Test
{
	public static void main(String... args)
	{
		try
		{
			CalendarModel model = new CalendarModel();

			CalendarSwimlane swimlane = new CalendarSwimlane(new Color(170, 190, 255, 128), new Color(210, 220, 240, 128));
			model.add(swimlane);

			CalendarText text1 = new CalendarText(new Calendar("2023-07-10 08:30"), Color.GRAY, 1, "Monkey");
			CalendarText text2 = new CalendarText(new Calendar("2023-07-10 12:30"), Color.GRAY, 1, "Chimp");

			CalendarDot dot1 = new CalendarDot(new Calendar("2023-07-10 07:30"), Color.RED, 0);
			CalendarDot dot2 = new CalendarDot(new Calendar("2023-07-10 09:30"), Color.GREEN, 0);
			CalendarDot dot3 = new CalendarDot(new Calendar("2023-07-10 10:30"), Color.BLUE, 0);
			CalendarDot dot4 = new CalendarDot(new Calendar("2023-07-10 11:30"), Color.CYAN, 0);
			CalendarDot dot5 = new CalendarDot(new Calendar("2023-07-10 12:30"), Color.MAGENTA, 0);
			CalendarDot dot6 = new CalendarDot(new Calendar("2023-07-10 13:30"), Color.YELLOW, 0);

			swimlane.add(new CalendarLine(dot1, text1));
			swimlane.add(new CalendarLine(dot2, text1));
			swimlane.add(new CalendarLine(dot3, text1));
			swimlane.add(new CalendarLine(dot4, text2));
			swimlane.add(new CalendarLine(dot5, text2));
			swimlane.add(new CalendarLine(dot6, text2));
			swimlane.add(new CalendarLine(dot3, dot4));
			swimlane.add(text1, text2, dot1, dot2, dot3, dot4, dot5, dot6);

			CalendarPane calendarPane = new CalendarPane(model, new Calendar("2023-07-10"), new Calendar("2023-07-10"));

			JFrame frame = new JFrame();
			frame.setLayout(new BorderLayout());
			frame.add(calendarPane, BorderLayout.CENTER);
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

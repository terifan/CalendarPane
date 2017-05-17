package org.terifan.ui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import org.terifan.ui.Anchor;
import org.terifan.ui.TextBox;
import org.terifan.util.Calendar;


public class CalendarPaneRowHeaderView extends JComponent
{
	private int mMajorUnitHeight = 80;
	private int mMinorUnitHeight = 20;
	private int mTimeBarWidth = 52;

	private final CalendarPane mCalendarPane;


	public CalendarPaneRowHeaderView(CalendarPane aCalendarPane)
	{
		mCalendarPane = aCalendarPane;
	}


	@Override
	protected void paintComponent(Graphics aGraphics)
	{
		Graphics2D g = (Graphics2D)aGraphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		int width = getWidth();
		int height = getHeight();

		g.setColor(getBackground());
		g.fillRect(0, 0, width, height);

		for (int y = 0; y < height; y += mMinorUnitHeight)
		{
			if (g.hitClip(0, y, width, mMinorUnitHeight + 6))
			{
				g.setColor(new Color(169,176,184));
				g.drawLine(mTimeBarWidth-21, y, mTimeBarWidth-5, y);
				g.setColor(new Color(230,237,247));
				g.drawLine(mTimeBarWidth, y, width, y);
			}
		}
		for (int y = 0; y < height; y += mMajorUnitHeight)
		{
			if (g.hitClip(0, y, width, mMajorUnitHeight + 6))
			{
				g.setColor(new Color(169,176,184));
				g.drawLine(0, y, mTimeBarWidth, y);
				g.setColor(new Color(165,191,225));
				g.drawLine(mTimeBarWidth, y, width, y);
			}
		}
		for (int y = 0; y < height; y += 24 * mMajorUnitHeight)
		{
			if (g.hitClip(0, y, width, 24 * mMajorUnitHeight + 6))
			{
				g.setColor(new Color(0,0,0));
				g.drawLine(0, y, mTimeBarWidth, y);
				g.drawLine(mTimeBarWidth, y, width, y);
			}
		}
		g.setColor(new Color(235,137,0));
		g.drawLine(mTimeBarWidth - 1, 0, mTimeBarWidth - 1, height);

		for (int y = 0; y < height; y += mMajorUnitHeight)
		{
			if (g.hitClip(0, y - 6, width, mMajorUnitHeight + 6))
			{
				Calendar calendar = new Calendar(mCalendarPane.getStartTime() + y / mMajorUnitHeight * 60 * 60 * 1000L);

				if ("00:00".equals(calendar.format("HH:mm")))
				{
					new TextBox(calendar.format("MMM dd").toUpperCase()).setAnchor(Anchor.NORTH).setFont(new Font("segoe ui", Font.BOLD, 11)).setBounds(0, y + 1, mTimeBarWidth, mMajorUnitHeight).setPadding(0, 4, 0, 4).render(g);
				}
				else
				{
					new TextBox(calendar.format("HH")).setAnchor(Anchor.NORTH_EAST).setFont(new Font("segoe ui", Font.PLAIN, 10)).setBounds(0, y + 1, mTimeBarWidth*3/4, mMajorUnitHeight).setPadding(3, 0, 0, 2).render(g);
					new TextBox(calendar.format("mm")).setAnchor(Anchor.NORTH_WEST).setFont(new Font("segoe ui", Font.PLAIN, 10)).setBounds(mTimeBarWidth*3/4, y + 1, mTimeBarWidth*1/4, mMajorUnitHeight).setPadding(0, 0, 3, 0).render(g);
				}
			}
		}
	}


	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(52, mCalendarPane.getPreferredSize().height);
	}
}

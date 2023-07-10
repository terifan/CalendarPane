package org.terifan.calendarpane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import javax.swing.JPanel;
import org.terifan.util.Calendar;


public class CalendarPane extends JPanel
{
	private final static long serialVersionUID = 1L;

	private final static Color MAJOR_LINE_COLOR = new Color(0xA5BFE1);
	private final static Color MINOR_LINE_COLOR = new Color(0xE6EDF7);
	private final static Color BACKGROUND_COLOR = new Color(0xFFFFFF);
	private final static Color LEGEND_LINE_COLOR = new Color(0xB0B0B0);
	private final static Color LEGEND_BACKGROUND = new Color(0xF0F0F0);

	private CalendarModel mModel;
	private boolean mHorizontal = true;
	private int legendPadding = 5;
	private int mStartHour = 0;
	private int mEndHour = 24;
	private int mMinutStep = 30;


	public CalendarPane(CalendarModel aModel, Calendar aFirstDate, Calendar aLastDate)
	{
		mModel = aModel;

		super.setBackground(Color.WHITE);
		super.requestFocus();
	}


	public void setModel(CalendarModel aModel)
	{
		mModel = aModel;
	}


	@Override
	protected void paintComponent(Graphics aGraphics)
	{
		Graphics2D g = (Graphics2D)aGraphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		int x = 0;
		int y = 0;
		int w = getWidth();
		int h = getHeight();

		g.setColor(getBackground());
		g.fillRect(x, y, w, h);

		if (getBorder() != null)
		{
			Insets insets = getBorder().getBorderInsets(this);
			x += insets.left;
			y += insets.top;
			w -= insets.left + insets.right;
			h -= insets.top + insets.bottom;
		}

		g.setColor(BACKGROUND_COLOR);
		g.fillRect(x, y, w, h);

		if (mHorizontal)
		{
			int legendHeight = 2 * legendPadding + g.getFontMetrics().getHeight();

			g.setColor(LEGEND_BACKGROUND);
			g.fillRect(x, y + h - legendHeight + 1, w, legendHeight);

			for (int i = 0; i <= mEndHour - mStartHour; i++)
			{
				for (int j = 0; j < 60; j += mMinutStep)
				{
					int x0 = x + w * (i * 60 + j) / ((mEndHour - mStartHour) * 60);

					if (j == 0)
					{
						g.setColor(MAJOR_LINE_COLOR);
						g.drawLine(x0, y, x0, y + h - legendHeight);
						g.setColor(LEGEND_LINE_COLOR);
						g.drawLine(x0, y + h - legendHeight, x0, y + h);
						if (i == mEndHour - mStartHour)
						{
							break;
						}
					}
					else
					{
						g.setColor(MINOR_LINE_COLOR);
						g.drawLine(x0, y, x0, y + h - legendHeight);
					}

					if (j == 0)
					{
						g.setColor(getForeground());
						g.drawString(String.format("%02d:%02d", i + mStartHour, j), x0 + legendPadding, y + h - legendPadding - g.getFontMetrics().getDescent());
					}
				}
			}

			g.setColor(MAJOR_LINE_COLOR);
			g.drawLine(x, y + h - legendHeight, x + w, y + h - legendHeight);
		}
		else
		{
			int legendWidth = 2 * legendPadding + g.getFontMetrics().stringWidth("00:00");

			g.setColor(LEGEND_BACKGROUND);
			g.fillRect(x, y, legendWidth, h);

			for (int i = 0; i <= mEndHour - mStartHour; i++)
			{
				for (int j = 0; j < 60; j += mMinutStep)
				{
					int y0 = y + h * (i * 60 + j) / ((mEndHour - mStartHour) * 60);

					if (j == 0)
					{
						g.setColor(LEGEND_LINE_COLOR);
						g.drawLine(x, y0, x + legendWidth, y0);
						g.setColor(MAJOR_LINE_COLOR);
						g.drawLine(x + legendWidth, y0, x + w, y0);
						if (i == mEndHour - mStartHour)
						{
							break;
						}
					}
					else
					{
						g.setColor(MINOR_LINE_COLOR);
						g.drawLine(x + legendWidth, y0, x + w, y0);
					}

					if (j == 0)
					{
						g.setColor(getForeground());
						g.drawString(String.format("%02d:%02d", i + mStartHour, j), x + legendPadding, y0 + g.getFontMetrics().getAscent());
					}
				}
			}

			g.setColor(MAJOR_LINE_COLOR);
			g.drawLine(x + legendWidth, y, x + legendWidth, y + h);
		}

		Shape oldClip = g.getClip();
		g.setClip(x, y, w, h);
		for (CalendarWidget item : mModel.getItems())
		{
			item.paint(this, g, w, h);
		}
		g.setClip(oldClip);
	}


	protected int dateToOffset(Calendar aCalendar)
	{
		return (mHorizontal ? getWidth() : getHeight()) * ((aCalendar.getHour() - mStartHour) * 60 * 60 + aCalendar.getMinute() * 60 + aCalendar.getSecond()) / ((mEndHour - mStartHour) * 60 * 60);
	}

//	@Override
//	public Dimension getPreferredSize()
//	{
//		return new Dimension(mBounds.width + mPadding.left + mPadding.right, (int)((mEndDate - mStartDate) / 1000 / 60 / 60 * mMajorUnitHeight));
//	}
}

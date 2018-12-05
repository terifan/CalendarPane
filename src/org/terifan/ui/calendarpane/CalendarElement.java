package org.terifan.ui.calendarpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import org.terifan.ui.TextBox;
import org.terifan.util.Calendar;


public abstract class CalendarElement
{
	protected Calendar mFromDate;
	protected Calendar mToDate;
	protected Rectangle mTimeBounds;
	protected Rectangle mVisualBounds;
	protected boolean mSelected;
	protected TextBox mText;
	protected Border mSelectedBorder;
	protected Color mSelectedBackgroundColor;
	protected Color mSelectedBackgroundHighlightColor;
	protected Color mSelectedForegroundColor;
	protected Border mBorder;
	protected Border mUpdatedBorder;
	protected Color mBackgroundColor;
	protected Color mBackgroundHighlightColor;
	protected Color mForegroundColor;
	protected boolean mEnabled;
	protected boolean mUpdated;


	public CalendarElement()
	{
		mEnabled = true;
		mText = new TextBox().setPadding(3, 3, 3, 3);

		mForegroundColor = Color.BLACK;
		mBackgroundColor = new Color(255,255,240);
		mBackgroundHighlightColor = new Color(255,255,255,200);
		mBorder = BorderFactory.createLineBorder(new Color(240,240,240), 1);

		mSelectedForegroundColor = Color.BLACK;
		mSelectedBackgroundColor = new Color(255,255,200);
		mSelectedBackgroundHighlightColor = new Color(255,255,255,200);
		mSelectedBorder = BorderFactory.createLineBorder(new Color(255,255,128), 2);

		mUpdatedBorder = BorderFactory.createLineBorder(new Color(255,0,0), 2);
	}


	public boolean isEnabled()
	{
		return mEnabled;
	}


	public void setEnabled(boolean aEnabled)
	{
		mEnabled = aEnabled;
	}


	public boolean isUpdated()
	{
		return mUpdated;
	}


	public void setUpdated(boolean aUpdated)
	{
		mUpdated = aUpdated;
	}


	public boolean isSelected()
	{
		return mSelected;
	}


	public void setSelected(boolean aSelected)
	{
		mSelected = aSelected;
	}


	public Color getBackgroundColor()
	{
		return mBackgroundColor;
	}


	public void setBackgroundColor(Color aBackgroundColor)
	{
		mBackgroundColor = aBackgroundColor;
	}


	public Border getBorder()
	{
		return mBorder;
	}


	public void setBorder(Border aBorder)
	{
		mBorder = aBorder;
	}


	public Color getForegroundColor()
	{
		return mForegroundColor;
	}


	public void setForegroundColor(Color aForegroundColor)
	{
		mForegroundColor = aForegroundColor;
	}


	public Calendar getFromDate()
	{
		return mFromDate;
	}


	public CalendarElement setFromDate(Calendar aFromDate)
	{
		mFromDate = aFromDate;
		return this;
	}


	public Calendar getToDate()
	{
		return mToDate;
	}


	public CalendarElement setToDate(Calendar aToDate)
	{
		mToDate = aToDate;
		return this;
	}


	public Rectangle getTimeBounds()
	{
		return mTimeBounds;
	}


	public void setTimeBounds(Rectangle aTimeBounds)
	{
		mTimeBounds = aTimeBounds;
	}


	public Rectangle getVisualBounds()
	{
		return mVisualBounds;
	}


	public CalendarElement setVisualBounds(Rectangle aVisualBounds)
	{
		mVisualBounds = aVisualBounds;
		return this;
	}


	public Border getSelectedBorder()
	{
		return mSelectedBorder;
	}


	public void setSelectedBorder(Border aSelectedBorder)
	{
		this.mSelectedBorder = aSelectedBorder;
	}


	public Color getSelectedBackgroundColor()
	{
		return mSelectedBackgroundColor;
	}


	public void setSelectedBackgroundColor(Color aSelectedBackgroundColor)
	{
		this.mSelectedBackgroundColor = aSelectedBackgroundColor;
	}


	public Color getSelectedForegroundColor()
	{
		return mSelectedForegroundColor;
	}


	public void setSelectedForegroundColor(Color aSelectedForegroundColor)
	{
		this.mSelectedForegroundColor = aSelectedForegroundColor;
	}


	public Color getBackgroundHighlightColor()
	{
		return mBackgroundHighlightColor;
	}


	public void setBackgroundHighlightColor(Color aBackgroundHighlightColor)
	{
		mBackgroundHighlightColor = aBackgroundHighlightColor;
	}


	public Color getSelectedBackgroundHighlightColor()
	{
		return mSelectedBackgroundHighlightColor;
	}


	public void setSelectedBackgroundHighlightColor(Color aSelectedBackgroundHighlightColor)
	{
		this.mSelectedBackgroundHighlightColor = aSelectedBackgroundHighlightColor;
	}


	public TextBox getText()
	{
		return mText;
	}


	public void setText(Object aText)
	{
		mText.setText(aText);
	}


	public Dimension getPreferredSize()
	{
		return mText.measure().getSize();
	}


	public void render(Graphics2D aGraphics)
	{
		aGraphics.setColor(mSelected ? mSelectedBackgroundHighlightColor : mBackgroundHighlightColor);
		aGraphics.fill(mVisualBounds);

		aGraphics.setColor(mSelected ? mSelectedBackgroundColor : mBackgroundColor);
		aGraphics.fill(mTimeBounds);

		(mUpdated ? mUpdatedBorder : mSelected ? mSelectedBorder : mBorder).paintBorder(null, aGraphics, mVisualBounds.x, mVisualBounds.y, mVisualBounds.width, mVisualBounds.height);

		mText.setForeground(mSelected ? mSelectedForegroundColor : mForegroundColor).setBounds(mVisualBounds).render(aGraphics);
	}
}
